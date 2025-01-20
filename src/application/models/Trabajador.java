// Trabajador.java

package application.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Trabajador {

	private static Trabajador trabajadorLogueado;
    private int id;
    private String nombre;
    private String apellidos;
    private int telefono;
    private String email;
    private String contrasena;
    private boolean esAdministrador;
    private double comision;
    private boolean activo;

    
	// Constructor
    public Trabajador(int id, String nombre, String apellidos, int telefono, String email, String contrasena, boolean esAdministrador, double comision) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.contrasena = contrasena;
        this.esAdministrador = esAdministrador;
        this.comision = comision;
    }
    // Constructor vacio
    public Trabajador() {
        
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }
    public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
    
 // Método para establecer al trabajador logueado
    public static void setTrabajadorLogueado(Trabajador trabajador) {
        trabajadorLogueado = trabajador;
    }

    // Método para obtener al trabajador logueado
    public static Trabajador getTrabajadorLogueado() {
        return trabajadorLogueado;
    }

    // Método para crear un trabajador en la base de datos
    public void crearTrabajador(String nombre, String apellidos, int telefono, String email, String contrasena, boolean esAdministrador, double comision) {
        String sql = "INSERT INTO trabajadores (nombre, apellidos, telefono, email, contrasena, es_administrador, comision) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellidos);
            stmt.setInt(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, contrasena);
            stmt.setBoolean(6, esAdministrador);
            stmt.setDouble(7, comision);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear el trabajador: " + e.getMessage());
        }
    }

    // Método para actualizar un trabajador en la base de datos
    public boolean actualizarTrabajador(Trabajador trabajador) {
        String sql = "UPDATE trabajadores SET nombre = ?, apellidos = ?, telefono = ?, email = ?, contrasena = ?, es_administrador = ?, comision = ? WHERE id_trabajador = ?";
        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, trabajador.getNombre());
            stmt.setString(2, trabajador.getApellidos());
            stmt.setInt(3, trabajador.getTelefono());
            stmt.setString(4, trabajador.getEmail());
            stmt.setString(5, trabajador.getContrasena());
            stmt.setBoolean(6, trabajador.isEsAdministrador());
            stmt.setDouble(7, trabajador.getComision());
            stmt.setInt(8, trabajador.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el trabajador: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un trabajador en la base de datos
    public void desactivarTrabajador(int id) {
        String sql = "UPDATE trabajadores SET activo = 0 WHERE id_trabajador = ?";
        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al desactivar el trabajador: " + e.getMessage());
        }
    }
    
    public void activarTrabajador(int id) {
        String sql = "UPDATE trabajadores SET activo = 1 WHERE id_trabajador = ?";
        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al activar el trabajador: " + e.getMessage());
        }
    }
    
    public void eliminarTrabajador(int id) {
        String sql = "DELETE FROM trabajadores WHERE id_trabajador = ?";
        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el trabajador: " + e.getMessage());
        }
    }

    // Método para obtener la lista de todos los trabajadores de la base de datos
    public static ObservableList<Trabajador> getTrabajadores() {
        ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
        String sql = "SELECT * FROM trabajadores";

        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trabajador trabajador = new Trabajador(
                    rs.getInt("id_trabajador"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("telefono"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    rs.getBoolean("es_administrador"),
                    rs.getDouble("comision")
                );
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los trabajadores: " + e.getMessage());
        }
        return trabajadores;
    }
    
    public static ObservableList<Trabajador> getTrabajadoresActivos() {
        ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
        String sql = "SELECT * FROM trabajadores WHERE activo = 1";

        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trabajador trabajador = new Trabajador(
                    rs.getInt("id_trabajador"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("telefono"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    rs.getBoolean("es_administrador"),
                    rs.getDouble("comision")
                );
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los trabajadores: " + e.getMessage());
        }
        return trabajadores;
    }
    
    public static ObservableList<Trabajador> getTrabajadoresInactivos() {
        ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
        String sql = "SELECT * FROM trabajadores WHERE activo = 0";

        try (Connection connection = databaseConection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trabajador trabajador = new Trabajador(
                    rs.getInt("id_trabajador"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("telefono"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    rs.getBoolean("es_administrador"),
                    rs.getDouble("comision")
                );
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los trabajadores: " + e.getMessage());
        }
        return trabajadores;
    }
    
    public String toString() {
        return "Trabajador{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", apellidos='" + apellidos + '\'' +
               ", telefono=" + telefono +
               ", email='" + email + '\'' +
               ", esAdministrador=" + esAdministrador +
               ", comision=" + comision +
               '}';
    }
}
