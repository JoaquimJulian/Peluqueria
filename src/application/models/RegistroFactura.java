package application.models;

import application.models.databaseConection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroFactura {

    private int idFactura;
    private String cliente;
    private String trabajador;
    private double montoTotal;
    private String metodoPago;
    private String fecha;
    private String comentario;

    public RegistroFactura(int idFactura, String cliente, String trabajador, double montoTotal, String metodoPago, String fecha, String comentario) {
        this.idFactura = idFactura;
        this.cliente = cliente;
        this.trabajador = trabajador;
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getFecha() {
        return fecha;
    }

    public String getComentario() {
        return comentario;
    }

    /**
     * Obtiene las facturas asociadas a un trabajador por su ID.
     *
     * @param idTrabajador ID del trabajador.
     * @return Lista de objetos RegistroFactura con las facturas del trabajador.
     * @throws SQLException Si ocurre un error al consultar la base de datos.
     */
    public static List<RegistroFactura> obtenerFacturasPorTrabajador(int idTrabajador) throws SQLException {
        List<RegistroFactura> facturas = new ArrayList<>();

        String query = "SELECT f.id_factura, " +
                       "       c.nombre AS cliente, " +
                       "       t.nombre AS trabajador, " +
                       "       f.monto_total, " +
                       "       f.metodo_pago, " +
                       "       f.fecha, " +
                       "       f.comentario " +
                       "FROM facturacion f " +
                       "JOIN clientes c ON f.id_cliente = c.id_cliente " +
                       "JOIN trabajadores t ON f.id_trabajador = t.id_trabajador " +
                       "WHERE f.id_trabajador = ?";

        try (Connection connection = databaseConection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idTrabajador);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RegistroFactura factura = new RegistroFactura(
                    resultSet.getInt("id_factura"),
                    resultSet.getString("cliente"),
                    resultSet.getString("trabajador"),
                    resultSet.getDouble("monto_total"),
                    resultSet.getString("metodo_pago"),
                    resultSet.getString("fecha"),
                    resultSet.getString("comentario")
                );
                facturas.add(factura);
            }
        }

        return facturas;
    }
}
