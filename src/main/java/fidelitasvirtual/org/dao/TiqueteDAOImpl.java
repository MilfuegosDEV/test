package fidelitasvirtual.org.dao;

import fidelitasvirtual.org.models.Cliente;
import fidelitasvirtual.org.models.Tiquete;
import fidelitasvirtual.org.models.Usando;
import fidelitasvirtual.org.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class TiqueteDAOImpl implements TiqueteDAO {
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    public void insertar(Tiquete t) throws Exception {
        // Asegura que el cliente esté en BD
        if (t.getCliente().getId() == 0) {
            clienteDAO.insertar(t.getCliente());
        }
        String sql = "INSERT INTO Tiquete (id, cliente_id, hora_creacion) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getId());
            ps.setInt(2, t.getCliente().getId());
            ps.setTimestamp(3, Timestamp.valueOf(t.getHoraCreacion()));
            ps.executeUpdate();
        }
    }

    @Override
    public void marcarPendiente(int id, char tipoCaja, int indexCaja) throws Exception {
        String sql = "INSERT INTO TiquetePendiente (tiquete_id, tipo_caja, index_caja) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, String.valueOf(tipoCaja));
            ps.setInt(3, indexCaja);
            ps.executeUpdate();
        }
    }

    @Override
    public Usando.TiquetesTemporales listarPendientes() throws Exception {
        String sql = """
            SELECT tp.tiquete_id, t.cliente_id, t.hora_creacion,
                   tp.tipo_caja, tp.index_caja
              FROM TiquetePendiente tp
              JOIN Tiquete t ON tp.tiquete_id = t.id
            """;
        Usando.TiquetesTemporales temp = new Usando.TiquetesTemporales();
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int id           = rs.getInt("tiquete_id");
                int cliId        = rs.getInt("cliente_id");
                LocalDateTime c  = rs.getTimestamp("hora_creacion").toLocalDateTime();
                char tipoCaja    = rs.getString("tipo_caja").charAt(0);
                int idxCaja      = rs.getInt("index_caja");

                Cliente cliente = clienteDAO.obtenerPorId(cliId);
                Tiquete t = new Tiquete(cliente);
                t.setId(id);
                t.setHoraCreacion(c);
                temp.agregarTiquete(tipoCaja, idxCaja, t);
            }
        }
        return temp;
    }

    @Override
    public void actualizarAtendido(int id) throws Exception {
        String sql = "UPDATE Tiquete SET hora_atencion = NOW() WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarPendiente(int id) throws Exception {
        String sql = "DELETE FROM TiquetePendiente WHERE tiquete_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
