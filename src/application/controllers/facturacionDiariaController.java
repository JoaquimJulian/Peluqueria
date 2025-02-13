package application.controllers;

import application.Main;
import application.models.Facturacion;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class facturacionDiariaController {
	
	@FXML
	private ImageView salir;

    
    @FXML
	private Label facturacionTotalLabel; 
    @FXML
	private Label facturacionEfectivoLabel;
    @FXML
	private Label facturacionTarjetaLabel;
    @FXML
	private Label facturacionBizumLabel;
    
	
    private Main mainApp; // Referencia a Main

    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    public void initialize() throws SQLException {
        salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml", Trabajador.getTrabajadorLogueado()));

        java.sql.Date hoy = java.sql.Date.valueOf(LocalDate.now());

        
    	mostrarFacturacionTotal(hoy);
    	mostrarFacturacionEfectivo(hoy);
        mostrarFacturacionTarjeta(hoy);
        mostrarFacturacionBizum(hoy);

       
    }

	private void mostrarFacturacionTotal(Date hoy) {
	     try {
	    
	         // Obtén la facturación total desde la base de datos
	         List<Map<String, Object>> facturacionPorTrabajador = Facturacion.sumaTotalFacturacion(hoy, hoy, null);
	         double facturacionTotal = 0.0;

	         for (Map<String, Object> fila : facturacionPorTrabajador) {
	             facturacionTotal += (Double) fila.get("facturacion_total");
	         }

	         // Actualiza la etiqueta en la interfaz
	         facturacionTotalLabel.setText(String.format("Facturación Total: %.2f €", facturacionTotal));
	     } catch (Exception e) {
	         e.printStackTrace();
	         facturacionTotalLabel.setText("Error al calcular la facturación total.");
	     }
	 }

	
	private void mostrarFacturacionEfectivo(Date hoy) {
	    try {

	        // Obtén la facturación total desde la base de datos
	        List<Map<String, Object>>facturacionTotal = Facturacion.facturacionEfectivoPrueba(hoy, hoy, null);
	        double facturacionTotalEfectivo = 0.0;

	        for (Map<String, Object> fila : facturacionTotal) {
	             facturacionTotalEfectivo += (Double) fila.get("facturacion_total");
	        }

	        // Actualiza la etiqueta en la interfaz
	        facturacionEfectivoLabel.setText(String.format("Facturación Efectivo: %.2f €", facturacionTotalEfectivo));

	    } catch (Exception e) {
	        e.printStackTrace();
	        facturacionEfectivoLabel.setText("Error al calcular la facturación en efectivo.");
	    }
	}
	
	private void mostrarFacturacionTarjeta(Date hoy) {
	    try {


	        List<Map<String, Object>>facturacionTotal = Facturacion.facturacionTarjetaPrueba(hoy, hoy, null);
	        double facturacionTotalTarjeta = 0.0;

	        for (Map<String, Object> fila : facturacionTotal) {
	             facturacionTotalTarjeta += (Double) fila.get("facturacion_total");
	        }
 
	        // Actualiza la etiqueta en la interfaz
	        facturacionTarjetaLabel.setText(String.format("Facturación Tarjeta: %.2f €", facturacionTotalTarjeta));

	    } catch (Exception e) {
	        e.printStackTrace();
	        facturacionTarjetaLabel.setText("Error al calcular la facturación con tarjeta.");
	    }
	}

	private void mostrarFacturacionBizum(Date hoy) {
	    try {

	        List<Map<String, Object>>facturacionTotal = Facturacion.facturacionBizumPrueba(hoy, hoy, null);
	        double facturacionTotalBizum = 0.0;

	        for (Map<String, Object> fila : facturacionTotal) {
	             facturacionTotalBizum += (Double) fila.get("facturacion_total");
	        }

	        // Actualiza la etiqueta en la interfaz
	        facturacionBizumLabel.setText(String.format("Facturación Bizum: %.2f €", facturacionTotalBizum));

	    } catch (Exception e) {
	        e.printStackTrace();
	        facturacionBizumLabel.setText("Error al calcular la facturación con bizum.");
	    }
	}
	
}





