package application.controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import application.Main;
import application.models.Cliente;
import application.models.Facturacion;
import application.models.Serviciovendido;
import application.models.Trabajador;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class fichaClienteController {
	
	// BOTONES HEADER
    @FXML
    private ImageView calendario;
    @FXML
    private ImageView ajustes;
    @FXML
    private ImageView cerrar;
    @FXML
    private ImageView cobrar;
    @FXML
    private ImageView usuarios;
    @FXML
    private ImageView salir;
    @FXML
    private ImageView ficha;
    @FXML
	private Text nombreSesion;

	@FXML
    private TableView<Facturacion> tableViewServicios;

    @FXML
    private TableColumn<Facturacion, Date> columnFecha;

    @FXML
    private TableColumn<Facturacion, Integer> columnProductos;

    @FXML
    private TableColumn<Facturacion, Integer> columnServicios;

    @FXML
    private TableColumn<Facturacion, Double> columnPrecio;
    
    @FXML
    private Button btnNuevoServicio;
    @FXML
    private Text nombreCliente;
    @FXML
    private TextArea observaciones;
    @FXML
    private Button guardarObservaciones;

    private Main mainApp; // Referencia a Main
    
    
    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        ponerNombreCliente();
        datosCliente();
        initialize();
    }
  

    @FXML
    public void initialize() throws SQLException {
    	if (mainApp != null) {
    		Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
        	nombreSesion.setText(trabajadorLogueado.getNombre());
        	
        	if (!trabajadorLogueado.isEsAdministrador()) {
        		Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
        		ajustes.setImage(imagenCliente);
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	}else {
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
        	}
        	
        	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        	salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	
        	
            // Configurar las columnas
            columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            columnProductos.setCellValueFactory(new PropertyValueFactory<>("nombre_producto"));
            columnServicios.setCellValueFactory(new PropertyValueFactory<>("nombre_servicio"));
            columnPrecio.setCellValueFactory(new PropertyValueFactory<>("monto_total"));
            
            
            btnNuevoServicio.setOnMouseClicked(event -> {
                // Obtén el nombre del cliente
            	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
                mainApp.mostrarVista("cobro.fxml", cliente);
            });
            
            observaciones.setText(datosCliente().getObservaciones());
            guardarObservaciones.setOnMouseClicked(event -> {
    			try {
    				guardarObservaciones();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		});
            cargarDatos();
    	}
        
        
    }

    
    public void cargarDatos() throws SQLException {
        
        Cliente cliente = (Cliente) mainApp.getDatosCompartidos();

        // Obtener los datos desde el modelo (Método modificado en Cliente para obtener los datos)
        ObservableList<Facturacion> datos = Facturacion.getFacturacionCliente(cliente.getId());
        // Cargar los datos en el TableView
        tableViewServicios.setItems(datos);
        
        tableViewServicios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Verifica si son 2 clics
                Facturacion facturacionSeleccionada = tableViewServicios.getSelectionModel().getSelectedItem();
                if (facturacionSeleccionada != null) { // Asegúrate de que hay una fila seleccionada
                    mostrarObservacionFacturacion(facturacionSeleccionada);
                }
            }
        });
        
        
    }
    
    public void ponerNombreCliente() {
        Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
        String apellidoCliente = "";
        
        if (!cliente.getApellido().isEmpty()) {
            apellidoCliente = cliente.getApellido();
        }
        
        nombreCliente.setText("Ficha de " + cliente.getNombre() + " " + apellidoCliente);
    }
    
    public Cliente datosCliente() {
        Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
        return cliente;
    }
    
    private void guardarObservaciones() throws SQLException {
    	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
    	int idCliente = cliente.getId();
    	if(observaciones.getText() != null && !observaciones.getText().isEmpty()) {
    		if(Cliente.guardarObservaciones(observaciones.getText(), idCliente)) {
    			Alert alerta = new Alert(AlertType.INFORMATION);
    		    
    		    alerta.setTitle("Observacion");
    		    alerta.setHeaderText("Observacion guardada con exito");
    		    
    		    alerta.showAndWait();
    		}else {
    			Alert alerta = new Alert(AlertType.ERROR);
    		    
    		    alerta.setTitle("Observacion");
    		    alerta.setHeaderText("Error al guardar la observación");
    		    
    		    alerta.showAndWait();
    		}
    	}
    }

    public void mostrarObservacionFacturacion(Facturacion facturacion) {
    	// Crear una nueva ventana para el popup
        Stage popup = new Stage();
        Stage owner = (Stage) nombreCliente.getScene().getWindow();

        // Establecer que el popup es modal (bloquea la ventana principal)
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner); // Propietario de la ventana popup (para que se quede encima de la ventana principal)

        TextArea observacion = new TextArea();
        observacion.setText(facturacion.getObservacion_facturacion());
        Button guardarCambios = new Button();
        guardarCambios.setText("Guardar cambios");
        guardarCambios.setStyle("-fx-text-fill: white; -fx-background-color: black;");
        guardarCambios.setPrefWidth(160);  
        guardarCambios.setPrefHeight(85);

        guardarCambios.setOnMouseClicked(event -> {
			try {
				guardarObservacionFacturacion(observacion.getText(), facturacion.getId_factura());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

        // Layout para el popup
        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(observacion);

        HBox buttonLayout = new HBox();
        buttonLayout.setAlignment(Pos.CENTER); // Centrar el botón en el HBox
        buttonLayout.setSpacing(5); // Añadir un espacio entre el TextArea y el botón
        buttonLayout.setPadding(new Insets(0, 0, 10, 0));
        buttonLayout.getChildren().add(guardarCambios);
        
        popupLayout.getChildren().add(buttonLayout);


        // Configurar la escena del popup
        Scene popupScene = new Scene(popupLayout, 600, 200);
        popup.setTitle("Observaciones de la facturacion");
        popup.setScene(popupScene);

        // Mostrar el popup
        popup.show();
    }

    public void guardarObservacionFacturacion(String observacion, int id_factura) throws SQLException {
    	if(Facturacion.guardarObservaciones(observacion, id_factura)) {
    		Alert alerta = new Alert(AlertType.INFORMATION);
		    
		    alerta.setTitle("Observacion");
		    alerta.setHeaderText("Observacion guardada con exito");
		    
		    alerta.show();
		 
		    PauseTransition pause = new PauseTransition(Duration.seconds(2));
		    pause.setOnFinished(event -> alerta.close()); 

		    pause.play();
		}else {
			Alert alerta = new Alert(AlertType.ERROR);
		    
		    alerta.setTitle("Observacion");
		    alerta.setHeaderText("Error al guardar la observación");
		    
		    alerta.show();
		    
		    PauseTransition pause = new PauseTransition(Duration.seconds(2));
		    pause.setOnFinished(event -> alerta.close()); 

		    pause.play();
		}
    	
    }

   
}
