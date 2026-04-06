package modelo.excepciones;

public class ClienteNoEncontradoException extends SistemaBancarioException {

    private String idCliente;

    public ClienteNoEncontradoException(String idCliente) {
        super("No se encontró ningún cliente con ID: " + idCliente, "CLIENTE-001");
        this.idCliente = idCliente;
    }

    public String getIdCliente() { return idCliente; }
}