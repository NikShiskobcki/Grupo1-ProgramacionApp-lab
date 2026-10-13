<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="util.Html" %>

<%
    List<String> institutos =
            (List<String>) request.getAttribute("institutos");

    String[] docentesSeleccionados =
            request.getParameterValues("docentes");

    List<String> seleccionados = docentesSeleccionados == null
            ? java.util.Collections.emptyList()
            : Arrays.asList(docentesSeleccionados);
%>

<jsp:include page="header.jsp" />

<div class="home-layout">
    <jsp:include page="sidebarIzq.jsp" />

    <div class="home-content">
        <div class="auth-box auth-box-large" style="margin-top:0;">
            <h2>Alta de Edición de Curso</h2>

            <p class="auth-subtitle">
                Registrá una nueva edición y seleccioná sus docentes.
            </p>

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error" role="alert">
                    <%= Html.esc(request.getAttribute("error")) %>
                </div>
            <% } %>

            <form id="formAltaEdicion"
                  action="${pageContext.request.contextPath}/alta-edicion"
                  method="post"
                  class="row g-3">

                <div class="col-md-6">
                    <label for="instituto" class="form-label">
                        Instituto:
                    </label>

                    <select id="instituto" name="instituto"
                            class="form-select" required
                            aria-describedby="errorInstituto">
                        <option value="">Seleccioná un instituto</option>

                        <% if (institutos != null) {
                            for (String inst : institutos) { %>
                                <option value="<%= Html.esc(inst) %>"
                                        <%= inst.equals(request.getParameter("instituto"))
                                                ? "selected" : "" %>>
                                    <%= Html.esc(inst) %>
                                </option>
                        <%  }
                        } %>
                    </select>

                    <div id="errorInstituto" class="invalid-feedback">
                        Seleccioná un instituto.
                    </div>
                </div>

                <div class="col-md-6">
                    <label for="curso" class="form-label">Curso:</label>

                    <select id="curso" name="curso"
                            class="form-select" required disabled
                            data-seleccionado="<%= Html.esc(request.getParameter("curso")) %>"
                            aria-describedby="errorCurso">
                        <option value="">
                            Primero seleccioná un instituto
                        </option>
                    </select>

                    <div id="errorCurso" class="invalid-feedback">
                        Seleccioná un curso.
                    </div>

                    <div id="estadoOpciones"
                         class="text-muted small mt-1"
                         role="status"></div>
                </div>

                <div class="col-12">
                    <label for="nombre" class="form-label">
                        Nombre de la edición:
                    </label>

                    <input type="text" id="nombre" name="nombre"
                           class="form-control" required maxlength="255"
                           value="<%= Html.esc(request.getParameter("nombre")) %>"
                           aria-describedby="errorNombre">

                    <div id="errorNombre" class="invalid-feedback">
                        Ingresá el nombre de la edición.
                    </div>
                </div>

                <div class="col-md-4">
                    <label for="fechaInicio" class="form-label">
                        Fecha de inicio:
                    </label>

                    <input type="date" id="fechaInicio" name="fechaInicio"
                           class="form-control" required
                           min="<%= Html.esc(request.getAttribute("hoy")) %>"
                           value="<%= Html.esc(request.getParameter("fechaInicio")) %>"
                           aria-describedby="errorFechaInicio">

                    <div id="errorFechaInicio" class="invalid-feedback">
                        La fecha de inicio no puede ser anterior a hoy.
                    </div>
                </div>

                <div class="col-md-4">
                    <label for="fechaFin" class="form-label">
                        Fecha de fin:
                    </label>

                    <input type="date" id="fechaFin" name="fechaFin"
                           class="form-control" required
                           min="<%= Html.esc(request.getAttribute("hoy")) %>"
                           value="<%= Html.esc(request.getParameter("fechaFin")) %>"
                           aria-describedby="errorFechaFin">

                    <div id="errorFechaFin" class="invalid-feedback">
                        La fecha de fin debe ser igual o posterior a la de inicio.
                    </div>
                </div>

                <div class="col-md-4">
                    <label for="cupo" class="form-label">
                        Cupo (opcional):
                    </label>

                    <input type="number" id="cupo" name="cupo"
                           class="form-control" min="1"
                           max="2147483647" step="1"
                           value="<%= Html.esc(request.getParameter("cupo")) %>"
                           aria-describedby="ayudaCupo errorCupo">

                    <small id="ayudaCupo" class="text-muted">
                        Dejalo vacío si no hay cupo definido.
                    </small>

                    <div id="errorCupo" class="invalid-feedback">
                        El cupo debe ser un entero mayor que cero.
                    </div>
                </div>

                <div class="col-12">
                    <fieldset>
                        <legend class="form-label fs-6">
                            Docentes participantes:
                        </legend>

                        <div id="docentesDisponibles"
                             class="border rounded p-2"
                             style="max-height:200px; overflow-y:auto;">
                            <span class="text-muted">
                                Seleccioná un instituto para ver sus docentes.
                            </span>
                        </div>

                        <div id="errorDocentes"
                             class="text-danger small mt-1"
                             role="alert" hidden>
                            Seleccioná al menos un docente.
                        </div>
                    </fieldset>
                </div>

                <div id="docentesAnteriores" hidden>
                    <% for (String nickname : seleccionados) { %>
                        <input type="hidden"
                               value="<%= Html.esc(nickname) %>">
                    <% } %>
                </div>

                <div class="col-12">
                    <label for="imagen" class="form-label">
                        URL de la imagen (opcional):
                    </label>

                    <input type="url" id="imagen" name="imagen"
                           class="form-control" placeholder="https://..."
                           value="<%= Html.esc(request.getParameter("imagen")) %>"
                           aria-describedby="errorImagen">

                    <div id="errorImagen" class="invalid-feedback">
                        Ingresá una URL http:// o https:// válida.
                    </div>

                    <div id="contenedorVistaPrevia" class="mt-3" hidden>
                        <img id="vistaPreviaImagen"
                             alt="Vista previa de la imagen de la edición"
                             class="img-fluid rounded"
                             style="max-height:200px;">
                    </div>

                    <div id="avisoImagen"
                         class="text-muted small mt-1"
                         role="status" hidden>
                        No se pudo cargar la vista previa.
                        Verificá la dirección.
                    </div>
                </div>

                <div class="col-6">
                    <a href="${pageContext.request.contextPath}/index.jsp"
                       class="btn btn-danger w-100 text-center">
                        Cancelar
                    </a>
                </div>

                <div class="col-6">
                    <button type="submit" id="btnAceptar"
                            class="btn btn-primary w-100">
                        Aceptar
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formAltaEdicion");
    const instituto = document.getElementById("instituto");
    const curso = document.getElementById("curso");
    const nombre = document.getElementById("nombre");
    const inicio = document.getElementById("fechaInicio");
    const fin = document.getElementById("fechaFin");
    const cupo = document.getElementById("cupo");
    const imagen = document.getElementById("imagen");
    const docentes = document.getElementById("docentesDisponibles");
    const errorDocentes = document.getElementById("errorDocentes");
    const estadoOpciones = document.getElementById("estadoOpciones");
    const aceptar = document.getElementById("btnAceptar");
    const contenedorPreview = document.getElementById("contenedorVistaPrevia");
    const preview = document.getElementById("vistaPreviaImagen");
    const avisoImagen = document.getElementById("avisoImagen");

    const campos = [instituto, curso, nombre, inicio, fin, cupo, imagen];
    const revisados = new Set();
    const hoy = inicio.min;

    let solicitudOpciones;
    let cargando = false;
    let esperaImagen;
    let versionImagen = 0;

    form.noValidate = true;

    function urlValida(valor) {
        try {
            const url = new URL(valor);
            return ["http:", "https:"].includes(url.protocol)
                    && Boolean(url.hostname)
                    && !url.username
                    && !url.password;
        } catch {
            return false;
        }
    }

    function actualizarMinimoFin() {
        fin.min = inicio.value && inicio.value >= hoy
                ? inicio.value : hoy;
    }

    function validar(campo) {
        let mensaje = "";
        const valor = campo.value.trim();

        if (campo === instituto && !valor) {
            mensaje = "Seleccioná un instituto.";

        } else if (campo === curso && (campo.disabled || !valor)) {
            mensaje = "Seleccioná un curso disponible.";

        } else if (campo === nombre) {
            if (!valor) {
                mensaje = "Ingresá el nombre de la edición.";
            } else if (valor.length > 255) {
                mensaje = "El nombre admite hasta 255 caracteres.";
            }

        } else if (campo === inicio) {
            if (!valor || campo.validity.badInput) {
                mensaje = "Ingresá una fecha de inicio válida.";
            } else if (valor < hoy) {
                mensaje = "La fecha de inicio no puede ser anterior a hoy.";
            }

        } else if (campo === fin) {
            if (!valor || campo.validity.badInput) {
                mensaje = "Ingresá una fecha de fin válida.";
            } else if (valor < hoy) {
                mensaje = "La fecha de fin no puede ser anterior a hoy.";
            } else if (inicio.value && valor < inicio.value) {
                mensaje = "La fecha de fin no puede ser anterior a la de inicio.";
            }

        } else if (campo === cupo) {
            if (campo.validity.badInput ||
                (valor && (!/^[0-9]+$/.test(valor) ||
                           Number(valor) < 1 ||
                           Number(valor) > 2147483647))) {
                mensaje = "El cupo debe ser un entero entre 1 y 2147483647.";
            }

        } else if (campo === imagen && valor && !urlValida(valor)) {
            mensaje = "Ingresá una URL http:// o https:// válida.";
        }

        campo.setCustomValidity(mensaje);
        campo.classList.toggle("is-invalid", mensaje !== "");
        campo.setAttribute("aria-invalid", String(mensaje !== ""));

        const aviso = campo.parentElement.querySelector(".invalid-feedback");
        if (aviso) aviso.textContent = mensaje;

        return mensaje === "";
    }

    function validarDocentes() {
        const seleccionado = docentes.querySelector(
                'input[name="docentes"]:checked');

        errorDocentes.hidden = Boolean(seleccionado);
        return Boolean(seleccionado);
    }

    campos.forEach(campo => {
        campo.addEventListener("blur", () => {
            revisados.add(campo);
            validar(campo);
        });

        campo.addEventListener("input", () => {
            // Los errores aparecen mientras escribís.
            revisados.add(campo);
            validar(campo);

            if (campo === inicio) {
                actualizarMinimoFin();
                if (fin.value || revisados.has(fin)) validar(fin);
            }
        });

        campo.addEventListener("change", () => {
            revisados.add(campo);
            validar(campo);

            if (campo === inicio) {
                actualizarMinimoFin();
                if (fin.value || revisados.has(fin)) validar(fin);
            }
        });
    });

    docentes.addEventListener("change", validarDocentes);

    function actualizarPreview() {
        clearTimeout(esperaImagen);
        const version = ++versionImagen;

        contenedorPreview.hidden = true;
        avisoImagen.hidden = true;
        preview.removeAttribute("src");

        const url = imagen.value.trim();
        if (!url || !urlValida(url)) return;

        esperaImagen = setTimeout(() => {
            const prueba = new Image();

            prueba.onload = () => {
                if (version !== versionImagen) return;
                preview.src = url;
                contenedorPreview.hidden = false;
                avisoImagen.hidden = true;
            };

            prueba.onerror = () => {
                if (version !== versionImagen) return;
                avisoImagen.hidden = false;
            };

            prueba.src = url;
        }, 500);
    }

    imagen.addEventListener("input", actualizarPreview);
    imagen.addEventListener("change", actualizarPreview);

    async function cargarOpciones(cursoAnterior = "", docentesAnteriores = []) {
        if (solicitudOpciones) solicitudOpciones.abort();

        cargando = false;
        aceptar.disabled = false;

        curso.disabled = true;
        curso.replaceChildren(
                new Option("Primero seleccioná un instituto", ""));

        curso.classList.remove("is-invalid");
        curso.setAttribute("aria-invalid", "false");
        curso.setCustomValidity("");

        docentes.replaceChildren();
        errorDocentes.hidden = true;
        estadoOpciones.textContent = "";

        if (!instituto.value) {
            docentes.textContent =
                    "Seleccioná un instituto para ver sus docentes.";
            return;
        }

        const solicitud = new AbortController();
        solicitudOpciones = solicitud;

        cargando = true;
        aceptar.disabled = true;
        curso.replaceChildren(new Option("Cargando cursos...", ""));
        docentes.textContent = "Cargando docentes...";
        estadoOpciones.textContent = "Cargando opciones del instituto…";

        try {
            const url = new URL(form.action);
            url.searchParams.set("accion", "opciones");
            url.searchParams.set("instituto", instituto.value);

            const respuesta = await fetch(url, {
                signal: solicitud.signal,
                headers: { "Accept": "application/json" }
            });

            if (!respuesta.ok) {
                let mensaje = "No se pudieron cargar los datos.";

                try {
                    const error = await respuesta.json();
                    if (error.error) mensaje = error.error;
                } catch {
                    // Conservamos el mensaje general.
                }

                throw new Error(mensaje);
            }

            const datos = await respuesta.json();
            if (solicitud.signal.aborted) return;

            curso.replaceChildren(new Option(
                    datos.cursos.length
                        ? "Seleccioná un curso"
                        : "No hay cursos en este instituto",
                    ""
            ));

            datos.cursos.forEach(nombreCurso => {
                curso.add(new Option(nombreCurso, nombreCurso));
            });

            curso.disabled = datos.cursos.length === 0;

            if (datos.cursos.includes(cursoAnterior)) {
                curso.value = cursoAnterior;
            }

            docentes.replaceChildren();

            if (!datos.docentes.length) {
                docentes.textContent = "No hay docentes en este instituto.";
            }

            datos.docentes.forEach((docente, indice) => {
                const fila = document.createElement("div");
                fila.className = "form-check";

                const casilla = document.createElement("input");
                casilla.type = "checkbox";
                casilla.name = "docentes";
                casilla.value = docente.nickname;
                casilla.id = "docente-" + indice;
                casilla.className = "form-check-input";
                casilla.checked =
                        docentesAnteriores.includes(docente.nickname);

                const etiqueta = document.createElement("label");
                etiqueta.htmlFor = casilla.id;
                etiqueta.className = "form-check-label";
                etiqueta.textContent =
                        docente.nombre + " (" + docente.nickname + ")";

                fila.append(casilla, etiqueta);
                docentes.append(fila);
            });

            estadoOpciones.textContent = datos.cursos.length
                    ? ""
                    : "Este instituto todavía no tiene cursos.";

        } catch (error) {
            if (error.name === "AbortError") return;

            curso.replaceChildren(
                    new Option("No se pudieron cargar los cursos", ""));

            docentes.textContent =
                    "Volvé a seleccionar el instituto para reintentar.";

            estadoOpciones.textContent = error.message;

        } finally {
            if (solicitudOpciones === solicitud) {
                cargando = false;
                aceptar.disabled = false;
            }
        }
    }

    instituto.addEventListener("change", () => cargarOpciones());

    form.addEventListener("submit", event => {
        if (cargando) {
            event.preventDefault();
            return;
        }

        let primeroInvalido = null;

        campos.forEach(campo => {
            revisados.add(campo);

            if (!validar(campo) && !primeroInvalido) {
                primeroInvalido = campo;
            }
        });

        const docentesValidos = validarDocentes();

        if (primeroInvalido || !docentesValidos) {
            event.preventDefault();

            if (primeroInvalido) {
                (primeroInvalido.disabled
                    ? instituto : primeroInvalido).focus();
            } else {
                docentes.querySelector("input")?.focus();
                errorDocentes.scrollIntoView({ block: "center" });
            }

            return;
        }

        nombre.value = nombre.value.trim();
        imagen.value = imagen.value.trim();
        aceptar.disabled = true;
        aceptar.textContent = "Guardando…";
    });

    if (instituto.value) {
        const docentesAnteriores = Array.from(
                document.querySelectorAll("#docentesAnteriores input"),
                campo => campo.value
        );

        cargarOpciones(curso.dataset.seleccionado, docentesAnteriores);
    }

    actualizarMinimoFin();
    actualizarPreview();

    // Recupera el botón si volvés usando el historial del navegador.
    window.addEventListener("pageshow", () => {
        aceptar.disabled = cargando;
        aceptar.textContent = "Aceptar";
    });
});
</script>

<jsp:include page="footer.jsp" />