package servlets;

import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.Entidades.Instituto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registro")
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

        String tipo = request.getParameter("tipoUsuario"); // "Estudiante" o "Docente"
        String nickname = request.getParameter("nickname");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fechaNacStr = request.getParameter("fechaNacimiento");
        String instituto = request.getParameter("instituto");

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

        try {
            if (esDocente) {
                control.altaUsuarioDocente(nickname.trim(), nombre.trim(), apellido.trim(), email.trim(), password.trim(), fechaNac, instituto.trim(), null);
            } else {
                control.altaUsuarioEstudiante(nickname.trim(), nombre.trim(), apellido.trim(), email.trim(), password.trim(), fechaNac, null);
            }

            request.setAttribute("mensajeExito", "Usuario registrado con éxito. Ya puedes iniciar sesión.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al registrar usuario: " + e.getMessage());
            doGet(request, response);
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
