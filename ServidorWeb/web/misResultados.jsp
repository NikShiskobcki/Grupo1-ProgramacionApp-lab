<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="modelo.ResultadoEvaluacion" %>
<%@ page import="util.Html" %>

<%
    List<ResultadoEvaluacion> resultados =
            (List<ResultadoEvaluacion>) request.getAttribute("resultados");

    String ctx = request.getContextPath();

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Mis Resultados de Evaluaciones</h2>

        <p class="auth-subtitle" style="text-align:left;">
            Resultados de tus inscripciones a ediciones de cursos ya cerradas.
            Las ediciones que siguen abiertas no aparecen hasta que el docente
            las cierre.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= Html.esc(request.getAttribute("error")) %>
            </div>
        <% } %>

        <% if (resultados == null || resultados.isEmpty()) { %>
            <div class="alert alert-secondary" role="status">
                Todavía no tenés inscripciones a ediciones cerradas.
            </div>

            <a href="<%= ctx %>/mis-inscripciones" class="btn btn-outline-secondary">
                Ver el estado de mis inscripciones
            </a>

        <% } else { %>
            <div class="edext-panel p-4">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th scope="col">Curso</th>
                                <th scope="col">Edición</th>
                                <th scope="col">Fecha de inscripción</th>
                                <th scope="col">Cierre de la edición</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Nota (1 a 12)</th>
                            </tr>
                        </thead>

                        <tbody>
                            <% for (ResultadoEvaluacion r : resultados) { %>
                                <tr>
                                    <td>
                                        <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(r.getNombreCurso()) %>">
                                            <%= Html.esc(r.getNombreCurso()) %>
                                        </a>
                                    </td>

                                    <td>
                                        <a href="<%= ctx %>/edicion-detalle?nombre=<%= Html.url(r.getNombreEdicion()) %>">
                                            <%= Html.esc(r.getNombreEdicion()) %>
                                        </a>
                                    </td>

                                    <td>
                                        <%= r.getFechaInscripcion() != null
                                                ? r.getFechaInscripcion().format(formato) : "—" %>
                                    </td>

                                    <td>
                                        <%= r.getFechaFin() != null
                                                ? r.getFechaFin().format(formato) : "—" %>
                                    </td>

                                    <td>
                                        <% if ("ACEPTADA".equals(r.getEstado())) { %>
                                            <span class="badge text-bg-success">Aceptada</span>
                                        <% } else if ("RECHAZADA".equals(r.getEstado())) { %>
                                            <span class="badge text-bg-danger">Rechazada</span>
                                        <% } else { %>
                                            <span class="badge text-bg-secondary">
                                                <%= Html.esc(r.getEstado()) %>
                                            </span>
                                        <% } %>
                                    </td>

                                    <td>
                                        <% if (r.getNota() != null) { %>
                                            <strong><%= r.getNota() %></strong>
                                        <% } else { %>
                                            <span class="text-muted">Sin nota</span>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>

                <div class="d-flex flex-wrap gap-2">
                    <a href="<%= ctx %>/valorar-curso" class="btn btn-primary">
                        Valorar un curso
                    </a>

                    <a href="<%= ctx %>/mis-inscripciones" class="btn btn-outline-secondary">
                        Estado de mis inscripciones
                    </a>
                </div>
            </div>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />
