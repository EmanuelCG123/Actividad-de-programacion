package modelo.empleados;

import java.time.LocalDate;
import java.time.Period;
import modelo.abstractas.Empleado;
import modelo.enums.Turno;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import java.time.LocalDateTime;
import modelo.excepciones.DatoInvalidoException;

public class Cajero extends Empleado implements Consultable, Auditable {

    private Turno turno;
    private String sucursalAsignada;
    private int transaccionesDia;

    
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public Cajero(String id, String nombre, String apellido,
                  LocalDate fechaNacimiento, String email,
                  String legajo, LocalDate fechaContratacion, double salarioBase,
                  Turno turno, String sucursalAsignada) {
        super(id, nombre, apellido, fechaNacimiento, email,
              legajo, fechaContratacion, salarioBase);
        setTurno(turno);
        setSucursalAsignada(sucursalAsignada);
        this.transaccionesDia = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

   

    @Override
    public double calcularBono() {
        return transaccionesDia * 2000.0;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + calcularBono();
    }

    

    @Override
    public int calcularEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public String obtenerTipo() {
        return "Cajero";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return "Legajo: " + getLegajo();
    }

   

    @Override
    public String obtenerResumen() {
        return "Cajero: " + getNombreCompleto()
               + " | Sucursal: " + sucursalAsignada
               + " | Turno: " + turno
               + " | Salario: $" + calcularSalario();
    }

    @Override
    public boolean estaActivo() {
        return isActivo();
    }

    

    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return ultimaModificacion;
    }

    @Override
    public String obtenerUsuarioModificacion() {
        return usuarioModificacion;
    }

    @Override
    public void registrarModificacion(String usuario) {
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario;
    }

   

    public Turno getTurno() { return turno; }
    public String getSucursalAsignada() { return sucursalAsignada; }
    public int getTransaccionesDia() { return transaccionesDia; }

    public void setTurno(Turno turno) {
        if (turno == null) {
            throw new DatoInvalidoException("turno", null);
        }
        this.turno = turno;
    }

    public void setSucursalAsignada(String sucursalAsignada) {
        if (sucursalAsignada == null || sucursalAsignada.trim().isEmpty()) {
            throw new DatoInvalidoException("sucursalAsignada", sucursalAsignada);
        }
        this.sucursalAsignada = sucursalAsignada.trim();
    }

    public void registrarTransaccion() {
        this.transaccionesDia++;
    }
}