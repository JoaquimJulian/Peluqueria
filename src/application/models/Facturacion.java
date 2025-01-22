package application.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Facturacion {
	private int id_factura;
	private int id_cliente;
	private String nombre;
	private int id_trabajador;
	private int id_servicio;
	private String nombre_servicio;
	private int id_producto;
	private String nombre_producto;
	private double monto_total;
	private Double bizum;
	private Double tarjeta;
	private Double efectivo;
	private Date fecha;
	private String observacion_facturacion;
	
	public Facturacion(int id_factura, int id_cliente, int id_trabajador, int id_servicio, String nombre_servicio, int id_producto, String nombre_producto, 
            double monto_total, Double bizum, Double tarjeta, Double efectivo, Date fecha, String observacion_facturacion) {
		this.id_factura = id_factura;
		this.id_cliente = id_cliente;
		this.id_trabajador = id_trabajador;
		this.id_servicio = id_servicio;
		this.nombre_servicio = nombre_servicio;
		this.id_producto = id_producto;
		this.nombre_producto = nombre_producto;
		this.monto_total = monto_total;
		this.bizum = bizum;
		this.tarjeta = tarjeta;
		this.efectivo = efectivo;
		this.fecha = fecha;
		this.observacion_facturacion = observacion_facturacion;
	}
	
	public Facturacion(int idFactura, int idCliente, String nombre,
            int idServicio, String nombre_servicio,
            int idProducto, String nombre_producto,
            double monto_total, double bizum, double tarjeta, double efectivo,
            Date fecha, String observacion_facturacion) {
		this.id_factura = idFactura;
		this.id_cliente = idCliente;
		this.nombre = nombre;
		this.id_servicio = idServicio;
		this.nombre_servicio = nombre_servicio;
		this.id_producto = idProducto;
		this.nombre_producto = nombre_producto;
		this.monto_total = monto_total;
		this.bizum = bizum;
		this.tarjeta = tarjeta;
		this.efectivo = efectivo;
		this.fecha = fecha;
		this.observacion_facturacion = observacion_facturacion;
	}
	
	public Facturacion(Object datosCompartidos) {
		
	}
	
	public int getId_factura() {
		return id_factura;
	}
	public void setId_factura(int id_factura) {
		this.id_factura = id_factura;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_trabajador() {
		return id_trabajador;
	}
	public void setId_trabajador(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}
	public int getId_servicio() {
		return id_servicio;
	}
	public void setId_servicio(int id_servicio) {
		this.id_servicio = id_servicio;
	}
	public String getNombre_servicio() {
		return nombre_servicio;
	}
	public void setNombre_servicio(String nombre_servicio) {
		this.nombre_servicio = nombre_servicio;
	}
	public int getId_producto() {
		return id_producto;
	}
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
	public String getNombre_producto() {
		return nombre_producto;
	}
	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(double monto_total) {
		this.monto_total = monto_total;
	}
	public double getefectivo() {
		return efectivo;
	}
	public void setefectivo(double efectivo) {
		this.efectivo = efectivo;
	}
	public double getbizum() {
		return bizum;
	}
	public void setbizum(double bizum) {
		this.bizum = bizum;
	}
	public double gettarjeta() {
		return tarjeta;
	}
	public void settarjeta(double tarjeta) {
		this.tarjeta = tarjeta;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getObservacion_facturacion() {
		return observacion_facturacion;
	}
	public void setObservacion_facturacion(String observacion_facturacion) {
		this.observacion_facturacion = observacion_facturacion;
	}
	
	public static ObservableList<Facturacion> getFacturacion() throws SQLException {
        ObservableList<Facturacion> facturacion = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM facturacion";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
        	while (rs.next()) {
                facturacion.add(new Facturacion(
                    rs.getInt("id_factura"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_trabajador"),
                    rs.getInt("id_servicio"),
                    rs.getString("nombre_servicio"),
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getDouble("monto_total"),
                    rs.getDouble("bizum"),
                    rs.getDouble("tarjeta"),
                    rs.getDouble("efectivo"),
                    rs.getDate("fecha"),
                    rs.getString("observacion_facturacion")
                ));
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return facturacion;
    }
	
	public static ObservableList<Facturacion> getFacturacionConNombres() throws SQLException {
	    ObservableList<Facturacion> facturacion = FXCollections.observableArrayList();
	    Connection connection = databaseConection.getConnection();
	    String sql = "SELECT f.id_factura, f.id_cliente, c.nombre, " +
	                 "f.id_servicio, s.nombre_servicio, " +
	                 "f.id_producto, p.nombre_producto, " +
	                 "f.monto_total, f.bizum, f.tarjeta, f.efectivo, f.fecha, f.observacion_facturacion " +
	                 "FROM facturacion f " +
	                 "JOIN clientes c ON f.id_cliente = c.id_cliente " +
	                 "JOIN servicios s ON f.id_servicio = s.id_servicio " +
	                 "JOIN productos p ON f.id_producto = p.id_producto";
	    PreparedStatement stmt = connection.prepareStatement(sql);

	    try {
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            facturacion.add(new Facturacion(
	                rs.getInt("id_factura"),           // ID Factura
	                rs.getInt("id_cliente"),           // ID Cliente
	                rs.getString("nombre"),    // Nombre Cliente
	                rs.getInt("id_servicio"),          // ID Servicio
	                rs.getString("nombre_servicio"),   // Nombre Servicio
	                rs.getInt("id_producto"),          // ID Producto
	                rs.getString("nombre_producto"),   // Nombre Producto
	                rs.getDouble("monto_total"),       // Monto Total
	                rs.getDouble("bizum"),             // Bizum
	                rs.getDouble("tarjeta"),           // Tarjeta
	                rs.getDouble("efectivo"),          // Efectivo
	                rs.getDate("fecha"),               // Fecha
	                rs.getString("observacion_facturacion") // Observación
	            ));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return facturacion;
	}
	
	
	public static ObservableList<Facturacion> getFacturacionCliente(int id) throws SQLException {
        ObservableList<Facturacion> facturacion = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM facturacion WHERE id_cliente = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
        	stmt.setInt(1, id);
        	try(ResultSet rs = stmt.executeQuery()) {
        		while (rs.next()) {
                    facturacion.add(new Facturacion(
                        rs.getInt("id_factura"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_trabajador"),
                        rs.getInt("id_servicio"),
                        rs.getString("nombre_servicio"),
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getDouble("monto_total"),
                        rs.getDouble("bizum"),
                        rs.getDouble("tarjeta"),
                        rs.getDouble("efectivo"),
                        rs.getDate("fecha"),
                        rs.getString("observacion_facturacion")
                    ));
                }
        	}
       
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return facturacion;
    }
	
	public static ObservableList<Facturacion> getFacturacionTrabajador(int id) throws SQLException {
        ObservableList<Facturacion> facturacion = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM facturacion WHERE id_trabajador = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
        	stmt.setInt(1, id);
        	try(ResultSet rs = stmt.executeQuery()) {
        		while (rs.next()) {
                    facturacion.add(new Facturacion(
                        rs.getInt("id_factura"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_trabajador"),
                        rs.getInt("id_servicio"),
                        rs.getString("nombre_servicio"),
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getDouble("monto_total"),
                        rs.getDouble("bizum"),
                        rs.getDouble("tarjeta"),
                        rs.getDouble("efectivo"),
                        rs.getDate("fecha"),
                        rs.getString("observacion_facturacion")
                    ));
                }
        	}
       
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return facturacion;
    }
	
	public static boolean guardarObservaciones(String observaciones, int id) throws SQLException {
		String sql = "UPDATE facturacion SET observacion_facturacion = ? WHERE id_factura = ?";
		boolean exitoso = false;
		try (Connection connection = databaseConection.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            // Establecemos los parámetros del PreparedStatement
	            stmt.setString(1, observaciones);
	            stmt.setInt(2, id);
	        
	            // Ejecutamos la inserción
	            stmt.executeUpdate();
	            exitoso = true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error al guardar la observacion: " + e.getMessage());
	        }
        
		return exitoso;
	}
	
	public boolean insertarFactura(Integer clienteId, Integer trabajadorId,
            Integer serviciosIds, Integer productoId,
            String nombreServicio, String nombreProducto,
            double montoTotal, double montoEfectivo,
            double montoTarjeta,double montoBizum,
            java.sql.Date fecha, String observacionFacturacion) throws SQLException {

		// Suponiendo que tu sentencia SQL sea algo como esto
		String query = "INSERT INTO facturacion (id_cliente, id_trabajador, id_servicio, id_producto, nombre_servicio, nombre_producto, monto_total, bizum, tarjeta, efectivo, fecha, observacion_facturacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		
		try (Connection connection = databaseConection.getConnection();
		PreparedStatement stmt = connection.prepareStatement(query)) {
		
		// Manejo de valores nulos para los campos opcionales
		if (clienteId != null) {
		stmt.setInt(1, clienteId);
		} else {
		stmt.setNull(1, java.sql.Types.INTEGER);
		}
		
		if (trabajadorId != null) {
		stmt.setInt(2, trabajadorId);
		} else {
		stmt.setNull(2, java.sql.Types.INTEGER);
		}
		
		if (serviciosIds != null) {
		stmt.setInt(3, serviciosIds);
		} else {
		stmt.setNull(3, java.sql.Types.INTEGER);
		}
		
		if (productoId != null) {
		stmt.setInt(4, productoId);
		} else {
		stmt.setNull(4, java.sql.Types.INTEGER);
		}
		
		if (nombreServicio != null) {
		stmt.setString(5, nombreServicio);
		} else {
		stmt.setNull(5, java.sql.Types.VARCHAR);
		}
		
		if (nombreProducto != null) {
		stmt.setString(6, nombreProducto);
		} else {
		stmt.setNull(6, java.sql.Types.VARCHAR);
		}
		
		stmt.setDouble(7, montoTotal);
		
		stmt.setDouble(8, montoEfectivo);
		stmt.setDouble(9, montoTarjeta);
		stmt.setDouble(10, montoBizum);
		
		if (fecha != null) {
		stmt.setDate(11, fecha);
		} else {
		stmt.setNull(11, java.sql.Types.DATE);
		}
		
		if (observacionFacturacion != null) {
		stmt.setString(12, observacionFacturacion);
		} else {
		stmt.setNull(12, java.sql.Types.VARCHAR);
		}
		
		// Ejecutar la consulta
		int filasAfectadas = stmt.executeUpdate();
		
		return filasAfectadas > 0;
		} catch (SQLException e) {
		e.printStackTrace();
		throw new SQLException("Error al insertar la factura", e);
		}
		}
	
	public static boolean restarStock(Integer id, Integer stock) {
	    // Consulta SQL corregida
	    String sql = "UPDATE productos SET cantidad_en_stock = ? WHERE id_producto = ?";
		boolean exitoso = false;

	    try (Connection connection = databaseConection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql)) {

	        // Establecemos los parámetros del PreparedStatement
	        stmt.setInt(1, stock); // El primer "?" en la consulta
	        stmt.setInt(2, id);   // El segundo "?" en la consulta
	    
	        // Ejecutamos la actualización
	        stmt.executeUpdate();
	        exitoso = true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exitoso;
	}
	
	
}
