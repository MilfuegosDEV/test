package fidelitasvirtual.org.dao;

import fidelitasvirtual.org.models.Tiquete;

public interface ReporteDAO {
    void insertarReporte(Tiquete t, char tipoCaja, int numCaja, long segundosEspera) throws Exception;
}