package application.models;

import javafx.collections.ObservableList;

public class DatosCompartidos {
	private Producto producto;
    private ObservableList<Servicio> servicios;

    public DatosCompartidos(Producto producto, ObservableList<Servicio> servicios) {
        this.producto = producto;
        this.servicios = servicios;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ObservableList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ObservableList<Servicio> servicios) {
        this.servicios = servicios;
    }
}
