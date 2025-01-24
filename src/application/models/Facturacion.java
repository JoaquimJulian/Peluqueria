package application.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Time horaCobro;
	private Date fecha;
	private String observacion_facturacion;
	
	public Facturacion(int id_factura, int id_cliente, int id_trabajador, int id_servicio, String nombre_servicio, int id_producto, String nombre_producto, 
            double monto_total, Double bizum, Double tarjeta, Double efectivo, Date fecha, Time horaCobro, String observacion_facturacion) {
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
		this.horaCobro = horaCobro;
		this.fecha = fecha;
		this.observacion_facturacion = observacion_facturacion;
	}
	
	public class FacturacionTrabajador {
		private String nombre_trabajador;
		private double total_facturado;
	}
	
	public Facturacion(int idFactura, int idCliente, String nombre,
            int idServicio, String nombre_servicio,
            int idProducto, String nombre_producto,
            double monto_total, double bizum, double tarjeta, double efectivo,
            Date fecha, Time hora, String observacion_facturacion) {
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
	public Time getHoraCobro() {
		return horaCobro;
	}
	public void setHoraCobro(Time horaCobro) {
		this.horaCobro = horaCobro;
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
                    rs.getTime("horaCobro"),
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
	                 "f.monto_total, f.bizum, f.tarjeta, f.efectivo, f.fecha, f.horaCobro, f.observacion_facturacion " +
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
	                rs.getDate("fecha"),
	                rs.getTime("horaCobro"),// Fecha
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
                        rs.getTime("horaCobro"),
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
                        rs.getTime("horaCobro"),
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
	
	// METODOS COBRO
	
	public boolean insertarFactura(Integer clienteId, Integer trabajadorId,
            Integer serviciosIds, Integer productoId,
            String nombreServicio, String nombreProducto,
            double montoTotal, double montoEfectivo,
            double montoTarjeta,double montoBizum,
            java.sql.Date fecha, Time horaCobro, String observacionFacturacion) throws SQLException {

		// Suponiendo que tu sentencia SQL sea algo como esto
		String query = "INSERT INTO facturacion (id_cliente, id_trabajador, id_servicio, id_producto, nombre_servicio, nombre_producto, monto_total, bizum, tarjeta, efectivo, fecha, horaCobro, observacion_facturacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
		
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
		stmt.setTime(12, horaCobro);
		
		if (observacionFacturacion != null) {
		stmt.setString(13, observacionFacturacion);
		} else {
		stmt.setNull(13, java.sql.Types.VARCHAR);
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
	
	// METODOS ESTADISTICAS
	public static List<Map<String, Object>> sumaTotalFacturacion(java.sql.Date inicio, java.sql.Date fin, Integer idTrabajador) throws SQLException {
	    List<Map<String, Object>> resultados = new ArrayList<>();
	    Map<String, Double> sumasPorTrabajador = new HashMap<>();

	    // Consulta para sumar los monto_total de los registros no repetidos dentro del rango de fechas y por trabajador
	    String sqlNoRepetidos = "SELECT t.nombre, SUM(f.monto_total) AS total_no_repetidos "
	                            + "FROM facturacion f "
	                            + "JOIN trabajadores t ON f.id_trabajador = t.id_trabajador "
	                            + "WHERE 1=1 ";

	    if (inicio != null && fin != null) {
	        sqlNoRepetidos += "AND f.fecha BETWEEN ? AND ? ";
	    }

	    if (idTrabajador != null) {
	        sqlNoRepetidos += "AND f.id_trabajador = ? ";
	    }

	    sqlNoRepetidos += "GROUP BY f.id_cliente, f.monto_total, f.bizum, f.tarjeta, f.efectivo, f.fecha, f.horaCobro "
	                     + "HAVING COUNT(*) = 1"; // Solo registros no repetidos

	    // Consulta para sumar los monto_total de los registros repetidos dentro del rango de fechas y por trabajador
	    String sqlRepetidos = "SELECT t.nombre, SUM(f.monto_total) / COUNT(*) AS total_repetidos "
	                          + "FROM facturacion f "
	                          + "JOIN trabajadores t ON f.id_trabajador = t.id_trabajador "
	                          + "WHERE 1=1 ";

	    if (inicio != null && fin != null) {
	        sqlRepetidos += "AND f.fecha BETWEEN ? AND ? ";
	    }

	    if (idTrabajador != null) {
	        sqlRepetidos += "AND f.id_trabajador = ? ";
	    }

	    sqlRepetidos += "GROUP BY f.id_cliente, f.monto_total, f.bizum, f.tarjeta, f.efectivo, f.fecha, f.horaCobro "
	                   + "HAVING COUNT(*) > 1"; // Solo registros repetidos

	    try (Connection conn = databaseConection.getConnection()) {
	        // Procesar los registros no repetidos
	        try (PreparedStatement stmtNoRepetidos = conn.prepareStatement(sqlNoRepetidos)) {
	            int paramIndex = 1;
	            if (inicio != null && fin != null) {
	                stmtNoRepetidos.setDate(paramIndex++, inicio);
	                stmtNoRepetidos.setDate(paramIndex++, fin);
	            }
	            if (idTrabajador != null) {
	                stmtNoRepetidos.setInt(paramIndex++, idTrabajador);
	            }

	            ResultSet rsNoRepetidos = stmtNoRepetidos.executeQuery();
	            while (rsNoRepetidos.next()) {
	                String nombre = rsNoRepetidos.getString("nombre");
	                double totalNoRepetidos = rsNoRepetidos.getDouble("total_no_repetidos");
	                
	                

	                // Acumular la suma total_no_repetidos
	                sumasPorTrabajador.merge(nombre, totalNoRepetidos, Double::sum);
	            }
	        }

	        // Procesar los registros repetidos
	        try (PreparedStatement stmtRepetidos = conn.prepareStatement(sqlRepetidos)) {
	            int paramIndex = 1;
	            if (inicio != null && fin != null) {
	                stmtRepetidos.setDate(paramIndex++, inicio);
	                stmtRepetidos.setDate(paramIndex++, fin);
	            }
	            if (idTrabajador != null) {
	                stmtRepetidos.setInt(paramIndex++, idTrabajador);
	            }

	            ResultSet rsRepetidos = stmtRepetidos.executeQuery();
	            while (rsRepetidos.next()) {
	                String nombre = rsRepetidos.getString("nombre");
	                double totalRepetidos = rsRepetidos.getDouble("total_repetidos");

	                // Acumular la suma total_repetidos
	                sumasPorTrabajador.merge(nombre, totalRepetidos, Double::sum);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Crear la lista de resultados con las sumas totales por trabajador
	    for (Map.Entry<String, Double> entry : sumasPorTrabajador.entrySet()) {
	        Map<String, Object> row = new HashMap<>();
	        row.put("nombre", entry.getKey());
	        row.put("facturacion_total", entry.getValue()); // Esta es la suma total
	        resultados.add(row);
	    }

	    return resultados;
	}
	
	
	
	
	public static List<Map<String, Object>> facturacionPorTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
	    List<Map<String, Object>> resultados = new ArrayList<>();
	    String sql = "SELECT t.nombre, SUM(f.monto_total) as total_facturado " +
	                 "FROM facturacion f " +
	                 "JOIN trabajadores t ON f.id_trabajador = t.id_trabajador ";

	    // Agregar filtro por rango de fechas
	    if (inicio != null && fin != null) {
	        sql += "WHERE f.fecha BETWEEN ? AND ? ";
	        if (idTrabajador != null) {
	            sql += "AND f.id_trabajador = ? ";
	        }
	    } else if (idTrabajador != null) {
	        sql += "WHERE f.id_trabajador = ? ";
	    }

	    sql += "GROUP BY t.id_trabajador, t.nombre";

	    try (Connection conn = databaseConection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        int paramIndex = 1;

	        if (inicio != null && fin != null) {
	            stmt.setDate(paramIndex++, inicio);
	            stmt.setDate(paramIndex++, fin);
	        }

	        if (idTrabajador != null) {
	            stmt.setInt(paramIndex++, idTrabajador);
	        }

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Map<String, Object> row = new HashMap<>();
	            row.put("nombre", rs.getString("nombre"));
	            row.put("total_facturado", rs.getDouble("total_facturado"));
	            resultados.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return resultados;
	}




	
	public static List<Map<String, Object>> productosPorTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
	    List<Map<String, Object>> resultados = new ArrayList<>();
	    String sql = "SELECT t.nombre, COUNT(*) as numero_productos " +
	                 "FROM facturacion f " +
	                 "JOIN trabajadores t ON f.id_trabajador = t.id_trabajador " +
	                 "WHERE f.id_producto != 0 ";

	    if (inicio != null && fin != null) {
	        sql += "AND f.fecha BETWEEN ? AND ? ";
	    }

	    if (idTrabajador != null) {
	        sql += "AND f.id_trabajador = ? "; // Filtrar solo por el trabajador logueado si no es admin
	    }

	    sql += "GROUP BY t.id_trabajador, t.nombre";

	    try (Connection conn = databaseConection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        int paramIndex = 1;

	        if (inicio != null && fin != null) {
	            stmt.setDate(paramIndex++, inicio);
	            stmt.setDate(paramIndex++, fin);
	        }

	        if (idTrabajador != null) {
	            stmt.setInt(paramIndex++, idTrabajador);
	        }

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Map<String, Object> row = new HashMap<>();
	            row.put("nombre", rs.getString("nombre"));
	            row.put("numero_productos", rs.getInt("numero_productos"));
	            resultados.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return resultados;
	}





	
	public static List<Map<String, Object>> serviciosPorTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
	    List<Map<String, Object>> resultados = new ArrayList<>();
	    String sql = "SELECT t.nombre, COUNT(*) as numero_servicios " +
	                 "FROM facturacion f " +
	                 "JOIN trabajadores t ON f.id_trabajador = t.id_trabajador " +
	                 "WHERE f.id_servicio != 0 ";

	    // Filtro por rango de fechas
	    if (inicio != null && fin != null) {
	        sql += "AND f.fecha BETWEEN ? AND ? ";
	    }

	    // Filtro por trabajador si no es administrador
	    if (idTrabajador != null) {
	        sql += "AND f.id_trabajador = ? ";
	    }

	    sql += "GROUP BY t.id_trabajador, t.nombre";

	    try (Connection conn = databaseConection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        int paramIndex = 1;

	        // Establece las fechas si están definidas
	        if (inicio != null && fin != null) {
	            stmt.setDate(paramIndex++, inicio);
	            stmt.setDate(paramIndex++, fin);
	        }

	        // Establece el ID del trabajador si no es administrador
	        if (idTrabajador != null) {
	            stmt.setInt(paramIndex++, idTrabajador);
	        }

	        // Ejecuta la consulta
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Map<String, Object> row = new HashMap<>();
	            row.put("nombre", rs.getString("nombre"));
	            row.put("numero_servicios", rs.getInt("numero_servicios"));
	            resultados.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return resultados;
	}





	
}
