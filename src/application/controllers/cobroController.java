package application.controllers;

import java.sql.SQLException;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import application.models.Cliente;  // Asegúrate de que Cliente esté correctamente importado
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
	
	 
	private Main mainApp;  // Referencia a la aplicación principal

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        cargarCliente();
    }
    
    @FXML
    public void initialize() {
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
    	

    	
    }

    // Método para cargar datos del cliente
    public void cargarCliente() throws SQLException {
   	 	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
   	 	ClienteNombreField.setText(cliente.getNombre());
    }

  
}
