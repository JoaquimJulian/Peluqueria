package application.controllers;

import application.Main;
import application.models.Servicio;
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
	@FXML
    private ImageView ficha;
	
	// INPUTS DE LOS DATOS DEL NUEVO SERVICIO
	@FXML
    private TextField nombreServicio;
	@FXML
    private TextField precioServicio;
    @FXML
    private ComboBox<String> duracionServicio;
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
	    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
	    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
	    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
	    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("servicios.fxml"));
	    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));

	    	crearServicio.setOnMouseClicked(event -> {
	    	    // Verificar si el campo nombreServicio está vacío
	    	    if (nombreServicio.getText().trim().isEmpty()) {
	    	        Alert alert = new Alert(Alert.AlertType.WARNING);
	    	        alert.setTitle("Campo requerido");
	    	        alert.setHeaderText(null);
	    	        alert.setContentText("El nombre del servicio es obligatorio");
	    	        alert.showAndWait();
	    	        return; // Detener ejecución si el campo está vacío
	    	    }
	    	    
	    	    try {
	    	        // Intentamos convertir el texto a double
	    	        Double precio = Double.parseDouble(precioServicio.getText());
	    	        crearServicio();
	    	        mainApp.mostrarVista("servicios.fxml");
	    	    } catch (NumberFormatException e) {
	    	        Alert alert = new Alert(Alert.AlertType.WARNING);
	    	        alert.setTitle("Error al crear el servicio");
	    	        alert.setHeaderText(null);
	    	        alert.setContentText("Formato del campo 'precio' incorrecto");
	    	        alert.showAndWait();
	    	        precioServicio.setText(null);
	    	    }
	    	});

    	   	
	        
    	   	// Valores posibles por defecto de los combobox
	    	duracionServicio.getItems().setAll("10min", "20min", "30min", "40min", "50min", "1h", "1:30h", "2h", "2:30", "3h", "3:30h", "4h");
	    }
	    
	    private void crearServicio() {
	    	if (nombreServicio.getText() != null && precioServicio.getText() != null && duracionServicio.getValue() != null && descripcionServicio.getText() != null) {
	    		String nombre = nombreServicio.getText();
		        Double precio = Double.parseDouble(precioServicio.getText()); 
		        String duracion = duracionServicio.getValue(); 
		        String descripcion = descripcionServicio.getText();
		        boolean requiereReservaChecked = requiereReserva.isSelected();
		        
		        Servicio.crearServicio(nombre, precio, duracion, descripcion, requiereReservaChecked);
	    	}else { //si los campos estan vacios te sale un aviso de que los tienes que rellenar todos
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION); 
	            alert.setTitle("Error"); 
	            alert.setHeaderText(null); 
	            alert.setContentText("Rellene todos los campos, por favor"); 
	            alert.showAndWait(); 
	    	}
	    }
	    
	    
}
