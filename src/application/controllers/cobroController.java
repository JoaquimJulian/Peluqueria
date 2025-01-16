package application.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.application.Platform;
import application.models.Servicio;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import application.models.Cliente;  // Asegúrate de que Cliente esté correctamente importado
import application.models.Facturacion;
import application.models.Producto;
import application.models.Trabajador;

public class cobroController {
	
	// BOTONES HEADER
    @FXML
    private ImageView calendario;
    @FXML
    private ImageView ajustes;
    @FXML
    private ImageView cerrar;
    @FXML
    private ImageView cobrar;
    @FXML
    private ImageView usuarios;
    @FXML
    private ImageView salir;
    @FXML
    private ImageView ficha;
    @FXML
	private Text nombreSesion;

	// CobroController.java
	@FXML
	private TextField ClienteNombreField;  // Este es el TextField donde mostrarás el nombre del cliente
	@FXML 
	private CheckBox metodoEfectivo;
	@FXML 
	private CheckBox metodoTarjeta;
	@FXML 
	private CheckBox metodoBizum;
	@FXML 
	private ChoiceBox nombrePeluquero;
	@FXML 
	private Button CobrarTodo;
	@FXML 
	private Button anadirServicio;
	@FXML 
	private Button anadirProducto;
	
	// Tabla para agregar servicios 
	
	@FXML
	private Label textoSeleccionarServicios;
	@FXML
	private TextField buscadorServicio;
	@FXML
	private TableView<Servicio> serviciosCobrar;
	@FXML
	private TableColumn<Servicio, String> nombreServicioCobrar;
	@FXML
	private TableColumn<Servicio, String> observacionServicioCobrar;
	
	// Tabla de productos y servicios añadidos
	
	@FXML
	private TableView<Producto> productosAnadidosTabla;
	@FXML
	private TableColumn<Producto, String> productosAnadidosColumna;
	@FXML
	private TableView<Servicio> servicioAnadidosTabla;
	@FXML
	private TableColumn<Servicio, String> servicioAnadidosColumna;
	
	
	private Main mainApp;  // Referencia a la aplicación principal

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        cargarCliente();
        datosCliente();
        initialize();
    }
    
    @FXML
    public void initialize() {
    	if (mainApp != null) {	
    		Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
        	nombreSesion.setText(trabajadorLogueado.getNombre());
        	
        	if (!trabajadorLogueado.isEsAdministrador()) {
        		Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
        		ajustes.setImage(imagenCliente);
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	}else {
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
        	}
        	
        	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        	salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	
        	ObservableList<Trabajador> trabajadores = Trabajador.getTrabajadoresActivos();
        	    	
        	ObservableList<String> nombresTrabajadores = FXCollections.observableArrayList();

        	for (Trabajador trabajador : trabajadores) {
        		if (!trabajador.getNombre().equals("dreams")) {
            	    nombresTrabajadores.add(trabajador.getId() + " " + trabajador.getNombre());
        		}
        	    if (trabajador.getId() == trabajadorLogueado.getId()) {
        	    	nombrePeluquero.setValue(trabajador.getId() + " " + trabajador.getNombre());
        	    }
        	}

        	// Asignar la lista de nombres al ChoiceBox
        	nombrePeluquero.setItems(nombresTrabajadores);
        	
        	
        	anadirServicio.setOnMouseClicked(event -> {
				try {
					mostrarServicios();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
        	
    	}
    	
    }

    // Método para cargar datos del cliente
    public void cargarCliente() throws SQLException {
   	 	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
   	 	ClienteNombreField.setText(cliente.getNombre());
    }
    
    public Cliente datosCliente() {
   	 	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
    	return cliente;
    }
    
    
    public void mostrarServicios() throws SQLException {
    	textoSeleccionarServicios.setVisible(true);
    	buscadorServicio.setVisible(true);
    	serviciosCobrar.setVisible(true);
    	
    	nombreServicioCobrar.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        observacionServicioCobrar.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    	
        ObservableList<Servicio> servicios = Servicio.getServicios();

    	serviciosCobrar.setItems(servicios);
    	serviciosCobrar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    	
    }
    
    
    
   
  
}
