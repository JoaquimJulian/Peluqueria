// trabajadoresController.java

package application.controllers;

import java.sql.SQLException;
import application.Main;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class trabajadoresInactivosController {

    @FXML
    private BorderPane panelPrincipal;

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

    // BOTONES CRUD
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActivar;
    @FXML
    private Button activos;

    @FXML
    private TextField barraBusqueda;

    // TABLA TRABAJADORES
    @FXML
    private TableView<Trabajador> tablaTrabajadores;
    @FXML
    private TableColumn<Trabajador, String> columnaNombre;
    @FXML
    private TableColumn<Trabajador, String> columnaApellidos;
    @FXML
    private TableColumn<Trabajador, Integer> columnaTelefono;
    @FXML
    private TableColumn<Trabajador, String> columnaEmail;
    @FXML
    private TableColumn<Trabajador, Boolean> columnaAdmin;
    @FXML
    private TableColumn<Trabajador, Double> columnaComision;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void initialize() throws SQLException {
        Platform.runLater(() -> panelPrincipal.requestFocus());

        cerrar.setOnMouseClicked(event -> Platform.exit());
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));

        btnActivar.setDisable(true);
        btnEliminar.setDisable(true);
        activos.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadores.fxml"));
        
        tablaTrabajadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnActivar.setDisable(false);
            btnEliminar.setDisable(false);
        });

        btnActivar.setOnAction(event -> {
            Trabajador trabajadorSeleccionado = tablaTrabajadores.getSelectionModel().getSelectedItem();
            if (trabajadorSeleccionado != null) {
                activarTrabajador(trabajadorSeleccionado);
            }
        });
        
        btnEliminar.setOnAction(event -> {
            Trabajador trabajadorSeleccionado = tablaTrabajadores.getSelectionModel().getSelectedItem();
            if (trabajadorSeleccionado != null) {
                eliminarTrabajador(trabajadorSeleccionado);
            }
        });

        // Configuración de las columnas
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnaAdmin.setCellValueFactory(new PropertyValueFactory<>("esAdministrador")); // Esta es la columna admin
        columnaComision.setCellValueFactory(new PropertyValueFactory<>("comision"));

        cargarTrabajadores();
    }

    private void cargarTrabajadores() throws SQLException {
    	Trabajador trabajadores = new Trabajador();
        ObservableList<Trabajador> trabajador = trabajadores.getTrabajadoresInactivos();
        tablaTrabajadores.setItems(trabajador);
    }

    private void activarTrabajador(Trabajador trabajador) { 
        trabajador.activarTrabajador(trabajador.getId());
        try {
            cargarTrabajadores();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private void eliminarTrabajador(Trabajador trabajador) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación de Eliminacion");
        alerta.setHeaderText("¿Estás seguro de que deseas eliminar el trabajador?");
        alerta.setContentText("Ten en cuenta que se perderá el registro de ventas, servicios realizados y todos los datos de este trabajador");
        if (alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            trabajador.eliminarTrabajador(trabajador.getId());
            try {
                cargarTrabajadores();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
