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
}
