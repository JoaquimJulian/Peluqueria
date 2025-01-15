package application.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import application.models.*;
import java.sql.SQLException;
import application.Main;

public class LogInController {
    @FXML
    private TableView<Trabajador> tablaUsuarios;
    @FXML
    private TableColumn<Trabajador, String> columnaUsuarios;
    @FXML
    private Button btnAcceder;
    @FXML
    private PasswordField contrasena;
    @FXML
    private BorderPane panelPrincipal;
    @FXML
    private ImageView cerrar;
    @FXML
    private ImageView salir;

    private Main mainApp; // Referencia a Main

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() throws SQLException {
        // Configuración para cerrar la aplicación
        cerrar.setOnMouseClicked(event -> Platform.exit());
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));

        // Cargar lista de usuarios en la tabla
        cargarUsuarios();

        // Botón para iniciar sesión
        btnAcceder.setOnAction(event -> loguearUsuario());

        // Evento para capturar el "Intro" en el formulario completo
        panelPrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loguearUsuario();
                event.consume(); // Previene la propagación del evento
            }
        });
    }

    private void cargarUsuarios() throws SQLException {
        Trabajador trabajadores = new Trabajador();

        // Cargar usuarios desde la base de datos o modelo
        ObservableList<Trabajador> trabajador = trabajadores.getTrabajadoresActivos();
        tablaUsuarios.setItems(trabajador);

        columnaUsuarios.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    private void loguearUsuario() {
        Trabajador usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado == null) {
            // Alerta si no se selecciona un usuario
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor selecciona un usuario.");
            alerta.show();
            return;
        }

        String usuario = usuarioSeleccionado.getNombre();
        String password = contrasena.getText();

        // Verificar login
        String loginExitoso = databaseConection.verificarContraseñaAdmin(usuario, password);

        if (loginExitoso.equals("exitoso")) {
            // Redirigir a la vista correspondiente
            mainApp.mostrarVista("diaAdmin.fxml");
        } else if (loginExitoso.equals("Contraseña incorrecta")) {
            // Mostrar alerta de error
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Acceso denegado");
            alerta.setHeaderText(null);
            alerta.setContentText("Contraseña incorrecta");
            alerta.show();
        }
    }
}
