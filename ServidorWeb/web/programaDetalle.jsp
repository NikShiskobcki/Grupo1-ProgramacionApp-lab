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
<div class="container-fluid px-0">
    <div class="edext-panel p-4">
        <div class="row g-4">
            <% if (!img.isEmpty()) { %>
                <div class="col-md-4">
                    <img src="<%= img %>" alt="<%= Html.esc(p.getNombre()) %>" class="img-fluid rounded" onerror="this.style.display='none'">
                </div>
            <% } %>
            <div class="<%= img.isEmpty() ? "col-12" : "col-md-8" %>">
                <h2 class="mb-1"><%= Html.esc(p.getNombre()) %></h2>
                <p><%= Html.esc(p.getDescripcion()) %></p>

                <dl class="row mb-3">
                    <dt class="col-sm-4 text-muted">Vigencia</dt>
                    <dd class="col-sm-8"><%= Html.esc(p.getFechaInicio()) %> a <%= Html.esc(p.getFechaFin()) %></dd>
                    <dt class="col-sm-4 text-muted">Fecha de alta</dt>
                    <dd class="col-sm-8"><%= Html.esc(p.getFechaAlta()) %></dd>
                </dl>

                <h5>Categorías</h5>
                <p>
                <% if (p.getCategorias() != null && !p.getCategorias().isEmpty()) {
                       for (String c : p.getCategorias()) { %>
                    <span class="badge text-bg-light border"><%= Html.esc(c) %></span>
                <%     }
                   } else { %>
                    <span class="text-muted">Sin categorías.</span>
                <% } %>
                </p>

                <h5>Cursos que lo integran</h5>
                <% if (p.getCursos() != null && !p.getCursos().isEmpty()) { %>
                    <div class="list-group">
                    <% for (CursoResumen c : p.getCursos()) { %>
                        <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                           href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(c.getNombre()) %>">
                            <span><%= Html.esc(c.getNombre()) %></span>
                            <span class="badge text-bg-secondary"><%= Html.esc(c.getNombreInstituto()) %></span>
                        </a>
                    <% } %>
                    </div>
                <% } else { %>
                    <p class="text-muted">Este programa todavía no tiene cursos.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>
<% } else { %>
    <div class="alert alert-secondary">Programa de formación no encontrado.</div>
<% } %>

<jsp:include page="footer.jsp" />
