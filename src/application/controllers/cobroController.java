package application.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.application.Platform;
import application.models.Servicio;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import application.models.Cliente;  // Asegúrate de que Cliente esté correctamente importado
import application.models.Facturacion;
import application.models.Producto;
import application.models.Trabajador;

public class cobroController {
	
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

	// CobroController.java
	@FXML
	private TextField ClienteNombreField; 
	@FXML
	private TextField precioTotalText;
	@FXML 
	private CheckBox metodoEfectivo;
	@FXML 
	private CheckBox metodoTarjeta;
	@FXML 
	private CheckBox metodoBizum;
	@FXML 
	private ChoiceBox nombrePeluquero;
	@FXML 
	private Button CobrarTodo;
	@FXML 
	private Button anadirServicio;
	@FXML 
	private Button anadirProducto;
	@FXML
	private TextArea observaciones;
	
	// Tabla para agregar servicios 
	
	@FXML
	private Label textoSeleccionarServicios;
	@FXML
	private TextField buscadorServicio;
	@FXML
	private TableView<Servicio> serviciosCobrar;
	@FXML
	private TableColumn<Servicio, String> nombreServicioCobrar;
	@FXML
	private TableColumn<Servicio, String> observacionServicioCobrar;
	
	// Popup para añadir productos
	
	@FXML
	private AnchorPane popupAnadirProductos;
	@FXML
	private TextField codigoBarras;
	@FXML
	private ComboBox nombreProducto;
	@FXML
	private TextArea descripcionProducto;
	@FXML
	private TextField precioProductoIndividual;
	@FXML
	private TextField precioProductoTotal;
	@FXML
	private Spinner<Integer> cantidadProducto;
	@FXML
	private Button anadirProductoPopUp;
	
	private Producto producto;
	
	private Double precioTotalProductos = 0.0;
	
	private Integer cantidadProductosIguales = 0;
	
    private ObservableList<Producto> productosAnadidos = FXCollections.observableArrayList();

	
	// Tabla de productos y servicios añadidos
	
	@FXML
	private TableView<Producto> productosAnadidosTabla;
	@FXML
	private TableColumn<Producto, String> productosAnadidosColumna;
	@FXML
	private TableView<Servicio> serviciosAnadidosTabla;
	@FXML
	private TableColumn<Servicio, String> serviciosAnadidosColumna;
	
    private ObservableList<Servicio> serviciosAnadidos = FXCollections.observableArrayList();

	
    private double precioTotal;
	
