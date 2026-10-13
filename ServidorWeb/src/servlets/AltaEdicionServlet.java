package servlets;

import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/alta-edicion")
public class AltaEdicionServlet extends HttpServlet {

    private final IControlador control =
            Fabrica.getInstance().getIControlador();

    private static final ZoneId ZONA =
            ZoneId.of("America/Montevideo");

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        boolean opciones =
                "opciones".equals(request.getParameter("accion"));

        if (!autorizar(request, response, opciones)) {
            return;
        }

        if (!opciones) {
            mostrarFormulario(request, response);
            return;
        }

        String instituto = parametro(request, "instituto");

        if (!control.listarNombresInstitutos().contains(instituto)) {
            responderError(response, 400,
                    "El instituto seleccionado no existe.");
            return;
        }

        List<Curso> cursos =
                control.listarCursosPorInstituto(instituto);

        List<Docente> docentes =
                control.listarDocentesPorInstituto(instituto);

        StringBuilder json = new StringBuilder("{\"cursos\":[");

        for (int i = 0; i < cursos.size(); i++) {
            if (i > 0) {
                json.append(",");
            }

            json.append(textoJson(cursos.get(i).getNombre()));
        }

        json.append("],\"docentes\":[");

        for (int i = 0; i < docentes.size(); i++) {
            if (i > 0) {
                json.append(",");
            }

            Docente docente = docentes.get(i);

            json.append("{\"nickname\":")
                    .append(textoJson(docente.getNickname()))
                    .append(",\"nombre\":")
                    .append(textoJson(
                            docente.getNombre() + " "
                            + docente.getApellido()))
                    .append("}");
        }

        json.append("]}");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        if (!autorizar(request, response, false)) {
            return;
        }

        try {
            String nombre = parametro(request, "nombre");
            String instituto = parametro(request, "instituto");
            String nombreCurso = parametro(request, "curso");
            String imagen = parametro(request, "imagen");
            String textoCupo = parametro(request, "cupo");

            if (nombre.isEmpty() || nombre.length() > 255) {
                throw new IllegalArgumentException(
                        "El nombre es obligatorio y admite hasta 255 caracteres.");
            }

            if (!control.listarNombresInstitutos().contains(instituto)) {
                throw new IllegalArgumentException(
                        "Seleccioná un instituto válido.");
            }

            List<Curso> cursos =
                    control.listarCursosPorInstituto(instituto);

            if (cursos.stream().noneMatch(
                    curso -> curso.getNombre().equals(nombreCurso))) {
                throw new IllegalArgumentException(
                        "Seleccioná un curso del instituto indicado.");
            }

            LocalDate inicio = LocalDate.parse(
                    parametro(request, "fechaInicio"));

            LocalDate fin = LocalDate.parse(
                    parametro(request, "fechaFin"));

            if (inicio.isBefore(LocalDate.now(ZONA))) {
                throw new IllegalArgumentException(
                        "La fecha de inicio no puede ser anterior a hoy.");
            }

            if (fin.isBefore(inicio)) {
                throw new IllegalArgumentException(
                        "La fecha de fin no puede ser anterior a la de inicio.");
            }

            Integer cupo = null;

            if (!textoCupo.isEmpty()) {
                if (!textoCupo.matches("[0-9]+")) {
                    throw new IllegalArgumentException(
                            "El cupo debe ser un número entero mayor que cero.");
                }

                cupo = Integer.valueOf(textoCupo);

                if (cupo <= 0) {
                    throw new IllegalArgumentException(
                            "El cupo debe ser mayor que cero.");
                }
            }

            if (control.existeEdicion(nombre)) {
                throw new IllegalArgumentException(
                        "Ya existe una edición con ese nombre. "
                        + "Modificá el nombre o cancelá el alta.");
            }

            String[] seleccionados =
                    request.getParameterValues("docentes");

            if (seleccionados == null || seleccionados.length == 0) {
                throw new IllegalArgumentException(
                        "Seleccioná al menos un docente.");
            }

            List<Docente> disponibles =
                    control.listarDocentesPorInstituto(instituto);

            List<Docente> docentes = new ArrayList<>();

            for (String nickname : seleccionados) {
                Docente docente = disponibles.stream()
                        .filter(d -> d.getNickname().equals(nickname))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Los docentes seleccionados deben pertenecer "
                                + "al instituto indicado."));

