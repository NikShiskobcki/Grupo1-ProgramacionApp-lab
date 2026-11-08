package servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Dispositivo;

/**
 * Deja en el request el atributo "esMovil" (Boolean) para que lo usen servlets y JSP.
 * En un celular, la página inicial del sitio es el login: si todavía no hay sesión,
 * entrar a la raíz o a index.jsp redirige a /login. En computadora no cambia nada.
 */
@WebFilter("/*")
public class DispositivoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        boolean movil = Dispositivo.esMovil(request);
        request.setAttribute(Dispositivo.ATRIBUTO, movil);

        if (movil && esPaginaInicial(request) && !haySesion(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        chain.doFilter(req, res);
    }

    private static boolean esPaginaInicial(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        String ruta = uri.startsWith(ctx) ? uri.substring(ctx.length()) : uri;
        return ruta.isEmpty() || ruta.equals("/") || ruta.equals("/index.jsp");
    }

    private static boolean haySesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuarioLogueado") != null;
    }
}
