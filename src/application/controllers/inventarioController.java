package application.controllers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import application.Main;
import application.models.Agenda;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class inventarioController {
	
	// BOTONES HEADER
    @FXML
    private ImageView ajustes;
    @FXML
    private ImageView calendario;
    @FXML
    private ImageView cerrar;
    @FXML
    private ImageView cobrar;
    @FXML
    private ImageView logIn;
    @FXML
    private ImageView salir;
    
    
    @FXML
    private Rectangle productos;
    @FXML
    private Rectangle servicios;
    @FXML
    private Rectangle trabajadores;
    @FXML
    private Rectangle clientes;
  
    
	private Main mainApp; // Referencia a Main
    
	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
  
    @FXML
    public void initialize() throws SQLException {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	
    	productos.setOnMouseClicked(event -> mainApp.mostrarVista("productos.fxml"));
    	trabajadores.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadores.fxml"));
    	servicios.setOnMouseClicked(event -> mainApp.mostrarVista("servicios.fxml"));
    	clientes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
    	
    	logIn.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml"));
    }
    
    
}