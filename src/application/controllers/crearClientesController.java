package application.controllers;

import javafx.scene.control.TextField;
import application.Main;
import application.models.Cliente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class crearClientesController {
	
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
	
	// INPUTS DE LOS DATOS DEL NUEVO CLIENTE
	@FXML
    private TextField nombreCliente;
    @FXML
    private TextField apellidosCliente;
    @FXML
    private TextField telefonoCliente;
    @FXML
    private TextField emailCliente;
    @FXML
    private CheckBox lpd;
    @FXML
    private Button crearCliente;

    
	private Main mainApp; // Referencia a Main
    
	 	// Este método se llamará desde Main para establecer la referencia
	    public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;
	    }
	    
	    public void initialize() {
    	   	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    		salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));

    	   	crearCliente.setOnMouseClicked(event -> {
		       	crearCliente();
		       	mainApp.mostrarVista("clientes.fxml");
		    });
    	   	
	    }
	    
	    private void crearCliente() {
	    	if (nombreCliente.getText() != null && apellidosCliente.getText() != null && telefonoCliente.getText() != null && emailCliente.getText() != null) {
	    		String nombre = nombreCliente.getText();
		        String apellidos = apellidosCliente.getText(); 
		        String telefonoString = telefonoCliente.getText();
		        int telefono = Integer.parseInt(telefonoString);
		        String email = emailCliente.getText();
		        boolean lpdd = lpd.isSelected();
		        
		        Cliente.crearCliente(nombre, apellidos, telefono, email, lpdd);
	    	}else { //si los campos estan vacios te sale un aviso de que los tienes que rellenar todos
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION); 
	            alert.setTitle("Error"); 
	            alert.setHeaderText(null); 
	            alert.setContentText("Rellene todos los campos, por favor"); 
	            alert.showAndWait(); 
	    	}
	    }
	    
	    
}
