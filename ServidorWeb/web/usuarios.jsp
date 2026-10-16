<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="java.util.List" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">


<%
    List<UsuarioResumen> usuarios = (List<UsuarioResumen>) request.getAttribute("usuarios");
    String ctx = request.getContextPath();
%>

<div class="container-fluid px-0">
    <div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
        <div>
            <h2 class="mb-0">Usuarios</h2>
            <p class="text-muted mb-0">Seleccioná un usuario para ver su perfil.</p>
        </div>
        <input type="search" id="filtroLista" class="form-control" style="max-width: 280px;"
               placeholder="Filtrar por nombre o nickname" aria-label="Filtrar usuarios">
    </div>

    <% if (usuarios != null && !usuarios.isEmpty()) { %>
    <div class="row g-3">
        <% for (UsuarioResumen u : usuarios) {
               String img = Html.imagen(ctx, u.getRutaImagen());
               boolean docente = "Docente".equalsIgnoreCase(u.getTipo());
               String filtro = (u.getNickname() + " " + u.getNombreCompleto() + " " + u.getTipo()).toLowerCase();
        %>
        <div class="col-12 col-sm-6 col-lg-4" data-filtro="<%= Html.esc(filtro) %>">
            <a href="<%= ctx %>/perfil?user=<%= Html.url(u.getNickname()) %>"
               class="edext-panel p-3 d-flex align-items-center gap-3 text-decoration-none h-100 text-reset">
                <% if (!img.isEmpty()) { %>
                    <img src="<%= img %>" alt="" class="avatar-sm" onerror="imgFallback(this)">
                    <span class="avatar-sm-ph" style="display:none;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                        </svg>
                    </span>
                <% } else { %>
                    <span class="avatar-sm-ph">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                        </svg>
                    </span>
                <% } %>
                <div class="flex-grow-1 text-truncate">
                    <div class="fw-semibold text-truncate"><%= Html.esc(u.getNombreCompleto()) %></div>
                    <div class="small text-muted">@<%= Html.esc(u.getNickname()) %></div>
                </div>
                <span class="badge <%= docente ? "text-bg-info" : "text-bg-secondary" %>"><%= Html.esc(u.getTipo()) %></span>
            </a>
        </div>
        <% } %>
    </div>
    <% } else { %>
        <div class="alert alert-secondary">No hay usuarios registrados.</div>
    <% } %>
</div>
    </div>

    <jsp:include page="sidebarDer.jsp" />
</div>

<jsp:include page="footer.jsp" />
