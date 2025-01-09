package application.models;

public class Serviciovendido {
    private String fecha;
    private String producto;
    private String servicio;
    private Double precio;

    public Serviciovendido(String fecha, String producto, String servicio, Double precio) {
        this.fecha = fecha;
        this.producto = producto;
        this.servicio = servicio;
        this.precio = precio;
    }

    // Getters y setters
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
