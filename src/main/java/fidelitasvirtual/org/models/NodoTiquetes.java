// src/main/java/fidelitasvirtual.org/models/NodoTiquetes.java
package fidelitasvirtual.org.models;

public class NodoTiquetes {
    private final Tiquete tiquete;
    private NodoTiquetes siguiente;

    public NodoTiquetes(Tiquete tiquete) {
        this.tiquete = tiquete;
        this.siguiente = null;
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public NodoTiquetes getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoTiquetes siguiente) {
        this.siguiente = siguiente;
    }
}
