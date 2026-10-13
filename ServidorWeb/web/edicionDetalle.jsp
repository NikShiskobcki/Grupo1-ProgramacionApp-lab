<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleEdicionCurso" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    DetalleEdicionCurso ed = (DetalleEdicionCurso) request.getAttribute("detalleEdicion");
    String ctx = request.getContextPath();
%>

<% if (ed != null) {
    String img = Html.imagen(ctx, ed.getRutaImagen());
%>
<div class="container-fluid px-0">
    <div class="edext-panel p-4">
        <div class="row g-4">
            <% if (!img.isEmpty()) { %>
                <div class="col-md-4">
                    <img src="<%= img %>" alt="<%= Html.esc(ed.getNombre()) %>" class="img-fluid rounded" onerror="this.style.display='none'">
                </div>
            <% } %>
            <div class="<%= img.isEmpty() ? "col-12" : "col-md-8" %>">
                <h2 class="mb-1"><%= Html.esc(ed.getNombre()) %></h2>
                <p class="text-muted">Edición del curso
                    <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(ed.getCurso()) %>"><%= Html.esc(ed.getCurso()) %></a>
                </p>

                <dl class="row mb-3">
                    <dt class="col-sm-4 text-muted">Fecha de inicio</dt><dd class="col-sm-8"><%= Html.esc(ed.getFechaInicio()) %></dd>
                    <dt class="col-sm-4 text-muted">Fecha de fin</dt><dd class="col-sm-8"><%= Html.esc(ed.getFechaFin()) %></dd>
                    <dt class="col-sm-4 text-muted">Cupo</dt><dd class="col-sm-8"><%= ed.getCupo() != null ? ed.getCupo() : "Sin cupo definido" %></dd>
                    <dt class="col-sm-4 text-muted">Fecha de publicación</dt><dd class="col-sm-8"><%= Html.esc(ed.getFechaPublicacion()) %></dd>
                    <dt class="col-sm-4 text-muted">Inscriptos</dt><dd class="col-sm-8"><%= ed.getCantidadInscriptos() %></dd>
                </dl>

                <h5>Docentes</h5>
                <% if (ed.getDocentes() != null && !ed.getDocentes().isEmpty()) { %>
                    <ul class="list-unstyled">
                    <% for (String d : ed.getDocentes()) {
                           String nick = Html.nicknameDeDocente(d); %>
                        <li>
                            <% if (nick != null) { %>
                                <a href="<%= ctx %>/perfil?user=<%= Html.url(nick) %>"><%= Html.esc(d) %></a>
                            <% } else { %>
                                <%= Html.esc(d) %>
                            <% } %>
                        </li>
                    <% } %>
                    </ul>
                <% } else { %>
                    <p class="text-muted">Sin docentes asignados.</p>
                <% } %>

                <% if (ed.getCategorias() != null && !ed.getCategorias().isEmpty()) { %>
                    <h5>Categorías</h5>
                    <p>
                    <% for (String c : ed.getCategorias()) { %>
                        <span class="badge text-bg-light border"><%= Html.esc(c) %></span>
                    <% } %>
                    </p>
                <% } %>
            </div>
        </div>
    </div>
</div>
<% } else { %>
    <div class="alert alert-secondary">Edición no encontrada.</div>
<% } %>

<jsp:include page="footer.jsp" />
