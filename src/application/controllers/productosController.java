package application.controllers;

import application.models.Producto;
import application.models.ProductosModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class productosController {

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> columnaNombre;
    @FXML
    private TableColumn<Producto, String> columnaDescripcion;
    @FXML
    private TableColumn<Producto, Double> columnaPrecioVenta;
    @FXML
    private TableColumn<Producto, Double> columnaCoste;
    @FXML
    private TableColumn<Producto, Integer> columnaStock;

    private ProductosModel productosModel = new ProductosModel();

    @FXML
    public void initialize() {
        // Configurar las columnas
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        columnaCoste.setCellValueFactory(new PropertyValueFactory<>("precioCosto"));
        columnaStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Cargar los datos
        cargarProductos();
    }

    private void cargarProductos() {
        ObservableList<Producto> productos = productosModel.getProductos();
        tablaProductos.setItems(productos);
    }
}
