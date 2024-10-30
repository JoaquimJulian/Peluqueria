package application.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Producto {

	private int id;
    private String nombre;
    private String descripcion;
    private double precioVenta;
    private double precioCosto;
    private int cantidad_en_stock;

    public Producto(int id, String nombre, String descripcion, double precioVenta, double precioCosto, int cantidad_en_stock) {
    	this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.precioCosto = precioCosto;
        this.cantidad_en_stock = cantidad_en_stock;
    }
    
 // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public int getCantidad_en_stock() {
        return cantidad_en_stock;
    }

    public void setCantidad_en_stock(int cantidad_en_stock) {
        this.cantidad_en_stock = cantidad_en_stock;
    }
    
    public static void crearproducto(String nombre, String descripcion, double precioVenta, double precioCosto, int cantidad_en_stock) {
		String sql = "INSERT INTO productos (nombre_producto, descripcion, precio_venta, precio_costo, cantidad_en_stock) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection connection = databaseConection.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            // Establecemos los parámetros del PreparedStatement
	            stmt.setString(1, nombre);
	            stmt.setString(2, descripcion);
	            stmt.setDouble(3, precioVenta);
	            stmt.setDouble(4, precioCosto);
	            stmt.setInt(5, cantidad_en_stock);

	            // Ejecutamos la inserción
	            stmt.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error al crear el producto: " + e.getMessage());
	        }
	}
	
	public static void editarproducto(Integer id, String nombre, String descripcion, double precioVenta, double precioCosto, int cantidad_en_stock) {
		String sql = "UPDATE productos SET nombre_producto = ?, descripcion = ?, precio_venta  = ?, precio_costo = ?, cantidad_en_stock = ? WHERE id_producto = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setString(1, nombre);
	            stmt.setString(2, descripcion);
	            stmt.setDouble(3, precioVenta);
	            stmt.setDouble(4, precioCosto);
	            stmt.setInt(5, cantidad_en_stock);
				stmt.setInt(6, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al editar el producto: " + e.getMessage());
        }
	}
	
	public static void eliminarproducto(Integer id) {
		String sql = "DELETE FROM productos WHERE id_producto = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setInt(1, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
	}
	
	public static ObservableList<Producto> getProductos() throws SQLException {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM productos";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
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
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return productos;
    }
    
}
