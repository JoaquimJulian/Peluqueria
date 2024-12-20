package application.controllers;

import application.Main;
import application.models.Producto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class crearProductosController {

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
    private ImageView ficha;
	
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
    private TextField codigo_barras;
    @FXML
    private Button crearProducto;
    
    private Main mainApp; // Referencia a Main
    
 	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    
    public void initialize() {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("productos.fxml"));
    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
		

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
        String codigoBarrasTexto = codigo_barras.getText();
        codigoBarrasTexto = codigoBarrasTexto.replaceAll("^\\^", "");
        Long codigoBarras = Long.parseLong(codigoBarrasTexto);

        // Llama al método en ProductosModel para crear el producto
        Producto.crearproducto(nombre, descripcion, precio_ventaValue, precioCostoValue, stock, codigoBarras);
        
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
