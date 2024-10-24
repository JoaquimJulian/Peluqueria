package application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductosModel {

    // Método para obtener los productos de la base de datos
    public ObservableList<Producto> getProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        try {
            // Conexión a la base de datos
        	Connection conn = databaseConection.getConnection();
            Statement stmt = conn.createStatement();
            String query = "SELECT id_producto, nombre_producto, descripcion, precio_venta, precio_costo, cantidad_en_stock FROM productos";
            ResultSet rs = stmt.executeQuery(query);

            // Recorrer los resultados y agregarlos a la lista
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio_venta"),
                    rs.getDouble("precio_costo"), 
                    rs.getInt("cantidad_en_stock")
                ));
            }

            // Cerrar la conexión
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productos;
    }
    
    
 
    public void crearproductos(Producto producto) {
        String sql = "INSERT INTO productos (nombre_producto, descripcion, precio_venta, precio_costo, cantidad_en_stock) VALUES (?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peluqueria", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setDouble(3, producto.getPrecioVenta());
            pstmt.setDouble(4, producto.getPrecioCosto()); 
            pstmt.setInt(5, producto.getStock()); 
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
