<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <div class="hero-banner">
            <h2>Bienvenido a ed<span>Ext</span></h2>
            <a href="<%= request.getContextPath() %>/buscar" class="btn btn-outline">Explorar cursos</a>
        </div>
    </div>

    <jsp:include page="sidebarDer.jsp" />
</div>

<jsp:include page="footer.jsp" />