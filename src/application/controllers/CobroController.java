package application.controllers;

import java.sql.SQLException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import application.models.Cliente;  // Asegúrate de que Cliente esté correctamente importado

public class CobroController {

	// CobroController.java
	@FXML
	private TextField ClienteNombreField;  // Este es el TextField donde mostrarás el nombre del cliente

	 
	 
	private Main mainApp;  // Referencia a la aplicación principal

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        cargarCliente();
    }
    

    // Método para cargar datos del cliente
    public void cargarCliente() throws SQLException {
   	 	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
   	 	ClienteNombreField.setText(cliente.getNombre());
   	 	
    }

  
}
