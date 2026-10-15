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
