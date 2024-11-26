package application.controllers;

import java.awt.Label;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;


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
    private TableView<Map<Integer, String>> tablaAgenda;
    @FXML
    private ImageView adelantarDia;
    @FXML
    private ImageView adelantarMes;
    @FXML
    private ImageView atrasarDia;
    @FXML
    private ImageView atrasarMes;
    @FXML
    private Text dia;
    @FXML
    private Text mes;
	
    
	private Main mainApp; // Referencia a Main
    
	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
  
    @FXML
    public void initialize() {
       configurarTabla(); 
       tablaAgenda.setFixedCellSize(100); 
    }
    
    private void configurarTabla() {
    	
    	double anchoTotalTableView = 1366;
        // Obtener la lista de trabajadores
        List<Trabajador> trabajadores = Trabajador.getTrabajadores();

        // Configurar la columna de horas (intervalos de tiempo)
        TableColumn<Map<Integer, String>, String> columnaHoras = new TableColumn<>("Horas");
        columnaHoras.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(-1))); // -1 será la clave para las horas
        tablaAgenda.getColumns().add(columnaHoras);
        double anchoFijoHoras = 100;
        columnaHoras.setPrefWidth(anchoFijoHoras);
        columnaHoras.setResizable(false);
        
        int numeroColumnasDinamicas = trabajadores.size();
        double anchoDisponible = anchoTotalTableView - anchoFijoHoras;
        double anchoPorColumna = anchoDisponible / numeroColumnasDinamicas;
        
        // Crear columnas dinámicas para cada trabajador
        for (Trabajador trabajador : trabajadores) {
            TableColumn<Map<Integer, String>, String> columnaTrabajador = new TableColumn<>(trabajador.getNombre());
            columnaTrabajador.setCellValueFactory(cellData -> {
                Map<Integer, String> fila = cellData.getValue();
                return new SimpleStringProperty(fila.get(trabajador.getId()));
            });
            
            columnaTrabajador.setResizable(false);
            columnaTrabajador.setPrefWidth(anchoPorColumna);
            columnaTrabajador.setEditable(true);
            columnaTrabajador.setCellFactory(TextFieldTableCell.forTableColumn());
            columnaTrabajador.setOnEditCommit(event -> {
                Map<Integer, String> fila = event.getRowValue();
                fila.put(trabajador.getId(), event.getNewValue());
            });
            tablaAgenda.getColumns().add(columnaTrabajador);
        }

        // Llenar los datos en la tabla
        tablaAgenda.setItems(generarFilas(trabajadores));
    }

    private ObservableList<Map<Integer, String>> generarFilas(List<Trabajador> trabajadores) {
        ObservableList<Map<Integer, String>> filas = FXCollections.observableArrayList();

        // Intervalos de tiempo (de 9:00 a 19:30, cada 30 minutos)
        LocalTime horaInicio = LocalTime.of(9, 0);
        LocalTime horaFin = LocalTime.of(19, 30);

        while (!horaInicio.isAfter(horaFin)) {
            Map<Integer, String> fila = new HashMap<>();
            fila.put(-1, horaInicio.toString()); // -1 para la columna de horas

            // Inicializar reservas vacías para cada trabajador
            for (Trabajador trabajador : trabajadores) {
                fila.put(trabajador.getId(), ""); // Valor inicial vacío
            }

            filas.add(fila);
            horaInicio = horaInicio.plusMinutes(30);
        }

        return filas;
    }
    
    
}