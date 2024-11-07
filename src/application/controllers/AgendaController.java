package application.controllers;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.image.ImageView;

public class AgendaController {
	@FXML
	private ImageView cerrar;
	@FXML
	private ImageView logIn;

    private Main mainApp; // Referencia a Main
    
 // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

	public void initialize() {
		
		
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	logIn.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
	}
}
