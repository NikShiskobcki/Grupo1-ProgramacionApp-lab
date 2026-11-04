package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.ResultadoEvaluacion;
import modelo.UsuarioResumen;
import util.ClienteCentral;
import util.ServletRemoto;

/** Listar resultados de evaluaciones: inscripciones del estudiante a ediciones cerradas. */
@WebServlet("/mis-resultados")
public class ResultadosEvaluacionesServlet extends ServletRemoto {

    private ClienteCentral control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UsuarioResumen usuario = session == null ? null
                : (UsuarioResumen) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!"Estudiante".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los estudiantes pueden consultar sus resultados de evaluaciones.");
            return;
        }

        response.setHeader("Cache-Control", "no-store");

        // El nickname sale siempre de la sesión: no se acepta otro por parámetro.
        List<ResultadoEvaluacion> resultados = new ArrayList<>();
        try {
            resultados = control.listarResultadosEvaluacionesPorEstudiante(usuario.getNickname());
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("resultados", resultados);
        request.getRequestDispatcher("/misResultados.jsp").forward(request, response);
    }
}
