package application.controllers;

import application.Main;
import application.models.Facturacion;
import application.models.Trabajador;
import application.models.databaseConection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class estadisticasController {

    @FXML
    private ImageView salir;
    @FXML
	private Text nombreSesion;

    @FXML
    private Label nombreTrabajador, registrosFacturacion;

    @FXML
    private TableView<Facturacion> tablaRegistros;

    @FXML
    private TableColumn<Facturacion, String> colCliente;

    @FXML
    private TableColumn<Facturacion, String> colNombreServicio;

    @FXML
    private TableColumn<Facturacion, String> colNombreProducto;

    @FXML
    private TableColumn<Facturacion, Double> colMontoTotal;

    @FXML
    private TableColumn<Facturacion, LocalDate> colFecha;


    private ObservableList<Facturacion> listaRegistros;

    private Trabajador trabajador; // Nuevo atributo para almacenar el Trabajador recibido

    
    private Main mainApp; // Referencia a Main
    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    public void initialize() throws SQLException {
    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
    	nombreSesion.setText("Estadisticas de: " + trabajadorLogueado.getNombre());
    	
    	salir.setOnMouseClicked(event -> { Platform.exit(); });

    	
    	colCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombre_servicio"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre_producto"));
        colMontoTotal.setCellValueFactory(new PropertyValueFactory<>("monto_total"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    	
        listaRegistros = Facturacion.getFacturacionConNombres();
        tablaRegistros.setItems(listaRegistros);
    }

}