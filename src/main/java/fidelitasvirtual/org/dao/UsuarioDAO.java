package fidelitasvirtual.org.dao;

public interface UsuarioDAO {
    void insertar(String usuario, String contrasena) throws Exception;
    boolean validar(String usuario, String contrasena) throws Exception;
}