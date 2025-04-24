package fidelitasvirtual.org.dao;
import fidelitasvirtual.org.models.Tiquete;
import fidelitasvirtual.org.models.Usando;


public interface TiqueteDAO {
    void insertar(Tiquete t) throws Exception;
    void marcarPendiente(int id, char tipoCaja, int indexCaja) throws Exception;
    Usando.TiquetesTemporales listarPendientes() throws Exception;
    void actualizarAtendido(int id) throws Exception;
    void eliminarPendiente(int id) throws Exception;
}