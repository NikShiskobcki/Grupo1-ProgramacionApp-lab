package servlets;

import Logica.Entidades.Curso;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sugerencias para la barra de búsqueda (AJAX).
 * GET /buscar-sugerencias?q=texto devuelve JSON con hasta MAX_SUGERENCIAS coincidencias
 * de cursos y programas de formación por nombre. Los que empiezan con el texto van primero.
 * Formato: [{"tipo":"Curso|Programa","nombre":"...","detalle":"...","url":"..."}]
 * La página de resultados completa sigue siendo /buscar (se usa al enviar el formulario).
 */
@WebServlet("/buscar-sugerencias")
public class BuscarSugerenciasServlet extends HttpServlet {

    private static final int MIN_CARACTERES = 2;
    private static final int MAX_SUGERENCIAS = 8;

    private final IControlador control = Fabrica.getInstance().getIControlador();

    /** Coincidencia ya calculada, para poder ordenar prefijos antes que "contiene". */
    private static final class Sugerencia {
        final String tipo, nombre, detalle, url;
        final boolean prefijo;
        Sugerencia(String tipo, String nombre, String detalle, String url, boolean prefijo) {
            this.tipo = tipo;
            this.nombre = nombre;
            this.detalle = detalle;
            this.url = url;
            this.prefijo = prefijo;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");

        String q = request.getParameter("q");
        q = (q == null) ? "" : q.trim().toLowerCase(Locale.ROOT);

        List<Sugerencia> encontradas = new ArrayList<>();
        if (q.length() >= MIN_CARACTERES) {
            String ctx = request.getContextPath();

            List<Curso> cursos = control.listarCursos();
            if (cursos != null) {
                for (Curso c : cursos) {
                    String nombre = c.getNombre();
                    if (nombre == null) continue;
                    String n = nombre.toLowerCase(Locale.ROOT);
                    if (n.contains(q)) {
                        String detalle = c.getInstituto() != null ? c.getInstituto().getNombre() : "General";
                        encontradas.add(new Sugerencia("Curso", nombre, detalle,
                                ctx + "/curso-detalle?nombre=" + util.Html.url(nombre),
                                n.startsWith(q)));
                    }
                }
            }

            List<String> programas = control.listarProgramas();
            if (programas != null) {
                for (String nombre : programas) {
                    if (nombre == null) continue;
                    String n = nombre.toLowerCase(Locale.ROOT);
                    if (n.contains(q)) {
                        encontradas.add(new Sugerencia("Programa", nombre, "Programa de formación",
                                ctx + "/programa-detalle?nombre=" + util.Html.url(nombre),
                                n.startsWith(q)));
                    }
                }
            }

            // Prefijo primero, después alfabético
            encontradas.sort((a, b) -> {
                if (a.prefijo != b.prefijo) return a.prefijo ? -1 : 1;
                return a.nombre.compareToIgnoreCase(b.nombre);
            });
        }

        StringBuilder json = new StringBuilder("[");
        int total = Math.min(encontradas.size(), MAX_SUGERENCIAS);
        for (int i = 0; i < total; i++) {
            Sugerencia s = encontradas.get(i);
            if (i > 0) json.append(',');
            json.append("{\"tipo\":").append(jsonString(s.tipo))
                .append(",\"nombre\":").append(jsonString(s.nombre))
                .append(",\"detalle\":").append(jsonString(s.detalle))
                .append(",\"url\":").append(jsonString(s.url))
                .append('}');
        }
        json.append(']');

        PrintWriter out = response.getWriter();
        out.print(json);
    }

    /** Escapa un texto como literal de cadena JSON (el proyecto no usa librería JSON). */
    private static String jsonString(String s) {
        if (s == null) return "\"\"";
        StringBuilder sb = new StringBuilder(s.length() + 2).append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        return sb.append('"').toString();
    }
}
