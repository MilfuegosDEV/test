package fidelitasvirtual.org.service;

import fidelitasvirtual.org.dao.TiqueteDAO;
import fidelitasvirtual.org.dao.TiqueteDAOImpl;
import fidelitasvirtual.org.dao.ReporteDAO;
import fidelitasvirtual.org.dao.ReporteDAOImpl;
import fidelitasvirtual.org.models.*;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class GestorCajas {
    private final Caja cajaP = new Caja('P');
    private final Caja cajaA = new Caja('A');
    private final ListaCajas cajasB = new ListaCajas();
    private final TiqueteDAO tiqueteDAO = new TiqueteDAOImpl();
    private final ReporteDAO reporteDAO = new ReporteDAOImpl();
    private final Grafo grafo = new Grafo();

    public GestorCajas(int cantB) throws Exception {
        for (int i = 0; i < cantB; i++) {
            cajasB.agregarCaja(new Caja('B'));
        }
        Usando.TiquetesTemporales tmp = tiqueteDAO.listarPendientes();
        Usando.NodoTiqueteCarga nodo = tmp.getCabeza();
        while (nodo != null) {
            Tiquete t = nodo.getTiquete();
            switch (nodo.getTipoCaja()) {
                case 'P': cajaP.encolar(t); break;
                case 'A': cajaA.encolar(t); break;
                case 'B': cajasB.obtenerCaja(nodo.getIndexCaja()).encolar(t); break;
            }
            nodo = nodo.getSiguiente();
        }
    }

    public void crearTiquete() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del cliente:");
            int edad = Integer.parseInt(
                    JOptionPane.showInputDialog("Edad del cliente:")
            );

            String[] opts1 = { "Preferencial (P)", "Un trámite (A)", "Varios trámites (B)" };
            int sel1 = JOptionPane.showOptionDialog(
                    null, "Tipo de cliente:", "",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opts1, opts1[0]
            );
            char tipoC = "PAB".charAt(sel1);

            String[] opts2 = { "Depósitos", "Retiros", "Cambio de divisas" };
            int sel2 = JOptionPane.showOptionDialog(
                    null, "Tipo de trámite:", "",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opts2, opts2[0]
            );
            String tramite = opts2[sel2];

            Cliente c = new Cliente(nombre, edad, tipoC, tramite);
            Tiquete t = new Tiquete(c);

            int idxB = 0;
            if (tipoC == 'B') {
                Caja menos = cajasB.obtenerCaja(0);
                int mejor = 0, idx = 0;
                for (NodoCaja nc = cajasB.getCabeza(); nc != null; nc = nc.getSiguiente(), idx++) {
                    if (nc.getCaja().getCantidad() < menos.getCantidad()) {
                        menos = nc.getCaja();
                        mejor = idx;
                    }
                }
                idxB = mejor;
            }

            t = tiqueteDAO.insertar(t, tipoC, idxB);

            switch (tipoC) {
                case 'P' -> cajaP.encolar(t);
                case 'A' -> cajaA.encolar(t);
                case 'B' -> cajasB.obtenerCaja(idxB).encolar(t);
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Tiquete creado:\n" + t
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error crear tiquete: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public void atenderTiquete() {
        try {
            StringBuilder m = new StringBuilder("Seleccione caja:\n");
            m.append("1. P (").append(cajaP.getCantidad()).append(")\n");
            m.append("2. A (").append(cajaA.getCantidad()).append(")\n");
            int opt = 3;
            NodoCaja nc = cajasB.getCabeza();
            while (nc != null) {
                m.append(opt).append(". B").append(opt-3)
                        .append(" (").append(nc.getCaja().getCantidad()).append(")\n");
                nc = nc.getSiguiente();
                opt++;
            }
            String in = JOptionPane.showInputDialog(m.toString());
            if (in == null || in.isEmpty()) return;
            int sel = Integer.parseInt(in)-1;
            Caja csel = switch (sel) {
                case 0 -> cajaP;
                case 1 -> cajaA;
                default -> cajasB.obtenerCaja(sel-2);
            };
            Tiquete t = csel.atender();
            if (t == null) {
                JOptionPane.showMessageDialog(null, "Sin tiquetes");
                return;
            }
            grafo.mostrarComplemento(t.getCliente().getTipoTramite());
            if ("Cambio de divisas".equals(t.getCliente().getTipoTramite())) {
                JOptionPane.showMessageDialog(null, grafo.obtenerTipoCambioDolar());
            }
            long seg = Duration.between(t.getHoraCreacion(), LocalDateTime.now()).getSeconds();
            t.setHoraAtencion(LocalDateTime.now());
            tiqueteDAO.actualizarAtendido(t.getId());
            reporteDAO.insertarReporte(t, csel.getTipo(), sel>=2?(sel-2):0, seg);
            tiqueteDAO.eliminarPendiente(t.getId());

            JOptionPane.showMessageDialog(null,
                    "Atendido:\n" + t +
                            "\nEspera: " + seg + "s" +
                            "\nQuedan en cola: " + csel.getCantidad()
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error atender: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void mostrarReportes() {
        try {
            String resumen = ReporteDAOImpl.generarResumenReportes();
            JOptionPane.showMessageDialog(null, resumen, "Reportes", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar reportes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void finalizarPrograma() {
        JOptionPane.showMessageDialog(null, "Saliendo...");
        System.exit(0);
    }
}
