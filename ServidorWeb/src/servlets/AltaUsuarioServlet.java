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

        if (nickname == null || nickname.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || !password.equals(confirmPassword)) {
            
            request.setAttribute("error", "Datos incompletos o las contraseñas no coinciden.");
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

        LocalDate fechaNac;
        try {
            fechaNac = LocalDate.parse(fechaNacStr);
        } catch (DateTimeParseException | NullPointerException e) {
            fechaNac = LocalDate.now().minusYears(20);
        }

        try {
            if ("Docente".equalsIgnoreCase(tipo)) {
                control.altaUsuarioDocente(nickname.trim(), nombre.trim(), apellido.trim(), email.trim(), password.trim(), fechaNac, instituto, null);
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
}
