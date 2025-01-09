package application.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private int telefono;
    private String email;
    private boolean lpd;

    // Constructor
    public Cliente(int id, String nombre, String apellido, int telefono, String email, boolean lpd) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.lpd = lpd;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLpd() {
        return lpd;
    }

    public void setLpd(boolean lpd) {
        this.lpd = lpd;
    }
    
    public static void crearCliente(String nombre, String apellido, Integer telefono, String email, Boolean lpd) {
		String sql = "INSERT INTO clientes (nombre, apellidos, telefono, email, lpd) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection connection = databaseConection.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            // Establecemos los parámetros del PreparedStatement
	            stmt.setString(1, nombre);
	            stmt.setString(2, apellido);
	            stmt.setInt(3, telefono);
	            stmt.setString(4, email);
	            stmt.setBoolean(5, lpd);

	            // Ejecutamos la inserción
	            stmt.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error al crear el servicio: " + e.getMessage());
	        }
	}
	
    
    
    public static List<String[]> cargarDatos(int id_cliente) throws SQLException {
        List<String[]> resultados = new ArrayList<>();
        
        Connection connection = databaseConection.getConnection();

        String sql = "SELECT f.fecha, s.nombre_servicio AS nombre_servicio, p.nombre_producto AS nombre_producto, f.monto_total " +
                     "FROM facturacion f " +
                     "LEFT JOIN servicios s ON f.id_servicio = s.id_servicio " +
                     "LEFT JOIN productos p ON f.id_producto = p.id_producto " +
                     "WHERE f.id_cliente = ?;";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id_cliente);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Obtener los datos de cada fila
                String fecha = rs.getString("fecha");
                String servicio = rs.getString("nombre_servicio");
                String producto = rs.getString("nombre_producto");
                String montoTotal = rs.getString("monto_total");

                // Agregar los datos como un array de Strings
                resultados.add(new String[]{fecha, servicio, producto, montoTotal});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            connection.close();
        }

        return resultados;
    }

    
    
	public static void editarCliente(Integer id, String nombre, String apellido, Integer telefono, String email, Boolean lpd) {
		String sql = "UPDATE clientes SET nombre = ?, apellidos = ?, telefono = ?, email = ?, lpd = ? WHERE id_cliente = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setString(1, nombre);
				stmt.setString(2, apellido);
	            stmt.setInt(3, telefono);
	            stmt.setString(4, email);
	            stmt.setBoolean(5, lpd);
				stmt.setInt(6, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al editar el cliente: " + e.getMessage());
        }
	}
	
	public static void eliminarCliente(Integer id) {
		String sql = "DELETE FROM clientes WHERE id_cliente = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setInt(1, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
	}
	
	public static ObservableList<Cliente> getClientes() throws SQLException {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM clientes";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
        	while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("telefono"),
                    rs.getString("email"),
                    rs.getBoolean("lpd")
                ));
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return clientes;
    }
    
}
