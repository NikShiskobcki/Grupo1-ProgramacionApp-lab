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
    <nav class="navbar navbar-expand-lg py-2">
        <div class="header-container container-fluid flex-wrap">

            <div class="brand">
                <a href="<%= request.getContextPath() %>/index.jsp">
                    <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor"
                         class="brand-logo bi bi-mortarboard-fill" viewBox="0 0 16 16" aria-hidden="true">
                        <path d="M8.211 2.047a.5.5 0 0 0-.422 0l-7.5 3.5a.5.5 0 0 0 .025.917l7.5 3a.5.5 0 0 0 .372 0L14 7.14V13a1 1 0 0 0-1 1v2h3v-2a1 1 0 0 0-1-1V6.739l.686-.275a.5.5 0 0 0 .025-.917z"/>
                        <path d="M4.176 9.032a.5.5 0 0 0-.656.327l-.5 1.7a.5.5 0 0 0 .294.605l4.5 1.8a.5.5 0 0 0 .372 0l4.5-1.8a.5.5 0 0 0 .294-.605l-.5-1.7a.5.5 0 0 0-.656-.327L8 10.466z"/>
                    </svg>
                    <span class="brand-name">ed<strong>Ext</strong></span>
                </a>
            </div>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navHeaderCollapse" aria-controls="navHeaderCollapse"
                    aria-expanded="false" aria-label="Mostrar menú">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse flex-grow-1" id="navHeaderCollapse">
                <div class="d-flex flex-column flex-lg-row align-items-stretch align-items-lg-center
                            justify-content-lg-between flex-grow-1 gap-3 mt-3 mt-lg-0">

                    <div class="header-search">
                        <form action="<%= request.getContextPath() %>/buscar" method="get">
                            <input type="text" name="q" placeholder="Buscar cursos, programas" value="<%= Html.esc(request.getParameter("q")) %>">
                            <button type="submit" aria-label="Buscar">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                     class="bi bi-search" viewBox="0 0 16 16" aria-hidden="true">
                                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                                </svg>
                            </button>
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
            </div>
        </div>
    </nav>
</header>
<main class="main-content edext-main">