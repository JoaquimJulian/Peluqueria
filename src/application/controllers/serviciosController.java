package application.controllers;

import javafx.scene.control.TextField;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class serviciosController {
	@FXML
	private BorderPane panelPrincipal;
	
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
	
	// CONTROLES GENERICOS PARA CRUD
	@FXML
	private Button btnGuardarCambios;
	@FXML
	private TextField barraBusqueda;
	
	// TABLA PRODUCTOS
	@FXML
	private TableView tablaProductos;
	@FXML
	private TableColumn columnaNombre;
	@FXML
	private TableColumn columnaDescripcion;
	@FXML
	private TableColumn columnaPrecio;
	@FXML
	private TableColumn columnaDuracion;
	@FXML
	private TableColumn columnaReserva;
	
	
	
    private Main mainApp; // Referencia a Main
    
 // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void initialize() {
    	Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que cargen todos los componentes, la applicacion pone el focus del usuario en el panel principal
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); }); //cerrar aplicacion cuando pulsar boton cerrar
    }
}

