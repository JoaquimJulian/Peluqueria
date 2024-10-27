package application.controllers;

import javafx.scene.control.TextField;
import application.Main;
import application.models.ServiciosModel;
import javafx.application.Platform;
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
    private ComboBox<Double> precioServicio;
    @FXML
    private ComboBox<Integer> duracionServicio;
    @FXML
    private TextArea descripcionServicio;
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
    	   	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	   	crearServicio.setOnMouseClicked(event -> {
		       	crearServicio();
		       	mainApp.mostrarVista("servicios.fxml");
		    });
    	   	
	        
    	   	// Valores posibles por defecto de los combobox
	    	precioServicio.getItems().setAll(5.0, 10.0, 15.0, 20.0);
	    	duracionServicio.getItems().setAll(0,1,2,3,4,5,6,7);
	    }
	    
	    private void crearServicio() {
	    	if (nombreServicio.getText() != null && precioServicio.getValue() != null && duracionServicio.getValue() != null && descripcionServicio.getText() != null) {
	    		String nombre = nombreServicio.getText();
		        Double precio = precioServicio.getValue(); 
		        Integer duracion = duracionServicio.getValue(); 
		        String descripcion = descripcionServicio.getText();
		        boolean requiereReservaChecked = requiereReserva.isSelected();
		        
		        ServiciosModel.crearServicio(nombre, precio, duracion, descripcion, requiereReservaChecked);
	    	}else { //si los campos estan vacios te sale un aviso de que los tienes que rellenar todos
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION); 
	            alert.setTitle("Error"); 
	            alert.setHeaderText(null); 
	            alert.setContentText("Rellene todos los campos, por favor"); 
	            alert.showAndWait(); 
	    	}
	    }
	    
	    
}
