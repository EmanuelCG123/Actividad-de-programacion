package modelo.banco;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.enums.EstadoTransaccion;
import modelo.excepciones.EstadoTransaccionInvalidoException;

public class Transaccion {

    private String id;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private double monto;
    private EstadoTransaccion estado;
    private LocalDateTime fecha;
    private String descripcion;

    public Transaccion(String id, Cuenta cuentaOrigen, Cuenta cuentaDestino,
                       double monto, String descripcion) {
        this.id = id;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.descripcion = descripcion;
        this.estado = EstadoTransaccion.PENDIENTE; 
        this.fecha = LocalDateTime.now();
    }


    public void cambiarEstado(EstadoTransaccion nuevo) {
        if (!transicionValida(estado, nuevo)) {
            throw new EstadoTransaccionInvalidoException(
                estado.toString(), nuevo.toString()
            );
        }
        this.estado = nuevo;
    }

    
    private boolean transicionValida(EstadoTransaccion actual, EstadoTransaccion nuevo) {
        switch (actual) {
            case PENDIENTE:
                return nuevo == EstadoTransaccion.PROCESANDO
                    || nuevo == EstadoTransaccion.RECHAZADA;
            case PROCESANDO:
                return nuevo == EstadoTransaccion.COMPLETADA
                    || nuevo == EstadoTransaccion.RECHAZADA;
            case COMPLETADA:
                return nuevo == EstadoTransaccion.REVERTIDA;
            case RECHAZADA:
                return false; 
            case REVERTIDA:
                return false; 
            default:
                return false;
        }
    }


    public String generarComprobante() {
        String destino = (cuentaDestino != null)
                ? cuentaDestino.getNumeroCuenta()
                : "";

        return "╔══════════════════════════════════════╗"
             + "        COMPROBANTE DE TRANSACCIÓN      "
             + "╠══════════════════════════════════════╣"
             + "  ID:          " + id + "\n"
             + "  Fecha:       " + fecha + "\n"
             + "  Origen:      " + cuentaOrigen.getNumeroCuenta() + "\n"
             + "  Destino:     " + destino + "\n"
             + "  Monto:       $" + monto + "\n"
             + "  Estado:      " + estado + "\n"
             + "  Descripción: " + descripcion + "\n"
             + "╚══════════════════════════════════════╝";
    }

   
    public String getId() { return id; }
    public Cuenta getCuentaOrigen() { return cuentaOrigen; }
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public double getMonto() { return monto; }
    public EstadoTransaccion getEstado() { return estado; }
    public LocalDateTime getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return "Transaccion{id='" + id + "', monto=$" + monto
               + ", estado=" + estado + ", fecha=" + fecha + "}";
    }
}