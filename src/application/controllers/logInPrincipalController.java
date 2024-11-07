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
        cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        
        btnAcceder.setOnAction(event -> loguearUsuario());
    }
    
    private void loguearUsuario() {
    	String user = usuario.getText();
    	String password = contraseña.getText();
    	
    	boolean loginExitoso = databaseConection.verificarContraseña(user, password);
    	
    	if (loginExitoso) {
    		mainApp.mostrarVista("Agenda.fxml");
    	}
    }
    
}