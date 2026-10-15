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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seleccionar-estudiantes")
public class SeleccionEstudiantesServlet extends HttpServlet {

    private final IControlador control =
            Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioResumen docente = autorizar(request, response);

        if (docente != null) {
            mostrarFormulario(request, response, docente);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        UsuarioResumen docente = autorizar(request, response);

        if (docente == null) {
            return;
        }

        List<Long> aceptadas = new ArrayList<>();
        List<Long> rechazadas = new ArrayList<>();

        Map<Long, String> decisiones = new LinkedHashMap<>();
        request.setAttribute("decisiones", decisiones);

        String edicion = parametro(request, "edicion");

        try {
            if (edicion.isEmpty()) {
                throw new IllegalArgumentException(
                        "Seleccioná una edición.");
            }

            if (!participa(docente.getNickname(), edicion)) {
                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "No participás como docente en esta edición.");
                return;
            }

            /*
             * Cada select del JSP tendrá un nombre:
             * decision_15, decision_16, etc.
             *
             * El número corresponde al ID de la inscripción.
             */
            Enumeration<String> parametros = request.getParameterNames();

            while (parametros.hasMoreElements()) {
                String nombre = parametros.nextElement();

                if (!nombre.startsWith("decision_")) {
                    continue;
                }

                String[] valores = request.getParameterValues(nombre);

                if (valores == null || valores.length != 1) {
                    throw new IllegalArgumentException(
                            "Se recibió una decisión repetida.");
                }

                String valor = valores[0].trim();

                // Vacío significa conservar la inscripción pendiente.
                if (valor.isEmpty()) {
                    continue;
                }

                Long id;

                try {
                    id = Long.valueOf(
                            nombre.substring("decision_".length()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Se recibió una inscripción inválida.");
                }

                if (id <= 0 || decisiones.containsKey(id)) {
                    throw new IllegalArgumentException(
                            "Se recibieron inscripciones inválidas o repetidas.");
                }

                if ("ACEPTADA".equals(valor)) {
                    aceptadas.add(id);

                } else if ("RECHAZADA".equals(valor)) {
                    rechazadas.add(id);

                } else {
                    throw new IllegalArgumentException(
                            "El estado seleccionado no es válido.");
                }

                decisiones.put(id, valor);
            }

            if (decisiones.isEmpty()) {
                throw new IllegalArgumentException(
                        "Seleccioná al menos una inscripción "
                        + "para aceptar o rechazar.");
            }

            control.seleccionarEstudiantesEdicion(
                    docente.getNickname(),
                    edicion,
                    aceptadas,
                    rechazadas
            );

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            mostrarFormulario(request, response, docente);
            return;

        } catch (RuntimeException e) {
            log("No se pudo guardar la selección de estudiantes", e);

            request.setAttribute("error",
                    "No se pudo guardar la selección. "
                    + "Revisá los datos e intentá nuevamente.");

            mostrarFormulario(request, response, docente);
            return;
        }

        /*
         * Redirigimos después del guardado para que actualizar
         * la página no vuelva a enviar las decisiones.
         */
        response.sendRedirect(
                request.getContextPath()
                + "/seleccionar-estudiantes?instituto="
                + codificar(parametro(request, "instituto"))
                + "&curso=" + codificar(parametro(request, "curso"))
                + "&edicion=" + codificar(edicion)
                + "&orden=" + codificar(orden(request))
                + "&resultado=guardado"
        );
    }

    private void mostrarFormulario(HttpServletRequest request,
                                   HttpServletResponse response,
                                   UsuarioResumen docente)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-store");

        String instituto = parametro(request, "instituto");
        String curso = parametro(request, "curso");
        String edicion = parametro(request, "edicion");

        /*
         * Permite entrar desde el detalle de una edición,
         * indicando solamente su nombre.
         */
        if (!edicion.isEmpty() && curso.isEmpty()) {
            DetalleEdicionCurso detalle =
                    control.consultarEdicion(edicion);

            if (detalle != null) {
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

        if (!instituto.isEmpty()) {
            if (institutos.contains(instituto)) {
                cursos = control.listarCursosPorInstituto(instituto);
            } else {
                ponerError(request, "El instituto seleccionado no existe.");
            }
        }

        boolean cursoValido = false;

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
                /*
                 * Reutilizamos la búsqueda de ediciones vigentes.
                 * Solo mostramos las del docente autenticado.
                 */
                for (EdicionCurso disponible
                        : control.listarEdicionesVigentesPorCurso(curso)) {

                    if (participa(docente.getNickname(),
                            disponible.getNombre())) {
                        ediciones.add(disponible);
                    }
                }
            }
        }

        request.setAttribute("institutos", institutos);
        request.setAttribute("cursos", cursos);
        request.setAttribute("ediciones", ediciones);
        request.setAttribute("institutoSel", instituto);
        request.setAttribute("cursoSel", cursoValido ? curso : "");
        request.setAttribute("ordenSel", orden(request));

        if (!edicion.isEmpty()) {
            boolean edicionValida = false;

            for (EdicionCurso disponible : ediciones) {
                if (disponible.getNombre().equals(edicion)) {
                    edicionValida = true;
                    break;
                }
            }

            if (!edicionValida) {
                ponerError(request,
                        "Seleccioná una edición vigente "
                        + "en la que participes como docente.");

            } else {
                DetalleEdicionCurso detalle =
                        control.consultarEdicion(edicion);

                List<InscripcionResumen> inscripciones =
                        control.listarInscripcionesPorEdicion(
                                edicion,
                                "prioridad".equals(orden(request))
                        );

                long aceptadosActuales = 0;

                for (InscripcionResumen inscripcion : inscripciones) {
                    if ("ACEPTADA".equals(inscripcion.getEstado())) {
                        aceptadosActuales++;
                    }
                }

                request.setAttribute("edicionSel", edicion);
                request.setAttribute("detalleEdicion", detalle);
                request.setAttribute("inscripciones", inscripciones);
                request.setAttribute("aceptadosActuales", aceptadosActuales);
            }
        }

        request.getRequestDispatcher("/seleccionEstudiantes.jsp")
                .forward(request, response);
    }

    private boolean participa(String nicknameDocente,
                              String nombreEdicion) {

        // Primero comprobamos que exista.
        if (control.consultarEdicion(nombreEdicion) == null) {
            return false;
        }

        // Este método existente obtiene la edición con sus docentes.
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

        if (!"Docente".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Solo los docentes pueden seleccionar estudiantes.");
            return null;
        }

        return usuario;
    }

    private static String orden(HttpServletRequest request) {
        return "prioridad".equals(parametro(request, "orden"))
                ? "prioridad" : "fecha";
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