                if (!docentes.contains(docente)) {
                    docentes.add(docente);
                }
            }

            if (!imagen.isEmpty()) {
                URI url = URI.create(imagen);

                if (url.getHost() == null
                        || url.getUserInfo() != null
                        || !("http".equalsIgnoreCase(url.getScheme())
                        || "https".equalsIgnoreCase(url.getScheme()))) {
                    throw new IllegalArgumentException(
                            "La imagen debe tener una URL http:// o https:// válida.");
                }
            }

            control.altaEdicionCurso(
                    nombre,
                    inicio,
                    fin,
                    cupo,
                    nombreCurso,
                    docentes,
                    imagen.isEmpty() ? null : imagen
            );

            response.sendRedirect(
                    request.getContextPath() + "/edicion-detalle?nombre="
                    + URLEncoder.encode(nombre, "UTF-8")
            );

        } catch (DateTimeParseException e) {
            mostrarErrorFormulario(request, response,
                    "Ingresá fechas de inicio y fin válidas.");

        } catch (NumberFormatException e) {
            mostrarErrorFormulario(request, response,
                    "El cupo debe ser un entero entre 1 y 2147483647.");

        } catch (IllegalArgumentException e) {
            mostrarErrorFormulario(request, response, e.getMessage());

        } catch (RuntimeException e) {
            log("No se pudo registrar la edición", e);

            mostrarErrorFormulario(request, response,
                    "No se pudo registrar la edición. "
                    + "Verificá los datos e intentá nuevamente.");
        }
    }

    private boolean autorizar(HttpServletRequest request,
                              HttpServletResponse response,
                              boolean json)
            throws IOException {

        HttpSession session = request.getSession(false);

        UsuarioResumen usuario = session == null ? null
                : (UsuarioResumen) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            if (json) {
                responderError(response, 401, "Debés iniciar sesión.");
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return false;
        }

        if (!"Docente".equalsIgnoreCase(usuario.getTipo())) {
            if (json) {
                responderError(response, 403,
                        "Solo los docentes pueden dar de alta ediciones.");
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Solo los docentes pueden dar de alta ediciones.");
            }
            return false;
        }

        return true;
    }

    private void mostrarFormulario(HttpServletRequest request,
                                   HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-store");
        request.setAttribute("institutos", control.listarNombresInstitutos());
        request.setAttribute("hoy", LocalDate.now(ZONA).toString());

        request.getRequestDispatcher("/altaEdicion.jsp")
                .forward(request, response);
    }

    private void mostrarErrorFormulario(HttpServletRequest request,
                                       HttpServletResponse response,
                                       String mensaje)
            throws ServletException, IOException {

        request.setAttribute("error", mensaje);
        mostrarFormulario(request, response);
    }

    private static String parametro(HttpServletRequest request,
                                    String nombre) {

        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }

    private void responderError(HttpServletResponse response,
                                int estado,
                                String mensaje)
            throws IOException {

        response.setStatus(estado);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");

        response.getWriter().write(
                "{\"error\":" + textoJson(mensaje) + "}"
        );
    }

    private static String textoJson(String texto) {
        if (texto == null) {
            return "null";
        }

        StringBuilder resultado = new StringBuilder("\"");

        for (char caracter : texto.toCharArray()) {
            switch (caracter) {
                case '"':
                    resultado.append("\\\"");
                    break;
                case '\\':
                    resultado.append("\\\\");
                    break;
                case '\n':
                    resultado.append("\\n");
                    break;
                case '\r':
                    resultado.append("\\r");
                    break;
                case '\t':
                    resultado.append("\\t");
                    break;
                default:
                    if (caracter < 32) {
                        resultado.append(
                                String.format("\\u%04x", (int) caracter));
                    } else {
                        resultado.append(caracter);
                    }
            }
        }

        return resultado.append("\"").toString();
    }
}