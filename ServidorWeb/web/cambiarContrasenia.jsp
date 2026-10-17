<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    String ctx = request.getContextPath();
%>

<div class="container" style="max-width: 560px;">
    <div class="edext-panel p-4">
        <h2 class="mb-1">Cambiar contraseña</h2>
        <p class="text-muted">Ingresá tu contraseña actual y la nueva contraseña, y confirmala para guardar el cambio.</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert"><%= Html.esc(request.getAttribute("error")) %></div>
        <% } %>

        <form action="<%= ctx %>/cambiar-contrasenia" method="post" class="needs-validation" novalidate>
            <div class="row g-3">
                <div class="col-12">
                    <label for="contraseniaActual" class="form-label">Contraseña actual</label>
                    <input type="password" id="contraseniaActual" name="contraseniaActual" class="form-control"
                           required autocomplete="current-password" placeholder="••••••••">
                    <div class="invalid-feedback">Ingresá tu contraseña actual.</div>
                </div>

                <div class="col-12">
                    <label for="contraseniaNueva" class="form-label">Nueva contraseña</label>
                    <input type="password" id="contraseniaNueva" name="contraseniaNueva" class="form-control"
                           required autocomplete="new-password" placeholder="••••••••">
                    <div class="invalid-feedback">Ingresá la nueva contraseña.</div>
                </div>

                <div class="col-12">
                    <label for="confirmarContrasenia" class="form-label">Confirmar nueva contraseña</label>
                    <input type="password" id="confirmarContrasenia" name="confirmarContrasenia" class="form-control"
                           required autocomplete="new-password" placeholder="••••••••">
                    <div class="invalid-feedback" id="msgConfirmar">Confirmá la nueva contraseña.</div>
                </div>
            </div>

            <div class="d-flex gap-2 justify-content-end mt-4">
                <a href="<%= ctx %>/perfil" class="btn btn-outline-secondary">Cancelar</a>
                <button type="submit" class="btn btn-primary">Guardar contraseña</button>
            </div>
        </form>
    </div>
</div>

<script>
    // La confirmación debe coincidir con la nueva contraseña (se valida antes de enviar)
    (function () {
        var nueva = document.getElementById('contraseniaNueva');
        var conf = document.getElementById('confirmarContrasenia');
        var msg = document.getElementById('msgConfirmar');
        function validar() {
            if (conf.value !== '' && conf.value !== nueva.value) {
                conf.setCustomValidity('no coincide');
                msg.textContent = 'La confirmación no coincide con la nueva contraseña.';
            } else {
                conf.setCustomValidity('');
                msg.textContent = 'Confirmá la nueva contraseña.';
            }
        }
        nueva.addEventListener('input', validar);
        conf.addEventListener('input', validar);
    })();
</script>

<jsp:include page="footer.jsp" />
