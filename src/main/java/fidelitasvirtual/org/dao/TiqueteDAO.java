package fidelitasvirtual.org.dao;
import fidelitasvirtual.org.models.Tiquete;
import fidelitasvirtual.org.models.Usando;


// En TiqueteDAO.java
public interface TiqueteDAO {
    /**
     * Inserta el tiquete (recuperando su ID) y lo marca pendiente.
     * @param t          el tiquete a crear
     * @param tipoCaja   'P', 'A' o 'B'
     * @param indexCaja  para las cajas B, el índice de la caja
     */
    Tiquete insertar(Tiquete t, char tipoCaja, int indexCaja) throws Exception;

    Usando.TiquetesTemporales listarPendientes() throws Exception;
    void actualizarAtendido(int id) throws Exception;
    void eliminarPendiente(int id) throws Exception;
}
