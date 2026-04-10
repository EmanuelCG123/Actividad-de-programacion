package modelo.abstractas;

import java.time.LocalDateTime;
import modelo.banco.Transaccion;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.DatoInvalidoException;

public abstract class Cuenta {

    private String numeroCuenta;
    private double saldo;
    private boolean bloqueada;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    
    private Transaccion[] historial;
    private int totalTransacciones;
    private static final int MAX_HISTORIAL = 20;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        setNumeroCuenta(numeroCuenta);
        setSaldo(saldoInicial);
        this.bloqueada = false;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
        this.historial = new Transaccion[MAX_HISTORIAL];
        this.totalTransacciones = 0;
    }

    
    public abstract double calcularInteres();
    public abstract double getLimiteRetiro();
    public abstract String getTipoCuenta();

   

    public void verificarBloqueada() throws CuentaBloqueadaException {
        if (bloqueada) {
            throw new CuentaBloqueadaException(numeroCuenta);
        }
    }

    public void agregarAlHistorial(Transaccion t) throws CapacidadExcedidaException {
        if (totalTransacciones >= MAX_HISTORIAL) {
            throw new CapacidadExcedidaException("historial de transacciones", MAX_HISTORIAL);
        }
        historial[totalTransacciones] = t;
        totalTransacciones++;
    }

  
    public Transaccion[] getHistorial() {
        Transaccion[] copia = new Transaccion[totalTransacciones];
        System.arraycopy(historial, 0, copia, 0, totalTransacciones);
        return copia;
    }

    public void bloquear() { this.bloqueada = true; }
    public void desbloquear() { this.bloqueada = false; }

   
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public boolean isBloqueada() { return bloqueada; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getUltimaModificacion() { return ultimaModificacion; }
    public String getUsuarioModificacion() { return usuarioModificacion; }

  
    public final void setNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            throw new DatoInvalidoException("numeroCuenta", numeroCuenta);
        }
        this.numeroCuenta = numeroCuenta.trim();
    }

    public void setSaldo(double saldo) {
        if (saldo < 0) {
            throw new DatoInvalidoException("saldo", saldo);
        }
        this.saldo = saldo;
    }

    public void setUltimaModificacion(LocalDateTime ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    @Override
    public String toString() {
        return getTipoCuenta() + " N°" + numeroCuenta + " | Saldo: $" + saldo
               + " | " + (bloqueada ? "BLOQUEADA" : "ACTIVA");
    }
}