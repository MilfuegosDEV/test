package fidelitasvirtual.org.dao;

import fidelitasvirtual.org.models.Cliente;

public interface ClienteDAO {
    void insertar(Cliente c) throws Exception;
    Cliente obtenerPorId(int id) throws Exception;
}
