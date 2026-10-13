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
 * Modificar contraseña. Solo el propio Estudiante/Docente (con sesión iniciada) puede
 * cambiar su contraseña: debe ingresar la actual y la nueva con su confirmación.
 * El nickname siempre sale de la sesión (nunca del formulario).
 */
@WebServlet("/cambiar-contrasenia")
public class CambiarContraseniaServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    private UsuarioResumen logueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (logueado(request) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/cambiarContrasenia.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UsuarioResumen usr = logueado(request);
        if (usr == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Igual que en el alta y el login, las contraseñas se comparan sin espacios en los extremos
        String actual = trim(request.getParameter("contraseniaActual"));
        String nueva = trim(request.getParameter("contraseniaNueva"));
        String confirmacion = trim(request.getParameter("confirmarContrasenia"));

        String error = null;
        if (actual.isEmpty() || nueva.isEmpty() || confirmacion.isEmpty()) {
            error = "Debe completar todos los campos.";
        } else if (!nueva.equals(confirmacion)) {
            error = "La nueva contraseña y su confirmación no coinciden.";
        } else if (nueva.equals(actual)) {
            error = "La nueva contraseña debe ser distinta de la actual.";
        }

        if (error == null) {
            try {
                if (!control.cambiarContrasenia(usr.getNickname(), actual, nueva)) {
                    error = "La contraseña actual es incorrecta.";
                }
            } catch (Exception e) {
                error = "No se pudo cambiar la contraseña. Intentá nuevamente.";
            }
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/cambiarContrasenia.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/perfil?msg=contrasenia");
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
