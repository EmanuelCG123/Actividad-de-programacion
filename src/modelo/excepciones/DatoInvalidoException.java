package modelo.excepciones;

public class DatoInvalidoException extends BancoRuntimeException {

    private String campo;
    private Object valorRecibido;

    public DatoInvalidoException(String campo, Object valorRecibido) {
        super("Dato inválido en el campo '" + campo
              + "'. Valor recibido: " + valorRecibido);
        this.campo = campo;
        this.valorRecibido = valorRecibido;
    }

    public String getCampo() { return campo; }
    public Object getValorRecibido() { return valorRecibido; }
}