package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.civilization.Civilization; // Importamos tu clase original del juego

public class HistorialBatallas {

    /**
     * Guarda el estado actual de los recursos de la civilización y actualiza el contador de batallas.
     * Este método se ejecuta justo después de terminar un combate.
     */
    public static void guardarResultadoBatalla(Civilization civ) {
        String sqlUpdateRecursos = "UPDATE partida SET comida = ?, madera = ?, hierro = ?, batallas = ? WHERE id = 1";
        
        // Usamos el método ConectarBD de tu clase mysqlConnect
        try (Connection con = mysqlConnect.ConectarBD()) {
            
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sqlUpdateRecursos)) {
                    // Mapeamos los recursos actuales de tu objeto Civilization
                    ps.setInt(1, civ.getFood());
                    ps.setInt(2, civ.getWood());
                    ps.setInt(3, civ.getIron());
                    ps.setInt(4, civ.getBattles());
                    
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("[BD] Recursos e historial sincronizados correctamente para la web.");
                    }
                }
            } else {
                System.out.println("[BD] Error: No se pudo establecer conexión para guardar la batalla.");
            }
            
        } catch (SQLException e) {
            System.out.println("[BD] Error crítico al guardar los datos post-batalla: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Opcional: Registra un log individual de cada batalla en una tabla independiente (historial_combates)
     * para mostrar un feed de actividades cronológico en tu página web.
     */
    public static void registrarLogBatalla(String resultado, int botinObtenido, int fuerzaJugador, int fuerzaEnemigo) {
        String sqlInsertLog = "INSERT INTO historial_combates (resultado, botin, fuerza_real, fuerza_enemigo, fecha) VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection con = mysqlConnect.ConectarBD()) {
            
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sqlInsertLog)) {
                    ps.setString(1, resultado); // "VICTORIA" o "DERROTA"
                    ps.setInt(2, botinObtenido);
                    ps.setInt(3, fuerzaJugador);
                    ps.setInt(4, fuerzaEnemigo);
                    
                    ps.executeUpdate();
                    System.out.println("[BD] Registro de combate añadido al feed de las crónicas web.");
                }
            }
            
        } catch (SQLException e) {
            System.out.println("[BD] No se pudo registrar el log de combate individual: " + e.getMessage());
        }
    }
}