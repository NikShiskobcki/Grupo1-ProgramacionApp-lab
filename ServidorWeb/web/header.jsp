<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%
    UsuarioResumen user = (UsuarioResumen) session.getAttribute("usuarioLogueado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>edExt - Plataforma Educativa</title>
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

        <div class="header-user">
            <% if (user != null) { %>
                <div class="user-menu">
                    <span class="user-greeting">Hola, <strong><%= user.getNombreCompleto() %></strong> (<%= user.getTipo() %>)</span>
                    <a href="<%= request.getContextPath() %>/perfil" class="btn btn-outline">Mi Perfil</a>
                    <a href="<%= request.getContextPath() %>/logout" class="btn btn-danger">Cerrar Sesión</a>
                </div>
            <% } %>
        </div>
    </div>
</header>
<main class="main-content">