	private Main mainApp;  // Referencia a la aplicación principal

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        cargarCliente();
        datosCliente();
        initialize();
    }
    
    @FXML
    public void initialize() {
    	if (mainApp != null) {	
    		Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
        	nombreSesion.setText(trabajadorLogueado.getNombre());
        	
        	if (!trabajadorLogueado.isEsAdministrador()) {
        		Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
        		ajustes.setImage(imagenCliente);
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	}else {
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
        	}
        	
        	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        	salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	
        	ObservableList<Trabajador> trabajadores = Trabajador.getTrabajadoresActivos();
        	    	
        	ObservableList<String> nombresTrabajadores = FXCollections.observableArrayList();

        	for (Trabajador trabajador : trabajadores) {
        		if (!trabajador.getNombre().equals("dreams")) {
            	    nombresTrabajadores.add(trabajador.getId() + " " + trabajador.getNombre());
        		}
        	    if (trabajador.getId() == trabajadorLogueado.getId()) {
        	    	nombrePeluquero.setValue(trabajador.getId() + " " + trabajador.getNombre());
        	    }
        	}

        	// Asignar la lista de nombres al ChoiceBox
        	nombrePeluquero.setItems(nombresTrabajadores);
        	
        	// LOGICA SERVICIOS
        	
        	anadirServicio.setOnMouseClicked(event -> {
				try {
					mostrarServicios();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
        	
        	serviciosAnadidosColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        	
        	serviciosAnadidosTabla.setOnMouseClicked(event -> {
        	    Servicio servicioSeleccionado = serviciosAnadidosTabla.getSelectionModel().getSelectedItem();
        	    if (servicioSeleccionado != null) {
        	        quitarServicioAnadido(servicioSeleccionado);
        	        restarPrecioServicio(servicioSeleccionado.getPrecio());
        	        Platform.runLater(() ->serviciosAnadidosTabla.getSelectionModel().clearSelection());
        	    }
        	});
        	
        	// LOGICA PRODUCTOS
        	
        	productosAnadidosColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        	
        	anadirProducto.setOnMouseClicked(event -> popupAnadirProductos.setVisible(true));
        	codigoBarras.textProperty().addListener((observable, oldValue, newValue) -> {
                // Si el código de barras no está vacío
                if (!newValue.isEmpty()) {
                    try {
                        // Llamar a la función para rellenar los datos del producto
                        rellenarDatosProducto(newValue);
                    } catch (NumberFormatException e) {
                        System.out.println("El código de barras debe ser un número válido.");
                    }
                } 
            });
        	anadirProductoPopUp.setOnMouseClicked(event -> anadirProducto(producto));
        		
        	productosAnadidosTabla.setOnMouseClicked(event -> {
        	    Producto productoSeleccionado = productosAnadidosTabla.getSelectionModel().getSelectedItem();
        	    if (productoSeleccionado != null) {
        	        quitarProductoAnadido(productoSeleccionado);
        	        restarPrecioProducto(productoSeleccionado.getPrecioVenta());
        	        Platform.runLater(() ->productosAnadidosTabla.getSelectionModel().clearSelection());
        	    }
        	});
        	
        	CobrarTodo.setOnMouseClicked(event -> {
        		   

        		try {
        		cobrarTodo();
        		mainApp.mostrarVista("clientes.fxml");
        		} catch (SQLException e) {
        		System.out.println("CATCH 1");  
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        		} catch (IOException e) {
        		System.out.println("CATCH 2");  
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        		}
        	});
    	}
    	
    }

    // Método para cargar datos del cliente
    public void cargarCliente() throws SQLException {
   	 	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
   	 	ClienteNombreField.setText(cliente.getNombre());
    }
    
    public Cliente datosCliente() {
   	 	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
    	return cliente;
    }
    
    
    // LOGICA SERVICIOS
    
    private ObservableList<Servicio> serviciosYaAnadidos = FXCollections.observableArrayList();
    private boolean servicioHaSidoAnadido = false;
    
    public void mostrarServicios() throws SQLException {
    	textoSeleccionarServicios.setVisible(true);
    	buscadorServicio.setVisible(true);
    	serviciosCobrar.setVisible(true);
    	
    	nombreServicioCobrar.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        observacionServicioCobrar.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    	
        ObservableList<Servicio> servicios = Servicio.getServicios();

    	serviciosCobrar.setItems(servicios);
    	
    	serviciosCobrar.setOnMouseClicked(event -> {
    		Servicio servicioSeleccionado = serviciosCobrar.getSelectionModel().getSelectedItem();
    		
    		if (servicioSeleccionado != null) {                
                servicioHaSidoAnadido = false;
                
            	 // Funcion añadir servicio a la tabla de servicios añadidos

                for (Servicio servicio : serviciosYaAnadidos) {
                	if (servicioSeleccionado == servicio && !serviciosYaAnadidos.isEmpty()) {
                		servicioHaSidoAnadido = true;
                	}
                }
                if (servicioHaSidoAnadido == false) {
                	anadirServicio(servicioSeleccionado);
                    sumarPrecioServicio(servicioSeleccionado.getPrecio());
                }
                Platform.runLater(() ->serviciosCobrar.getSelectionModel().clearSelection());
            }
    	});
    	
    }
    
    public void anadirServicio(Servicio servicio) {
        serviciosAnadidos.add(servicio);
        serviciosAnadidosTabla.setItems(serviciosAnadidos);
        serviciosYaAnadidos.add(servicio);
    }
    
    public void quitarServicioAnadido(Servicio servicio) {
    	serviciosAnadidos.remove(servicio);
    	serviciosYaAnadidos.remove(servicio);
    }
    
    
    public void sumarPrecioServicio(double precioServicio) {
    	precioTotal += precioServicio;
    	precioTotalText.setText(String.format("%.2f", precioTotal));
    }
    
    public void restarPrecioServicio(double precioServicio) {
    	precioTotal -= precioServicio;
    	precioTotalText.setText(String.format("%.2f", precioTotal));
    }
    
    // LOGICA PRODUCTOS
    
    public Producto rellenarDatosProducto(String codigo_barras) {
        try {
            producto = Producto.buscarPorCodigoBarras(codigo_barras);  // Asignar el producto encontrado a la variable miembro
            
            if (producto != null) {
                nombreProducto.setValue(producto.getNombre());
                descripcionProducto.setText(producto.getDescripcion());
                precioProductoIndividual.setText(String.valueOf(producto.getPrecioVenta()));
                precioTotalProductos = producto.getPrecioVenta();
                precioProductoTotal.setText(String.valueOf(precioTotalProductos));
                SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
                cantidadProducto.setValueFactory(valueFactory);
                
                cantidadProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
                	cantidadProductosIguales = newValue;
                    int cantidad = newValue;  
                    double total = producto.getPrecioVenta() * cantidad; 
                    precioTotalProductos = total;
                    precioProductoTotal.setText(String.valueOf(precioTotalProductos));  
                });
                
                return producto;
            } else {
                System.out.println("Producto no encontrado.");  // Si no se encuentra el producto
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el producto: " + e.getMessage());  // Depurar error SQL
            return null;
        }
    }
    
    public void anadirProducto(Producto producto) {
    	for (int i = 0; i<cantidadProducto.getValue(); i++) {
    		productosAnadidos.add(producto);
    	}
        productosAnadidosTabla.setItems(productosAnadidos);
        cantidadProductosIguales = 0;
        
        sumarPrecioProducto(precioTotalProductos);
        
        
        popupAnadirProductos.setVisible(false);
    }
    
    public void sumarPrecioProducto(double precioProducto) {
    	precioTotal += precioProducto;
    	precioTotalText.setText(String.format("%.2f", precioTotal));
    }
    
    public void restarPrecioProducto(double precioProducto) {
    	precioTotal -= precioProducto;
    	precioTotalText.setText(String.format("%.2f", precioTotal));
    }
    
    public void quitarProductoAnadido(Producto producto) {
    	productosAnadidos.remove(producto);
    }
    
    @FXML
    private void cobrarTodo() throws SQLException, IOException {
        try {
            // Obtener los datos del cliente y del trabajador
            Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
            Trabajador trabajador = Trabajador.getTrabajadorLogueado();
           


            // Obtener los datos del formulario
            String metodoPago = "efectivo";
            // Reemplazar la coma por punto antes de convertir el precio total
            String precioTotalStr = precioTotalText.getText().replace(",", ".");
            double montoTotal = Double.parseDouble(precioTotalStr); // Ahora debería funcionar correctamente
           
           
            // Crear la fecha actual
            java.sql.Date fecha = new java.sql.Date(System.currentTimeMillis());

            String observacion = observaciones.getText();;
           
            int idServicio = 0;
            String nombreServicio = "";

         // Insertar los servicios
            for (Servicio servicio : serviciosAnadidos) {
                int idServicio1 = servicio.getId();
                String nombreServicio1 = servicio.getNombre();

                System.out.println("Servicio añadido: " + nombreServicio1);

                // Inserción de cada servicio (uno por uno)
                Facturacion facturacionModel = new Facturacion(mainApp.getDatosCompartidos());
                boolean resultado = facturacionModel.insertarFactura(
                    cliente.getId(),
                    trabajador.getId(),
                    idServicio1, // Aquí se usa el id de cada servicio
                    0, // idProducto se pasa como 0, ya que el servicio no tiene producto asociado en este caso
                    nombreServicio1,
                    "", // nombreProducto vacío ya que no es necesario para el servicio
                    montoTotal, // Monto total
                    metodoPago,
                    fecha,
                    observacion
                );
               
                if (resultado) {
                    System.out.println("Factura para servicio " + nombreServicio1 + " creada con éxito");
                } else {
                    System.out.println("Hubo un error al crear la factura para el servicio " + nombreServicio1);
                }
               
            }
           
            int idProducto = 0;
            String nombreProducto = "";

         // Insertar los productos (si es necesario)
            for (Producto producto : productosAnadidos) {
                int idProducto1 = producto.getId();
                String nombreProducto1 = producto.getNombre();

                System.out.println("Producto añadido: " + nombreProducto1);

                // Inserción de cada producto
                Facturacion facturacionModel = new Facturacion(mainApp.getDatosCompartidos());
                boolean resultado = facturacionModel.insertarFactura(
                    cliente.getId(),
                    trabajador.getId(),
                    0, // idServicio se pasa como 0 ya que estamos trabajando con productos
                    idProducto1, // idProducto de cada producto
                    "", // nombreServicio vacío ya que no es necesario para el producto
                    nombreProducto1,
                    montoTotal, // Monto total
                    metodoPago,
                    fecha,
                    observacion
                );

                if (resultado) {
                    System.out.println("Factura para producto " + nombreProducto1 + " creada con éxito");
                } else {
                    System.out.println("Hubo un error al crear la factura para el producto " + nombreProducto1);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al procesar los datos numéricos: " + e.getMessage());
        }
       
       
    }
  
}
