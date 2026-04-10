package modelo.personas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import modelo.abstractas.Cliente;
import modelo.abstractas.Cuenta;
import modelo.enums.TipoDocumento;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Notificable;

public class ClienteNatural extends Cliente
        implements Consultable, Notificable, Auditable {

    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private boolean aceptaNotificaciones;

    private Cuenta[] cuentas;
    private int totalCuentas;
    private static final int MAX_CUENTAS = 5;

    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public ClienteNatural(String id, String nombre, String apellido,
                          LocalDate fechaNacimiento, String email,
                          TipoDocumento tipoDocumento, String numeroDocumento,
                          boolean aceptaNotificaciones) {
        super(id, nombre, apellido, fechaNacimiento, email);
        setTipoDocumento(tipoDocumento);
        setNumeroDocumento(numeroDocumento);
        this.aceptaNotificaciones = aceptaNotificaciones;
        this.cuentas = new Cuenta[MAX_CUENTAS];
        this.totalCuentas = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }


   @Override
public int calcularEdad() {
    LocalDate nacimiento = getFechaNacimiento();
    return Period.between(nacimiento, LocalDate.now()).getYears();
}

    @Override
    public String obtenerTipo() {
        return "Cliente Natural";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return tipoDocumento + ": " + numeroDocumento;
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
        return "Cliente Natural: " + getNombreCompleto()
               + " | " + obtenerDocumentoIdentidad()
               + " | Cuentas: " + totalCuentas
               + " | Edad: " + calcularEdad();
    }

    @Override
    public boolean estaActivo() {
        return true;
    }


    @Override
public void notificar(String mensaje) {
    if (aceptaNotificaciones) {
        String correo = getEmail();
        System.out.println("? Notificación para " + getNombreCompleto()
                           + " (" + correo + "): " + mensaje);
    } else {
        System.out.println("esto" + getNombreCompleto()
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


    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            throw new DatoInvalidoException("tipoDocumento", null);
        }
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new DatoInvalidoException("numeroDocumento", numeroDocumento);
        }
        this.numeroDocumento = numeroDocumento.trim();
    }

    public void setAceptaNotificaciones(boolean aceptaNotificaciones) {
        this.aceptaNotificaciones = aceptaNotificaciones;
    }
}