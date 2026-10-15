</main>
<footer class="main-footer">
    <div class="footer-container">
        <p>&copy; 2026 edExt - Programacion de Aplicaciones (Grupo 1) - UTEC Maldonado</p>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<%
    String vJs = "";
    String rutaJs = application.getRealPath("/js/main.js");
    if (rutaJs != null) vJs = "?v=" + new java.io.File(rutaJs).lastModified();
%>
<script src="<%= request.getContextPath() %>/js/main.js<%= vJs %>"></script>
</body>
</html>
