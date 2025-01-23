package application.controllers;

import application.Main;
import application.models.Facturacion;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class estadisticasGeneralesController {
	
	@FXML
	private ImageView salir;

    @FXML
    private BarChart<String, Number> graficoFacturacionTrabajador;
    @FXML
    private BarChart<String, Number> graficoProductosTrabajador;
    @FXML
    private BarChart<String, Number> graficoServiciosTrabajador;
	
    private Main mainApp; // Referencia a Main
    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    public void initialize() throws SQLException {
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml", Trabajador.getTrabajadorLogueado()));
    	
    	llenarGraficoFacturacionTrabajador();
    	llenarGraficoProductosTrabajador();
    	llenarGraficoServiciosTrabajador();
    }

    
    public void llenarGraficoFacturacionTrabajador() {
    	List<Map<String, Object>> facturacionTrabajador = Facturacion.facturacionPorTrabajador();
	
    	// Crear la serie
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();

    	// Agregar los datos a la serie
    	for (Map<String, Object> fila : facturacionTrabajador) {
    	    String nombre = (String) fila.get("nombre");
    	    Double totalFacturado = (Double) fila.get("total_facturado");

    	    XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, totalFacturado);
    	    serie.getData().add(data);
    	}

    	// Agregar la serie al gráfico
    	graficoFacturacionTrabajador.getData().add(serie);
    }
    
    public void llenarGraficoProductosTrabajador() {
    	List<Map<String, Object>> productosTrabajador = Facturacion.productosPorTrabajador();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();

    	for (Map<String, Object> fila : productosTrabajador) {
    		String nombre = (String) fila.get("nombre");
    	    Integer totalFacturado = (Integer) fila.get("numero_productos");

    	    XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, totalFacturado);
    	    serie.getData().add(data);
    	}
    	
    	graficoProductosTrabajador.getData().add(serie);
 
    	
    }
    
    public void llenarGraficoServiciosTrabajador() {
    	List<Map<String, Object>> serviciosTrabajador = Facturacion.serviciosPorTrabajador();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();

    	for (Map<String, Object> fila : serviciosTrabajador) {
    		String nombre = (String) fila.get("nombre");
    	    Integer totalFacturado = (Integer) fila.get("numero_servicios");

    	    XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, totalFacturado);
    	    serie.getData().add(data);
    	}
    	
    	graficoServiciosTrabajador.getData().add(serie);
    }
}
