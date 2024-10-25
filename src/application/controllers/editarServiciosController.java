package application.controllers;

import javafx.scene.control.TextField;

import application.Main;
import application.models.Servicio;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class editarServiciosController {
	
	@FXML
    private TextField nombreServicio;
    @FXML
    private ComboBox<Double> precioServicio;
    @FXML
    private ComboBox<Integer> duracionServicio;
    @FXML
    private TextField descripcionServicio;
    @FXML
    private CheckBox requiereReserva;
    
    private Main mainApp; // Referencia a Main
    
    // Este método se llamará desde Main para establecer la referencia
       public void setMainApp(Main mainApp) {
           this.mainApp = mainApp;
           cargarDatosServicio();
       }
    
       @FXML
       
	// Método para cargar los datos del servicio en los campos
    public void cargarDatosServicio() {
    	Servicio servicio = (Servicio) mainApp.getDatosCompartidos(); /*recojo el valor de la variable datosCompartidos, la cual contiene el contenido
     	que me ha enviado el otro controlador. Realizo un casting para convertir los datos compartidos en un objeto de tipo 'Servicio'*/
    	
    	// Valores posibles por defecto de los combobox
    	precioServicio.getItems().setAll(5.0, 10.0, 15.0, 20.0);
    	duracionServicio.getItems().setAll(0,1,2,3);
    	
        nombreServicio.setText(servicio.getNombre());
        descripcionServicio.setText(servicio.getDescripcion());
        precioServicio.getSelectionModel().select(servicio.getPrecio());
        duracionServicio.getSelectionModel().select(servicio.getDuracionEstimada());
        requiereReserva.setSelected(servicio.isRequiereReserva());
    }
}
