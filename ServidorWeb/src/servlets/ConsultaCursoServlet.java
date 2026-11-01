package servlets;

import util.ServletRemoto;

import util.ClienteCentral;

import modelo.DetalleCurso;
import modelo.Curso;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/curso-detalle")
public class ConsultaCursoServlet extends ServletRemoto {

    @Override
    public void init() throws javax.servlet.ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }


    private ClienteCentral control;

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
