<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.Entidades.Curso" %>
<%@ page import="Logica.DTO.DetalleProgramaFormacion" %>
<%@ page import="util.Html" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp" />

<%
    String query = (String) request.getAttribute("query");
    String orden = (String) request.getAttribute("orden");
    List<Curso> resultados = (List<Curso>) request.getAttribute("resultados");
    List<DetalleProgramaFormacion> programas = (List<DetalleProgramaFormacion>) request.getAttribute("programas");
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
    
    <h3 class="mt-4 mb-3">Cursos</h3>        
    <div class="cards-grid">
        <% if (resultados != null && !resultados.isEmpty()) { 
            for (Curso c : resultados) { %>
                <div class="card">
                    <div class="card-body">
                        <span class="card-badge"><%= c.getInstituto() != null ? c.getInstituto().getNombre() : "General" %></span>
                        <h3 class="card-title"><%= c.getNombre() %></h3>
                        <p class="card-desc"><%= c.getDescripcion() != null && c.getDescripcion().length() > 90 ? c.getDescripcion().substring(0, 90) + "..." : c.getDescripcion() %></p>
                        <p class="card-date"><small>Publicado: <%= c.getFechaAlta() %></small></p>
                        <a href="<%= request.getContextPath() %>/curso-detalle?nombre=<%= java.net.URLEncoder.encode(c.getNombre(), "UTF-8") %>" class="btn btn-primary btn-block">Ver Detalle</a>
                    </div>
                </div>
        <%  } 
        } else { %>
            <p>No se encontraron resultados que coincidan con el término de búsqueda.</p>
        <% } %>
    </div>
    <h3 class="mt-5 mb-3" id="programas">Programas de formación</h3>
<div class="cards-grid">
    <% if (programas != null && !programas.isEmpty()) {
        for (DetalleProgramaFormacion p : programas) { %>
            <div class="card">
                <div class="card-body">
                    <span class="card-badge">Programa de formación</span>
                    <h3 class="card-title"><%= Html.esc(p.getNombre()) %></h3>
                    <p class="card-desc"><%= p.getDescripcion() != null && p.getDescripcion().length() > 90
                            ? Html.esc(p.getDescripcion().substring(0, 90)) + "..."
                            : Html.esc(p.getDescripcion()) %></p>
                    <p class="card-date"><small>Vigencia: <%= Html.esc(p.getFechaInicio()) %> a <%= Html.esc(p.getFechaFin()) %></small></p>
                    <a href="<%= request.getContextPath() %>/programa-detalle?nombre=<%= Html.url(p.getNombre()) %>" class="btn btn-primary btn-block">Ver Detalle</a>
                </div>
            </div>
    <%  }
    } else { %>
        <p>No se encontraron programas de formación que coincidan con el término de búsqueda.</p>
    <% } %>
</div>
</div>

<jsp:include page="footer.jsp" />
