<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<jsp:include page="header.jsp" />

<%
    UsuarioResumen user = (UsuarioResumen) session.getAttribute("usuarioLogueado");
%>

<% if (user == null) { %>
    <div class="auth-box">
        <h2>Iniciar Sesión en edExt</h2>
        <p class="auth-subtitle">Ingresa tus credenciales para acceder al sistema.</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>
        <% if (request.getAttribute("mensajeExito") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("mensajeExito") %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/login" method="post" class="form-vertical">
            <div class="form-group">
                <label for="usuario">Nickname o Correo Electrónico:</label>
                <input type="text" id="usuario" name="usuario" required placeholder="ej. heisenberg o correo@ejemplo.com">
            </div>

            <div class="form-group">
                <label for="contrasenia">Contraseña:</label>
                <input type="password" id="contrasenia" name="contrasenia" required placeholder="••••••••">
            </div>

            <button type="submit" class="btn btn-primary btn-block">Ingresar</button>
        </form>

        <div class="auth-footer">
            ¿Aún no tienes cuenta? <a href="<%= request.getContextPath() %>/registro">Regístrate aquí</a>
        </div>
    </div>
<% } else { %>
    <div class="auth-box auth-box-large text-center">
        <h2>Sesión Iniciada</h2>
        <p class="auth-subtitle">Bienvenido/a, <strong><%= user.getNombreCompleto() %></strong>.</p>
        <p>Has iniciado sesión con el rol de <strong><%= user.getTipo() %></strong> (@<%= user.getNickname() %>).</p>
        
        <div style="margin-top: 25px; display: flex; gap: 15px; justify-content: center;">
            <a href="<%= request.getContextPath() %>/perfil" class="btn btn-primary">Ver Mi Perfil</a>
            <a href="<%= request.getContextPath() %>/logout" class="btn btn-danger">Cerrar Sesión</a>
        </div>
    </div>
<% } %>

<jsp:include page="footer.jsp" />
