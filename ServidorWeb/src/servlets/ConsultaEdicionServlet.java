package servlets;

import Logica.DTO.DetalleEdicionCurso;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/edicion-detalle")
public class ConsultaEdicionServlet extends HttpServlet {

    private final IControlador control =
            Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-store");

        String nombre = request.getParameter("nombre");

        if (nombre == null || nombre.trim().isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/consulta-edicion");
            return;
        }

        nombre = nombre.trim();

        DetalleEdicionCurso detalle = control.consultarEdicion(nombre);

        if (detalle == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "La edición solicitada no fue encontrada.");
            return;
        }

        HttpSession sesion = request.getSession(false);

        UsuarioResumen usuario = sesion == null ? null
                : (UsuarioResumen) sesion.getAttribute("usuarioLogueado");

        boolean puedeVerAceptados = false;

        if (usuario != null
                && "Docente".equalsIgnoreCase(usuario.getTipo())) {

            EdicionCurso edicion = control.buscarEdicion(nombre);

            if (edicion != null && edicion.getDocentes() != null) {
                for (Docente docente : edicion.getDocentes()) {
                    if (docente.getNickname()
                            .equals(usuario.getNickname())) {

                        puedeVerAceptados = true;
                        break;
                    }
                }
            }
        }

        request.setAttribute("detalleEdicion", detalle);
        request.setAttribute("puedeVerAceptados", puedeVerAceptados);

        // La lista se consulta únicamente después de comprobar el permiso.
        if (puedeVerAceptados) {
            request.setAttribute(
                    "aceptados",
                    control.listarAceptadosPorEdicion(nombre));
        }

        request.getRequestDispatcher("/edicionDetalle.jsp")
                .forward(request, response);
    }
}