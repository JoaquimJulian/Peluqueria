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
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	btnCrear.setOnMouseClicked(event -> mainApp.mostrarVista("crearClientes.fxml"));
    	btnEditar.setDisable(true);
    	tablaClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //listener que detecta cuando se hace click en una fila de la tabla para asi activar el boton de editar
            btnEditar.setDisable(false);
            btnEditar.setOnAction(event -> abrirVistaEdicion());
        });
    	
    	btnEliminar.setOnMouseClicked(event -> {
			try {
				eliminarCliente();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
    	columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnaLpd.setCellValueFactory(new PropertyValueFactory<>("lpd"));
    
    	cargarClientes();
    }
    
    private void cargarClientes() throws SQLException {
        ObservableList<Cliente> clientes = ClientesModel.getClientes();
        tablaClientes.setItems(clientes);
    }
    
    @FXML
    private void abrirVistaEdicion() {
        // Obtener la fila seleccionada
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
       
        mainApp.mostrarVista("editarClientes.fxml", clienteSeleccionado);  // Método para abrir la vista de edición en Main
        
    }
    
    private void eliminarCliente() throws SQLException {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        
        ClientesModel.eliminarCliente(clienteSeleccionado.getId());
        
        cargarClientes();
    }
}

