document.addEventListener('DOMContentLoaded', () => {
    cargarEquipos();
});

async function cargarEquipos() {
    try {
        // Usa la función apiFetch de api.js conectándose a EquipoRestController
        const equipos = await apiFetch('/equipos?estado=disponible'); 
        renderGrid(equipos);
    } catch (error) {
        console.error("Fallo al cargar equipos", error);
        // Fallback visual para pruebas sin el backend encendido
        renderGrid([
            { id: 1, codigo: 'LAP-001', marca: 'Dell', modelo: 'Latitude 5520', categoria: 'Laptop', estado: 'Disponible', imagen: 'https://images.unsplash.com/photo-1588702547923-7093a6c3ba33?w=400&h=280&fit=crop' }
        ]);
    }
}

function renderGrid(data) {
    const grid = document.getElementById('grid-equipos');
    if(!grid) return;
    
    grid.innerHTML = data.map(eq => `
        <div class="bg-white rounded-2xl border border-gray-100 overflow-hidden hover:shadow-lg transition-all group">
            <div class="relative h-44 bg-gray-100 overflow-hidden">
                <img src="${eq.imagen || 'https://via.placeholder.com/400x280'}" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300">
                <div class="absolute top-3 left-3">
                    <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-medium border bg-white shadow-sm ${eq.estado === 'Disponible' ? 'text-emerald-700 border-emerald-200' : 'text-amber-700 border-amber-200'}">
                        <span class="w-1.5 h-1.5 rounded-full ${eq.estado === 'Disponible' ? 'bg-emerald-500' : 'bg-amber-500'}"></span>
                        ${eq.estado}
                    </span>
                </div>
            </div>
            <div class="p-4">
                <div class="text-[10px] font-mono text-gray-400 mb-1 tracking-wider">${eq.codigo}</div>
                <div class="font-semibold text-gray-900 text-sm leading-snug">${eq.marca} ${eq.modelo}</div>
                <div class="text-xs text-gray-400 mt-0.5">${eq.categoria}</div>
                <button onclick="openModal(${eq.id}, '${eq.marca}', '${eq.modelo}', '${eq.codigo}', '${eq.imagen}')" 
                    ${eq.estado !== 'Disponible' ? 'disabled' : ''} 
                    class="w-full mt-3.5 py-2 rounded-xl text-xs font-semibold transition-colors ${eq.estado === 'Disponible' ? 'bg-[#8B0000] text-white hover:bg-[#6e0000]' : 'bg-gray-100 text-gray-400 cursor-not-allowed'}">
                    ${eq.estado === 'Disponible' ? 'Solicitar Préstamo' : 'No disponible'}
                </button>
            </div>
        </div>
    `).join('');
}

let equipoSeleccionadoId = null;

function openModal(id, marca, modelo, codigo, img) {
    equipoSeleccionadoId = id;
    document.getElementById('modal-name').textContent = `${marca} ${modelo}`;
    document.getElementById('modal-code').textContent = codigo;
    document.getElementById('modal-img').src = img || 'https://via.placeholder.com/400x280';
    document.getElementById('modal-prestamo').classList.remove('hidden');
}

function closeModal() {
    equipoSeleccionadoId = null;
    document.getElementById('modal-prestamo').classList.add('hidden');
}

async function submitPrestamo() {
    const motivo = document.getElementById('motivo-input').value; // Asumiendo que le pusiste ese ID al textarea
    
    if(!motivo.trim()) {
        alert("El motivo es obligatorio");
        return;
    }

    try {
        await apiFetch('/prestamos', {
            method: 'POST',
            body: JSON.stringify({ 
                equipoId: equipoSeleccionadoId, 
                motivo: motivo 
            })
        });
        alert("Solicitud generada con éxito");
        closeModal();
        cargarEquipos(); // Recarga la lista para actualizar disponibilidad
    } catch (error) {
        alert("Error al procesar la solicitud: " + error.message);
    }
}