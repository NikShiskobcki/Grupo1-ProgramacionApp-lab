package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Curso;
import modelo.DetalleCurso;
import modelo.DetalleEdicionCurso;
import modelo.Docente;
import modelo.EdicionCurso;
import modelo.EvaluacionEstudiante;
import modelo.UsuarioResumen;
import util.ActaEstudio;
import util.ClienteCentral;
import util.ServletRemoto;
import java.time.LocalDate;

//Ver resultados de evaluaciones en ediciones de cursos
 @WebServlet("/acta-edicion")
public class ActaEdicionServlet extends ServletRemoto {

        private ClienteCentral control;

        @Override
        public void init() throws ServletException {
            super.init();
            control = new ClienteCentral(getServletContext());
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            UsuarioResumen docente = autorizar(request, response);
            if (docente == null) {
                return;
            }

            response.setHeader("Cache-Control", "no-store");

            String instituto = parametro(request, "instituto");
            String curso = parametro(request, "curso");
            String edicion = parametro(request, "edicion");

            // Permite entrar indicando solamente la edición (por ejemplo, desde un link)
            if (!edicion.isEmpty()) {
                DetalleEdicionCurso detalle = control.consultarEdicion(edicion);
                if (detalle == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,
                            "La edición no existe.");
                    return;
                }
                if (!participa(docente.getNickname(), edicion)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN,
                            "Solo podés ver el acta de ediciones en las que participás.");
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
            Set<String> edicionesCerradas = new HashSet<>();
            boolean institutoValido = false;
            boolean cursoValido = false;

            if (!instituto.isEmpty()) {
                institutoValido = institutos.contains(instituto);
                if (institutoValido) {
                    cursos = control.listarCursosPorInstituto(instituto);
                } else {
                    ponerError(request, "El instituto seleccionado no existe.");
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
                    ponerError(request, "El curso no pertenece al instituto seleccionado.");
                } else {
                    LocalDate hoy = LocalDate.now();

                    for (EdicionCurso disponible : control.listarEdicionesPorCurso(curso)) {
                        LocalDate inicio = disponible.getFechaInicio();
                        LocalDate fin = disponible.getFechaFin();

                        if (inicio == null || fin == null
                                || hoy.isBefore(inicio) || hoy.isAfter(fin)) {
                            continue;
                        }

                        String nombreEdicion = disponible.getNombre();

                        if (participa(docente.getNickname(), nombreEdicion)) {
                            ediciones.add(disponible);

                            if (control.edicionCerrada(nombreEdicion)) {
                                edicionesCerradas.add(nombreEdicion);
                            }
                        }
                    }
                }
            }

            request.setAttribute("institutos", institutos);
            request.setAttribute("cursos", cursos);
            request.setAttribute("ediciones", ediciones);
            request.setAttribute("edicionesCerradas", edicionesCerradas);
            request.setAttribute("institutoSel", institutoValido ? instituto : "");
            request.setAttribute("cursoSel", cursoValido ? curso : "");

            if (!edicion.isEmpty() && cursoValido) {
                boolean edicionValida = false;
                for (EdicionCurso disponible : ediciones) {
                    if (disponible.getNombre().equals(edicion)) {
                        edicionValida = true;
                        break;
                    }
                }

                if (!edicionValida) {
                    ponerError(request, "La edición no pertenece al curso seleccionado.");
                } else {
                    boolean cerrada = edicionesCerradas.contains(edicion);
                    request.setAttribute("edicionSel", edicion);
                    request.setAttribute("detalleEdicion", control.consultarEdicion(edicion));
                    request.setAttribute("cerrada", cerrada);

                    // El acta solo se carga si la edición está cerrada.
                    if (cerrada) {
                        List<EvaluacionEstudiante> evaluaciones
                                = control.listarEvaluacionesPorEdicion(edicion);
                        request.setAttribute("evaluaciones", evaluaciones);

                        int aprobados = 0;
                        int conNota = 0;
                        int suma = 0;
                        for (EvaluacionEstudiante ev : evaluaciones) {
                            if (ev.getNota() != null) {
                                conNota++;
                                suma += ev.getNota();
                                if (ev.getNota() >= ActaEstudio.NOTA_APROBACION) {
                                    aprobados++;
                                }
                            }
                        }
                        request.setAttribute("aprobados", aprobados);
                        request.setAttribute("promedio",
                                conNota == 0 ? null : (double) suma / conNota);
                    }
                }
            }

            request.getRequestDispatcher("/actaEdicion.jsp").forward(request, response);
        }

        // ------------------------------------------------------------------
        private UsuarioResumen autorizar(HttpServletRequest request,
                HttpServletResponse response) throws IOException {

            HttpSession session = request.getSession(false);
            UsuarioResumen usuario = session == null ? null
                    : (UsuarioResumen) session.getAttribute("usuarioLogueado");

            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return null;
            }
            if (!"Docente".equalsIgnoreCase(usuario.getTipo())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Solo los docentes pueden ver el acta de una edición.");
                return null;
            }
            return usuario;
        }

        private boolean participa(String nicknameDocente, String nombreEdicion) {
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

        private static String parametro(HttpServletRequest request, String nombre) {
            String valor = request.getParameter(nombre);
            return valor == null ? "" : valor.trim();
        }

        private static void ponerError(HttpServletRequest request, String mensaje) {
            if (request.getAttribute("error") == null) {
                request.setAttribute("error", mensaje);
            }
        }
    }
