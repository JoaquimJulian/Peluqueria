package application.models;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precioVenta;
    private double precioCosto;
    private int stock;

    public Producto(int idProducto, String nombre, String descripcion, double precioVenta, double precioCosto, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.precioCosto = precioCosto;
        this.stock = stock;
    }

    // Getters y setters
    public int getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecioVenta() { return precioVenta; }
    public double getPrecioCosto() { return precioCosto; }
    public int getStock() { return stock; }
}
