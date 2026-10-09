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

        HttpSession session = request.getSession(false);
        UsuarioResumen usr = (session != null)
                ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;
        if (usr == null) {
            response.sendRedirect(ctx + "/login");
            return;
        }

        String objetivo = trim(request.getParameter("user"));
        String accion = trim(request.getParameter("accion"));

        // El usuario a seguir debe existir
        if (objetivo.isEmpty() || control.consultarUsuario(objetivo, null) == null) {
            response.sendRedirect(ctx + "/usuarios");
            return;
        }

        String destino = ctx + "/perfil?user=" + util.Html.url(objetivo);

        // Nadie puede seguirse a sí mismo
        if (objetivo.equals(usr.getNickname())) {
            response.sendRedirect(destino + "&msg=errorseguir");
            return;
        }

        try {
            if ("seguir".equals(accion)) {
                control.seguirUsuario(usr.getNickname(), objetivo);
                response.sendRedirect(destino + "&msg=seguido");
            } else if ("dejar".equals(accion)) {
                control.dejarDeSeguirUsuario(usr.getNickname(), objetivo);
                response.sendRedirect(destino + "&msg=dejado");
            } else {
                response.sendRedirect(destino + "&msg=errorseguir");
            }
        } catch (Exception e) {
            response.sendRedirect(destino + "&msg=errorseguir");
        }
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
