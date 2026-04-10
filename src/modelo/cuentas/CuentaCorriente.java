package modelo.cuentas;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.excepciones.SaldoInsuficienteException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Transaccionable;

public class CuentaCorriente extends Cuenta
        implements Consultable, Transaccionable, Auditable {

    private double montoSobregiro;
    private double comisionMantenimiento;

    public CuentaCorriente(String numeroCuenta, double saldoInicial,
                           double montoSobregiro, double comisionMantenimiento) {
        super(numeroCuenta, saldoInicial);
        setMontoSobregiro(montoSobregiro);
        setComisionMantenimiento(comisionMantenimiento);
    }


    @Override
    public double calcularInteres() {
        return -comisionMantenimiento;
    }

    @Override
    public double getLimiteRetiro() {
        return getSaldo() + montoSobregiro;
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta Corriente";
    }


    @Override
    public void depositar(double monto) throws CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        setSaldo(getSaldo() + monto);
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public void retirar(double monto) throws SaldoInsuficienteException,
                                             CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        if (monto > getLimiteRetiro()) {
            throw new SaldoInsuficienteException(getSaldo(), monto);
        }
        setSaldo(getSaldo() - monto);
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public double calcularComision(double monto) {
        return comisionMantenimiento;
    }

    @Override
    public double consultarSaldo() {
        return getSaldo();
    }


    @Override
    public String obtenerResumen() {
        return getTipoCuenta() + " N°" + getNumeroCuenta()
               + " | Saldo: $" + getSaldo()
               + " | Sobregiro: $" + montoSobregiro
               + " | Comisión: $" + comisionMantenimiento;
    }

    @Override
    public boolean estaActivo() {
        return !isBloqueada();
    }

    @Override
    public String obtenerTipo() {
        return getTipoCuenta();
    }


    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return getFechaCreacion();
    }

    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return getUltimaModificacion();
    }

    @Override
    public String obtenerUsuarioModificacion() {
        return getUsuarioModificacion();
    }

    @Override
    public void registrarModificacion(String usuario) {
        setUltimaModificacion(LocalDateTime.now());
        setUsuarioModificacion(usuario);
    }


    public double getMontoSobregiro() { return montoSobregiro; }
    public double getComisionMantenimiento() { return comisionMantenimiento; }

    public void setMontoSobregiro(double montoSobregiro) {
        if (montoSobregiro < 0) {
            throw new DatoInvalidoException("montoSobregiro", montoSobregiro);
        }
        this.montoSobregiro = montoSobregiro;
    }

    public void setComisionMantenimiento(double comisionMantenimiento) {
        if (comisionMantenimiento < 0) {
            throw new DatoInvalidoException("comisionMantenimiento", comisionMantenimiento);
        }
        this.comisionMantenimiento = comisionMantenimiento;
    }
}