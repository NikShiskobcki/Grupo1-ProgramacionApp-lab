<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleProgramaFormacion" %>
<%@ page import="Logica.DTO.CursoResumen" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    DetalleProgramaFormacion p = (DetalleProgramaFormacion) request.getAttribute("detallePrograma");
    String ctx = request.getContextPath();
%>

<% if (p != null) {
    String img = Html.imagen(ctx, p.getRutaImagen());
%>
<div class="detail-container">
    <div class="detail-header">
        <div class="detail-title-group">
            <span class="card-badge">Programa de formación</span>
            <h1><%= Html.esc(p.getNombre()) %></h1>
        </div>
        <% if (!img.isEmpty()) { %>
        <img src="<%= img %>" alt="<%= Html.esc(p.getNombre()) %>" class="detail-img" onerror="this.style.display='none'">
        <% } %>
    </div>

    <% if ("creado".equals(request.getParameter("msg"))) { %>
    <div class="alert alert-success">¡Programa de formación creado con éxito!</div>
    <% } %>

    <div class="detail-grid">
        <div class="detail-main">
            <h3>Descripción</h3>
            <p><%= Html.esc(p.getDescripcion()) %></p>

            <h3>Categorías Asociadas</h3>
            <div class="tags-container">
                <% if (p.getCategorias() != null && !p.getCategorias().isEmpty()) {
                    for (String cat : p.getCategorias()) { %>
                <span class="tag"><%= Html.esc(cat) %></span>
                <%  }
                } else { %>
                <span>Sin categorías asignadas</span>
                <% } %>
            </div>

            <h3>Cursos que lo integran</h3>
            <ul>
                <% if (p.getCursos() != null && !p.getCursos().isEmpty()) {
                    for (CursoResumen c : p.getCursos()) { %>
                <li>
                    <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(c.getNombre()) %>"><%= Html.esc(c.getNombre()) %></a>
                    <small>(<%= Html.esc(c.getNombreInstituto()) %>)</small>
                </li>
                <%  }
                } else { %>
                <li>Este programa todavía no tiene cursos.</li>
                    <% } %>
            </ul>
        </div>

        <div class="detail-sidebar">
            <div class="sidebar-box">
                <h4>Información del Programa</h4>
                <p><strong>Vigencia:</strong> <%= Html.esc(p.getFechaInicio()) %> a <%= Html.esc(p.getFechaFin()) %></p>
                <p><strong>Fecha de Alta:</strong> <%= Html.esc(p.getFechaAlta()) %></p>
            </div>
        </div>
    </div>
</div>
<% } else { %>
<div class="alert alert-error">
    <%= request.getAttribute("error") != null ? Html.esc(request.getAttribute("error")) : "Programa de formación no encontrado." %>
</div>
<p><a href="<%= ctx %>/buscar#programas">← Volver a los programas</a></p>
<% } %>

<jsp:include page="footer.jsp" />