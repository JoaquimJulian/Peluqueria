package application.models;

import java.sql.Date;
import java.sql.Time;

public class Agenda {
	
	private int id_reserva;
	private Date fecha;
	private Time hora;
	private String descripcion;
	
	public Agenda(int id_reserva, Date fecha, Time hora, String descripcion) {
		this.id_reserva = id_reserva;
		this.fecha = fecha;
		this.hora = hora;
		this.descripcion = descripcion;
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
	
	
}
