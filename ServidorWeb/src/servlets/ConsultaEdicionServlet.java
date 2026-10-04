package servlets;

import Logica.DTO.DetalleEdicionCurso;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Consulta de Edición de Curso (destino de la navegación desde el perfil de usuario). */
@WebServlet("/edicion-detalle")
public class ConsultaEdicionServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        DetalleEdicionCurso detalle = control.consultarEdicion(nombre.trim());
        if (detalle == null) {
            request.setAttribute("error", "La edición solicitada no fue encontrada.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        request.setAttribute("detalleEdicion", detalle);
        request.getRequestDispatcher("/edicionDetalle.jsp").forward(request, response);
    }
}
