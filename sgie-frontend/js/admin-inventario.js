document.addEventListener('DOMContentLoaded', () => {
    cargarInventario();
});

const STATUS_CONFIG = {
    'Disponible': { color: 'text-emerald-700', bg: 'bg-emerald-50', border: 'border-emerald-200', dot: 'bg-emerald-500' },
    'Prestado': { color: 'text-amber-700', bg: 'bg-amber-50', border: 'border-amber-200', dot: 'bg-amber-500' },
    'En mantenimiento': { color: 'text-blue-700', bg: 'bg-blue-50', border: 'border-blue-200', dot: 'bg-blue-500' },
    'De baja': { color: 'text-red-700', bg: 'bg-red-50', border: 'border-red-200', dot: 'bg-red-500' }
};

async function cargarInventario() {
    try {
        const inventario = await apiFetch('/equipos'); 
        
        if (inventario) {
            actualizarKPIs(inventario);
            renderizarTablaInventario(inventario);
        }
    } catch (error) {
        console.error("Error al cargar el inventario:", error);
    }
}

function actualizarKPIs(inventario) {
    const total = inventario.length;
    const disponibles = inventario.filter(eq => eq.estado === 'Disponible').length;
    const enUso = inventario.filter(eq => eq.estado === 'Prestado').length;
    const fueraServicio = inventario.filter(eq => eq.estado === 'En mantenimiento' || eq.estado === 'De baja').length;

    // Actualizar el DOM
    document.getElementById('kpi-total').textContent = total;
    document.getElementById('kpi-disponibles').textContent = disponibles;
    document.getElementById('kpi-uso').textContent = enUso;
    document.getElementById('kpi-fuera').textContent = fueraServicio;
}

function renderizarTablaInventario(data) {
    const tbody = document.getElementById('tabla-inventario');
    if(!tbody) return;

    tbody.innerHTML = data.map(eq => {
        const cfg = STATUS_CONFIG[eq.estado] || STATUS_CONFIG['Disponible'];
        
        return `
            <tr class="border-b border-gray-50 hover:bg-gray-50/50 transition-colors">
                <td class="px-4 py-3.5 font-mono text-xs text-gray-400 tabular-nums">${eq.codigo_patrimonial}</td>
                <td class="px-4 py-3.5">
                    <div class="font-medium text-gray-900 text-xs">${eq.marca_modelo}</div>
                </td>
                <td class="px-4 py-3.5">
                    <span class="text-xs bg-gray-100 text-gray-600 px-2 py-1 rounded-lg">${eq.categoria || 'N/A'}</span>
                </td>
                <td class="px-4 py-3.5">
                    <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-medium border ${cfg.color} ${cfg.bg} ${cfg.border}">
                        <span class="w-1.5 h-1.5 rounded-full flex-shrink-0 ${cfg.dot}"></span>
                        ${eq.estado}
                    </span>
                </td>
                <td class="px-4 py-3.5 text-xs text-gray-500">${eq.ubicacion || 'Almacén Principal'}</td>
                <td class="px-4 py-3.5">
                    <div class="flex gap-3">
                        <button class="text-xs text-[#8b0000] hover:underline font-medium">Editar</button>
                        <button class="text-xs text-gray-400 hover:underline">Historial</button>
                    </div>
                </td>
            </tr>
        `;
    }).join('');
}