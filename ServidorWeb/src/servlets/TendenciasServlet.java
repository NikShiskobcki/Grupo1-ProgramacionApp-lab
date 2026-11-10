package servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.CursoTendencia;
import util.ClienteCentral;


@WebServlet("/tendencias")
public class TendenciasServlet extends HttpServlet {

    private static final int CANTIDAD = 5;
    private ClienteCentral control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CursoTendencia> tendencias;
        try {
            tendencias = control.listarCursosTendencia(CANTIDAD);
        } catch (RuntimeException e) {
            log("No se pudieron obtener las tendencias", e);
            tendencias = Collections.emptyList();
            request.setAttribute("tendenciasError", Boolean.TRUE);
        }

        request.setAttribute("tendencias", tendencias);
        request.getRequestDispatcher("/tendencias.jsp").include(request, response);
    }
}