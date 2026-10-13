package servlets;

import Logica.DTO.CursoResumen;
import Logica.DTO.DetalleProgramaFormacion;
import Logica.DTO.ElementoResumen;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Curso;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/agregar-curso-programa")
public class AgregarCursoProgramaServlet extends HttpServlet{
    private final IControlador icon = Fabrica.getInstance().getIControlador();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        UsuarioResumen docente = docenteLogueado(request);
        if (docente == null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        cargarVista(request, docente, trim(request.getParameter("programa")));
        request.getRequestDispatcher("/agregarCursoPrograma.jsp").forward(request, response);        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        UsuarioResumen docente = docenteLogueado(request);
        if (docente == null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String programa = trim(request.getParameter("programa"));
        String[] cursos = request.getParameterValues("curso");
        
        //check de si el programa es del docente
        if(!esPropio(icon.listarProgramasCreados(docente.getNickname()),programa)){
            error(request, response, docente, "", "Solo el docente que crea el programa puede agregar cursos");
            return;
        }
        if (cursos == null || cursos.length == 0) {
            error(request, response, docente, programa, "Seleccione al menos un curso");
            return;
        }
        
        try {
            for (String curso : cursos) {
                icon.agregarCursoAPrograma(programa, curso);
            }
        } catch (Exception e) {
            error(request, response, docente, programa,
                    "No se pudieron agregar los cursos: " + e.getMessage());
            return;
        }
        response.sendRedirect(request.getContextPath() + "/programa-detalle?msg=cursos&nombre="
                + URLEncoder.encode(programa, "UTF-8"));
        
    }
    
    //carga lo de la pantalla (programas del docente y los cursos que le faltan al programa)
    private void cargarVista(HttpServletRequest request, UsuarioResumen docente, String programaSel){
        List<ElementoResumen> propios = icon.listarProgramasCreados(docente.getNickname());
        request.setAttribute("programas", propios);
        
        if (programaSel.isEmpty()) {
            return;
        }
        if (!esPropio(propios, programaSel)) {
            request.setAttribute("error", "Ese programa no existe o no tenes permisos");
            return;
        }
        
        Set<String> progSeleccionado = new HashSet<>();
        DetalleProgramaFormacion detalle = icon.consultarPrograma(programaSel);
        
        if (detalle != null && detalle.getCursos() != null) {
            for (CursoResumen c : detalle.getCursos()) {
                progSeleccionado.add(c.getNombre());
            }
        }
        
        List<Curso> disponibles = new ArrayList<>();
        for (Curso c : icon.listarCursos()) {
            if (!progSeleccionado.contains(c.getNombre())) {
                disponibles.add(c);
            }
        }
        request.setAttribute("programaSel", programaSel);
        request.setAttribute("disponibles", disponibles);
    }
    
    private void error(HttpServletRequest request, HttpServletResponse response,
            UsuarioResumen docente, String programa, String mensaje)
            throws ServletException, IOException {
        cargarVista(request, docente, programa);
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("/agregarCursoPrograma.jsp").forward(request, response);
    }
    
    private static boolean esPropio(List<ElementoResumen> propios, String nombre) {
        for (ElementoResumen p : propios) {
            if (p.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
    
    private static UsuarioResumen docenteLogueado(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return null;
        }
        UsuarioResumen u = (UsuarioResumen) sesion.getAttribute("usuarioLogueado");
        return (u != null && "Docente".equalsIgnoreCase(u.getTipo())) ? u : null;
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
