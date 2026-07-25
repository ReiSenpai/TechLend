// URL base de tu backend Spring Boot (CMMI-DEV / SOA Interoperability)
const API_BASE_URL = 'http://localhost:8090/api';

// CORRECCIÓN: Usar los nombres exactos que guarda tu login.js
const token = localStorage.getItem('token');
const rol = localStorage.getItem('rol');

// Validación de sesión estricta (Bloquea si está vacío, es null o dice "undefined")
if ((!token || token === 'undefined' || token === 'null') && !window.location.href.includes('index.html')) {
    window.location.href = 'index.html';
}

/**
 * Función central para llamadas FETCH que inyecta automáticamente el JWT
 */
async function apiFetch(endpoint, options = {}) {
    const defaultHeaders = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };

    const fetchOptions = {
        ...options,
        headers: {
            ...defaultHeaders,
            ...options.headers
        }
    };

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, fetchOptions);
        
        // Manejo de token expirado o permisos insuficientes
        if (response.status === 401 || response.status === 403) {
            localStorage.clear();
            window.location.href = 'index.html';
            throw new Error('Sesión expirada o permisos insuficientes');
        }

        // Manejo de errores 400 y 500 de forma segura
        if (!response.ok) {
            let errorMsg = 'Error en la petición';
            const errorText = await response.text();
            try {
                const errJson = JSON.parse(errorText);
                errorMsg = errJson.message || errorMsg;
            } catch (e) {
                // Si Java devuelve un error en texto plano en lugar de JSON
                errorMsg = errorText || errorMsg;
            }
            throw new Error(errorMsg);
        }

        // SOLUCIÓN AL ERROR DE JSON: Leemos primero como texto
        const responseText = await response.text();
        
        // Si el texto está vacío (como cuando Java hace ResponseEntity.ok().build()), retornamos null
        if (!responseText) {
            return null;
        }

        // Si sí hay contenido, entonces lo convertimos a JSON
        return JSON.parse(responseText);

    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Función global de cierre de sesión
function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}

// Cargar nombres del usuario en la UI de forma automática
document.addEventListener('DOMContentLoaded', () => {
    const nameEl = document.getElementById('user-name');
    if (nameEl) {
        // CORRECCIÓN: Usar el nombre exacto que guarda el login
        nameEl.textContent = localStorage.getItem('nombres') || 'Usuario';
    }
});