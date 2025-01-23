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
import java.util.HashMap;
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
    
 // Mapa para asignar colores a cada trabajador
    private final Map<String, String> coloresTrabajadores = new HashMap<>();

    // Paleta de colores
    private final String[] colores = {
    	 
    		 "#FFD580", // Melocotón
    		 "#E63946", // Rojo intenso
    		 "#F4A261", // Naranja vibrante
    		 "#2A9D8F", // Verde azulado oscuro
    		 "#264653", // Azul petróleo
    		 "#A8DADC", // Verde menta brillante
    	};


    // Contador para asignar colores únicos
    private int colorIndex = 0;
    
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

    
    private String obtenerColorTrabajador(String nombre) {
        // Asignar un color único si el trabajador no tiene uno asignado
        if (!coloresTrabajadores.containsKey(nombre)) {
            coloresTrabajadores.put(nombre, colores[colorIndex % colores.length]);
            colorIndex++;
        }
        return coloresTrabajadores.get(nombre);
    }

    public void llenarGraficoFacturacionTrabajador() {
        List<Map<String, Object>> facturacionTrabajador = Facturacion.facturacionPorTrabajador();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (Map<String, Object> fila : facturacionTrabajador) {
            String nombre = (String) fila.get("nombre");
            Double totalFacturado = (Double) fila.get("total_facturado");

            XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, totalFacturado);

            // Obtener el color del trabajador y aplicarlo
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + obtenerColorTrabajador(nombre) + ";");
                }
            });

            serie.getData().add(data);
        }

        graficoFacturacionTrabajador.getData().add(serie);
    }

    public void llenarGraficoProductosTrabajador() {
        List<Map<String, Object>> productosTrabajador = Facturacion.productosPorTrabajador();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (Map<String, Object> fila : productosTrabajador) {
            String nombre = (String) fila.get("nombre");
            Integer totalProductos = (Integer) fila.get("numero_productos");

            XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, totalProductos);

            // Obtener el color del trabajador y aplicarlo
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + obtenerColorTrabajador(nombre) + ";");
                }
            });

            serie.getData().add(data);
        }

        graficoProductosTrabajador.getData().add(serie);
    }

    public void llenarGraficoServiciosTrabajador() {
        List<Map<String, Object>> serviciosTrabajador = Facturacion.serviciosPorTrabajador();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (Map<String, Object> fila : serviciosTrabajador) {
            String nombre = (String) fila.get("nombre");
            Integer totalServicios = (Integer) fila.get("numero_servicios");

            XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, totalServicios);

            // Obtener el color del trabajador y aplicarlo
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + obtenerColorTrabajador(nombre) + ";");
                }
            });

            serie.getData().add(data);
        }

        graficoServiciosTrabajador.getData().add(serie);
    }
}
