// src/main/java/fidelitasvirtual.org/models/Grafo.java
package fidelitasvirtual.org.models;

import javax.swing.JOptionPane;

public class Grafo {
    private static final String[] TRAMITES = {
            "Depósitos",
            "Retiros",
            "Cambio de Divisas"
    };

    private static final String[][] COMPLEMENTOS = {
            {"Seguro de vida", "Fondo de inversión"},       // Depósitos
            {"Retiro sin tarjeta", "Préstamo rápido"},      // Retiros
            {"Cuenta en dólares", "Certificado de divisas"} // Cambio de Divisas
    };

    /**
     * Muestra por pantalla los productos complementarios
     * para el trámite indicado.
     */
    public void mostrarComplemento(String tipoTramite) {
        int index = -1;
        for (int i = 0; i < TRAMITES.length; i++) {
            if (TRAMITES[i].equals(tipoTramite)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            StringBuilder mensaje = new StringBuilder("Recuerde ofrecer: ");
            for (int j = 0; j < COMPLEMENTOS[index].length; j++) {
                if (j > 0) mensaje.append(", ");
                mensaje.append(COMPLEMENTOS[index][j]);
            }
            JOptionPane.showMessageDialog(null, mensaje.toString());
        }
    }

    /**
     * Devuelve un stub del tipo de cambio USD.
     * Luego puedes reemplazarlo con una consulta real a tu BD o API.
     */
    public String obtenerTipoCambioDolar() {
        return "Tipo de cambio actual: 600.00 CRC/USD";
    }
}
