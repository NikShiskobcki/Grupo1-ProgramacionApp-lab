<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    List<String> institutos = (List<String>) request.getAttribute("institutos");
    List<String> categorias = (List<String>) request.getAttribute("categorias");
    List<String> cursosExistentes = (List<String>) request.getAttribute("cursosExistentes");

    List<String> vPrevias = (List<String>) request.getAttribute("v_previas");
    List<String> vCategorias = (List<String>) request.getAttribute("v_categorias");
    String vInstituto = (String) request.getAttribute("v_instituto");
%>

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <div class="auth-box auth-box-large" style="margin-top:0;">
            <h2>Alta de Curso</h2>
            <p class="auth-subtitle">Registrá un nuevo curso en la plataforma.</p>

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error"><%= Html.esc(request.getAttribute("error")) %></div>
            <% } %>

            <form action="<%= request.getContextPath() %>/alta-curso" method="post" class="row g-3">

                <div class="col-md-6">
                    <label for="instituto" class="form-label">Instituto:</label>
                    <select class="form-select" id="instituto" name="instituto" required>
                        <% if (institutos != null) {
                            for (String inst : institutos) { %>
                                <option value="<%= Html.esc(inst) %>" <%= inst.equals(vInstituto) ? "selected" : "" %>><%= Html.esc(inst) %></option>
                        <%  }
                        } %>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="nombre" class="form-label">Nombre (único):</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required maxlength="100"
                           value="<%= Html.esc(request.getAttribute("v_nombre")) %>">
                </div>

                <div class="col-12">
                    <label for="descripcion" class="form-label">Descripción:</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required><%= Html.esc(request.getAttribute("v_descripcion")) %></textarea>
                </div>

                <div class="col-md-4">
                    <label for="duracion" class="form-label">Duración (meses):</label>
                    <input type="number" class="form-control" id="duracion" name="duracion" min="1" required
                           value="<%= Html.esc(request.getAttribute("v_duracion")) %>">
                </div>
                <div class="col-md-4">
                    <label for="cantidadHoras" class="form-label">Cantidad de horas:</label>
                    <input type="number" class="form-control" id="cantidadHoras" name="cantidadHoras" min="1" required
                           value="<%= Html.esc(request.getAttribute("v_horas")) %>">
                </div>
                <div class="col-md-4">
                    <label for="creditos" class="form-label">Créditos:</label>
                    <input type="number" class="form-control" id="creditos" name="creditos" min="1" required
                           value="<%= Html.esc(request.getAttribute("v_creditos")) %>">
                </div>

                <div class="col-12">
                    <label for="url" class="form-label">URL asociada:</label>
                    <input type="url" class="form-control" id="url" name="url" required placeholder="https://eva.fing.edu.uy/curso"
                           value="<%= Html.esc(request.getAttribute("v_url")) %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Previas (ninguna, una o más):</label>
                    <div class="border rounded p-2" style="max-height:170px; overflow-y:auto;">
                        <% if (cursosExistentes == null || cursosExistentes.isEmpty()) { %>
                            <small class="text-muted">No hay cursos registrados todavía.</small>
                        <% } else {
                            int i = 0;
                            for (String c : cursosExistentes) { i++; %>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="previas" id="prev<%= i %>"
                                       value="<%= Html.esc(c) %>" <%= (vPrevias != null && vPrevias.contains(c)) ? "checked" : "" %>>
                                <label class="form-check-label" for="prev<%= i %>"><%= Html.esc(c) %></label>
                            </div>
                        <%  }
                        } %>
                    </div>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Categorías:</label>
                    <div class="border rounded p-2" style="max-height:170px; overflow-y:auto;">
                        <% if (categorias == null || categorias.isEmpty()) { %>
                            <small class="text-muted">No hay categorías definidas.</small>
                        <% } else {
                            int j = 0;
                            for (String cat : categorias) { j++; %>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="categorias" id="cat<%= j %>"
                                       value="<%= Html.esc(cat) %>" <%= (vCategorias != null && vCategorias.contains(cat)) ? "checked" : "" %>>
                                <label class="form-check-label" for="cat<%= j %>"><%= Html.esc(cat) %></label>
                            </div>
                        <%  }
                        } %>
                    </div>
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
