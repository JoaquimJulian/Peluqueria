package application.controllers;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Optional;

import application.Main;
import application.models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

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
	
	
	
    private Main mainApp; // Referencia a Main
    
 // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void initialize() throws SQLException {
    	Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que cargen todos los componentes, la applicacion pone el focus del usuario en el panel principal
    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
    	nombreSesion.setText(trabajadorLogueado.getNombre());
    	
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
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
    
    	cargarClientes();
    	cargarCliente();
    }
    
    private void cargarCliente() throws SQLException {
        ObservableList<Cliente> clientes = Cliente.getClientes();
        tablaClientes.setItems(clientes);
        
        tablaClientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Verifica si son 2 clics
                Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
                if (clienteSeleccionado != null) { // Asegúrate de que hay una fila seleccionada
                    mostrarInformacionCliente(clienteSeleccionado);
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
}

