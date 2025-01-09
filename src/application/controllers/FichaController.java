package application.controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import application.Main;
import application.models.Cliente;
import application.models.Serviciovendido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FichaController {

	@FXML
    private TableView<Serviciovendido> tableViewServicios;

    @FXML
    private TableColumn<Serviciovendido, String> columnFecha;

    @FXML
    private TableColumn<Serviciovendido, String> columnProductos;

    @FXML
    private TableColumn<Serviciovendido, String> columnServicios;

    @FXML
    private TableColumn<Serviciovendido, Double> columnPrecio;

private Main mainApp; // Referencia a Main
    
    
    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        cargarDatos();
    }
  

    @FXML
    public void initialize() {
        // Configurar las columnas
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnProductos.setCellValueFactory(new PropertyValueFactory<>("producto"));
        columnServicios.setCellValueFactory(new PropertyValueFactory<>("servicio"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    
    public void cargarDatos() throws SQLException {
        if (mainApp == null) {
            System.err.println("Error: mainApp no está inicializado.");
            return;
        }

        Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
        if (cliente == null) {
            System.err.println("Error: No hay datos compartidos en mainApp.");
            return;
        }

        // Obtener los datos desde el modelo (Método modificado en Cliente para obtener los datos)
        List<String[]> datos = Cliente.cargarDatos(cliente.getId());

        // Crear una lista observable de objetos Serviciovendido para cargarla en el TableView
        ObservableList<Serviciovendido> serviciosVendidos = FXCollections.observableArrayList();

        // Recorrer los datos y agregarlos a la lista observable
        for (String[] fila : datos) {
            String fecha = fila[0];
            String producto = fila[1];
            String servicio = fila[2];
            Double precio = Double.parseDouble(fila[3]);

            // Crear un objeto Serviciovendido y agregarlo a la lista
            serviciosVendidos.add(new Serviciovendido(fecha, producto, servicio, precio));
        }

        // Cargar los datos en el TableView
        tableViewServicios.setItems(serviciosVendidos);
    }



   

   
}
