package modelo.personas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import modelo.abstractas.Cliente;
import modelo.abstractas.Cuenta;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Notificable;

public class ClienteEmpresarial extends Cliente
        implements Consultable, Notificable, Auditable {

    private String nit;
    private String razonSocial;
    private String representanteLegal;
    private boolean aceptaNotificaciones;

    private Cuenta[] cuentas;
    private int totalCuentas;
    private static final int MAX_CUENTAS = 5;

    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public ClienteEmpresarial(String id, String nombre, String apellido,
                               LocalDate fechaNacimiento, String email,
                               String nit, String razonSocial,
                               String representanteLegal,
                               boolean aceptaNotificaciones) {
        super(id, nombre, apellido, fechaNacimiento, email);
        setNit(nit);
        setRazonSocial(razonSocial);
        setRepresentanteLegal(representanteLegal);
        this.aceptaNotificaciones = aceptaNotificaciones;
        this.cuentas = new Cuenta[MAX_CUENTAS];
        this.totalCuentas = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }


    @Override
    public int calcularEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public String obtenerTipo() {
        return "Cliente Empresarial";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return "NIT: " + nit + " | Razón Social: " + razonSocial;
    }


    @Override
    public Cuenta[] getCuentas() {
        Cuenta[] copia = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, copia, 0, totalCuentas);
        return copia;
    }


    public void agregarCuenta(Cuenta cuenta) throws CapacidadExcedidaException {
        if (totalCuentas >= MAX_CUENTAS) {
            throw new CapacidadExcedidaException("cuentas del cliente", MAX_CUENTAS);
        }
        cuentas[totalCuentas] = cuenta;
        totalCuentas++;
    }


    @Override
    public String obtenerResumen() {
        return "Cliente Empresarial: " + razonSocial
               + " | NIT: " + nit
               + " | Representante: " + representanteLegal
               + " | Cuentas: " + totalCuentas;
    }

    @Override
    public boolean estaActivo() {
        return true;
    }


    @Override
    public void notificar(String mensaje) {
        if (aceptaNotificaciones) {
            System.out.println(" Notificación para " + razonSocial
                               + " (" + getEmail() + "): " + mensaje);
        } else {
            System.out.println(" " + razonSocial
                               + " no acepta notificaciones.");
        }
    }

    @Override
    public String obtenerContacto() {
        return getEmail();
    }

    @Override
    public boolean aceptaNotificaciones() {
        return aceptaNotificaciones;
    }


    @Override
    public LocalDateTime obtenerFechaCreacion() { return fechaCreacion; }

    @Override
    public LocalDateTime obtenerUltimaModificacion() { return ultimaModificacion; }

    @Override
    public String obtenerUsuarioModificacion() { return usuarioModificacion; }

    @Override
    public void registrarModificacion(String usuario) {
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario;
    }


    public String getNit() { return nit; }
    public String getRazonSocial() { return razonSocial; }
    public String getRepresentanteLegal() { return representanteLegal; }

    public void setNit(String nit) {
        if (nit == null || nit.trim().isEmpty()) {
            throw new DatoInvalidoException("nit", nit);
        }
        this.nit = nit.trim();
    }

    public void setRazonSocial(String razonSocial) {
        if (razonSocial == null || razonSocial.trim().isEmpty()) {
            throw new DatoInvalidoException("razonSocial", razonSocial);
        }
        this.razonSocial = razonSocial.trim();
    }

    public void setRepresentanteLegal(String representanteLegal) {
        if (representanteLegal == null || representanteLegal.trim().isEmpty()) {
            throw new DatoInvalidoException("representanteLegal", representanteLegal);
        }
        this.representanteLegal = representanteLegal.trim();
    }

    public void setAceptaNotificaciones(boolean aceptaNotificaciones) {
        this.aceptaNotificaciones = aceptaNotificaciones;
    }
}