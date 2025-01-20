package application.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.models.databaseConection;
import application.models.Trabajador;
import application.models.RegistroFactura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EstadisticasController {

    // HEADER ELEMENTOS
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
    private Label nombreSesion;

    @FXML
    private Button cerrar;

    // CONTENIDO PRINCIPAL
    @FXML
    private Label nombreTrabajador;

    @FXML
    private Label registrosFacturacion;

    @FXML
    private TableView<RegistroFactura> tablaRegistros;

    @FXML
    private TableColumn<RegistroFactura, Integer> colFacturaId;

    @FXML
    private TableColumn<RegistroFactura, String> colCliente;

    @FXML
    private TableColumn<RegistroFactura, String> colTrabajador;

    @FXML
    private TableColumn<RegistroFactura, Double> colMontoTotal;

    @FXML
    private TableColumn<RegistroFactura, String> colMetodoPago;

    @FXML
    private TableColumn<RegistroFactura, String> colFecha;

    @FXML
    private TableColumn<RegistroFactura, String> colComentario;

    private Trabajador trabajador;

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
        mostrarEstadisticas();
    }

    @FXML
    public void initialize() {
        cerrar.setOnAction(event -> {
            Stage stage = (Stage) cerrar.getScene().getWindow();
            stage.close();
        });

        salir.setOnMouseClicked(event -> {
            Stage stage = (Stage) salir.getScene().getWindow();
            stage.close();
        });

        // Aquí puedes añadir acciones para otros elementos del header, si es necesario
        calendario.setOnMouseClicked(event -> System.out.println("Calendario clicado."));
        ajustes.setOnMouseClicked(event -> System.out.println("Ajustes clicado."));
        cobrar.setOnMouseClicked(event -> System.out.println("Cobrar clicado."));
        usuarios.setOnMouseClicked(event -> System.out.println("Usuarios clicado."));

        inicializarTabla();
    }

    private void inicializarTabla() {
        colFacturaId.setCellValueFactory(new PropertyValueFactory<>("idFactura"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colTrabajador.setCellValueFactory(new PropertyValueFactory<>("trabajador"));
        colMontoTotal.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        colMetodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));
    }

    private void mostrarEstadisticas() {
        if (trabajador != null) {
            nombreTrabajador.setText(trabajador.getNombre() + " " + trabajador.getApellidos());
            ObservableList<RegistroFactura> registros = obtenerRegistrosFacturacion(trabajador.getId());
            registrosFacturacion.setText("Registros en facturación: " + registros.size());
            tablaRegistros.setItems(registros);
        }
    }

    private ObservableList<RegistroFactura> obtenerRegistrosFacturacion(int trabajadorId) {
        String query = "SELECT f.id_factura, " +
                       "c.nombre AS cliente_nombre, c.apellidos AS cliente_apellidos, " +
                       "t.nombre AS trabajador_nombre, t.apellidos AS trabajador_apellidos, " +
                       "f.monto_total, f.metodo_pago, f.fecha, f.comentario " +
                       "FROM facturacion f " +
                       "JOIN clientes c ON f.id_cliente = c.id_cliente " +
                       "JOIN trabajadores t ON f.id_trabajador = t.id_trabajador " +
                       "WHERE f.id_trabajador = ?";
        ObservableList<RegistroFactura> registros = FXCollections.observableArrayList();

        try (Connection connection = databaseConection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, trabajadorId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RegistroFactura registro = new RegistroFactura(
                    resultSet.getInt("id_factura"),
                    resultSet.getString("cliente_nombre") + " " + resultSet.getString("cliente_apellidos"),
                    resultSet.getString("trabajador_nombre") + " " + resultSet.getString("trabajador_apellidos"),
                    resultSet.getDouble("monto_total"),
                    resultSet.getString("metodo_pago"),
                    resultSet.getString("fecha"),
                    resultSet.getString("comentario")
                );
                registros.add(registro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registros;
    }
}
