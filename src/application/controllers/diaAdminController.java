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
	
    

	public void initialize() {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
	}
}
