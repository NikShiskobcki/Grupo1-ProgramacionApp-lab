package servlets;

import Logica.DTO.UsuarioResumen;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import util.Imagenes;

@WebServlet("/alta-programa")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 6L * 1024 * 1024,
        maxRequestSize = 7L * 1024 * 1024
)
public class AltaProgramaServlet extends HttpServlet {
    private final IControlador icon = Fabrica.getInstance().getIControlador();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException{
        if (docenteLogueado(request)==null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        cargarCursos(request);
        request.getRequestDispatcher("/altaPrograma.jsp").forward(request, response);
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
        try{
            request.getParts();
        }catch(IllegalStateException e){
            error(request,response, "El archivo supera el tamaño permitido");
            return;
        }
        
        String nombre = trim(request.getParameter("nombre"));
        String descripcion = trim(request.getParameter("descripcion"));
        String fInicioStr = trim(request.getParameter("fechaInicio"));
        String fFinStr = trim(request.getParameter("fechaFin"));
        String imagenUrl = trim(request.getParameter("imagen"));
        Part archivo = request.getPart("imagenArchivo");
        boolean hayArchivo = archivo != null
                && archivo.getSubmittedFileName() != null
                && !archivo.getSubmittedFileName().trim().isEmpty();
        
        request.setAttribute("v_nombre",nombre);
        request.setAttribute("v_descripcion",descripcion);
        request.setAttribute("v_fInicio",fInicioStr);
        request.setAttribute("v_fFin",fFinStr);

        String[] cursosSel = request.getParameterValues("curso");
        request.setAttribute("v_cursos", cursosSel == null ? new ArrayList<String>() : Arrays.asList(cursosSel));
        
        request.setAttribute("v_imagen", imagenUrl);
        
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
        
        if (hayArchivo && !imagenUrl.isEmpty()) {
            error(request, response, "Elegí una imagen desde tu PC o ingresá una URL, pero no ambas.");
            return;
        }

        if (!imagenUrl.isEmpty()) {
            try {
                URI uri = URI.create(imagenUrl);
                String esquema = uri.getScheme();
                if (uri.getHost() == null
                        || !("http".equalsIgnoreCase(esquema) || "https".equalsIgnoreCase(esquema))) {
                    error(request, response, "La imagen debe tener una URL http:// o https:// válida.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                error(request, response, "La URL de la imagen no es válida.");
                return;
            }
        }
        
        String rutaImagen = imagenUrl.isEmpty() ? null : imagenUrl;
        String imagenGuardada = null;

        if (hayArchivo) {
            try {
                imagenGuardada = Imagenes.guardar(archivo);
                rutaImagen = imagenGuardada;
            } catch (IllegalArgumentException e) {
                error(request, response, e.getMessage());
                return;
            } catch (IOException e) {
                log("No se pudo guardar el archivo de imagen", e);
                error(request, response, "No se pudo guardar la imagen. Intente nuevamente.");
                return;
            }
        }

        try {
            icon.altaPrograma(nombre, descripcion, inicio, fin, LocalDate.now(), rutaImagen, docente.getNickname());
        } catch (Exception e) {
            if (imagenGuardada != null) {
                try {
                    Imagenes.eliminar(imagenGuardada);
                } catch (IOException ex) {
                    log("No se pudo eliminar una imagen de un alta fallida", ex);
                }
            }
            error(request, response, "No se pudo dar de alta al Programa de Formacion: " + e.getMessage());
            return;
        }
        if (cursosSel != null) {
            try {
                for (String curso : cursosSel) {
                    icon.agregarCursoAPrograma(nombre, curso);
                }
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/agregar-curso-programa?programa="
                        + URLEncoder.encode(nombre, "UTF-8"));
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/programa-detalle?msg=creado&nombre="
                + URLEncoder.encode(nombre,"UTF-8"));
    }
    
    
    private void error(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException{
        request.setAttribute("error",mensaje);
        cargarCursos(request);
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
    
   //lista de cursos
    private void cargarCursos(HttpServletRequest request) {
        request.setAttribute("cursos", icon.listarCursos());
    }
    
}
