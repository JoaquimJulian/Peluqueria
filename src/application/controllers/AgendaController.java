package application.controllers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.time.LocalDate;

import application.Main;
import application.models.Trabajador;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AgendaController {
	
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
    private BorderPane diaAdmin;
    @FXML
    private DatePicker calendarioAgenda;
    @FXML
    private HBox hboxAgenda;
    @FXML
    private Button guardarAgenda;
  
    
	private Main mainApp; // Referencia a Main
    
	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
  
    @FXML
    public void initialize() {
    	logIn.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	crearTabla();
    }
    
    
    public void crearTabla() {
    	Trabajador trabajador = new Trabajador();
    	
    	String[] horas= {
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                "17:00", "17:30", "18:00", "18:30", "19:00", "19:30",
                "20:00"
            };
    	
    	int tamanoScrollPane = 1250;
    	int numTrabajadores = trabajador.getTrabajadores().size();
    	int tamanoColumna = tamanoScrollPane/numTrabajadores;
    	
    	ObservableList<Trabajador> trabajadores = trabajador.getTrabajadores();
    	
    	// CREAR COLUMNA DE HORAS
    	
    	VBox vbox2 = new VBox();
    	
    	for (int i = -1; i<horas.length; i++) {
			Label label2 = new Label();
			label2.setPrefHeight(100);
			if (i == -1) {
				label2.setText(null);
			}else {
				
				label2.setText(horas[i]);
			}
			vbox2.getChildren().add(label2);
		}
    	hboxAgenda.getChildren().add(vbox2);
    	
    	// CREAR COLUMNA PARA CADA TRABAJADOR CON LOS TEXTFIELD CON ID
    	
    	for (Trabajador t : trabajadores) {
    		VBox vbox = new VBox();
    		Label label = new Label();
    		label.setPrefHeight(100);
    		label.setText(t.getNombre());
    		vbox.getChildren().add(label);
    		
    		LocalDate fecha = LocalDate.now();
    		calendarioAgenda.setValue(fecha);
    		
    		for (int i = 0; i<horas.length; i++) {
    			TextField textField = new TextField();
    			textField.setPrefHeight(100);
    			textField.setPrefWidth(tamanoColumna);
    			textField.setId(calendarioAgenda.getValue().toString() + "__" + horas[i] + "__" + t.getNombre()); //le doy id a cada textField
    			textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
    				textField.focusedProperty().addListener((observablee, oldValuee, newValuee) -> {
    					System.out.println(newValue + "  " + textField.getId());
    				});
    			});
    			vbox.getChildren().add(textField);
    		}
    		
    		hboxAgenda.getChildren().add(vbox);
    	}
    }
    
    public String guardarAgenda(String reserva) {
    	
    	
    	
		return reserva;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}