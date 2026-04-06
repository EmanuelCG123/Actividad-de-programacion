package modelo.banco;

import java.time.LocalDateTime;
import modelo.abstractas.Cliente;
import modelo.abstractas.Cuenta;
import modelo.abstractas.Empleado;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.ClienteNoEncontradoException;
import modelo.interfaces.Auditable;

public class Banco implements Auditable {

    private String nombre;

    private Empleado[] empleados;
    private int totalEmpleados;
    private static final int MAX_EMPLEADOS = 50;

    private Cliente[] clientes;
    private int totalClientes;
    private static final int MAX_CLIENTES = 200;

    private Cuenta[] cuentas;
    private int totalCuentas;
    private static final int MAX_CUENTAS = 500;

    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public Banco(String nombre) {
        this.nombre = nombre;
        this.empleados = new Empleado[MAX_EMPLEADOS];
        this.totalEmpleados = 0;
        this.clientes = new Cliente[MAX_CLIENTES];
        this.totalClientes = 0;
        this.cuentas = new Cuenta[MAX_CUENTAS];
        this.totalCuentas = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }


    public void registrarCliente(Cliente c) throws CapacidadExcedidaException {
        if (totalClientes >= MAX_CLIENTES) {
            throw new CapacidadExcedidaException("clientes", MAX_CLIENTES);
        }
        clientes[totalClientes] = c;
        totalClientes++;
        registrarModificacion("SISTEMA");
    }


    public void registrarEmpleado(Empleado e) throws CapacidadExcedidaException {
        if (totalEmpleados >= MAX_EMPLEADOS) {
            throw new CapacidadExcedidaException("empleados", MAX_EMPLEADOS);
        }
        empleados[totalEmpleados] = e;
        totalEmpleados++;
        registrarModificacion("SISTEMA");
    }


    public void abrirCuenta(String idCliente, Cuenta c)
            throws ClienteNoEncontradoException, CapacidadExcedidaException {
        Cliente cliente = buscarCliente(idCliente);
        if (totalCuentas >= MAX_CUENTAS) {
            throw new CapacidadExcedidaException("cuentas", MAX_CUENTAS);
        }
        cuentas[totalCuentas] = c;
        totalCuentas++;
        registrarModificacion("SISTEMA");
    }


    public Cliente buscarCliente(String id) throws ClienteNoEncontradoException {
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i].getId().equals(id)) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException(id);
    }


    public double calcularNominaTotal() {
        double total = 0;
        for (int i = 0; i < totalEmpleados; i++) {
            total += empleados[i].calcularSalario();
        }
        return total;
    }

    
    public void calcularInteresesMensuales() {
        System.out.println(" INTERESES MENSUALES ");
        for (int i = 0; i < totalCuentas; i++) {
            double interes = cuentas[i].calcularInteres();
            System.out.println(cuentas[i].getTipoCuenta()
                    + " N°" + cuentas[i].getNumeroCuenta()
                    + " → Interés: $" + interes);
        }
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


    public String getNombre() { return nombre; }
    public int getTotalClientes() { return totalClientes; }
    public int getTotalEmpleados() { return totalEmpleados; }
    public int getTotalCuentas() { return totalCuentas; }

    public Empleado[] getEmpleados() {
        Empleado[] copia = new Empleado[totalEmpleados];
        System.arraycopy(empleados, 0, copia, 0, totalEmpleados);
        return copia;
    }

    public Cliente[] getClientes() {
        Cliente[] copia = new Cliente[totalClientes];
        System.arraycopy(clientes, 0, copia, 0, totalClientes);
        return copia;
    }

    public Cuenta[] getCuentas() {
        Cuenta[] copia = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, copia, 0, totalCuentas);
        return copia;
    }

    @Override
    public String toString() {
        return "Banco: " + nombre
               + " | Clientes: " + totalClientes
               + " | Empleados: " + totalEmpleados
               + " | Cuentas: " + totalCuentas;
    }
}