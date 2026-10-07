<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="Logica.controladores.Fabrica" %>
<%@ page import="Logica.controladores.IControlador" %>
<%@ page import="java.util.List" %>

<%
   UsuarioResumen sidebarUser = (UsuarioResumen) session.getAttribute("usuarioLogueado");
   IControlador sidebarControl = Fabrica.getInstance().getIControlador();

   List<String> sidebarInstitutos = sidebarControl.listarNombresInstitutos();
   List<String> sidebarCategorias = sidebarControl.listarNombresCategorias();
%>

<aside class="home-sidebar">
    <div class="nav-group">
        <span class="nav-title">Institutos</span>
        <% for (String inst : sidebarInstitutos) { %>
        <a class="nav-item" href="<%= request.getContextPath() %>/consulta-curso?instituto=<%= java.net.URLEncoder.encode(inst, "UTF-8") %>">
            <%= util.Html.esc(inst) %>
        </a>
        <% } %>
    </div>

    <div class="nav-group">
        <span class="nav-title">Categorías</span>
        <% for (String cat : sidebarCategorias) { %>
        <a class="nav-item cat" href="<%= request.getContextPath() %>/consulta-curso?categoria=<%= java.net.URLEncoder.encode(cat, "UTF-8") %>">
            <%= util.Html.esc(cat) %>
        </a>
        <% } %>
    </div>

    <%-- Cursos: Consultar Curso lo ven todos (Visitante, Estudiante, Docente) --%>
    <div class="nav-group">
        <span class="nav-title">Cursos</span>
        <a class="nav-item" href="<%= request.getContextPath() %>/consulta-curso">Consultar Curso</a>
        <% if (sidebarUser != null && "Docente".equalsIgnoreCase(sidebarUser.getTipo())) { %>
        <a class="nav-item" href="<%= request.getContextPath() %>/alta-curso">Alta Curso</a>
        <a class="nav-item" href="<%= request.getContextPath() %>/alta-edicion">Alta Edición</a>
        <a class="nav-item" href="<%= request.getContextPath() %>/alta-programa">Alta Programa</a>
        <% } %>
    </div>

    <% if (sidebarUser != null && !"Docente".equalsIgnoreCase(sidebarUser.getTipo())) { %>
    <div class="nav-group">
        <span class="nav-title">Inscripciones</span>
        <a class="nav-item" href="<%= request.getContextPath() %>/buscar">Inscribirme</a>
        <a class="nav-item" href="<%= request.getContextPath() %>/perfil">Resultados</a>
    </div>
    <% } %>
</aside>
