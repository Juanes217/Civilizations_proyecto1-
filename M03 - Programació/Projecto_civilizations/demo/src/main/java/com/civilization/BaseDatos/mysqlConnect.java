package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlConnect {

    public static Connection ConectarBD() {
        Connection conexion = null;
        String host = "jdbc:mysql://localhost/";
        String user = "root";
        String pass = "P@ssw0rd";
        String bd = "juego_civilizaciones";
        
        System.out.println("Conectando...");

        try {
        	Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(host + bd, user, pass);
            System.out.println("Conexion Exitosa!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return conexion;
    }

    public static void main(String[] args) {
        Connection bd = ConectarBD();
        
        // Cierre de seguridad opcional al terminar la prueba en el main
        try {
            if (bd != null && !bd.isClosed()) {
                bd.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}