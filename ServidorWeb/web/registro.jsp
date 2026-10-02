<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
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

    <form action="<%= request.getContextPath() %>/registro" method="post" class="form-vertical" id="formRegistro">
        <div class="form-group">
            <label>Tipo de Usuario:</label>
            <div class="radio-group">
                <label><input type="radio" name="tipoUsuario" value="Estudiante" checked onchange="toggleInstituto(false)"> Estudiante</label>
                <label><input type="radio" name="tipoUsuario" value="Docente" onchange="toggleInstituto(true)"> Docente</label>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="nickname">Nickname (único):</label>
                <input type="text" id="nickname" name="nickname" required placeholder="ej. juanp">
            </div>
            <div class="form-group">
                <label for="email">Correo Electrónico (único):</label>
                <input type="email" id="email" name="email" required placeholder="correo@ejemplo.com">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required placeholder="Juan">
            </div>
            <div class="form-group">
                <label for="apellido">Apellido:</label>
                <input type="text" id="apellido" name="apellido" required placeholder="Pérez">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required placeholder="••••••••">
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirmar Contraseña:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="••••••••">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
            </div>
            <div class="form-group" id="groupInstituto" style="display: none;">
                <label for="instituto">Instituto (solo Docentes):</label>
                <select id="instituto" name="instituto">
                    <% if (institutos != null) { 
                        for (String inst : institutos) { %>
                            <option value="<%= inst %>"><%= inst %></option>
                    <%  } 
                    } %>
                </select>
            </div>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Completar Registro</button>
    </form>
</div>

<script>
function toggleInstituto(esDocente) {
    document.getElementById('groupInstituto').style.display = esDocente ? 'block' : 'none';
}
</script>

<jsp:include page="footer.jsp" />
