// script.js

function showSection(sectionId) {
    var sections = document.querySelectorAll('.content');
    sections.forEach(function (section) {
        section.classList.remove('active');
    });

    var sectionToShow = document.getElementById(sectionId);
    sectionToShow.classList.add('active');
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


