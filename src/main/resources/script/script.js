// script.js

function showForm(formId) {
    var forms = document.querySelectorAll('.content-altaTitulo, .content-actualizarTitulo, .content-borrarTitulo');
    for (var i = 0; i < forms.length; i++) {
        var form = forms[i];
        if (form.id === formId) {
            // Si es el formulario clicado, se agrega la clase active
            form.classList.add('active');
        } else {
            // Si no es el formulario clicado, se quita la clase active
            form.classList.remove('active');
        }
    }
}

function validateForm(form) {
    var inputs = form.querySelectorAll('input[required]');
    for (var i = 0; i < inputs.length; i++) {
        if (!inputs[i].value) {
            alert('Por favor, complete todos los campos obligatorios.');
            return false;
        }
    }
    return true;
}

function showSection(sectionId) {
    var sections = document.querySelectorAll('.login-seccion, .registro-seccion');
    sections.forEach(function (section) {
        section.style.display = 'none';
    });

    var sectionToShow = document.getElementById(sectionId);
    sectionToShow.style.display = 'block';

    // Actualiza la clase activa en los enlaces
    var links = document.querySelectorAll('.menu-content a');
    links.forEach(function (link) {
        link.classList.remove('active');
    });

    var activeLink = document.getElementById('menu' + sectionId);
    activeLink.classList.add('active');

}

