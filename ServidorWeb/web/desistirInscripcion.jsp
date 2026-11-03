<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="modelo.ResultadoInscripcion" %>
<%@ page import="util.Html" %>

<%
    String ctx = request.getContextPath();

    List<ResultadoInscripcion> inscripciones =
            (List<ResultadoInscripcion>)
                    request.getAttribute("inscripciones");

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Desistir de inscripción</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Seleccioná la inscripción de la que querés desistir.
            Solo aparecen las que están en estado Inscripto,
            pendientes de la selección del docente.
        </p>

        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="alert alert-success" role="status">
                <%= Html.esc(request.getAttribute("mensaje")) %>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <% if (inscripciones == null || inscripciones.isEmpty()) { %>

            <div class="alert alert-secondary" role="status">
                No tenés inscripciones en estado Inscripto
                de las que puedas desistir.
            </div>

            <a href="<%= ctx %>/mis-inscripciones"
               class="btn btn-outline-secondary">
                Ver mis inscripciones
            </a>

        <% } else { %>

            <div class="edext-panel p-4">
                <form id="formDesistimiento"
                      action="<%= ctx %>/desistir-inscripcion"
                      method="post">

                    <input type="hidden"
                           name="token"
                           value="<%= Html.esc(
                                   request.getAttribute(
                                           "tokenDesistimiento")) %>">

                    <fieldset>
                        <legend class="fs-5">
                            Inscripciones disponibles
                        </legend>

                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead>
                                    <tr>
                                        <th scope="col">Seleccionar</th>
                                        <th scope="col">Curso</th>
                                        <th scope="col">Edición</th>
                                        <th scope="col">
                                            Fecha de inscripción
                                        </th>
                                        <th scope="col">Estado</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <%
                                        int numero = 0;
                                        for (ResultadoInscripcion inscripcion
                                                : inscripciones) {
                                            numero++;
                                    %>
                                        <tr>
                                            <td>
                                                <input
                                                    type="radio"
                                                    class="form-check-input"
                                                    id="inscripcion<%= numero %>"
                                                    name="edicion"
                                                    value="<%= Html.esc(
                                                            inscripcion.getNombreEdicion()) %>"
                                                    required
                                                    aria-label="Seleccionar <%= Html.esc(
                                                            inscripcion.getNombreEdicion()) %>">
                                            </td>

                                            <td>
                                                <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(
                                                        inscripcion.getNombreCurso()) %>">
                                                    <%= Html.esc(
                                                            inscripcion.getNombreCurso()) %>
                                                </a>
                                            </td>

                                            <td>
                                                <label
                                                    for="inscripcion<%= numero %>"
                                                    class="form-check-label">
                                                    <%= Html.esc(
                                                            inscripcion.getNombreEdicion()) %>
                                                </label>
                                            </td>

                                            <td>
                                                <%= inscripcion.getFechaInscripcion() == null
                                                        ? "—"
                                                        : inscripcion.getFechaInscripcion()
                                                                .format(formato) %>
                                            </td>

                                            <td>
                                                <span class="badge text-bg-secondary">
                                                    Inscripto
                                                </span>
                                            </td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </fieldset>

                    <p id="seleccionDesistimiento"
                       class="text-muted"
                       role="status"
                       aria-live="polite">
                        Seleccioná una inscripción para continuar.
                    </p>

                    <p class="text-muted small">
                        Al confirmar, se eliminará tu inscripción
                        a la edición seleccionada.
                    </p>

                    <div class="d-flex flex-wrap gap-2">
                        <button type="submit"
                                id="confirmarDesistimiento"
                                class="btn btn-danger">
                            Confirmar desistimiento
                        </button>

                        <a href="<%= ctx %>/mis-inscripciones"
                           class="btn btn-outline-secondary">
                            Cancelar
                        </a>
                    </div>
                </form>
            </div>

        <% } %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("formDesistimiento");

    if (!formulario) return;

    const boton = document.getElementById("confirmarDesistimiento");
    const mensaje = document.getElementById("seleccionDesistimiento");

    function actualizarSeleccion() {
        const seleccion = formulario.querySelector(
                'input[name="edicion"]:checked');

        boton.disabled = !seleccion;

        mensaje.textContent = seleccion
                ? "Edición seleccionada: " + seleccion.value
                : "Seleccioná una inscripción para continuar.";
    }

    formulario.addEventListener("change", actualizarSeleccion);

    formulario.addEventListener("submit", (evento) => {
        const seleccion = formulario.querySelector(
                'input[name="edicion"]:checked');

        if (!seleccion) {
            evento.preventDefault();
            return;
        }

        const confirmado = window.confirm(
                '¿Querés eliminar tu inscripción a "'
                + seleccion.value + '"?');

        if (!confirmado) {
            evento.preventDefault();
        }
    });

    window.addEventListener("pageshow", actualizarSeleccion);
    actualizarSeleccion();
});
</script>

<jsp:include page="footer.jsp" />
