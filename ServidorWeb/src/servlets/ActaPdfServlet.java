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
import modelo.DetalleUsuario;
import modelo.UsuarioResumen;
import util.ActaEstudio;
import util.ActaEstudio.Acta;
import util.ActaEstudio.Estado;
import util.ActaEstudio.Fila;
import util.ClienteCentral;
import util.ServletRemoto;

/**
 * Caso de uso: Tramitar Acta de Estudio (paso 2).
 * Genera dinámicamente, en cada pedido, el PDF del acta con iText
 * (Requerimiento Especial 7.4).
 */
@WebServlet("/acta-pdf")
public class ActaPdfServlet extends ServletRemoto {

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
        if (!"Estudiante".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los estudiantes pueden tramitar el acta de estudios.");
            return;
        }

        String nombrePrograma = request.getParameter("programa");
        if (nombrePrograma == null || nombrePrograma.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/acta");
            return;
        }
        nombrePrograma = nombrePrograma.trim();

        String nickname = usuario.getNickname();

        List<String> programas = ActaEstudio.programasDelEstudiante(control, nickname);
        if (!programas.contains(nombrePrograma)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "No estás inscripto en ese programa de formación.");
            return;
        }

        // Se recalcula en el servidor: no alcanza con que el botón esté habilitado.
        Acta acta = ActaEstudio.construir(control, nickname, nombrePrograma);
        if (!acta.isTramitable()) {
            response.sendRedirect(request.getContextPath() + "/acta?programa="
                    + util.Html.url(nombrePrograma) + "&error=incompleto");
            return;
        }

        DetalleUsuario estudiante = control.consultarUsuario(nickname, nickname);

        byte[] pdf;
        try {
            pdf = generarPdf(estudiante, acta);
        } catch (DocumentException e) {
            throw new ServletException("No se pudo generar el PDF del acta.", e);
        }

        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"acta-" + archivo(nombrePrograma) + ".pdf\"");
        response.setContentLength(pdf.length);
        response.getOutputStream().write(pdf);
    }

    // ------------------------------------------------------------------

    private byte[] generarPdf(DetalleUsuario estudiante, Acta acta)
            throws DocumentException {

        // Se arma en memoria y recién después se envía: si algo falla no
        // queda un PDF cortado a la mitad en el navegador.
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 54, 54, 54, 54);
        PdfWriter.getInstance(doc, salida);

        doc.addTitle("Acta de estudios - " + acta.getPrograma().getNombre());
        doc.addAuthor("edEXT");
        doc.open();

        Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Font.NORMAL, COLOR);
        Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.GRAY);
        Font etiqueta = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Font.NORMAL, BaseColor.BLACK);
        Font texto = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
        Font cabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.NORMAL, BaseColor.WHITE);
        Font celda = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        Font pie = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY);

        Paragraph p = new Paragraph("ACTA DE ESTUDIOS", titulo);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);

        p = new Paragraph("Plataforma educativa edEXT", subtitulo);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(8);
        doc.add(p);

        doc.add(new Chunk(new LineSeparator(1f, 100, COLOR, Element.ALIGN_CENTER, -2)));

        p = new Paragraph("Se deja constancia de que:", texto);
        p.setSpacingBefore(18);
        p.setSpacingAfter(10);
        doc.add(p);

        doc.add(linea("Estudiante: ", estudiante.getNombre() + " " + estudiante.getApellido(), etiqueta, texto));
        doc.add(linea("Correo electrónico: ", estudiante.getEmail(), etiqueta, texto));

        p = new Paragraph("aprobó la totalidad de los cursos del programa de formación:", texto);
        p.setSpacingBefore(12);
        p.setSpacingAfter(6);
        doc.add(p);

        Paragraph nombrePrograma = new Paragraph(acta.getPrograma().getNombre(),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, Font.NORMAL, COLOR));
        nombrePrograma.setSpacingAfter(16);
        doc.add(nombrePrograma);

        PdfPTable tabla = new PdfPTable(new float[]{3.2f, 2.6f, 1f, 1.6f, 1.8f});
        tabla.setWidthPercentage(100);
        tabla.setHeaderRows(1);

        for (String h : new String[]{"Curso", "Edición", "Nota", "Resultado", "Fecha"}) {
            PdfPCell c = new PdfPCell(new Phrase(h, cabecera));
            c.setBackgroundColor(COLOR);
            c.setPadding(6);
            tabla.addCell(c);
        }

        boolean alterna = false;
        for (Fila f : acta.getFilas()) {
            String[] valores = {
                f.getNombreCurso(),
                f.getNombreEdicion() == null ? "—" : f.getNombreEdicion(),
                f.getNota() == null ? "—" : String.valueOf(f.getNota()),
                f.getEstado() == Estado.APROBADO ? "Aprobado" : "—",
                f.getFecha() == null ? "—" : f.getFecha().format(FECHA)
            };
            for (int i = 0; i < valores.length; i++) {
                PdfPCell c = new PdfPCell(new Phrase(valores[i], celda));
                c.setPadding(5);
                if (alterna) c.setBackgroundColor(GRIS);
                if (i == 2 || i == 3 || i == 4) c.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(c);
            }
            alterna = !alterna;
        }
        doc.add(tabla);

        p = new Paragraph("Cursos aprobados: " + acta.getCursosAprobados() + " de "
                + acta.getTotalCursos(), etiqueta);
        p.setSpacingBefore(14);
        doc.add(p);

        p = new Paragraph("Emitida el " + LocalDate.now().format(FECHA) + ".", texto);
        p.setSpacingBefore(24);
        doc.add(p);

        p = new Paragraph("Se considera aprobado un curso con nota igual o mayor a "
                + ActaEstudio.NOTA_APROBACION + " (escala de 1 a 12). La fecha indicada es la de "
                + "finalización de la edición cursada. Documento generado automáticamente por edEXT.", pie);
        p.setSpacingBefore(12);
        doc.add(p);

        doc.close();
        return salida.toByteArray();
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
        return limpio.isEmpty() ? "programa" : limpio;
    }
}