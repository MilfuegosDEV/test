package fidelitasvirtual.org.dao;

import fidelitasvirtual.org.models.Banco;

public interface BancoDAO {
    boolean existeConfiguracion() throws Exception;
    void insertar(Banco b) throws Exception;
    Banco obtener() throws Exception;
}