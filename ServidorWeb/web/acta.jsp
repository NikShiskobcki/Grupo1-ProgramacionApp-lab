<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="util.ActaEstudio" %>
<%@ page import="util.ActaEstudio.Acta" %>
<%@ page import="util.ActaEstudio.Fila" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    List<String> programas = (List<String>) request.getAttribute("programas");
    Acta acta = (Acta) request.getAttribute("acta");
    String programaSel = (String) request.getAttribute("programaSel");
    String ctx = request.getContextPath();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Tramitar Acta de Estudios</h2>
        <p class="auth-subtitle" style="text-align:left;">
            Elegí el programa de formación del que querés obtener el acta.
            Podés generarla cuando hayas aprobado todos sus cursos.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-warning" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <% if (programas == null || programas.isEmpty()) { %>
            <div class="alert alert-secondary" role="status">
                Todavía no estás inscripto a ningún programa de formación.
            </div>
            <a href="<%= ctx %>/buscar#programas" class="btn btn-primary">
                Ver programas de formación
            </a>
        <% } else { %>

            <form action="<%= ctx %>/acta" method="get" class="row g-2 align-items-end mb-4">
                <div class="col-md-8">
                    <label for="programa" class="form-label">Programa de formación:</label>
                    <select class="form-select" id="programa" name="programa" required>
                        <option value="">-- Seleccionar --</option>
                        <% for (String nombre : programas) { %>
                            <option value="<%= Html.esc(nombre) %>"
                                <%= nombre.equals(programaSel) ? "selected" : "" %>>
                                <%= Html.esc(nombre) %>
                            </option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Ver estado</button>
                </div>
            </form>

            <% if (acta != null) {
                int total = acta.getTotalCursos();
                int aprobados = acta.getCursosAprobados();
                int porcentaje = total == 0 ? 0 : (aprobados * 100) / total;
            %>
                <div class="edext-panel p-4">
                    <h4 class="mb-1"><%= Html.esc(acta.getPrograma().getNombre()) %></h4>
                    <% if (acta.getPrograma().getDescripcion() != null) { %>
                        <p class="text-muted"><%= Html.esc(acta.getPrograma().getDescripcion()) %></p>
                    <% } %>

                    <div class="d-flex justify-content-between small mb-1">
                        <span>Cursos aprobados</span>
                        <span><strong><%= aprobados %></strong> de <%= total %></span>
                    </div>
                    <div class="progress mb-4" role="progressbar"
                         aria-label="Cursos aprobados del programa"
                         aria-valuenow="<%= porcentaje %>" aria-valuemin="0" aria-valuemax="100">
                        <div class="progress-bar bg-success" style="width: <%= porcentaje %>%"></div>
                    </div>

                    <% if (total == 0) { %>
                        <div class="alert alert-secondary mb-0" role="status">
                            Este programa todavía no tiene cursos asociados.
                        </div>
                    <% } else { %>
                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead>
                                    <tr>
                                        <th scope="col">Curso</th>
                                        <th scope="col">Edición</th>
                                        <th scope="col" class="text-center">Nota</th>
                                        <th scope="col">Resultado</th>
                                        <th scope="col">Fecha</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Fila f : acta.getFilas()) { %>
                                        <tr>
                                            <td>
                                                <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(f.getNombreCurso()) %>">
                                                    <%= Html.esc(f.getNombreCurso()) %>
                                                </a>
                                            </td>
                                            <td><%= f.getNombreEdicion() == null ? "—" : Html.esc(f.getNombreEdicion()) %></td>
                                            <td class="text-center"><%= f.getNota() == null ? "—" : f.getNota() %></td>
                                            <td>
                                                <% if (f.getEstado() == ActaEstudio.Estado.APROBADO) { %>
                                                    <span class="badge text-bg-success">Aprobado</span>
                                                <% } else if (f.getEstado() == ActaEstudio.Estado.REPROBADO) { %>
                                                    <span class="badge text-bg-danger">No aprobado</span>
                                                <% } else { %>
                                                    <span class="badge text-bg-secondary">Pendiente</span>
                                                <% } %>
                                            </td>
                                            <td><%= f.getFecha() == null ? "—" : f.getFecha().format(formato) %></td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>

                        <% if (acta.isTramitable()) { %>
                            <div class="alert alert-success" role="status">
                                ¡Aprobaste todos los cursos del programa! Ya podés descargar tu acta.
                            </div>
                            <a href="<%= ctx %>/acta-pdf?programa=<%= Html.url(acta.getPrograma().getNombre()) %>"
                               class="btn btn-success">
                                Descargar acta (PDF)
                            </a>
                        <% } else { %>
                            <div class="alert alert-warning" role="status">
                                Todavía no podés tramitar el acta: te faltan
                                <strong><%= total - aprobados %></strong>
                                curso<%= (total - aprobados) == 1 ? "" : "s" %> por aprobar.
                            </div>
                            <a class="btn btn-success disabled" aria-disabled="true" tabindex="-1">
                                Descargar acta (PDF)
                            </a>
                        <% } %>

                        <p class="text-muted small mt-3 mb-0">
                            Un curso se considera aprobado con nota
                            <%= ActaEstudio.NOTA_APROBACION %> o más (escala de 1 a 12). Las notas
                            valen cuando el docente cerró la edición. Si cursaste varias
                            ediciones, se toma la mejor nota.
                        </p>
                    <% } %>
                </div>
            <% } %>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />
