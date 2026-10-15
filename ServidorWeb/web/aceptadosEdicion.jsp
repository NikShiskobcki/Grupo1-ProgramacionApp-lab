<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Logica.Entidades.Curso" %>
<%@ page import="Logica.Entidades.EdicionCurso" %>
<%@ page import="Logica.DTO.DetalleEdicionCurso" %>
<%@ page import="Logica.DTO.InscripcionResumen" %>
<%@ page import="util.Html" %>

<%
    List<String> institutos =
            (List<String>) request.getAttribute("institutos");

    List<Curso> cursos =
            (List<Curso>) request.getAttribute("cursos");

    List<EdicionCurso> ediciones =
            (List<EdicionCurso>) request.getAttribute("ediciones");

    List<InscripcionResumen> aceptados =
            (List<InscripcionResumen>) request.getAttribute("aceptados");

    DetalleEdicionCurso detalle =
            (DetalleEdicionCurso) request.getAttribute("detalleEdicion");

    String institutoSel =
            (String) request.getAttribute("institutoSel");

    String cursoSel =
            (String) request.getAttribute("cursoSel");

    String edicionSel =
            (String) request.getAttribute("edicionSel");

    String ctx = request.getContextPath();

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    LocalDate hoy = LocalDate.now(
            ZoneId.of("America/Montevideo"));

    boolean vigente = detalle != null
            && detalle.getFechaInicio() != null
            && detalle.getFechaFin() != null
            && !hoy.isBefore(detalle.getFechaInicio())
            && !hoy.isAfter(detalle.getFechaFin());
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Estudiantes Aceptados</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Consultá los estudiantes aceptados en las ediciones
            en las que participás como docente.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <div class="edext-panel p-4 mb-4">
            <div class="row g-3">

                <div class="col-md-4">
                    <form action="<%= ctx %>/aceptados-edicion"
                          method="get" class="filtro-aceptados">

                        <label for="institutoAceptados" class="form-label">
                            Instituto:
                        </label>

                        <select id="institutoAceptados" name="instituto"
                                class="form-select mb-2" required>
                            <option value="">
                                Seleccioná un instituto
                            </option>

                            <% if (institutos != null) {
                                for (String instituto : institutos) { %>
                                    <option value="<%= Html.esc(instituto) %>"
                                            <%= instituto.equals(institutoSel)
                                                    ? "selected" : "" %>>
                                        <%= Html.esc(instituto) %>
                                    </option>
                            <%  }
                            } %>
                        </select>

                        <button type="submit"
                                class="btn btn-primary w-100">
                            Ver cursos
                        </button>
                    </form>
                </div>

                <div class="col-md-4">
                    <% if (cursos != null && !cursos.isEmpty()) { %>
                        <form action="<%= ctx %>/aceptados-edicion"
                              method="get" class="filtro-aceptados">

                            <input type="hidden" name="instituto"
                                   value="<%= Html.esc(institutoSel) %>">

                            <label for="cursoAceptados" class="form-label">
                                Curso:
                            </label>

                            <select id="cursoAceptados" name="curso"
                                    class="form-select mb-2" required>
                                <option value="">
                                    Seleccioná un curso
                                </option>

                                <% for (Curso curso : cursos) { %>
                                    <option value="<%= Html.esc(curso.getNombre()) %>"
                                            <%= curso.getNombre().equals(cursoSel)
                                                    ? "selected" : "" %>>
                                        <%= Html.esc(curso.getNombre()) %>
                                    </option>
                                <% } %>
                            </select>

                            <button type="submit"
                                    class="btn btn-primary w-100">
                                Ver ediciones
                            </button>
                        </form>

                    <% } else if (institutoSel != null
                            && !institutoSel.isEmpty()) { %>
                        <p class="text-muted mb-0">
                            No hay cursos asociados a este instituto.
                        </p>
                    <% } %>
                </div>

                <div class="col-md-4">
                    <% if (ediciones != null && !ediciones.isEmpty()) { %>
                        <form action="<%= ctx %>/aceptados-edicion"
                              method="get" class="filtro-aceptados">

                            <input type="hidden" name="instituto"
                                   value="<%= Html.esc(institutoSel) %>">

                            <input type="hidden" name="curso"
                                   value="<%= Html.esc(cursoSel) %>">

                            <label for="edicionAceptados" class="form-label">
                                Edición:
                            </label>

                            <select id="edicionAceptados" name="edicion"
                                    class="form-select mb-2" required>
                                <option value="">
                                    Seleccioná una edición
                                </option>

                                <% for (EdicionCurso edicion : ediciones) { %>
                                    <option value="<%= Html.esc(edicion.getNombre()) %>"
                                            <%= edicion.getNombre().equals(edicionSel)
                                                    ? "selected" : "" %>>
                                        <%= Html.esc(edicion.getNombre()) %>
                                    </option>
                                <% } %>
                            </select>

                            <button type="submit"
                                    class="btn btn-primary w-100">
                                Ver aceptados
                            </button>
                        </form>

                    <% } else if (cursoSel != null
                            && !cursoSel.isEmpty()) { %>
                        <p class="text-muted mb-0">
                            No participás en ninguna edición de este curso.
                        </p>
                    <% } %>
                </div>
            </div>
        </div>

        <% if (detalle != null) { %>
            <div class="edext-panel p-4 mb-4">
                <h3><%= Html.esc(detalle.getNombre()) %></h3>

                <p class="text-muted">
                    Curso: <%= Html.esc(detalle.getCurso()) %>
                </p>

                <dl class="row mb-3">
                    <dt class="col-sm-4">Inicio</dt>
                    <dd class="col-sm-8">
                        <%= detalle.getFechaInicio() != null
                                ? detalle.getFechaInicio().format(formato) : "—" %>
                    </dd>

                    <dt class="col-sm-4">Fin</dt>
                    <dd class="col-sm-8">
                        <%= detalle.getFechaFin() != null
                                ? detalle.getFechaFin().format(formato) : "—" %>
                    </dd>

                    <dt class="col-sm-4">Cupo</dt>
                    <dd class="col-sm-8">
                        <%= detalle.getCupo() != null
                                ? detalle.getCupo() : "Sin cupo definido" %>
                    </dd>

                    <dt class="col-sm-4">Estudiantes aceptados</dt>
                    <dd class="col-sm-8">
                        <%= aceptados != null ? aceptados.size() : 0 %>
                    </dd>
                </dl>

                <div class="d-flex flex-wrap gap-2">
                    <a href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(detalle.getNombre()) %>"
                       class="btn btn-outline-secondary">
                        Consultar edición
                    </a>

                    <% if (vigente) { %>
                        <a href="<%= ctx %>/seleccionar-estudiantes?edicion=<%= Html.url(detalle.getNombre()) %>"
                           class="btn btn-primary">
                            Seleccionar estudiantes
                        </a>
                    <% } %>
                </div>
            </div>

            <% if (aceptados == null || aceptados.isEmpty()) { %>
                <div class="alert alert-secondary" role="status">
                    Todavía no hay estudiantes aceptados en esta edición.
                </div>

            <% } else { %>
                <div class="edext-panel p-4">
                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label for="buscarAceptado" class="form-label">
                                Buscar estudiante:
                            </label>

                            <input type="search" id="buscarAceptado"
                                   class="form-control"
                                   placeholder="Nombre o nickname">
                        </div>

                        <div class="col-md-6">
                            <label for="ordenAceptados" class="form-label">
                                Ordenar por:
                            </label>

                            <select id="ordenAceptados" class="form-select">
                                <option value="nombre">Nombre</option>
                                <option value="fecha">
                                    Fecha de inscripción: más antiguas primero
                                </option>
                            </select>
                        </div>
                    </div>

                    <p id="cantidadVisible"
                       class="text-muted small"
                       role="status" aria-live="polite"></p>

                    <div class="table-responsive">
                        <table class="table align-middle">
                            <thead>
                                <tr>
                                    <th scope="col">Estudiante</th>
                                    <th scope="col">Nickname</th>
                                    <th scope="col">Fecha de inscripción</th>
                                    <th scope="col">Estado</th>
                                </tr>
                            </thead>

                            <tbody id="filasAceptados">
                                <% for (InscripcionResumen estudiante : aceptados) { %>
                                    <tr data-nombre="<%= Html.esc(estudiante.getNombreEstudiante()) %>"
                                        data-nickname="<%= Html.esc(estudiante.getNicknameEstudiante()) %>"
                                        data-fecha="<%= Html.esc(estudiante.getFechaInscripcion()) %>">

                                        <td>
                                            <a href="<%= ctx %>/perfil?user=<%= Html.url(estudiante.getNicknameEstudiante()) %>">
                                                <%= Html.esc(estudiante.getNombreEstudiante()) %>
                                            </a>
                                        </td>

                                        <td>
                                            @<%= Html.esc(estudiante.getNicknameEstudiante()) %>
                                        </td>

                                        <td>
                                            <%= estudiante.getFechaInscripcion() != null
                                                    ? estudiante.getFechaInscripcion().format(formato)
                                                    : "—" %>
                                        </td>

                                        <td>
                                            <span class="badge text-bg-success">
                                                Aceptada
                                            </span>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <p id="sinAceptadosCoincidentes"
                       class="text-muted" hidden>
                        No hay estudiantes que coincidan con la búsqueda.
                    </p>
                </div>
            <% } %>
        <% } %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".filtro-aceptados select")
            .forEach(selector => {
                selector.addEventListener("change", () => {
                    if (selector.value) {
                        selector.form.requestSubmit();
                    }
                });
            });

    const cuerpo = document.getElementById("filasAceptados");

    if (!cuerpo) return;

    const filas = Array.from(cuerpo.querySelectorAll("tr"));
    const buscador = document.getElementById("buscarAceptado");
    const orden = document.getElementById("ordenAceptados");
    const cantidad = document.getElementById("cantidadVisible");
    const sinCoincidencias =
            document.getElementById("sinAceptadosCoincidentes");

    function ordenar() {
        filas.sort((a, b) => {
            if (orden.value === "fecha") {
                const fechaA = a.dataset.fecha || "9999-12-31";
                const fechaB = b.dataset.fecha || "9999-12-31";
                const diferencia = fechaA.localeCompare(fechaB);

                if (diferencia !== 0) return diferencia;
            }

            const diferenciaNombre = a.dataset.nombre.localeCompare(
                    b.dataset.nombre, "es", { sensitivity: "base" });

            if (diferenciaNombre !== 0) return diferenciaNombre;

            return a.dataset.nickname.localeCompare(
                    b.dataset.nickname, "es");
        });

        filas.forEach(fila => cuerpo.appendChild(fila));
    }

    function filtrar() {
        const texto = buscador.value.trim().toLocaleLowerCase("es");
        let visibles = 0;

        filas.forEach(fila => {
            const contenido = (
                    fila.dataset.nombre + " " + fila.dataset.nickname
            ).toLocaleLowerCase("es");

            const coincide = contenido.includes(texto);
            fila.hidden = !coincide;

            if (coincide) visibles++;
        });

        cantidad.textContent =
                "Mostrando " + visibles + " de "
                + filas.length + " estudiantes aceptados.";

        sinCoincidencias.hidden = visibles !== 0;
    }

    orden.addEventListener("change", ordenar);
    buscador.addEventListener("input", filtrar);

    ordenar();
    filtrar();
});
</script>

<jsp:include page="footer.jsp" />
