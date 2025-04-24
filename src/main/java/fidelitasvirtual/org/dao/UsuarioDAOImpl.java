package fidelitasvirtual.org.dao;


import fidelitasvirtual.org.util.DBUtil;

import java.sql.*;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public void insertar(String usuario, String contrasena) throws Exception {
        String sql = "INSERT INTO Usuario (usuario, contrasena) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean validar(String usuario, String contrasena) throws Exception {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE usuario = ? AND contrasena = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}