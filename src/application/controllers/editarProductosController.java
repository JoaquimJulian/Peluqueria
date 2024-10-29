package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import application.Main;
import application.models.Producto;
import application.models.ProductosModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class editarProductosController {
    
    // BOTONES HEADER
    @FXML
    private ImageView salir;
    @FXML
    private ImageView calendario;
    @FXML
    private ImageView ajustes;
    @FXML
    private ImageView cobrar;
    @FXML
    private ImageView usuarios;
    @FXML
    private ImageView cerrar;
    
    @FXML
    private TextField nombreProducto;
    @FXML
    private TextField PrecioVenta; // Cambiado a TextField
    @FXML
    private TextField PrecioCosto;  // Cambié a Double para que coincida con tu modelo
    @FXML
    private TextField descripcionProducto;
    @FXML
    private TextField cantidad_en_stock;
    @FXML
    private Button editarproducto;
    
    private Main mainApp; // Referencia a Main
    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        cargarDatosProducto();
    }
    
    public void initialize() {
        cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        editarproducto.setOnAction(event -> {
            editarProducto();
            mainApp.mostrarVista("Productos.fxml");
        });
    }
    
    // Método para cargar los datos del Producto en los campos
    @FXML
    public void cargarDatosProducto() {
        Producto producto = (Producto) mainApp.getDatosCompartidos(); // Obtener el producto a editar
        
        // Cargar los datos en los campos
        nombreProducto.setText(producto.getNombre());
        descripcionProducto.setText(producto.getDescripcion());
        PrecioVenta.setText(String.valueOf(producto.getPrecioVenta())); // Cambiado a setText()
        PrecioCosto.setText(String.valueOf(producto.getPrecioCosto())); // Cambiado a setText()
        cantidad_en_stock.setText(String.valueOf(producto.getCantidad_en_stock())); // Cargar la cantidad en stock
    }
    
    public void editarProducto() {
        Producto producto = (Producto) mainApp.getDatosCompartidos(); // Obtener el producto a editar
        
        // Obtener los valores de los campos
        String nombre = nombreProducto.getText();
        
        // Convertir los valores de texto a Double
        double precioVenta = Double.parseDouble(PrecioVenta.getText());
        double precioCosto = Double.parseDouble(PrecioCosto.getText());
        
        String descripcion = descripcionProducto.getText();
        int cantidad = Integer.parseInt(cantidad_en_stock.getText()); // Convertir a int

        // Llamar al método de edición en el modelo
        ProductosModel.editarproducto(producto.getId(), nombre, descripcion, precioVenta, precioCosto, cantidad);
    }
}
