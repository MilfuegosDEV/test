package fidelitasvirtual.org.dao;

import fidelitasvirtual.org.models.Banco;
import fidelitasvirtual.org.util.DBUtil;

import java.sql.*;

public class BancoDAOImpl implements BancoDAO {
    @Override
    public boolean existeConfiguracion() throws Exception {
        String sql = "SELECT COUNT(*) FROM Banco";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1) > 0;
            return false;
        }
    }

    @Override
    public void insertar(Banco b) throws Exception {
        String sql = "INSERT INTO Banco (nombre, cantidad_cajas) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getNombre());
            ps.setInt(2, b.getCantidadCajas());
            ps.executeUpdate();
        }
    }

    @Override
    public Banco obtener() throws Exception {
        String sql = "SELECT nombre, cantidad_cajas FROM Banco LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return new Banco(rs.getString("nombre"), rs.getInt("cantidad_cajas"));
            }
            throw new SQLException("No hay configuración de Banco");
        }
    }
}