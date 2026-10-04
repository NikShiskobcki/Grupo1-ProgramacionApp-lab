package servlets;

import Logica.DTO.DetalleProgramaFormacion;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Consulta de Programa de Formación (destino de la navegación desde el perfil de usuario). */
@WebServlet("/programa-detalle")
public class ConsultaProgramaServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        DetalleProgramaFormacion detalle = control.consultarPrograma(nombre.trim());
        if (detalle == null) {
            request.setAttribute("error", "El programa de formación solicitado no fue encontrado.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        request.setAttribute("detallePrograma", detalle);
        request.getRequestDispatcher("/programaDetalle.jsp").forward(request, response);
    }
}
