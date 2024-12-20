package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    private ComboBox<Double> precioServicio;
    @FXML
    private ComboBox<Integer> duracionServicio;
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
	    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
	    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("servicios.fxml"));
	    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));


    	   	editarServicio.setOnAction(event ->  {
    	   		editarServicio();
	         	mainApp.mostrarVista("servicios.fxml");
    	   	});
       }
       
       // Método para cargar los datos del servicio en los campos
       @FXML
       public void cargarDatosServicio() {
    	Servicio servicio = (Servicio) mainApp.getDatosCompartidos(); /*recojo el valor de la variable datosCompartidos, la cual contiene el contenido
     	que me ha enviado el otro controlador, en este caso los datos de la fila a editar. Realizo un casting para convertir los datos compartidos en un objeto de tipo 'Servicio'*/
    	
    	// Valores posibles por defecto de los combobox
    	precioServicio.getItems().setAll(5.0, 10.0, 15.0, 20.0);
    	duracionServicio.getItems().setAll(0,1,2,3,4,5,6,7);
    	
        nombreServicio.setText(servicio.getNombre());
        descripcionServicio.setText(servicio.getDescripcion());
        precioServicio.getSelectionModel().select(servicio.getPrecio());
        duracionServicio.getSelectionModel().select(servicio.getDuracionEstimada());
        requiereReserva.setSelected(servicio.isRequiereReserva());
       	}
       
	    public void editarServicio() {
	    	Servicio servicio = (Servicio) mainApp.getDatosCompartidos(); /* recojo el valor de la variable datosCompartidos, que son los datos de la fila que quiero editar, en este caso.
	    	Convierto los datos a un objeto de tipo Servicio*/
	    	
			String nombre = nombreServicio.getText();
			Double precio = precioServicio.getValue();
			Integer duracion = duracionServicio.getValue();
			String descripcion = descripcionServicio.getText();
			boolean requiereReservaChecked = requiereReserva.isSelected();
			
			
	        Servicio.editarServicio(servicio.getId(), nombre, precio, duracion, descripcion, requiereReservaChecked);
	    }
}
