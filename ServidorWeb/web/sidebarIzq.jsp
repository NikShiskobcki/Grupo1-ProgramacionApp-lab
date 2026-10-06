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
        <a class="nav-item" href="<%= request.getContextPath() %>/buscar">
            <%= inst %>
        </a>
        <% } %>
    </div>

    <div class="nav-group">
        <span class="nav-title">Categorías</span>
        <% for (String cat : sidebarCategorias) { %>
        <a class="nav-item cat" href="<%= request.getContextPath() %>/buscar">
            <%= cat %>
        </a>
        <% } %>
    </div>

        <% if (sidebarUser != null) { %>
        <% if ("Docente".equalsIgnoreCase(sidebarUser.getTipo())) { %>
        <div class="nav-group">
            <span class="nav-title">Cursos</span>
            <a class="nav-item" href="<%= request.getContextPath() %>/alta-curso">Alta Curso</a>
            <a class="nav-item" href="<%= request.getContextPath() %>/alta-edicion">Alta Edición</a>
            <a class="nav-item" href="<%= request.getContextPath() %>/alta-programa">Alta Programa</a>
        </div>
        <% } else { %>
        <div class="nav-group">
            <span class="nav-title">Inscripciones</span>
            <a class="nav-item" href="<%= request.getContextPath() %>/buscar">Inscribirme</a>
            <a class="nav-item" href="<%= request.getContextPath() %>/perfil">Resultados</a>
        </div>
        <% } %>
        <% } %>
</aside>
