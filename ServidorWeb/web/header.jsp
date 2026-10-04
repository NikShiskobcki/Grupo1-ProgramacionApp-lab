<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="util.Html" %>
<%
    UsuarioResumen user = (UsuarioResumen) session.getAttribute("usuarioLogueado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>edExt - Plataforma Educativa</title>
    <!-- Bootstrap 5 (se carga antes que styles.css para que el estilo propio tenga prioridad) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
<header class="main-header">
    <div class="header-container">
        <div class="brand">
            <a href="<%= request.getContextPath() %>/index.jsp">
                <span class="brand-logo">🎓</span>
                <span class="brand-name">ed<strong>Ext</strong></span>
            </a>
        </div>

        <div class="header-search">
            <form action="<%= request.getContextPath() %>/buscar" method="get">
                <input type="text" name="q" placeholder="Buscar cursos, programas" value="<%= Html.esc(request.getParameter("q")) %>">
                <button type="submit" aria-label="Buscar">🔍</button>
            </form>
        </div>

        <div class="header-user">
            <a href="<%= request.getContextPath() %>/usuarios" class="btn btn-outline">Usuarios</a>
            <% if (user != null) { %>
                <div class="user-menu">
                    <% String imgHdr = Html.imagen(request.getContextPath(), user.getRutaImagen()); %>
                    <% if (!imgHdr.isEmpty()) { %>
                        <img src="<%= imgHdr %>" alt="" class="avatar-sm" onerror="this.style.display='none'">
                    <% } %>
                    <span class="user-greeting">Hola, <strong><%= Html.esc(user.getNombreCompleto()) %></strong> (<%= Html.esc(user.getTipo()) %>)</span>
                    <a href="<%= request.getContextPath() %>/perfil" class="btn btn-outline">Mi Perfil</a>
                    <a href="<%= request.getContextPath() %>/logout" class="btn btn-danger">Cerrar Sesión</a>
                </div>
            <% } else { %>
                <div class="auth-buttons">
                    <a href="<%= request.getContextPath() %>/login" class="btn btn-outline">Iniciar Sesión</a>
                    <a href="<%= request.getContextPath() %>/registro" class="btn btn-outline">Registrarse</a>
                </div>
            <% } %>
        </div>
    </div>
</header>
<main class="main-content edext-main">
