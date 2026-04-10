package modelo.excepciones;

import java.time.LocalDateTime;

public class SistemaBancarioException extends Exception {

    private String codigoError;
    private LocalDateTime timestamp;

    public SistemaBancarioException(String mensaje, String codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
        this.timestamp = LocalDateTime.now();
    }

    public String getCodigoError() {
        return codigoError;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Error [" + codigoError + "] a las " + timestamp + ": " + getMessage();
    }
}