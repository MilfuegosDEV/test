package fidelitasvirtual.org.models;

public class Caja {
    private NodoTiquetes frente;
    private NodoTiquetes fin;
    private final char tipo;
    private int cantidad;

    public Caja(char tipo) {
        this.tipo = tipo;
        this.frente = null;
        this.fin = null;
        this.cantidad = 0;
    }

    public void encolar(Tiquete tiquete) {
        NodoTiquetes nuevo = new NodoTiquetes(tiquete);
        if (fin == null) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        cantidad++;
    }

    public Tiquete atender() {
        if (frente == null) return null;
        Tiquete tiquete = frente.getTiquete();
        frente = frente.getSiguiente();
        if (frente == null) fin = null;
        cantidad--;
        return tiquete;
    }

    public NodoTiquetes getFrente() {
        return frente;
    }

    public char getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }
}
