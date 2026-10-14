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
            <% if (urlSegura) { %>
                <p class="detail-url">
                    <a href="<%= Html.esc(urlCurso) %>" target="_blank" rel="noopener">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-globe2" viewBox="0 0 16 16">
                          <path d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m7.5-6.923c-.67.204-1.335.82-1.887 1.855q-.215.403-.395.872c.705.157 1.472.257 2.282.287zM4.249 3.539q.214-.577.481-1.078a7 7 0 0 1 .597-.933A7 7 0 0 0 3.051 3.05q.544.277 1.198.49zM3.509 7.5c.036-1.07.188-2.087.436-3.008a9 9 0 0 1-1.565-.667A6.96 6.96 0 0 0 1.018 7.5zm1.4-2.741a12.3 12.3 0 0 0-.4 2.741H7.5V5.091c-.91-.03-1.783-.145-2.591-.332M8.5 5.09V7.5h2.99a12.3 12.3 0 0 0-.399-2.741c-.808.187-1.681.301-2.591.332zM4.51 8.5c.035.987.176 1.914.399 2.741A13.6 13.6 0 0 1 7.5 10.91V8.5zm3.99 0v2.409c.91.03 1.783.145 2.591.332.223-.827.364-1.754.4-2.741zm-3.282 3.696q.18.469.395.872c.552 1.035 1.218 1.65 1.887 1.855V11.91c-.81.03-1.577.13-2.282.287zm.11 2.276a7 7 0 0 1-.598-.933 9 9 0 0 1-.481-1.079 8.4 8.4 0 0 0-1.198.49 7 7 0 0 0 2.276 1.522zm-1.383-2.964A13.4 13.4 0 0 1 3.508 8.5h-2.49a6.96 6.96 0 0 0 1.362 3.675c.47-.258.995-.482 1.565-.667m6.728 2.964a7 7 0 0 0 2.275-1.521 8.4 8.4 0 0 0-1.197-.49 9 9 0 0 1-.481 1.078 7 7 0 0 1-.597.933M8.5 11.909v3.014c.67-.204 1.335-.82 1.887-1.855q.216-.403.395-.872A12.6 12.6 0 0 0 8.5 11.91zm3.555-.401c.57.185 1.095.409 1.565.667A6.96 6.96 0 0 0 14.982 8.5h-2.49a13.4 13.4 0 0 1-.437 3.008M14.982 7.5a6.96 6.96 0 0 0-1.362-3.675c-.47.258-.995.482-1.565.667.248.92.4 1.938.437 3.008zM11.27 2.461q.266.502.482 1.078a8.4 8.4 0 0 0 1.196-.49 7 7 0 0 0-2.275-1.52c.218.283.418.597.597.932m-.488 1.343a8 8 0 0 0-.395-.872C9.835 1.897 9.17 1.282 8.5 1.077V4.09c.81-.03 1.577-.13 2.282-.287z"/>
                        </svg> <%= Html.esc(urlCurso) %>
                    </a>
                </p>
            <% } %>
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
