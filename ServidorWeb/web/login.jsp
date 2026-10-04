<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<div class="auth-box">
    <h2>Iniciar Sesión en edExt</h2>
    <p class="auth-subtitle">Ingresa tus credenciales para acceder a tus funcionalidades.</p>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("mensajeExito") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("mensajeExito") %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/login" method="post" class="form-vertical">
        <div class="form-group">
            <label for="usuario">Nickname o Correo Electrónico:</label>
            <input type="text" id="usuario" name="usuario" required placeholder="ej. usuario123 o correo@ejemplo.com">
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

<jsp:include page="footer.jsp" />
