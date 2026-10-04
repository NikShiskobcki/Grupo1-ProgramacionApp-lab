package servlets;

import Logica.DTO.DetalleUsuario;
import Logica.DTO.ElementoResumen;
import Logica.DTO.InscripcionResumen;
import Logica.DTO.ResultadoInscripcion;
import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Consulta de Usuario (US-04, US-05, US-06, US-08).
 * - /perfil            : perfil del usuario logueado.
 * - /perfil?user=nick  : perfil de cualquier usuario (visitantes incluidos).
 */
@WebServlet("/perfil")
public class ConsultaUsuarioServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nickname = request.getParameter("user");

        HttpSession session = request.getSession(false);
        UsuarioResumen usuarioLogueado = (session != null)
                ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;

        // Sin parámetro 'user' se muestra el perfil del usuario logueado
        if (nickname == null || nickname.trim().isEmpty()) {
            if (usuarioLogueado != null) {
                nickname = usuarioLogueado.getNickname();
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        String quienConsulta = (usuarioLogueado != null) ? usuarioLogueado.getNickname() : null;
        DetalleUsuario detalle = control.consultarUsuario(nickname.trim(), quienConsulta);

        if (detalle == null) {
            request.setAttribute("error", "El usuario no existe.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        boolean propio = quienConsulta != null && quienConsulta.equals(detalle.getNickname());
        request.setAttribute("detalleUsuario", detalle);
        request.setAttribute("esPropioPerfil", propio);

        if (propio) {
            if ("Estudiante".equalsIgnoreCase(detalle.getTipoUsuario())) {
                // US-05: estado de cada inscripción (Inscripto / Aceptada / Rechazada)
                Map<String, String> estados = new HashMap<>();
                List<ResultadoInscripcion> resultados = control.listarResultadosPorEstudiante(detalle.getNickname());
                if (resultados != null) {
                    for (ResultadoInscripcion r : resultados) {
                        estados.put(r.getNombreEdicion(), r.getEstado());
                    }
                }
                request.setAttribute("estadosInscripcion", estados);

            } else if ("Docente".equalsIgnoreCase(detalle.getTipoUsuario())) {
                // US-06: aceptados por cada edición en la que participa el docente
                Map<String, List<InscripcionResumen>> aceptados = new LinkedHashMap<>();
                if (detalle.getEdiciones() != null) {
                    for (ElementoResumen ed : detalle.getEdiciones()) {
                        aceptados.put(ed.getNombre(), control.listarAceptadosPorEdicion(ed.getNombre()));
                    }
                }
                request.setAttribute("aceptadosPorEdicion", aceptados);
            }
        }

        request.getRequestDispatcher("/perfil.jsp").forward(request, response);
    }
}
