package servlets;

import Logica.DTO.UsuarioEdicion;
import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import util.Imagenes;

/**
 * US-07: Modificar datos de usuario. Solo el propio Estudiante/Docente (con sesión iniciada)
 * puede editar sus datos básicos; nickname y correo electrónico no son modificables.
 */
@WebServlet("/modificar-perfil")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 6L * 1024 * 1024,
        maxRequestSize = 7L * 1024 * 1024
)
public class ModificarUsuarioServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    private UsuarioResumen logueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (UsuarioResumen) session.getAttribute("usuarioLogueado") : null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioResumen usr = logueado(request);
        if (usr == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        cargarFormulario(request, usr.getNickname());
        request.getRequestDispatcher("/modificarPerfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UsuarioResumen usr = logueado(request);
        if (usr == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // El nickname siempre sale de la sesión (nunca del formulario)
        String nickname = usr.getNickname();
        UsuarioEdicion actual = control.buscarUsuarioParaEditar(nickname);
        if (actual == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            request.getParts();
        } catch (IllegalStateException e) {
            errorMultipart(request, response, nickname,
                    "El archivo o formulario supera el tamaño permitido. Seleccioná una imagen de hasta 5 MB.");
            return;
        }

        String nombre = trim(request.getParameter("nombre"));
        String apellido = trim(request.getParameter("apellido"));
        String fechaStr = trim(request.getParameter("fechaNacimiento"));
        String imagenUrl = trim(request.getParameter("imagen"));
        String instituto = trim(request.getParameter("instituto"));
        boolean quitarImagen = request.getParameter("quitarImagen") != null;
        boolean esDocente = "Docente".equalsIgnoreCase(actual.getTipoUsuario());
        String imagenAnterior = actual.getRutaImagen();

        Part archivo = request.getPart("imagenArchivo");
        boolean hayArchivo = archivo != null
                && archivo.getSubmittedFileName() != null
                && !archivo.getSubmittedFileName().trim().isEmpty();

        String error = null;
        LocalDate fecha = null;

        if (nombre.isEmpty() || apellido.isEmpty()) {
            error = "El nombre y el apellido son obligatorios.";
        } else if (fechaStr.isEmpty()) {
            error = "Debe indicar la fecha de nacimiento.";
        } else {
            try {
                fecha = LocalDate.parse(fechaStr);
                if (fecha.isAfter(LocalDate.now())) {
                    error = "La fecha de nacimiento no puede ser futura.";
                }
            } catch (DateTimeParseException e) {
                error = "La fecha de nacimiento no es válida.";
            }
        }
        if (error == null && esDocente) {
            if (instituto.isEmpty()) {
                instituto = actual.getInstituto();
            } else if (!control.existeInstituto(instituto)) {
                error = "El instituto seleccionado no existe.";
            }
        }
        if (error == null && hayArchivo && !imagenUrl.isEmpty()) {
            error = "Elegí una imagen desde tu PC o ingresá una URL, pero no ambas opciones.";
        }
        if (error == null && !imagenUrl.isEmpty()) {
            try {
                URI uri = URI.create(imagenUrl);
                if (uri.getHost() == null || uri.getUserInfo() != null
                        || !("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))) {
                    error = "La imagen debe tener una URL http:// o https:// válida.";
                }
            } catch (IllegalArgumentException e) {
                error = "La URL de la imagen no es válida.";
            }
        }

        if (error != null) {
            cargarFormulario(request, nickname);
            // Conserva lo que el usuario escribió
            request.setAttribute("v_nombre", nombre);
            request.setAttribute("v_apellido", apellido);
            request.setAttribute("v_fecha", fechaStr);
            request.setAttribute("v_imagen", imagenUrl);
            request.setAttribute("v_instituto", instituto);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/modificarPerfil.jsp").forward(request, response);
            return;
        }

        // Sin archivo nuevo, sin URL y sin pedir quitarla: se conserva la imagen que ya tenía.
        String rutaImagen = imagenUrl.isEmpty()
                ? (quitarImagen ? null : imagenAnterior)
                : imagenUrl;
        String imagenGuardada = null;

        if (hayArchivo) {
            try {
                imagenGuardada = Imagenes.guardar(archivo);
                rutaImagen = imagenGuardada;
            } catch (IllegalArgumentException e) {
                cargarFormulario(request, nickname);
                request.setAttribute("v_nombre", nombre);
                request.setAttribute("v_apellido", apellido);
                request.setAttribute("v_fecha", fechaStr);
                request.setAttribute("v_imagen", imagenUrl);
                request.setAttribute("v_instituto", instituto);
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/modificarPerfil.jsp").forward(request, response);
                return;
            } catch (IOException e) {
                log("No se pudo guardar el archivo de imagen", e);
                errorMultipart(request, response, nickname, "No se pudo guardar la imagen. Intentá nuevamente.");
                return;
            }
        }

        try {
            control.modificarUsuario(nickname, nombre, apellido, fecha,
                    esDocente ? instituto : null,
                    rutaImagen);

            // Si se reemplazó o quitó una imagen que vivía en nuestro almacenamiento, la borramos.
            if (imagenAnterior != null && !imagenAnterior.equals(rutaImagen)) {
                try {
                    Imagenes.eliminar(imagenAnterior);
                } catch (IOException ex) {
                    log("No se pudo eliminar la imagen anterior del perfil", ex);
                }
            }

            // Refresca los datos de la sesión (el encabezado muestra nombre e imagen)
            UsuarioResumen nuevo = new UsuarioResumen(nickname, nombre + " " + apellido,
                    usr.getTipo(), rutaImagen);
            request.getSession().setAttribute("usuarioLogueado", nuevo);

            response.sendRedirect(request.getContextPath() + "/perfil?msg=modificado");
        } catch (Exception e) {
            if (imagenGuardada != null) {
                try {
                    Imagenes.eliminar(imagenGuardada);
                } catch (IOException ex) {
                    log("No se pudo eliminar una imagen de una modificación fallida", ex);
                }
            }
            cargarFormulario(request, nickname);
            request.setAttribute("error", "No se pudo actualizar el perfil: " + e.getMessage());
            request.getRequestDispatcher("/modificarPerfil.jsp").forward(request, response);
        }
    }

    private void errorMultipart(HttpServletRequest request, HttpServletResponse response,
            String nickname, String mensaje) throws ServletException, IOException {
        cargarFormulario(request, nickname);
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("/modificarPerfil.jsp").forward(request, response);
    }

    private void cargarFormulario(HttpServletRequest request, String nickname) {
        UsuarioEdicion datos = control.buscarUsuarioParaEditar(nickname);
        request.setAttribute("usuarioEdicion", datos);
        if (datos != null && "Docente".equalsIgnoreCase(datos.getTipoUsuario())) {
            List<String> institutos = control.listarNombresInstitutos();
            request.setAttribute("institutos", institutos);
        }
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
