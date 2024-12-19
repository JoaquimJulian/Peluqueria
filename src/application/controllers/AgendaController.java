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
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import application.Main;
import application.models.Agenda;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AgendaController {
	
	// BOTONES HEADER
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
    private Button expandirReserva;
    @FXML
    private Label nombreFecha;
  
    
	private Main mainApp; // Referencia a Main
	
	String descripcion = "";
    
	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
  
    @FXML
    public void initialize() throws SQLException {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	logIn.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("metodopago.fxml"));
    	
    	crearTabla(LocalDate.now());
    	calendarioAgenda.setValue(LocalDate.now());
		calendarioAgenda.getValue();
    	calendarioAgenda.valueProperty().addListener((observable, oldValue, newValue) -> {
    	    if (newValue != null) {
    	        try {
					crearTabla(newValue);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Método para actualizar los datos
    	    }
    	});
    }
    
    
    public void crearTabla(LocalDate fechaSeleccionada) throws SQLException {
    	
    	// Poner fecha en escrito al lado del date picker
    	DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", new Locale("es", "ES"));
    	String fechaFormateada = fechaSeleccionada.format(formatoFecha);
    	nombreFecha.setText(fechaFormateada);
    	
    	hboxAgenda.getChildren().removeIf(node -> node instanceof VBox); //eliminar todos los vbox del hbox cuando cambiamos de fecha
    	
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
				
				label2.setText(horas[i]); ///aaaaaaaaaaaaaaaaaaaaaaaaaa
			}
			vbox2.getChildren().add(label2);
		}
    	hboxAgenda.getChildren().add(vbox2);
    	
    	// CREAR COLUMNA PARA CADA TRABAJADOR CON LOS TEXTFIELD CON ID
    	
    	for (Trabajador t : trabajadores) {
    		VBox vbox = new VBox();
    		Label label = new Label();
    		label.getStyleClass().add("label");
    		label.setPrefHeight(300);
    		vbox.setAlignment(Pos.CENTER);
    		label.setText(t.getNombre());
    		int id_trabaj = (t.getId());
    		vbox.getChildren().add(label);
  
    		
    		
    		
    		for (int i = 0; i<horas.length; i++) {
    			
    			TextField textField = new TextField();
    			textField.setPrefHeight(300);
    			textField.setPrefWidth(tamanoColumna);
    			textField.setId(fechaSeleccionada.toString() + "__" + horas[i] + "__" + t.getNombre()); //le doy id a cada textField
    			String[] partes = textField.getId().split("__");
                LocalDate fechaCampo = LocalDate.parse(partes[0]); // primera posicion la fecha
                LocalTime horaCampo = LocalTime.parse(partes[1]); // segunda posicion la hora
                String reserva = Agenda.rellenartabla(fechaCampo,horaCampo, id_trabaj);
                if(reserva != null) {
                	textField.getStyleClass().add("textRelleno");	
                	textField.setText(reserva);
                }else {
                	textField.getStyleClass().add("textNoRelleno");
                }
                 
    			textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
    				
    				// cuando entras
    				if (!oldValue) {
    					descripcion = textField.getText(); // descripcion == null si el textField esta vacio
    				}
    				// cuando sales
    				else if (!newValue) {
    					if (descripcion != "" && textField.getText() != "") {
    						// Actualizar reserva
    						try {
								Agenda.actualizarReserva(
								        textField.getText(), 
								        textField.getId()  
								);
								textField.getStyleClass().add("textRelleno");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    					}
    					else if (descripcion == "" && textField.getText() != "") {
                            // Insertar en la base de datos
                        	try {
                        		Agenda.crearReserva(
                        				Date.valueOf(fechaCampo),
                                        Time.valueOf(horaCampo),
                                        textField.getText(), 
                                        textField.getId(),
                                        id_trabaj
                        		);
                        		textField.getStyleClass().add("textRelleno");
                                 
                            } catch (Exception e) {
                            	e.printStackTrace();
                            }
                        }
    					else if (textField.getText() == "") {
    						textField.getStyleClass().remove("textRelleno");
    						textField.getStyleClass().add("textNoRelleno");
                        	// Eliminar reserva
                        	try {
								Agenda.eliminarReserva(
										textField.getId()
								);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        	
                        }
                     
    				}
    			});
    			vbox.getChildren().add(textField);
    			
    		}
    		
    		hboxAgenda.getChildren().add(vbox);
    	}
    }
    
}