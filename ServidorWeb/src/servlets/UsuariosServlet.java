package servlets;

import util.ServletRemoto;

import util.ClienteCentral;

import modelo.UsuarioResumen;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** US-04: lista de todos los usuarios (accesible para visitantes y usuarios registrados). */
@WebServlet("/usuarios")
public class UsuariosServlet extends ServletRemoto {

    @Override
    public void init() throws javax.servlet.ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }


    private ClienteCentral control;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<UsuarioResumen> usuarios = control.listarUsuarios();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
    }
}
