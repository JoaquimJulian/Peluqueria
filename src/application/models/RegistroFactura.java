package application.models;

import java.time.LocalDate;

public class RegistroFactura {

    private String cliente; // Cambiar para que coincida con la columna en FXML
    private String nombreServicio;
    private String nombreProducto;
    private double montoTotal;
    private String metodoPago;
    private LocalDate fecha;
    private String observacion;

    public RegistroFactura(String cliente, String nombreServicio, String nombreProducto, double montoTotal, String metodoPago, LocalDate fecha, String observacion) {
        this.cliente = cliente; // Este será el valor de la columna "id_cliente" en la consulta
        this.nombreServicio = nombreServicio;
        this.nombreProducto = nombreProducto;
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    // Getters y Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}