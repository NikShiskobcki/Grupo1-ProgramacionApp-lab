<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Logica.DTO.DetalleEdicionCurso" %>
<%@ page import="Logica.DTO.InscripcionResumen" %>
<%@ page import="util.Html" %>

<%
    DetalleEdicionCurso ed =
            (DetalleEdicionCurso) request.getAttribute("detalleEdicion");

    boolean puedeVerAceptados =
            Boolean.TRUE.equals(request.getAttribute("puedeVerAceptados"));

    List<InscripcionResumen> aceptados =
            (List<InscripcionResumen>) request.getAttribute("aceptados");

    String ctx = request.getContextPath();
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <% if (ed != null) {
            String img = Html.imagen(ctx, ed.getRutaImagen());
        %>

        <div class="container-fluid px-0">
            <div class="edext-panel p-4">
                <div class="row g-4">

                    <% if (!img.isEmpty()) { %>
                    <div class="col-md-4">
                        <img src="<%= img %>"
                             alt="<%= Html.esc(ed.getNombre()) %>"
                             class="img-fluid rounded detail-img-aside"
                             onerror="this.style.display='none'">
                    </div>
                    <% } %>

                    <div class="<%= img.isEmpty() ? "col-12" : "col-md-8" %>">
                        <h2 class="mb-1">
                            <%= Html.esc(ed.getNombre()) %>
                        </h2>

                        <p class="text-muted">
                            Edición del curso
                            <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(ed.getCurso()) %>">
                                <%= Html.esc(ed.getCurso()) %>
                            </a>
                        </p>

                        <dl class="row mb-3">
                            <dt class="col-sm-4 text-muted">
                                Fecha de inicio
                            </dt>
                            <dd class="col-sm-8">
                                <%= Html.esc(ed.getFechaInicio()) %>
                            </dd>

                            <dt class="col-sm-4 text-muted">
                                Fecha de fin
                            </dt>
                            <dd class="col-sm-8">
                                <%= Html.esc(ed.getFechaFin()) %>
                            </dd>

                            <dt class="col-sm-4 text-muted">
                                Cupo
                            </dt>
                            <dd class="col-sm-8">
                                <%= ed.getCupo() != null
                                        ? ed.getCupo()
                                        : "Sin cupo definido" %>
                            </dd>

                            <dt class="col-sm-4 text-muted">
                                Fecha de publicación
                            </dt>
                            <dd class="col-sm-8">
                                <%= Html.esc(ed.getFechaPublicacion()) %>
                            </dd>

                            <dt class="col-sm-4 text-muted">
                                Inscriptos
                            </dt>
                            <dd class="col-sm-8">
                                <%= ed.getCantidadInscriptos() %>
                            </dd>
                        </dl>

                        <h5>Docentes</h5>

                        <% if (ed.getDocentes() != null
                                && !ed.getDocentes().isEmpty()) { %>

                        <ul class="list-unstyled">
                            <% for (String docente : ed.getDocentes()) {
                                String nick = Html.nicknameDeDocente(docente);
                            %>
                            <li>
                                <% if (nick != null) { %>
                                <a href="<%= ctx %>/perfil?user=<%= Html.url(nick) %>">
                                    <%= Html.esc(docente) %>
                                </a>
                                <% } else { %>
                                    <%= Html.esc(docente) %>
                                <% } %>
                            </li>
                            <% } %>
                        </ul>

                        <% } else { %>
                        <p class="text-muted">Sin docentes asignados.</p>
                        <% } %>

                        <% if (ed.getCategorias() != null
                                && !ed.getCategorias().isEmpty()) { %>

                        <h5>Categorías</h5>
                        <p>
                            <% for (String categoria : ed.getCategorias()) { %>
                            <span class="badge text-bg-light border">
                                <%= Html.esc(categoria) %>
                            </span>
                            <% } %>
                        </p>

                        <% } %>
                    </div>
                </div>

                <% if (puedeVerAceptados) { %>
                <section class="mt-4 pt-4 border-top"
                         aria-labelledby="tituloAceptados">

                    <h5 id="tituloAceptados">
                        Estudiantes aceptados
                        <span class="badge text-bg-secondary">
                            <%= aceptados == null ? 0 : aceptados.size() %>
                        </span>
                    </h5>

                    <% if (aceptados == null || aceptados.isEmpty()) { %>

                    <p class="text-muted mb-0">
                        Todavía no hay estudiantes aceptados en esta edición.
                    </p>

                    <% } else { %>

                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr>
                                    <th scope="col">Estudiante</th>
                                    <th scope="col">Nickname</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (InscripcionResumen estudiante : aceptados) { %>
                                <tr>
                                    <td>
                                        <a href="<%= ctx %>/perfil?user=<%= Html.url(estudiante.getNicknameEstudiante()) %>">
                                            <%= Html.esc(estudiante.getNombreEstudiante()) %>
                                        </a>
                                    </td>
                                    <td>
                                        <%= Html.esc(estudiante.getNicknameEstudiante()) %>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <% } %>
                </section>
                <% } %>
            </div>
        </div>

        <% } else { %>
        <div class="alert alert-secondary">
            Edición no encontrada.
        </div>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />
