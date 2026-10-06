<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="Logica.DTO.DetalleUsuario" %>
<%@ page import="Logica.DTO.ElementoResumen" %>
<%@ page import="Logica.controladores.Fabrica" %>
<%@ page import="Logica.controladores.IControlador" %>
<%@ page import="util.Html" %>

<%
   UsuarioResumen sidebarDerUser = (UsuarioResumen) session.getAttribute("usuarioLogueado");
   DetalleUsuario sidebarDerDetalle = null;
   if (sidebarDerUser != null) {
       IControlador sidebarDerControl = Fabrica.getInstance().getIControlador();
       sidebarDerDetalle = sidebarDerControl.consultarUsuario(
               sidebarDerUser.getNickname(), sidebarDerUser.getNickname());
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
        <span class="nav-title">Programas de formación</span>
        <% if (sidebarDerDetalle.getProgramas().isEmpty()) { %>
        <span class="nav-empty">No hay programas.</span>
        <% } else { %>
        <% for (ElementoResumen p : sidebarDerDetalle.getProgramas()) { %>
        <a class="nav-item cat" href="<%= sidebarDerCtx %>/programa-detalle?nombre=<%= Html.url(p.getNombre()) %>">
            <%= Html.esc(p.getNombre()) %>
        </a>
        <% } %>
        <% } %>
    </div>
</aside>
<% } %>