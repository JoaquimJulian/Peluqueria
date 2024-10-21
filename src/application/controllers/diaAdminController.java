package application.controllers;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.image.ImageView;

public class diaAdminController {
	@FXML
	private ImageView cerrar;
	@FXML
	private Button usuarios;
	@FXML
	private Button servicios;
	@FXML
	private Button productos;
	
    private Main mainApp; // Referencia a Main
    
 // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

	public void initialize() {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	productos.setOnMouseClicked(event -> mainApp.mostrarVista("Productos.fxml"));
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("Usuarios.fxml"));
    	servicios.setOnMouseClicked(event -> mainApp.mostrarVista("Servicios.fxml"));
	}
}
