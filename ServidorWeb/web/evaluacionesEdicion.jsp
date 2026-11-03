<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="modelo.Curso" %>
<%@ page import="modelo.EdicionCurso" %>
<%@ page import="modelo.DetalleEdicionCurso" %>
<%@ page import="modelo.EvaluacionEstudiante" %>
<%@ page import="util.Html" %>

<%
    List<String> institutos = (List<String>) request.getAttribute("institutos");
    List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
    List<EdicionCurso> ediciones = (List<EdicionCurso>) request.getAttribute("ediciones");
    List<EvaluacionEstudiante> evaluaciones
            = (List<EvaluacionEstudiante>) request.getAttribute("evaluaciones");
    DetalleEdicionCurso detalle
            = (DetalleEdicionCurso) request.getAttribute("detalleEdicion");
    Map<Long, String> ingresadas
            = (Map<Long, String>) request.getAttribute("notasIngresadas");

    String institutoSel = (String) request.getAttribute("institutoSel");
    String cursoSel = (String) request.getAttribute("cursoSel");
    String edicionSel = (String) request.getAttribute("edicionSel");
    boolean cerrada = Boolean.TRUE.equals(request.getAttribute("cerrada"));

    String ctx = request.getContextPath();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Evaluaciones de estudiantes</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Ingresá las notas (1 a 12) de los estudiantes aceptados en las
            ediciones en las que participás. Al cerrar la edición, las notas
            ya no se pueden modificar.
        </p>

        <% if (request.getAttribute("error") != null) {%>
        <div class="alert alert-error" role="alert">
            <%= Html.esc(request.getAttribute("error"))%>
        </div>
        <% } %>

        <% if (request.getAttribute("exito") != null) {%>
        <div class="alert alert-success" role="status">
            <%= Html.esc(request.getAttribute("exito"))%>
        </div>
        <% }%>

        <%-- ===== Selección instituto / curso / edición ===== --%>
        <div class="edext-panel p-4 mb-4">
            <div class="row g-3">

                <div class="col-md-4">
                    <form action="<%= ctx%>/evaluaciones-edicion"
                          method="get" class="filtro-evaluaciones">
                        <label for="institutoEval" class="form-label">Instituto:</label>
                        <select id="institutoEval" name="instituto"
                                class="form-select mb-2" required>
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
                    <form action="<%= ctx%>/evaluaciones-edicion"
                          method="get" class="filtro-evaluaciones">
                        <input type="hidden" name="instituto"
                               value="<%= Html.esc(institutoSel)%>">
                        <label for="cursoEval" class="form-label">Curso:</label>
                        <select id="cursoEval" name="curso"
                                class="form-select mb-2" required>
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
                    <form action="<%= ctx%>/evaluaciones-edicion"
                          method="get" class="filtro-evaluaciones">
                        <input type="hidden" name="instituto"
                               value="<%= Html.esc(institutoSel)%>">
                        <input type="hidden" name="curso"
                               value="<%= Html.esc(cursoSel)%>">
                        <label for="edicionEval" class="form-label">Edición:</label>
                        <select id="edicionEval" name="edicion"
                                class="form-select mb-2" required>
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
                    <p class="text-muted mb-0">No participás en ninguna edición de este curso.</p>
                    <% } %>
                </div>
            </div>
        </div>


        <% if (detalle != null) {%>
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
                    <%= detalle.getFechaInicio() != null
                                ? detalle.getFechaInicio().format(formato) : "—"%>
                </dd>
                <dt class="col-sm-4">Fin</dt>
                <dd class="col-sm-8">
                    <%= detalle.getFechaFin() != null
                                ? detalle.getFechaFin().format(formato) : "—"%>
                </dd>
                <dt class="col-sm-4">Estudiantes aceptados</dt>
                <dd class="col-sm-8">
                    <%= evaluaciones != null ? evaluaciones.size() : 0%>
                </dd>
            </dl>
        </div>


        <% if (evaluaciones == null || evaluaciones.isEmpty()) { %>
        <div class="alert alert-secondary" role="status">
            Esta edición no tiene estudiantes aceptados para evaluar.
        </div>

        <% } else {%>
        <div class="edext-panel p-4">
            <form action="<%= ctx%>/evaluaciones-edicion" method="post"
                  id="formNotas">
                <input type="hidden" name="instituto" value="<%= Html.esc(institutoSel)%>">
                <input type="hidden" name="curso" value="<%= Html.esc(cursoSel)%>">
                <input type="hidden" name="edicion" value="<%= Html.esc(edicionSel)%>">

                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th scope="col">Estudiante</th>
                                <th scope="col">Nickname</th>
                                <th scope="col" style="width: 140px;">Nota (1-12)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (EvaluacionEstudiante ev : evaluaciones) {
                                    // Si hubo error, mostrar lo que escribió; si no, la nota guardada
                                    String valor;
                                    if (ingresadas != null
                                            && ingresadas.containsKey(ev.getIdInscripcion())) {
                                        valor = ingresadas.get(ev.getIdInscripcion());
                                    } else {
                                        valor = ev.getNota() == null ? "" : String.valueOf(ev.getNota());
                                    }
                            %>
                            <tr>
                                <td>
                                    <a href="<%= ctx%>/perfil?user=<%= Html.url(ev.getNicknameEstudiante())%>">
                                        <%= Html.esc(ev.getNombreEstudiante())%>
                                    </a>
                                </td>
                                <td>@<%= Html.esc(ev.getNicknameEstudiante())%></td>
                                <td>
                                    <% if (cerrada) {%>
                                    <strong><%= Html.esc(valor.isEmpty() ? "—" : valor)%></strong>
                                    <% } else {%>
                                    <input type="number"
                                           name="nota_<%= ev.getIdInscripcion()%>"
                                           value="<%= Html.esc(valor)%>"
                                           min="1" max="12" step="1"
                                           class="form-control"
                                           aria-label="Nota de <%= Html.esc(ev.getNombreEstudiante())%>">
                                    <% } %>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>

                <% if (!cerrada) { %>
                <div class="d-flex flex-wrap gap-2">
                    <button type="submit" name="accion" value="guardar"
                            class="btn btn-primary">
                        Guardar notas
                    </button>
                    <button type="submit" name="accion" value="cerrar"
                            class="btn btn-outline-danger" id="btnCerrar">
                        Guardar y cerrar curso
                    </button>
                </div>
                <% } %>
            </form>
        </div>
        <% } %>
        <% }%>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        // Al elegir instituto / curso / edición se envía solo
        document.querySelectorAll(".filtro-evaluaciones select").forEach(selector => {
            selector.addEventListener("change", () => {
                if (selector.value) {
                    selector.form.requestSubmit();
                }
            });
        });

        // Confirmación antes de cerrar, porque no se puede deshacer
        const btnCerrar = document.getElementById("btnCerrar");
        if (btnCerrar) {
            btnCerrar.addEventListener("click", (evento) => {
                const ok = confirm("¿Cerrar la edición? Después no vas a poder modificar las notas.");
                if (!ok) {
                    evento.preventDefault();
                }
            });
        }
    });
</script>

<jsp:include page="footer.jsp" />