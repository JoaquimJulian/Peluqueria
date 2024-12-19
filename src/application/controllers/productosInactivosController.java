package application.controllers;

import java.sql.SQLException;

import application.Main;
import application.models.Producto;
import application.models.Trabajador;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class productosInactivosController {
	// BOTONES HEADER
		@FXML
		private BorderPane panelPrincipal;
		@FXML
		private ImageView salir;
		@FXML
		private ImageView calendario;
		@FXML
		private ImageView ajustes;
		@FXML
		private ImageView cobrar;
		@FXML
		private ImageView usuarios;
		@FXML
		private ImageView cerrar;
		@FXML
		private Text nombreSesion;
		@FXML
	    private ImageView ficha;
		
		// CONTROLES GENERICOS PARA CRUD
		@FXML
		private TextField barraBusqueda;
		@FXML
		private Button btnEliminar;
		@FXML
		private Button btnActivar;
		@FXML
		private Button activos;
		
			
		@FXML
	    private TableView<Producto> tablaProductos;
	    @FXML
	    private TableColumn<Producto, String> columnaNombre;
	    @FXML
	    private TableColumn<Producto, String> columnaDescripcion;
	    @FXML
	    private TableColumn<Producto, Double> columnaPrecioVenta; 
	    @FXML
	    private TableColumn<Producto, Double> columnaCoste; 
	    @FXML
	    private TableColumn<Producto, Integer> columnacantidad_en_stock;
	    @FXML
	    private TableColumn<Producto, Integer> columnacodigo_barras;
	    @FXML
	    private Button crearProductoButton;
	  

	    private Main mainApp; // Referencia a Main
	    
	    // Este método se llamará desde Main para establecer la referencia
	       public void setMainApp(Main mainApp) {
	           this.mainApp = mainApp;
	       }
	    @FXML
	    public void initialize() throws SQLException {
	    	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
	    	nombreSesion.setText(trabajadorLogueado.getNombre());
	    
		    Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que carguen todos los componentes, la applicacion pone el focus del usuario en el panel principal
		    cerrar.setOnMouseClicked(event -> Platform.exit());
	        salir.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
	        ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
			calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
	    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
	    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
			
			btnActivar.setDisable(true);
			tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //listener que detecta cuando se hace click en una fila de la tabla para asi activar el boton de editar
		        btnActivar.setDisable(false);
		        btnActivar.setOnMouseClicked(event -> {
		    		try {
		    			activarProducto();
		    		} catch (SQLException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    	});
		    });
			
			btnEliminar.setDisable(true);
			tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //listener que detecta cuando se hace click en una fila de la tabla para asi activar el boton de editar
		        btnEliminar.setDisable(false);
		        btnEliminar.setOnMouseClicked(event -> {
		    		try {
		    			eliminarProducto();
		    		} catch (SQLException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    	});
		    });
			
			activos.setOnMouseClicked(event -> mainApp.mostrarVista("productos.fxml"));
			
			
	        // Configurar las columnas
	        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
	        columnaPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
	        columnaCoste.setCellValueFactory(new PropertyValueFactory<>("precioCosto"));
	        columnacantidad_en_stock.setCellValueFactory(new PropertyValueFactory<>("cantidad_en_stock"));
	        columnacodigo_barras.setCellValueFactory(new PropertyValueFactory<>("codigo_barras"));

	     // Cargar los datos
	        cargarProductos();
	    }
	    
	    private void cargarProductos() throws SQLException {
	        ObservableList<Producto> productos = Producto.getProductosInactivos();
	        tablaProductos.setItems(productos);
	    }
	    
	    private void activarProducto() throws SQLException {
	        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
	        
	        
	        if (productoSeleccionado != null) {
	        	Producto.activarProducto(productoSeleccionado.getId());
	    	} else {
		        // Mostrar alerta indicando que no se ha seleccionado ningún producto
		        Alert alerta = new Alert(Alert.AlertType.WARNING);
		        alerta.setTitle("Advertencia");
		        alerta.setHeaderText(null);
		        alerta.setContentText("Por favor, selecciona un producto de la tabla para activar.");
		        alerta.showAndWait();
	    	}
	        
	        
	        cargarProductos();
	       
	    }
	    private void eliminarProducto() throws SQLException {
	        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
	        
	        
	        if (productoSeleccionado != null) {
	        	Producto.eliminarProducto(productoSeleccionado.getId());
	    	} else {
		        // Mostrar alerta indicando que no se ha seleccionado ningún producto
		        Alert alerta = new Alert(Alert.AlertType.WARNING);
		        alerta.setTitle("Advertencia");
		        alerta.setHeaderText(null);
		        alerta.setContentText("Por favor, selecciona un producto de la tabla para eliminar.");
		        alerta.showAndWait();
	    	}
	        
	        
	        cargarProductos();
	       
	    }
}
