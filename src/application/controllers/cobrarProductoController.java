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
import application.models.DatosCompartidos;
import application.models.Producto;
import application.models.Servicio;
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
	
	 private ObservableList<Servicio> serviciosAnadidos = FXCollections.observableArrayList();
	
	
	private Main mainApp;  // Referencia a la aplicación principal
	// private cobroController cobroController;

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        initialize();
    }
    
    @FXML
    public void initialize() {
        if (mainApp != null) {
        	Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
            nombreSesion.setText(trabajadorLogueado.getNombre());
            cerrar.setOnMouseClicked(event -> { Platform.exit(); });
            usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
            calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
            ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
            salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
            
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
            
            anadirProducto.setOnMouseClicked(event -> {
				try {
					anadirProducto();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
            
            if (mainApp.getDatosCompartidos() != null) {
                serviciosAnadidos.addAll((ObservableList<Servicio>) mainApp.getDatosCompartidos());
            }
        }
    }


 // Método que rellena los datos del producto
    public void rellenarDatosProducto(String codigo_barras) {
        try {
            Producto producto = Producto.buscarPorCodigoBarras(codigo_barras);  // Asignar el producto encontrado a la variable miembro
            
            if (producto != null) {
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

    @FXML
    public void anadirProducto() throws SQLException {
    	Producto producto = Producto.buscarPorCodigoBarras(codigo_barras.getText());
    	// Suponiendo que tienes la lista de servicios añadidos
        ObservableList<Servicio> serviciosActuales = FXCollections.observableArrayList(serviciosAnadidos);

        // Crea el contenedor con el producto y los servicios
        DatosCompartidos datos = new DatosCompartidos(producto, serviciosActuales);

        // Envía el contenedor al siguiente controlador
        mainApp.mostrarVista("cobro.fxml", datos);
    }






    
 
}
