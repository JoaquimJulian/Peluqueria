package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import application.Main;
import application.models.Cliente;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class editarClientesController {
	
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
		private Text nombreSesion;
	
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
	    private Button editarCliente;
    
    private Main mainApp; // Referencia a Main
    
    // Este método se llamará desde Main para establecer la referencia
       public void setMainApp(Main mainApp) {
           this.mainApp = mainApp;
           cargarDatosCliente();
       }
   
       public void initialize() {
    	   	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
        	nombreSesion.setText(trabajadorLogueado.getNombre());
    	   	
			cerrar.setOnMouseClicked(event -> { Platform.exit(); });
			salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
			ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
			usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
			calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
			salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
			if (!trabajadorLogueado.isEsAdministrador()) {
				Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
				ajustes.setImage(imagenCliente);
				ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
			}else {
				ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
			}
			
			editarCliente.setOnAction(event ->  {
				editarCliente();
			 	mainApp.mostrarVista("clientes.fxml");
			});
        	
        	
       }
       
       // Método para cargar los datos del cliente en los campos
       @FXML
       public void cargarDatosCliente() {
    	Cliente cliente = (Cliente) mainApp.getDatosCompartidos(); /*recojo el valor de la variable datosCompartidos, la cual contiene el contenido
     	que me ha enviado el otro controlador, en este caso los datos de la fila a editar. Realizo un casting para convertir los datos compartidos en un objeto de tipo 'Servicio'*/
    	
    	
        nombreCliente.setText(cliente.getNombre());
        apellidosCliente.setText(cliente.getApellido());
        telefonoCliente.setText(cliente.getTelefono() + "");
        emailCliente.setText(cliente.getEmail());
        lpd.setSelected(cliente.isLpd());
       	}
       
	    public void editarCliente() {
	    	Cliente cliente = (Cliente) mainApp.getDatosCompartidos(); /* recojo el valor de la variable datosCompartidos, que son los datos de la fila que quiero editar, en este caso.
	    	Convierto los datos a un objeto de tipo Servicio*/
	    	
			String nombre = nombreCliente.getText();
			String apellidos = apellidosCliente.getText();
			String telefonoString = telefonoCliente.getText();
			int telefono = Integer.parseInt(telefonoString);
			String email = emailCliente.getText();
			boolean lpdd = lpd.isSelected();
			
			
	        Cliente.editarCliente(cliente.getId(), nombre, apellidos, telefono, email, lpdd);
	    }
}
