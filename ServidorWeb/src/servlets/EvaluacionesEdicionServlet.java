package servlets;

import util.ClienteCentral;
import util.ServletRemoto;

import modelo.Curso;
import modelo.DetalleCurso;
import modelo.DetalleEdicionCurso;
import modelo.Docente;
import modelo.EdicionCurso;
import modelo.EvaluacionEstudiante;
import modelo.UsuarioResumen;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/evaluaciones-edicion")
public class EvaluacionesEdicionServlet extends ServletRemoto {

    private ClienteCentral control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }


    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioResumen docente = autorizar(request, response);
        if (docente == null) {
            return;
        }

        String resultado = parametro(request, "resultado");
        if ("guardado".equals(resultado)) {
            request.setAttribute("exito", "Notas guardadas correctamente.");
        } else if ("cerrado".equals(resultado)) {
            request.setAttribute("exito",
                    "La edición quedó cerrada. Las notas ya no se pueden modificar.");
        }

        mostrar(request, response, docente);
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

        String edicion = parametro(request, "edicion");
        String accion = parametro(request, "accion");

        // Lo que escribió el docente, para no perderlo si hay error
        Map<Long, String> ingresadas = new LinkedHashMap<>();
        request.setAttribute("notasIngresadas", ingresadas);

        try {
            if (edicion.isEmpty()) {
                throw new IllegalArgumentException("Seleccioná una edición.");
            }
            if (!participa(docente.getNickname(), edicion)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "No participás como docente en esta edición.");
                return;
            }


            Map<Long, Integer> notas = new LinkedHashMap<>();
            Enumeration<String> nombres = request.getParameterNames();

            while (nombres.hasMoreElements()) {
                String nombre = nombres.nextElement();
                if (!nombre.startsWith("nota_")) {
                    continue;
                }

                Long id;
                try {
                    id = Long.valueOf(nombre.substring("nota_".length()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Se recibió una inscripción inválida.");
                }

                String texto = parametro(request, nombre);
                ingresadas.put(id, texto);

                if (texto.isEmpty()) {
                    notas.put(id, null);
                    continue;
                }

                int nota;
                try {
                    nota = Integer.parseInt(texto);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Las notas deben ser números enteros del 1 al 12.");
                }
                if (nota < 1 || nota > 12) {
                    throw new IllegalArgumentException(
                            "Las notas deben estar entre 1 y 12.");
                }
                notas.put(id, nota);
            }

            if (!notas.isEmpty()) {
                control.guardarNotas(docente.getNickname(), edicion, notas);
            }

            if ("cerrar".equals(accion)) {
                control.cerrarEdicion(docente.getNickname(), edicion);
            } else if (notas.isEmpty()) {
                throw new IllegalArgumentException("No hay notas para guardar.");
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            mostrar(request, response, docente);
            return;
        }

        
        response.sendRedirect(request.getContextPath()
                + "/evaluaciones-edicion?instituto="
                + codificar(parametro(request, "instituto"))
                + "&curso=" + codificar(parametro(request, "curso"))
                + "&edicion=" + codificar(edicion)
                + "&resultado="
                + ("cerrar".equals(accion) ? "cerrado" : "guardado"));
    }

    private void mostrar(HttpServletRequest request,
            HttpServletResponse response,
            UsuarioResumen docente)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-store");

        String instituto = parametro(request, "instituto");
        String curso = parametro(request, "curso");
        String edicion = parametro(request, "edicion");

        // Permite entrar indicando solamente la edición
        if (!edicion.isEmpty()) {
            DetalleEdicionCurso detalle = control.consultarEdicion(edicion);
            if (detalle == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "La edición no existe.");
                return;
            }
            if (!participa(docente.getNickname(), edicion)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Solo podés evaluar ediciones en las que participás.");
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
                ponerError(request,
                        "El curso no pertenece al instituto seleccionado.");
            } else {
                for (EdicionCurso disponible
                        : control.listarEdicionesPorCurso(curso)) {
                    if (participa(docente.getNickname(), disponible.getNombre())) {
                        ediciones.add(disponible);
                    }
                }
            }
        }

        request.setAttribute("institutos", institutos);
        request.setAttribute("cursos", cursos);
        request.setAttribute("ediciones", ediciones);
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
                ponerError(request,
                        "La edición no pertenece al curso seleccionado.");
            } else {
                List<EvaluacionEstudiante> evaluaciones
                        = control.listarEvaluacionesPorEdicion(edicion);

                request.setAttribute("edicionSel", edicion);
                request.setAttribute("detalleEdicion",
                        control.consultarEdicion(edicion));
                request.setAttribute("evaluaciones", evaluaciones);
                request.setAttribute("cerrada",
                        control.edicionCerrada(edicion));
            }
        }

        request.getRequestDispatcher("/evaluacionesEdicion.jsp")
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
        if (!"Docente".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los docentes pueden ingresar evaluaciones.");
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

    private static String codificar(String valor) {
        return URLEncoder.encode(valor, StandardCharsets.UTF_8);
    }
}
