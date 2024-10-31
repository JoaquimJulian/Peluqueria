package application.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import application.models.*;


import java.sql.SQLException;

import application.Main; // Importa la clase Main

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

    private Main mainApp; // Referencia a Main

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() throws SQLException {
        cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        cargarUsuarios();

        btnAcceder.setOnAction(event -> loguearUsuario());

    }
    
    private void cargarUsuarios() throws SQLException {

        ObservableList<Trabajador> trabajadores = Trabajador.getTrabajadores();
        tablaUsuarios.setItems(trabajadores);
    	
        columnaUsuarios.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    private void loguearUsuario() {
    	Trabajador usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
    	
    	String usuario = usuarioSeleccionado.getNombre();
    	String password = contrasena.getText();
    	
    	boolean loginExitoso = databaseConection.verificarContraseña(usuario, password);
    	
    	if (loginExitoso) {
    		mainApp.mostrarVista("diaAdmin.fxml");
    	}
    }
}
