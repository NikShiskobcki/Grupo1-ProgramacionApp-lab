package servlets;

import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/alta-programa")
public class AltaProgramaServlet extends HttpServlet {
    private final IControlador icon = Fabrica.getInstance().getIControlador();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException{
        if (docenteLogueado(request)==null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/altaPrograma.jsp").forward(request,response);
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
        
        String nombre = trim(request.getParameter("nombre"));
        String descripcion = trim(request.getParameter("descripcion"));
        String fInicioStr = trim(request.getParameter("fechaInicio"));
        String fFinStr = trim(request.getParameter("fechaFin"));
        
        request.setAttribute("v_nombre",nombre);
        request.setAttribute("v_descripcion",descripcion);
        request.setAttribute("v_fInicio",fInicioStr);
        request.setAttribute("v_fFin",fFinStr);
        
        if (nombre.isEmpty()||descripcion.isEmpty()|| fInicioStr.isEmpty()||fFinStr.isEmpty()){
            error(request,response,"Debe completar todos los campos obligatorios");
            return;
        }
        
        LocalDate inicio,fin;
        try{
            inicio = LocalDate.parse(fInicioStr);
            fin = LocalDate.parse(fFinStr);
        }catch (DateTimeParseException e){
            error(request,response, "Las fechas ingresadas no son validas");
            return;
        }
        if (fin.isBefore(inicio)){
            error(request,response, "Las fechas ingresadas no son validas");
            return;
        }
        if(icon.existePrograma(nombre)){
            error(request, response, "Ya existe un programa de formacion con ese nombre");
            return;
        }
        
        try{
            icon.altaPrograma(nombre, descripcion, inicio, fin, LocalDate.now(), null, docente.getNickname());
        }catch(Exception e){
            error(request,response,"No se pudo dar de alta al Programa de Formacion: "+ e.getMessage());
            return;
        }
        response.sendRedirect(request.getContextPath() + "/programa-detalle?msg=creado&nombre="
                + URLEncoder.encode(nombre,"UTF-8"));
    }
    
    
    private void error(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException{
        request.setAttribute("error",mensaje);
        request.getRequestDispatcher("/altaPrograma.jsp").forward(request,response);
    }
    
    private static UsuarioResumen docenteLogueado(HttpServletRequest request){
        HttpSession sesion = request.getSession(false);
        if (sesion == null)return null;
        UsuarioResumen u = (UsuarioResumen) sesion.getAttribute("usuarioLogueado");
        return(u!=null && "Docente".equalsIgnoreCase(u.getTipo())) ? u : null;
    }
    
    private static String trim(String s){
        return s == null ? "" : s.trim();
    }
    
}
