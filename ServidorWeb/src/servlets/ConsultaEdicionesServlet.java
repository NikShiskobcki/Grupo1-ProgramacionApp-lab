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

@WebServlet("/consulta-edicion")
public class ConsultaEdicionesServlet extends HttpServlet {

    private final IControlador control =
            Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String instituto = parametro(request, "instituto");
        String categoria = parametro(request, "categoria");
        String cursoElegido = parametro(request, "curso");

        List<String> institutos = control.listarNombresInstitutos();
        List<String> categorias = control.listarNombresCategorias();
        List<Curso> cursos = new ArrayList<>();

        request.setAttribute("institutos", institutos);
        request.setAttribute("categorias", categorias);

        if (!instituto.isEmpty() && !categoria.isEmpty()) {
            request.setAttribute("error",
                    "Elegí un instituto o una categoría, no ambos.");

        } else if (!instituto.isEmpty()) {
            if (institutos.contains(instituto)) {
                cursos = control.listarCursosPorInstituto(instituto);
            } else {
                request.setAttribute("error",
                        "El instituto seleccionado no existe.");
            }

        } else if (!categoria.isEmpty()) {
            if (categorias.contains(categoria)) {
                for (Curso curso : control.listarCursos()) {
                    DetalleCurso detalle =
                            control.consultarCurso(curso.getNombre());

                    if (detalle != null
                            && detalle.getCategorias() != null
                            && detalle.getCategorias().contains(categoria)) {
                        cursos.add(curso);
                    }
                }
            } else {
                request.setAttribute("error",
                        "La categoría seleccionada no existe.");
            }
        }

        request.setAttribute("institutoSel", instituto);
        request.setAttribute("categoriaSel", categoria);
        request.setAttribute("cursos", cursos);

        if (!cursoElegido.isEmpty()) {
            boolean pertenece = false;

            for (Curso curso : cursos) {
                if (curso.getNombre().equals(cursoElegido)) {
                    pertenece = true;
                    break;
                }
            }

            if (pertenece) {
                request.setAttribute("cursoSel", cursoElegido);
                request.setAttribute("ediciones",
                        control.listarEdicionesPorCurso(cursoElegido));
            } else {
                request.setAttribute("error",
                        "El curso no pertenece a la selección indicada.");
            }
        }

        request.getRequestDispatcher("/consultaEdicion.jsp")
                .forward(request, response);
    }

    private static String parametro(HttpServletRequest request,
                                    String nombre) {
        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }
}