package application.controllers;

import javafx.scene.control.TextField;

import java.util.function.UnaryOperator;

import application.Main;
import application.models.Cliente;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
	@FXML
	private ImageView basura;
	@FXML
	private ImageView ficha;
	@FXML
	private Text nombreSesion;
	
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
    	   	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
        	nombreSesion.setText(trabajadorLogueado.getNombre());
	    	
    	   	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));

        	if (!trabajadorLogueado.isEsAdministrador()) {
				Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
				ajustes.setImage(imagenCliente);
				ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
			}else {
				ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
			}
        	
        	controlFormatoTelefono();
        	
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
		        
		        Cliente.crearCliente(nombre, apellidos, telefono, email, lpdd, "");
	    	}else { //si los campos estan vacios te sale un aviso de que los tienes que rellenar todos
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION); 
	            alert.setTitle("Error"); 
	            alert.setHeaderText(null); 
	            alert.setContentText("Rellene todos los campos, por favor"); 
	            alert.showAndWait(); 
	    	}
	    }
	    
	    private void controlFormatoTelefono() {
	    	int maxLength = 9;
        	UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();
                
                // Verifica si el texto es numérico y no excede la longitud máxima
                if (newText.matches("\\d*") && newText.length() <= maxLength) {
                    return change; // Aceptar el cambio
                }
                return null; // Rechazar el cambio
            };

            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
            telefonoCliente.setTextFormatter(textFormatter);
	    }
	    
	    
}
