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
