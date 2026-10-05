<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<jsp:include page="header.jsp" />

<%
    List<String> institutos = (List<String>) request.getAttribute("institutos");
%>

<div class="auth-box auth-box-large">
    <h2>Registro de Nuevo Usuario</h2>
    <p class="auth-subtitle">Crea tu cuenta como Estudiante o Docente.</p>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/registro" method="post" id="formRegistro" class="row g-3">

        <div class="col-12">
            <label class="form-label d-block">Tipo de Usuario:</label>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="tipoUsuario" id="tipoEstudiante"
                       value="Estudiante" checked onchange="toggleInstituto(false)">
                <label class="form-check-label" for="tipoEstudiante">Estudiante</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="tipoUsuario" id="tipoDocente"
                       value="Docente" onchange="toggleInstituto(true)">
                <label class="form-check-label" for="tipoDocente">Docente</label>
            </div>
        </div>

        <div class="col-md-6">
            <label for="nickname" class="form-label">Nickname (único):</label>
            <input type="text" class="form-control" id="nickname" name="nickname" required placeholder="ej. juanp">
        </div>
        <div class="col-md-6">
            <label for="email" class="form-label">Correo Electrónico (único):</label>
            <input type="email" class="form-control" id="email" name="email" required placeholder="correo@ejemplo.com">
        </div>

        <div class="col-md-6">
            <label for="nombre" class="form-label">Nombre:</label>
            <input type="text" class="form-control" id="nombre" name="nombre" required placeholder="Juan">
        </div>
        <div class="col-md-6">
            <label for="apellido" class="form-label">Apellido:</label>
            <input type="text" class="form-control" id="apellido" name="apellido" required placeholder="Pérez">
        </div>

        <div class="col-md-6">
            <label for="password" class="form-label">Contraseña:</label>
            <input type="password" class="form-control" id="password" name="password" required placeholder="••••••••">
        </div>
        <div class="col-md-6">
            <label for="confirmPassword" class="form-label">Confirmar Contraseña:</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required placeholder="••••••••">
        </div>

        <div class="col-md-6">
            <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento:</label>
            <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento"
                   max="<%= LocalDate.now() %>" required>
            <div class="invalid-feedback" id="errFechaNacimiento"></div>
        </div>
        <div class="col-md-6" id="groupInstituto" style="display: none;">
            <label for="instituto" class="form-label">Instituto (solo Docentes):</label>
            <select class="form-select" id="instituto" name="instituto">
                <% if (institutos != null) {
                    for (String inst : institutos) { %>
                        <option value="<%= inst %>"><%= inst %></option>
                <%  }
                } %>
            </select>
        </div>

        <div class="col-12">
            <button type="submit" class="btn btn-primary w-100">Completar Registro</button>
        </div>
    </form>
</div>

<script>
function toggleInstituto(esDocente) {
    document.getElementById('groupInstituto').style.display = esDocente ? 'block' : 'none';
    document.getElementById('instituto').required = esDocente;
}

document.getElementById('formRegistro').addEventListener('submit', function (ev) {
    var fecha = document.getElementById('fechaNacimiento');
    var hoy = new Date().toISOString().split('T')[0];
    var errFecha = document.getElementById('errFechaNacimiento');

    if (fecha.value && fecha.value > hoy) {
        ev.preventDefault();
        fecha.classList.add('is-invalid');
        errFecha.textContent = 'La fecha de nacimiento no puede ser posterior a la fecha actual.';
        return;
    }
    fecha.classList.remove('is-invalid');

    var password = document.getElementById('password').value;
    var confirmPassword = document.getElementById('confirmPassword').value;
    if (password !== confirmPassword) {
        ev.preventDefault();
        alert('Las contraseñas no coinciden.');
    }
});
</script>

<jsp:include page="footer.jsp" />
