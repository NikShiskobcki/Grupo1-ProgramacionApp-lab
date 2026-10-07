<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.Entidades.Curso" %>
<%@ page import="java.util.List" %>
<%@ page import="util.Html" %>
<jsp:include page="header.jsp" />

<%
    List<String> institutos = (List<String>) request.getAttribute("institutos");
    List<String> categorias = (List<String>) request.getAttribute("categorias");
    List<Curso> resultados = (List<Curso>) request.getAttribute("resultados");
    String institutoSel = (String) request.getAttribute("institutoSel");
    String categoriaSel = (String) request.getAttribute("categoriaSel");
    String ctx = request.getContextPath();
%>

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <h2>Consulta de Curso</h2>
        <p class="auth-subtitle" style="text-align:left;">Elegí un instituto o una categoría para ver los cursos asociados.</p>

        <div class="row g-3 mb-4">
            <div class="col-md-6">
                <form action="<%= ctx %>/consulta-curso" method="get">
                    <label for="instituto" class="form-label">Instituto:</label>
                    <div class="input-group">
                        <select class="form-select" id="instituto" name="instituto" required>
                            <option value="">-- Seleccionar --</option>
                            <% if (institutos != null) { for (String inst : institutos) { %>
                                <option value="<%= Html.esc(inst) %>" <%= inst.equals(institutoSel) ? "selected" : "" %>><%= Html.esc(inst) %></option>
                            <% } } %>
                        </select>
                        <button type="submit" class="btn btn-primary">Ver cursos</button>
                    </div>
                </form>
            </div>
            <div class="col-md-6">
                <form action="<%= ctx %>/consulta-curso" method="get">
                    <label for="categoria" class="form-label">Categoría:</label>
                    <div class="input-group">
                        <select class="form-select" id="categoria" name="categoria" required>
                            <option value="">-- Seleccionar --</option>
                            <% if (categorias != null) { for (String cat : categorias) { %>
                                <option value="<%= Html.esc(cat) %>" <%= cat.equals(categoriaSel) ? "selected" : "" %>><%= Html.esc(cat) %></option>
                            <% } } %>
                        </select>
                        <button type="submit" class="btn btn-primary">Ver cursos</button>
                    </div>
                </form>
            </div>
        </div>

        <% if (resultados != null) { %>
            <h4>
                Cursos de
                <%= (institutoSel != null && !institutoSel.isEmpty())
                        ? "el instituto " + Html.esc(institutoSel)
                        : "la categoría " + Html.esc(categoriaSel) %>
            </h4>

            <div class="cards-grid">
                <% if (!resultados.isEmpty()) {
                    for (Curso c : resultados) { %>
                    <div class="card">
                        <div class="card-body">
                            <span class="card-badge"><%= c.getInstituto() != null ? Html.esc(c.getInstituto().getNombre()) : "General" %></span>
                            <h3 class="card-title"><%= Html.esc(c.getNombre()) %></h3>
                            <p class="card-desc">
                                <%= c.getDescripcion() != null && c.getDescripcion().length() > 90
                                        ? Html.esc(c.getDescripcion().substring(0, 90)) + "..."
                                        : Html.esc(c.getDescripcion()) %>
                            </p>
                            <a href="<%= ctx %>/curso-detalle?nombre=<%= Html.url(c.getNombre()) %>" class="btn btn-primary btn-block">Ver Detalle</a>
                        </div>
                    </div>
                <%  }
                } else { %>
                    <p>No hay cursos asociados a la selección.</p>
                <% } %>
            </div>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />
