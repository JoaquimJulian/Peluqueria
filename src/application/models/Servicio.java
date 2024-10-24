package application.models;

public class Servicio {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionEstimada;
    private boolean requiereReserva;

    // Constructor
    public Servicio(int id, String nombre, String descripcion, double precio, int duracionEstimada, boolean requiereReserva) {
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

    public int getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(int duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public boolean isRequiereReserva() {
        return requiereReserva;
    }

    public void setRequiereReserva(boolean requiereReserva) {
        this.requiereReserva = requiereReserva;
    }
}
