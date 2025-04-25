package fidelitasvirtual.org;// src/main/java/Main.java
import fidelitasvirtual.org.models.Banco;
import fidelitasvirtual.org.models.Configuracion;
import fidelitasvirtual.org.service.GestorCajas;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Configuracion.verificarConfiguracion();
        if (!Configuracion.autenticar()) {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        Banco banco = Configuracion.leerBanco();
        try {
            GestorCajas gestor = new GestorCajas(banco.getCantidadCajas());
            menu(gestor, banco);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error iniciar gestor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void menu(GestorCajas g, Banco b) {
        while (true) {
            String o = JOptionPane.showInputDialog(
                    "Banco " + b.getNombre() + "\n" +
                            "1. Crear tiquete\n" +
                            "2. Atender tiquete\n" +
                            "3. Ver reportes\n" +
                            "4. Salir"
            );
            if (o == null || o.equals("4")) {
                g.finalizarPrograma();
                break;
            }
            switch (o) {
                case "1" -> g.crearTiquete();
                case "2" -> g.atenderTiquete();
                case "3" -> g.mostrarReportes();
                default  -> JOptionPane.showMessageDialog(null, "Opción inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
