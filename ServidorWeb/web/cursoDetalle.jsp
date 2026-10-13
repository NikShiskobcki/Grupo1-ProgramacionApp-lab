<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleCurso" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    DetalleCurso curso = (DetalleCurso) request.getAttribute("detalleCurso");
    UsuarioResumen user = (UsuarioResumen) session.getAttribute("usuarioLogueado");
    String urlCurso = (curso != null && curso.getUrl() != null) ? curso.getUrl().trim() : "";
    boolean urlSegura = urlCurso.toLowerCase().startsWith("http://") || urlCurso.toLowerCase().startsWith("https://");
%>

<% if (curso != null) { %>
<div class="detail-container">
    <div class="detail-header">
        <div class="detail-title-group">
            <span class="card-badge"><%= Html.esc(curso.getInstituto()) %></span>
            <h1><%= Html.esc(curso.getNombre()) %></h1>
            <% if (urlSegura) { %><p class="detail-url"><a href="<%= Html.esc(urlCurso) %>" target="_blank" rel="noopener">🌐 <%= Html.esc(urlCurso) %></a></p><% } %>
        </div>
        <% if (curso.getRutaImagen() != null && !curso.getRutaImagen().trim().isEmpty()) { %>
            <img src="<%= Html.imagen(request.getContextPath(), curso.getRutaImagen()) %>" alt="<%= Html.esc(curso.getNombre()) %>" class="detail-img" onerror="this.style.display='none'">
        <% } %>
    </div>

    <% if ("inscripto".equals(request.getParameter("msg"))) { %>
        <div class="alert alert-success">¡Inscripción realizada con éxito! Estado: Inscripto.</div>
    <% } else if ("creado".equals(request.getParameter("msg"))) { %>
        <div class="alert alert-success">¡Curso creado con éxito!</div>
    <% } else if (request.getParameter("err") != null) { %>
        <div class="alert alert-error"><%= Html.esc(request.getParameter("err")) %></div>
    <% } %>

    <div class="detail-grid">
        <div class="detail-main">
            <h3>Descripción</h3>
            <p><%= Html.esc(curso.getDescripcion()) %></p>

            <h3>Categorías Asociadas</h3>
            <div class="tags-container">
                <% if (curso.getCategorias() != null && !curso.getCategorias().isEmpty()) { 
                    for (String cat : curso.getCategorias()) { %>
                        <span class="tag"><%= Html.esc(cat) %></span>
                <%  } 
                } else { %>
                    <span>Sin categorías asignadas</span>
                <% } %>
            </div>

            <h3>Cursos Previos Requeridos</h3>
            <ul>
                <% if (curso.getPrevias() != null && !curso.getPrevias().isEmpty()) { 
                    for (String previa : curso.getPrevias()) { %>
                        <li><a href="<%= request.getContextPath() %>/curso-detalle?nombre=<%= Html.url(previa) %>"><%= Html.esc(previa) %></a></li>
                <%  } 
                } else { %>
                    <li>Ninguna previa requerida</li>
                <% } %>
            </ul>
        </div>

        <div class="detail-sidebar">
            <div class="sidebar-box">
                <h4>Información del Curso</h4>
                <p><strong>Duración:</strong> <%= curso.getDuracion() %> meses</p>
                <p><strong>Horas:</strong> <%= curso.getCantidadHoras() %> hrs</p>
                <p><strong>Créditos:</strong> <%= curso.getCreditos() %></p>
                <p><strong>Fecha de Alta:</strong> <%= curso.getFechaAlta() %></p>
            </div>

            <div class="sidebar-box">
                <h4>Ediciones del Curso</h4>
                <% if (curso.getEdiciones() != null && !curso.getEdiciones().isEmpty()) { %>
                    <ul class="editions-list">
                        <% for (String ed : curso.getEdiciones()) { %>
                            <li>
                                <a href="<%= request.getContextPath() %>/edicion-detalle?nombre=<%= Html.url(ed) %>"><%= Html.esc(ed) %></a>
                                <% if (user != null && "Estudiante".equalsIgnoreCase(user.getTipo())) { %>
                                    <form action="<%= request.getContextPath() %>/inscribir-edicion" method="post" style="display:inline;">
                                        <input type="hidden" name="nombreEdicion" value="<%= Html.esc(ed) %>">
                                        <input type="hidden" name="nombreCurso" value="<%= Html.esc(curso.getNombre()) %>">
                                        <button type="submit" class="btn btn-sm btn-primary">Inscribirme</button>
                                    </form>
                                <% } %>
                            </li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p>No hay ediciones registradas para este curso.</p>
                <% } %>
            </div>

            <div class="sidebar-box">
                <h4>Programas de Formación</h4>
                <% if (curso.getProgramas() != null && !curso.getProgramas().isEmpty()) { %>
                    <ul>
                        <% for (String prog : curso.getProgramas()) { %>
                            <li><a href="<%= request.getContextPath() %>/programa-detalle?nombre=<%= Html.url(prog) %>"><%= Html.esc(prog) %></a></li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p>No forma parte de ningún programa de formación.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>
<% } else { %>
    <p>Curso no encontrado.</p>
<% } %>

<jsp:include page="footer.jsp" />
