package servlets;

import util.Recordarme;
import util.ServletRemoto;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Cierra la sesión (solo si hay una iniciada) y vuelve a la página de login. */
@WebServlet("/logout")
public class LogoutServlet extends ServletRemoto {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean haySesion = session != null && session.getAttribute("usuarioLogueado") != null;

        if (haySesion) {
            session.invalidate();
            Recordarme.borrarCookieSesion(request, response);
        }
        // Con o sin sesión, el destino es el login (sin sesión no hay nada que cerrar)
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
