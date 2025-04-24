// src/main/java/model/Usando.java
package fidelitasvirtual.org.models;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Usando {
    private static final String ARCHIVO_TIQUETES = "usando.txt";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Guarda un tiquete en crudo (antes de atender). */
    public static void guardarTiqueteCompleto(Tiquete tiquete, char tipoCaja, int indexCaja) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_TIQUETES, true))) {
            Cliente c = tiquete.getCliente();
            writer.println(String.format("%s|%d|%d|%s|%d|%s|%s|%s",
                    tipoCaja,
                    indexCaja,
                    tiquete.getId(),
                    c.getNombre(),
                    c.getEdad(),
                    c.getTipoCliente(),
                    c.getTipoTramite(),
                    tiquete.getHoraCreacion().format(formatter)
            ));
        } catch (IOException e) {
            System.err.println("Error al guardar tiquete: " + e.getMessage());
        }
    }

    /** Elimina de usando.txt un tiquete atendido. */
    public static void eliminarTiqueteExacto(Tiquete tiquete) {
        File orig = new File(ARCHIVO_TIQUETES);
        File temp = new File("temp_" + ARCHIVO_TIQUETES);
        try (BufferedReader br = new BufferedReader(new FileReader(orig));
             PrintWriter pw = new PrintWriter(new FileWriter(temp))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8 && Integer.parseInt(parts[2]) == tiquete.getId())
                    continue;
                pw.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar tiquete: " + e.getMessage());
            return;
        }
        orig.delete();
        temp.renameTo(orig);
    }

    /** Carga todos los tiquetes pendientes al iniciar. */
    public static TiquetesTemporales cargarTiquetesPendientes() {
        TiquetesTemporales temp = new TiquetesTemporales();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_TIQUETES))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length != 8) continue;
                char tipo = p[0].charAt(0);
                int idx   = Integer.parseInt(p[1]);
                int id    = Integer.parseInt(p[2]);
                String nom= p[3];
                int ed    = Integer.parseInt(p[4]);
                char tc   = p[5].charAt(0);
                String tr = p[6];
                LocalDateTime fc = LocalDateTime.parse(p[7], formatter);

                Cliente c = new Cliente(id, nom, ed, tc, tr);
                Tiquete t = new Tiquete(c);
                t.setId(id);
                t.setHoraCreacion(fc);
                temp.agregarTiquete(tipo, idx, t);
            }
        } catch (FileNotFoundException e) {
            // Primer arranque: no existe
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error cargar pendientes: " + e.getMessage());
        }
        return temp;
    }

    /** Limpia todo el archivo usado. */
    public static void limpiarArchivoTiquetes() {
        try {
            new FileWriter(ARCHIVO_TIQUETES, false).close();
        } catch (IOException e) {
            System.err.println("Error al limpiar archivo: " + e.getMessage());
        }
    }

    /** Guarda al finalizar las colas en el archivo. */
    public static void guardarTiquetesEncolados(Caja p, Caja a, ListaCajas bs) {
        limpiarArchivoTiquetes();
        // P
        NodoTiquetes n = p.getFrente();
        while (n != null) {
            guardarTiqueteCompleto(n.getTiquete(), 'P', 0);
            n = n.getSiguiente();
        }
        // A
        n = a.getFrente();
        while (n != null) {
            guardarTiqueteCompleto(n.getTiquete(), 'A', 0);
            n = n.getSiguiente();
        }
        // B
        NodoCaja nc = bs.getCabeza();
        int idx = 0;
        while (nc != null) {
            n = nc.getCaja().getFrente();
            while (n != null) {
                guardarTiqueteCompleto(n.getTiquete(), 'B', idx);
                n = n.getSiguiente();
            }
            nc = nc.getSiguiente();
            idx++;
        }
    }

    // --- Estructura de nodos para pendientes en memoria ---
    public static class TiquetesTemporales {
        private NodoTiqueteCarga cabeza, cola;
        public void agregarTiquete(char tipoCaja, int idx, Tiquete tique) {
            NodoTiqueteCarga nodo = new NodoTiqueteCarga(tipoCaja, idx, tique);
            if (cabeza == null) cabeza = cola = nodo;
            else { cola.setSiguiente(nodo); cola = nodo; }
        }
        public NodoTiqueteCarga getCabeza() { return cabeza; }
    }

    public static class NodoTiqueteCarga {
        private final char tipoCaja;
        private final int indexCaja;
        private final Tiquete tiquete;
        private NodoTiqueteCarga siguiente;
        public NodoTiqueteCarga(char tipoCaja, int indexCaja, Tiquete t) {
            this.tipoCaja = tipoCaja;
            this.indexCaja = indexCaja;
            this.tiquete = t;
        }
        public char getTipoCaja() { return tipoCaja; }
        public int getIndexCaja() { return indexCaja; }
        public Tiquete getTiquete() { return tiquete; }
        public NodoTiqueteCarga getSiguiente() { return siguiente; }
        public void setSiguiente(NodoTiqueteCarga s) { this.siguiente = s; }
    }
}
