package fidelitasvirtual.org.models;

public class Banco {
    private String nombre;
    private int cantidadCajas;

    public Banco(String nombre, int cantidadCajas) {
        this.nombre = nombre;
        this.cantidadCajas = cantidadCajas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadCajas() {
        return cantidadCajas;
    }

    public void setCantidadCajas(int cantidadCajas) {
        this.cantidadCajas = cantidadCajas;
    }
}