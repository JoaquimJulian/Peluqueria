// trabajadoresController.java

package application.controllers;

import java.sql.SQLException;
import application.Main;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

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
    @FXML
	private Text nombreSesion;
    @FXML
    private ImageView ficha;

    // BOTONES CRUD
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDesactivar;
    @FXML
    private Button inactivos;

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
    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
    	nombreSesion.setText(trabajadorLogueado.getNombre());
    	
        Platform.runLater(() -> panelPrincipal.requestFocus());

        cerrar.setOnMouseClicked(event -> Platform.exit());
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
        ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
		calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        
        btnCrear.setOnMouseClicked(event -> mainApp.mostrarVista("crearTrabajadores.fxml"));
        btnEditar.setDisable(true);
        btnDesactivar.setDisable(true);
        inactivos.setOnMouseClicked(event -> mainApp.mostrarVista("trabajadoresInactivos.fxml"));
        
        tablaTrabajadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnEditar.setDisable(false);
            btnDesactivar.setDisable(false);
        });

        btnEditar.setOnAction(event -> {
            Trabajador trabajadorSeleccionado = tablaTrabajadores.getSelectionModel().getSelectedItem();
            if (trabajadorSeleccionado != null) {
                mainApp.mostrarVista("editarTrabajadores.fxml", trabajadorSeleccionado);
            }
        });

        btnDesactivar.setOnAction(event -> {
            Trabajador trabajadorSeleccionado = tablaTrabajadores.getSelectionModel().getSelectedItem();
            if (trabajadorSeleccionado != null) {
                desactivarTrabajador(trabajadorSeleccionado);
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
        ObservableList<Trabajador> trabajadores = Trabajador.getTrabajadoresActivos();
        ObservableList<Trabajador> filtroBusqueda = FXCollections.observableArrayList(trabajadores);
        tablaTrabajadores.setItems(filtroBusqueda);

        barraBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filtroBusqueda.clear();
            for (Trabajador trabajador : trabajadores) {
                if (trabajador.getNombre().toLowerCase().contains(newValue.toLowerCase())) {
                    filtroBusqueda.add(trabajador);
                }
            }
        });
    }
    

    private void desactivarTrabajador(Trabajador trabajador) {
        trabajador.desactivarTrabajador(trabajador.getId());
        try {
            cargarTrabajadores();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
