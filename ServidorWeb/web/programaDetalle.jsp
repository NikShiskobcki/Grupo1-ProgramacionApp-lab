<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleProgramaFormacion" %>
<%@ page import="Logica.DTO.CursoResumen" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="Logica.Entidades.InscripcionPrograma" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    DetalleProgramaFormacion p = (DetalleProgramaFormacion) request.getAttribute("detallePrograma");
    String ctx = request.getContextPath();
    UsuarioResumen usuario = (UsuarioResumen) session.getAttribute("usuarioLogueado");
    boolean esEstudiante = usuario != null && "Estudiante".equalsIgnoreCase(usuario.getTipo());
    InscripcionPrograma inscripcion = (InscripcionPrograma) request.getAttribute("inscripcionPrograma");
%>

<% if (p != null) {
    String img = Html.imagen(ctx, p.getRutaImagen());
%>
<div class="detail-container">
    <div class="detail-header">
        <div class="detail-title-group">
            <span class="card-badge">Programa de formacion</span>
            <h1><%= Html.esc(p.getNombre()) %></h1>
        </div>
        <% if (!img.isEmpty()) { %>
        <img src="<%= img %>" alt="<%= Html.esc(p.getNombre()) %>" class="detail-img" onerror="this.style.display='none'">
        <% } %>
    </div>

    <% if ("creado".equals(request.getParameter("msg"))) { %>
    <div class="alert alert-success">Programa de formacion creado con exito!</div>
    <% } %>
    <% if ("cursos".equals(request.getParameter("msg"))) { %>
    <div class="alert alert-success">Cursos agregados al programa con exito!</div>
    <% } %>
    
    <% if ("inscripto".equals(request.getParameter("msg"))) { %>
    <div class="alert alert-success">Inscripcion al programa realizada con exito!</div>
    <% } %>
    <% if (request.getParameter("err") != null) { %>
    <div class="alert alert-error"><%= Html.esc(request.getParameter("err")) %></div>
    <% } %>

    <div class="detail-grid">
        <div class="detail-main">
            <h3>Descripcion</h3>
            <p><%= Html.esc(p.getDescripcion()) %></p>

            <h3>Categorias Asociadas</h3>
            <div class="tags-container">
                <% if (p.getCategorias() != null && !p.getCategorias().isEmpty()) {
                    for (String cat : p.getCategorias()) { %>
                <span class="tag"><%= Html.esc(cat) %></span>
                <%  }
                } else { %>
                <span>Sin categorias asignadas</span>
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
                <li>Este programa todavia no tiene cursos.</li>
                    <% } %>
            </ul>
        </div>

        <div class="detail-sidebar">
            <div class="sidebar-box">
                <h4>Informacion del Programa</h4>
                <p><strong>Vigencia:</strong> <%= Html.esc(p.getFechaInicio()) %> a <%= Html.esc(p.getFechaFin()) %></p>
                <p><strong>Fecha de Alta:</strong> <%= Html.esc(p.getFechaAlta()) %></p>
            </div>
                <% if (esEstudiante) { %>
                <div class="sidebar-box">
                    <h4>Inscripcion</h4>
                    <% if (inscripcion != null) { %>
                    <p>Ya estas inscripto a este programa desde el
                        <%= inscripcion.getFechaInscripcion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>.</p>
                        <% } else { %>
                    <form action="<%= ctx %>/inscribir-programa" method="post">
                        <input type="hidden" name="nombrePrograma" value="<%= Html.esc(p.getNombre()) %>">
                        <button type="submit" class="btn btn-sm btn-primary">Inscribirme</button>
                    </form>
                    <% } %>
                </div>
                <% } %>
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