package application.controllers;

import javafx.scene.control.TextField;
import application.Main;
import application.models.ServiciosModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class crearServiciosController {
	
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
	
	// INPUTS DE LOS DATOS DEL NUEVO SERVICIO
	@FXML
    private TextField nombreServicio;
    @FXML
    private ComboBox<String> precioServicio;
    @FXML
    private ComboBox<String> duracionServicio;
    @FXML
    private TextField descripcionServicio;
    @FXML
    private CheckBox requiereReserva;
    @FXML
    private Button crearServicio;

    
	private Main mainApp; // Referencia a Main
    
	 	// Este método se llamará desde Main para establecer la referencia
	    public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;
	    }
	    
	    public void initialize() {
	        crearServicio.setOnMouseClicked(event -> crearServicio());
	        
	        precioServicio.setEditable(true);
	        duracionServicio.setEditable(true);
	    }
	    
	    private void crearServicio() {
	    	String nombre = nombreServicio.getText();
	        String precio = precioServicio.getValue(); 
	        String duracion = duracionServicio.getValue(); 
	        String descripcion = descripcionServicio.getText();
	        boolean requiereReservaChecked = requiereReserva.isSelected();
	        
	        ServiciosModel.crearServicio(nombre, precio, duracion, descripcion, requiereReservaChecked);
	    }
	    
}
