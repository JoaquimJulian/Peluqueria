package application.controllers;

import javafx.scene.control.TextField;
import application.Main;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;

public class crearTrabajadoresController {
    
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

    // CAMPOS DE TRABAJADOR
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
    private Button crearTrabajador;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void initialize() {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadores.fxml"));
    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
        

        if (salir != null) {
            salir.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadores.fxml"));
        } else {
            System.out.println("Error: 'salir' ImageView es null");
        }

        crearTrabajador.setOnAction(event -> {
            crearTrabajador();
            mainApp.mostrarVista("trabajadores.fxml");
        });
    }

    @FXML
    private void crearTrabajador() {
    	Trabajador trabajador = new Trabajador();
        try {
            String nombreText = nombre.getText();
            String apellidosText = apellidos.getText();
            int telefonoInt = Integer.parseInt(telefono.getText());
            String emailText = email.getText();
            String contrasenaText = contrasena.getText();
            boolean adminCheck = admin.isSelected();
            Double comisionDouble = Double.parseDouble(comision.getText());

            trabajador.crearTrabajador(nombreText, apellidosText, telefonoInt, emailText, contrasenaText, adminCheck, comisionDouble);
        } catch (Exception e) {
            e.printStackTrace();
            // Mostrar una alerta si algo falla
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Error al crear el trabajador. Verifique los datos ingresados.");
            alerta.showAndWait();
        }
    }
}
