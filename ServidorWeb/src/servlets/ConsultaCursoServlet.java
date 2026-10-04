package servlets;

import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.DTO.DetalleCurso;
import Logica.Entidades.Curso;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/curso-detalle")
public class ConsultaCursoServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreCurso = request.getParameter("nombre");

        if (nombreCurso == null || nombreCurso.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        DetalleCurso detalle = control.consultarCurso(nombreCurso.trim());

        if (detalle != null) {
            request.setAttribute("detalleCurso", detalle);
            request.getRequestDispatcher("/cursoDetalle.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "El curso solicitado no fue encontrado.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
