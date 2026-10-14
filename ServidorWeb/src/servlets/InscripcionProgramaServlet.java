package servlets;

import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/inscribir-programa")
public class InscripcionProgramaServlet extends HttpServlet {
    private final IControlador icon = Fabrica.getInstance().getIControlador();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        UsuarioResumen estudiante = estudianteLogueado(request);
        if (estudiante ==null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String nombrePrograma = request.getParameter("nombrePrograma");
        if (nombrePrograma == null || nombrePrograma.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/buscar#programas");
            return;
        }
        nombrePrograma = nombrePrograma.trim();
        
        if (icon.consultarPrograma(nombrePrograma) == null) {
            response.sendRedirect(request.getContextPath() + "/buscar#programas");
            return;
        }

        if (icon.buscarInscripcionPrograma(estudiante.getNickname(), nombrePrograma) != null) {
            redirigir(request, response, nombrePrograma,
                    "err", "Ya estas inscripto a este programa de formacion.");
            return;
        }
        
        try {
            icon.inscribirEstudiantePrograma(estudiante.getNickname(), nombrePrograma, LocalDate.now());
        } catch (Exception e) {
            redirigir(request, response, nombrePrograma,
                    "err", "No se pudo realizar la inscripcion: " + e.getMessage());
            return;
        }

        redirigir(request, response, nombrePrograma, "msg", "inscripto");
    }
    
    
    private void redirigir(HttpServletRequest request, HttpServletResponse response,
            String nombrePrograma, String clave, String valor) throws IOException {
        response.sendRedirect(request.getContextPath() + "/programa-detalle?nombre="
                + URLEncoder.encode(nombrePrograma, "UTF-8")
                + "&" + clave + "=" + URLEncoder.encode(valor, "UTF-8"));
    }
    
    private static UsuarioResumen estudianteLogueado(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return null;
        }
        UsuarioResumen u = (UsuarioResumen) sesion.getAttribute("usuarioLogueado");
        return (u != null && "Estudiante".equalsIgnoreCase(u.getTipo())) ? u : null;
    }
}
