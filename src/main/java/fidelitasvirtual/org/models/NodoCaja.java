package fidelitasvirtual.org.models;

public class NodoCaja {
    private final Caja caja;
    private NodoCaja anterior;
    private NodoCaja siguiente;

    public NodoCaja(Caja caja) {
        this.caja = caja;
        this.anterior = null;
        this.siguiente = null;
    }

    public Caja getCaja() {
        return caja;
    }

    public NodoCaja getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoCaja anterior) {
        this.anterior = anterior;
    }

    public NodoCaja getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCaja siguiente) {
        this.siguiente = siguiente;
    }
}
