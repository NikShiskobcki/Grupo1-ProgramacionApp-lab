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

    <form action="<%= request.getContextPath() %>/registro" method="post" id="formRegistro"
          enctype="multipart/form-data" class="row g-3">
        
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
            <input type="text" class="form-control" id="nickname" name="nickname" required
                   minlength="3" maxlength="30" pattern="[A-Za-z0-9_.\-]+"
                   title="Solo letras, números, punto, guion y guion bajo." placeholder="ej. juanp"
                   autocomplete="off">
            <small class="form-text" id="fbNickname"></small>
        </div>
        <div class="col-md-6">
            <label for="email" class="form-label">Correo Electrónico (único):</label>
            <input type="email" class="form-control" id="email" name="email" required
                   placeholder="correo@ejemplo.com" autocomplete="off">
            <small class="form-text" id="fbEmail"></small>
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
                <label for="imagenArchivo" class="form-label">Imagen de perfil desde tu PC (opcional):</label>
                <input type="file" class="form-control" id="imagenArchivo" name="imagenArchivo"
                       accept="image/jpeg,image/png">
                <small class="text-muted">
                    JPG o PNG, hasta 5 MB. Elegí un archivo o ingresá una URL, no ambas.
                    Si el formulario vuelve con un error, seleccioná el archivo nuevamente.
                </small>
            </div>

            <div class="col-12">
                <label for="imagen" class="form-label">URL de la imagen de perfil (opcional):</label>
                <input type="url" class="form-control" id="imagen" name="imagen" placeholder="https://...">
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

// ---- Requerimiento 7.5: verificación en segundo plano (AJAX) de nickname/email ----
// Estado de disponibilidad por campo: true = disponible, false = ocupado, null = sin verificar todavía.
var disponibilidad = { nickname: null, email: null };

function verificarDisponibilidad(campo, input, feedback) {
    var valor = input.value.trim();
    feedback.classList.remove('text-success', 'text-danger', 'text-muted');

    if (!valor || (campo === 'nickname' && valor.length < 3)) {
        disponibilidad[campo] = null;
        feedback.textContent = '';
        input.classList.remove('is-invalid', 'is-valid');
        return;
    }

    feedback.textContent = 'Verificando...';
    feedback.classList.add('text-muted');

    var ctx = '<%= request.getContextPath() %>';
    fetch(ctx + '/verificar-disponibilidad?campo=' + campo + '&valor=' + encodeURIComponent(valor),
          { headers: { 'Accept': 'application/json' } })
        .then(function (r) { if (!r.ok) throw new Error(r.status); return r.json(); })
        .then(function (data) {
            // Si el usuario siguió escribiendo mientras esperábamos la respuesta, se ignora (está desactualizada).
            if (input.value.trim() !== valor) return;

            disponibilidad[campo] = !!data.disponible;
            feedback.classList.remove('text-muted');
            if (data.disponible) {
                feedback.textContent = campo === 'nickname' ? 'Nickname disponible.' : 'Correo disponible.';
                feedback.classList.add('text-success');
                input.classList.remove('is-invalid');
                input.classList.add('is-valid');
            } else {
                feedback.textContent = campo === 'nickname'
                    ? 'Ese nickname ya está en uso. Probá con otro, por ejemplo "' + valor + Math.floor(Math.random() * 90 + 10) + '".'
                    : 'Ya existe una cuenta registrada con ese correo.';
                feedback.classList.add('text-danger');
                input.classList.remove('is-valid');
                input.classList.add('is-invalid');
            }
        })
        .catch(function () {
            // Si falla la verificación en segundo plano, no se bloquea el envío: la validación
            // definitiva la hace igual el servidor al enviar el formulario.
            disponibilidad[campo] = null;
            feedback.textContent = '';
            feedback.classList.remove('text-muted');
        });
}

(function () {
    var timers = { nickname: null, email: null };
    [['nickname', 'fbNickname'], ['email', 'fbEmail']].forEach(function (par) {
        var campo = par[0], input = document.getElementById(campo), feedback = document.getElementById(par[1]);
        input.addEventListener('input', function () {
            clearTimeout(timers[campo]);
            timers[campo] = setTimeout(function () { verificarDisponibilidad(campo, input, feedback); }, 400);
        });
        input.addEventListener('blur', function () {
            clearTimeout(timers[campo]);
            verificarDisponibilidad(campo, input, feedback);
        });
    });
})();

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
        return;
    }

    // No se bloquea el envío si todavía no se sabe (null): el servidor valida igual.
    if (disponibilidad.nickname === false) {
        ev.preventDefault();
        document.getElementById('nickname').classList.add('is-invalid');
        document.getElementById('nickname').focus();
        return;
    }
    if (disponibilidad.email === false) {
        ev.preventDefault();
        document.getElementById('email').classList.add('is-invalid');
        document.getElementById('email').focus();
    }
});
</script>

<jsp:include page="footer.jsp" />
