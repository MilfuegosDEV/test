// src/main/java/fidelitasvirtual.org/models/ListaCajas.java
package fidelitasvirtual.org.models;

public class ListaCajas {
    private NodoCaja cabeza;
    private NodoCaja cola;
    private int tamaño;

    public ListaCajas() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }

    public void agregarCaja(Caja caja) {
        NodoCaja nuevoNodo = new NodoCaja(caja);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setAnterior(cola);
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
        tamaño++;
    }

    public Caja obtenerCaja(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        NodoCaja actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getCaja();
    }

    public int getTamaño() {
        return tamaño;
    }

    public NodoCaja getCabeza() {
        return cabeza;
    }

    public NodoCaja getCola() {
        return cola;
    }
}
