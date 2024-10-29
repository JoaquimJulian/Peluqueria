package application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrabajadoresModel {

	public static ObservableList<Trabajador> getTrabajadores() throws SQLException {
        ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM trabajadores";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
        	while (rs.next()) {
                trabajadores.add(new Trabajador(
                    rs.getInt("id_trabajador"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("telefono"),
                    rs.getString("email"),
                    rs.getString("foto_perfil"),
                    rs.getString("contrasena"),
                    rs.getBoolean("es_administrador"),
                    rs.getDouble("comision")
                ));
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return trabajadores;
    }
    
    
 
    public void crearproductos(Producto producto) {
        String sql = "INSERT INTO productos (nombre_producto, descripcion, precio_venta, precio_costo, cantidad_en_stock) VALUES (?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peluqueria", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setDouble(3, producto.getPrecioVenta());
            pstmt.setDouble(4, producto.getPrecioCosto()); 
           // pstmt.setInt(5, producto.getStock()); 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



	private Connection connect() {
		// TODO Auto-generated method stub
		return null;
	}


}
