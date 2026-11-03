package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.UsuarioResumen;
import util.ActaEstudio;
import util.ClienteCentral;
import util.ServletRemoto;

/**
 * Caso de uso: Tramitar Acta de Estudio (paso 1, actor: Estudiante).
 * Muestra los programas del estudiante y, para el elegido, el resultado de
 * cada curso. El PDF se descarga desde /acta-pdf.
 */
@WebServlet("/acta")
public class ActaEstudioServlet extends ServletRemoto {

    private ClienteCentral control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        UsuarioResumen usuario = session == null ? null
                : (UsuarioResumen) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!"Estudiante".equalsIgnoreCase(usuario.getTipo())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los estudiantes pueden tramitar el acta de estudios.");
            return;
        }

        response.setHeader("Cache-Control", "no-store");

        // El nickname sale siempre de la sesión, nunca de un parámetro.
        List<String> programas =
                ActaEstudio.programasDelEstudiante(control, usuario.getNickname());
        request.setAttribute("programas", programas);

        String elegido = request.getParameter("programa");
        if (elegido != null && !elegido.trim().isEmpty()) {
            elegido = elegido.trim();
            // Solo se puede pedir el acta de un programa en el que está inscripto.
            if (programas.contains(elegido)) {
                request.setAttribute("programaSel", elegido);
                request.setAttribute("acta",
                        ActaEstudio.construir(control, usuario.getNickname(), elegido));
            } else {
                request.setAttribute("error",
                        "No estás inscripto en el programa seleccionado.");
            }
        }

        if ("incompleto".equals(request.getParameter("error"))) {
            request.setAttribute("error",
                    "Todavía no podés tramitar el acta: faltan cursos por aprobar.");
        }

        request.getRequestDispatcher("/acta.jsp").forward(request, response);
    }
}
