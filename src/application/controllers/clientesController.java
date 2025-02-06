package application.controllers;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Optional;

import application.Main;
import application.models.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class clientesController {
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
	@FXML
	private ImageView basura;
	@FXML
    private ImageView ficha;
	
	// CONTROLES GENERICOS PARA CRUD
	@FXML
	private TextField barraBusqueda;
	@FXML
	private Button btnCrear;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Text nombreSesion;
	
	// TABLA PRODUCTOS
	@FXML
	private TableView<Cliente> tablaClientes;
	@FXML
	private TableColumn<Cliente, String> columnaNombre;
	@FXML
	private TableColumn<Cliente, String> columnaApellidos;
	@FXML
	private TableColumn<Cliente, Integer> columnaTelefono;
	@FXML
	private TableColumn<Cliente, String> columnaEmail;
	@FXML
	private TableColumn<Cliente, Boolean> columnaLpd;
	
	@FXML
	private AnchorPane popupCobro;
	
    private Main mainApp; // Referencia a Main
    
 // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        cobroRealizado();
        initialize();
    }
    
    public void initialize() throws SQLException {
    	if (mainApp != null) {
	    	Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que cargen todos los componentes, la applicacion pone el focus del usuario en el panel principal
	    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
	    	nombreSesion.setText(trabajadorLogueado.getNombre());
	    	
	    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
	    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
	    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
	    	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
	    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
	    	basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));

	    	if (!trabajadorLogueado.isEsAdministrador()) {
	    		Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
	    		ajustes.setImage(imagenCliente);
	    		salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml"));
	    	}else {
	    		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
	    		salir.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
	    	}
	    	
	    	btnCrear.setOnMouseClicked(event -> mainApp.mostrarVista("crearClientes.fxml"));
	    	btnEditar.setDisable(true);
	    	tablaClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //listener que detecta cuando se hace click en una fila de la tabla para asi activar el boton de editar
	            btnEditar.setDisable(false);
	            btnEditar.setOnAction(event -> abrirVistaEdicion());
	        });
	    	
	    	btnEliminar.setOnMouseClicked(event -> {
				try {
					//Confirmar la eliminacion
					Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
					alerta.setTitle("Confirmación de Eliminación");
					alerta.setHeaderText(null);
					alerta.setContentText("¿Estás seguro de que deseas eliminar el cliente?");
					Optional<ButtonType> respuesta = alerta.showAndWait();
					
					if (respuesta.get() == ButtonType.OK) {
						eliminarCliente();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	    	
	    	columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellido"));
	        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
	        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
	        columnaLpd.setCellValueFactory(new PropertyValueFactory<>("lpd"));
	        columnaLpd.setCellFactory(columna -> new TableCell<Cliente, Boolean>() {
	            @Override
	            protected void updateItem(Boolean item, boolean empty) {
	                super.updateItem(item, empty);
	                if (item == null || empty) {
	                    setText(null);
	                } else {
	                    setText(item ? "Sí" : "No");  // Muestra "Sí" si lpd es true, "No" si lpd es false
	                }
	            }
	        });
	        
	        
	    
	    	cargarClientes();
	    	cargarCliente();
	    	
	    	
	    	
	    	if (cobroRealizado()) {
	    		popupCobro.setVisible(true);
	    		
	    		PauseTransition delay = new PauseTransition(Duration.seconds(3));
	    		delay.setOnFinished(event -> popupCobro.setVisible(false));
	    		delay.play();
	    	}
    	}
    }
    
    
    private void cargarCliente() throws SQLException {
        ObservableList<Cliente> clientes = Cliente.getClientes();
        tablaClientes.setItems(clientes);
        ObservableList<Cliente> filtroBusqueda = FXCollections.observableArrayList(clientes);
        tablaClientes.setItems(filtroBusqueda);
        
        tablaClientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Verifica si son 2 clics
                Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
                if (clienteSeleccionado != null) { // Asegúrate de que hay una fila seleccionada
                    mostrarInformacionCliente(clienteSeleccionado);
                }
            }
        });

        barraBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filtroBusqueda.clear();
            for (Cliente cliente : clientes) {
            	if (cliente.getNombre().toLowerCase().contains(newValue.toLowerCase()) ||
            		    cliente.getApellido().toLowerCase().contains(newValue.toLowerCase()) ||
            		    cliente.getEmail().toLowerCase().contains(newValue.toLowerCase()) || // Corrección aquí
            		    String.valueOf(cliente.getTelefono()).contains(newValue)) {
            		    filtroBusqueda.add(cliente);
            		}

            }
        });

        
    }
    
    
    @FXML
    private void mostrarInformacionCliente(Cliente cliente) {
        // Cambiar a mostrar la vista fichaCliente
        mainApp.mostrarVista("fichaCliente.fxml", cliente); // Pasa la vista y el cliente seleccionado
    }

    
    
    private void cargarClientes() throws SQLException {
        ObservableList<Cliente> clientes = Cliente.getClientes();
        tablaClientes.setItems(clientes);
        
        tablaClientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Verifica si son 2 clics
                Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
                if (clienteSeleccionado != null) { // Asegúrate de que hay una fila seleccionada
                    abrirFichaCliente(clienteSeleccionado);
                }
            }
        });
    }

    private void abrirFichaCliente(Cliente cliente) {
    	
        mainApp.mostrarVista("fichaCliente.fxml", cliente);  // Método para abrir la vista de edición en Main
    }

    
    

    
    @FXML
    private void abrirVistaEdicion() {
        // Obtener la fila seleccionada
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
       
        mainApp.mostrarVista("editarClientes.fxml", clienteSeleccionado);  // Método para abrir la vista de edición en Main
        
    }
    
    private void eliminarCliente() throws SQLException {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        
        Cliente.eliminarCliente(clienteSeleccionado.getId());
        
        cargarClientes();
        cargarCliente();
    }
    
    private boolean cobroRealizado() {
    	if (mainApp.getDatosCompartidos() != null) {
    		boolean cobro = (boolean) mainApp.getDatosCompartidos();
        	return cobro;
    	}else {
    		return false;
    	}
    }
}

