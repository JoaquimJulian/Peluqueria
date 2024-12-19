package application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class databaseConection {
    private static final String URL = "jdbc:mysql://localhost:3306/peluqueria";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Método para verificar la contraseña de Juan
    public static boolean verificarContraseña(String usuario, String contrasena) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        String sql = "SELECT * FROM trabajadores WHERE nombre = ? AND contrasena = ?";
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            rs = stmt.executeQuery();
            return rs.next(); // Si hay un resultado, significa que la contraseña es correcta
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String verificarContraseñaAdmin(String usuario, String contrasena) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String resultado = "";
        
        String sql2 = "SELECT * FROM trabajadores WHERE nombre = ? AND contrasena = ?";
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql2);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            rs = stmt.executeQuery();
            //return rs.next(); // Si hay un resultado, significa que la contraseña es correcta
            if (rs.next()) {
                
            	int id = rs.getInt("id_trabajador");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int telefono = rs.getInt("telefono");
                String email = rs.getString("email");
                String contrasenaDB = rs.getString("contrasena");
                boolean esAdministrador = rs.getBoolean("es_administrador");
                double comision = rs.getDouble("comision");
                
                // Crear un objeto Trabajador con los datos obtenidos de la base de datos
                Trabajador trabajador = new Trabajador(id, nombre, apellidos, telefono, email, contrasenaDB, esAdministrador, comision);

                // Almacenar el trabajador logueado en la variable estática de la clase Trabajador
                Trabajador.setTrabajadorLogueado(trabajador);
        
                resultado = "exitoso";
                    
            } else {
            	resultado = "Contraseña incorrecta";
            }
        } catch (SQLException e) {
            e.printStackTrace();
          
        }
        
        return resultado;
    }
}
