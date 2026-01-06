package Trabajodeenfoque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {  // Clase para gestionar conexiones a la base de datos

    // URL de conexión a MySQL con parámetros de configuración:
    private static final String URL
            = "jdbc:mysql://localhost:3306/resgistro" // Conexión a BD 'resgistro'
            + "?useSSL=false" 
            + "&allowPublicKeyRetrieval=true" 
            + "&serverTimezone=Europe/Madrid" 
            + "&characterEncoding=UTF-8";       

    private static final String usuario = "root";       // Usuario de MySQL (por defecto)
    private static final String contrasena = "";        // Contraseña vacía

    // Método estático que devuelve una conexión a la base de datos
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, usuario, contrasena);  // Crea y devuelve la conexión
    }
}
