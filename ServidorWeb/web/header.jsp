<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.UsuarioResumen" %>
<%@ page import="util.Html" %>
<%
    UsuarioResumen user = (UsuarioResumen) session.getAttribute("usuarioLogueado");
    String ctx = request.getContextPath();

    // Sección actual, para resaltarla en la barra (si la página llegó por forward, se usa la URL original)
    String uriActual = (String) request.getAttribute("javax.servlet.forward.request_uri");
    if (uriActual == null) uriActual = request.getRequestURI();
    boolean enCursos = uriActual.contains("/consulta-curso");
    boolean enUsuarios = uriActual.contains("/usuarios");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>edExt - Plataforma Educativa</title>
    <link rel="icon" type="image/svg+xml" href="<%= ctx %>/favicon.svg">
    <!-- Bootstrap 5 (se carga antes que styles.css para que el estilo propio tenga prioridad) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <%
        String vCss = "";
        String rutaCss = application.getRealPath("/css/styles.css");
        if (rutaCss != null) vCss = "?v=" + new java.io.File(rutaCss).lastModified();
        String vNav = "";
        String rutaNav = application.getRealPath("/css/navbar.css");
        if (rutaNav != null) vNav = "?v=" + new java.io.File(rutaNav).lastModified();
    %>
    <link rel="stylesheet" href="<%= ctx %>/css/styles.css<%= vCss %>">
    <link rel="stylesheet" href="<%= ctx %>/css/navbar.css<%= vNav %>">
</head>
<body>
<header class="main-header">
    <nav class="navbar navbar-expand-lg edext-navbar" data-bs-theme="dark" aria-label="Navegación principal">
        <div class="container-fluid">
            <a class="navbar-brand d-flex align-items-center gap-2" href="<%= ctx %>/index.jsp">
                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor"
                     class="brand-logo bi bi-mortarboard-fill" viewBox="0 0 16 16" aria-hidden="true">
                    <path d="M8.211 2.047a.5.5 0 0 0-.422 0l-7.5 3.5a.5.5 0 0 0 .025.917l7.5 3a.5.5 0 0 0 .372 0L14 7.14V13a1 1 0 0 0-1 1v2h3v-2a1 1 0 0 0-1-1V6.739l.686-.275a.5.5 0 0 0 .025-.917z"/>
                    <path d="M4.176 9.032a.5.5 0 0 0-.656.327l-.5 1.7a.5.5 0 0 0 .294.605l4.5 1.8a.5.5 0 0 0 .372 0l4.5-1.8a.5.5 0 0 0 .294-.605l-.5-1.7a.5.5 0 0 0-.656-.327L8 10.466z"/>
                </svg>
                <span class="brand-name">ed<strong>Ext</strong></span>
            </a>

            <!-- Botón «hamburger»: aparece en pantallas chicas y despliega el menú -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#menuPrincipal" aria-controls="menuPrincipal"
                    aria-expanded="false" aria-label="Mostrar u ocultar el menú">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="menuPrincipal">
                <ul class="navbar-nav me-lg-auto">
                    <li>
                        <a class="nav-link<%= enCursos ? " active" : "" %>" href="<%= ctx %>/consulta-curso"
                           <%= enCursos ? "aria-current=\"page\"" : "" %>>Consulta de Curso</a>
                    </li>
                    <li>
                        <a class="nav-link<%= enUsuarios ? " active" : "" %>" href="<%= ctx %>/usuarios"
                           <%= enUsuarios ? "aria-current=\"page\"" : "" %>>Usuarios</a>
                    </li>
                </ul>

                <div class="header-search">
                    <form action="<%= ctx %>/buscar" method="get">
                        <input type="text" name="q" placeholder="Buscar cursos, programas" value="<%= Html.esc(request.getParameter("q")) %>">
                        <button type="submit" aria-label="Buscar">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="20" fill="#ffffff"
                                 class="bi bi-search" viewBox="0 0 16 16" aria-hidden="true">
                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                            </svg>
                        </button>
                    </form>
                </div>

                <% if (user != null) { %>
                    <div class="navbar-usuario">
                        <% String imgHdr = Html.imagen(ctx, user.getRutaImagen()); %>
                        <%-- Foto y nombre llevan al perfil propio --%>
                        <a href="<%= ctx %>/perfil" class="user-link" title="Ver mi perfil">
                        <% if (!imgHdr.isEmpty()) { %>
                            <img src="<%= imgHdr %>" alt="" class="avatar-sm" onerror="this.style.display='none'">
                        <% } %>
                        <span class="user-greeting">Hola, <strong><%= Html.esc(user.getNombreCompleto()) %></strong> (<%= Html.esc(user.getTipo()) %>)</span>
                        </a>
                        <a href="<%= ctx %>/perfil" class="btn btn-outline">Mi Perfil</a>
                        <%-- Cierre de sesión: solo se ofrece con la sesión iniciada; POST para que no se dispare con un simple enlace --%>
                        <form action="<%= ctx %>/logout" method="post" class="m-0">
                            <button type="submit" class="btn btn-danger">Cerrar Sesión</button>
                        </form>
                    </div>
                <% } else { %>
                    <div class="navbar-usuario">
                        <a href="<%= ctx %>/login" class="btn btn-outline">Iniciar Sesión</a>
                        <a href="<%= ctx %>/registro" class="btn btn-outline">Registrarse</a>
                    </div>
                <% } %>
            </div>
        </div>
    </nav>
</header>
<main class="main-content edext-main">
