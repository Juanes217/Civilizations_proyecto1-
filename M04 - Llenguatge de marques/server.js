const express = require('express');
const app = express();
const path = require('path');

// Configuración del motor de plantillas EJS
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Archivos estáticos
app.use(express.static(path.join(__dirname, 'public')));

// --- RUTAS ---

// 1. Inicio (Index)
app.get('/', (req, res) => {
    const batalles_recents = [
        { id: 1, data: '2026-05-11', resultat: 'Victòria' },
        { id: 2, data: '2026-05-12', resultat: 'Derrota' }
    ];
    res.render('index', { batalles: batalles_recents });
});

// 2. Estado de la Civilización (Recursos)
app.get('/civilitzacio', (req, res) => {
    const stats = { fusta: 1200, ferro: 450, menjar: 2000, mana: 100 };
    res.render('civilitzacio', { stats: stats });
});

// 3. Historial de Batallas
app.get('/batalles', (req, res) => {
    const llista = [
        { id: 1, data: '2026-05-01', enemic: 'Legió Romana', resultat: 'Victòria' },
        { id: 2, data: '2026-05-05', enemic: 'Vikings', resultat: 'Derrota' },
        { id: 3, data: '2026-05-10', enemic: 'Bàrbars', resultat: 'Victòria' }
    ];
    res.render('batalles', { batalles: llista });
});

// 4. Informe
app.get('/informe', (req, res) => {
    res.render('informe');
});

// 5. Equipo (Programadors)
app.get('/programadors', (req, res) => {
    res.render('programadors');
});

// Iniciar servidor
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Servidor web en marxa: http://localhost:${PORT}`);
});