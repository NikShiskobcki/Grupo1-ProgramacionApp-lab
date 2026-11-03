package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.ResultadoInscripcion;
import modelo.UsuarioResumen;

import util.ClienteCentral;
import util.ServletRemoto;

@WebServlet("/desistir-inscripcion")
public class DesistirInscripcionServlet extends ServletRemoto {

    private static final String TOKEN_SESION = "tokenDesistimiento";

    private ClienteCentral control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioResumen usuario = autorizar(request, response);

        if (usuario == null) {
            return;
        }

        response.setHeader("Cache-Control", "no-store");

        List<ResultadoInscripcion> pendientes = new ArrayList<>();

        for (ResultadoInscripcion inscripcion
                : control.listarResultadosPorEstudiante(
                        usuario.getNickname())) {

            if ("INSCRIPTO".equals(inscripcion.getEstado())) {
                pendientes.add(inscripcion);
            }
        }

        HttpSession sesion = request.getSession(false);

        if (sesion.getAttribute(TOKEN_SESION) == null) {
            sesion.setAttribute(
                    TOKEN_SESION,
                    UUID.randomUUID().toString());
        }

        request.setAttribute(
                "tokenDesistimiento",
                sesion.getAttribute(TOKEN_SESION));

        request.setAttribute("inscripciones", pendientes);

        if ("1".equals(request.getParameter("desistida"))) {
            request.setAttribute(
                    "mensaje",
                    "El desistimiento fue realizado correctamente.");
        }

        request.getRequestDispatcher("/desistirInscripcion.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        UsuarioResumen usuario = autorizar(request, response);

        if (usuario == null) {
            return;
        }

        HttpSession sesion = request.getSession(false);

        String tokenEsperado =
                (String) sesion.getAttribute(TOKEN_SESION);

        String tokenRecibido = request.getParameter("token");

        if (tokenEsperado == null
                || !tokenEsperado.equals(tokenRecibido)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "El formulario no es válido. Recargá la página.");
            return;
        }

        String edicion = request.getParameter("edicion");

        if (edicion == null || edicion.isBlank()) {
            request.setAttribute(
                    "error",
                    "Seleccioná una inscripción.");

            doGet(request, response);
            return;
        }

        try {
            control.desistirInscripcionEdicion(
                    usuario.getNickname(),
                    edicion.trim());

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());

            doGet(request, response);
            return;
        }

        response.sendRedirect(
                request.getContextPath()
                + "/desistir-inscripcion?desistida=1");
    }

    private UsuarioResumen autorizar(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession sesion = request.getSession(false);

        UsuarioResumen usuario = sesion == null
                ? null
                : (UsuarioResumen) sesion.getAttribute(
                        "usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return null;
        }

        if (!"Estudiante".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Solo los estudiantes pueden desistir "
                    + "de sus inscripciones.");
            return null;
        }

        return usuario;
    }
}