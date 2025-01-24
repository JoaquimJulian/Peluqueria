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

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

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
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private Button btnFiltrar;
    @FXML
    private ChoiceBox comboTrabajadores;
    @FXML
    private TextField facturacionTrabajador;
    @FXML
    private Button btnDatosGenerales;

	
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

        // Configurar DatePicker con la fecha actual
        fechaInicio.setValue(java.time.LocalDate.now());
        fechaFin.setValue(java.time.LocalDate.now());

        // Determinar si el usuario es administrador
        boolean esAdmin = Trabajador.getTrabajadorLogueado().isEsAdministrador();
        Integer idTrabajador = esAdmin ? null : Trabajador.getTrabajadorLogueado().getId();
        
        if (esAdmin) {
            // Mostrar el botón "Datos Generales" y configurar su acción
            btnDatosGenerales.setVisible(true);
            btnDatosGenerales.setOnAction(event -> mostrarDatosGenerales());

            // Mostrar el ComboBox para el administrador
            comboTrabajadores.setVisible(true);

        } else {
            btnDatosGenerales.setVisible(false);
            comboTrabajadores.setVisible(false);
        }
        
        Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
        // Cargar trabajadores en el ComboBox
        ObservableList<Trabajador> trabajadores = Trabajador.getTrabajadoresActivos();
    	
    	ObservableList<String> nombresTrabajadores = FXCollections.observableArrayList();

    	for (Trabajador trabajador : trabajadores) {
    		if (!trabajador.getNombre().equals("dreams")) {
        	    nombresTrabajadores.add(trabajador.getId() + " " + trabajador.getNombre());
    		}
    	    if (trabajador.getId() == trabajadorLogueado.getId()) {
    	    	comboTrabajadores.setValue(trabajador.getId() + " " + trabajador.getNombre());
    	    }
    	}

    	comboTrabajadores.setItems(nombresTrabajadores);
    	
    	aplicarFiltro();

        // Configurar el evento del botón "Filtrar"
        btnFiltrar.setOnAction(event -> aplicarFiltro());
    }



    private void mostrarDatosGenerales() {
        try {
            java.sql.Date inicio = java.sql.Date.valueOf(fechaInicio.getValue());
            java.sql.Date fin = java.sql.Date.valueOf(fechaFin.getValue());

            if (inicio.after(fin)) {
                System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin.");
                return;
            }

            // Llenar gráficos con datos generales (todos los usuarios)
            llenarGraficoFacturacionTrabajador(null, inicio, fin);
            llenarGraficoProductosTrabajador(null, inicio, fin);
            llenarGraficoServiciosTrabajador(null, inicio, fin);

            // Limpiar selección del ComboBox (opcional, para evitar confusión visual)
            comboTrabajadores.getSelectionModel().clearSelection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    private void aplicarFiltro() {
        try {
            java.sql.Date inicio = java.sql.Date.valueOf(fechaInicio.getValue());
            java.sql.Date fin = java.sql.Date.valueOf(fechaFin.getValue());
            
            String peluqueroSeleccionado = (String) comboTrabajadores.getValue(); // Esto devuelve "1 Carmen"
            String idPeluqueroString = peluqueroSeleccionado.split(" ")[0]; 
            int idPeluquero = Integer.parseInt(idPeluqueroString);
            
            if (inicio.after(fin)) {
                System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin.");
                return;
            }

            // Llenar gráficos con los datos del trabajador seleccionado
            llenarGraficoFacturacionTrabajador(idPeluquero, inicio, fin);
            llenarGraficoProductosTrabajador(idPeluquero, inicio, fin);
            llenarGraficoServiciosTrabajador(idPeluquero, inicio, fin);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    private String obtenerColorTrabajador(String nombre) {
        // Asignar un color único si el trabajador no tiene uno asignado
        if (!coloresTrabajadores.containsKey(nombre)) {
            coloresTrabajadores.put(nombre, colores[colorIndex % colores.length]);
            colorIndex++;
        }
        return coloresTrabajadores.get(nombre);
    }
    
    public void llenarGraficoFacturacionTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
        // Limpia los datos actuales del gráfico
        graficoFacturacionTrabajador.getData().clear();

        // Obtén la facturación con el filtro correspondiente
        List<Map<String, Object>> facturacionTrabajador = Facturacion.facturacionPorTrabajador(idTrabajador, inicio, fin);

        if (facturacionTrabajador.isEmpty()) {
            // Si no hay datos, imprime mensaje y no hace nada más
            System.out.println("No hay facturación para este usuario en el rango especificado.");
            return;
        }

        // Crea una nueva serie para los datos
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (Map<String, Object> fila : facturacionTrabajador) {
            String nombre = (String) fila.get("nombre");
            Double totalFacturado = (Double) fila.get("total_facturado");

            serie.getData().add(new XYChart.Data<>(nombre, totalFacturado));
        }

        // Añade la serie única al gráfico
        graficoFacturacionTrabajador.getData().add(serie);
    }



    public void llenarGraficoServiciosTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
        // Limpia los datos actuales del gráfico
        graficoServiciosTrabajador.getData().clear();

        // Obtén los servicios vendidos con el filtro correspondiente
        List<Map<String, Object>> serviciosTrabajador = Facturacion.serviciosPorTrabajador(idTrabajador, inicio, fin);

        if (serviciosTrabajador.isEmpty()) {
            // Si no hay datos, imprime mensaje y no hace nada más
            System.out.println("No hay servicios vendidos para este usuario en el rango especificado.");
            return;
        }

        // Crea una nueva serie para los datos
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (Map<String, Object> fila : serviciosTrabajador) {
            String nombre = (String) fila.get("nombre");
            Integer serviciosVendidos = (Integer) fila.get("numero_servicios");

            serie.getData().add(new XYChart.Data<>(nombre, serviciosVendidos));
        }

        // Añade la serie única al gráfico
        graficoServiciosTrabajador.getData().add(serie);
    }





    public void llenarGraficoProductosTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
        // Limpia los datos actuales del gráfico
        graficoProductosTrabajador.getData().clear();

        // Obtén los productos vendidos con el filtro correspondiente
        List<Map<String, Object>> productosTrabajador = Facturacion.productosPorTrabajador(idTrabajador, inicio, fin);

        if (productosTrabajador.isEmpty()) {
            // Si no hay datos, imprime mensaje y no hace nada más
            System.out.println("No hay productos vendidos para este usuario en el rango especificado.");
            return;
        }

        // Crea una nueva serie para los datos
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (Map<String, Object> fila : productosTrabajador) {
            String nombre = (String) fila.get("nombre");
            Integer productosVendidos = (Integer) fila.get("numero_productos");

            serie.getData().add(new XYChart.Data<>(nombre, productosVendidos));
        }

        // Añade la serie única al gráfico
        graficoProductosTrabajador.getData().add(serie);
    }

}





