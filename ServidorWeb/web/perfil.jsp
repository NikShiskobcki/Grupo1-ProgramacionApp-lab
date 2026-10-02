<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Logica.DTO.DetalleUsuario" %>
<%@ page import="Logica.DTO.UsuarioResumen" %>
<jsp:include page="header.jsp" />

<%
    DetalleUsuario perfil = (DetalleUsuario) request.getAttribute("detalleUsuario");
    Boolean esPropio = (Boolean) request.getAttribute("esPropioPerfil");
    if (esPropio == null) esPropio = false;
%>

<% if (perfil != null) { %>
<div class="profile-container">
    <div class="profile-header">
        <div class="profile-avatar">👤</div>
        <div class="profile-info">
            <h2><%= perfil.getNombre() %> <%= perfil.getApellido() %></h2>
            <p class="profile-nick">@<%= perfil.getNickname() %> &bull; <span class="badge"><%= perfil.getTipoUsuario() %></span></p>
            <p class="profile-mail">✉ <%= perfil.getEmail() %></p>
            <% if (perfil.getInstituto() != null && !perfil.getInstituto().isEmpty()) { %>
                <p>🏛 Instituto: <strong><%= perfil.getInstituto() %></strong></p>
            <% } %>
        </div>
    </div>

    <!-- Pestañas (Requerimiento Especial 7.3) -->
    <div class="tabs-wrapper">
        <div class="tabs-header">
            <button class="tab-btn active" onclick="openTab(event, 'tab-general')">Datos Generales</button>
            <% if ("Docente".equalsIgnoreCase(perfil.getTipoUsuario())) { %>
                <button class="tab-btn" onclick="openTab(event, 'tab-ediciones')">Ediciones Dictadas</button>
            <% } else { %>
                <button class="tab-btn" onclick="openTab(event, 'tab-inscripciones')">Inscripciones</button>
                <button class="tab-btn" onclick="openTab(event, 'tab-programas')">Programas</button>
            <% } %>
        </div>

        <div id="tab-general" class="tab-content active">
            <div class="card-simple">
                <h3>Información de la Cuenta</h3>
                <p><strong>Nickname:</strong> <%= perfil.getNickname() %></p>
                <p><strong>Nombre Completo:</strong> <%= perfil.getNombre() %> <%= perfil.getApellido() %></p>
                <p><strong>Correo Electrónico:</strong> <%= perfil.getEmail() %></p>
                <p><strong>Fecha de Nacimiento:</strong> <%= perfil.getFechaNacimiento() %></p>
                <p><strong>Tipo de Usuario:</strong> <%= perfil.getTipoUsuario() %></p>
            </div>
        </div>

        <% if ("Docente".equalsIgnoreCase(perfil.getTipoUsuario())) { %>
            <div id="tab-ediciones" class="tab-content">
                <h3>Ediciones en las que participa</h3>
                <% if (perfil.getEdiciones() != null && !perfil.getEdiciones().isEmpty()) { %>
                    <ul class="list-group">
                        <% for (String ed : perfil.getEdiciones()) { %>
                            <li><%= ed %></li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p>No tiene ediciones asignadas actualmente.</p>
                <% } %>
            </div>
        <% } else { %>
            <div id="tab-inscripciones" class="tab-content">
                <h3>Inscripciones a Ediciones de Cursos</h3>
                <% if (perfil.getEdiciones() != null && !perfil.getEdiciones().isEmpty()) { %>
                    <ul class="list-group">
                        <% for (String ed : perfil.getEdiciones()) { %>
                            <li><%= ed %></li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p>No tiene inscripciones registradas.</p>
                <% } %>
            </div>

            <div id="tab-programas" class="tab-content">
                <h3>Programas de Formación</h3>
                <% if (perfil.getProgramas() != null && !perfil.getProgramas().isEmpty()) { %>
                    <ul class="list-group">
                        <% for (String prog : perfil.getProgramas()) { %>
                            <li><%= prog %></li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p>No tiene programas asociados.</p>
                <% } %>
            </div>
        <% } %>
    </div>
</div>
<% } else { %>
    <p>Perfil de usuario no disponible.</p>
<% } %>

<jsp:include page="footer.jsp" />
