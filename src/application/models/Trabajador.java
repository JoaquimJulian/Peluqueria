package application.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Trabajador {
	
	private String nombre;
	private String apellidos;
    private int telefono;
    private String email;
    private String foto;
    private String contrasena;
    private Boolean admin;
    private Double comision;
    private int id;
	
    public Trabajador(int id, String nombre, String apellidos, int telefono, String email, String foto, String contrasena, Boolean admin,
			Double comision) {
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.contrasena = contrasena;
		this.admin = admin;
		this.comision = comision;
		this.id = id;
	}
	
    
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
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	
	public static ObservableList<Trabajador> getTrabajadores() throws SQLException {
        ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
        Connection connection = databaseConection.getConnection();
        String sql = "SELECT * FROM trabajadores";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        try {
        	
        	ResultSet rs = stmt.executeQuery(sql);
        	
        	while (rs.next()) {
                trabajadores.add(new Trabajador(
                    rs.getInt("id_trabajador"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("telefono"),
                    rs.getString("email"),
                    rs.getString("foto_perfil"),
                    rs.getString("contrasena"),
                    rs.getBoolean("es_administrador"),
                    rs.getDouble("comision")
                ));
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        	
        return trabajadores;
    }
    
}