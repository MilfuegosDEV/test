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
    public Tiquete insertar(Tiquete t, char tipoCaja, int indexCaja) throws Exception {
        // 1) Asegura cliente
        if (t.getCliente().getId() == 0) {
            clienteDAO.insertar(t.getCliente());
        }

        String sqlInsert =
                "INSERT INTO Tiquete (cliente_id, hora_creacion) VALUES (?, ?)";
        String sqlPend   =
                "INSERT INTO TiquetePendiente (tiquete_id, tipo_caja, index_caja) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (
                    PreparedStatement psIns = conn.prepareStatement(
                            sqlInsert, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement psPen = conn.prepareStatement(sqlPend)
            ) {
                // a) Inserto en Tiquete
                psIns.setInt(1, t.getCliente().getId());
                psIns.setTimestamp(2, Timestamp.valueOf(t.getHoraCreacion()));
                psIns.executeUpdate();

                // b) Recupero y asigno ID
                try (ResultSet rs = psIns.getGeneratedKeys()) {
                    if (rs.next()) {
                        t.setId(rs.getInt(1));
                    } else {
                        throw new SQLException("No se obtuvo ID al insertar Tiquete");
                    }
                }

                // c) Marco pendiente
                psPen.setInt(1, t.getId());
                psPen.setString(2, String.valueOf(tipoCaja));
                psPen.setInt(3, indexCaja);
                psPen.executeUpdate();

                conn.commit();
                return t;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
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
