package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

import application.Main;
import application.models.Producto;
import application.models.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
	@FXML
	private Button btnCrear;
	
	// TABLA PRODUCTOS
	@FXML
	private TableView tablaServicios;
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
    
    public void initialize() throws SQLException {
    	Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que cargen todos los componentes, la applicacion pone el focus del usuario en el panel principal
    	btnCrear.setOnMouseClicked(event -> mainApp.mostrarVista("crearServicios.fxml"));
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); }); //cerrar aplicacion cuando pulsar boton cerrar
    	
    	columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion_estimada"));
        columnaReserva.setCellValueFactory(new PropertyValueFactory<>("requiere_reserva"));
    	
    	cargarServicios();
    }
    
    private void cargarServicios() throws SQLException {
        ObservableList<Servicio> servicios = ServiciosModel.getServicios();
        tablaServicios.setItems(servicios);
    }
    
}

