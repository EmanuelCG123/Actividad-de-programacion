package modelo.excepciones;

public class EstadoTransaccionInvalidoException extends BancoRuntimeException {

    public EstadoTransaccionInvalidoException(String estadoOrigen, String estadoDestino) {
        super("Transición de estado inválida: no se puede pasar de "
              + estadoOrigen + " a " + estadoDestino);
    }
}