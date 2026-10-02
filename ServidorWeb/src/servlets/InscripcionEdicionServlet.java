package servlets;

import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.DTO.UsuarioResumen;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/inscribir-edicion")
public class InscripcionEdicionServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UsuarioResumen usuarioLogueado = (session != null) ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;

        if (usuarioLogueado == null || !"Estudiante".equalsIgnoreCase(usuarioLogueado.getTipo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String nombreEdicion = request.getParameter("nombreEdicion");
        String nombreCurso = request.getParameter("nombreCurso");

        if (nombreEdicion != null && !nombreEdicion.trim().isEmpty()) {
            try {
                control.inscribirEstudianteEdicion(usuarioLogueado.getNickname(), nombreEdicion.trim(), LocalDate.now());
                response.sendRedirect(request.getContextPath() + "/curso-detalle?nombre=" + java.net.URLEncoder.encode(nombreCurso != null ? nombreCurso : "", "UTF-8") + "&msg=inscripto");
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/curso-detalle?nombre=" + java.net.URLEncoder.encode(nombreCurso != null ? nombreCurso : "", "UTF-8") + "&err=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
