document.addEventListener("DOMContentLoaded", function () {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                // Añade la clase de animación cuando el elemento entra en pantalla
                entry.target.classList.add('scroll-animate');
            } else {
                // Opcional: Quita la clase si quieres que la animación se repita al subir y bajar
                entry.target.classList.remove('scroll-animate');
            }
        });
    }, { threshold: 0.1 }); // Se activa cuando el 10% del elemento es visible

    const hiddenElements = document.querySelectorAll('.scroll-hidden');
    hiddenElements.forEach((el) => observer.observe(el));
});