package Modelo;

import Frame.Login;
import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {
    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            if (conexion != null && !conexion.isClosed()) {
             
                System.out.println("✅ Conexion exitosa a la base de datos.");
            
                new Login().setVisible(true);
            } else {
                System.out.println("❌ No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
}

