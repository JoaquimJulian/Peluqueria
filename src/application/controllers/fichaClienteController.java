package application.controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.models.Cliente;
import application.models.Facturacion;

import application.models.Trabajador;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private ImageView basura;
    @FXML
    private ImageView salir;
    @FXML
    private ImageView ficha;
    @FXML
	private Text nombreSesion;

	@FXML
    private TableView<Facturacion> tableViewFechas;

    @FXML
    private TableColumn<Facturacion, Date> columnFecha;

    @FXML
    private TextField productoMasVendido;
    @FXML
    private TextField servicioMasVendido;
    @FXML
    private TextField productosTotalesVendidos;
    @FXML
    private TextField serviciosTotalesVendidos;
    
    
    // POPUP
    
    @FXML
    private AnchorPane popupDetalles;
    @FXML
    private ImageView cerrarPopup;
    
    @FXML
    private TableView<Facturacion> tableServicios;
    @FXML
    private TableColumn<Facturacion, String> columnServicios;
    @FXML
    private TableView<Map<String, Object>> tableProductos;
    @FXML
    private TableColumn<Map<String, Object>, String> columnProductos;
    @FXML
    private TableColumn<Map<String, Object>, Integer> columnUnidades;
    @FXML
    private TextArea comentario;
    @FXML
    private Button btnGuardarComentarioSesion;
    
    
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
        	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));
        	
        	
            // Configurar las columnas
            columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            
            
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
        
        ObservableList<Facturacion> filtroBusqueda = Facturacion.getFacturacionCliente(cliente.getId());
        tableViewFechas.setItems(filtroBusqueda);
        
        
        tableViewFechas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Verifica si son 2 clics
                Facturacion facturacionSeleccionada = tableViewFechas.getSelectionModel().getSelectedItem();
                if (facturacionSeleccionada != null) { // Asegúrate de que hay una fila seleccionada
                	popupDetalles.setVisible(true);
                    try {
						mostrarDetallesFacturacion(facturacionSeleccionada);
					} catch (SQLException e) {
						e.printStackTrace();
					}
                }
            }
        });
        
        productosTotalesVendidos.setText(Facturacion.getProductosVendidosCliente(datosCliente().getId()).toString());
        serviciosTotalesVendidos.setText(Facturacion.getServiciosVendidosCliente(datosCliente().getId()).toString());
        productoMasVendido.setText(Facturacion.getProductoMasRepetido(datosCliente().getId()));
        servicioMasVendido.setText(Facturacion.getServicioMasRepetido(datosCliente().getId()));
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

    public void mostrarDetallesFacturacion(Facturacion facturacion) throws SQLException {
    	cerrarPopup.setOnMouseClicked(event -> popupDetalles.setVisible(false));
    	ObservableList<Facturacion> serviciosFacturacion = Facturacion.getServiciosSesion(datosCliente().getId(), facturacion.getFecha());
    	columnServicios.setCellValueFactory(new PropertyValueFactory<>("nombre_servicio"));
    	tableServicios.setItems(serviciosFacturacion);

    	columnProductos.setCellValueFactory(cellData -> 
        	new SimpleStringProperty((String) cellData.getValue().get("nombre_producto"))
    	);

	    // Configurar la columna de unidades
	    columnUnidades.setCellValueFactory(cellData -> 
	        new SimpleObjectProperty<>((Integer) cellData.getValue().get("unidades"))
	    );
	    
	    List<Map<String, Object>> productosSesion = Facturacion.getProductosSesion(datosCliente().getId(), facturacion.getFecha());
        ObservableList<Map<String, Object>> productosFacturacion = FXCollections.observableArrayList(productosSesion);

        // Establecer los datos en la tabla
        tableProductos.setItems(productosFacturacion);
        
        comentario.setText(Facturacion.getObservacionFacturacion(facturacion.getFecha(), datosCliente().getId()));
        btnGuardarComentarioSesion.setOnMouseClicked(event -> {
        	try {
				guardarObservacionFacturacion(comentario.getText(), facturacion.getFecha(), datosCliente().getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
    }
 
    public void guardarObservacionFacturacion(String observacion, Date fecha, int id_cliente) throws SQLException {
    	if(Facturacion.guardarObservaciones(observacion, fecha, id_cliente)) {
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
