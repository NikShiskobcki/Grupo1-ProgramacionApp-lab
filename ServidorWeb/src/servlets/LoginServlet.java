package servlets;

import util.ServletRemoto;

import util.ClienteCentral;
import util.Dispositivo;
import util.Recordarme;

import modelo.UsuarioResumen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends ServletRemoto {

    @Override
    public void init() throws javax.servlet.ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }


    private ClienteCentral control;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        // login.jsp muestra la versión móvil (loginMovil.jsp) cuando se entra desde un celular
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");

        if (usuario == null || usuario.trim().isEmpty() || contrasenia == null || contrasenia.trim().isEmpty()) {
            request.setAttribute("error", "Debe ingresar usuario/email y contraseña.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        UsuarioResumen usr = control.iniciarSesion(usuario.trim(), contrasenia.trim());

        if (usr == null) {
            request.setAttribute("error", "Credenciales inválidas. Verifique sus datos.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Desde el celular se ingresa como estudiante: los docentes usan la versión de escritorio.
        if (Dispositivo.esMovil(request) && !"Estudiante".equalsIgnoreCase(usr.getTipo())) {
            request.setAttribute("error", "El acceso desde el celular es solo para estudiantes.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Sesión nueva en cada login (evita reutilizar un identificador de sesión previo)
        HttpSession anterior = request.getSession(false);
        if (anterior != null) {
            anterior.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("usuarioLogueado", usr);

        if (request.getParameter("recordarme") != null) {
            Recordarme.activar(request, response, session, usr.getNickname());
        } else {
            Recordarme.desactivar(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
