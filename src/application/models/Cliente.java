package application.models;

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
}
