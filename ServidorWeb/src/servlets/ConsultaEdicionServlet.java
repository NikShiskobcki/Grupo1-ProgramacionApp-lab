package servlets;

import util.ServletRemoto;

import util.ClienteCentral;

import modelo.DetalleEdicionCurso;
import modelo.UsuarioResumen;
import modelo.Docente;
import modelo.EdicionCurso;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/edicion-detalle")
public class ConsultaEdicionServlet extends ServletRemoto {

    @Override
    public void init() throws javax.servlet.ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    private ClienteCentral control;

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

        boolean esEstudiante = usuario != null
                && "Estudiante".equalsIgnoreCase(usuario.getTipo());

        boolean permiteAcciones = (esEstudiante || puedeVerAceptados)
                && control.listarEdicionesVigentesPorCurso(detalle.getCurso())
                        .stream()
                        .anyMatch(e -> detalle.getNombre().equals(e.getNombre()));

        request.setAttribute("puedeInscribirse", esEstudiante && permiteAcciones);
        request.setAttribute("puedeSeleccionarEstudiantes",
                puedeVerAceptados && permiteAcciones);

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
