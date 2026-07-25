document.addEventListener('DOMContentLoaded', () => {
    cargarReportesDashboard();
});

async function cargarReportesDashboard() {
    try {
        const stats = await apiFetch('/reportes/dashboard');
        
        if (stats) {
            // Actualizar tarjetas de KPI superiores
            actualizarElemento('rotacion-promedio', stats.rotacionPromedio || '0');
            actualizarElemento('disponibilidad-total', stats.disponibilidadActual || '0%');
            actualizarElemento('tasa-retraso', stats.tasaRetraso || '0%');
            actualizarElemento('duracion-promedio', stats.duracionPromedio || '0d');

            // Si el backend envía arrays con datos para los gráficos, 
            // se puede iterar aquí para alterar el width y height en línea
            if(stats.demandaCategorias) {
                renderizarBarrasDemanda(stats.demandaCategorias);
            }
        }
    } catch (error) {
        console.error("Error al cargar los reportes OLAP:", error);
    }
}

function actualizarElemento(id, valor) {
    const el = document.getElementById(id);
    if(el) el.textContent = valor;
}

// Ejemplo de cómo alterar los gráficos de barra dinámicamente con JS
function renderizarBarrasDemanda(datos) {
    const container = document.getElementById('grafico-demanda');
    if(!container) return;

    // Asumiendo que datos es un array: [{ categoria: 'Laptops', cantidad: 48, porcentaje: 100 }, ...]
    container.innerHTML = datos.map(d => `
        <div>
            <div class="flex justify-between text-xs mb-1.5">
                <span class="text-gray-600 font-medium">${d.categoria}</span>
                <span class="text-gray-900 font-semibold tabular-nums">${d.cantidad} sol.</span>
            </div>
            <div class="h-2 bg-gray-100 rounded-full overflow-hidden">
                <div class="h-full bg-[#8b0000] rounded-full transition-all duration-1000" style="width: ${d.porcentaje}%;"></div>
            </div>
        </div>
    `).join('');
}