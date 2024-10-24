package application.controllers;

import javafx.scene.control.Button; 
import javafx.scene.control.TextField;

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
    @FXML
    private Button crearProducto;
    
 // Añade los TextFields
    @FXML
    private TextField nombreProducto;
    @FXML
    private TextField descripcionProducto;
    @FXML
    private TextField precio_venta;
    @FXML
    private TextField precio_costo;
    @FXML
    private TextField stockProducto;

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
        
     // Evento de creación
        crearProducto.setOnAction(event -> crearNuevoProducto());
        
    }

    private void cargarProductos() {
        ObservableList<Producto> productos = productosModel.getProductos();
        tablaProductos.setItems(productos);
    }
    
    private void crearNuevoProducto() {
        // Recoger los datos ingresados
        String nombre = nombreProducto.getText();
        String descripcion = descripcionProducto.getText();
        double precioVenta = Double.parseDouble(precio_venta.getText()); 
        double precioCosto = Double.parseDouble(precio_costo.getText()); 
        int stock = Integer.parseInt(stockProducto.getText());

        // Crear un nuevo objeto Producto
        Producto nuevoProducto = new Producto(nombre, descripcion, precioVenta, precioCosto, stock);

        // Guardar el nuevo producto en la base de datos usando ProductosModel
        productosModel.crearproductos(nuevoProducto);

        // Recargar la tabla para mostrar el nuevo producto
        cargarProductos();
    }
    
    
}
