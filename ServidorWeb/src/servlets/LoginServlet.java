package servlets;

import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.DTO.UsuarioResumen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");

        if (usuario == null || usuario.trim().isEmpty() || contrasenia == null || contrasenia.trim().isEmpty()) {
            request.setAttribute("error", "Debe ingresar usuario/email y contraseña.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        UsuarioResumen usr = control.iniciarSesion(usuario.trim(), contrasenia.trim());

        if (usr != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("usuarioLogueado", usr);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("error", "Credenciales inválidas. Verifique sus datos.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
