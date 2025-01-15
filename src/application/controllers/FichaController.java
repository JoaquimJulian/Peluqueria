package application.controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import application.Main;
import application.models.Cliente;
import application.models.Serviciovendido;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

public class FichaController {
	
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
    private ImageView logIn;
    @FXML
    private ImageView salir;
    @FXML
    private ImageView ficha;

	@FXML
    private TableView<Serviciovendido> tableViewServicios;

    @FXML
    private TableColumn<Serviciovendido, String> columnFecha;

    @FXML
    private TableColumn<Serviciovendido, String> columnProductos;

    @FXML
    private TableColumn<Serviciovendido, String> columnServicios;

    @FXML
    private TableColumn<Serviciovendido, Double> columnPrecio;
    @FXML
    private Button btnNuevoServicio;

private Main mainApp; // Referencia a Main
    
    
    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        cargarDatos();
    }
  

    @FXML
    public void initialize() {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	logIn.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
    	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("metodopago.fxml"));
    	
        // Configurar las columnas
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnProductos.setCellValueFactory(new PropertyValueFactory<>("producto"));
        columnServicios.setCellValueFactory(new PropertyValueFactory<>("servicio"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
        
        btnNuevoServicio.setOnMouseClicked(event -> {
            // Obtén el nombre del cliente
        	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
            String clienteNombre = null;
			try {
				clienteNombre = Cliente.nombreCliente(cliente.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            
            // Llama a la función para mostrar la vista y pasar el nombre del cliente
            mainApp.mostrarVista("metodopago.fxml", cliente);
        });

    }

    
    public void cargarDatos() throws SQLException {
        
        Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
        

        // Obtener los datos desde el modelo (Método modificado en Cliente para obtener los datos)
        List<String[]> datos = Cliente.cargarDatos(cliente.getId());

        // Crear una lista observable de objetos Serviciovendido para cargarla en el TableView
        ObservableList<Serviciovendido> serviciosVendidos = FXCollections.observableArrayList();

        // Recorrer los datos y agregarlos a la lista observable
        for (String[] fila : datos) {
            String fecha = fila[0];
            String producto = fila[1];
            String servicio = fila[2];
            Double precio = Double.parseDouble(fila[3]);

            // Crear un objeto Serviciovendido y agregarlo a la lista
            serviciosVendidos.add(new Serviciovendido(fecha, producto, servicio, precio));
        }

        // Cargar los datos en el TableView
        tableViewServicios.setItems(serviciosVendidos);
    }
    
    



   

   
}
