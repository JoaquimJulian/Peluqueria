package application.controllers;

import java.sql.Date;
import java.sql.SQLException;

import application.Main;
import application.models.Cliente;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FichaController {

    @FXML
    private TableView<Cliente> tableViewServicios;

    @FXML
    private TableColumn<Cliente, Date> columnFecha;

    @FXML
    private TableColumn<Cliente, String> columnProductosServicios;

    @FXML
    private TableColumn<Cliente, Double> columnPrecio;

    private Main mainApp; // Referencia a Main

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        
    }

    @FXML
    public void initialize() throws SQLException {
        // Configurar las columnas
    	columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    	columnProductosServicios.setCellValueFactory(new PropertyValueFactory<>("productos/servicios"));
    	columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    	cargarDatos();
        
        
    }

    
    public void cargarDatos() throws SQLException {
    	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
    	
    	Cliente.cargarDatos(cliente.getId());
    	
    }
   

   
}
