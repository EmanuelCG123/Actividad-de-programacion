package modelo.excepciones;

public class CuentaBloqueadaException extends SistemaBancarioException {

    public CuentaBloqueadaException(String numeroCuenta) {
        super("La cuenta " + numeroCuenta + " está bloqueada.", "CUENTA-001");
    }
}