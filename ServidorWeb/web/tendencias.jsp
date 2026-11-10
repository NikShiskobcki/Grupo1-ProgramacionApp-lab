<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.CursoTendencia" %>
<%@ page import="util.Html" %>
<%
    List<CursoTendencia> tendencias
            = (List<CursoTendencia>) request.getAttribute("tendencias");
    boolean hayError = Boolean.TRUE.equals(request.getAttribute("tendenciasError"));
    String ctx = request.getContextPath();
%>

<section class="section-container mt-4" aria-labelledby="tituloTendencias">
    <h2 id="tituloTendencias">Cursos en tendencia</h2>

    <% if (hayError) { %>
    <p class="text-muted">No se pudieron cargar las tendencias en este momento.</p>

    <% } else if (tendencias == null || tendencias.isEmpty()) { %>
    <p class="text-muted">
        Todavía no hay tendencias. Explorá los cursos y aparecerán acá.
    </p>

    <% } else { %>
    <ol class="ranking">
        <% int puesto = 1;
            for (CursoTendencia c : tendencias) {
                String img = Html.imagen(ctx, c.getRutaImagen());
                String nombre = c.getNombre() == null ? "" : c.getNombre();
                    String inicial = nombre.isEmpty() ? "?" : nombre.substring(0, 1).toUpperCase();%>
        <li class="ranking-item">
            <a class="ranking-link"
               href="<%= ctx%>/curso-detalle?nombre=<%= Html.url(nombre)%>">

                <span class="ranking-puesto"><%= puesto%></span>

                <% if (!img.isEmpty()) {%>
                <img src="<%= img%>" alt="" class="ranking-img" loading="lazy">
                <% } else {%>
                <span class="ranking-img ranking-img-vacia" aria-hidden="true">
                    <%= Html.esc(inicial)%>
                </span>
                <% }%>

                <span class="ranking-info">
                    <h3 class="ranking-nombre"><%= Html.esc(nombre)%></h3>
                    <% if (c.getNombreInstituto() != null) {%>
                    <span class="ranking-instituto">
                        <%= Html.esc(c.getNombreInstituto())%>
                    </span>
                    <% }%>
                </span>

                <span class="ranking-visitas">
                    <strong><%= c.getVisitas()%></strong>
                    <%= c.getVisitas() == 1 ? "visita" : "visitas"%>
                </span>
            </a>
        </li>
        <%   puesto++;
                } %>
    </ol>
    <% }%>
</section>