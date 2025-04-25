package fidelitasvirtual.org.dao;
import fidelitasvirtual.org.models.Tiquete;
import fidelitasvirtual.org.models.Usando;


public interface TiqueteDAO {

    Tiquete insertar(Tiquete t, char tipoCaja, int indexCaja) throws Exception;

    Usando.TiquetesTemporales listarPendientes() throws Exception;
    void actualizarAtendido(int id) throws Exception;
    void eliminarPendiente(int id) throws Exception;
}
