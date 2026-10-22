<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleUsuario" %>
<%@ page import="Logica.DTO.ElementoResumen" %>
<%@ page import="Logica.DTO.InscripcionResumen" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="util.Html" %>
<%!
    /** Clase Bootstrap del badge según el estado de la inscripción. */
    private static String claseEstado(String estado) {
        if (estado == null) return "text-bg-secondary";
        switch (estado.toUpperCase()) {
            case "ACEPTADA":   return "text-bg-success";
            case "RECHAZADA":  return "text-bg-danger";
            default:           return "text-bg-warning";
        }
    }
    /** "ACEPTADA" -> "Aceptada". */
    private static String textoEstado(String estado) {
        if (estado == null || estado.isEmpty()) return "";
        return estado.substring(0, 1).toUpperCase() + estado.substring(1).toLowerCase();
    }
%>
<jsp:include page="header.jsp" />

<%
    DetalleUsuario perfil = (DetalleUsuario) request.getAttribute("detalleUsuario");
    Boolean esPropio = (Boolean) request.getAttribute("esPropioPerfil");
    if (esPropio == null) esPropio = false;
    Boolean puedeSeguirAttr = (Boolean) request.getAttribute("puedeSeguir");
    boolean puedeSeguir = puedeSeguirAttr != null && puedeSeguirAttr;
    Boolean siguiendoAttr = (Boolean) request.getAttribute("siguiendo");
    boolean siguiendo = siguiendoAttr != null && siguiendoAttr;
    String ctx = request.getContextPath();

    List<UsuarioResumen> seguidos = (List<UsuarioResumen>) request.getAttribute("seguidos");
    List<UsuarioResumen> seguidores = (List<UsuarioResumen>) request.getAttribute("seguidores");
    int cantSeguidos = (seguidos != null) ? seguidos.size() : 0;
    int cantSeguidores = (seguidores != null) ? seguidores.size() : 0;

    Map<String, String> estados = (Map<String, String>) request.getAttribute("estadosInscripcion");
    Map<String, List<InscripcionResumen>> aceptadosPorEdicion =
            (Map<String, List<InscripcionResumen>>) request.getAttribute("aceptadosPorEdicion");
%>

