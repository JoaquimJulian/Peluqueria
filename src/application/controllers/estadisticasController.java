package application.controllers;

import application.models.RegistroFactura;
import application.models.Trabajador;
import application.models.databaseConection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class estadisticasController {

    @FXML
    private ImageView salir;

    @FXML
    private Label nombreTrabajador, registrosFacturacion;

    @FXML
    private TableView<RegistroFactura> tablaRegistros;

    @FXML
    private TableColumn<RegistroFactura, String> colCliente;

    @FXML
    private TableColumn<RegistroFactura, String> colNombreServicio;

    @FXML
    private TableColumn<RegistroFactura, String> colNombreProducto;

    @FXML
    private TableColumn<RegistroFactura, Double> colMontoTotal;

    @FXML
    private TableColumn<RegistroFactura, String> colMetodoPago;

    @FXML
    private TableColumn<RegistroFactura, LocalDate> colFecha;

    @FXML
    private TableColumn<RegistroFactura, String> colObservacion;

    private ObservableList<RegistroFactura> listaRegistros;

    private int idUsuarioSesion; // ID del usuario en sesión

    private Trabajador trabajador; // Nuevo atributo para almacenar el Trabajador recibido

    @FXML
    public void initialize() {
        configurarTabla();
        salir.setOnMouseClicked(event -> cerrarVentana()); // Cierra la ventana de estadísticas
    }

    /**
     * Método para recibir un objeto Trabajador y configurar la vista con sus datos.
     *
     * @param trabajador Objeto Trabajador que contiene la información del usuario en sesión.
     */
    public void recibirTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
        this.idUsuarioSesion = trabajador.getId(); // Obtiene el ID del trabajador
        nombreTrabajador.setText("Trabajador: " + trabajador.getNombre()); // Muestra el nombre del trabajador
        cargarDatos(); // Carga los datos de facturación asociados al trabajador
    }

    private void configurarTabla() {
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colNombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colMontoTotal.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        colMetodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colObservacion.setCellValueFactory(new PropertyValueFactory<>("observacion"));
    }

    private void cargarDatos() {
        listaRegistros = FXCollections.observableArrayList();

        try (Connection conexion = databaseConection.getConnection()) {
            // Ajustamos la consulta para obtener el nombre del cliente
            String query = "SELECT c.nombre AS cliente, f.nombre_servicio, f.nombre_producto, f.monto_total, f.metodo_pago, f.fecha, f.observacion_facturacion AS observacion " +
                           "FROM facturacion f " +
                           "JOIN clientes c ON f.id_cliente = c.id_cliente " +
                           "WHERE f.id_trabajador = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, idUsuarioSesion);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listaRegistros.add(new RegistroFactura(
                        resultSet.getString("cliente"), // Ahora es el nombre del cliente
                        resultSet.getString("nombre_servicio"),
                        resultSet.getString("nombre_producto"),
                        resultSet.getDouble("monto_total"),
                        resultSet.getString("metodo_pago"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getString("observacion")
                ));
            }

            tablaRegistros.setItems(listaRegistros);
            registrosFacturacion.setText("Registros en Facturación: " + listaRegistros.size());
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar los datos: " + e.getMessage());
        }
    }

    public void setIdUsuarioSesion(int idUsuarioSesion) {
        this.idUsuarioSesion = idUsuarioSesion; // Asignamos el ID del trabajador
        cargarDatos(); // Cargamos los datos una vez que se recibe el ID
    }

    private void cerrarVentana() {
        Stage stage = (Stage) salir.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}