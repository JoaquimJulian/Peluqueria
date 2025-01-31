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
    @FXML
	private Label facturacionTotalLabel; 
    @FXML
	private Label facturacionEfectivoLabel;
    @FXML
	private Label facturacionTarjetaLabel;
    @FXML
	private Label facturacionBizumLabel;
    
	
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
            btnDatosGenerales.setOnAction(event -> {
                mostrarDatosGenerales();
                mostrarFacturacionTotal();
                mostrarFacturacionEfectivo();
                mostrarFacturacionTarjeta();
                mostrarFacturacionBizum();
            });



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
    	mostrarFacturacionEfectivo();
        mostrarFacturacionTarjeta();
        mostrarFacturacionBizum();

        // Configurar el evento del botón "Filtrar"
        btnFiltrar.setOnAction(event -> { 
	        aplicarFiltro();  
	        mostrarFacturacionTotal();
	        mostrarFacturacionEfectivo();
	        mostrarFacturacionTarjeta();
	        mostrarFacturacionBizum();
        });
    }



    private void mostrarDatosGenerales() {
        try {
            java.sql.Date inicio = java.sql.Date.valueOf(fechaInicio.getValue());
            java.sql.Date fin = java.sql.Date.valueOf(fechaFin.getValue());

            if (inicio.after(fin)) {
            	Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error en las fechas");
                alert.setHeaderText(null);
                alert.setContentText("La fecha de inicio no puede ser posterior a la fecha de fin.");
                alert.showAndWait();
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
            
            if (inicio.after(fin)) {
            	Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error en las fechas");
                alert.setHeaderText(null);
                alert.setContentText("La fecha de inicio no puede ser posterior a la fecha de fin.");
                alert.showAndWait();
            } else if (comboTrabajadores.getValue() == null) {
            	Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo vacio");
                alert.setHeaderText(null);
                alert.setContentText("Selecciona un trabajador para filtrar");
                alert.showAndWait();
            }else {
            	String peluqueroSeleccionado = (String) comboTrabajadores.getValue(); 
                String idPeluqueroString = peluqueroSeleccionado.split(" ")[0]; 
                int idPeluquero = Integer.parseInt(idPeluqueroString);
                
                llenarGraficoFacturacionTrabajador(idPeluquero, inicio, fin);
                llenarGraficoProductosTrabajador(idPeluquero, inicio, fin);
                llenarGraficoServiciosTrabajador(idPeluquero, inicio, fin);
                
            }
           
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
    
   

    public void llenarGraficoFacturacionTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) throws SQLException {
	     graficoFacturacionTrabajador.getData().clear();
	
	     List<Map<String, Object>> facturacionPorTrabajador = Facturacion.sumaTotalFacturacion(inicio, fin, idTrabajador);
	
	     XYChart.Series<String, Number> serie = new XYChart.Series<>();
	     for (Map<String, Object> fila : facturacionPorTrabajador) {
	         String nombre = (String) fila.get("nombre");
	         Double totalFacturado = (Double) fila.get("facturacion_total");
	
	         XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, totalFacturado);
	         // Agregar el número encima de la barra
	         Label label = new Label(String.valueOf(totalFacturado));
	         label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
	
	         // Ajustar la posición del número al centro superior de la barra
	         data.nodeProperty().addListener((obs, oldNode, newNode) -> {
	             if (newNode != null) {
	                 StackPane parent = (StackPane) newNode;
	                 parent.getChildren().add(label);
	                 Platform.runLater(() -> {
	                     label.setLayoutY(label.getLayoutY() - 20); // Ajusta la posición vertical del número
	                 });
	             }
	         });
	
	         serie.getData().add(data);
	     }
	
	     graficoFacturacionTrabajador.getData().add(serie);
	 }
	
	public void llenarGraficoServiciosTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
	     graficoServiciosTrabajador.getData().clear();
	
	     List<Map<String, Object>> serviciosTrabajador = Facturacion.serviciosPorTrabajador(idTrabajador, inicio, fin);
	
	     XYChart.Series<String, Number> serie = new XYChart.Series<>();
	     for (Map<String, Object> fila : serviciosTrabajador) {
	         String nombre = (String) fila.get("nombre");
	         Integer serviciosVendidos = (Integer) fila.get("numero_servicios");
	
	         XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, serviciosVendidos);
	         // Agregar el número encima de la barra
	         Label label = new Label(String.valueOf(serviciosVendidos));
	         label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
	
	         data.nodeProperty().addListener((obs, oldNode, newNode) -> {
	             if (newNode != null) {
	                 StackPane parent = (StackPane) newNode;
	                 parent.getChildren().add(label);
	                 Platform.runLater(() -> {
	                     label.setLayoutY(label.getLayoutY() - 20);
	                 });
	             }
	         });
	
	         serie.getData().add(data);
	     }
	
	     graficoServiciosTrabajador.getData().add(serie);
	 }
	
	public void llenarGraficoProductosTrabajador(Integer idTrabajador, java.sql.Date inicio, java.sql.Date fin) {
	     graficoProductosTrabajador.getData().clear();
	
	     List<Map<String, Object>> productosTrabajador = Facturacion.productosPorTrabajador(idTrabajador, inicio, fin);
	
	     XYChart.Series<String, Number> serie = new XYChart.Series<>();
	     for (Map<String, Object> fila : productosTrabajador) {
	         String nombre = (String) fila.get("nombre");
	         Integer productosVendidos = (Integer) fila.get("numero_productos");
	
	         XYChart.Data<String, Number> data = new XYChart.Data<>(nombre, productosVendidos);
	         // Agregar el número encima de la barra
	         Label label = new Label(String.valueOf(productosVendidos));
	         label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
	
	         data.nodeProperty().addListener((obs, oldNode, newNode) -> {
	             if (newNode != null) {
	                 StackPane parent = (StackPane) newNode;
	                 parent.getChildren().add(label);
	                 Platform.runLater(() -> {
	                     label.setLayoutY(label.getLayoutY() - 20);
	                 });
	             }
	         });
	
	         serie.getData().add(data);
	     }
	
	     graficoProductosTrabajador.getData().add(serie);
	 }

	private void mostrarFacturacionTotal() {
	     try {
	         java.sql.Date inicio = java.sql.Date.valueOf(fechaInicio.getValue());
	         java.sql.Date fin = java.sql.Date.valueOf(fechaFin.getValue());
	         
	         // Verifica si se seleccionó un trabajador
	         String peluqueroSeleccionado = (String) comboTrabajadores.getValue();
	         Integer idPeluquero = null;
	         
	         if (peluqueroSeleccionado != null) {
	             String idPeluqueroString = peluqueroSeleccionado.split(" ")[0];
	             idPeluquero = Integer.parseInt(idPeluqueroString);
	         }
	         
	         // Obtén la facturación total desde la base de datos
	         List<Map<String, Object>> facturacionPorTrabajador = Facturacion.sumaTotalFacturacion(inicio, fin, idPeluquero);
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

	
	private void mostrarFacturacionEfectivo() {
	    try {
	        java.sql.Date inicio = java.sql.Date.valueOf(fechaInicio.getValue());
	        java.sql.Date fin = java.sql.Date.valueOf(fechaFin.getValue());

	        // Verifica si se seleccionó un trabajador
	        String peluqueroSeleccionado = (String) comboTrabajadores.getValue();
	        Integer idPeluquero = null;

	        if (peluqueroSeleccionado != null) {
	            String idPeluqueroString = peluqueroSeleccionado.split(" ")[0];
	            idPeluquero = Integer.parseInt(idPeluqueroString);
	        }

	        // Obtén la facturación total desde la base de datos
	        double facturacionTotal = Facturacion.facturacionEfectivo(idPeluquero, inicio, fin);


	        // Actualiza la etiqueta en la interfaz
	        facturacionEfectivoLabel.setText(String.format("Facturación Efectivo: %.2f €", facturacionTotal));

	    } catch (Exception e) {
	        e.printStackTrace();
	        facturacionEfectivoLabel.setText("Error al calcular la facturación en efectivo.");
	    }
	}
	
	private void mostrarFacturacionTarjeta() {
	    try {
	        java.sql.Date inicio = java.sql.Date.valueOf(fechaInicio.getValue());
	        java.sql.Date fin = java.sql.Date.valueOf(fechaFin.getValue());

	        // Verifica si se seleccionó un trabajador
	        String peluqueroSeleccionado = (String) comboTrabajadores.getValue();
	        Integer idPeluquero = null;

	        if (peluqueroSeleccionado != null) {
	            String idPeluqueroString = peluqueroSeleccionado.split(" ")[0];
	            idPeluquero = Integer.parseInt(idPeluqueroString);
	        }

	        // Obtén la facturación total desde la base de datos
	        double facturacionTotal = Facturacion.facturacionTarjeta(idPeluquero, inicio, fin);


	        // Actualiza la etiqueta en la interfaz
	        facturacionTarjetaLabel.setText(String.format("Facturación Tarjeta: %.2f €", facturacionTotal));

	    } catch (Exception e) {
	        e.printStackTrace();
	        facturacionTarjetaLabel.setText("Error al calcular la facturación con tarjeta.");
	    }
	}

	private void mostrarFacturacionBizum() {
	    try {
	        java.sql.Date inicio = java.sql.Date.valueOf(fechaInicio.getValue());
	        java.sql.Date fin = java.sql.Date.valueOf(fechaFin.getValue());

	        // Verifica si se seleccionó un trabajador
	        String peluqueroSeleccionado = (String) comboTrabajadores.getValue();
	        Integer idPeluquero = null;

	        if (peluqueroSeleccionado != null) {
	            String idPeluqueroString = peluqueroSeleccionado.split(" ")[0];
	            idPeluquero = Integer.parseInt(idPeluqueroString);
	        }

	        // Obtén la facturación total desde la base de datos
	        double facturacionTotal = Facturacion.facturacionBizum(idPeluquero, inicio, fin);


	        // Actualiza la etiqueta en la interfaz
	        facturacionBizumLabel.setText(String.format("Facturación Bizum: %.2f €", facturacionTotal));

	    } catch (Exception e) {
	        e.printStackTrace();
	        facturacionBizumLabel.setText("Error al calcular la facturación con bizum.");
	    }
	}
	
}





