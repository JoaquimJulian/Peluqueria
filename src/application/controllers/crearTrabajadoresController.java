package application.controllers;

import javafx.scene.control.TextField;
import application.Main;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
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
    private ImageView basura;
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
    private PasswordField contrasena;
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
        cerrar.setOnMouseClicked(event -> Platform.exit());
        ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadores.fxml"));
        ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
        basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));

        if (salir != null) {
            salir.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadores.fxml"));
        } else {
            System.out.println("Error: 'salir' ImageView es null");
        }

        // Solo cambia de vista si la creación del trabajador es exitosa
        crearTrabajador.setOnAction(event -> {
            if (crearTrabajador()) {
                mainApp.mostrarVista("trabajadores.fxml");
            }
        });
    }

    @FXML
    private boolean crearTrabajador() {
        Trabajador trabajador = new Trabajador();
        try {
            String nombreText = nombre.getText().trim();
            String apellidosText = apellidos.getText().trim();
            String telefonoText = telefono.getText().trim();
            String emailText = email.getText().trim();
            String contrasenaText = contrasena.getText().trim();
            String comisionText = comision.getText().trim();

            // Validaciones básicas
            if (nombreText.isEmpty() || apellidosText.isEmpty() || telefonoText.isEmpty() ||
                emailText.isEmpty() || contrasenaText.isEmpty() || comisionText.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios.");
                return false;
            }

            // Validar número de teléfono y comisión
            int telefonoInt;
            double comisionDouble;
            try {
                telefonoInt = Integer.parseInt(telefonoText);
                comisionDouble = Double.parseDouble(comisionText);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "El teléfono debe tener 9 dígitos.");
                return false;
            }

            boolean adminCheck = admin.isSelected();
            trabajador.crearTrabajador(nombreText, apellidosText, telefonoInt, emailText, contrasenaText, adminCheck, comisionDouble);

            return true; // Éxito
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al crear el trabajador. Verifique los datos ingresados.");
            return false; // Fallo
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}