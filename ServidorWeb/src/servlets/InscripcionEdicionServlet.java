package servlets;

import Logica.DTO.DetalleCurso;
import Logica.DTO.DetalleEdicionCurso;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Curso;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.EstadoInscripcion;
import Logica.Entidades.InscripcionEdicion;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/inscribir-edicion")
public class InscripcionEdicionServlet extends HttpServlet {

    private final IControlador control =
            Fabrica.getInstance().getIControlador();

    private static final ZoneId ZONA =
            ZoneId.of("America/Montevideo");

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioResumen estudiante = autorizar(request, response);

        if (estudiante != null) {
            mostrarFormulario(request, response, estudiante);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        UsuarioResumen estudiante = autorizar(request, response);

        if (estudiante == null) {
            return;
        }

        /*
         * Compatibilidad con el botón antiguo del detalle del curso.
         * Abrimos la confirmación; no registramos una inscripción.
         */
        if (parametro(request, "accion").isEmpty()
                && !parametro(request, "nombreEdicion").isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/inscribir-edicion?curso="
                    + codificar(parametro(request, "nombreCurso"))
                    + "&edicion="
                    + codificar(parametro(request, "nombreEdicion"))
            );
            return;
        }

        String curso = parametro(request, "curso");
        String edicion = parametro(request, "edicion");
        String accion = parametro(request, "accion");
        String resultado;

        try {
            if (curso.isEmpty() || edicion.isEmpty()) {
                throw new IllegalArgumentException(
                        "Seleccioná un curso y una edición vigente.");
            }

            boolean vigente = false;

            for (EdicionCurso disponible
                    : control.listarEdicionesVigentesPorCurso(curso)) {

                if (disponible.getNombre().equals(edicion)) {
                    vigente = true;
                    break;
                }
            }

            if (!vigente) {
                throw new IllegalArgumentException(
                        "La edición no está vigente o no pertenece al curso.");
            }

            InscripcionEdicion existente =
                    control.buscarInscripcionEdicion(
                            estudiante.getNickname(), edicion);

            LocalDate hoy = LocalDate.now(ZONA);

            if ("inscribir".equals(accion)) {
                if (existente != null) {
                    throw new IllegalArgumentException(
                            "Ya tenés una inscripción a esta edición. "
                            + "Podés revisar sus datos o cancelar.");
                }

                /*
                 * También comprobamos el historial por si la búsqueda
                 * de esta versión de Central omite las rechazadas.
                 */
                for (Logica.DTO.ResultadoInscripcion anterior
                        : control.listarResultadosPorEstudiante(
                                estudiante.getNickname())) {

                    if (edicion.equals(anterior.getNombreEdicion())) {
                        throw new IllegalArgumentException(
                                "Ya existe una inscripción tuya "
                                + "a esta edición, aunque esté rechazada.");
                    }
                }

                control.inscribirEstudianteEdicion(
                        estudiante.getNickname(), edicion, hoy);

                resultado = "inscripto";

            } else if ("modificar".equals(accion)) {
                if (existente == null) {
                    throw new IllegalArgumentException(
                            "No existe una inscripción para modificar.");
                }

                if (existente.getEstado() != EstadoInscripcion.INSCRIPTO) {
                    throw new IllegalArgumentException(
                            "No podés modificar una inscripción "
                            + "que ya fue aceptada o rechazada.");
                }

                control.modificarInscripcionEdicion(
                        existente.getId(), hoy);

                resultado = "modificado";

            } else {
                throw new IllegalArgumentException(
                        "La acción solicitada no es válida.");
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            mostrarFormulario(request, response, estudiante);
            return;

        } catch (RuntimeException e) {
            log("Error al gestionar una inscripción", e);

            request.setAttribute("error",
                    "No se pudo procesar la inscripción. "
                    + "Revisá los datos e intentá nuevamente.");

            mostrarFormulario(request, response, estudiante);
            return;
        }

        String destino = request.getContextPath()
                + "/inscribir-edicion?curso=" + codificar(curso)
                + "&edicion=" + codificar(edicion)
                + "&resultado=" + resultado;

        String instituto = parametro(request, "instituto");
        String categoria = parametro(request, "categoria");

        if (!instituto.isEmpty()) {
            destino += "&instituto=" + codificar(instituto);
        } else if (!categoria.isEmpty()) {
            destino += "&categoria=" + codificar(categoria);
        }

        response.sendRedirect(destino);
    }

