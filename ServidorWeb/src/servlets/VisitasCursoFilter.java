package servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import util.ClienteCentral;


@WebFilter("/curso-detalle")
public class VisitasCursoFilter implements Filter {

    private ClienteCentral control;
    private ServletContext contexto;

    @Override
    public void init(FilterConfig config) throws ServletException {
        contexto = config.getServletContext();
        control = new ClienteCentral(contexto);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        
        chain.doFilter(req, res);

        
        HttpServletRequest request = (HttpServletRequest) req;

        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return;
        }
        
        if (request.getAttribute("detalleCurso") == null) {
            return;
        }

        String nombre = request.getParameter("nombre");
        try {
            control.registrarVisitaCurso(nombre.trim());
        } catch (RuntimeException e) {
            contexto.log("No se pudo registrar la visita al curso " + nombre, e);
        }
    }

    @Override
    public void destroy() {
    }
}
