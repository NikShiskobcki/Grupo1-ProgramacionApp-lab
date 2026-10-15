<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.UsuarioEdicion" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    UsuarioEdicion u = (UsuarioEdicion) request.getAttribute("usuarioEdicion");
    List<String> institutos = (List<String>) request.getAttribute("institutos");
    String ctx = request.getContextPath();

    // Si hubo un error se conservan los valores escritos; si no, los actuales del usuario
    String vNombre   = request.getAttribute("v_nombre")   != null ? (String) request.getAttribute("v_nombre")   : (u != null ? u.getNombre() : "");
    String vApellido = request.getAttribute("v_apellido") != null ? (String) request.getAttribute("v_apellido") : (u != null ? u.getApellido() : "");
    String vFecha    = request.getAttribute("v_fecha")    != null ? (String) request.getAttribute("v_fecha")    : (u != null && u.getFechaNacimiento() != null ? u.getFechaNacimiento().toString() : "");
    String vImagen   = request.getAttribute("v_imagen")   != null ? (String) request.getAttribute("v_imagen")   : (u != null && u.getRutaImagen() != null ? u.getRutaImagen() : "");
    String vInst     = request.getAttribute("v_instituto")!= null ? (String) request.getAttribute("v_instituto"): (u != null ? u.getInstituto() : "");
%>

<% if (u != null) {
    boolean docente = "Docente".equalsIgnoreCase(u.getTipoUsuario());
%>
<div class="container" style="max-width: 760px;">
    <div class="edext-panel p-4">
        <h2 class="mb-1">Modificar mis datos</h2>
        <p class="text-muted">Podés editar tus datos básicos. El nickname y el correo electrónico no se pueden modificar.</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert"><%= Html.esc(request.getAttribute("error")) %></div>
        <% } %>

        <form action="<%= ctx %>/modificar-perfil" method="post" class="needs-validation" novalidate>
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="nickname" class="form-label">Nickname</label>
                    <input type="text" id="nickname" class="form-control" value="<%= Html.esc(u.getNickname()) %>" disabled>
                </div>
                <div class="col-md-6">
                    <label for="email" class="form-label">Correo electrónico</label>
                    <input type="email" id="email" class="form-control" value="<%= Html.esc(u.getEmail()) %>" disabled>
                </div>

                <div class="col-md-6">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" id="nombre" name="nombre" class="form-control" required maxlength="100"
                           value="<%= Html.esc(vNombre) %>">
                    <div class="invalid-feedback">Ingresá tu nombre.</div>
                </div>
                <div class="col-md-6">
                    <label for="apellido" class="form-label">Apellido</label>
                    <input type="text" id="apellido" name="apellido" class="form-control" required maxlength="100"
                           value="<%= Html.esc(vApellido) %>">
                    <div class="invalid-feedback">Ingresá tu apellido.</div>
                </div>

                <div class="col-md-6">
                    <label for="fechaNacimiento" class="form-label">Fecha de nacimiento</label>
                    <input type="date" id="fechaNacimiento" name="fechaNacimiento" class="form-control" required
                           max="<%= LocalDate.now() %>" value="<%= Html.esc(vFecha) %>">
                    <div class="invalid-feedback">Indicá una fecha de nacimiento válida (no futura).</div>
                </div>

                <% if (docente) { %>
                <div class="col-md-6">
                    <label for="instituto" class="form-label">Instituto</label>
                    <select id="instituto" name="instituto" class="form-select" required>
                        <% if (institutos != null) {
                               for (String inst : institutos) { %>
                            <option value="<%= Html.esc(inst) %>" <%= inst.equals(vInst) ? "selected" : "" %>><%= Html.esc(inst) %></option>
                        <%     }
                           } %>
                    </select>
                    <div class="invalid-feedback">Seleccioná un instituto.</div>
                </div>
                <% } %>

                <div class="col-12">
                    <label for="rutaImagen" class="form-label">Imagen de perfil <span class="text-muted">(URL o ruta, opcional)</span></label>
                    <input type="text" id="rutaImagen" name="rutaImagen" class="form-control" maxlength="255"
                           placeholder="https://..." value="<%= Html.esc(vImagen) %>">
                </div>
            </div>

            <div class="d-flex gap-2 justify-content-end mt-4">
                <a href="<%= ctx %>/perfil" class="btn btn-outline-secondary">Cancelar</a>
                <button type="submit" class="btn btn-primary">Guardar cambios</button>
            </div>
        </form>
    </div>
</div>
<% } else { %>
    <div class="alert alert-secondary">No se pudo cargar el usuario.</div>
<% } %>

<jsp:include page="footer.jsp" />
