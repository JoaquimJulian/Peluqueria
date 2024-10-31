package application.controllers;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Optional;

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
	private TextField barraBusqueda;
	@FXML
	private Button btnCrear;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnEliminar;
	
	// TABLA PRODUCTOS
	@FXML
	private TableView<Servicio> tablaServicios;
	@FXML
	private TableColumn<Servicio, String> columnaNombre;
	@FXML
	private TableColumn<Servicio, String> columnaDescripcion;
	@FXML
	private TableColumn<Servicio, Double> columnaPrecio;
	@FXML
	private TableColumn<Servicio, Integer> columnaDuracion;
	@FXML
	private TableColumn<Servicio, Boolean> columnaReserva = new TableColumn<>("Reserva");
	
	
	
    private Main mainApp; // Referencia a Main
    
 // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void initialize() throws SQLException {
    	Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que carguen todos los componentes, la applicacion pone el focus del usuario en el panel principal
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	btnCrear.setOnMouseClicked(event -> mainApp.mostrarVista("crearServicios.fxml"));
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml"));

    	
    	btnEditar.setDisable(true);
    	tablaServicios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //listener que detecta cuando se hace click en una fila de la tabla para asi activar el boton de editar
            btnEditar.setDisable(false);
            btnEditar.setOnAction(event -> abrirVistaEdicion());
        });
    	
    	btnEliminar.setOnMouseClicked(event -> {
			try {
				//Confirmar la eliminacion
				Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
				alerta.setTitle("Confirmación de Eliminación");
				alerta.setHeaderText(null);
				alerta.setContentText("¿Estás seguro de que deseas eliminar el servicio?");
				Optional<ButtonType> respuesta = alerta.showAndWait();
				
				if (respuesta.get() == ButtonType.OK) {
					eliminarServicio();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
    	columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionEstimada"));
        columnaReserva.setCellValueFactory(new PropertyValueFactory<>("requiereReserva"));
    
    	cargarServicios();
    }
    
    private void cargarServicios() throws SQLException {
        ObservableList<Servicio> servicios = Servicio.getServicios();
        tablaServicios.setItems(servicios);
    }
    
    @FXML
    private void abrirVistaEdicion() {
        // Obtener la fila seleccionada
        Servicio servicioSeleccionado = tablaServicios.getSelectionModel().getSelectedItem();
       
        mainApp.mostrarVista("editarServicios.fxml", servicioSeleccionado);  // Método para abrir la vista de edición en Main
        
    }
    
    private void eliminarServicio() throws SQLException {
        Servicio servicioSeleccionado = tablaServicios.getSelectionModel().getSelectedItem();
        
        Servicio.eliminarServicio(servicioSeleccionado.getId());
        
        cargarServicios();
    }
}

