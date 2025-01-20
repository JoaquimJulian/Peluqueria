package application.controllers;


import javafx.scene.control.Button;  // Importa la clase Button correcta
import java.sql.SQLException;


import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import application.models.Producto;
import application.models.Trabajador;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;  // Importante para MouseEvent


public class cobrarProductoController {
	
	// BOTONES HEADER
    @FXML
    private ImageView calendario;
    @FXML
    private ImageView ajustes;
    @FXML
    private ImageView cerrar;
    @FXML
    private ImageView cobrar;
    @FXML
    private ImageView usuarios;
    @FXML
    private ImageView salir;
    @FXML
    private ImageView ficha;
    @FXML
	private Text nombreSesion;

	 @FXML
	 private TextField codigo_barras;
	 @FXML
	 private TextField nombreProducto;
	 @FXML
	 private TextField descripcionProducto;
	 @FXML
	 private TextField precio_venta;
	 @FXML
	 private TextField cantidad_producto;
	 @FXML
	private Button anadirProducto;
	 
	 @FXML
	 private TableView<Producto> productosAnadidosTabla;
	 @FXML
	 private TableColumn<Producto, String> productosAnadidosColumna;

	 private ObservableList<Producto> productosAnadidos = FXCollections.observableArrayList();  // Lista de productos añadidos
	 private Producto producto;

	
	
	private Main mainApp;  // Referencia a la aplicación principal
	private cobroController cobroController;

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
      
        initialize();
    }
    
    @FXML
    public void initialize() {
    	
    
    	System.out.println("Inicializando controlador de cobrarProducto...");
        
        // Detectar cuando cambia el texto en el campo de código de barras
        codigo_barras.textProperty().addListener((observable, oldValue, newValue) -> {
            // Si el código de barras no está vacío
            if (!newValue.isEmpty()) {
                try {
                    // Llamar a la función para rellenar los datos del producto
                    rellenarDatosProducto(newValue);
                } catch (NumberFormatException e) {
                    System.out.println("El código de barras debe ser un número válido.");
                }
            } else {
                // Limpiar los campos si el código de barras está vacío
                nombreProducto.clear();
                descripcionProducto.clear();
                precio_venta.clear();
                cantidad_producto.clear();
            }
        });

        if (mainApp != null) {
            Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
            nombreSesion.setText(trabajadorLogueado.getNombre());
            cerrar.setOnMouseClicked(event -> { Platform.exit(); });
            usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
            calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
            ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
            salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        }
    }



 // Método que rellena los datos del producto
    public void rellenarDatosProducto(String codigo_barras) {
        try {
            System.out.println("Buscando producto con código de barras: " + codigo_barras);  // Depurar código de barras
            producto = Producto.buscarPorCodigoBarras(codigo_barras);  // Asignar el producto encontrado a la variable miembro
            
            if (producto != null) {
                System.out.println("Producto encontrado: " + producto.getNombre());
                System.out.println(producto);
                nombreProducto.setText(producto.getNombre());
                descripcionProducto.setText(producto.getDescripcion());
                precio_venta.setText(String.valueOf(producto.getPrecioVenta()));
                cantidad_producto.setText(String.valueOf(producto.getCantidad_en_stock()));
            } else {
                System.out.println("Producto no encontrado.");  // Si no se encuentra el producto
                nombreProducto.clear();
                descripcionProducto.clear();
                precio_venta.clear();
                cantidad_producto.clear();
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el producto: " + e.getMessage());  // Depurar error SQL
        }
    }


    

    
    // Método para añadir el producto a la lista
    public void anadirProductoALista() {
        if (cobroController != null && producto != null) {
            cobroController.anadirProducto(producto);  // Pasar el producto a cobroController
        } else {
            System.out.println("Error: cobroController o producto es nulo.");
        }
    }

    @FXML
    public void anadirProducto() {
    	System.out.println("Producto." + producto);
        // Si el producto ya ha sido recuperado y existe, lo añades al cobroController
        if (producto != null) {
            // Asegurarse de que el controlador de cobro está configurado y añadir el producto
            if (cobroController != null) {
                cobroController.anadirProducto(producto);  // Pasar el producto ya recuperado
            }

            // Limpiar los campos después de añadir el producto
            nombreProducto.clear();
            descripcionProducto.clear();
            precio_venta.clear();
            cantidad_producto.clear();

            // Cambiar a la vista de cobro (cobro.fxml)
            if (mainApp != null) {
                mainApp.mostrarVista("cobro.fxml");  // Asegúrate de que "mostrarVista" cargue la vista correcta
            }
        } else {
            System.out.println("No se ha encontrado un producto válido para añadir.");
        }
    }



    public void setCobroController(cobroController cobroController) {
        this.cobroController = cobroController;
        System.out.println("cobroController recibido en cobrarProductoController.");
    }





    
 
}
