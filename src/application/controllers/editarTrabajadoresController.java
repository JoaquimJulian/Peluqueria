// editarTrabajadoresController.java

package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import application.Main;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class editarTrabajadoresController {

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
        if (cerrar != null) {
            cerrar.setOnMouseClicked(event -> Platform.exit());
        }
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadores.fxml"));
   

        guardarCambios.setOnAction(event -> {
            editarTrabajador();
            mainApp.mostrarVista("trabajadores.fxml");
        });
    }

    // Método para cargar los datos del trabajador en los campos
    @FXML
    public void cargarDatosTrabajador() {
        trabajadorActual = (Trabajador) mainApp.getDatosCompartidos(); // Obtener el trabajador a editar

        // Cargar los datos en los campos de edición
        nombre.setText(trabajadorActual.getNombre());
        apellidos.setText(trabajadorActual.getApellidos());
        telefono.setText(String.valueOf(trabajadorActual.getTelefono()));
        email.setText(trabajadorActual.getEmail());
        contrasena.setText(trabajadorActual.getContrasena());
        admin.setSelected(trabajadorActual.isEsAdministrador());
        comision.setText(String.valueOf(trabajadorActual.getComision()));
    }

    // Método para guardar los cambios del trabajador
    public void editarTrabajador() {
        // Obtener los valores ingresados en los campos
        String nuevoNombre = nombre.getText();
        String nuevosApellidos = apellidos.getText();
        int nuevoTelefono = Integer.parseInt(telefono.getText());
        String nuevoEmail = email.getText();
        String nuevaContrasena = contrasena.getText();
        boolean esAdmin = admin.isSelected();
        double nuevaComision = Double.parseDouble(comision.getText());

        // Actualizar los datos en el objeto trabajador actual
        trabajadorActual.setNombre(nuevoNombre);
        trabajadorActual.setApellidos(nuevosApellidos);
        trabajadorActual.setTelefono(nuevoTelefono);
        trabajadorActual.setEmail(nuevoEmail);
        trabajadorActual.setContrasena(nuevaContrasena);
        trabajadorActual.setEsAdministrador(esAdmin);
        trabajadorActual.setComision(nuevaComision);

        // Llamar al método de edición en el modelo
        Trabajador.actualizarTrabajador(trabajadorActual);
    }
}