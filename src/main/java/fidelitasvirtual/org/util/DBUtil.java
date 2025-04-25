package fidelitasvirtual.org.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/banco"
                    + "?useSSL=false"
                    + "&serverTimezone=UTC"
                    + "&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";       // ajusta usuario
    private static final String PASSWORD = "_=lDX;430y7~"; // ajusta contraseña

    private DBUtil() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
