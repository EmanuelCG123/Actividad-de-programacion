package modelo.interfaces;

import modelo.excepciones.SistemaBancarioException;

public interface Transaccionable {

    void depositar(double monto) throws SistemaBancarioException;

    void retirar(double monto) throws SistemaBancarioException;

    double calcularComision(double monto);

    double consultarSaldo();
}