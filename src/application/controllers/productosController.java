package application.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import application.Main;
import application.models.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class productosController {

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
	
	// CONTROLES GENERICOS PARA CRUD
	@FXML
	private TextField barraBusqueda;
	@FXML
	private Button btnCrear;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnDesactivar;
	@FXML
	private Button inactivos;
	
		
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
    
    Platform.runLater(() -> panelPrincipal.requestFocus()); //despues de que carguen todos los componentes, la applicacion pone el focus del usuario en el panel principal
	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
	btnCrear.setOnMouseClicked(event -> mainApp.mostrarVista("crearProductos.fxml"));
	cerrar.setOnMouseClicked(event -> { Platform.exit(); }); //cerrar aplicacion cuando pulsar boton cerrar
	salir.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
	
	btnEditar.setDisable(true);
	tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //listener que detecta cuando se hace click en una fila de la tabla para asi activar el boton de editar
        btnEditar.setDisable(false);
        btnEditar.setOnAction(event -> abrirVistaEdicion());
    });
	
	btnDesactivar.setOnMouseClicked(event -> {
		try {
			desactivarProducto();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
	inactivos.setOnMouseClicked(event -> mainApp.mostrarVista("productosInactivos.fxml"));
			
		
		
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
        ObservableList<Producto> productos = Producto.getProductosActivos();
        tablaProductos.setItems(productos);
    }
    
    @FXML
    private void abrirVistaEdicion() {
        // Obtener la fila seleccionada
        Producto Productoseleccionado = tablaProductos.getSelectionModel().getSelectedItem();
       
        mainApp.mostrarVista("editarProductos.fxml", Productoseleccionado);  // Método para abrir la vista de edición en Main
        
    }
    
    
    private void desactivarProducto() throws SQLException {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        
        
        if (productoSeleccionado != null) {
        	Producto.desactivarProducto(productoSeleccionado.getId());
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
