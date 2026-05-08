const express = require('express');
const app = express();
const path = require('path');

// Configuramos que vamos a usar EJS
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Carpeta para archivos estáticos (CSS, Imágenes)
app.use(express.static(path.join(__dirname, 'public')));

// --- RUTAS ---

// Página Principal (Index)
app.get('/', (req, res) => {
    // Datos de prueba (luego vendrán de la DB)
    const batalles = [
        { id: 1, data: '2026-05-01', resultat: 'Victòria' },
        { id: 2, data: '2026-05-05', resultat: 'Derrota' }
    ];
    res.render('index', { batalles: batalles });
});

// Página de Civilización (Recursos)
app.get('/civilitzacio', (req, res) => {
    const recursos = { fusta: 1200, ferro: 450, menjar: 2000, mana: 100 };
    res.render('civilitzacio', { recursos: recursos });
});

// Página de Programadores
app.get('/programadors', (req, res) => {
    res.render('programadors');
});

// Iniciar servidor
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Servidor web en marcha: http://localhost:${PORT}`);
});