package application.controllers;

import application.Main;
import application.models.Producto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class crearProductosController {

	@FXML
	private ImageView cerrar;
    @FXML
    private TextField nombreProducto;
    @FXML
    private TextField descripcionProducto;
    @FXML
    private TextField precio_venta ;
    @FXML
    private TextField precio_costo;
    @FXML
    private TextField stockProducto;
    @FXML
    private Button crearProducto;
    
    private Main mainApp; // Referencia a Main
    
 	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    
    public void initialize() {
	   	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
	   	crearProducto.setOnMouseClicked(event -> {
	       	crearProducto();
	       	mainApp.mostrarVista("productos.fxml");
	    });
	   	}

    private void crearProducto() {
        String nombre = nombreProducto.getText();
        String descripcion = descripcionProducto.getText();
        double precio_ventaValue = Double.parseDouble(precio_venta.getText());
        double precioCostoValue = Double.parseDouble(precio_costo.getText());
        int stock = Integer.parseInt(stockProducto.getText());

        // Llama al método en ProductosModel para crear el producto
        Producto.crearproducto(nombre, descripcion, precio_ventaValue, precioCostoValue, stock);
        
        // Opcional: limpiar los campos o mostrar un mensaje de éxito
        limpiarCampos();
    }

    private void limpiarCampos() {
        nombreProducto.clear();
        descripcionProducto.clear();
        precio_venta .clear();
        precio_costo.clear();
        stockProducto.clear();
    }
}
