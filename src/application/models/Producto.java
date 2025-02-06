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
    private int aviso_stock;
    private String codigo_barras;
    private boolean activo;

    public Producto(int id, String nombre, String descripcion, double precioVenta, double precioCosto, int cantidad_en_stock, int aviso_stock, String codigo_barras, boolean activo) {
    	this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.precioCosto = precioCosto;
        this.cantidad_en_stock = cantidad_en_stock;
        this.aviso_stock = aviso_stock;
        this.codigo_barras = codigo_barras;
        this.activo = activo;
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
    
    public int getAviso_stock() {
        return aviso_stock;
    }

    public void setAviso_stock(int aviso_stock) {
        this.aviso_stock = aviso_stock;
    }
    
    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }
    
    public boolean getActivo() {
    	return activo;
    }
    
    public void setActivo(boolean activo) {
    	this.activo = activo;
    }

    
    public static void crearproducto(String nombre, String descripcion, double precioVenta, double precioCosto, int cantidad_en_stock, Long codigo_barras, int aviso_stock) {
		String sql = "INSERT INTO productos (nombre_producto, descripcion, precio_venta, precio_costo, cantidad_en_stock, codigo_barras, alerta_bajo_stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection connection = databaseConection.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            // Establecemos los parámetros del PreparedStatement
	            stmt.setString(1, nombre);
	            stmt.setString(2, descripcion);
	            stmt.setDouble(3, precioVenta);
	            stmt.setDouble(4, precioCosto);
	            stmt.setInt(5, cantidad_en_stock);
	            stmt.setLong(6, codigo_barras);
	            stmt.setLong(7, aviso_stock);

	            // Ejecutamos la inserción
	            stmt.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error al crear el producto: " + e.getMessage());
	        }
	}
	
	public static void editarproducto(Integer id, String nombre, String descripcion, double precioVenta, double precioCosto, int cantidad_en_stock, Long codigo_barras, int aviso_stock) {
		String sql = "UPDATE productos SET nombre_producto = ?, descripcion = ?, precio_venta  = ?, precio_costo = ?, cantidad_en_stock = ?, codigo_barras = ?, aviso_stock = ? WHERE id_producto = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setString(1, nombre);
	            stmt.setString(2, descripcion);
	            stmt.setDouble(3, precioVenta);
	            stmt.setDouble(4, precioCosto);
	            stmt.setInt(5, cantidad_en_stock);
				stmt.setLong(6, codigo_barras);
				stmt.setLong(7, aviso_stock);
				stmt.setInt(8, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al editar el producto: " + e.getMessage());
        }
	}
	
	public static void desactivarProducto(Integer id) {
		String sql = "UPDATE productos SET activo = 0 WHERE id_producto = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setInt(1, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al desactivar el producto: " + e.getMessage());
        }
	}
	
	public static void activarProducto(Integer id) {
		String sql = "UPDATE productos SET activo = 1 WHERE id_producto = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setInt(1, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al desactivar el producto: " + e.getMessage());
        }
	}
	
	public static void eliminarProducto(Integer id) {
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
                     rs.getInt("cantidad_en_stock"),
                     rs.getInt("aviso_stock"),
                     rs.getString("codigo_barras"),
                     rs.getBoolean("activo")
                 ));
             }
        
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return productos;
    }
	
	public static ObservableList<Producto> getProductosActivos() throws SQLException {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM productos WHERE activo = 1";
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
                     rs.getInt("cantidad_en_stock"),
                     rs.getInt("aviso_stock"),
                     rs.getString("codigo_barras"),
                     rs.getBoolean("activo")
                 ));
             }
        
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return productos;
    }
    
	public static ObservableList<Producto> getProductosInactivos() throws SQLException {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM productos WHERE activo = 0";
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
                     rs.getInt("cantidad_en_stock"),
                     rs.getInt("aviso_stock"),
                     rs.getString("codigo_barras"),
                     rs.getBoolean("activo")
                 ));
             }
        
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return productos;
    }
	
	public static String nombreProducto(int id) throws SQLException {
		String sql = "SELECT nombre_producto WHERE id_producto = ?";
        Connection connection = databaseConection.getConnection();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
        	stmt.setInt(1, id);
        	try(ResultSet rs = stmt.executeQuery()) {
        		if (rs.next()) {
            		return rs.getString("nombre_producto");
        		}
        	}
       
        }catch (Exception e) {
            e.printStackTrace();
        }
		
        return null;
	}
	
	
	
	
	public static Producto buscarPorCodigoBarras(String codigo_barras) throws SQLException {
	    
	    Connection conexion = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conexion = databaseConection.getConnection(); // Asegúrate de usar tu conexión correctamente
	        String query = "SELECT * FROM productos WHERE codigo_barras = ?";
	        
	        stmt = conexion.prepareStatement(query);
	        stmt.setString(1, codigo_barras.trim()); // Usamos setString para tipo varchar
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            // Aquí usas los nombres correctos de las columnas
	            return new Producto(
	                rs.getInt("id_producto"),
	                rs.getString("nombre_producto"),
	                rs.getString("descripcion"),
	                rs.getDouble("precio_venta"),
	                rs.getDouble("precio_costo"),
	                rs.getInt("cantidad_en_stock"),
                    rs.getInt("aviso_stock"),
	                rs.getString("codigo_barras"),
	                rs.getBoolean("activo")
	            );
	        }
	    } finally {
	        if (rs != null) rs.close();
	        if (stmt != null) stmt.close();
	        if (conexion != null) conexion.close();
	    }

	    return null; // Si no se encuentra el producto
	}

	public static ObservableList<String> codigos() throws SQLException {
	    ObservableList<String> codigos = FXCollections.observableArrayList();

	    Connection connection = databaseConection.getConnection();

	    String sql = "SELECT codigo_barras FROM productos";

	    PreparedStatement stmt = connection.prepareStatement(sql);

	    try (ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            codigos.add(rs.getString("codigo_barras"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Manejo de excepciones
	    } finally {
	        stmt.close();
	        connection.close();
	    }

	    return codigos;
	}
	
	
	public boolean restarStock(String codigoBarras, int cantidad) throws SQLException {
		Connection connection = databaseConection.getConnection();
		String query = "UPDATE productos SET cantidad_en_stock = cantidad_en_stock - ? WHERE codigo_barras = ? AND cantidad_en_stock >= ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        
        try (
        		ResultSet rs = stmt.executeQuery(query);
        	
        	PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cantidad);
            statement.setString(2, codigoBarras);
            statement.setInt(3, cantidad);
            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	

	
	
}
