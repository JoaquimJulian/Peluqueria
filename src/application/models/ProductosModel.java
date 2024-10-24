package application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductosModel {

    // Método para obtener los productos de la base de datos
    public ObservableList<Producto> getProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        try {
            // Conexión a la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peluqueria", "root", "");
            Statement stmt = conn.createStatement();
            String query = "SELECT id_producto, nombre_producto, descripcion, precio_venta, precio_costo, cantidad_en_stock FROM productos";
            ResultSet rs = stmt.executeQuery(query);

            // Recorrer los resultados y agregarlos a la lista
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id_producto"),
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
}
