package modelo.empleados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import modelo.abstractas.Empleado;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.excepciones.PermisoInsuficienteException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;

public class GerenteSucursal extends Empleado implements Consultable, Auditable {

    private String sucursal;
    private double presupuestoAnual;
    private static final double BONO_GERENCIA = 500000.0;

    private Empleado[] empleadosACargo;
    private int totalEmpleadosACargo;
    private static final int MAX_EMPLEADOS = 30;


    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public GerenteSucursal(String id, String nombre, String apellido,
                           LocalDate fechaNacimiento, String email,
                           String legajo, LocalDate fechaContratacion, double salarioBase,
                           String sucursal, double presupuestoAnual) {
        super(id, nombre, apellido, fechaNacimiento, email,
              legajo, fechaContratacion, salarioBase);
        setSucursal(sucursal);
        setPresupuestoAnual(presupuestoAnual);
        this.empleadosACargo = new Empleado[MAX_EMPLEADOS];
        this.totalEmpleadosACargo = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }


    
    @Override
    public double calcularBono() {

        return calcularAntigüedad() * 50000.0;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + calcularBono() + BONO_GERENCIA;
    }


    
    @Override
    public int calcularEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public String obtenerTipo() {
        return "Gerente de Sucursal";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return "Legajo: " + getLegajo();
    }


    
    @Override
    public String obtenerResumen() {
        return "Gerente: " + getNombreCompleto()
               + " | Sucursal: " + sucursal
               + " | Empleados a cargo: " + totalEmpleadosACargo
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



    
    public void aprobarCredito(Empleado solicitante, double monto)
            throws PermisoInsuficienteException {
        if (!(solicitante instanceof GerenteSucursal)) {
            throw new PermisoInsuficienteException(
                "aprobar crédito — solo el Gerente de Sucursal puede hacerlo"
            );
        }
        System.out.println("Crédito de $" + monto + " aprobado por " + getNombreCompleto());
    }


    
    public void agregarEmpleado(Empleado empleado) throws CapacidadExcedidaException {
        if (totalEmpleadosACargo >= MAX_EMPLEADOS) {
            throw new CapacidadExcedidaException("empleados a cargo", MAX_EMPLEADOS);
        }
        empleadosACargo[totalEmpleadosACargo] = empleado;
        totalEmpleadosACargo++;
    }

    public Empleado[] getEmpleadosACargo() {
        Empleado[] copia = new Empleado[totalEmpleadosACargo];
        System.arraycopy(empleadosACargo, 0, copia, 0, totalEmpleadosACargo);
        return copia;
    }


    
    public String getSucursal() { return sucursal; }
    public double getPresupuestoAnual() { return presupuestoAnual; }

    public void setSucursal(String sucursal) {
        if (sucursal == null || sucursal.trim().isEmpty()) {
            throw new DatoInvalidoException("sucursal", sucursal);
        }
        this.sucursal = sucursal.trim();
    }

    public void setPresupuestoAnual(double presupuestoAnual) {
        if (presupuestoAnual < 0) {
            throw new DatoInvalidoException("presupuestoAnual", presupuestoAnual);
        }
        this.presupuestoAnual = presupuestoAnual;
    }
}