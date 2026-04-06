package modelo.abstractas;

import java.time.LocalDate;
import java.time.Period;
import modelo.excepciones.DatoInvalidoException;

public abstract class Empleado extends Persona {

    private String legajo;
    private LocalDate fechaContratacion;
    private double salarioBase;
    private boolean activo;

    public Empleado(String id, String nombre, String apellido,
                    LocalDate fechaNacimiento, String email,
                    String legajo, LocalDate fechaContratacion, double salarioBase) {
       
        super(id, nombre, apellido, fechaNacimiento, email);
        setLegajo(legajo);
        setFechaContratacion(fechaContratacion);
        setSalarioBase(salarioBase);
        this.activo = true;
    }

   
    public abstract double calcularSalario();
    public abstract double calcularBono();

    
    public int calcularAntigüedad() {
        return Period.between(fechaContratacion, LocalDate.now()).getYears();
    }

 
    public String getLegajo() { return legajo; }
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public double getSalarioBase() { return salarioBase; }
    public boolean isActivo() { return activo; }

    
    public final void setLegajo(String legajo) {
        if (legajo == null || legajo.trim().isEmpty()) {
            throw new DatoInvalidoException("legajo", legajo);
        }
        this.legajo = legajo.trim();
    }

    public final void setFechaContratacion(LocalDate fechaContratacion) {
        if (fechaContratacion == null || fechaContratacion.isAfter(LocalDate.now())) {
            throw new DatoInvalidoException("fechaContratacion", fechaContratacion);
        }
        this.fechaContratacion = fechaContratacion;
    }

    public final void setSalarioBase(double salarioBase) {
        if (salarioBase <= 0) {
            throw new DatoInvalidoException("salarioBase", salarioBase);
        }
        this.salarioBase = salarioBase;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}