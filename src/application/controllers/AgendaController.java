package application.controllers;

import java.sql.SQLException;

import application.Main;
import application.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    private TableView<Trabajador> tablaAgenda;
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

    public void initialize() throws SQLException {
    	crearColumnas();
    	ajustarAnchoColumnas();
    	agregarFilas();
    }
    
    public void crearColumnas() {
    	ObservableList<Trabajador> trabajadores = Trabajador.getTrabajadores();
    	
    	TableColumn<Trabajador, String> columnaHora = new TableColumn<>("Hora");
        columnaHora.setPrefWidth(20);  // Establecer un ancho fijo para la columna "Hora"
        tablaAgenda.getColumns().add(columnaHora);
    	
    	for (Trabajador trabajador : trabajadores) {
    		// Crear una nueva columna para el trabajador
            TableColumn<Trabajador, String> columnaTrabajador = new TableColumn<>(trabajador.getNombre());

            // Configurar la columna para que muestre el nombre del trabajador
            // Cambia esto según el tipo de dato que deseas mostrar (por ejemplo, nombre, teléfono, etc.)
            columnaTrabajador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            
            // Agregar la columna al TableView
            tablaAgenda.getColumns().add(columnaTrabajador);
    	}
    }
    
    public void ajustarAnchoColumnas() {
        // Establecer la política de redimensionamiento para que las columnas ocupen todo el ancho
        tablaAgenda.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Asegurarse de que las demás columnas se ajusten al ancho de la tabla
        for (int i = 1; i < tablaAgenda.getColumns().size(); i++) {
            TableColumn<Trabajador, ?> columna = tablaAgenda.getColumns().get(i);
            columna.setResizable(true);  // Hacer que las demás columnas sean redimensionables
        }
    }
    
    public void agregarFilas() {
    	
    }
    
    
}