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

public class trabajadoresController {

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
    private Button btnCrear;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

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
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml"));

        btnCrear.setOnMouseClicked(event -> mainApp.mostrarVista("crearTrabajadores.fxml"));
        
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        
        tablaTrabajadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnEditar.setDisable(newValue == null);
            btnEliminar.setDisable(newValue == null);
        });

        btnEditar.setOnAction(event -> {
            Trabajador trabajadorSeleccionado = tablaTrabajadores.getSelectionModel().getSelectedItem();
            if (trabajadorSeleccionado != null) {
                mainApp.mostrarVista("editarTrabajadores.fxml", trabajadorSeleccionado);
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
        ObservableList<Trabajador> trabajadores = Trabajador.getTrabajadores();
        tablaTrabajadores.setItems(trabajadores);
    }

    private void eliminarTrabajador(Trabajador trabajador) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación de Eliminación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Estás seguro de que deseas eliminar el trabajador?");
        if (alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Trabajador.eliminarTrabajador(trabajador.getId());
            try {
                cargarTrabajadores();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
