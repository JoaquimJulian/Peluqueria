// editarTrabajadoresController.java

package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import application.Main;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class fichaTrabajadorController {

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
	private Text nombreSesion;

    // CAMPOS DE EDICIÓN DE TRABAJADOR
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidos;
    @FXML
    private TextField telefono;
    @FXML
    private TextField email;
    @FXML
    private TextField contrasena;
    @FXML
    private CheckBox admin;
    @FXML
    private TextField comision;
    @FXML
    private Button guardarCambios;
 

    private Main mainApp; // Referencia a Main
    private Trabajador trabajadorActual; // Referencia al trabajador que se va a editar

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        cargarDatosTrabajador();
    }

    @FXML
    public void initialize() {
    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
    	nombreSesion.setText(trabajadorLogueado.getNombre());
    	
        cerrar.setOnMouseClicked(event -> Platform.exit());
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml"));
        calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        if (!trabajadorLogueado.isEsAdministrador()) {
    		Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
    		ajustes.setImage(imagenCliente);
    		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
    		admin.setDisable(true);
    		comision.setDisable(true);
    	}else {
    		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
    	}
        
        guardarCambios.setOnAction(event -> {
            editarTrabajador();
            mainApp.mostrarVista("diaAdmin.fxml");
        });
        
    }

    // Método para cargar los datos del trabajador en los campos
    @FXML
    public void cargarDatosTrabajador() {
    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
    	nombreSesion.setText(trabajadorLogueado.getNombre()); // Obtener el trabajador a editar

        // Cargar los datos en los campos de edición
        nombre.setText(trabajadorLogueado.getNombre());
        apellidos.setText(trabajadorLogueado.getApellidos());
        telefono.setText(String.valueOf(trabajadorLogueado.getTelefono()));
        email.setText(trabajadorLogueado.getEmail());
        contrasena.setText(trabajadorLogueado.getContrasena());
        admin.setSelected(trabajadorLogueado.isEsAdministrador());
        comision.setText(String.valueOf(trabajadorLogueado.getComision()));
    }

    // Método para guardar los cambios del trabajador
    public void editarTrabajador() {
    	Trabajador trabajador = new Trabajador();
    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
    	
        // Obtener los valores ingresados en los campos
        String nuevoNombre = nombre.getText();
        String nuevosApellidos = apellidos.getText();
        int nuevoTelefono = Integer.parseInt(telefono.getText());
        String nuevoEmail = email.getText();
        String nuevaContrasena = contrasena.getText();
        boolean esAdmin = admin.isSelected();
        double nuevaComision = Double.parseDouble(comision.getText());

        // Actualizar los datos en el objeto trabajador actual
        trabajadorLogueado.setNombre(nuevoNombre);
        trabajadorLogueado.setApellidos(nuevosApellidos);
        trabajadorLogueado.setTelefono(nuevoTelefono);
        trabajadorLogueado.setEmail(nuevoEmail);
        trabajadorLogueado.setContrasena(nuevaContrasena);
        trabajadorLogueado.setEsAdministrador(esAdmin);
        trabajadorLogueado.setComision(nuevaComision);

        // Llamar al método de edición en el modelo
        trabajador.actualizarTrabajador(trabajadorLogueado);
    }
}
