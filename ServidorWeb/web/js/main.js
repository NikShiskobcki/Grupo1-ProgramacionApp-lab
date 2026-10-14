// Utilidades de interfaz (edExt)
// Nota: las pestañas del perfil (Requerimiento 7.3) usan el componente Tabs de
// Bootstrap (JavaScript, sin recargar la página).

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

// Búsqueda con sugerencias en vivo (AJAX con fetch).
// - Espera 250 ms sin teclear antes de pedir (debounce) y cancela el pedido anterior.
// - Los textos se insertan con textContent (nunca innerHTML) para evitar XSS.
// - Flechas arriba/abajo + Enter para elegir, Esc o clic afuera para cerrar.
// - Enter sin elegir nada (o el botón de la lupa) envía el formulario normal a /buscar.
document.addEventListener('DOMContentLoaded', function () {
    var input = document.getElementById('busquedaInput');
    var lista = document.getElementById('busquedaSugerencias');
    if (!input || !lista || !window.fetch) return;

    var MIN = 2;
    var timer = null;
    var controlador = null;
    var activa = -1;

    function cerrar() {
        lista.hidden = true;
        lista.innerHTML = '';
        activa = -1;
        input.setAttribute('aria-expanded', 'false');
    }

    function marcar(i) {
        var items = lista.querySelectorAll('a');
        items.forEach(function (a, idx) { a.classList.toggle('activa', idx === i); });
        activa = i;
    }

    function mostrar(datos) {
        lista.innerHTML = '';
        if (!datos.length) {
            var vacio = document.createElement('li');
            vacio.className = 'search-suggestions-vacio';
            vacio.textContent = 'Sin coincidencias. Enter para buscar igual.';
            lista.appendChild(vacio);
        } else {
            datos.forEach(function (d) {
                var li = document.createElement('li');
                li.setAttribute('role', 'option');
                var a = document.createElement('a');
                a.href = d.url;
                var tipo = document.createElement('span');
                tipo.className = 'sug-tipo';
                tipo.textContent = d.tipo;
                var texto = document.createElement('span');
                texto.className = 'sug-texto';
                var nombre = document.createElement('span');
                nombre.className = 'sug-nombre';
                nombre.textContent = d.nombre;
                var detalle = document.createElement('span');
                detalle.className = 'sug-detalle';
                detalle.textContent = d.detalle;
                texto.appendChild(nombre);
                texto.appendChild(detalle);
                a.appendChild(tipo);
                a.appendChild(texto);
                li.appendChild(a);
                lista.appendChild(li);
            });
        }
        activa = -1;
        lista.hidden = false;
        input.setAttribute('aria-expanded', 'true');
    }

    function pedir() {
        var q = input.value.trim();
        if (q.length < MIN) { cerrar(); return; }
        if (controlador) controlador.abort();
        controlador = new AbortController();
        fetch(input.getAttribute('data-url') + '?q=' + encodeURIComponent(q), {
            signal: controlador.signal,
            headers: { 'Accept': 'application/json' }
        })
        .then(function (r) { if (!r.ok) throw new Error('HTTP ' + r.status); return r.json(); })
        .then(function (datos) {
            // Descarta la respuesta si el texto cambió mientras tanto
            if (input.value.trim() === q) mostrar(datos);
        })
        .catch(function (e) {
            if (e.name !== 'AbortError') cerrar(); // si falla, queda la búsqueda normal
        });
    }

    input.addEventListener('input', function () {
        clearTimeout(timer);
        timer = setTimeout(pedir, 250);
    });

    input.addEventListener('keydown', function (ev) {
        if (lista.hidden) return;
        var items = lista.querySelectorAll('a');
        if (ev.key === 'ArrowDown' && items.length) {
            ev.preventDefault();
            marcar((activa + 1) % items.length);
        } else if (ev.key === 'ArrowUp' && items.length) {
            ev.preventDefault();
            marcar(activa <= 0 ? items.length - 1 : activa - 1);
        } else if (ev.key === 'Enter' && activa >= 0) {
            ev.preventDefault();
            window.location.href = items[activa].href;
        } else if (ev.key === 'Escape') {
            cerrar();
        }
    });

    input.addEventListener('focus', function () {
        if (input.value.trim().length >= MIN && !lista.children.length) pedir();
        else if (lista.children.length) { lista.hidden = false; input.setAttribute('aria-expanded', 'true'); }
    });

    document.addEventListener('click', function (ev) {
        if (!ev.target.closest('.header-search')) cerrar();
    });
});

// Seguir / Dejar de seguir con AJAX (perfil de usuario).
// Envía el POST con fetch y, si salió bien, vuelve a pedir el perfil y reemplaza solo
// el botón, el contador y la lista de seguidores (el HTML lo sigue generando el JSP).
// Sin JavaScript el formulario funciona igual, con redirect a /perfil?user=...&msg=...
document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('formSeguir');
    if (!form || !window.fetch || !window.DOMParser) return;

    var zonaAlertas = document.getElementById('alertasSeguir');

    function alerta(tipo, texto) {
        if (!zonaAlertas) return;
        zonaAlertas.innerHTML = '';
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
        zonaAlertas.appendChild(div);
    }

    // Reemplaza las partes del perfil que cambian con lo que devuelve el servidor
    function refrescarPerfil() {
        return fetch(window.location.pathname + '?user=' + encodeURIComponent(
                         form.querySelector('[name=user]').value), {
            cache: 'no-store'
        })
        .then(function (r) { if (!r.ok) throw new Error('HTTP ' + r.status); return r.text(); })
        .then(function (html) {
            var doc = new DOMParser().parseFromString(html, 'text/html');
            ['formSeguir', 'badgeSeguidores', 'tab-seguidores'].forEach(function (id) {
                var nuevo = doc.getElementById(id);
                var actual = document.getElementById(id);
                if (!nuevo || !actual) return;
                if (id === 'tab-seguidores') actual.innerHTML = nuevo.innerHTML; // conserva show/active
                else actual.replaceWith(nuevo);
            });
            var nuevoForm = document.getElementById('formSeguir');
            if (nuevoForm) enlazar(nuevoForm);
        });
    }

    function enlazar(f) {
        form = f;
        f.addEventListener('submit', function (ev) {
            ev.preventDefault();
            var boton = f.querySelector('button[type=submit]');
            if (boton) boton.disabled = true; // evita doble clic
            var accion = f.querySelector('[name=accion]').value;

            fetch(f.action, {
                method: 'POST',
                headers: {
                    'X-Requested-With': 'fetch',
                    'Accept': 'application/json',
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                body: new URLSearchParams(new FormData(f)).toString()
            })
            .then(function (r) {
                return r.json().then(function (j) { return { status: r.status, datos: j }; });
            })
            .then(function (res) {
                if (res.status === 401 && res.datos.login) {
                    window.location.href = res.datos.login; // sesión vencida
                    return null;
                }
                if (!res.datos.ok) throw new Error('operacion');
                return refrescarPerfil().then(function () {
                    alerta('success', accion === 'seguir'
                        ? 'Ahora seguís a este usuario.'
                        : 'Dejaste de seguir a este usuario.');
                });
            })
            .catch(function () {
                if (boton) boton.disabled = false;
                alerta('danger', 'No se pudo completar la operación. Intentá nuevamente.');
            });
        });
    }

    enlazar(form);
});
