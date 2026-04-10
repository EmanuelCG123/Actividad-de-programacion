package modelo.empleados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import modelo.abstractas.Cliente;
import modelo.abstractas.Empleado;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;

public class AsesorFinanciero extends Empleado implements Consultable, Auditable {

    private double comisionBase;
    private double metasMensuales;

    private Cliente[] clientesAsignados;
    private int totalClientesAsignados;
    private static final int MAX_CLIENTES = 20;

    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public AsesorFinanciero(String id, String nombre, String apellido,
                            LocalDate fechaNacimiento, String email,
                            String legajo, LocalDate fechaContratacion, double salarioBase,
                            double comisionBase, double metasMensuales) {
        super(id, nombre, apellido, fechaNacimiento, email,
              legajo, fechaContratacion, salarioBase);
        setComisionBase(comisionBase);
        setMetasMensuales(metasMensuales);
        this.clientesAsignados = new Cliente[MAX_CLIENTES];
        this.totalClientesAsignados = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }


    @Override
    public double calcularBono() {
        return (totalClientesAsignados >= metasMensuales) ? comisionBase : 0;
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
        return "Asesor Financiero";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return "Legajo: " + getLegajo();
    }


    @Override
    public String obtenerResumen() {
        return "Asesor: " + getNombreCompleto()
               + " | Clientes asignados: " + totalClientesAsignados
               + " | Salario: $" + calcularSalario();
    }

    @Override
    public boolean estaActivo() {
        return isActivo();
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


    public void asignarCliente(Cliente cliente) throws CapacidadExcedidaException {
        if (totalClientesAsignados >= MAX_CLIENTES) {
            throw new CapacidadExcedidaException("clientes del asesor", MAX_CLIENTES);
        }
        clientesAsignados[totalClientesAsignados] = cliente;
        totalClientesAsignados++;
    }

    public Cliente[] getClientesAsignados() {
        Cliente[] copia = new Cliente[totalClientesAsignados];
        System.arraycopy(clientesAsignados, 0, copia, 0, totalClientesAsignados);
        return copia;
    }


    public double getComisionBase() { return comisionBase; }
    public double getMetasMensuales() { return metasMensuales; }

    public void setComisionBase(double comisionBase) {
        if (comisionBase < 0) {
            throw new DatoInvalidoException("comisionBase", comisionBase);
        }
        this.comisionBase = comisionBase;
    }

    public void setMetasMensuales(double metasMensuales) {
        if (metasMensuales < 0) {
            throw new DatoInvalidoException("metasMensuales", metasMensuales);
        }
        this.metasMensuales = metasMensuales;
    }
}