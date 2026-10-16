<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Logica.DTO.ElementoResumen" %>
<%@ page import="Logica.Entidades.Curso" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    List<ElementoResumen> programas = (List<ElementoResumen>) request.getAttribute("programas");
    List<Curso> disponibles = (List<Curso>) request.getAttribute("disponibles");
    String programaSel = (String) request.getAttribute("programaSel");
    String ctx = request.getContextPath();
%>

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <div class="auth-box auth-box-large" style="margin-top:0;">
            <h2>Agregar Curso a Programa de Formacion</h2>

            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= Html.esc(request.getAttribute("error")) %></div>
            <% } %>

            <% if (programas == null || programas.isEmpty()) { %>
            <div class="alert alert-secondary">
                Todavia no creaste ningún programa de formacion.
                <a href="<%= ctx %>/alta-programa">Crear un programa</a>
            </div>
            <% } else { %>

            <form action="<%= ctx %>/agregar-curso-programa" method="get" class="mb-4">
                <label for="programa" class="form-label">Programa de formacion:</label>
                <select class="form-select" id="programa" name="programa" onchange="this.form.submit()" required>
                    <option value="">-- Seleccionar --</option>
                    <% for (ElementoResumen p : programas) { %>
                    <option value="<%= Html.esc(p.getNombre()) %>"
                            <%= p.getNombre().equals(programaSel) ? "selected" : "" %>>
                        <%= Html.esc(p.getTextoMostrar()) %>
                    </option>
                    <% } %>
                </select>
                <noscript><button type="submit" class="btn btn-primary mt-2">Ver cursos</button></noscript>
            </form>

            <% if (programaSel != null) { %>
            <% if (disponibles == null || disponibles.isEmpty()) { %>
            <div class="alert alert-secondary">No hay cursos disponibles</div>
            <% } else { %>
            <form action="<%= ctx %>/agregar-curso-programa" method="post">
                <input type="hidden" name="programa" value="<%= Html.esc(programaSel) %>">

                <h5>Cursos disponibles</h5>
                <div class="list-group mb-3">
                    <% for (Curso c : disponibles) { %>
                    <label class="list-group-item d-flex justify-content-between align-items-center">
                        <span>
                            <input class="form-check-input me-2" type="checkbox" name="curso"
                                   value="<%= Html.esc(c.getNombre()) %>">
                            <%= Html.esc(c.getNombre()) %>
                        </span>
                        <span class="badge text-bg-secondary">
                            <%= c.getInstituto() != null ? Html.esc(c.getInstituto().getNombre()) : "General" %>
                        </span>
                    </label>
                    <% } %>
                </div>

                <div class="row g-3">
                    <div class="col-6">
                        <a href="<%= ctx %>/index.jsp" class="btn btn-danger w-100 text-center">Cancelar</a>
                    </div>
                    <div class="col-6">
                        <button type="submit" class="btn btn-primary w-100">Agregar cursos</button>
                    </div>
                </div>
            </form>
            <% } %>
            <% } %>

            <% } %>
        </div>
    </div>

    <jsp:include page="sidebarDer.jsp" />
</div>

<jsp:include page="footer.jsp" />