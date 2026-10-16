<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Logica.DTO.ResultadoInscripcion" %>
<%@ page import="util.Html" %>

<%
    List<ResultadoInscripcion> resultados =
            (List<ResultadoInscripcion>) request.getAttribute("resultados");

    String ctx = request.getContextPath();

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    int pendientes = 0;
    int aceptadas = 0;
    int rechazadas = 0;

    if (resultados != null) {
        for (ResultadoInscripcion resultado : resultados) {
            if ("ACEPTADA".equals(resultado.getEstado())) {
                aceptadas++;
            } else if ("RECHAZADA".equals(resultado.getEstado())) {
                rechazadas++;
            } else if ("INSCRIPTO".equals(resultado.getEstado())) {
                pendientes++;
            }
        }
    }
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Mis Resultados de Inscripción</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Consultá todas tus inscripciones a ediciones de cursos
            y el estado de cada una.
        </p>

        <div class="d-flex flex-wrap gap-2 mb-4">
            <span class="badge text-bg-secondary">
                Inscripto: <%= pendientes %>
            </span>

            <span class="badge text-bg-success">
                Aceptadas: <%= aceptadas %>
            </span>

            <span class="badge text-bg-danger">
                Rechazadas: <%= rechazadas %>
            </span>
        </div>

        <% if (resultados == null || resultados.isEmpty()) { %>
            <div class="alert alert-secondary" role="status">
                Todavía no tenés inscripciones a ediciones de cursos.
            </div>

            <a href="<%= ctx %>/inscribir-edicion"
               class="btn btn-primary">
                Inscribirme a una edición
            </a>

        <% } else { %>
            <div class="edext-panel p-4">
                <div class="row g-3 mb-3">

                    <div class="col-md-5">
                        <label for="buscarInscripcion" class="form-label">
                            Buscar:
                        </label>

                        <input type="search"
                               id="buscarInscripcion"
                               class="form-control"
                               placeholder="Nombre del curso o de la edición">
                    </div>

                    <div class="col-md-3">
                        <label for="estadoInscripcion" class="form-label">
                            Estado:
                        </label>

                        <select id="estadoInscripcion" class="form-select">
                            <option value="">Todos</option>
                            <option value="INSCRIPTO">Inscripto</option>
                            <option value="ACEPTADA">Aceptada</option>
                            <option value="RECHAZADA">Rechazada</option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label for="ordenResultados" class="form-label">
                            Ordenar por:
                        </label>

                        <select id="ordenResultados" class="form-select">
                            <option value="recientes">
                                Inscripciones más recientes
                            </option>
                            <option value="antiguas">
                                Inscripciones más antiguas
                            </option>
                            <option value="curso">
                                Nombre del curso
                            </option>
                        </select>
                    </div>
                </div>

                <p id="cantidadResultados"
                   class="text-muted small"
                   role="status" aria-live="polite"></p>

                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th scope="col">Curso</th>
                                <th scope="col">Edición</th>
                                <th scope="col">Fecha de inscripción</th>
                                <th scope="col">Estado</th>
                            </tr>
                        </thead>

                        <tbody id="filasResultados">
                            <% for (ResultadoInscripcion resultado : resultados) { %>
                                <tr data-curso="<%= Html.esc(resultado.getNombreCurso()) %>"
                                    data-edicion="<%= Html.esc(resultado.getNombreEdicion()) %>"
                                    data-fecha="<%= Html.esc(resultado.getFechaInscripcion()) %>"
                                    data-estado="<%= Html.esc(resultado.getEstado()) %>">

                                    <td>
                                        <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(resultado.getNombreCurso()) %>">
                                            <%= Html.esc(resultado.getNombreCurso()) %>
                                        </a>
                                    </td>

                                    <td>
                                        <a href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(resultado.getNombreEdicion()) %>">
                                            <%= Html.esc(resultado.getNombreEdicion()) %>
                                        </a>
                                    </td>

                                    <td>
                                        <%= resultado.getFechaInscripcion() != null
                                                ? resultado.getFechaInscripcion().format(formato)
                                                : "—" %>
                                    </td>

                                    <td>
                                        <% if ("ACEPTADA".equals(
                                                resultado.getEstado())) { %>
                                            <span class="badge text-bg-success">
                                                Aceptada
                                            </span>

                                        <% } else if ("RECHAZADA".equals(
                                                resultado.getEstado())) { %>
                                            <span class="badge text-bg-danger">
                                                Rechazada
                                            </span>

                                        <% } else if ("INSCRIPTO".equals(
                                                resultado.getEstado())) { %>
                                            <span class="badge text-bg-secondary">
                                                Inscripto
                                            </span>

                                        <% } else { %>
                                            <%= Html.esc(resultado.getEstado()) %>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>

                <p id="sinResultadosCoincidentes"
                   class="text-muted" hidden>
                    No hay inscripciones que coincidan con los filtros.
                </p>

                <p class="text-muted small">
                    “Inscripto” significa que tu solicitud está pendiente
                    de la selección del docente.
                </p>

                <div class="d-flex flex-wrap gap-2">
                    <a href="<%= ctx %>/inscribir-edicion"
                       class="btn btn-primary">
                        Nueva inscripción
                    </a>

                    <a href="<%= ctx %>/perfil"
                       class="btn btn-outline-secondary">
                        Mi perfil
                    </a>
                </div>
            </div>
        <% } %>
    </div>

    <jsp:include page="sidebarDer.jsp" />
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const cuerpo = document.getElementById("filasResultados");

    if (!cuerpo) return;

    const filas = Array.from(cuerpo.querySelectorAll("tr"));
    const buscador = document.getElementById("buscarInscripcion");
    const estado = document.getElementById("estadoInscripcion");
    const orden = document.getElementById("ordenResultados");
    const cantidad = document.getElementById("cantidadResultados");
    const sinResultados =
            document.getElementById("sinResultadosCoincidentes");

    function compararTexto(a, b) {
        return a.localeCompare(b, "es", { sensitivity: "base" });
    }

    function ordenar() {
        filas.sort((a, b) => {
            let diferencia = 0;

            if (orden.value === "curso") {
                diferencia = compararTexto(
                        a.dataset.curso, b.dataset.curso);

            } else {
                const fechaA = a.dataset.fecha;
                const fechaB = b.dataset.fecha;

                // Las fechas ausentes quedan al final en ambos órdenes.
                if (!fechaA && fechaB) return 1;
                if (fechaA && !fechaB) return -1;

                diferencia = orden.value === "antiguas"
                        ? fechaA.localeCompare(fechaB)
                        : fechaB.localeCompare(fechaA);
            }

            if (diferencia !== 0) return diferencia;

            diferencia = compararTexto(
                    a.dataset.curso, b.dataset.curso);

            if (diferencia !== 0) return diferencia;

            return compararTexto(
                    a.dataset.edicion, b.dataset.edicion);
        });

        filas.forEach(fila => cuerpo.appendChild(fila));
    }

    function filtrar() {
        const texto = buscador.value.trim().toLocaleLowerCase("es");
        let visibles = 0;

        filas.forEach(fila => {
            const contenido = (
                    fila.dataset.curso + " " + fila.dataset.edicion
            ).toLocaleLowerCase("es");

            const coincideTexto = contenido.includes(texto);
            const coincideEstado = !estado.value
                    || fila.dataset.estado === estado.value;

            const visible = coincideTexto && coincideEstado;
            fila.hidden = !visible;

            if (visible) visibles++;
        });

        cantidad.textContent =
                "Mostrando " + visibles + " de "
                + filas.length + " inscripciones.";

        sinResultados.hidden = visibles !== 0;
    }

    buscador.addEventListener("input", filtrar);
    estado.addEventListener("change", filtrar);
    orden.addEventListener("change", ordenar);

    ordenar();
    filtrar();
});
</script>

<jsp:include page="footer.jsp" />