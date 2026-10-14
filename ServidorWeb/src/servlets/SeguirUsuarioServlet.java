package servlets;

import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
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
 * Si el pedido trae el encabezado X-Requested-With: fetch (AJAX desde main.js) responde JSON
 * {"ok":true|false,"accion":"seguir|dejar"}; si no, redirige al perfil con ?msg=... como antes.
 */
@WebServlet("/seguir-usuario")
public class SeguirUsuarioServlet extends HttpServlet {

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
        // Pedido AJAX (fetch de main.js): responde JSON. Sin JS se mantiene el redirect de siempre.
        boolean ajax = "fetch".equals(request.getHeader("X-Requested-With"));

        HttpSession session = request.getSession(false);
        UsuarioResumen usr = (session != null)
                ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;
        if (usr == null) {
            if (ajax) {
                responderJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "{\"ok\":false,\"login\":\"" + ctx + "/login\"}");
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
                responderJson(response, HttpServletResponse.SC_NOT_FOUND, "{\"ok\":false}");
            } else {
                response.sendRedirect(ctx + "/usuarios");
            }
            return;
        }

        String destino = ctx + "/perfil?user=" + util.Html.url(objetivo);

        // Nadie puede seguirse a sí mismo
        if (objetivo.equals(usr.getNickname())) {
            terminar(response, ajax, false, accion, destino + "&msg=errorseguir");
            return;
        }

        try {
            if ("seguir".equals(accion)) {
                control.seguirUsuario(usr.getNickname(), objetivo);
                terminar(response, ajax, true, accion, destino + "&msg=seguido");
            } else if ("dejar".equals(accion)) {
                control.dejarDeSeguirUsuario(usr.getNickname(), objetivo);
                terminar(response, ajax, true, accion, destino + "&msg=dejado");
            } else {
                terminar(response, ajax, false, accion, destino + "&msg=errorseguir");
            }
        } catch (Exception e) {
            terminar(response, ajax, false, accion, destino + "&msg=errorseguir");
        }
    }

    /** AJAX: JSON con el resultado. No AJAX: redirect al perfil con el mensaje. */
    private static void terminar(HttpServletResponse response, boolean ajax, boolean ok,
                                 String accion, String urlRedirect) throws IOException {
        if (!ajax) {
            response.sendRedirect(urlRedirect);
            return;
        }
        String a = "seguir".equals(accion) ? "seguir" : ("dejar".equals(accion) ? "dejar" : "");
        responderJson(response, ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST,
                "{\"ok\":" + ok + ",\"accion\":\"" + a + "\"}");
    }

    private static void responderJson(HttpServletResponse response, int status, String json)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(json);
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
