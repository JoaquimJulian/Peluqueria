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
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.function.UnaryOperator;

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
    private TextField precio_venta;
    @FXML
    private TextField precio_costo;
    @FXML
    private TextField stockProducto;
    @FXML
    private TextField aviso_stock;
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
		
    	controlFormatoPrecio();
    	controlFormatoStock();
    	controlFormatoCodigo();
    	controlFormatoStock2();
    	
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
        int avisostock = Integer.parseInt(aviso_stock.getText());
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
            Producto.crearproducto(nombre, descripcion, precio_ventaValue, precioCostoValue, stock, codigoBarras, avisostock);
            mainApp.mostrarVista("productos.fxml");
            nombreProducto.clear();
            descripcionProducto.clear();
            precio_venta .clear();
            precio_costo.clear();
            stockProducto.clear();
            aviso_stock.clear();
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
            aviso_stock.clear();
        }
        
      
    }
    
    private void controlFormatoPrecio() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String newText = change.getControlNewText();

            // Permite solo números y un único punto decimal
            if (newText.matches("\\d*\\.?\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para cada TextField
        precio_venta.setTextFormatter(new TextFormatter<>(filtro));
        precio_costo.setTextFormatter(new TextFormatter<>(filtro));
    }

    private void controlFormatoStock() {
        UnaryOperator<TextFormatter.Change> filtroStock = change -> {
            String newText = change.getControlNewText();
            
            // Verifica si el texto es numérico (solo dígitos)
            if (newText.matches("\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para stockProducto
        stockProducto.setTextFormatter(new TextFormatter<>(filtroStock));
    }
    
    private void controlFormatoStock2() {
        UnaryOperator<TextFormatter.Change> filtroStock = change -> {
            String newText = change.getControlNewText();
            
            // Verifica si el texto es numérico (solo dígitos)
            if (newText.matches("\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para stockProducto
        aviso_stock.setTextFormatter(new TextFormatter<>(filtroStock));
    }
    
    private void controlFormatoCodigo() {
    	UnaryOperator<TextFormatter.Change> filtroCodigo = change -> {
            String newText = change.getControlNewText();
            
            // Verifica si el texto es numérico (solo dígitos)
            if (newText.matches("\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para stockProducto
        codigo_barras.setTextFormatter(new TextFormatter<>(filtroCodigo));
    }
}
