<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Logica.Entidades.Curso" %>
<%@ page import="Logica.Entidades.EdicionCurso" %>
<%@ page import="util.Html" %>

<%
    List<String> institutos =
            (List<String>) request.getAttribute("institutos");

    List<String> categorias =
            (List<String>) request.getAttribute("categorias");

    List<Curso> cursos =
            (List<Curso>) request.getAttribute("cursos");

    List<EdicionCurso> ediciones =
            (List<EdicionCurso>) request.getAttribute("ediciones");

    String institutoSel =
            (String) request.getAttribute("institutoSel");

    String categoriaSel =
            (String) request.getAttribute("categoriaSel");

    String cursoSel =
            (String) request.getAttribute("cursoSel");

    boolean hayFiltro =
            (institutoSel != null && !institutoSel.isEmpty())
            || (categoriaSel != null && !categoriaSel.isEmpty());

    String ctx = request.getContextPath();

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Consulta de Edición de Curso</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Elegí un instituto o una categoría, luego un curso
            y la edición que querés consultar.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <div class="row g-3 mb-4">
            <div class="col-md-6">
                <form action="<%= ctx %>/consulta-edicion" method="get">
                    <label for="filtroInstituto" class="form-label">
                        Instituto:
                    </label>

                    <div class="input-group">
                        <select id="filtroInstituto" name="instituto"
                                class="form-select" required>
                            <option value="">
                                Seleccioná un instituto
                            </option>

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

                        <button type="submit" class="btn btn-primary">
                            Ver cursos
                        </button>
                    </div>
                </form>
            </div>

            <div class="col-md-6">
                <form action="<%= ctx %>/consulta-edicion" method="get">
                    <label for="filtroCategoria" class="form-label">
                        Categoría:
                    </label>

                    <div class="input-group">
                        <select id="filtroCategoria" name="categoria"
                                class="form-select" required>
                            <option value="">
                                Seleccioná una categoría
                            </option>

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

                        <button type="submit" class="btn btn-primary">
                            Ver cursos
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <% if (hayFiltro && request.getAttribute("error") == null) { %>
            <div class="edext-panel p-4 mb-4">
                <% if (cursos != null && !cursos.isEmpty()) { %>
                    <form action="<%= ctx %>/consulta-edicion"
                          method="get">

                        <% if (institutoSel != null
                                && !institutoSel.isEmpty()) { %>
                            <input type="hidden" name="instituto"
                                   value="<%= Html.esc(institutoSel) %>">
                        <% } else { %>
                            <input type="hidden" name="categoria"
                                   value="<%= Html.esc(categoriaSel) %>">
                        <% } %>

                        <label for="cursoConsulta" class="form-label">
                            Curso:
                        </label>

                        <div class="input-group">
                            <select id="cursoConsulta" name="curso"
                                    class="form-select" required>
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

                            <button type="submit" class="btn btn-primary">
                                Ver ediciones
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

        <% if (ediciones != null) { %>
            <h4 class="mb-3">
                Ediciones de <%= Html.esc(cursoSel) %>
            </h4>

            <% if (ediciones.isEmpty()) { %>
                <div class="alert alert-secondary">
                    Este curso todavía no tiene ediciones.
                </div>
            <% } else { %>
                <div class="cards-grid">
                    <% for (EdicionCurso edicion : ediciones) { %>
                        <div class="card">
                            <div class="card-body">
                                <h3 class="card-title">
                                    <%= Html.esc(edicion.getNombre()) %>
                                </h3>

                                <p class="card-desc">
                                    <strong>Inicio:</strong>
                                    <%= edicion.getFechaInicio() != null
                                            ? edicion.getFechaInicio().format(formato)
                                            : "—" %>
                                    <br>

                                    <strong>Fin:</strong>
                                    <%= edicion.getFechaFin() != null
                                            ? edicion.getFechaFin().format(formato)
                                            : "—" %>
                                    <br>

                                    <strong>Cupo:</strong>
                                    <%= edicion.getCupo() != null
                                            ? edicion.getCupo()
                                            : "Sin cupo definido" %>
                                </p>

                                <a href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(edicion.getNombre()) %>"
                                   class="btn btn-primary btn-block">
                                    Consultar edición
                                </a>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        <% } %>
    </div>

    <jsp:include page="sidebarDer.jsp" />
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    ["filtroInstituto", "filtroCategoria", "cursoConsulta"].forEach(id => {
        const selector = document.getElementById(id);

        if (selector) {
            selector.addEventListener("change", () => {
                if (selector.value) {
                    selector.form.requestSubmit();
                }
            });
        }
    });
});
</script>

<jsp:include page="footer.jsp" />
