package servlets;

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
import javax.servlet.http.Part;
import util.Imagenes;

@WebServlet("/registro")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 6L * 1024 * 1024,
        maxRequestSize = 7L * 1024 * 1024
)
public class AltaUsuarioServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> institutos = control.listarNombresInstitutos();
        request.setAttribute("institutos", institutos);
        request.getRequestDispatcher("/registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            request.getParts();
        } catch (IllegalStateException e) {
            request.setAttribute("error",
                    "El archivo o formulario supera el tamaño permitido. Seleccioná una imagen de hasta 5 MB.");
            doGet(request, response);
            return;
        } catch (ServletException e) {
            request.setAttribute("error", "No se pudo recibir el archivo. Volvé a enviar el formulario.");
            doGet(request, response);
            return;
        }

        String tipo = request.getParameter("tipoUsuario"); // "Estudiante" o "Docente"
        String nickname = request.getParameter("nickname");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fechaNacStr = request.getParameter("fechaNacimiento");
        String instituto = request.getParameter("instituto");
        String imagenUrl = request.getParameter("imagen");
        imagenUrl = imagenUrl == null ? "" : imagenUrl.trim();

        boolean esDocente = "Docente".equalsIgnoreCase(tipo);

        if (isBlank(nickname) || isBlank(nombre) || isBlank(apellido) || isBlank(email)
                || isBlank(password) || isBlank(confirmPassword) || isBlank(fechaNacStr)) {
            request.setAttribute("error", "Debe completar todos los campos obligatorios.");
            doGet(request, response);
            return;
        }

        if (!password.trim().equals(confirmPassword.trim())) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            doGet(request, response);
            return;
        }

        if (esDocente && isBlank(instituto)) {
            request.setAttribute("error", "Debe seleccionar el instituto al que pertenece.");
            doGet(request, response);
            return;
        }

        LocalDate fechaNac;
        try {
            fechaNac = LocalDate.parse(fechaNacStr.trim());
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "La fecha de nacimiento ingresada no es válida.");
            doGet(request, response);
            return;
        }

        if (fechaNac.isAfter(LocalDate.now())) {
            request.setAttribute("error", "La fecha de nacimiento no puede ser posterior a la fecha actual.");
            doGet(request, response);
            return;
        }

        if (control.existeNickname(nickname.trim())) {
            request.setAttribute("error", "El nickname ya se encuentra en uso.");
            doGet(request, response);
            return;
        }

        if (control.existeEmail(email.trim())) {
            request.setAttribute("error", "El correo electrónico ya se encuentra en uso.");
            doGet(request, response);
            return;
        }
        Part archivo = request.getPart("imagenArchivo");
        boolean hayArchivo = archivo != null
                && archivo.getSubmittedFileName() != null
                && !archivo.getSubmittedFileName().trim().isEmpty();

        if (hayArchivo && !imagenUrl.isEmpty()) {
            request.setAttribute("error", "Elegí una imagen desde tu PC o ingresá una URL, pero no ambas opciones.");
            doGet(request, response);
            return;
        }

        if (!imagenUrl.isEmpty()) {
            try {
                URI uri = URI.create(imagenUrl);
                if (uri.getHost() == null || uri.getUserInfo() != null
                        || !("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))) {
                    request.setAttribute("error", "La imagen debe tener una URL http:// o https:// válida.");
                    doGet(request, response);
                    return;
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "La URL de la imagen no es válida.");
                doGet(request, response);
                return;
            }
        }

        String rutaImagen = imagenUrl.isEmpty() ? null : imagenUrl;
        String imagenGuardada = null;

        if (hayArchivo) {
            try {
                imagenGuardada = Imagenes.guardar(archivo);
                rutaImagen = imagenGuardada;
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", e.getMessage());
                doGet(request, response);
                return;
            } catch (IOException e) {
                log("No se pudo guardar el archivo de imagen", e);
                request.setAttribute("error", "No se pudo guardar la imagen. Intentá nuevamente.");
                doGet(request, response);
                return;
            }
        }

        try {
            if (esDocente) {
                control.altaUsuarioDocente(nickname.trim(), nombre.trim(), apellido.trim(), email.trim(), password.trim(), fechaNac, instituto.trim(), rutaImagen);
            } else {
                control.altaUsuarioEstudiante(nickname.trim(), nombre.trim(), apellido.trim(), email.trim(), password.trim(), fechaNac, rutaImagen);
            }

            request.setAttribute("mensajeExito", "Usuario registrado con éxito. Ya puedes iniciar sesión.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (Exception e) {
            if (imagenGuardada != null) {
                try {
                    Imagenes.eliminar(imagenGuardada);
                } catch (IOException ex) {
                    log("No se pudo eliminar una imagen de un alta fallida", ex);
                }
            }
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
