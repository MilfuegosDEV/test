// src/main/java/dao/ReporteDAOImpl.java
package fidelitasvirtual.org.dao;

import fidelitasvirtual.org.models.Tiquete;
import fidelitasvirtual.org.util.DBUtil;

import java.sql.*;

public class ReporteDAOImpl implements ReporteDAO {
    @Override
    public void insertarReporte(Tiquete t, char tipoCaja, int numCaja, long segundosEspera) throws Exception {
        String sql = "INSERT INTO Reporte (tiquete_id, tipo_caja, num_caja, segundos_espera, hora_registro) "
                + "VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getId());
            ps.setString(2, String.valueOf(tipoCaja));
            ps.setInt(3, numCaja);
            ps.setLong(4, segundosEspera);
            ps.executeUpdate();
        }
    }
    public static String generarResumenReportes() {
        StringBuilder resumen = new StringBuilder();

        try (Connection conn = DBUtil.getConnection()) {

            // 1. Caja con más clientes
            String sql1 = "SELECT tipo_caja, num_caja, COUNT(*) AS total FROM Reporte " +
                    "GROUP BY tipo_caja, num_caja ORDER BY total DESC LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql1);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resumen.append("1. Caja con más clientes: ")
                            .append(rs.getString("tipo_caja"))
                            .append(rs.getInt("num_caja"))
                            .append(" (").append(rs.getInt("total")).append(" clientes)\n");
                }
            }

            // 2. Total de clientes atendidos
            String sql2 = "SELECT COUNT(*) AS total_clientes FROM Reporte";
            try (PreparedStatement ps = conn.prepareStatement(sql2);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resumen.append("2. Total de clientes atendidos: ")
                            .append(rs.getInt("total_clientes")).append("\n");
                }
            }

            // 3. Caja con mejor tiempo promedio
            String sql3 = "SELECT tipo_caja, num_caja, AVG(segundos_espera) AS promedio FROM Reporte " +
                    "GROUP BY tipo_caja, num_caja ORDER BY promedio ASC LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql3);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resumen.append("3. Caja más eficiente: ")
                            .append(rs.getString("tipo_caja"))
                            .append(rs.getInt("num_caja"))
                            .append(" (Promedio: ").append(rs.getLong("promedio")).append("s)\n");
                }
            }

            // 4. Tiempo promedio general
            String sql4 = "SELECT AVG(segundos_espera) AS promedio_general FROM Reporte";
            try (PreparedStatement ps = conn.prepareStatement(sql4);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resumen.append("4. Promedio general de atención: ")
                            .append(rs.getLong("promedio_general")).append("s\n");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resumen.toString();
    }
}
