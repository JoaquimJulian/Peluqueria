package application.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

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
import javafx.scene.control.TextFormatter;
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

public class stockProductosController {
	
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
    private ImageView basura;
    @FXML
	private Text nombreSesion;

    @FXML
	private Button restarstock;
    
	@FXML
	private TextField codigoBarras;
	@FXML
	private TextField nombreProducto;
	@FXML
	private Spinner<Integer> cantidadProducto;

	


	
	
    private Producto producto;
	

	
	private Main mainApp;  // Referencia a la aplicación principal

    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        initialize();
    }
    
    @FXML
    public void initialize() throws SQLException {
    	if (mainApp != null) {

        	// BOTONES HEADER
        	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
        	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
        	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
        	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
        	basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));
        	salir.setOnMouseClicked(event -> mainApp.mostrarVista("diaAdmin.fxml"));
        	
        	controlFormatoCodigo();
        	
        	codigoBarras.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    try {
                        rellenarDatosProducto(newValue);
                    } catch (NumberFormatException e) {
                        System.out.println("El código de barras debe ser un número válido.");
                    }
                } 
            });
        	
        	// Lógica del botón Restar stock
            restarstock.setOnAction(event -> {
                if (producto != null) {
                    int cantidadRestar = cantidadProducto.getValue();
                    if (cantidadRestar > 0) {
                        try {
							restarStock(producto, cantidadRestar);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    } else {
                        mostrarAlerta("Cantidad inválida", "La cantidad debe ser mayor que 0.");
                    }
                } else {
                    mostrarAlerta("Producto no encontrado", "No se encontró el producto con ese código de barras.");
                }
            });
        	
    	}
    	
    }

    private void controlFormatoCodigo() {
    	UnaryOperator<TextFormatter.Change> filtroCodigo = change -> {
            String newText = change.getControlNewText();
            
            // Verifica si el texto es numérico (solo dígitos)
            if (newText.matches("\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para stockProducto
        codigoBarras.setTextFormatter(new TextFormatter<>(filtroCodigo));
    }
    
    public Producto rellenarDatosProducto(String codigo_barras) {
        try {
            producto = Producto.buscarPorCodigoBarras(codigo_barras); // Asignar el producto encontrado a la variable miembro
            if (producto != null && producto.getActivo()) {
				nombreProducto.setText(producto.getNombre());
				SpinnerValueFactory.IntegerSpinnerValueFactory valoresSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, producto.getCantidad_en_stock(), 1);
				cantidadProducto.setValueFactory(valoresSpinner);
				return producto; 
			}else {
			    return null;
			}
				
            
		} catch (SQLException e) {
		    System.err.println("Error al consultar el producto: " + e.getMessage());  // Depurar error SQL
		    return null;
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
    
 // Método para restar el stock
    public void restarStock(Producto producto, int cantidadRestar) throws SQLException {
        // Asegurarse de que la cantidadRestar sea mayor que 0
        if (cantidadRestar <= 0) {
            mostrarAlerta("Cantidad inválida", "La cantidad debe ser mayor que 0.");
            return;
        }

        int stock = producto.getCantidad_en_stock() - cantidadRestar;  // Restar la cantidad seleccionada en lugar de 1

        // Verificar que no se quede con stock negativo
        if (stock < 0) {
            mostrarAlerta("Stock insuficiente", "No hay suficiente stock para realizar la operación.");
            return;
        }

        // Restar el stock en la base de datos
        if (Facturacion.restarStock(producto.getId(), stock)) {
            producto.setCantidad_en_stock(stock); // Actualizar el stock en el objeto Producto
        }

        // Verificar si el stock ha bajado del nivel de aviso
        boolean stockBajo = Producto.verificarAvisoStock(producto.getId(), producto.getCantidad_en_stock());
        System.out.println("Producto: " + producto.getNombre() + " - Stock bajo: " + stockBajo); // Depurar el valor de stockBajo

        if (stockBajo) {
            // Si el stock está por debajo del nivel de aviso, mostrar la alerta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("AVISO DE STOCK");
            alert.setHeaderText(null);
            alert.setContentText("El stock de " + producto.getNombre() + " ha bajado de su nivel mínimo que es: " + producto.getAviso_stock() + "\nStock actual: " + producto.getCantidad_en_stock());
            alert.showAndWait();
            mainApp.mostrarVista("productos.fxml");
        }
    }



    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
  
}