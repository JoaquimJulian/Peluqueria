package application.controllers;

import java.sql.SQLException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import application.models.Cliente;  // Asegúrate de que Cliente esté correctamente importado

public class CobroController {

	// CobroController.java
	@FXML
	private TextField clienteNombreField;  // Este es el TextField donde mostrarás el nombre del cliente

	 
	 
	private Main mainApp;  // Referencia a la aplicación principal

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setClienteNombre(String clienteNombre) {
    		System.out.println("nombre del cliente 2: " + clienteNombre);
    	    clienteNombreField.setText(clienteNombre); // Establece el nombre en el TextField
    	}

    // Método para cargar y mostrar el nombre del cliente
    public void cargarNombreCliente() throws SQLException {
    	
    	
    	// Obtén el nombre del cliente
   	 Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
       String clienteNombre = null;
		try {
			clienteNombre = Cliente.nombreCliente(cliente.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       
       System.out.println("nombre del cliente4: " + clienteNombre);
       // Llama a la función para mostrar la vista y pasar el nombre del cliente
    }

    // Método para manejar la acción del botón "Nuevo Servicio"
    @FXML
    public void onNuevoServicioClicked() throws SQLException {
        // Al hacer clic en "Nuevo Servicio", cargar el nombre del cliente
        cargarNombreCliente();
    }
}
