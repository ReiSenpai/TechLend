document.addEventListener('DOMContentLoaded', () => {
    cargarSolicitudes();
});

// Función para cambiar de pestañas
function switchTab(tabId) {
    // Ocultar todos los contenidos
    document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
    
    // Resetear estilos de los botones
    document.querySelectorAll('button[id^="btn-"]').forEach(el => {
        el.classList.remove('ring-2', 'ring-amber-200', 'ring-blue-200', 'ring-emerald-200');
        el.classList.add('opacity-70');
    });

    // Mostrar el tab activo
    document.getElementById(`tab-${tabId}`).classList.add('active');
    const activeBtn = document.getElementById(`btn-${tabId}`);
    activeBtn.classList.remove('opacity-70');
    
    if(tabId === 'pendientes') activeBtn.classList.add('ring-2', 'ring-amber-200');
    if(tabId === 'entrega') activeBtn.classList.add('ring-2', 'ring-blue-200');
    if(tabId === 'devolucion') activeBtn.classList.add('ring-2', 'ring-emerald-200');
}

async function cargarSolicitudes() {
    try {
        // Asumiendo que tu endpoint devuelve todas las solicitudes activas
        const solicitudes = await apiFetch('/prestamos/activos'); 
        
        // Aquí iría la lógica para filtrar y renderizar cada pestaña 
        // (Pendientes, Aprobados/Por Entregar, Entregados/Por Devolver)
        // Ejemplo simplificado:
        console.log("Solicitudes cargadas:", solicitudes);
    } catch (error) {
        console.error("Error al cargar las solicitudes:", error);
    }
}

async function aprobarSolicitud(id) {
    try {
        await apiFetch(`/prestamos/${id}/aprobar`, { method: 'PUT' });
        alert("Solicitud aprobada y enviada a la pestaña de Entrega.");
        cargarSolicitudes();
        switchTab('entrega');
    } catch (error) {
        alert("Error al aprobar: " + error.message);
    }
}

async function rechazarSolicitud(id) {
    if(!confirm("¿Estás seguro de rechazar esta solicitud?")) return;
    try {
        await apiFetch(`/prestamos/${id}/rechazar`, { method: 'PUT' });
        alert("Solicitud rechazada.");
        cargarSolicitudes();
    } catch (error) {
        alert("Error al rechazar: " + error.message);
    }
}

async function registrarSalida(id) {
    try {
        await apiFetch(`/prestamos/${id}/entrega`, { method: 'PUT' });
        alert("Entrega registrada correctamente. El equipo está ahora en campo.");
        cargarSolicitudes();
        switchTab('devolucion');
    } catch (error) {
        alert("Error al registrar entrega: " + error.message);
    }
}

async function registrarRetorno(id) {
    try {
        await apiFetch(`/prestamos/${id}/devolucion`, { method: 'PUT' });
        alert("Devolución exitosa. Equipo marcado como Disponible.");
        cargarSolicitudes();
        switchTab('pendientes');
    } catch (error) {
        alert("Error al procesar la devolución: " + error.message);
    }
}