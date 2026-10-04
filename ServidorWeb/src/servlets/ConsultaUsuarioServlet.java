package servlets;

import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.DTO.DetalleUsuario;
import Logica.DTO.UsuarioResumen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/perfil")
public class ConsultaUsuarioServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nickname = request.getParameter("user");

        HttpSession session = request.getSession(false);
        UsuarioResumen usuarioLogueado = (session != null) ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;

        // Si no se pasa parámetro 'user', se muestra el perfil del usuario logueado
        if (nickname == null || nickname.trim().isEmpty()) {
            if (usuarioLogueado != null) {
                nickname = usuarioLogueado.getNickname();
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
        }

        String quienConsulta = (usuarioLogueado != null) ? usuarioLogueado.getNickname() : null;
        DetalleUsuario detalle = control.consultarUsuario(nickname.trim(), quienConsulta);

        if (detalle != null) {
            request.setAttribute("detalleUsuario", detalle);
            request.setAttribute("esPropioPerfil", (quienConsulta != null && quienConsulta.equalsIgnoreCase(nickname.trim())));
            request.getRequestDispatcher("/perfil.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "El usuario no existe.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
