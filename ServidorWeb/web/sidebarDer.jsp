<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="Logica.DTO.DetalleUsuario" %>
<%@ page import="Logica.DTO.ElementoResumen" %>
<%@ page import="Logica.controladores.Fabrica" %>
<%@ page import="Logica.controladores.IControlador" %>
<%@ page import="java.util.List" %>
<%@ page import="util.Html" %>


<%
   UsuarioResumen sidebarDerUser = (UsuarioResumen) session.getAttribute("usuarioLogueado");
   DetalleUsuario sidebarDerDetalle = null;
   List<ElementoResumen> sidebarDerProgramas = null;
   boolean sidebarDerEsDocente = false;

   if (sidebarDerUser != null) {
       IControlador sidebarDerControl = Fabrica.getInstance().getIControlador();
       String sidebarDerNick = sidebarDerUser.getNickname();

       sidebarDerDetalle = sidebarDerControl.consultarUsuario(sidebarDerNick, sidebarDerNick);
       sidebarDerEsDocente = "Docente".equalsIgnoreCase(sidebarDerUser.getTipo());

       sidebarDerProgramas = sidebarDerEsDocente
               ? sidebarDerControl.listarProgramasCreados(sidebarDerNick)
               : sidebarDerDetalle.getProgramas();
   }
   String sidebarDerCtx = request.getContextPath();
%>

<% if (sidebarDerDetalle != null) { %>
<aside class="home-sidebar home-sidebar-der">
    <div class="nav-group">
        <span class="nav-title">Ediciones de curso</span>
        <% if (sidebarDerDetalle.getEdiciones().isEmpty()) { %>
        <span class="nav-empty">No hay ediciones.</span>
        <% } else { %>
        <% for (ElementoResumen e : sidebarDerDetalle.getEdiciones()) { %>
        <a class="nav-item" href="<%= sidebarDerCtx %>/edicion-detalle?nombre=<%= Html.url(e.getNombre()) %>">
            <%= Html.esc(e.getNombre()) %>
        </a>
        <% } %>
        <% } %>
    </div>

    <div class="nav-group">
        <span class="nav-title"><%= sidebarDerEsDocente ? "Programas creados" : "Programas de formación" %></span>
        <% if (sidebarDerProgramas.isEmpty()) { %>
        <span class="nav-empty">No hay programas.</span>
        <% } else { %>
        <% for (ElementoResumen p : sidebarDerProgramas) { %>
        <a class="nav-item cat" href="<%= sidebarDerCtx %>/programa-detalle?nombre=<%= Html.url(p.getNombre()) %>">
            <%= Html.esc(p.getNombre()) %>
        </a>
        <% } %>
        <% } %>
    </div>
</aside>
<% } %>