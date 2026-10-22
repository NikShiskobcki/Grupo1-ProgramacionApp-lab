// Utilidades de interfaz (edExt)

// Si una imagen no carga, se reemplaza por su placeholder (elemento siguiente).
function imgFallback(img) {
    img.style.display = 'none';
    var ph = img.nextElementSibling;
    if (ph) ph.style.display = 'flex';
}

// Activa una pestaña del perfil a partir del hash de la URL (ej. perfil#tab-programas).
document.addEventListener('DOMContentLoaded', function () {
    if (!window.bootstrap) return;
    var hash = window.location.hash;
    if (hash) {
        var btn = document.querySelector('[data-bs-toggle="tab"][data-bs-target="' + hash + '"]');
        if (btn) bootstrap.Tab.getOrCreateInstance(btn).show();
    }
    // Filtro de texto en listas (ej. lista de usuarios)
    var filtro = document.getElementById('filtroLista');
    if (filtro) {
        filtro.addEventListener('input', function () {
            var q = filtro.value.toLowerCase();
            document.querySelectorAll('[data-filtro]').forEach(function (el) {
                el.style.display = el.getAttribute('data-filtro').indexOf(q) >= 0 ? '' : 'none';
            });
        });
    }
});

// Validación de formularios con Bootstrap (clase .needs-validation), sin recargar la página.
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('form.needs-validation').forEach(function (form) {
        form.addEventListener('submit', function (ev) {
            if (!form.checkValidity()) {
                ev.preventDefault();
                ev.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });
});

// Sugerencias de búsqueda (AJAX) en el header: GET {ctx}/buscar-sugerencias?q=...
document.addEventListener('DOMContentLoaded', function () {
    var form = document.querySelector('.header-search form');
    if (!form) return;
    var input = form.querySelector('input[name="q"]');
    if (!input) return;

    var endpoint = form.getAttribute('action').replace(/\/buscar$/, '/buscar-sugerencias');
    var lista = document.createElement('ul');
    lista.className = 'search-suggestions';
    lista.setAttribute('role', 'listbox');
    lista.hidden = true;
    form.parentNode.style.position = 'relative';
    form.parentNode.appendChild(lista);
    input.setAttribute('autocomplete', 'off');

    var timer = null, controller = null, activo = -1, items = [];

    function esc(s) {
        return String(s).replace(/[&<>"']/g, function (c) {
            return {'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'}[c];
        });
    }
    function cerrar() { lista.hidden = true; activo = -1; }
    function marcar(i) {
        var lis = lista.querySelectorAll('li');
        lis.forEach(function (li, k) { li.classList.toggle('active', k === i); });
        activo = i;
    }
    function mostrar(datos) {
        items = datos;
        if (!datos.length) {
            lista.innerHTML = '<li class="sin-resultados">Sin coincidencias</li>';
            lista.hidden = false;
            return;
        }
        lista.innerHTML = datos.map(function (d) {
            return '<li role="option"><a href="' + esc(d.url) + '">' +
                '<span class="sug-tipo sug-' + esc(d.tipo.toLowerCase()) + '">' + esc(d.tipo) + '</span>' +
                '<span class="sug-texto"><span class="sug-nombre">' + esc(d.nombre) + '</span>' +
                '<span class="sug-detalle">' + esc(d.detalle) + '</span></span></a></li>';
        }).join('');
        lista.hidden = false;
        activo = -1;
    }
    function buscar() {
        var q = input.value.trim();
        if (q.length < 2) { cerrar(); return; }
        if (controller) controller.abort();
        controller = window.AbortController ? new AbortController() : null;
        fetch(endpoint + '?q=' + encodeURIComponent(q),
              controller ? {signal: controller.signal, headers: {'Accept': 'application/json'}} : {})
            .then(function (r) { if (!r.ok) throw new Error(r.status); return r.json(); })
            .then(mostrar)
            .catch(function (e) {
                if (e.name === 'AbortError') return;
                console.error('Sugerencias de búsqueda: fallo al consultar ' + endpoint, e);
                lista.innerHTML = '<li class="sin-resultados">No se pudieron cargar las sugerencias</li>';
                lista.hidden = false;
            });
    }

    input.addEventListener('input', function () {
        clearTimeout(timer);
        timer = setTimeout(buscar, 250);
    });
    input.addEventListener('focus', function () { if (input.value.trim().length >= 2) buscar(); });
    input.addEventListener('keydown', function (ev) {
        if (lista.hidden) return;
        var n = lista.querySelectorAll('li[role="option"]').length;
        if (ev.key === 'ArrowDown') { ev.preventDefault(); marcar((activo + 1) % n); }
        else if (ev.key === 'ArrowUp') { ev.preventDefault(); marcar((activo - 1 + n) % n); }
        else if (ev.key === 'Escape') { cerrar(); }
        else if (ev.key === 'Enter' && activo >= 0) {
            ev.preventDefault();
            window.location.href = items[activo].url;
        }
    });
    document.addEventListener('click', function (ev) {
        if (!form.parentNode.contains(ev.target)) cerrar();
    });
});


// Seguir / dejar de seguir (AJAX) en perfil.jsp: POST {ctx}/seguir-usuario sin recargar la página.
// El servlet responde JSON cuando la petición trae la cabecera X-Requested-With.
// Si el navegador no soporta fetch o este script falla, el formulario se envía normalmente
// (el servlet redirige a /perfil?user=...&msg=...), así que la funcionalidad no se rompe.
document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('formSeguir');
    if (!form || !window.fetch || !window.URLSearchParams || !window.FormData) return;

    var btn = document.getElementById('btnSeguir');
    var alertas = document.getElementById('alertas-seguir');
    var enCurso = false;

    var ICONO_PERSONA = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" ' +
        'viewBox="0 0 16 16"><path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/></svg>';

    // Cartel arriba de la página (reemplaza a los que haya, incluidos los que vienen de ?msg=)
    function mostrarAlerta(texto, tipo) {
        if (!alertas) return;
        var contenedor = alertas.parentNode;
        Array.prototype.forEach.call(contenedor.querySelectorAll(':scope > .alert'), function (a) { a.remove(); });
        alertas.textContent = '';
        var div = document.createElement('div');
        div.className = 'alert alert-' + tipo + ' alert-dismissible fade show';
        div.setAttribute('role', 'alert');
        div.appendChild(document.createTextNode(texto));
        var cerrar = document.createElement('button');
        cerrar.type = 'button';
        cerrar.className = 'btn-close';
        cerrar.setAttribute('data-bs-dismiss', 'alert');
        cerrar.setAttribute('aria-label', 'Cerrar');
        div.appendChild(cerrar);
        alertas.appendChild(div);
    }

    // El mismo botón alterna entre "Seguir" y "Dejar de seguir"
    function actualizarBoton(siguiendo) {
        form.elements['accion'].value = siguiendo ? 'dejar' : 'seguir';
        btn.className = 'btn ' + (siguiendo ? 'btn-outline-secondary' : 'btn-primary');
        btn.textContent = siguiendo ? '✖ Dejar de seguir' : '＋ Seguir';
    }

    // Tarjeta de usuario igual a la que genera perfil.jsp (con DOM, sin innerHTML: los textos son datos)
    function crearTarjeta(yo) {
        var col = document.createElement('div');
        col.className = 'col-12 col-md-6';
        col.setAttribute('data-nick', yo.nickname);

        var a = document.createElement('a');
        a.href = yo.url;
        a.className = 'edext-panel p-3 d-flex align-items-center gap-3 text-decoration-none h-100 text-reset';

        var ph = document.createElement('span');
        ph.className = 'avatar-sm-ph';
        ph.innerHTML = ICONO_PERSONA;               // constante propia, no viene del servidor
        if (yo.imagen) {
            var img = document.createElement('img');
            img.src = yo.imagen;
            img.alt = '';
            img.className = 'avatar-sm';
            img.onerror = function () { imgFallback(img); };
            ph.style.display = 'none';
            a.appendChild(img);
        }
        a.appendChild(ph);

        var datos = document.createElement('div');
        datos.className = 'flex-grow-1 text-truncate';
        var nombre = document.createElement('div');
        nombre.className = 'fw-semibold text-truncate';
        nombre.textContent = yo.nombre;
        var nick = document.createElement('div');
        nick.className = 'small text-muted';
        nick.textContent = '@' + yo.nickname;
        datos.appendChild(nombre);
        datos.appendChild(nick);
        a.appendChild(datos);

        var tipo = document.createElement('span');
        tipo.className = 'badge ' + (String(yo.tipo).toLowerCase() === 'docente' ? 'text-bg-info' : 'text-bg-secondary');
        tipo.textContent = yo.tipo;
        a.appendChild(tipo);

        col.appendChild(a);
        return col;
    }

    // Pestaña "Seguidores" del perfil visto: agrega o quita al usuario logueado y actualiza el contador
    function actualizarSeguidores(d) {
        var lista = document.getElementById('lista-seguidores');
        var vacio = document.getElementById('vacio-seguidores');
        var cont = document.getElementById('cont-seguidores');
        if (cont) cont.textContent = d.seguidores;
        if (!lista || !d.yo) return;

        var existente = null;
        Array.prototype.forEach.call(lista.children, function (el) {
            if (el.getAttribute('data-nick') === d.yo.nickname) existente = el;
        });
        if (d.siguiendo && !existente) lista.appendChild(crearTarjeta(d.yo));
        if (!d.siguiendo && existente) lista.removeChild(existente);

        var hay = lista.children.length > 0;
        lista.classList.toggle('d-none', !hay);
        if (vacio) vacio.classList.toggle('d-none', hay);
    }

    form.addEventListener('submit', function (ev) {
        ev.preventDefault();
        if (enCurso) return;                         // evita doble clic mientras espera la respuesta
        enCurso = true;
        btn.disabled = true;

        fetch(form.getAttribute('action'), {
            method: 'POST',
            credentials: 'same-origin',
            headers: {'X-Requested-With': 'XMLHttpRequest', 'Accept': 'application/json'},
            body: new URLSearchParams(new FormData(form))
        })
        .then(function (r) {
            return r.json().then(function (d) { return d; }, function () { return {ok: false}; });
        })
        .then(function (d) {
            if (d.ok) {
                actualizarBoton(d.siguiendo);
                actualizarSeguidores(d);
                mostrarAlerta(d.mensaje, 'success');
            } else {
                mostrarAlerta(d.mensaje || 'No se pudo completar la operación. Intentá nuevamente.', 'danger');
            }
        })
        .catch(function (e) {
            console.error('Seguir usuario: fallo de red', e);
            mostrarAlerta('No se pudo completar la operación. Intentá nuevamente.', 'danger');
        })
        .then(function () {
            enCurso = false;
            btn.disabled = false;
        });
    });
});
