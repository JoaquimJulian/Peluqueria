package application.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.application.Platform;
import application.models.Servicio;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
import application.models.databaseConection;

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
    private ImageView basura;
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
	private TextField efectivo;
	@FXML 
	private TextField bizum;
	@FXML 
	private TextField tarjeta;
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
	@FXML
	private Button salirPopUp;
	


	
	// Tabla de productos y servicios añadidos
	
	@FXML
	private TableView<Producto> productosAnadidosTabla;
	@FXML
	private TableColumn<Producto, String> productosAnadidosColumna;
	@FXML
	private TableView<Servicio> serviciosAnadidosTabla;
	@FXML
	private TableColumn<Servicio, String> serviciosAnadidosColumna;
	
	// OBJETOS Y VARIABLES DE SERVICIOS
	
    private ObservableList<Servicio> serviciosAnadidos = FXCollections.observableArrayList();
    
    private ObservableList<Servicio> serviciosYaAnadidos = FXCollections.observableArrayList();
    
    private boolean servicioHaSidoAnadido = false;
    
    // OBJETOS Y VARIABLES DE PRODUCTOS
    
    private Producto producto;
	
	private Double precioTotalProductos = 0.0;
	
	private Integer cantidadProductosIguales = 0;
	
    private ObservableList<Producto> productosAnadidos = FXCollections.observableArrayList();

	
    private double precioTotal;
	
	private Main mainApp;  // Referencia a la aplicación principal

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        datosCliente();
        initialize();
    }
    
    @FXML
    public void initialize() throws SQLException {
    	if (mainApp != null) {
    		
    		// NOMBRE DEL CLIENTE EN EL TEXT FIELD
    		
    		ClienteNombreField.setText(datosCliente().getNombre());
    		
    		
    		// LOGICA TRABAJADOR
    		
    		Trabajador trabajadorLogueado = Trabajador.getTrabajadorLogueado();
        	nombreSesion.setText(trabajadorLogueado.getNombre());
        	
        	if (!trabajadorLogueado.isEsAdministrador()) {
        		Image imagenCliente = new Image(getClass().getResource("/application/images/clientes.png").toExternalForm());
        		ajustes.setImage(imagenCliente);
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	}else {
        		ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
        	}
        	
        	
        	// LOGICA CHOICEBOX TRABAJADOR
        	
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

        	nombrePeluquero.setItems(nombresTrabajadores);
        	
        	
        	// BOTONES HEADER
        	
        	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        	salir.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
        	basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));
        	
        	
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
        	
        	anadirProducto.setOnMouseClicked(event -> {
        	    popupAnadirProductos.setVisible(true);
        	    codigoBarras.requestFocus();
        	});
        	
        	productosAnadidosColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        	
        	
        	codigoBarras.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    try {
                        rellenarDatosProducto(newValue);
                    } catch (NumberFormatException e) {
                        System.out.println("El código de barras debe ser un número válido.");
                    }
                } 
            });
        	
        	anadirProductoPopUp.setOnMouseClicked(event -> anadirProducto(producto));
        	salirPopUp.setOnMouseClicked(event -> popupAnadirProductos.setVisible(false));
        		
        	productosAnadidosTabla.setOnMouseClicked(event -> {
        	    Producto productoSeleccionado = productosAnadidosTabla.getSelectionModel().getSelectedItem();
        	    if (productoSeleccionado != null) {
        	        quitarProductoAnadido(productoSeleccionado);
        	        restarPrecioProducto(productoSeleccionado.getPrecioVenta());
        	        Platform.runLater(() ->productosAnadidosTabla.getSelectionModel().clearSelection());
        	    }
        	});
        	
        	
        	// LOGICA DE COBRO
        	
        	CobrarTodo.setOnMouseClicked(event -> {
        		   

        		try {
	        		cobrarTodo();
	        		for (Producto producto : productosAnadidos) {
	        			restarStock(producto);
	        		}
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

    
    // FUNCION QUE RECOGE Y DEVUELVE LOS DATOS DEL CLIENTE
    
    public Cliente datosCliente() {
   	 	Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
    	return cliente;
    }
    
    
    // LOGICA SERVICIOS
    
    public void mostrarServicios() throws SQLException {
    	textoSeleccionarServicios.setVisible(true);
    	buscadorServicio.setVisible(true);
    	serviciosCobrar.setVisible(true);
    	
    	nombreServicioCobrar.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        observacionServicioCobrar.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        ObservableList<Servicio> servicios = Servicio.getServiciosActivos();
        servicios.removeIf(servicio -> servicio.getId() == 0);
        ObservableList<Servicio> filtroBusqueda = FXCollections.observableArrayList(servicios);

    	serviciosCobrar.setItems(filtroBusqueda);
    	
    	buscadorServicio.textProperty().addListener((observable, oldValue, newValue) -> {
            filtroBusqueda.clear();
            for (Servicio servicio : servicios) {
            	if (servicio.getNombre().toLowerCase().contains(newValue.toLowerCase()) || servicio.getDescripcion().toLowerCase().contains(newValue.toLowerCase())) {
            		filtroBusqueda.add(servicio);
            	}
            }
        });

    	
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
            producto = Producto.buscarPorCodigoBarras(codigo_barras); // Asignar el producto encontrado a la variable miembro
            if (producto != null && producto.getActivo()) {
				nombreProducto.setValue(producto.getNombre());
				descripcionProducto.setText(producto.getDescripcion());
				precioProductoIndividual.setText(String.valueOf(producto.getPrecioVenta()));
				precioTotalProductos = producto.getPrecioVenta();
				precioProductoTotal.setText(String.valueOf(precioTotalProductos));
				SpinnerValueFactory.IntegerSpinnerValueFactory valoresSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, producto.getCantidad_en_stock(), 1);
				cantidadProducto.setValueFactory(valoresSpinner);
				
				cantidadProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
					cantidadProductosIguales = newValue;
				    int cantidad = newValue;  
				    double total = producto.getPrecioVenta() * cantidad; 
				    precioTotalProductos = total;
				    precioProductoTotal.setText(String.valueOf(precioTotalProductos));  
				});
				
				return producto; 
			} else {
			    return null;
			}
            
		} catch (SQLException e) {
		    System.err.println("Error al consultar el producto: " + e.getMessage());  // Depurar error SQL
		    return null;
		}
    }
    
    public void anadirProducto(Producto producto) {
    	if (codigoBarras.getText() != null && codigoBarras.getText() != "" && producto != null) {
    		if (producto.getCantidad_en_stock() < 1) {
        		Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error de cobro");
                alert.setHeaderText(null);
                alert.setContentText("No hay stock del producto: " + producto.getNombre());
                alert.showAndWait();
                
                codigoBarras.setText(null);
                nombreProducto.setValue(null);
    			descripcionProducto.setText(null);
    			precioProductoIndividual.setText(null);
    			precioProductoTotal.setText(null);
        	} else if (!producto.getActivo()) {
        		Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error de cobro");
                alert.setHeaderText(null);
                alert.setContentText("Este producto esta inactivo");
                alert.showAndWait();
                
                codigoBarras.setText(null);
                nombreProducto.setValue(null);
    			descripcionProducto.setText(null);
    			precioProductoIndividual.setText(null);
    			precioProductoTotal.setText(null);
        	} else {
        		if (cantidadProducto.getValue() > 0) {
        			for (int i = 0; i<cantidadProducto.getValue(); i++) {
    	    			productosAnadidos.add(producto);
    				}
    			    productosAnadidosTabla.setItems(productosAnadidos);
    			    cantidadProductosIguales = 0;
    			    
    			    sumarPrecioProducto(precioTotalProductos);
    			    
    			    popupAnadirProductos.setVisible(false);
    			    
    			    codigoBarras.setText(null);
    	            nombreProducto.setValue(null);
    				descripcionProducto.setText(null);
    				precioProductoIndividual.setText(null);
    				precioProductoTotal.setText(null);
        		} else {
        			Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error de cobro");
                    alert.setHeaderText(null);
                    alert.setContentText("La cantidad de unidades no puede ser 0");
                    alert.showAndWait();
        		}	
        	}
    	}
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
    
    
    // LOGICA COBRAR
    
    @FXML
    private void cobrarTodo() throws SQLException, IOException {
        try {
            // Obtener el monto total
            String precioTotalStr = precioTotalText.getText().replace(",", ".");
            double montoTotal = Double.parseDouble(precioTotalStr);

            // Obtener los valores de los métodos de pago
            double montoEfectivo = parseDoubleFromTextField(efectivo);
            double montoTarjeta = parseDoubleFromTextField(tarjeta);
            double montoBizum = parseDoubleFromTextField(bizum);

            // Verificar que la suma de los métodos de pago no supere el monto total
            double sumaPagos = montoEfectivo + montoTarjeta + montoBizum;
            if (sumaPagos > montoTotal) {  // Verifica si la suma de los pagos supera el monto total
                // Mostrar una alerta indicando que la suma no puede superar el monto total
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error de cobro");
                alert.setHeaderText(null);
                alert.setContentText("La suma de los pagos no puede superar el monto total.");
                alert.showAndWait();
                return; // Detener el proceso y no continuar con el cobro
            }

            // Verificar que la suma de los pagos coincida con el monto total
            if (Math.abs(sumaPagos - montoTotal) > 0.01) {  // Permite un margen pequeño por errores de redondeo
            	// Mostrar una alerta indicando que la suma no puede superar el monto total
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error de cobro");
                alert.setHeaderText(null);
                alert.setContentText("La suma de los pagos no puede estar por debajo del monto total.");
                alert.showAndWait();
                return; // Detener el proceso y no continuar con el cobro
            }
            

            // Continuar con el flujo de inserción (si la suma es correcta)
            Cliente cliente = (Cliente) mainApp.getDatosCompartidos();
            System.out.println(nombrePeluquero.getValue());
            String peluqueroSeleccionado = (String) nombrePeluquero.getValue(); // Esto devuelve "1 Carmen"
            String idPeluqueroString = peluqueroSeleccionado.split(" ")[0]; 
            int idPeluquero = Integer.parseInt(idPeluqueroString);
            System.out.println(idPeluquero);
            java.sql.Date fecha = new java.sql.Date(System.currentTimeMillis());
            Time hora = new Time(System.currentTimeMillis());
            String observacion = observaciones.getText();

            // Insertar factura para los servicios
            for (Servicio servicio : serviciosAnadidos) {
                Facturacion facturacionModel = new Facturacion(mainApp.getDatosCompartidos());
                facturacionModel.insertarFactura(
                    cliente.getId(),
                    idPeluquero,
                    servicio.getId(),
                    0,
                    servicio.getNombre(),
                    "",
                    montoTotal,
                    montoEfectivo,
                    montoTarjeta,
                    montoBizum,
                    fecha,
                    hora,
                    observacion
                );
            }

            // Insertar factura para los productos
            for (Producto producto : productosAnadidos) {
                Facturacion facturacionModel = new Facturacion(mainApp.getDatosCompartidos());
                facturacionModel.insertarFactura(
                    cliente.getId(),
                    idPeluquero,
                    0,
                    producto.getId(),
                    "",
                    producto.getNombre(),
                    montoTotal,
                    montoEfectivo,
                    montoTarjeta,
                    montoBizum,
                    fecha,
                    hora,
                    observacion
                );
                // Restar el stock del producto
                restarStock(producto);
            }

            // Verificar el stock solo después de procesar todos los productos
            for (Producto producto : productosAnadidos) {
                boolean stockBajo = Producto.verificarAvisoStock(producto.getId(), producto.getCantidad_en_stock());
                System.out.println("Producto: " + producto.getNombre() + " - Stock bajo: " + stockBajo); // Depurar el valor de stockBajo

                if (stockBajo) {
                    // Si el stock está por debajo del nivel de aviso, mostrar la alerta
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("AVISO DE STOCK");
                    alert.setHeaderText(null);
                    alert.setContentText("El stock de " + producto.getNombre() + " ha bajado de su nivel mínimo que es: " + producto.getAviso_stock() + " \n Stock actual: " + producto.getCantidad_en_stock());
                    alert.showAndWait();
                    break; // Salir del bucle después de mostrar la alerta
                }
            }

     
            
            mainApp.mostrarVista("clientes.fxml", true);
        } catch (NumberFormatException e) {
            System.out.println("Error al procesar los datos numéricos: " + e.getMessage());
        }
    }


    private double parseDoubleFromTextField(TextField textField) {
        try {
            String text = textField.getText().trim().replace(",", ".");
            return text.isEmpty() ? 0 : Double.parseDouble(text);
        } catch (NumberFormatException e) {
            System.out.println("Valor no válido en campo: " + textField.getPromptText());
            return 0;
        }
    }
    
    
    // LOGICA STOCK
    
    public void restarStock(Producto producto) throws SQLException {
		int stock = producto.getCantidad_en_stock() - 1;
		if (Facturacion.restarStock(producto.getId(),stock)) {
			producto.setCantidad_en_stock(stock);
		}
    }
    
    
  
}
