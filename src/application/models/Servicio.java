package application.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Servicio {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String duracionEstimada;
    private boolean requiereReserva;

    // Constructor
    public Servicio(int id, String nombre, String descripcion, double precio, String duracionEstimada, boolean requiereReserva) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionEstimada = duracionEstimada;
        this.requiereReserva = requiereReserva;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(String duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public boolean isRequiereReserva() {
        return requiereReserva;
    }

    public void setRequiereReserva(boolean requiereReserva) {
        this.requiereReserva = requiereReserva;
    }
    
    public static void crearServicio(String nombre, Double precio, String duracion, String descripcion, Boolean requiereReserva) {
		String sql = "INSERT INTO servicios (nombre_servicio, precio, duracion_estimada, descripcion, requiere_reserva) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection connection = databaseConection.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            // Establecemos los parámetros del PreparedStatement
	            stmt.setString(1, nombre);
	            stmt.setDouble(2, precio);
	            stmt.setString(3, duracion);
	            stmt.setString(4, descripcion);
	            stmt.setBoolean(5, requiereReserva);

	            // Ejecutamos la inserción
	            stmt.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error al crear el servicio: " + e.getMessage());
	        }
	}
	
	public static void editarServicio(Integer id, String nombre, Double precio, String duracion, String descripcion, Boolean requiereReserva) {
		String sql = "UPDATE servicios SET nombre_servicio = ?, precio = ?, duracion_estimada = ?, descripcion = ?, requiere_reserva = ? WHERE id_servicio = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setString(1, nombre);
				stmt.setDouble(2, precio);
				stmt.setString(3, duracion);
				stmt.setString(4, descripcion);
				stmt.setBoolean(5, requiereReserva);
				stmt.setInt(6, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al editar el servicio: " + e.getMessage());
        }
	}
	
	public static void eliminarServicio(Integer id) {
		String sql = "DELETE FROM servicios WHERE id_servicio = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setInt(1, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el servicio: " + e.getMessage());
        }
	}
	
	public static void desactivarServicio(Integer id) {
		String sql = "UPDATE servicios SET activo = 0 WHERE id_servicio = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setInt(1, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al desactivar el servicio: " + e.getMessage());
        }
	}
	
	public static void activarServicio(Integer id) {
		String sql = "UPDATE servicios SET activo = 1 WHERE id_servicio = ?";
		
		try (Connection connection = databaseConection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				stmt.setInt(1, id);
				
				stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al activar el servicio: " + e.getMessage());
        }
	}
	
	public static ObservableList<Servicio> getServicios() throws SQLException {
        ObservableList<Servicio> servicios = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM servicios";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
        	while (rs.next()) {
                servicios.add(new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre_servicio"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("duracion_estimada"),
                    rs.getBoolean("requiere_reserva")
                ));
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return servicios;
    }
	
	public static ObservableList<Servicio> getServiciosActivos() throws SQLException {
        ObservableList<Servicio> servicios = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM servicios WHERE activo = 1";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
        	while (rs.next()) {
                servicios.add(new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre_servicio"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("duracion_estimada"),
                    rs.getBoolean("requiere_reserva")
                ));
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return servicios;
    }
	
	public static ObservableList<Servicio> getServiciosInactivos() throws SQLException {
        ObservableList<Servicio> servicios = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM servicios WHERE activo = 0";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
        	while (rs.next()) {
                servicios.add(new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre_servicio"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("duracion_estimada"),
                    rs.getBoolean("requiere_reserva")
                ));
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return servicios;
    }
	
	public static String nombreServicio(int id) throws SQLException {
		String sql = "SELECT nombre_servicio WHERE id_servicio = ?";
        Connection connection = databaseConection.getConnection();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
        	stmt.setInt(1, id);
        	try(ResultSet rs = stmt.executeQuery()) {
        		if (rs.next()) {
            		return rs.getString("nombre_servicio");
        		}
        	}
       
        }catch (Exception e) {
            e.printStackTrace();
        }
		
        return null;
	}
}
