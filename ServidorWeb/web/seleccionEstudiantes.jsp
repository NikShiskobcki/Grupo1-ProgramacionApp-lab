<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Collections" %>
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

    List<InscripcionResumen> inscripciones =
            (List<InscripcionResumen>) request.getAttribute("inscripciones");

    Map<Long, String> decisiones =
            (Map<Long, String>) request.getAttribute("decisiones");

    if (decisiones == null) {
        decisiones = Collections.emptyMap();
    }

    DetalleEdicionCurso detalle =
            (DetalleEdicionCurso) request.getAttribute("detalleEdicion");

    String institutoSel =
            (String) request.getAttribute("institutoSel");

    String cursoSel =
            (String) request.getAttribute("cursoSel");

    String edicionSel =
            (String) request.getAttribute("edicionSel");

    String ordenSel =
            (String) request.getAttribute("ordenSel");

    Number cantidadAceptados =
            (Number) request.getAttribute("aceptadosActuales");

    long aceptadosActuales = cantidadAceptados == null
            ? 0 : cantidadAceptados.longValue();

    String ctx = request.getContextPath();

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Selección de Estudiantes</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Seleccioná una edición vigente en la que participes
            y resolvé las inscripciones de sus estudiantes.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <% if ("guardado".equals(request.getParameter("resultado"))
                && detalle != null
                && request.getAttribute("error") == null) { %>
            <div class="alert alert-success" role="status">
                La selección se guardó correctamente.
            </div>
        <% } %>

        <div class="edext-panel p-4 mb-4">
            <div class="row g-3">

                <div class="col-md-4">
                    <form action="<%= ctx %>/seleccionar-estudiantes"
                          method="get" class="filtro-seleccion">

                        <label for="institutoSeleccion" class="form-label">
                            Instituto:
                        </label>

                        <select id="institutoSeleccion" name="instituto"
                                class="form-select mb-2" required>
                            <option value="">Seleccioná un instituto</option>

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
                        <form action="<%= ctx %>/seleccionar-estudiantes"
                              method="get" class="filtro-seleccion">

                            <input type="hidden" name="instituto"
                                   value="<%= Html.esc(institutoSel) %>">

                            <label for="cursoSeleccion" class="form-label">
                                Curso:
                            </label>

                            <select id="cursoSeleccion" name="curso"
                                    class="form-select mb-2" required>
                                <option value="">Seleccioná un curso</option>

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
                            No hay cursos para este instituto.
                        </p>
                    <% } %>
                </div>

                <div class="col-md-4">
                    <% if (ediciones != null && !ediciones.isEmpty()) { %>
                        <form action="<%= ctx %>/seleccionar-estudiantes"
                              method="get" class="filtro-seleccion">

                            <input type="hidden" name="instituto"
                                   value="<%= Html.esc(institutoSel) %>">

                            <input type="hidden" name="curso"
                                   value="<%= Html.esc(cursoSel) %>">

                            <label for="edicionSeleccion" class="form-label">
                                Edición:
                            </label>

                            <select id="edicionSeleccion" name="edicion"
                                    class="form-select mb-2" required>
                                <option value="">Seleccioná una edición</option>

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
                                Ver inscripciones
                            </button>
                        </form>

                    <% } else if (cursoSel != null
                            && !cursoSel.isEmpty()) { %>
                        <p class="text-muted mb-0">
                            No hay ediciones vigentes de este curso
                            en las que participes como docente.
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

                    <dt class="col-sm-4">Ya aceptados</dt>
                    <dd class="col-sm-8">
                        <%= aceptadosActuales %>
                    </dd>
                </dl>

                <a href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(detalle.getNombre()) %>"
                   class="btn btn-outline-secondary">
                    Consultar detalle de la edición
                </a>
            </div>

            <% if (inscripciones == null || inscripciones.isEmpty()) { %>
                <div class="alert alert-secondary">
                    Esta edición todavía no tiene inscripciones.
                </div>

            <% } else { %>
                <form id="formSeleccionEstudiantes"
                      action="<%= ctx %>/seleccionar-estudiantes"
                      method="post"
                      data-cupo="<%= detalle.getCupo() != null
                              ? detalle.getCupo() : "" %>"
                      data-aceptados="<%= aceptadosActuales %>">

                    <input type="hidden" name="instituto"
                           value="<%= Html.esc(institutoSel) %>">

                    <input type="hidden" name="curso"
                           value="<%= Html.esc(cursoSel) %>">

                    <input type="hidden" name="edicion"
                           value="<%= Html.esc(edicionSel) %>">

                    <div class="edext-panel p-4">
                        <div class="row g-3 align-items-end mb-3">
                            <div class="col-md-6">
                                <label for="ordenInscripciones"
                                       class="form-label">
                                    Ordenar inscripciones:
                                </label>

                                <select id="ordenInscripciones"
                                        name="orden" class="form-select">
                                    <option value="fecha"
                                            <%= !"prioridad".equals(ordenSel)
                                                    ? "selected" : "" %>>
                                        Fecha de inscripción: más antiguas primero
                                    </option>

                                    <option value="prioridad"
                                            <%= "prioridad".equals(ordenSel)
                                                    ? "selected" : "" %>>
                                        Prioridad: mayor primero
                                    </option>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="filtroEstudiante"
                                       class="form-label">
                                    Buscar estudiante en esta lista:
                                </label>

                                <input id="filtroEstudiante"
                                       type="search"
                                       class="form-control"
                                       placeholder="Nombre o nickname">
                            </div>
                        </div>

                        <p class="text-muted small">
                            Prioridad = cantidad de rechazos en ediciones
                            finalizadas del mismo curso × 0,5.
                            El ranking ordena las solicitudes;
                            no acepta estudiantes automáticamente.
                        </p>

                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead>
                                    <tr>
                                        <th scope="col">Estudiante</th>
                                        <th scope="col">Inscripción</th>
                                        <th scope="col">Prioridad</th>
                                        <th scope="col">Estado actual</th>
                                        <th scope="col">Decisión</th>
                                    </tr>
                                </thead>

                                <tbody id="filasInscripciones">
                                    <% for (InscripcionResumen inscripcion
                                            : inscripciones) {

                                        boolean pendiente = "INSCRIPTO".equals(
                                                inscripcion.getEstado());

                                        String decision =
                                                decisiones.get(inscripcion.getId());
                                    %>
                                        <tr data-id="<%= inscripcion.getId() %>"
                                            data-fecha="<%= Html.esc(inscripcion.getFechaInscripcion()) %>"
                                            data-prioridad="<%= inscripcion.getPrioridad() %>"
                                            data-nickname="<%= Html.esc(inscripcion.getNicknameEstudiante()) %>"
                                            data-busqueda="<%= Html.esc(
                                                    inscripcion.getNombreEstudiante()
                                                    + " "
                                                    + inscripcion.getNicknameEstudiante()) %>">

                                            <td>
                                                <a href="<%= ctx %>/perfil?user=<%= Html.url(inscripcion.getNicknameEstudiante()) %>">
                                                    <%= Html.esc(inscripcion.getNombreEstudiante()) %>
                                                </a>

                                                <div class="text-muted small">
                                                    @<%= Html.esc(inscripcion.getNicknameEstudiante()) %>
                                                </div>
                                            </td>

                                            <td>
                                                <%= inscripcion.getFechaInscripcion() != null
                                                        ? inscripcion.getFechaInscripcion().format(formato)
                                                        : "—" %>
                                            </td>

                                            <td>
                                                <%= String.format(
                                                        java.util.Locale.forLanguageTag("es-UY"),
                                                        "%.1f",
                                                        inscripcion.getPrioridad()) %>
                                            </td>

                                            <td>
                                                <% if ("ACEPTADA".equals(
                                                        inscripcion.getEstado())) { %>
                                                    <span class="badge text-bg-success">
                                                        Aceptada
                                                    </span>

                                                <% } else if ("RECHAZADA".equals(
                                                        inscripcion.getEstado())) { %>
                                                    <span class="badge text-bg-danger">
                                                        Rechazada
                                                    </span>

                                                <% } else { %>
                                                    <span class="badge text-bg-secondary">
                                                        Inscripto
                                                    </span>
                                                <% } %>
                                            </td>

                                            <td>
                                                <% if (pendiente) { %>
                                                    <label class="visually-hidden"
                                                           for="decision_<%= inscripcion.getId() %>">
                                                        Decisión para
                                                        <%= Html.esc(inscripcion.getNombreEstudiante()) %>
                                                    </label>

                                                    <select id="decision_<%= inscripcion.getId() %>"
                                                            name="decision_<%= inscripcion.getId() %>"
                                                            class="form-select decision-inscripcion">
                                                        <option value="">
                                                            Mantener pendiente
                                                        </option>

                                                        <option value="ACEPTADA"
                                                                <%= "ACEPTADA".equals(decision)
                                                                        ? "selected" : "" %>>
                                                            Aceptar
                                                        </option>

                                                        <option value="RECHAZADA"
                                                                <%= "RECHAZADA".equals(decision)
                                                                        ? "selected" : "" %>>
                                                            Rechazar
                                                        </option>
                                                    </select>
                                                <% } else { %>
                                                    <span class="text-muted small">
                                                        Ya resuelta
                                                    </span>
                                                <% } %>
                                            </td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>

                        <p id="sinCoincidencias"
                           class="text-muted" hidden>
                            No hay estudiantes que coincidan con la búsqueda.
                        </p>

                        <div id="resumenSeleccion"
                             class="alert alert-secondary mt-3"
                             role="status" aria-live="polite"></div>

                        <div id="errorSeleccion"
                             class="alert alert-danger"
                             role="alert" hidden></div>

                        <p class="text-muted small">
                            Podés resolver solo algunas inscripciones.
                            Las demás continuarán pendientes.
                            Los cambios se aplican al guardar.
                        </p>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <button type="button"
                                        id="limpiarSeleccion"
                                        class="btn btn-outline-secondary w-100">
                                    Limpiar decisiones sin guardar
                                </button>
                            </div>

                            <div class="col-md-6">
                                <button type="submit"
                                        id="guardarSeleccion"
                                        class="btn btn-primary w-100">
                                    Guardar selección
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            <% } %>
        <% } %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const formulario =
            document.getElementById("formSeleccionEstudiantes");

    const filtros = document.querySelectorAll(".filtro-seleccion");

    let hayDecisiones = () => false;

    filtros.forEach(filtro => {
        filtro.addEventListener("submit", event => {
            if (hayDecisiones() &&
                !window.confirm(
                    "Tenés decisiones sin guardar. "
                    + "¿Querés cambiar la selección y descartarlas?")) {
                event.preventDefault();
            }
        });

        const selector = filtro.querySelector("select");

        if (selector) {
            selector.addEventListener("change", () => {
                if (selector.value) {
                    filtro.requestSubmit();
                }
            });
        }
    });

    if (!formulario) return;

    const cuerpo = document.getElementById("filasInscripciones");
    const filas = Array.from(cuerpo.querySelectorAll("tr"));
    const decisiones = Array.from(
            formulario.querySelectorAll(".decision-inscripcion"));

    const orden = document.getElementById("ordenInscripciones");
    const filtroTexto = document.getElementById("filtroEstudiante");
    const sinCoincidencias = document.getElementById("sinCoincidencias");
    const resumen = document.getElementById("resumenSeleccion");
    const error = document.getElementById("errorSeleccion");
    const guardar = document.getElementById("guardarSeleccion");
    const limpiar = document.getElementById("limpiarSeleccion");

    const aceptadosActuales = Number(formulario.dataset.aceptados);
    const cupo = formulario.dataset.cupo === ""
            ? null : Number(formulario.dataset.cupo);

    hayDecisiones = () => decisiones.some(campo => campo.value !== "");

    function actualizarResumen() {
        const nuevasAceptadas = decisiones.filter(
                campo => campo.value === "ACEPTADA").length;

        const nuevasRechazadas = decisiones.filter(
                campo => campo.value === "RECHAZADA").length;

        const totalAceptados = aceptadosActuales + nuevasAceptadas;
        const superaCupo = cupo !== null && totalAceptados > cupo;
        const cantidadCambios = nuevasAceptadas + nuevasRechazadas;

        resumen.textContent =
                "Ya aceptados: " + aceptadosActuales
                + " · Nuevas aceptaciones: " + nuevasAceptadas
                + " · Nuevos rechazos: " + nuevasRechazadas
                + " · Total de aceptados: " + totalAceptados
                + (cupo === null
                    ? " · Sin cupo definido"
                    : " / " + cupo);

        if (superaCupo) {
            error.textContent =
                    "Superaste el cupo por "
                    + (totalAceptados - cupo)
                    + " estudiante(s). Revisá las nuevas aceptaciones.";
            error.hidden = false;
        } else {
            error.textContent = "";
            error.hidden = true;
        }

        guardar.disabled = superaCupo || cantidadCambios === 0;
        limpiar.disabled = cantidadCambios === 0;

        return {
            valido: !superaCupo && cantidadCambios > 0,
            cantidadCambios: cantidadCambios
        };
    }

    function compararFecha(a, b) {
        // Las fechas ISO se ordenan cronológicamente como texto.
        const fechaA = a.dataset.fecha || "9999-12-31";
        const fechaB = b.dataset.fecha || "9999-12-31";

        return fechaA.localeCompare(fechaB);
    }

    function ordenarFilas() {
        filas.sort((a, b) => {
            if (orden.value === "prioridad") {
                const diferencia =
                        Number(b.dataset.prioridad)
                        - Number(a.dataset.prioridad);

                if (diferencia !== 0) return diferencia;
            }

            const diferenciaFecha = compararFecha(a, b);
            if (diferenciaFecha !== 0) return diferenciaFecha;

            const diferenciaNickname =
                    a.dataset.nickname.localeCompare(
                            b.dataset.nickname, "es");

            if (diferenciaNickname !== 0) return diferenciaNickname;

            return Number(a.dataset.id) - Number(b.dataset.id);
        });

        // Movemos las mismas filas: las decisiones no se pierden.
        filas.forEach(fila => cuerpo.appendChild(fila));
    }

    function filtrarFilas() {
        const texto = filtroTexto.value.trim().toLocaleLowerCase("es");
        let visibles = 0;

        filas.forEach(fila => {
            const coincide = fila.dataset.busqueda
                    .toLocaleLowerCase("es").includes(texto);

            fila.hidden = !coincide;
            if (coincide) visibles++;
        });

        sinCoincidencias.hidden = visibles !== 0;
    }

    decisiones.forEach(campo => {
        campo.addEventListener("change", actualizarResumen);
    });

    orden.addEventListener("change", ordenarFilas);
    filtroTexto.addEventListener("input", filtrarFilas);

    limpiar.addEventListener("click", () => {
        if (!window.confirm(
                "¿Querés quitar todas las decisiones sin guardar?")) {
            return;
        }

        decisiones.forEach(campo => {
            campo.value = "";
        });

        actualizarResumen();
    });

    formulario.addEventListener("submit", event => {
        const estado = actualizarResumen();

        if (!estado.valido) {
            event.preventDefault();

            if (estado.cantidadCambios === 0) {
                error.textContent =
                        "Elegí al menos una inscripción para aceptar o rechazar.";
                error.hidden = false;
            }

            error.scrollIntoView({ block: "center" });
            return;
        }

        guardar.disabled = true;
        guardar.textContent = "Guardando…";
    });

    window.addEventListener("pageshow", () => {
        guardar.textContent = "Guardar selección";
        actualizarResumen();
    });

    ordenarFilas();
    actualizarResumen();
});
</script>

<jsp:include page="footer.jsp" />
