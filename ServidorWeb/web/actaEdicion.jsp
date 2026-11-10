<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="modelo.Curso" %>
<%@ page import="modelo.EdicionCurso" %>
<%@ page import="modelo.DetalleEdicionCurso" %>
<%@ page import="modelo.EvaluacionEstudiante" %>
<%@ page import="util.ActaEstudio" %>
<%@ page import="util.Html" %>

<%
    List<String> institutos = (List<String>) request.getAttribute("institutos");
    List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
    List<EdicionCurso> ediciones = (List<EdicionCurso>) request.getAttribute("ediciones");
    Set<String> edicionesCerradas = (Set<String>) request.getAttribute("edicionesCerradas");
    List<EvaluacionEstudiante> evaluaciones
            = (List<EvaluacionEstudiante>) request.getAttribute("evaluaciones");
    DetalleEdicionCurso detalle = (DetalleEdicionCurso) request.getAttribute("detalleEdicion");

    String institutoSel = (String) request.getAttribute("institutoSel");
    String cursoSel = (String) request.getAttribute("cursoSel");
    String edicionSel = (String) request.getAttribute("edicionSel");
    boolean cerrada = Boolean.TRUE.equals(request.getAttribute("cerrada"));
    Integer aprobados = (Integer) request.getAttribute("aprobados");
    Double promedio = (Double) request.getAttribute("promedio");

    String ctx = request.getContextPath();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Resultados de evaluaciones</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Consultá el acta de las ediciones cerradas en las que participás.
        </p>

        <% if (request.getAttribute("error") != null) {%>
        <div class="alert alert-danger" role="alert">
            <%= Html.esc(request.getAttribute("error"))%>
        </div>
        <% }%>

        <%-- ===== Selección instituto / curso / edición ===== --%>
        <div class="edext-panel p-4 mb-4">
            <div class="row g-3">

                <div class="col-md-4">
                    <form action="<%= ctx%>/acta-edicion" method="get" class="filtro-acta">
                        <label for="institutoActa" class="form-label">Instituto:</label>
                        <select id="institutoActa" name="instituto" class="form-select mb-2" required>
                            <option value="">Seleccioná un instituto</option>
                            <% if (institutos != null) {
                                    for (String instituto : institutos) {%>
                            <option value="<%= Html.esc(instituto)%>"
                                    <%= instituto.equals(institutoSel) ? "selected" : ""%>>
                                <%= Html.esc(instituto)%>
                            </option>
                            <%  }
                                } %>
                        </select>
                    </form>
                </div>

                <div class="col-md-4">
                    <% if (cursos != null && !cursos.isEmpty()) {%>
                    <form action="<%= ctx%>/acta-edicion" method="get" class="filtro-acta">
                        <input type="hidden" name="instituto" value="<%= Html.esc(institutoSel)%>">
                        <label for="cursoActa" class="form-label">Curso:</label>
                        <select id="cursoActa" name="curso" class="form-select mb-2" required>
                            <option value="">Seleccioná un curso</option>
                            <% for (Curso curso : cursos) {%>
                            <option value="<%= Html.esc(curso.getNombre())%>"
                                    <%= curso.getNombre().equals(cursoSel) ? "selected" : ""%>>
                                <%= Html.esc(curso.getNombre())%>
                            </option>
                            <% } %>
                        </select>
                    </form>
                    <% } else if (institutoSel != null && !institutoSel.isEmpty()) { %>
                    <p class="text-muted mb-0">No hay cursos asociados a este instituto.</p>
                    <% } %>
                </div>

                <div class="col-md-4">
                    <% if (ediciones != null && !ediciones.isEmpty()) {%>
                    <form action="<%= ctx%>/acta-edicion" method="get" class="filtro-acta">
                        <input type="hidden" name="instituto" value="<%= Html.esc(institutoSel)%>">
                        <input type="hidden" name="curso" value="<%= Html.esc(cursoSel)%>">
                        <label for="edicionActa" class="form-label">Edición:</label>
                        <select id="edicionActa" name="edicion" class="form-select mb-2" required>
                            <option value="">Seleccioná una edición</option>
                            <% for (EdicionCurso edicion : ediciones) {%>
                            <option value="<%= Html.esc(edicion.getNombre())%>"
                                    <%= edicion.getNombre().equals(edicionSel) ? "selected" : ""%>>
                                <%= Html.esc(edicion.getNombre())%>
                            </option>
                            <% } %>
                        </select>
                    </form>
                    <% } else if (cursoSel != null && !cursoSel.isEmpty()) { %>
                    <p class="text-muted mb-0">No hay ediciones vigentes y cerradas de este curso en las que participes.</p>
                    <% } %>
                </div>
            </div>
        </div>

        <% if (detalle != null) {%>
        <%-- ===== Datos básicos de la edición ===== --%>
        <div class="edext-panel p-4 mb-4">
            <h3>
                <%= Html.esc(detalle.getNombre())%>
                <% if (cerrada) { %>
                <span class="badge text-bg-secondary">Cerrada</span>
                <% } else { %>
                <span class="badge text-bg-success">Abierta</span>
                <% }%>
            </h3>
            <p class="text-muted">Curso: <%= Html.esc(detalle.getCurso())%></p>

            <dl class="row mb-0">
                <dt class="col-sm-4">Inicio</dt>
                <dd class="col-sm-8">
                    <%= detalle.getFechaInicio() != null ? detalle.getFechaInicio().format(formato) : "—"%>
                </dd>
                <dt class="col-sm-4">Fin</dt>
                <dd class="col-sm-8">
                    <%= detalle.getFechaFin() != null ? detalle.getFechaFin().format(formato) : "—"%>
                </dd>
                <dt class="col-sm-4">Fecha de publicación</dt>
                <dd class="col-sm-8">
                    <%= detalle.getFechaPublicacion() != null
                            ? detalle.getFechaPublicacion().format(formato) : "—"%>
                </dd>

                <dt class="col-sm-4">Cupo</dt>
                <dd class="col-sm-8">
                    <%= detalle.getCupo() != null ? detalle.getCupo().toString() : "—"%>
                </dd>
                <dt class="col-sm-4">Docentes</dt>
                <dd class="col-sm-8">
                    <%= detalle.getDocentes() == null || detalle.getDocentes().isEmpty()
                            ? "—" : Html.esc(String.join(", ", detalle.getDocentes()))%>
                </dd>
            </dl>
        </div>

        <% if (!cerrada) {%>
        <div class="alert alert-warning" role="status">
            Esta edición todavía no está cerrada. El acta solo se puede consultar
            cuando se cierra la edición.
            <a href="<%= ctx%>/evaluaciones-edicion?edicion=<%= Html.url(edicionSel)%>"
               class="alert-link">Ir a ingresar evaluaciones</a>
        </div>

        <% } else if (evaluaciones == null || evaluaciones.isEmpty()) { %>
        <div class="alert alert-secondary" role="status">
            Esta edición no tiene estudiantes participantes.
        </div>

        <% } else {%>
        <%-- ===== Acta ===== --%>
        <div class="edext-panel p-4">
            <div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-3">
                <h4 class="mb-0">Acta de la edición</h4>
                <a class="btn btn-outline-secondary btn-sm"
                   href="<%= ctx%>/acta-edicion-pdf?edicion=<%= Html.url(edicionSel)%>">
                    Descargar acta (PDF)
                </a>
            </div>

            <div class="row g-3 mb-4 text-center">
                <div class="col-4">
                    <div class="border rounded p-2">
                        <div class="fs-4 fw-bold"><%= evaluaciones.size()%></div>
                        <div class="small text-muted">Participantes</div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="border rounded p-2">
                        <div class="fs-4 fw-bold"><%= aprobados == null ? 0 : aprobados%></div>
                        <div class="small text-muted">Aprobados</div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="border rounded p-2">
                        <div class="fs-4 fw-bold">
                            <%= promedio == null ? "—" : String.format(java.util.Locale.US, "%.1f", promedio)%>
                        </div>
                        <div class="small text-muted">Nota promedio</div>
                    </div>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-striped align-middle">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Estudiante</th>
                            <th scope="col">Nickname</th>
                            <th scope="col" class="text-center">Nota (1-12)</th>
                            <th scope="col">Resultado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% int n = 0;
                            for (EvaluacionEstudiante ev : evaluaciones) {
                                n++;%>
                        <tr>
                            <td><%= n%></td>
                            <td><%= Html.esc(ev.getNombreEstudiante())%></td>
                            <td><%= Html.esc(ev.getNicknameEstudiante())%></td>
                            <td class="text-center"><%= ev.getNota() == null ? "—" : ev.getNota()%></td>
                            <td>
                                <% if (ev.getNota() == null) { %>
                                <span class="badge text-bg-secondary">Sin nota</span>
                                <% } else if (ev.getNota() >= ActaEstudio.NOTA_APROBACION) { %>
                                <span class="badge text-bg-success">Aprobado</span>
                                <% } else { %>
                                <span class="badge text-bg-danger">No aprobado</span>
                                <% }%>
                            </td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>

            <p class="text-muted small mb-0">
                Se considera aprobado un curso con nota
                <%= ActaEstudio.NOTA_APROBACION%> o más (escala de 1 a 12).
            </p>
        </div>
        <% } %>
        <% }%>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        // Al elegir instituto / curso / edición se envía solo
        document.querySelectorAll(".filtro-acta select").forEach(selector => {
            selector.addEventListener("change", () => {
                if (selector.value) {
                    selector.form.requestSubmit();
                }
            });
        });
    });
</script>

<jsp:include page="footer.jsp" />
