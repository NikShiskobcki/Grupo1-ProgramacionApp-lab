package servlets;

import Logica.DTO.UsuarioEdicion;
import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * US-07: Modificar datos de usuario. Solo el propio Estudiante/Docente (con sesión iniciada)
 * puede editar sus datos básicos; nickname y correo electrónico no son modificables.
 */
@WebServlet("/modificar-perfil")
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

        String nombre = trim(request.getParameter("nombre"));
        String apellido = trim(request.getParameter("apellido"));
        String fechaStr = trim(request.getParameter("fechaNacimiento"));
        String imagen = trim(request.getParameter("rutaImagen"));
        String instituto = trim(request.getParameter("instituto"));
        boolean esDocente = "Docente".equalsIgnoreCase(actual.getTipoUsuario());

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

        if (error != null) {
            cargarFormulario(request, nickname);
            // Conserva lo que el usuario escribió
            request.setAttribute("v_nombre", nombre);
            request.setAttribute("v_apellido", apellido);
            request.setAttribute("v_fecha", fechaStr);
            request.setAttribute("v_imagen", imagen);
            request.setAttribute("v_instituto", instituto);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/modificarPerfil.jsp").forward(request, response);
            return;
        }

        try {
            control.modificarUsuario(nickname, nombre, apellido, fecha,
                    esDocente ? instituto : null,
                    imagen.isEmpty() ? null : imagen);

            // Refresca los datos de la sesión (el encabezado muestra nombre e imagen)
            UsuarioResumen nuevo = new UsuarioResumen(nickname, nombre + " " + apellido,
                    usr.getTipo(), imagen.isEmpty() ? null : imagen);
            request.getSession().setAttribute("usuarioLogueado", nuevo);

            response.sendRedirect(request.getContextPath() + "/perfil?msg=modificado");
        } catch (Exception e) {
            cargarFormulario(request, nickname);
            request.setAttribute("error", "No se pudo actualizar el perfil: " + e.getMessage());
            request.getRequestDispatcher("/modificarPerfil.jsp").forward(request, response);
        }
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
