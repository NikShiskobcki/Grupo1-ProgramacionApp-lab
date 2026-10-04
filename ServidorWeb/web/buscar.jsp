<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.Entidades.Curso" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp" />

<%
    String query = (String) request.getAttribute("query");
    String orden = (String) request.getAttribute("orden");
    List<Curso> resultados = (List<Curso>) request.getAttribute("resultados");
%>

<div class="search-page-container">
    <h2>Resultados de Búsqueda para: "<em><%= query %></em>"</h2>

    <div class="search-controls">
        <form action="<%= request.getContextPath() %>/buscar" method="get" class="filter-form">
            <input type="hidden" name="q" value="<%= query %>">
            <label for="orden">Ordenar por:</label>
            <select name="orden" id="orden" onchange="this.form.submit()">
                <option value="alfa" <%= "alfa".equals(orden) ? "selected" : "" %>>Alfabéticamente (A-Z)</option>
                <option value="fecha" <%= "fecha".equals(orden) ? "selected" : "" %>>Fecha de Publicación (Más recientes)</option>
            </select>
        </form>
    </div>

    <div class="cards-grid">
        <% if (resultados != null && !resultados.isEmpty()) { 
            for (Curso c : resultados) { %>
                <div class="card">
                    <div class="card-body">
                        <span class="card-badge"><%= c.getInstituto() != null ? c.getInstituto().getNombre() : "General" %></span>
                        <h3 class="card-title"><%= c.getNombre() %></h3>
                        <p class="card-desc"><%= c.getDescripcion() != null && c.getDescripcion().length() > 90 ? c.getDescripcion().substring(0, 90) + "..." : c.getDescripcion() %></p>
                        <p class="card-date"><small>📅 Publicado: <%= c.getFechaAlta() %></small></p>
                        <a href="<%= request.getContextPath() %>/curso-detalle?nombre=<%= java.net.URLEncoder.encode(c.getNombre(), "UTF-8") %>" class="btn btn-primary btn-block">Ver Detalle</a>
                    </div>
                </div>
        <%  } 
        } else { %>
            <p>No se encontraron resultados que coincidan con el término de búsqueda.</p>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />
