document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    
    // Si ya tiene token, redirigir automáticamente según el rol
    const existingRol = localStorage.getItem('sgie_rol');
    if (existingRol) {
        redirectByRole(existingRol);
    }

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const btnSubmit = document.getElementById('btn-submit');
            const errorDiv = document.getElementById('error-msg');

            btnSubmit.disabled = true;
            btnSubmit.textContent = 'Verificando credenciales...';
            errorDiv.classList.add('hidden');

            try {
                // Hacemos el fetch manual aquí porque api.js no bloquea index.html
                const response = await fetch('http://localhost:8090/api/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ correo: email, contrasena: password })
                });

                if (!response.ok) throw new Error('Credenciales institucionales incorrectas');

                const data = await response.json();
                
                localStorage.setItem('sgie_token', data.token);
                localStorage.setItem('sgie_rol', data.rol);
                localStorage.setItem('sgie_nombres', data.nombres);

                redirectByRole(data.rol);
            } catch (error) {
                errorDiv.textContent = error.message;
                errorDiv.classList.remove('hidden');
                btnSubmit.disabled = false;
                btnSubmit.textContent = 'Ingresar al Sistema';
            }
        });
    }
});

function redirectByRole(rol) {
    switch (rol) {
        case 'Solicitante': window.location.href = 'catalogo.html'; break;
        case 'Encargado': window.location.href = 'encargado.html'; break;
        case 'Administrador': window.location.href = 'admin-inventario.html'; break;
        default: 
            localStorage.clear();
            alert('Rol no reconocido');
    }
}