<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleCurso" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<jsp:include page="header.jsp" />

<%
    DetalleCurso curso = (DetalleCurso) request.getAttribute("detalleCurso");
    UsuarioResumen user = (UsuarioResumen) session.getAttribute("usuarioLogueado");
%>

<% if (curso != null) { %>
<div class="detail-container">
    <div class="detail-header">
        <div class="detail-title-group">
            <span class="card-badge"><%= curso.getInstituto() %></span>
            <h1><%= curso.getNombre() %></h1>
            <p class="detail-url"><a href="<%= curso.getUrl() %>" target="_blank">🌐 <%= curso.getUrl() %></a></p>
        </div>
        <% if (curso.getRutaImagen() != null && !curso.getRutaImagen().trim().isEmpty()) { %>
            <img src="<%= curso.getRutaImagen() %>" alt="<%= curso.getNombre() %>" class="detail-img">
        <% } %>
    </div>

    <% if ("inscripto".equals(request.getParameter("msg"))) { %>
        <div class="alert alert-success">¡Inscripción realizada con éxito! Estado: Inscripto.</div>
    <% } else if (request.getParameter("err") != null) { %>
        <div class="alert alert-error"><%= request.getParameter("err") %></div>
    <% } %>

    <div class="detail-grid">
        <div class="detail-main">
            <h3>Descripción</h3>
            <p><%= curso.getDescripcion() %></p>

            <h3>Categorías Asociadas</h3>
            <div class="tags-container">
                <% if (curso.getCategorias() != null && !curso.getCategorias().isEmpty()) { 
                    for (String cat : curso.getCategorias()) { %>
                        <span class="tag"><%= cat %></span>
                <%  } 
                } else { %>
                    <span>Sin categorías asignadas</span>
                <% } %>
            </div>

            <h3>Cursos Previos Requeridos</h3>
            <ul>
                <% if (curso.getPrevias() != null && !curso.getPrevias().isEmpty()) { 
                    for (String previa : curso.getPrevias()) { %>
                        <li><a href="<%= request.getContextPath() %>/curso-detalle?nombre=<%= java.net.URLEncoder.encode(previa, "UTF-8") %>"><%= previa %></a></li>
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
                                <a href="<%= request.getContextPath() %>/edicion-detalle?nombre=<%= java.net.URLEncoder.encode(ed, "UTF-8") %>"><%= ed %></a>
                                <% if (user != null && "Estudiante".equalsIgnoreCase(user.getTipo())) { %>
                                    <form action="<%= request.getContextPath() %>/inscribir-edicion" method="post" style="display:inline;">
                                        <input type="hidden" name="nombreEdicion" value="<%= ed %>">
                                        <input type="hidden" name="nombreCurso" value="<%= curso.getNombre() %>">
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
                            <li><a href="<%= request.getContextPath() %>/programa-detalle?nombre=<%= java.net.URLEncoder.encode(prog, "UTF-8") %>"><%= prog %></a></li>
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
