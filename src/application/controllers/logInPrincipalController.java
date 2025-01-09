package application.controllers;

import java.sql.SQLException;

import application.Main;
import application.models.Trabajador;
import application.models.databaseConection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class logInPrincipalController {

    @FXML
    private Button btnAcceder;
    @FXML
    private ImageView cerrar;
    @FXML
    private PasswordField contraseña;
    @FXML
    private BorderPane panelPrincipal;
    @FXML
    private TextField usuario;

    private Main mainApp; // Referencia a Main

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() throws SQLException {
        // Cerrar la aplicación al hacer clic en "cerrar"
        cerrar.setOnMouseClicked(event -> Platform.exit());

        // Configurar usuario por defecto y no editable
        usuario.setText("Dreams");
        usuario.setEditable(false);

        // Listener para botón "Acceder"
        btnAcceder.setOnAction(event -> loguearUsuario());

        // Listener para tecla "Intro" en el formulario
        panelPrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loguearUsuario();
                event.consume(); // Evita la propagación adicional del evento
            }
        });
    }

    private void loguearUsuario() {
        String user = usuario.getText();
        String password = contraseña.getText();

        boolean loginExitoso = databaseConection.verificarContraseña(user, password);

        if (loginExitoso) {
            mainApp.mostrarVista("Agenda.fxml");
        } else {
            // Aquí puedes agregar lógica para mostrar un mensaje de error, si lo necesitas
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }
}
