package application.controllers;

import application.Main;


public class AgendaController {
	
	private Main mainApp; // Referencia a Main
    
	// Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}