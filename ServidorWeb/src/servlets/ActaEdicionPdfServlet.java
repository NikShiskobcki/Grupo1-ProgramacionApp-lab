package servlets;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.DetalleEdicionCurso;
import modelo.Docente;
import modelo.EdicionCurso;
import modelo.EvaluacionEstudiante;
import modelo.UsuarioResumen;
import util.ActaEstudio;
import util.ClienteCentral;
import util.ServletRemoto;

/**
 * Caso de uso: Ver resultados de evaluaciones en ediciones de cursos (US-09).
 * Genera dinámicamente, en cada pedido, el PDF del acta de una edición cerrada
 * con iText. Solo para un docente que participa en la edición.
 */
@WebServlet("/acta-edicion-pdf")
public class ActaEdicionPdfServlet extends ServletRemoto {

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BaseColor COLOR = new BaseColor(50, 98, 103);
    private static final BaseColor GRIS = new BaseColor(238, 242, 243);

    private ClienteCentral control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        UsuarioResumen usuario = session == null ? null
                : (UsuarioResumen) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!"Docente".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los docentes pueden obtener el acta de una edición.");
            return;
        }

        String nombreEdicion = request.getParameter("edicion");
        if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/acta-edicion");
            return;
        }
        nombreEdicion = nombreEdicion.trim();

        DetalleEdicionCurso detalle = control.consultarEdicion(nombreEdicion);
        if (detalle == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "La edición no existe.");
            return;
        }
        if (!participa(usuario.getNickname(), nombreEdicion)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo podés obtener el acta de ediciones en las que participás.");
            return;
        }
        // Se valida en el servidor: no alcanza con que el enlace esté visible.
        if (!control.edicionCerrada(nombreEdicion)) {
            response.sendError(HttpServletResponse.SC_CONFLICT,
                    "El acta solo está disponible cuando la edición está cerrada.");
            return;
        }

        List<EvaluacionEstudiante> evaluaciones =
                control.listarEvaluacionesPorEdicion(nombreEdicion);

        byte[] pdf;
        try {
            pdf = generarPdf(detalle, evaluaciones);
        } catch (DocumentException e) {
            throw new ServletException("No se pudo generar el PDF del acta.", e);
        }

        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"acta-edicion-" + archivo(nombreEdicion) + ".pdf\"");
        response.setContentLength(pdf.length);
        response.getOutputStream().write(pdf);
    }

    // ------------------------------------------------------------------

    private boolean participa(String nicknameDocente, String nombreEdicion) {
        EdicionCurso edicion = control.buscarEdicion(nombreEdicion);
        if (edicion == null || edicion.getDocentes() == null) {
            return false;
        }
        for (Docente docente : edicion.getDocentes()) {
            if (docente.getNickname().equals(nicknameDocente)) {
                return true;
            }
        }
        return false;
    }

    private byte[] generarPdf(DetalleEdicionCurso edicion,
            List<EvaluacionEstudiante> evaluaciones) throws DocumentException {

        // Se arma en memoria y recién después se envía: si algo falla no
        // queda un PDF cortado a la mitad en el navegador.
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 54, 54, 54, 54);
        PdfWriter.getInstance(doc, salida);

        doc.addTitle("Acta de edición - " + edicion.getNombre());
        doc.addAuthor("edEXT");
        doc.open();

        Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Font.NORMAL, COLOR);
        Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.GRAY);
        Font etiqueta = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Font.NORMAL, BaseColor.BLACK);
        Font texto = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
        Font cabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.NORMAL, BaseColor.WHITE);
        Font celda = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        Font pie = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY);

        Paragraph p = new Paragraph("ACTA DE EDICIÓN", titulo);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);

        p = new Paragraph("Plataforma educativa edEXT", subtitulo);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(8);
        doc.add(p);

        doc.add(new Chunk(new LineSeparator(1f, 100, COLOR, Element.ALIGN_CENTER, -2)));

        Paragraph datos = linea("Edición: ", edicion.getNombre(), etiqueta, texto);
        datos.setSpacingBefore(18);
        doc.add(datos);
        doc.add(linea("Curso: ", edicion.getCurso(), etiqueta, texto));
        doc.add(linea("Período: ", fecha(edicion.getFechaInicio()) + " a "
                + fecha(edicion.getFechaFin()), etiqueta, texto));
        doc.add(linea("Docentes: ", edicion.getDocentes() == null || edicion.getDocentes().isEmpty()
                ? "—" : String.join(", ", edicion.getDocentes()), etiqueta, texto));

        PdfPTable tabla = new PdfPTable(new float[]{0.6f, 3.2f, 2.2f, 1.2f, 1.8f});
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(16);
        tabla.setHeaderRows(1);

        for (String h : new String[]{"#", "Estudiante", "Nickname", "Nota", "Resultado"}) {
            PdfPCell c = new PdfPCell(new Phrase(h, cabecera));
            c.setBackgroundColor(COLOR);
            c.setPadding(6);
            tabla.addCell(c);
        }

        int n = 0;
        int conNota = 0;
        int suma = 0;
        int aprobados = 0;
        boolean alterna = false;
        for (EvaluacionEstudiante ev : evaluaciones) {
            n++;
            Integer nota = ev.getNota();
            String resultado = "Sin nota";
            if (nota != null) {
                conNota++;
                suma += nota;
                if (nota >= ActaEstudio.NOTA_APROBACION) {
                    aprobados++;
                    resultado = "Aprobado";
                } else {
                    resultado = "No aprobado";
                }
            }
            String[] valores = {
                String.valueOf(n),
                ev.getNombreEstudiante(),
                ev.getNicknameEstudiante(),
                nota == null ? "—" : String.valueOf(nota),
                resultado
            };
            for (int i = 0; i < valores.length; i++) {
                PdfPCell c = new PdfPCell(new Phrase(valores[i] == null ? "" : valores[i], celda));
                c.setPadding(5);
                if (alterna) c.setBackgroundColor(GRIS);
                if (i == 0 || i == 3 || i == 4) c.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(c);
            }
            alterna = !alterna;
        }
        doc.add(tabla);

        String promedio = conNota == 0 ? "—"
                : String.format(java.util.Locale.US, "%.1f", (double) suma / conNota);
        p = new Paragraph("Participantes: " + evaluaciones.size() + "    Aprobados: "
                + aprobados + "    Nota promedio: " + promedio, etiqueta);
        p.setSpacingBefore(14);
        doc.add(p);

        p = new Paragraph("Emitida el " + LocalDate.now().format(FECHA) + ".", texto);
        p.setSpacingBefore(24);
        doc.add(p);

        p = new Paragraph("Se considera aprobado un curso con nota igual o mayor a "
                + ActaEstudio.NOTA_APROBACION + " (escala de 1 a 12). "
                + "Documento generado automáticamente por edEXT.", pie);
        p.setSpacingBefore(12);
        doc.add(p);

        doc.close();
        return salida.toByteArray();
    }

    private static String fecha(LocalDate f) {
        return f == null ? "—" : f.format(FECHA);
    }

    private static Paragraph linea(String etiqueta, String valor, Font fEtiqueta, Font fTexto) {
        Paragraph p = new Paragraph();
        p.setIndentationLeft(18);
        p.add(new Chunk(etiqueta, fEtiqueta));
        p.add(new Chunk(valor == null ? "" : valor, fTexto));
        return p;
    }

    /** Nombre de archivo seguro: solo letras, números y guiones. */
    private static String archivo(String nombre) {
        String limpio = Normalizer.normalize(nombre, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^A-Za-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
        return limpio.isEmpty() ? "edicion" : limpio;
    }
}
