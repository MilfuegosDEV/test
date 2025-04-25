// src/main/java/model/Tiquete.java
package fidelitasvirtual.org.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Tiquete {
    private int id;
    private final Cliente cliente;
    private LocalDateTime horaCreacion;
    private LocalDateTime horaAtencion;

    public Tiquete(Cliente cliente) {
        this.cliente = cliente;
        this.horaCreacion = LocalDateTime.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHoraCreacion(LocalDateTime horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public void setHoraAtencion(LocalDateTime horaAtencion) {
        this.horaAtencion = horaAtencion;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getHoraCreacion() {
        return horaCreacion;
    }

    public LocalDateTime getHoraAtencion() {
        return horaAtencion;
    }

    public long getSegundosEspera() {
        if (horaAtencion == null) return 0;
        return ChronoUnit.SECONDS.between(horaCreacion, horaAtencion);
    }

    public String getTiempoAtencion() {
        if (horaAtencion == null) return "No atendido";
        long seg = getSegundosEspera();
        long min = seg / 60;
        long sec = seg % 60;
        return String.format("%dm %ds", min, sec);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Cliente: %s (%d años) | Tipo: %c | Trámite: %s | Tiempo: %s",
                id,
                cliente.getNombre(),
                cliente.getEdad(),
                cliente.getTipoCliente(),
                cliente.getTipoTramite(),
                getTiempoAtencion()
        );
    }
}
