package modelo.cuentas;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Transaccionable;

public class CuentaCredito extends Cuenta
        implements Consultable, Transaccionable, Auditable {

    private double limiteCredito;
    private double tasaInteres;
    private double deudaActual;

    public CuentaCredito(String numeroCuenta, double limiteCredito, double tasaInteres) {
        super(numeroCuenta, 0);
        setLimiteCredito(limiteCredito);
        setTasaInteres(tasaInteres);
        this.deudaActual = 0;
    }


    @Override
    public double calcularInteres() {
        return deudaActual * tasaInteres / 12;
    }

    @Override
    public double getLimiteRetiro() {
        return limiteCredito - deudaActual;
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta Crédito";
    }


    @Override
    public void depositar(double monto) throws CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        deudaActual = Math.max(0, deudaActual - monto);
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public void retirar(double monto) throws CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        if (monto > getLimiteRetiro()) {
            throw new DatoInvalidoException("monto supera límite de crédito", monto);
        }
        deudaActual += monto;
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public double calcularComision(double monto) {
        return monto * 0.02;
    }

    @Override
    public double consultarSaldo() {
        return getLimiteRetiro();
    }


    @Override
    public String obtenerResumen() {
        return getTipoCuenta() + " N°" + getNumeroCuenta()
               + " | Límite: $" + limiteCredito
               + " | Deuda: $" + deudaActual
               + " | Disponible: $" + getLimiteRetiro();
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


    public double getLimiteCredito() { return limiteCredito; }
    public double getTasaInteres() { return tasaInteres; }
    public double getDeudaActual() { return deudaActual; }

    public void setLimiteCredito(double limiteCredito) {
        if (limiteCredito <= 0) {
            throw new DatoInvalidoException("limiteCredito", limiteCredito);
        }
        this.limiteCredito = limiteCredito;
    }

    public void setTasaInteres(double tasaInteres) {
        if (tasaInteres <= 0) {
            throw new DatoInvalidoException("tasaInteres", tasaInteres);
        }
        this.tasaInteres = tasaInteres;
    }
}