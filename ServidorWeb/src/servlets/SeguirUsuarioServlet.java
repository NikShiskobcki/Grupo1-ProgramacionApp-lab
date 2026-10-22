package servlets;

import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seguir a un usuario / Dejar de seguir a un usuario.
 * Solo un Estudiante/Docente con sesión iniciada puede ejecutarlo (POST /seguir-usuario).
 * Parámetros: user = nickname del usuario objetivo, accion = "seguir" | "dejar".
 * El seguidor siempre sale de la sesión (nunca del formulario).
 *
 * Funciona de dos maneras:
 *  - AJAX (la petición trae la cabecera X-Requested-With: XMLHttpRequest): responde JSON
 *    y la página NO se recarga. Ver main.js.
 *  - Formulario normal (sin JavaScript): redirige a /perfil?user=...&msg=... como antes.
 */
@WebServlet("/seguir-usuario")
public class SeguirUsuarioServlet extends HttpServlet {

    private static final String MSG_ERROR = "No se pudo completar la operación. Intentá nuevamente.";

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Esta operación solo se realiza por POST
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String ctx = request.getContextPath();
        boolean ajax = esAjax(request);

        HttpSession session = request.getSession(false);
        UsuarioResumen usr = (session != null)
                ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;
        if (usr == null) {
            if (ajax) {
                json(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "{\"ok\":false,\"mensaje\":" + jsonString("Tu sesión expiró. Iniciá sesión nuevamente.") + "}");
            } else {
                response.sendRedirect(ctx + "/login");
            }
            return;
        }

        String objetivo = trim(request.getParameter("user"));
        String accion = trim(request.getParameter("accion"));

        // El usuario a seguir debe existir
        if (objetivo.isEmpty() || control.consultarUsuario(objetivo, null) == null) {
            if (ajax) {
                json(response, HttpServletResponse.SC_NOT_FOUND,
                        "{\"ok\":false,\"mensaje\":" + jsonString("El usuario no existe.") + "}");
            } else {
                response.sendRedirect(ctx + "/usuarios");
            }
            return;
        }

        String destino = ctx + "/perfil?user=" + util.Html.url(objetivo);

        // Nadie puede seguirse a sí mismo
        if (objetivo.equals(usr.getNickname())) {
            error(response, ajax, destino, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if ("seguir".equals(accion)) {
                control.seguirUsuario(usr.getNickname(), objetivo);
                exito(request, response, ajax, destino, usr, objetivo, true);
            } else if ("dejar".equals(accion)) {
                control.dejarDeSeguirUsuario(usr.getNickname(), objetivo);
                exito(request, response, ajax, destino, usr, objetivo, false);
            } else {
                error(response, ajax, destino, HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            error(response, ajax, destino, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // ---------- Respuestas ----------

    /** Éxito: JSON para AJAX, redirect con ?msg= para el formulario normal. */
    private void exito(HttpServletRequest request, HttpServletResponse response, boolean ajax,
            String destino, UsuarioResumen usr, String objetivo, boolean siguiendo) throws IOException {
        if (!ajax) {
            response.sendRedirect(destino + (siguiendo ? "&msg=seguido" : "&msg=dejado"));
            return;
        }

        List<UsuarioResumen> seguidores = control.listarSeguidores(objetivo);
        int cantidad = (seguidores != null) ? seguidores.size() : 0;
        String ctx = request.getContextPath();

        // Datos del usuario logueado para que el navegador pueda agregarlo/quitarlo
        // de la pestaña "Seguidores" sin recargar la página.
        String yo = "{\"nickname\":" + jsonString(usr.getNickname())
                + ",\"nombre\":" + jsonString(usr.getNombreCompleto())
                + ",\"tipo\":" + jsonString(usr.getTipo())
                + ",\"imagen\":" + jsonString(rutaImagen(ctx, usr.getRutaImagen()))
                + ",\"url\":" + jsonString(ctx + "/perfil?user=" + util.Html.url(usr.getNickname()))
                + "}";

        String mensaje = siguiendo ? "Ahora seguís a este usuario." : "Dejaste de seguir a este usuario.";
        json(response, HttpServletResponse.SC_OK,
                "{\"ok\":true,\"siguiendo\":" + siguiendo
                + ",\"seguidores\":" + cantidad
                + ",\"mensaje\":" + jsonString(mensaje)
                + ",\"yo\":" + yo + "}");
    }

    /** Error: JSON para AJAX, redirect con ?msg=errorseguir para el formulario normal. */
    private void error(HttpServletResponse response, boolean ajax, String destino, int status)
            throws IOException {
        if (ajax) {
            json(response, status, "{\"ok\":false,\"mensaje\":" + jsonString(MSG_ERROR) + "}");
        } else {
            response.sendRedirect(destino + "&msg=errorseguir");
        }
    }

    private static void json(HttpServletResponse response, int status, String cuerpo) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(cuerpo);
    }

    // ---------- Utilidades ----------

    /** Las peticiones hechas con fetch() desde main.js envían esta cabecera. */
    private static boolean esAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    /**
     * URL de imagen lista para usar como src desde JavaScript (sin escapar a HTML:
     * el navegador la asigna con setAttribute, no con innerHTML). Vacía si no hay imagen
     * o si el esquema no es seguro.
     */
    private static String rutaImagen(String ctx, String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) return "";
        String r = ruta.trim();
        String lower = r.toLowerCase();
        if (lower.startsWith("http://") || lower.startsWith("https://")) return r;
        if (lower.startsWith("javascript:") || lower.startsWith("data:")) return "";
        return r.startsWith("/") ? ctx + r : ctx + "/" + r;
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

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
