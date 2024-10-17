package application.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import application.models.databaseConection;
import application.Main; // Importa la clase Main

public class LogInController {
    @FXML
    private ImageView cerrar;
    @FXML
    private PasswordField contraseña;
    @FXML
    private Button acceder;
    @FXML
    private BorderPane panelPrincipal;

    private Main mainApp; // Referencia a Main

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
    	Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que cargen todos los componentes, la applicacion pone el focus del usuario en el panel principal
    	eliminarEspaciosContraseña(); //llama al metodo que no permite poner espacios en la contraseña
        cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        acceder.setOnAction(event -> loguearUsuario());
        
    }

    private void loguearUsuario() {
        String pass = contraseña.getText();

        if (pass.isEmpty()) {
            mostrarAlerta("Campo vacío", "Por favor, introduce la contraseña.");
            return;
        }

        boolean loginExitoso = databaseConection.verificarContraseña(pass);

        if (loginExitoso) {
            
            mainApp.mostrarVista("diaAdmin.fxml"); // Cambia a la nueva vista a través de Main
        } else {
            mostrarAlerta("Error", "Contraseña incorrecta.");
        }
    }

    private void eliminarEspaciosContraseña() {
    	// Listener para eliminar espacios en el texto del PasswordField
        contraseña.textProperty().addListener((observable, oldValue, newValue) -> {
            // Si el nuevo texto contiene espacios, reemplazarlos
            if (newValue.contains(" ")) {
                // Eliminar espacios del texto actual
                contraseña.setText(newValue.replace(" ", ""));
            }
        });
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
