<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Logica.Entidades.Curso" %>
<%@ page import="Logica.Entidades.EdicionCurso" %>
<%@ page import="Logica.Entidades.InscripcionEdicion" %>
<%@ page import="Logica.Entidades.EstadoInscripcion" %>
<%@ page import="Logica.DTO.DetalleEdicionCurso" %>
<%@ page import="util.Html" %>

<%
    List<String> institutos =
            (List<String>) request.getAttribute("institutos");

    List<String> categorias =
            (List<String>) request.getAttribute("categorias");

    List<Curso> cursos =
            (List<Curso>) request.getAttribute("cursos");

    List<EdicionCurso> vigentes =
            (List<EdicionCurso>) request.getAttribute("edicionesVigentes");

    DetalleEdicionCurso detalle =
            (DetalleEdicionCurso) request.getAttribute("detalleEdicion");

    InscripcionEdicion inscripcion =
            (InscripcionEdicion) request.getAttribute("inscripcion");

    String institutoSel =
            (String) request.getAttribute("institutoSel");

    String categoriaSel =
            (String) request.getAttribute("categoriaSel");

    String cursoSel =
            (String) request.getAttribute("cursoSel");

    String edicionSel =
            (String) request.getAttribute("edicionSel");

    String ctx = request.getContextPath();

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    boolean hayFiltro =
            (institutoSel != null && !institutoSel.isEmpty())
            || (categoriaSel != null && !categoriaSel.isEmpty());
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Inscripción a Edición de Curso</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Elegí un instituto o una categoría, seleccioná un curso
            y revisá su edición vigente antes de inscribirte.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <% if ("inscripto".equals(request.getParameter("resultado"))
                && inscripcion != null) { %>
            <div class="alert alert-success" role="status">
                Tu inscripción fue registrada.
                Queda pendiente de la selección del docente.
            </div>
        <% } else if ("modificado".equals(request.getParameter("resultado"))
                && inscripcion != null) { %>
            <div class="alert alert-success" role="status">
                La fecha de tu inscripción fue actualizada.
            </div>
        <% } %>

        <div class="row g-3 mb-4">
            <div class="col-md-6">
                <form action="<%= ctx %>/inscribir-edicion" method="get">
                    <label for="institutoInscripcion" class="form-label">
                        Instituto:
                    </label>

                    <div class="input-group">
                        <select id="institutoInscripcion" name="instituto"
                                class="form-select" required>
                            <option value="">Seleccioná un instituto</option>

                            <% if (institutos != null) {
                                for (String inst : institutos) { %>
                                    <option value="<%= Html.esc(inst) %>"
                                            <%= inst.equals(institutoSel)
                                                    ? "selected" : "" %>>
                                        <%= Html.esc(inst) %>
                                    </option>
                            <%  }
                            } %>
                        </select>

                        <button class="btn btn-primary" type="submit">
                            Ver cursos
                        </button>
                    </div>
                </form>
            </div>

            <div class="col-md-6">
                <form action="<%= ctx %>/inscribir-edicion" method="get">
                    <label for="categoriaInscripcion" class="form-label">
                        Categoría:
                    </label>

                    <div class="input-group">
                        <select id="categoriaInscripcion" name="categoria"
                                class="form-select" required>
                            <option value="">Seleccioná una categoría</option>

                            <% if (categorias != null) {
                                for (String cat : categorias) { %>
                                    <option value="<%= Html.esc(cat) %>"
                                            <%= cat.equals(categoriaSel)
                                                    ? "selected" : "" %>>
                                        <%= Html.esc(cat) %>
                                    </option>
                            <%  }
                            } %>
                        </select>

                        <button class="btn btn-primary" type="submit">
                            Ver cursos
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <% if (hayFiltro) { %>
            <div class="edext-panel p-4 mb-4">
                <% if (cursos != null && !cursos.isEmpty()) { %>
                    <form action="<%= ctx %>/inscribir-edicion" method="get">
                        <input type="hidden" name="instituto"
                               value="<%= Html.esc(institutoSel) %>">
                        <input type="hidden" name="categoria"
                               value="<%= Html.esc(categoriaSel) %>">

                        <label for="cursoInscripcion" class="form-label">
                            Curso:
                        </label>

                        <div class="input-group">
                            <select id="cursoInscripcion" name="curso"
                                    class="form-select" required>
                                <option value="">Seleccioná un curso</option>

                                <% for (Curso curso : cursos) { %>
                                    <option value="<%= Html.esc(curso.getNombre()) %>"
                                            <%= curso.getNombre().equals(cursoSel)
                                                    ? "selected" : "" %>>
                                        <%= Html.esc(curso.getNombre()) %>
                                    </option>
                                <% } %>
                            </select>

                            <button class="btn btn-primary" type="submit">
                                Ver edición vigente
                            </button>
                        </div>
                    </form>
                <% } else { %>
                    <p class="text-muted mb-0">
                        No hay cursos asociados a esta selección.
                    </p>
                <% } %>
            </div>
        <% } %>

        <% if (vigentes != null && vigentes.isEmpty()) { %>
            <div class="alert alert-secondary">
                Este curso no tiene una edición vigente.
            </div>
        <% } %>

        <% if (vigentes != null && vigentes.size() > 1) { %>
            <div class="edext-panel p-4 mb-4">
                <form action="<%= ctx %>/inscribir-edicion" method="get">
                    <input type="hidden" name="instituto"
                           value="<%= Html.esc(institutoSel) %>">
                    <input type="hidden" name="categoria"
                           value="<%= Html.esc(categoriaSel) %>">
                    <input type="hidden" name="curso"
                           value="<%= Html.esc(cursoSel) %>">

                    <label for="edicionInscripcion" class="form-label">
                        Edición vigente:
                    </label>

                    <div class="input-group">
                        <select id="edicionInscripcion" name="edicion"
                                class="form-select" required>
                            <option value="">Seleccioná una edición</option>

                            <% for (EdicionCurso edicion : vigentes) { %>
                                <option value="<%= Html.esc(edicion.getNombre()) %>"
                                        <%= edicion.getNombre().equals(edicionSel)
                                                ? "selected" : "" %>>
                                    <%= Html.esc(edicion.getNombre()) %>
                                </option>
                            <% } %>
                        </select>

                        <button class="btn btn-primary" type="submit">
                            Ver edición
                        </button>
                    </div>
                </form>
            </div>
        <% } %>

        <% if (detalle != null) {
            String img = Html.imagen(ctx, detalle.getRutaImagen());
        %>
            <div class="edext-panel p-4">
                <h3><%= Html.esc(detalle.getNombre()) %></h3>

                <p class="text-muted">
                    Curso: <%= Html.esc(detalle.getCurso()) %>
                </p>

                <% if (!img.isEmpty()) { %>
                    <img src="<%= img %>"
                         alt="<%= Html.esc(detalle.getNombre()) %>"
                         class="img-fluid rounded mb-3"
                         style="max-height:200px;"
                         onerror="this.hidden=true;">
                <% } %>

                <dl class="row">
                    <dt class="col-sm-4">Inicio</dt>
                    <dd class="col-sm-8">
                        <%= detalle.getFechaInicio().format(formato) %>
                    </dd>

                    <dt class="col-sm-4">Fin</dt>
                    <dd class="col-sm-8">
                        <%= detalle.getFechaFin().format(formato) %>
                    </dd>

                    <dt class="col-sm-4">Cupo</dt>
                    <dd class="col-sm-8">
                        <%= detalle.getCupo() != null
                                ? detalle.getCupo() : "Sin cupo definido" %>
                    </dd>
                </dl>

                <h5>Docentes</h5>

                <% if (detalle.getDocentes() != null
                        && !detalle.getDocentes().isEmpty()) { %>
                    <ul>
                        <% for (String docente : detalle.getDocentes()) { %>
                            <li><%= Html.esc(docente) %></li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p class="text-muted">Sin docentes asignados.</p>
                <% } %>

                <% if (inscripcion != null) { %>
                    <div class="alert alert-secondary">
                        Ya tenés una inscripción a esta edición.
                        <br>
                        <strong>Estado:</strong>
                        <%= Html.esc(inscripcion.getEstado()) %>
                        <br>
                        <strong>Fecha:</strong>
                        <%= inscripcion.getFechaInscripcion() != null
                                ? inscripcion.getFechaInscripcion().format(formato)
                                : "—" %>
                    </div>
                <% } %>

                <form action="<%= ctx %>/inscribir-edicion"
                      method="post" id="formConfirmarInscripcion">

                    <input type="hidden" name="instituto"
                           value="<%= Html.esc(institutoSel) %>">
                    <input type="hidden" name="categoria"
                           value="<%= Html.esc(categoriaSel) %>">
                    <input type="hidden" name="curso"
                           value="<%= Html.esc(cursoSel) %>">
                    <input type="hidden" name="edicion"
                           value="<%= Html.esc(edicionSel) %>">

                    <% if (inscripcion == null) { %>
                        <input type="hidden" name="accion" value="inscribir">

                        <p class="text-muted">
                            La inscripción se registrará con la fecha actual
                            y quedará pendiente de aceptación.
                        </p>

                    <% } else if (inscripcion.getEstado()
                            == EstadoInscripcion.INSCRIPTO) { %>
                        <input type="hidden" name="accion" value="modificar">

                        <p class="text-muted">
                            Podés actualizar la fecha de tu inscripción a hoy.
                            Su estado seguirá siendo Inscripto.
                        </p>
                    <% } else { %>
                        <p class="text-muted">
                            La inscripción ya fue resuelta.
                            No podés modificarla ni crear otra para esta edición.
                        </p>
                    <% } %>

                    <div class="row g-3 mt-1">
                        <div class="col-md-6">
                            <a href="<%= ctx %>/inscribir-edicion"
                               class="btn btn-danger w-100 text-center">
                                Cancelar
                            </a>
                        </div>

                        <% if (inscripcion == null
                                || inscripcion.getEstado()
                                == EstadoInscripcion.INSCRIPTO) { %>
                            <div class="col-md-6">
                                <button class="btn btn-primary w-100"
                                        type="submit" id="btnConfirmarInscripcion">
                                    <%= inscripcion == null
                                            ? "Confirmar inscripción"
                                            : "Actualizar fecha a hoy" %>
                                </button>
                            </div>
                        <% } %>
                    </div>
                </form>
            </div>
        <% } %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    [
        "institutoInscripcion",
        "categoriaInscripcion",
        "cursoInscripcion",
        "edicionInscripcion"
    ].forEach(id => {
        const selector = document.getElementById(id);

        if (selector) {
            selector.addEventListener("change", () => {
                if (selector.value) {
                    selector.form.requestSubmit();
                }
            });
        }
    });

    const formulario =
            document.getElementById("formConfirmarInscripcion");
    const boton =
            document.getElementById("btnConfirmarInscripcion");

    if (formulario && boton) {
        const textoOriginal = boton.textContent;

        formulario.addEventListener("submit", () => {
            boton.disabled = true;
            boton.textContent = "Procesando…";
        });

        window.addEventListener("pageshow", () => {
            boton.disabled = false;
            boton.textContent = textoOriginal;
        });
    }
});
</script>

<jsp:include page="footer.jsp" />
