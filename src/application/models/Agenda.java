package application.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class Agenda {
	
	private int id_reserva;
	private Date fecha;
	private Time hora;
	private String descripcion;
	private int id_trabajador;
	
	public Agenda(int id_reserva, Date fecha, Time hora, String descripcion, int id_trabajador) {
		this.id_reserva = id_reserva;
		this.fecha = fecha;
		this.hora = hora;
		this.descripcion = descripcion;
		this.id_trabajador = id_trabajador;
	}
	
	
	public int getId_reserva() {
		return id_reserva;
	}
	public void setId_reserva(int id_reserva) {
		this.id_reserva = id_reserva;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getId_trabajador() {
		return id_trabajador;
	}
	public void setId_trabajador(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}
	
	public static void crearReserva(Date fecha, Time hora, String descripcion, String id_reserva, int id_trabajador) throws SQLException {
		String sql = "INSERT INTO agenda (fecha, hora, descripcion, id_reserva, id_trabajador) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection connection = databaseConection.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setDate(1, fecha);
			stmt.setTime(2, hora);
			stmt.setString(3, descripcion);
			stmt.setString(4, id_reserva);
			stmt.setInt(5, id_trabajador);
			
			
			stmt.executeUpdate();
		}catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la reserva: " + e.getMessage());
        }
		
	}
	
	
}
	
	
