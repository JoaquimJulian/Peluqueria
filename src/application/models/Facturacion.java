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
	private int id_trabajador;
	private int id_servicio;
	private String nombre_servicio;
	private int id_producto;
	private String nombre_producto;
	private double monto_total;
	private String metodo_pago;
	private Date fecha;
	private String observacion_facturacion;
	
	public Facturacion(int id_factura, int id_cliente, int id_trabajador, int id_servicio, String nombre_servicio, int id_producto, String nombre_producto, 
            double monto_total, String metodo_pago, Date fecha, String observacion_facturacion) {
		this.id_factura = id_factura;
		this.id_cliente = id_cliente;
		this.id_trabajador = id_trabajador;
		this.id_servicio = id_servicio;
		this.nombre_servicio = nombre_servicio;
		this.id_producto = id_producto;
		this.nombre_producto = nombre_producto;
		this.monto_total = monto_total;
		this.metodo_pago = metodo_pago;
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
	public double getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(double monto_total) {
		this.monto_total = monto_total;
	}
	public String getMetodo_pago() {
		return metodo_pago;
	}
	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
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
                    rs.getString("metodo_pago"),
                    rs.getDate("fecha"),
                    rs.getString("observacion_facturacion")
                ));
            }
        	
        }catch (Exception e) {
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
                        rs.getString("metodo_pago"),
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
            double montoTotal, String metodoPago,
            java.sql.Date fecha, String observacionFacturacion) throws SQLException {

		// Suponiendo que tu sentencia SQL sea algo como esto
		String query = "INSERT INTO facturacion (id_cliente, id_trabajador, id_servicio, id_producto, nombre_servicio, nombre_producto, monto_total, metodo_pago, fecha, observacion_facturacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
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
		
		stmt.setString(8, metodoPago);
		
		if (fecha != null) {
		stmt.setDate(9, fecha);
		} else {
		stmt.setNull(9, java.sql.Types.DATE);
		}
		
		if (observacionFacturacion != null) {
		stmt.setString(10, observacionFacturacion);
		} else {
		stmt.setNull(10, java.sql.Types.VARCHAR);
		}
		
		// Ejecutar la consulta
		int filasAfectadas = stmt.executeUpdate();
		
		return filasAfectadas > 0;
		} catch (SQLException e) {
		e.printStackTrace();
		throw new SQLException("Error al insertar la factura", e);
		}
		}
	
	
}
