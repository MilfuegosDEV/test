package fidelitasvirtual.org.models;

import fidelitasvirtual.org.dao.BancoDAO;
import fidelitasvirtual.org.dao.BancoDAOImpl;
import fidelitasvirtual.org.dao.UsuarioDAO;
import fidelitasvirtual.org.dao.UsuarioDAOImpl;

import javax.swing.JOptionPane;

public class Configuracion {
    private static final BancoDAO bancoDAO = new BancoDAOImpl();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public static void verificarConfiguracion() {
        try {
            if (!bancoDAO.existeConfiguracion()) {
                // Banco
                String nombre;
                do {
                    nombre = JOptionPane.showInputDialog("Ingrese el nombre del banco:");
                    if (nombre == null) System.exit(0);
                } while (nombre.trim().isEmpty());

                int cajasB = 0;
                while (cajasB <= 0) {
                    try {
                        String in = JOptionPane.showInputDialog("Cantidad de cajas tipo B:");
                        if (in == null) System.exit(0);
                        cajasB = Integer.parseInt(in);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Número inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                Banco b = new Banco(nombre, cajasB);
                bancoDAO.insertar(b);

                // Usuarios
                int numU = 0;
                while (numU <= 0) {
                    try {
                        String in = JOptionPane.showInputDialog("¿Cuántos usuarios administradores?");
                        if (in == null) System.exit(0);
                        numU = Integer.parseInt(in);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Número inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                for (int i = 1; i <= numU; i++) {
                    String u, p;
                    do {
                        u = JOptionPane.showInputDialog("Usuario #" + i + " - Nombre:");
                        if (u == null) System.exit(0);
                    } while (u.trim().isEmpty());
                    do {
                        p = JOptionPane.showInputDialog("Usuario #" + i + " - Contraseña:");
                        if (p == null) System.exit(0);
                    } while (p.trim().isEmpty());
                    usuarioDAO.insertar(u, p);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error configuración", e);
        }
    }

    public static boolean autenticar() {
        try {
            String u = JOptionPane.showInputDialog("Usuario:");
            String p = JOptionPane.showInputDialog("Contraseña:");
            if (u == null || p == null) return false;
            return usuarioDAO.validar(u, p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error autenticación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Banco leerBanco() {
        try {
            return bancoDAO.obtener();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer banco", e);
        }
    }
}