    private void mostrarFormulario(HttpServletRequest request,
                                   HttpServletResponse response,
                                   UsuarioResumen estudiante)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-store");

        String instituto = parametro(request, "instituto");
        String categoria = parametro(request, "categoria");
        String cursoElegido = parametro(request, "curso");
        String edicionElegida = parametro(request, "edicion");

        // Permite entrar desde el detalle de una edición.
        if (cursoElegido.isEmpty() && !edicionElegida.isEmpty()) {
            DetalleEdicionCurso detalle =
                    control.consultarEdicion(edicionElegida);

            if (detalle != null) {
                cursoElegido = detalle.getCurso();
            }
        }

        // Permite entrar desde un curso sin repetir la selección del instituto.
        if (instituto.isEmpty() && categoria.isEmpty()
                && !cursoElegido.isEmpty()) {

            DetalleCurso detalle = control.consultarCurso(cursoElegido);

            if (detalle != null && detalle.getInstituto() != null) {
                instituto = detalle.getInstituto();
            }
        }

        List<String> institutos = control.listarNombresInstitutos();
        List<String> categorias = control.listarNombresCategorias();
        List<Curso> cursos = new ArrayList<>();

        if (!instituto.isEmpty() && !categoria.isEmpty()) {
            ponerError(request,
                    "Elegí un instituto o una categoría, no ambos.");

        } else if (!instituto.isEmpty()) {
            if (institutos.contains(instituto)) {
                cursos = control.listarCursosPorInstituto(instituto);
            } else {
                ponerError(request, "El instituto no existe.");
            }

        } else if (!categoria.isEmpty()) {
            if (categorias.contains(categoria)) {
                for (Curso curso : control.listarCursos()) {
                    DetalleCurso detalle =
                            control.consultarCurso(curso.getNombre());

                    if (detalle != null
                            && detalle.getCategorias() != null
                            && detalle.getCategorias().contains(categoria)) {
                        cursos.add(curso);
                    }
                }
            } else {
                ponerError(request, "La categoría no existe.");
            }
        }

        request.setAttribute("institutos", institutos);
        request.setAttribute("categorias", categorias);
        request.setAttribute("institutoSel", instituto);
        request.setAttribute("categoriaSel", categoria);
        request.setAttribute("cursos", cursos);

        if (!cursoElegido.isEmpty()) {
            boolean pertenece = false;

            for (Curso curso : cursos) {
                if (curso.getNombre().equals(cursoElegido)) {
                    pertenece = true;
                    break;
                }
            }

            if (!pertenece) {
                ponerError(request,
                        "Seleccioná un curso del instituto o categoría indicado.");

            } else {
                request.setAttribute("cursoSel", cursoElegido);

                List<EdicionCurso> vigentes =
                        control.listarEdicionesVigentesPorCurso(cursoElegido);

                request.setAttribute("edicionesVigentes", vigentes);

                if (edicionElegida.isEmpty() && vigentes.size() == 1) {
                    edicionElegida = vigentes.get(0).getNombre();
                }

                if (!edicionElegida.isEmpty()) {
                    boolean encontrada = false;

                    for (EdicionCurso edicion : vigentes) {
                        if (edicion.getNombre().equals(edicionElegida)) {
                            encontrada = true;
                            break;
                        }
                    }

                    if (encontrada) {
                        DetalleEdicionCurso detalle =
                                control.consultarEdicion(edicionElegida);

                        request.setAttribute("edicionSel", edicionElegida);
                        request.setAttribute("detalleEdicion", detalle);

                        request.setAttribute("inscripcion",
                                control.buscarInscripcionEdicion(
                                        estudiante.getNickname(),
                                        edicionElegida));

                    } else {
                        ponerError(request,
                                "La edición seleccionada no está vigente.");
                    }
                }
            }
        }

        request.getRequestDispatcher("/inscripcionEdicion.jsp")
                .forward(request, response);
    }

    private UsuarioResumen autorizar(HttpServletRequest request,
                                     HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        UsuarioResumen usuario = session == null ? null
                : (UsuarioResumen) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if (!"Estudiante".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Solo los estudiantes pueden inscribirse.");
            return null;
        }

        return usuario;
    }

    private static void ponerError(HttpServletRequest request,
                                   String mensaje) {
        if (request.getAttribute("error") == null) {
            request.setAttribute("error", mensaje);
        }
    }

    private static String parametro(HttpServletRequest request,
                                    String nombre) {
        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }

    private static String codificar(String valor) {
        try {
            return URLEncoder.encode(valor, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}