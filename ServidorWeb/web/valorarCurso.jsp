<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="modelo.ValoracionCurso" %>
<%@ page import="util.Html" %>

<%
    List<String> cursos = (List<String>) request.getAttribute("cursos");
    ValoracionCurso valoracion = (ValoracionCurso) request.getAttribute("valoracion");
    String cursoSel = (String) request.getAttribute("cursoSel");
    String ctx = request.getContextPath();
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Valorar un curso</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Elegí un curso en el que participás (en cualquiera de sus ediciones)
            y asignale un puntaje de 1 a 5. Cada curso se puede valorar una sola vez.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <% if ("valorado".equals(request.getParameter("resultado"))
                && valoracion != null && valoracion.getPuntajeEstudiante() != null) { %>
            <div class="alert alert-success" role="status">
                Tu valoración fue registrada. ¡Gracias!
            </div>
        <% } %>

        <% if (cursos == null || cursos.isEmpty()) { %>
            <div class="alert alert-secondary" role="status">
                Todavía no participás en ningún curso. Podés valorar un curso
                cuando un docente acepte tu inscripción a alguna de sus ediciones.
            </div>

            <a href="<%= ctx %>/mis-inscripciones" class="btn btn-outline-secondary">
                Ver el estado de mis inscripciones
            </a>

        <% } else { %>
            <div class="edext-panel p-4 mb-4">
                <form action="<%= ctx %>/valorar-curso" method="get">
                    <label for="cursoValorar" class="form-label">Curso:</label>

                    <div class="input-group">
                        <select id="cursoValorar" name="curso" class="form-select" required>
                            <option value="">Seleccioná un curso</option>
                            <% for (String curso : cursos) { %>
                                <option value="<%= Html.esc(curso) %>"
                                        <%= curso.equals(cursoSel) ? "selected" : "" %>>
                                    <%= Html.esc(curso) %>
                                </option>
                            <% } %>
                        </select>

                        <button class="btn btn-primary" type="submit">Ver valoración</button>
                    </div>
                </form>
            </div>
        <% } %>

        <% if (valoracion != null) { %>
            <div class="edext-panel p-4">
                <h3><%= Html.esc(valoracion.getNombreCurso()) %></h3>

                <h5>Puntaje promedio</h5>
                <% if (valoracion.getPromedio() == null) { %>
                    <p class="text-muted">Este curso todavía no tiene valoraciones.</p>
                <% } else { %>
                    <p class="fs-4 mb-1"
                       role="img"
                       aria-label="Promedio <%= String.format(Locale.US, "%.1f", valoracion.getPromedio()) %> de 5">
                        <span style="color:#f5a623;"><%= Html.estrellas(valoracion.getPromedio()) %></span>
                        <span class="fs-6 text-muted">
                            <%= String.format(new Locale("es", "UY"), "%.1f", valoracion.getPromedio()) %> de 5
                            (<%= valoracion.getCantidad() %>
                            <%= valoracion.getCantidad() == 1 ? "valoración" : "valoraciones" %>)
                        </span>
                    </p>
                <% } %>

                <h5 class="mt-3">Tu valoración</h5>

                <% if (valoracion.getPuntajeEstudiante() != null) { %>
                    <p class="fs-4 mb-1"
                       role="img"
                       aria-label="Tu puntaje: <%= valoracion.getPuntajeEstudiante() %> de 5">
                        <span style="color:#f5a623;"><%= Html.estrellas(valoracion.getPuntajeEstudiante().doubleValue()) %></span>
                        <span class="fs-6 text-muted"><%= valoracion.getPuntajeEstudiante() %> de 5</span>
                    </p>
                    <div class="alert alert-secondary mt-3" role="status">
                        Ya valoraste este curso. Cada curso se puede valorar una sola vez,
                        por lo que el puntaje no se puede modificar.
                    </div>
                <% } else { %>
                    <form action="<%= ctx %>/valorar-curso" method="post" id="formValorar">
                        <input type="hidden" name="curso" value="<%= Html.esc(cursoSel) %>">

                        <fieldset class="mb-3">
                            <legend class="form-label fs-6">Puntaje (1 a 5)</legend>

                            <% for (int p = 1; p <= 5; p++) { %>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio"
                                           name="puntaje" id="puntaje<%= p %>"
                                           value="<%= p %>" required>
                                    <label class="form-check-label" for="puntaje<%= p %>">
                                        <%= p %>
                                        <span style="color:#f5a623;" aria-hidden="true">&#9733;</span>
                                    </label>
                                </div>
                            <% } %>
                        </fieldset>

                        <p class="text-muted small">
                            Solo podés valorar este curso una vez; después no vas a poder cambiar el puntaje.
                        </p>

                        <div class="d-flex gap-2">
                            <a href="<%= ctx %>/valorar-curso" class="btn btn-outline-secondary">
                                Cancelar
                            </a>
                            <button class="btn btn-primary" type="submit" id="btnValorar">
                                Valorar curso
                            </button>
                        </div>
                    </form>
                <% } %>
            </div>
        <% } %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const selector = document.getElementById("cursoValorar");
    if (selector) {
        selector.addEventListener("change", () => {
            if (selector.value) selector.form.requestSubmit();
        });
    }

    const formulario = document.getElementById("formValorar");
    const boton = document.getElementById("btnValorar");
    if (formulario && boton) {
        formulario.addEventListener("submit", () => {
            boton.disabled = true;
            boton.textContent = "Procesando…";
        });
        // Al volver con el botón "atrás" el formulario se restaura habilitado.
        window.addEventListener("pageshow", () => {
            boton.disabled = false;
            boton.textContent = "Valorar curso";
        });
    }
});
</script>

<jsp:include page="footer.jsp" />
