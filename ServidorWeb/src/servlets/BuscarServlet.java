package servlets;

import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.Entidades.Curso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/buscar")
public class BuscarServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("q");
        String orden = request.getParameter("orden"); // "alfa", "fecha"

        List<Curso> cursos = control.listarCursos();
        List<Curso> resultados = new ArrayList<>();

        if (cursos != null) {
            for (Curso c : cursos) {
                if (query == null || query.trim().isEmpty() ||
                    c.getNombre().toLowerCase().contains(query.toLowerCase()) ||
                    (c.getDescripcion() != null && c.getDescripcion().toLowerCase().contains(query.toLowerCase()))) {
                    resultados.add(c);
                }
            }
        }

        if ("fecha".equalsIgnoreCase(orden)) {
            resultados.sort((c1, c2) -> {
                if (c1.getFechaAlta() == null || c2.getFechaAlta() == null) return 0;
                return c2.getFechaAlta().compareTo(c1.getFechaAlta());
            });
        } else {
            resultados.sort((c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));
        }

        request.setAttribute("query", query != null ? query : "");
        request.setAttribute("orden", orden != null ? orden : "alfa");
        request.setAttribute("resultados", resultados);
        request.getRequestDispatcher("/buscar.jsp").forward(request, response);
    }
}
