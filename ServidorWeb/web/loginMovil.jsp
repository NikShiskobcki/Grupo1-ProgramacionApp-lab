<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.Html" %>
<%@ page import="util.Recordarme" %>
<%
    String ctx = request.getContextPath();

    // Si viene de un intento fallido se conserva lo escrito; si no, se usa el nickname recordado
    String nickRecordado = Recordarme.nickRecordado(request);
    boolean reintento = request.getParameter("usuario") != null;
    String usuarioValor = reintento ? request.getParameter("usuario")
            : (nickRecordado != null ? nickRecordado : "");
    boolean recordarMarcado = reintento ? request.getParameter("recordarme") != null
            : nickRecordado != null;
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Iniciar sesión - edExt</title>
    <link rel="icon" type="image/svg+xml" href="<%= ctx %>/favicon.svg">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= ctx %>/css/signin.css">
</head>
<body class="signin">
<main class="form-signin w-100 m-auto">
    <form action="<%= ctx %>/login" method="post">
        <div class="text-center mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" width="56" height="56" fill="currentColor"
                 class="signin-logo mb-2" viewBox="0 0 16 16" aria-hidden="true">
                <path d="M8.211 2.047a.5.5 0 0 0-.422 0l-7.5 3.5a.5.5 0 0 0 .025.917l7.5 3a.5.5 0 0 0 .372 0L14 7.14V13a1 1 0 0 0-1 1v2h3v-2a1 1 0 0 0-1-1V6.739l.686-.275a.5.5 0 0 0 .025-.917z"/>
                <path d="M4.176 9.032a.5.5 0 0 0-.656.327l-.5 1.7a.5.5 0 0 0 .294.605l4.5 1.8a.5.5 0 0 0 .372 0l4.5-1.8a.5.5 0 0 0 .294-.605l-.5-1.7a.5.5 0 0 0-.656-.327L8 10.466z"/>
            </svg>
            <div class="signin-brand">ed<strong>Ext</strong></div>
        </div>

        <h1 class="h3 mb-3 fw-normal text-center">Iniciar sesión</h1>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger py-2" role="alert"><%= Html.esc(request.getAttribute("error")) %></div>
        <% } %>
        <% if (request.getAttribute("mensajeExito") != null) { %>
            <div class="alert alert-success py-2" role="alert"><%= Html.esc(request.getAttribute("mensajeExito")) %></div>
        <% } %>

        <div class="form-floating">
            <input type="text" class="form-control" id="usuario" name="usuario"
                   placeholder="Nickname o correo" value="<%= Html.esc(usuarioValor) %>"
                   autocomplete="username" autocapitalize="none" autocorrect="off" spellcheck="false" required>
            <label for="usuario">Nickname o correo electrónico</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="contrasenia" name="contrasenia"
                   placeholder="Contraseña" autocomplete="current-password" required>
            <label for="contrasenia">Contraseña</label>
        </div>

        <div class="form-check text-start my-3">
            <input class="form-check-input" type="checkbox" value="1" id="recordarme" name="recordarme"
                   <%= recordarMarcado ? "checked" : "" %>>
            <label class="form-check-label" for="recordarme">Recordarme</label>
        </div>

        <button class="btn btn-primary w-100 py-2" type="submit">Ingresar</button>
        <p class="mt-5 mb-3 text-body-secondary text-center small">&copy; 2026 edExt - UTEC Maldonado</p>
    </form>
</main>
</body>
</html>
