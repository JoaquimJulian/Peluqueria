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
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class diaAdminController {
    
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
	    private ImageView ficha;
		@FXML
	    private ImageView basura;
		@FXML
		private Text nombreSesion;

	    @FXML
	    private BorderPane diaAdmin;
	    @FXML
	    private DatePicker calendarioAgenda;
	    @FXML
	    private HBox hboxAgenda;
	    @FXML
	    private Button expandirReserva;
	    @FXML
	    private Button vaciarReserva;
	    @FXML
	    private Button verEstadisticas;
	    @FXML
	    private Label nombreFecha;

	    private Main mainApp;
	    private Trabajador trabajadorLogueado;
	    private String descripcion = "";

	    public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;
	    }
  
    @FXML
    public void initialize() throws SQLException {
    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
    	nombreSesion.setText(trabajadorLogueado.getNombre());

    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
    	basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));
		
    	if (!trabajadorLogueado.isEsAdministrador()) {
    		Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
    		ajustes.setImage(imagenCliente);
    		salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml"));
			ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
    	}else {
    		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
    		salir.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
    	}
    	
    	crearTabla(LocalDate.now());
        calendarioAgenda.setValue(LocalDate.now());
        calendarioAgenda.getValue();
        calendarioAgenda.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    crearTabla(newValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                } // Método para actualizar los datos
            }
        });
        
        if (expandirReserva != null) {
            expandirReserva.setFocusTraversable(false); // Evita que el botón tome el foco
            expandirReserva.setOnAction(event -> onExpandirReserva());
        }

        if (vaciarReserva != null) {
            vaciarReserva.setFocusTraversable(false); // Evita que el botón tome el foco
            vaciarReserva.setOnAction(event -> {
                // Obtén el nodo actualmente enfocado
                javafx.scene.Node focusedNode = vaciarReserva.getScene().getFocusOwner();

                // Verifica si el nodo es un TextField
                if (focusedNode instanceof TextField) {
                    TextField currentTextField = (TextField) focusedNode;

                    // Borra el contenido del TextField enfocado
                    currentTextField.clear();

                    // Busca el TextField inmediatamente superior
                    String[] partesId = currentTextField.getId().split("__");

                    if (partesId.length >= 3) {
                        try {
                            // Calcula la hora anterior para encontrar el TextField superior
                            LocalTime horaActual = LocalTime.parse(partesId[1]);
                            LocalTime horaAnterior = horaActual.minusMinutes(30);
                            String idSuperior = partesId[0] + "__" + horaAnterior + "__" + partesId[2];

                            // Busca el TextField superior
                            TextField superiorTextField = buscarTextFieldEnHBox(hboxAgenda, idSuperior);

                            // Si se encuentra, transfiere el foco al TextField superior
                            if (superiorTextField != null) {
                                superiorTextField.requestFocus();
                            } else {
                                System.err.println("No se encontró el TextField superior con ID: " + idSuperior);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("El ID del TextField no tiene el formato esperado.");
                    }
                } else {
                    System.err.println("El nodo enfocado no es un TextField.");
                }
            });
        }
        
        verEstadisticas.setOnMouseClicked(event -> mainApp.mostrarVista("estadisticasGenerales.fxml"));

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
                label2.setText(horas[i]); 
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
                textField.setId(fechaSeleccionada.toString() + "__" + horas[i] + "__" + t.getId()); //le doy id a cada textField
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
    
    private void onExpandirReserva() {
        javafx.scene.Node focusedNode = diaAdmin.getScene().getFocusOwner();

        if (focusedNode instanceof TextField) {
            TextField currentTextField = (TextField) focusedNode;
            String contenido = currentTextField.getText();

            String[] partesId = currentTextField.getId().split("__");

            if (partesId.length >= 3) {
                try {
                    // Calcula la hora siguiente y genera el ID del siguiente TextField
                    LocalTime horaActual = LocalTime.parse(partesId[1]);
                    LocalTime horaSiguiente = horaActual.plusMinutes(30);
                    String nuevoId = partesId[0] + "__" + horaSiguiente + "__" + partesId[2];

                    // Busca manualmente el siguiente TextField en hboxAgenda
                    TextField siguienteTextField = buscarTextFieldEnHBox(hboxAgenda, nuevoId);

                    if (siguienteTextField != null) {
                        if (!siguienteTextField.getText().isEmpty()) {
                            // Mostrar popup de alerta
                            Alert alerta = new Alert(AlertType.WARNING);
                            alerta.setTitle("Advertencia");
                            alerta.setHeaderText(null);
                            alerta.setContentText("No se puede sobrescribir.");
                            alerta.showAndWait();
                        } else {
                            // Copia el contenido al siguiente TextField
                            siguienteTextField.setText(contenido);
                            siguienteTextField.getStyleClass().add("textRelleno");
                            siguienteTextField.requestFocus(); // Mueve el focus al siguiente TextField

                            // Inserta en la base de datos
                            Agenda.crearReserva(
                                    Date.valueOf(partesId[0]),
                                    Time.valueOf(horaSiguiente),
                                    contenido,
                                    nuevoId,
                                    Integer.parseInt(partesId[2]) // ID del trabajador numérico
                            );
                        }
                    } else {
                        System.err.println("No se encontró el TextField con ID: " + nuevoId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("El ID del TextField actual no tiene el formato esperado.");
            }
        } else {
            System.err.println("El nodo enfocado no es un TextField.");
        }
    }
    
    private TextField buscarTextFieldEnHBox(HBox hbox, String idBuscado) {
        for (javafx.scene.Node node : hbox.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;

                for (javafx.scene.Node childNode : vbox.getChildren()) {
                    if (childNode instanceof TextField) {
                        TextField textField = (TextField) childNode;
                        if (idBuscado.equals(textField.getId())) {
                            return textField;
                        }
                    }
                }
            }
        }
        return null; // Si no encuentra el TextField
    }
    
    
}
