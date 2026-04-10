package principal.SistemaBancarioDemo;

import java.time.LocalDate;
import modelo.banco.Banco;
import modelo.banco.Transaccion;
import modelo.abstractas.Cuenta;
import modelo.abstractas.Empleado;
import modelo.cuentas.CuentaAhorros;
import modelo.cuentas.CuentaCorriente;
import modelo.cuentas.CuentaCredito;
import modelo.empleados.AsesorFinanciero;
import modelo.empleados.Cajero;
import modelo.empleados.GerenteSucursal;
import modelo.enums.EstadoTransaccion;
import modelo.enums.TipoDocumento;
import modelo.enums.Turno;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.ClienteNoEncontradoException;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.EstadoTransaccionInvalidoException;
import modelo.excepciones.PermisoInsuficienteException;
import modelo.excepciones.SaldoInsuficienteException;
import modelo.interfaces.Notificable;
import modelo.personas.ClienteEmpresarial;
import modelo.personas.ClienteNatural;

public class SistemaBancarioDemo {

    public static void main(String[] args) {

        
        System.out.println("       SISTEMA DE GESTION BANCARIA          ");              
        Banco banco = new Banco("Banco Central los goats");

        
        System.out.println(" ESCENARIO 1: Registrar clientes ");

        try {
            ClienteNatural cliente1 = new ClienteNatural(
                "C001", "Emanuel", "Correa",
                LocalDate.of(1990, 5, 15), "manu@email.com",
                TipoDocumento.CEDULA, "123456789", true
            );

            ClienteNatural cliente2 = new ClienteNatural(
                "C002", "sara", "Gómez",
                LocalDate.of(1985, 8, 20), "sara@email.com",
                TipoDocumento.PASAPORTE, "987654321", false
            );

            ClienteEmpresarial cliente3 = new ClienteEmpresarial(
                "C003", "Carlos", "Lopez",
                LocalDate.of(1975, 3, 10), "empresa@email.com",
                "900123156-1", "Empresa XYZ S.A.S",
                "Carlos López", true
            );

            banco.registrarCliente(cliente1);
            banco.registrarCliente(cliente2);
            banco.registrarCliente(cliente3);

            System.out.println(" Clientes registrados: " + banco.getTotalClientes());
            System.out.println(cliente1.obtenerResumen());
            System.out.println(cliente2.obtenerResumen());
            System.out.println(cliente3.obtenerResumen());

        } catch (CapacidadExcedidaException e) {
            System.out.println(" " + e.getMessage());
        }

       
        System.out.println(" ESCENARIO 2: Abrir cuentas ");

        CuentaAhorros cuentaAhorros = new CuentaAhorros("AH001", 500000, 0.05, 5);
        CuentaCorriente cuentaCorriente = new CuentaCorriente("CC001", 1000000, 200000, 15000);
        CuentaCredito cuentaCredito = new CuentaCredito("CR001", 3000000, 0.02);

        try {
            banco.abrirCuenta("C001", cuentaAhorros);
            banco.abrirCuenta("C001", cuentaCorriente);
            banco.abrirCuenta("C002", cuentaCredito);
            System.out.println(" Cuentas abiertas: " + banco.getTotalCuentas());
            System.out.println(cuentaAhorros.obtenerResumen());
            System.out.println(cuentaCorriente.obtenerResumen());
            System.out.println(cuentaCredito.obtenerResumen());
        } catch (ClienteNoEncontradoException | CapacidadExcedidaException e) {
            System.out.println(" " + e.getMessage());
        }

        
        System.out.println(" ESCENARIO 3: Depósitos ");

        try {
            cuentaAhorros.depositar(100000);
            System.out.println(" Depósito exitoso. Saldo: " + cuentaAhorros.consultarSaldo());
        } catch (CuentaBloqueadaException e) {
            System.out.println(" " + e.getMessage());
        }

        
        cuentaCorriente.bloquear();
        try {
            cuentaCorriente.depositar(50000);
        } catch (CuentaBloqueadaException e) {
            System.out.println(" Excepción capturada correctamente: " + e.getMessage());
        }
        cuentaCorriente.desbloquear();

       
        System.out.println(" ESCENARIO 4: Retiros ");

        try {
            cuentaAhorros.retirar(100000);
            System.out.println(" Retiro exitoso. Saldo: $" + cuentaAhorros.consultarSaldo());
        } catch (SaldoInsuficienteException | CuentaBloqueadaException e) {
            System.out.println(" " + e.getMessage());
        }

        try {
            cuentaAhorros.retirar(999999999);
        } catch (SaldoInsuficienteException e) {
            System.out.println(" Excepción capturada: " + e.getMessage());
            System.out.println("   Saldo actual: $" + e.getSaldoActual());
            System.out.println("   Monto solicitado: $" + e.getMontoSolicitado());
        } catch (CuentaBloqueadaException e) {
            System.out.println(" " + e.getMessage());
        }

       
        System.out.println(" ESCENARIO 5: Transferencia ");

        try {
            double montoTransferencia = 100000;
            cuentaAhorros.retirar(montoTransferencia);
            cuentaCorriente.depositar(montoTransferencia);

            Transaccion transferencia = new Transaccion(
                "T001", cuentaAhorros, cuentaCorriente,
                montoTransferencia, "Transferencia entre cuentas"
            );
            transferencia.cambiarEstado(EstadoTransaccion.PROCESANDO);
            transferencia.cambiarEstado(EstadoTransaccion.COMPLETADA);
            System.out.println(" Transferencia completada.");
            System.out.println(transferencia.generarComprobante());

        } catch (SaldoInsuficienteException | CuentaBloqueadaException e) {
            System.out.println(" " + e.getMessage());
        }

      
        System.out.println(" ESCENARIO 6: Salarios de empleados ");

        Cajero cajero = new Cajero(
            "E001", "Gojo", "satoru",
            LocalDate.of(1995, 2, 10), "gojo@banco.com",
            "LEG001", LocalDate.of(2020, 1, 15),
            1500000, Turno.MAÑANA, "Sucursal Norte"
        );

        AsesorFinanciero asesor = new AsesorFinanciero(
            "E002", "Laura", "Martínez",
            LocalDate.of(1988, 7, 22), "laura@banco.com",
            "LEG002", LocalDate.of(2018, 3, 10),
            2000000, 500000, 10
        );

        GerenteSucursal gerente = new GerenteSucursal(
            "E003", "Roberto", "Silva",
            LocalDate.of(1980, 11, 5), "roberto@banco.com",
            "LEG003", LocalDate.of(2010, 6, 1),
            4000000, "Sucursal Centro", 50000000
        );

        try {
            banco.registrarEmpleado(cajero);
            banco.registrarEmpleado(asesor);
            banco.registrarEmpleado(gerente);
        } catch (CapacidadExcedidaException e) {
            System.out.println(" " + e.getMessage());
        }

       
        Empleado[] empleados = banco.getEmpleados();
        for (int i = 0; i < empleados.length; i++) {
            System.out.println(" " + empleados[i].obtenerTipo()
                    + " " + empleados[i].getNombreCompleto()
                    + "  Salario: $" + empleados[i].calcularSalario());
        }

      
        System.out.println("ESCENARIO 7: Intereses por tipo de cuenta ");

        Cuenta[] cuentas = banco.getCuentas();
        for (int i = 0; i < cuentas.length; i++) {
            System.out.println(" " + cuentas[i].getTipoCuenta()
                    + " N°" + cuentas[i].getNumeroCuenta()
                    + "  Interés: $" + cuentas[i].calcularInteres());
        }

       
        System.out.println(" ESCENARIO 8: Transición de estado inválida ");

        Transaccion transaccion = new Transaccion(
            "T002", cuentaAhorros, null, 50000, "Retiro en cajero"
        );
        transaccion.cambiarEstado(EstadoTransaccion.PROCESANDO);
        transaccion.cambiarEstado(EstadoTransaccion.COMPLETADA);

        try {
            
            transaccion.cambiarEstado(EstadoTransaccion.PROCESANDO);
        } catch (EstadoTransaccionInvalidoException e) {
            System.out.println(" Excepción capturada: " + e.getMessage());
        }

        System.out.println(" ESCENARIO 9: Permiso insuficiente ");

        try {
            
            gerente.aprobarCredito(cajero, 5000000);
        } catch (PermisoInsuficienteException e) {
            System.out.println(" Excepción capturada: " + e.getMessage());
        }

       
        System.out.println("ESCENARIO 10: Notificaciones ");

        try {
            ClienteNatural clienteConNotif = (ClienteNatural) banco.buscarCliente("C001");
            ClienteNatural clienteSinNotif = (ClienteNatural) banco.buscarCliente("C002");

            clienteConNotif.notificar("Su cuenta ha sido acreditada con $100.000");
            clienteSinNotif.notificar("Su cuenta ha sido acreditada con $100.000");

        } catch (ClienteNoEncontradoException e) {
            System.out.println(" " + e.getMessage());
        }

        
        System.out.println(" ESCENARIO 11: Auditoría ");

        cuentaAhorros.registrarModificacion("LEG001");
        System.out.println("Última modificación: " + cuentaAhorros.obtenerUltimaModificacion());
        System.out.println(" Usuario que modificó: " + cuentaAhorros.obtenerUsuarioModificacion());

       
        System.out.println(" ESCENARIO 12: Nómina total ");

        double nomina = banco.calcularNominaTotal();
        System.out.println(" Nómina total del banco: $" + nomina);

        banco.calcularInteresesMensuales();

       
        System.out.println("         DEMO COMPLETADA EXITOSAMENTE       ");
    }
}