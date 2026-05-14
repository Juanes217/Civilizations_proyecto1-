const express = require('express');
const app = express();
const path = require('path');
const hbs = require('hbs');
const db = require(path.join(__dirname, 'base de dades', 'db.js'));

app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views')); // Usa __dirname con DOBLE guion bajo

// Registrar los partials
hbs.registerPartials(path.join(__dirname, 'views', 'partials'));

app.use(express.static(path.join(__dirname, 'public')));

// 1. INICIO
app.get('/', async (req, res) => {
    try {
        const [rows] = await db.query('SELECT * FROM BATTLE_LOG ORDER BY id DESC LIMIT 2');
        res.render('index', { batalles: rows, page: 'inici' }); 
    } catch (error) {
        res.render('index', { batalles: [], page: 'inici' });
    }
});

// 2. CIVILIZACIÓN
app.get('/civilitzacio', async (req, res) => {
    try {
        const [rows] = await db.query('SELECT * FROM CIVILIZATION_STATS LIMIT 1');
        const s = rows[0] || { WOOD_AMOUNT: 0, IRON_AMOUNT: 0, FOOD_AMOUNT: 0, MANA_AMOUNT: 0 };
        res.render('civilitzacio', { 
            stats: { 
                fusta: s.WOOD_AMOUNT, 
                ferro: s.IRON_AMOUNT, 
                menjar: s.FOOD_AMOUNT, 
                mana: s.MANA_AMOUNT 
            },
            page: 'civi' // Añadido page
        });
    } catch (error) {
        res.render('civilitzacio', { stats: { fusta: 0, ferro: 0, menjar: 0, mana: 0 }, page: 'civi' });
    }
});

// 3. HISTORIAL
app.get('/batalles', async (req, res) => {
    try {
        const [rows] = await db.query('SELECT * FROM BATTLE_LOG ORDER BY BATTLE_ID DESC');
        res.render('batalles', { batalles: rows, total: rows.length, page: 'historial' }); // Añadido page
    } catch (error) {
        res.render('batalles', { batalles: [], total: 0, page: 'historial' });
    }
});

// 4. INFORME (Ajustado a tus columnas: WOOD_GAIN, IRON_GAIN...)
app.get('/informe', async (req, res) => {
    const idBatalla = req.query.informe;
    
    // Si no hay ID, en lugar de redirigir, podemos buscar la última batalla
    if (!idBatalla) {
        try {
            const [rows] = await db.query('SELECT * FROM BATTLE_LOG ORDER BY id DESC LIMIT 1');
            if (rows.length > 0) {
                return res.render('informe', { batalla: rows[0], page: 'informe' });
            }
        } catch (e) {
            return res.redirect('/batalles');
        }
    }

    try {
        const [rows] = await db.query('SELECT * FROM BATTLE_LOG WHERE id = ?', [idBatalla]);
        if (rows.length > 0) {
            res.render('informe', { batalla: rows[0], page: 'informe' }); 
        } else {
            res.status(404).send("Informe no trobat.");
        }
    } catch (error) {
        res.status(500).send("Error al cargar informe");
    }
});

app.get('/programadors', (req, res) => res.render('programadors', { page: 'equip' }));

hbs.registerHelper('eq', function (a, b) {
    return String(a) === String(b);
});

app.listen(3000, () => console.log("Servidor listo en http://localhost:3000"));