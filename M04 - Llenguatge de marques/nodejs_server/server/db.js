const mysql = require('mysql2/promise');

// Detectamos de forma automática si estamos en Proxmox (si tiene un puerto de entorno asignado)
const isProxmox = process.env.PORT ? true : false;

// Declaramos la variable db vacía usando let para poder reasignarla dentro del condicional
let db;

if (!isProxmox) {
    // Configuración cuando ejecutas en tu ordenador local (hacia el túnel/puerto 3307)
    db = mysql.createPool({
        port: 3307,
        host: 'localhost',
        user: 'super',
        password: '1234', 
        database: 'juego_civilizaciones',
        waitForConnections: true,
        connectionLimit: 10,
        queueLimit: 0
    });
    console.log(" Conectado a la base de datos en entorno LOCAL (Puerto 3307)");
} else {
    // Configuración cuando la web ya corre dentro del contenedor de Proxmox (Puerto 3306 nativo)
    db = mysql.createPool({
        port: 3306,
        host: 'localhost',
        user: 'super',
        password: '1234', 
        database: 'juego_civilizaciones',
        waitForConnections: true,
        connectionLimit: 10,
        queueLimit: 0
    });
    console.log(" Conectado a la base de datos en entorno PROXMOX (Puerto 3306)");
}

// Exportamos la conexión para que app.js la use perfectamente
module.exports = db;