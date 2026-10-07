package servlets;

import Logica.DTO.DetalleCurso;
import Logica.Entidades.Curso;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Caso de uso: Consulta de Curso (paso 1).
 * El usuario indica un instituto o una categoría y se listan los cursos asociados.
 * Al elegir uno se continúa en /curso-detalle (ConsultaCursoServlet).
 */
@WebServlet("/consulta-curso")
public class ConsultaCursosServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String instituto = trim(request.getParameter("instituto"));
        String categoria = trim(request.getParameter("categoria"));

        request.setAttribute("institutos", control.listarNombresInstitutos());
        request.setAttribute("categorias", control.listarNombresCategorias());
        request.setAttribute("institutoSel", instituto);
        request.setAttribute("categoriaSel", categoria);

        if (!instituto.isEmpty()) {
            List<Curso> cursos = control.listarCursosPorInstituto(instituto);
            request.setAttribute("resultados", cursos);
        } else if (!categoria.isEmpty()) {
            List<Curso> filtrados = new ArrayList<>();
            for (Curso c : control.listarCursos()) {
                DetalleCurso d = control.consultarCurso(c.getNombre());
                if (d != null && d.getCategorias() != null && d.getCategorias().contains(categoria)) {
                    filtrados.add(c);
                }
            }
            request.setAttribute("resultados", filtrados);
        }

        request.getRequestDispatcher("/consultaCurso.jsp").forward(request, response);
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
