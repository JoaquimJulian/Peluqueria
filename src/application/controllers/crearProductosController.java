package application.controllers;

import java.sql.SQLException;

import application.Main;
import application.models.Producto;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    
    private boolean existeCodigo = false;
    
    private Main mainApp; // Referencia a Main
    
 	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    
    public void initialize() throws SQLException {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("productos.fxml"));
    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
		

	   	crearProducto.setOnMouseClicked(event -> {
	       	try {
				crearProducto();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
	   	
	   	
	   	}

    private void crearProducto() throws SQLException {
        String nombre = nombreProducto.getText();
        String descripcion = descripcionProducto.getText();
        double precio_ventaValue = Double.parseDouble(precio_venta.getText());
        double precioCostoValue = Double.parseDouble(precio_costo.getText());
        int stock = Integer.parseInt(stockProducto.getText());
        String codigoBarrasTexto = codigo_barras.getText();
        codigoBarrasTexto = codigoBarrasTexto.replaceAll("^\\^", "");
        Long codigoBarras = Long.parseLong(codigoBarrasTexto);
        
        
        ObservableList<String> codigos = Producto.codigos();
        for (String producto : codigos) {
        	if(codigoBarrasTexto.equals(producto)) {
        		existeCodigo = true;
        	}
        }
        
        if (!existeCodigo) {
            Producto.crearproducto(nombre, descripcion, precio_ventaValue, precioCostoValue, stock, codigoBarras);
            mainApp.mostrarVista("productos.fxml");
            nombreProducto.clear();
            descripcionProducto.clear();
            precio_venta .clear();
            precio_costo.clear();
            stockProducto.clear();
        } else {
        	Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al crear el producto");
            alert.setHeaderText(null);
            alert.setContentText("Ya existe un producto con este codigo de barras");
            alert.showAndWait();
            
            nombreProducto.clear();
            descripcionProducto.clear();
            precio_venta .clear();
            precio_costo.clear();
            stockProducto.clear();
        }
        
      
    }

}