<% if (perfil != null) {
    boolean docente = "Docente".equalsIgnoreCase(perfil.getTipoUsuario());
    String img = Html.imagen(ctx, perfil.getRutaImagen());
%>
<div class="container-fluid px-0">

    <!-- Carteles que agrega main.js al seguir / dejar de seguir (AJAX) -->
    <div id="alertas-seguir" aria-live="polite"></div>

    <% if ("modificado".equals(request.getParameter("msg"))) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Tus datos se actualizaron correctamente.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
        </div>
    <% } %>

    <% if ("contrasenia".equals(request.getParameter("msg"))) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Tu contraseña se cambió correctamente.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
        </div>
    <% } %>

    <% String msgSeguir = request.getParameter("msg");
       if ("seguido".equals(msgSeguir) || "dejado".equals(msgSeguir)) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <%= "seguido".equals(msgSeguir) ? "Ahora seguís a este usuario." : "Dejaste de seguir a este usuario." %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
        </div>
    <% } else if ("errorseguir".equals(msgSeguir)) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            No se pudo completar la operación. Intentá nuevamente.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
        </div>
    <% } %>

    <!-- Cabecera del perfil -->
    <div class="edext-panel p-4 mb-4">
        <div class="d-flex flex-wrap align-items-center gap-4">
            <% if (!img.isEmpty()) { %>
            <img src="<%= img %>" alt="Imagen de <%= Html.esc(perfil.getNickname()) %>" class="avatar-lg" onerror="imgFallback(this)">
            <div class="avatar-ph" style="display:none;">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                </svg>
            </div>
            <% } else { %>
            <div class="avatar-ph">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                </svg>
            </div>
            <% } %>

            <div class="flex-grow-1">
                <h2 class="mb-1"><%= Html.esc(perfil.getNombre()) %> <%= Html.esc(perfil.getApellido()) %></h2>
                <p class="mb-1 text-muted">
                    @<%= Html.esc(perfil.getNickname()) %>
                    <span class="badge <%= docente ? "text-bg-info" : "text-bg-secondary" %> ms-1"><%= Html.esc(perfil.getTipoUsuario()) %></span>
                </p>
                <p class="mb-1 text-muted">✉ <%= Html.esc(perfil.getEmail()) %></p>
                <% if (docente && perfil.getInstituto() != null && !perfil.getInstituto().isEmpty()) { %>
                    <p class="mb-0 text-muted">Instituto: <strong><%= Html.esc(perfil.getInstituto()) %></strong></p>
                <% } %>
            </div>

            <% if (esPropio) { %>
                <a href="<%= ctx %>/modificar-perfil" class="btn btn-primary">✎ Modificar mis datos</a>
                <a href="<%= ctx %>/cambiar-contrasenia" class="btn btn-outline-secondary">Cambiar contraseña</a>
            <% } %>
            <% if (puedeSeguir) { %>
                <form method="post" action="<%= ctx %>/seguir-usuario" class="m-0" id="formSeguir">
                    <input type="hidden" name="user" value="<%= Html.esc(perfil.getNickname()) %>">
                    <% if (siguiendo) { %>
                        <input type="hidden" name="accion" value="dejar">
                        <button type="submit" id="btnSeguir" class="btn btn-outline-secondary">✖ Dejar de seguir</button>
                    <% } else { %>
                        <input type="hidden" name="accion" value="seguir">
                        <button type="submit" id="btnSeguir" class="btn btn-primary">＋ Seguir</button>
                    <% } %>
                </form>
            <% } %>
        </div>
    </div>

    <!-- Pestañas (Requerimiento Especial 7.3) - Bootstrap Tabs, sin recargar la página -->
    <ul class="nav nav-tabs edext-tabs" id="perfilTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="btn-general" data-bs-toggle="tab" data-bs-target="#tab-general"
                    type="button" role="tab" aria-controls="tab-general" aria-selected="true">General</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="btn-cursos" data-bs-toggle="tab" data-bs-target="#tab-cursos"
                    type="button" role="tab" aria-controls="tab-cursos" aria-selected="false">Cursos</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="btn-programas" data-bs-toggle="tab" data-bs-target="#tab-programas"
                    type="button" role="tab" aria-controls="tab-programas" aria-selected="false">Programas</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="btn-seguidos" data-bs-toggle="tab" data-bs-target="#tab-seguidos"
                    type="button" role="tab" aria-controls="tab-seguidos" aria-selected="false">
                Seguidos <span class="badge text-bg-secondary ms-1" id="cont-seguidos"><%= cantSeguidos %></span>
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="btn-seguidores" data-bs-toggle="tab" data-bs-target="#tab-seguidores"
                    type="button" role="tab" aria-controls="tab-seguidores" aria-selected="false">
                Seguidores <span class="badge text-bg-secondary ms-1" id="cont-seguidores"><%= cantSeguidores %></span>
            </button>
        </li>
    </ul>

    <div class="tab-content edext-panel border-top-0 rounded-top-0 p-4" id="perfilTabsContent">

        <!-- GENERAL -->
        <div class="tab-pane fade show active" id="tab-general" role="tabpanel" aria-labelledby="btn-general" tabindex="0">
            <dl class="row mb-0">
                <dt class="col-sm-4 col-md-3 text-muted">Nickname</dt>
                <dd class="col-sm-8 col-md-9"><%= Html.esc(perfil.getNickname()) %></dd>
                <dt class="col-sm-4 col-md-3 text-muted">Nombre</dt>
                <dd class="col-sm-8 col-md-9"><%= Html.esc(perfil.getNombre()) %></dd>
                <dt class="col-sm-4 col-md-3 text-muted">Apellido</dt>
                <dd class="col-sm-8 col-md-9"><%= Html.esc(perfil.getApellido()) %></dd>
                <dt class="col-sm-4 col-md-3 text-muted">Correo electrónico</dt>
                <dd class="col-sm-8 col-md-9"><%= Html.esc(perfil.getEmail()) %></dd>
                <dt class="col-sm-4 col-md-3 text-muted">Fecha de nacimiento</dt>
                <dd class="col-sm-8 col-md-9"><%= Html.esc(perfil.getFechaNacimiento()) %></dd>
                <dt class="col-sm-4 col-md-3 text-muted">Tipo de usuario</dt>
                <dd class="col-sm-8 col-md-9"><%= Html.esc(perfil.getTipoUsuario()) %></dd>
                <% if (docente) { %>
                    <dt class="col-sm-4 col-md-3 text-muted">Instituto</dt>
                    <dd class="col-sm-8 col-md-9 mb-0"><%= Html.esc(perfil.getInstituto()) %></dd>
                <% } %>
            </dl>
        </div>

        <!-- CURSOS -->
        <div class="tab-pane fade" id="tab-cursos" role="tabpanel" aria-labelledby="btn-cursos" tabindex="0">
            <% if (docente) { %>
                <h5 class="mb-3">Ediciones de cursos en que participa</h5>
                <% if (perfil.getEdiciones() != null && !perfil.getEdiciones().isEmpty()) { %>
                    <div class="list-group mb-4">
                    <% int n = 0;
                       for (ElementoResumen ed : perfil.getEdiciones()) {
                           n++;
                           String collapseId = "aceptados-" + n;
                    %>
                        <div class="list-group-item">
                            <div class="d-flex flex-wrap justify-content-between align-items-center gap-2">
                                <a href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(ed.getNombre()) %>"><%= Html.esc(ed.getTextoMostrar()) %></a>
                                <% if (esPropio) { %>
                                    <button class="btn btn-sm btn-outline-secondary" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#<%= collapseId %>"
                                            aria-expanded="false" aria-controls="<%= collapseId %>">
                                        Ver aceptados
                                    </button>
                                <% } %>
                            </div>
                            <% if (esPropio) {
                                List<InscripcionResumen> acept = (aceptadosPorEdicion != null) ? aceptadosPorEdicion.get(ed.getNombre()) : null; %>
                                <div class="collapse mt-3" id="<%= collapseId %>">
                                    <% if (acept != null && !acept.isEmpty()) { %>
                                        <div class="table-responsive">
                                        <table class="table table-sm table-striped align-middle mb-0">
                                            <thead><tr><th>Estudiante</th><th>Nickname</th><th>Fecha de inscripción</th><th>Estado</th></tr></thead>
                                            <tbody>
                                            <% for (InscripcionResumen i : acept) { %>
                                                <tr>
                                                    <td><%= Html.esc(i.getNombreEstudiante()) %></td>
                                                    <td><a href="<%= ctx %>/perfil?user=<%= Html.url(i.getNicknameEstudiante()) %>">@<%= Html.esc(i.getNicknameEstudiante()) %></a></td>
                                                    <td><%= Html.esc(i.getFechaInscripcion()) %></td>
                                                    <td><span class="badge <%= claseEstado(i.getEstado()) %>"><%= Html.esc(textoEstado(i.getEstado())) %></span></td>
                                                </tr>
                                            <% } %>
                                            </tbody>
                                        </table>
                                        </div>
                                    <% } else { %>
                                        <p class="text-muted mb-0">Todavía no hay estudiantes aceptados en esta edición.</p>
                                    <% } %>
                                </div>
                            <% } %>
                        </div>
                    <% } %>
                    </div>
                <% } else { %>
                    <p class="text-muted">No participa en ninguna edición de curso.</p>
                <% } %>

                <h5 class="mb-3">Cursos asociados</h5>
                <% if (perfil.getCursos() != null && !perfil.getCursos().isEmpty()) { %>
                    <div class="list-group">
                        <% for (ElementoResumen c : perfil.getCursos()) { %>
                            <a class="list-group-item list-group-item-action"
                               href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(c.getNombre()) %>"><%= Html.esc(c.getTextoMostrar()) %></a>
                        <% } %>
                    </div>
                <% } else { %>
                    <p class="text-muted mb-0">No tiene cursos asociados.</p>
                <% } %>

            <% } else { %>
                <h5 class="mb-3">Inscripciones a ediciones de cursos</h5>
                <% if (perfil.getEdiciones() != null && !perfil.getEdiciones().isEmpty()) { %>
                    <div class="list-group mb-4">
                        <% for (ElementoResumen ed : perfil.getEdiciones()) {
                               String est = (esPropio && estados != null) ? estados.get(ed.getNombre()) : null; %>
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(ed.getNombre()) %>">
                                <span><%= Html.esc(ed.getTextoMostrar()) %></span>
                                <% if (est != null) { %>
                                    <span class="badge <%= claseEstado(est) %>"><%= Html.esc(textoEstado(est)) %></span>
                                <% } %>
                            </a>
                        <% } %>
                    </div>
                <% } else { %>
                    <p class="text-muted">No tiene inscripciones vigentes a ediciones de cursos.</p>
                <% } %>

                <% if (esPropio) { %>
                    <h5 class="mb-3">Inscripciones rechazadas</h5>
                    <% if (perfil.getInscripcionesRechazadas() != null && !perfil.getInscripcionesRechazadas().isEmpty()) { %>
                        <div class="list-group">
                            <% for (ElementoResumen ed : perfil.getInscripcionesRechazadas()) { %>
                                <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                                   href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(ed.getNombre()) %>">
                                    <span><%= Html.esc(ed.getTextoMostrar()) %></span>
                                    <span class="badge text-bg-danger">Rechazada</span>
                                </a>
                            <% } %>
                        </div>
                    <% } else { %>
                        <p class="text-muted mb-0">No tenés inscripciones rechazadas.</p>
                    <% } %>
                <% } %>
            <% } %>
        </div>

        <!-- PROGRAMAS -->
        <div class="tab-pane fade" id="tab-programas" role="tabpanel" aria-labelledby="btn-programas" tabindex="0">
            <h5 class="mb-3"><%= docente ? "Programas de formación asociados" : "Programas de formación en los que está inscripto" %></h5>
            <% if (perfil.getProgramas() != null && !perfil.getProgramas().isEmpty()) { %>
                <div class="list-group">
                    <% for (ElementoResumen p : perfil.getProgramas()) { %>
                        <a class="list-group-item list-group-item-action"
                           href="<%= ctx %>/programa-detalle?nombre=<%= Html.url(p.getNombre()) %>"><%= Html.esc(p.getTextoMostrar()) %></a>
                    <% } %>
                </div>
            <% } else { %>
                <p class="text-muted mb-0">No tiene programas de formación asociados.</p>
            <% } %>
        </div>

        <!-- SEGUIDOS Y SEGUIDORES -->
        <%
            String[] idsTab = {"seguidos", "seguidores"};
            List<List<UsuarioResumen>> listasTab = new java.util.ArrayList<>();
            listasTab.add(seguidos);
            listasTab.add(seguidores);
            String[] titulosTab = {
                esPropio ? "Usuarios que seguís" : "Usuarios que sigue",
                esPropio ? "Usuarios que te siguen" : "Usuarios que lo siguen"
            };
            String[] vaciosTab = {
                esPropio ? "Todavía no seguís a ningún usuario." : "Todavía no sigue a ningún usuario.",
                esPropio ? "Todavía no te sigue ningún usuario." : "Todavía no lo sigue ningún usuario."
            };
            for (int k = 0; k < idsTab.length; k++) {
                List<UsuarioResumen> lista = listasTab.get(k);
        %>
        <div class="tab-pane fade" id="tab-<%= idsTab[k] %>" role="tabpanel" aria-labelledby="btn-<%= idsTab[k] %>" tabindex="0">
            <h5 class="mb-3"><%= titulosTab[k] %></h5>
            <% boolean hayLista = lista != null && !lista.isEmpty(); %>
                <div class="row g-3<%= hayLista ? "" : " d-none" %>" id="lista-<%= idsTab[k] %>">
                    <% if (hayLista) for (UsuarioResumen u : lista) {
                           String imgU = Html.imagen(ctx, u.getRutaImagen());
                           boolean docenteU = "Docente".equalsIgnoreCase(u.getTipo());
                    %>
                    <div class="col-12 col-md-6" data-nick="<%= Html.esc(u.getNickname()) %>">
                        <a href="<%= ctx %>/perfil?user=<%= Html.url(u.getNickname()) %>"
                           class="edext-panel p-3 d-flex align-items-center gap-3 text-decoration-none h-100 text-reset">
                            <% if (!imgU.isEmpty()) { %>
                            <img src="<%= imgU %>" alt="" class="avatar-sm" onerror="imgFallback(this)">
                                <span class="avatar-sm-ph" style="display:none;">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                                    </svg>
                                </span>
                                <% } else { %>
                                <span class="avatar-sm-ph">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                                    </svg>
                                </span>
                                <% } %>
                                <div class="flex-grow-1 text-truncate">
                                    <div class="fw-semibold text-truncate"><%= Html.esc(u.getNombreCompleto()) %></div>
                                    <div class="small text-muted">@<%= Html.esc(u.getNickname()) %></div>
                                </div>
                                <span class="badge <%= docenteU ? "text-bg-info" : "text-bg-secondary" %>"><%= Html.esc(u.getTipo()) %></span>
                        </a>
                    </div>  
                    <% } %>
                </div>
                <p class="text-muted mb-0<%= hayLista ? " d-none" : "" %>" id="vacio-<%= idsTab[k] %>"><%= vaciosTab[k] %></p>
        </div>
        <% } %>
    </div>
</div>
<% } else { %>
    <div class="alert alert-secondary">Perfil de usuario no disponible.</div>
<% } %>

<jsp:include page="footer.jsp" />
