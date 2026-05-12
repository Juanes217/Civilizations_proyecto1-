const mysql = require('mysql2/promise');

// Creamos el pool de conexiones
const db = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'test123@', // Asegúrate de que esta sea tu clave real de MySQL
    database: 'juego_civilizaciones',
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

module.exports = db;