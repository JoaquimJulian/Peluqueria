package application.controllers;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.util.function.UnaryOperator;

import application.Main;
import application.models.Producto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;


public class editarProductosController {
    
    // BOTONES HEADER
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
    private ImageView basura;
    @FXML
    private ImageView cerrar;
    @FXML
    private ImageView ficha;
    
    @FXML
    private TextField nombreProducto;
    @FXML
    private TextField PrecioVenta; // Cambiado a TextField
    @FXML
    private TextField PrecioCosto;  // Cambié a Double para que coincida con tu modelo
    @FXML
    private TextField descripcionProducto;
    @FXML
    private TextField cantidad_en_stock;
    @FXML
    private TextField aviso_stock;
    @FXML
    private TextField codigo_barras;
    @FXML
    private Button editarproducto;
    
    private Main mainApp; // Referencia a Main
    
    // Este método se llamará desde Main para establecer la referencia
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        cargarDatosProducto();
    }
    
    public void initialize() {
    	cerrar.setOnMouseClicked(event -> { Platform.exit(); });
    	ficha.setOnMouseClicked(event -> mainApp.mostrarVista("fichaTrabajador.fxml"));
    	usuarios.setOnMouseClicked(event -> mainApp.mostrarVista("LogIn.fxml"));
    	calendario.setOnMouseClicked(event -> mainApp.mostrarVista("Agenda.fxml"));
    	cobrar.setOnMouseClicked(event -> mainApp.mostrarVista("clientes.fxml"));
    	salir.setOnMouseClicked(event -> mainApp.mostrarVista("productos.fxml"));
    	ajustes.setOnMouseClicked(event -> mainApp.mostrarVista("inventario.fxml"));
    	basura.setOnMouseClicked(event -> mainApp.mostrarVista("stockProductos.fxml"));

    	controlFormatoPrecio();
    	controlFormatoStock();
    	controlFormatoCodigo();
    	controlFormatoStock2();
    	
        editarproducto.setOnAction(event -> {
            editarProducto();
            mainApp.mostrarVista("Productos.fxml");
        });
    }
    
    // Método para cargar los datos del Producto en los campos
    @FXML
    public void cargarDatosProducto() {
        Producto producto = (Producto) mainApp.getDatosCompartidos(); // Obtener el producto a editar
        
        // Cargar los datos en los campos
        nombreProducto.setText(producto.getNombre());
        descripcionProducto.setText(producto.getDescripcion());
        PrecioVenta.setText(String.valueOf(producto.getPrecioVenta())); // Cambiado a setText()
        PrecioCosto.setText(String.valueOf(producto.getPrecioCosto())); // Cambiado a setText()
        cantidad_en_stock.setText(String.valueOf(producto.getCantidad_en_stock())); // Cargar la cantidad en stock
        codigo_barras.setText(String.valueOf(producto.getCodigo_barras()));
        aviso_stock.setText(String.valueOf(producto.getAviso_stock()));
    }
    
    public void editarProducto() {
        Producto producto = (Producto) mainApp.getDatosCompartidos(); // Obtener el producto a editar
        
        // Obtener los valores de los campos
        String nombre = nombreProducto.getText();
        
        // Convertir los valores de texto a Double
        double precioVenta = Double.parseDouble(PrecioVenta.getText());
        double precioCosto = Double.parseDouble(PrecioCosto.getText());
        
        String descripcion = descripcionProducto.getText();
        int cantidad = Integer.parseInt(cantidad_en_stock.getText()); // Convertir a int
        int aviso = Integer.parseInt(aviso_stock.getText()); // Convertir a int
        String codigoBarrasTexto = codigo_barras.getText();
        codigoBarrasTexto = codigoBarrasTexto.replaceAll("^\\^", "");
        Long codigoBarras = Long.parseLong(codigoBarrasTexto);

        // Llamar al método de edición en el modelo
        Producto.editarproducto(producto.getId(), nombre, descripcion, precioVenta, precioCosto, cantidad, codigoBarras, aviso);
    }
    
    private void controlFormatoPrecio() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String newText = change.getControlNewText();

            // Permite solo números y un único punto decimal
            if (newText.matches("\\d*\\.?\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para cada TextField
        PrecioVenta.setTextFormatter(new TextFormatter<>(filtro));
        PrecioCosto.setTextFormatter(new TextFormatter<>(filtro));
    }

    private void controlFormatoStock() {
        UnaryOperator<TextFormatter.Change> filtroStock = change -> {
            String newText = change.getControlNewText();
            
            // Verifica si el texto es numérico (solo dígitos)
            if (newText.matches("\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para stockProducto
        cantidad_en_stock.setTextFormatter(new TextFormatter<>(filtroStock));
    }
    
    private void controlFormatoStock2() {
        UnaryOperator<TextFormatter.Change> filtroStock = change -> {
            String newText = change.getControlNewText();
            
            // Verifica si el texto es numérico (solo dígitos)
            if (newText.matches("\\d*")) {
                return change; // Aceptar el cambio
            }
            return null; // Rechazar el cambio
        };

        // Crear una nueva instancia de TextFormatter para stockProducto
        aviso_stock.setTextFormatter(new TextFormatter<>(filtroStock));
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
        codigo_barras.setTextFormatter(new TextFormatter<>(filtroCodigo));
    }
}
