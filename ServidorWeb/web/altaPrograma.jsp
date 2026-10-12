<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <div class="auth-box auth-box-large" style="margin-top:0;">
            <h2>Alta de Programa de Formación</h2>
            <p class="auth-subtitle">Registra un nuevo programa de formación</p>

            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= Html.esc(request.getAttribute("error")) %></div>
            <% } %>

            <form action="<%= request.getContextPath() %>/alta-programa" method="post" class="row g-3">

                <div class="col-12">
                    <label for="nombre" class="form-label">Nombre (único):</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required maxlength="100"
                           value="<%= Html.esc(request.getAttribute("v_nombre")) %>">
                </div>

                <div class="col-12">
                    <label for="descripcion" class="form-label">Descripción:</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required><%= Html.esc(request.getAttribute("v_descripcion")) %></textarea>
                </div>

                <div class="col-md-6">
                    <label for="fechaInicio" class="form-label">Fecha de inicio:</label>
                    <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" required
                           value="<%= Html.esc(request.getAttribute("v_fInicio")) %>">
                </div>

                <div class="col-md-6">
                    <label for="fechaFin" class="form-label">Fecha de fin:</label>
                    <input type="date" class="form-control" id="fechaFin" name="fechaFin" required
                           value="<%= Html.esc(request.getAttribute("v_fFin")) %>">
                </div>

                    <div class="col-6">
                        <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-danger w-100 text-center">Cancelar</a>
                    </div>
                    <div class="col-6">
                        <button type="submit" class="btn btn-primary w-100">Aceptar</button>
                    </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />