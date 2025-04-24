// src/main/java/dao/ClienteDAOImpl.java
package fidelitasvirtual.org.dao;

import fidelitasvirtual.org.models.Cliente;
import fidelitasvirtual.org.util.DBUtil;

import java.sql.*;

public class ClienteDAOImpl implements ClienteDAO {
    @Override
    public void insertar(Cliente c) throws Exception {
        String sql = "INSERT INTO Cliente (nombre, edad, tipo_cliente, tipo_tramite) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getEdad());
            ps.setString(3, String.valueOf(c.getTipoCliente()));
            ps.setString(4, c.getTipoTramite());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Cliente obtenerPorId(int id) throws Exception {
        String sql = "SELECT nombre, edad, tipo_cliente, tipo_tramite "
                + "FROM Cliente WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            id,
                            rs.getString("nombre"),
                            rs.getInt("edad"),
                            rs.getString("tipo_cliente").charAt(0),
                            rs.getString("tipo_tramite")
                    );
                } else {
                    throw new SQLException("Cliente no encontrado, id=" + id);
                }
            }
        }
    }
}
