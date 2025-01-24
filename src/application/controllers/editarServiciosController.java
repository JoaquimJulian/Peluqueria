package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import application.Main;
import application.models.Servicio;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class editarServiciosController {
	
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
    private Button editarServicio;
    
    private Main mainApp; // Referencia a Main
    
    // Este método se llamará desde Main para establecer la referencia
       public void setMainApp(Main mainApp) {
           this.mainApp = mainApp;
           cargarDatosServicio();
       }
   
       public void initialize() {
    	   	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
	    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
	    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
	    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
	    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("servicios.fxml"));
	    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));


    	   	editarServicio.setOnAction(event ->  {
    	   		try {
	    		    // Intentamos convertir el texto a double
	    		    Double precio = Double.parseDouble(precioServicio.getText());
	    		    editarServicio();
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
       }
       
       // Método para cargar los datos del servicio en los campos
       @FXML
       public void cargarDatosServicio() {
    	Servicio servicio = (Servicio) mainApp.getDatosCompartidos(); /*recojo el valor de la variable datosCompartidos, la cual contiene el contenido
     	que me ha enviado el otro controlador, en este caso los datos de la fila a editar. Realizo un casting para convertir los datos compartidos en un objeto de tipo 'Servicio'*/
    	
    	
    	duracionServicio.getItems().setAll("10min", "20min", "30min", "40min", "50min", "1h", "1:30h", "2h", "2:30", "3h", "3:30h", "4h");
    	
        nombreServicio.setText(servicio.getNombre());
        descripcionServicio.setText(servicio.getDescripcion());
        precioServicio.setText(String.valueOf(servicio.getPrecio()));
        duracionServicio.getSelectionModel().select(servicio.getDuracionEstimada());
        requiereReserva.setSelected(servicio.isRequiereReserva());
       	}
       
	    public void editarServicio() {
	    	Servicio servicio = (Servicio) mainApp.getDatosCompartidos(); /* recojo el valor de la variable datosCompartidos, que son los datos de la fila que quiero editar, en este caso.
	    	Convierto los datos a un objeto de tipo Servicio*/
	    	
			String nombre = nombreServicio.getText();
	        Double precio = Double.parseDouble(precioServicio.getText()); 
	        String duracion = duracionServicio.getValue(); 
			String descripcion = descripcionServicio.getText();
			boolean requiereReservaChecked = requiereReserva.isSelected();
			
			
	        Servicio.editarServicio(servicio.getId(), nombre, precio, duracion, descripcion, requiereReservaChecked);
	    }
}
