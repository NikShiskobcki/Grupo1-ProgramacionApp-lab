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
    // El campo de URL solo se prellena si la imagen actual es una URL http(s);
    // una imagen subida como archivo vive en una ruta interna (/imagenes/...) que no es editable como URL.
    String vImagenLower = vImagen == null ? "" : vImagen.toLowerCase();
    boolean vImagenEsUrl = vImagenLower.startsWith("http://") || vImagenLower.startsWith("https://");
    String vImagenUrlCampo = vImagenEsUrl ? vImagen : "";
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

        <form action="<%= ctx %>/modificar-perfil" method="post" enctype="multipart/form-data" class="needs-validation" novalidate>            <div class="row g-3">
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
                
                <% String srcActual = Html.imagen(ctx, vImagen); %>
                <% if (!srcActual.isEmpty()) { %>
                <div class="col-12">
                    <label class="form-label d-block">Imagen actual</label>
                    <img src="<%= srcActual %>" alt="Imagen de perfil" class="avatar-sm-ph"
                         style="width:80px;height:80px;object-fit:cover;border-radius:50%;" onerror="this.style.display='none'">
                    <div class="form-check mt-2">
                        <input class="form-check-input" type="checkbox" id="quitarImagen" name="quitarImagen" value="1">
                        <label class="form-check-label" for="quitarImagen">Quitar la imagen actual</label>
                    </div>
                </div>
                <% } %>

                <div class="col-12">
                    <label for="imagenArchivo" class="form-label">Imagen de perfil desde tu PC (opcional):</label>
                    <input type="file" id="imagenArchivo" name="imagenArchivo" class="form-control"
                           accept="image/jpeg,image/png">
                    <small class="text-muted">
                        JPG o PNG, hasta 5 MB. Elegí un archivo o ingresá una URL, no ambas.
                        Dejando todo esto vacío (y sin tildar "Quitar la imagen actual") se conserva la que ya tenés.
                    </small>
                </div>

                <div class="col-12">
                    <label for="imagen" class="form-label">URL de la imagen de perfil (opcional)</label>
                    <input type="url" id="imagen" name="imagen" class="form-control" maxlength="255"
                           placeholder="https://..." value="<%= Html.esc(vImagenUrlCampo) %>">
                    <% if (!vImagenEsUrl && !srcActual.isEmpty()) { %>
                    <small class="text-muted">Tu imagen actual fue subida como archivo; dejá esto vacío para conservarla.</small>
                    <% } %>
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
