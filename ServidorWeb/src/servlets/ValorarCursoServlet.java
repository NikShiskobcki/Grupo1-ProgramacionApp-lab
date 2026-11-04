package servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.UsuarioResumen;
import util.ClienteCentral;
import util.ServletRemoto;

/** Valorar un curso en el que el estudiante participa (puntaje de 1 a 5, una sola vez). */
@WebServlet("/valorar-curso")
public class ValorarCursoServlet extends ServletRemoto {

    private ClienteCentral control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioResumen estudiante = autorizar(request, response);
        if (estudiante != null) {
            mostrar(request, response, estudiante, parametro(request, "curso"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        UsuarioResumen estudiante = autorizar(request, response);
        if (estudiante == null) {
            return;
        }

        String curso = parametro(request, "curso");

        try {
            if (curso.isEmpty()) {
                throw new IllegalArgumentException("Seleccioná un curso.");
            }

            int puntaje;
            try {
                puntaje = Integer.parseInt(parametro(request, "puntaje"));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Elegí un puntaje de 1 a 5.");
            }
            if (puntaje < 1 || puntaje > 5) {
                throw new IllegalArgumentException("El puntaje debe estar entre 1 y 5.");
            }

            control.valorarCurso(estudiante.getNickname(), curso, puntaje);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            mostrar(request, response, estudiante, curso);
            return;
        }

        // Post/Redirect/Get: recargar la página no vuelve a enviar la valoración.
        response.sendRedirect(request.getContextPath() + "/valorar-curso?curso="
                + URLEncoder.encode(curso, "UTF-8") + "&resultado=valorado");
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response,
            UsuarioResumen estudiante, String cursoElegido)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-store");

        List<String> cursos = new ArrayList<>();
        try {
            cursos = control.listarCursosConParticipacion(estudiante.getNickname());
            request.setAttribute("cursos", cursos);

            if (!cursoElegido.isEmpty()) {
                if (!cursos.contains(cursoElegido)) {
                    ponerError(request, "Seleccioná un curso en el que participás.");
                } else {
                    request.setAttribute("cursoSel", cursoElegido);
                    request.setAttribute("valoracion", control.consultarValoracionCurso(
                            estudiante.getNickname(), cursoElegido));
                }
            }
        } catch (IllegalArgumentException e) {
            ponerError(request, e.getMessage());
            request.setAttribute("cursos", cursos);
        }

        request.getRequestDispatcher("/valorarCurso.jsp").forward(request, response);
    }

    private UsuarioResumen autorizar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        UsuarioResumen usuario = session == null ? null
                : (UsuarioResumen) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if (!"Estudiante".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los estudiantes pueden valorar cursos.");
            return null;
        }
        return usuario;
    }

    private static void ponerError(HttpServletRequest request, String mensaje) {
        if (request.getAttribute("error") == null) {
            request.setAttribute("error", mensaje);
        }
    }

    private static String parametro(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }
}
