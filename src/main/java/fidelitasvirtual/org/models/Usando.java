package fidelitasvirtual.org.models;

public class Usando {


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
