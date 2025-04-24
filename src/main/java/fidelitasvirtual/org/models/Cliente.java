package fidelitasvirtual.org.models;

public class Cliente {
    private int id;              // se asigna tras INSERT
    private final String nombre;
    private final int edad;
    private final char tipoCliente;
    private final String tipoTramite;

    /** Para creación previa a INSERT (id se asigna luego) */
    public Cliente(String nombre, int edad, char tipoCliente, String tipoTramite) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipoCliente = tipoCliente;
        this.tipoTramite = tipoTramite;
    }

    /** Para carga desde BD */
    public Cliente(int id, String nombre, int edad, char tipoCliente, String tipoTramite) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.tipoCliente = tipoCliente;
        this.tipoTramite = tipoTramite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public char getTipoCliente() { return tipoCliente; }
    public String getTipoTramite() { return tipoTramite; }
}
