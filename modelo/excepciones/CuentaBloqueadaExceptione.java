package modelo.excepciones;

public class CuentaBloqueadaExceptione extends SistemaBancarioException {

    public CuentaBloqueadaExceptione(String numeroCuenta) {
        super("La cuenta " + numeroCuenta + " está bloqueada.", "CUENTA-001");
    }
}