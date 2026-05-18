const mysql = require('mysql2/promise');

// Creamos el pool de conexiones
const db = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'P@ssw0rd', 
    database: 'juego_civilizaciones',
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

module.exports = db;