package servlets;

import Logica.DTO.DetalleCurso;
import Logica.DTO.DetalleEdicionCurso;
import Logica.DTO.InscripcionResumen;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/aceptados-edicion")
public class AceptadosEdicionServlet extends HttpServlet {

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

        if (!"Docente".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Solo los docentes pueden consultar los aceptados.");
            return;
        }

        response.setHeader("Cache-Control", "no-store");

        String instituto = parametro(request, "instituto");
        String curso = parametro(request, "curso");
        String edicion = parametro(request, "edicion");

        // Permite entrar indicando solamente una edición.
        if (!edicion.isEmpty()) {
            DetalleEdicionCurso detalle =
                    control.consultarEdicion(edicion);

            if (detalle == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La edición no existe.");
                return;
            }

            if (!participa(usuario.getNickname(), edicion)) {
                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Solo podés consultar los aceptados "
                        + "de ediciones en las que participás.");
                return;
            }

            if (curso.isEmpty()) {
                curso = detalle.getCurso();
            }
        }

        if (instituto.isEmpty() && !curso.isEmpty()) {
            DetalleCurso detalle = control.consultarCurso(curso);

            if (detalle != null && detalle.getInstituto() != null) {
                instituto = detalle.getInstituto();
            }
        }

        List<String> institutos = control.listarNombresInstitutos();
        List<Curso> cursos = new ArrayList<>();
        List<EdicionCurso> ediciones = new ArrayList<>();

        boolean institutoValido = false;
        boolean cursoValido = false;

        if (!instituto.isEmpty()) {
            institutoValido = institutos.contains(instituto);

            if (institutoValido) {
                cursos = control.listarCursosPorInstituto(instituto);
            } else {
                ponerError(request,
                        "El instituto seleccionado no existe.");
            }
        }

        if (!curso.isEmpty()) {
            for (Curso disponible : cursos) {
                if (disponible.getNombre().equals(curso)) {
                    cursoValido = true;
                    break;
                }
            }

            if (!cursoValido) {
                ponerError(request,
                        "El curso no pertenece al instituto seleccionado.");

            } else {
                // Incluye ediciones vigentes y finalizadas.
                for (EdicionCurso disponible
                        : control.listarEdicionesPorCurso(curso)) {

                    if (participa(usuario.getNickname(),
                            disponible.getNombre())) {
                        ediciones.add(disponible);
                    }
                }
            }
        }

        request.setAttribute("institutos", institutos);
        request.setAttribute("cursos", cursos);
        request.setAttribute("ediciones", ediciones);

        request.setAttribute("institutoSel",
                institutoValido ? instituto : "");

        request.setAttribute("cursoSel",
                cursoValido ? curso : "");

        if (!edicion.isEmpty() && cursoValido) {
            boolean edicionValida = false;

            for (EdicionCurso disponible : ediciones) {
                if (disponible.getNombre().equals(edicion)) {
                    edicionValida = true;
                    break;
                }
            }

            if (!edicionValida) {
                ponerError(request,
                        "La edición no pertenece al curso seleccionado.");

            } else {
                DetalleEdicionCurso detalle =
                        control.consultarEdicion(edicion);

                List<InscripcionResumen> aceptados =
                        control.listarAceptadosPorEdicion(edicion);

                request.setAttribute("edicionSel", edicion);
                request.setAttribute("detalleEdicion", detalle);
                request.setAttribute("aceptados", aceptados);
            }
        }

        request.getRequestDispatcher("/aceptadosEdicion.jsp")
                .forward(request, response);
    }

    private boolean participa(String nicknameDocente,
                              String nombreEdicion) {

        EdicionCurso edicion = control.buscarEdicion(nombreEdicion);

        if (edicion == null || edicion.getDocentes() == null) {
            return false;
        }

        for (Docente docente : edicion.getDocentes()) {
            if (docente.getNickname().equals(nicknameDocente)) {
                return true;
            }
        }

        return false;
    }

    private static String parametro(HttpServletRequest request,
                                    String nombre) {

        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }

    private static void ponerError(HttpServletRequest request,
                                   String mensaje) {

        if (request.getAttribute("error") == null) {
            request.setAttribute("error", mensaje);
        }
    }
}