package servlets;

import Logica.DTO.ResultadoInscripcion;
import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/mis-inscripciones")
public class ResultadosInscripcionesServlet extends HttpServlet {

    private final IControlador control =
            Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        UsuarioResumen usuario = session == null ? null
                : (UsuarioResumen) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!"Estudiante".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Solo los estudiantes pueden consultar "
                    + "sus resultados de inscripción.");
            return;
        }

        response.setHeader("Cache-Control", "no-store");

        /*
         * El nickname se obtiene exclusivamente de la sesión.
         * No aceptamos un parámetro para consultar a otro estudiante.
         */
        List<ResultadoInscripcion> resultados =
                control.listarResultadosPorEstudiante(
                        usuario.getNickname());

        request.setAttribute("resultados", resultados);

        request.getRequestDispatcher("/resultadosInscripciones.jsp")
                .forward(request, response);
    }
}