<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleEdicionCurso" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="util.Html" %>

<%
    DetalleEdicionCurso ed =
            (DetalleEdicionCurso) request.getAttribute("detalleEdicion");

    String ctx = request.getContextPath();

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <% if (ed != null) {
            String img = Html.imagen(ctx, ed.getRutaImagen());
        %>
            <div class="edext-panel p-4">
                <h2 class="mb-2"><%= Html.esc(ed.getNombre()) %></h2>

                <p class="text-muted">
                    Edición del curso
                    <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(ed.getCurso()) %>">
                        <%= Html.esc(ed.getCurso()) %>
                    </a>
                </p>

                <div class="row g-4">
                    <% if (!img.isEmpty()) { %>
                        <div class="col-md-4">
                            <img src="<%= img %>"
                                 alt="<%= Html.esc(ed.getNombre()) %>"
                                 class="img-fluid rounded"
                                 onerror="this.hidden=true; this.nextElementSibling.hidden=false;">

                            <p class="text-muted small" hidden>
                                La imagen no está disponible.
                            </p>
                        </div>
                    <% } %>

                    <div class="<%= img.isEmpty() ? "col-12" : "col-md-8" %>">
                        <dl class="row mb-3">
                            <dt class="col-sm-5 text-muted">
                                Fecha de inicio
                            </dt>
                            <dd class="col-sm-7">
                                <%= ed.getFechaInicio() != null
                                        ? ed.getFechaInicio().format(formato) : "—" %>
                            </dd>

                            <dt class="col-sm-5 text-muted">
                                Fecha de fin
                            </dt>
                            <dd class="col-sm-7">
                                <%= ed.getFechaFin() != null
                                        ? ed.getFechaFin().format(formato) : "—" %>
                            </dd>

                            <dt class="col-sm-5 text-muted">
                                Cupo
                            </dt>
                            <dd class="col-sm-7">
                                <%= ed.getCupo() != null
                                        ? ed.getCupo() : "Sin cupo definido" %>
                            </dd>

                            <dt class="col-sm-5 text-muted">
                                Fecha de publicación
                            </dt>
                            <dd class="col-sm-7">
                                <%= ed.getFechaPublicacion() != null
                                        ? ed.getFechaPublicacion().format(formato) : "—" %>
                            </dd>

                            <dt class="col-sm-5 text-muted">
                                Cantidad de inscriptos
                            </dt>
                            <dd class="col-sm-7">
                                <%= ed.getCantidadInscriptos() %>
                            </dd>
                        </dl>

                        <h5>Docentes participantes</h5>

                        <% if (ed.getDocentes() != null
                                && !ed.getDocentes().isEmpty()) { %>
                            <ul class="list-unstyled">
                                <% for (String docente : ed.getDocentes()) {
                                    String nick =
                                            Html.nicknameDeDocente(docente);
                                %>
                                    <li class="mb-1">
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
                            <p class="text-muted">
                                Sin docentes asignados.
                            </p>
                        <% } %>

                        <% if (ed.getCategorias() != null
                                && !ed.getCategorias().isEmpty()) { %>
                            <h5>Categorías</h5>

                            <div class="d-flex flex-wrap gap-2">
                                <% for (String categoria : ed.getCategorias()) { %>
                                    <a class="badge text-bg-light border text-decoration-none"
                                       href="<%= ctx %>/consulta-curso?categoria=<%= Html.url(categoria) %>">
                                        <%= Html.esc(categoria) %>
                                    </a>
                                <% } %>
                            </div>
                        <% } %>
                    </div>
                </div>

                <div class="mt-4">
                    <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(ed.getCurso()) %>"
                       class="btn btn-primary">
                        Volver al curso
                    </a>
                </div>
            </div>
        <% } else { %>
            <div class="alert alert-secondary" role="alert">
                Edición no encontrada.
            </div>

            <a href="<%= ctx %>/consulta-curso" class="btn btn-primary">
                Consultar cursos
            </a>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />