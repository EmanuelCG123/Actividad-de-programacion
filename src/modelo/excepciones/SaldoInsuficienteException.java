package modelo.excepciones;

public class SaldoInsuficienteException extends SistemaBancarioException {

    private double saldoActual;
    private double montoSolicitado;

    public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
        super("Saldo insuficiente. Saldo actual: " + saldoActual
              + ", monto solicitado: " + montoSolicitado, "SALDO-001");
        this.saldoActual = saldoActual;
        this.montoSolicitado = montoSolicitado;
    }

    public double getSaldoActual() { return saldoActual; }
    public double getMontoSolicitado() { return montoSolicitado; }
}