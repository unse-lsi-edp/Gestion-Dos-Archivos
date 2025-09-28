package gestores;

import datos.Cliente;
import datos.vehiculos.Vehiculo;
import persistencia.Archivo;
import persistencia.Registro;
import utilidades.GestorEntradaConsola;

public class GestorAlquiler {

    GestorClientes gestorClientes;
    GestorVehiculos gestorVehiculos;

    public GestorAlquiler() {
        gestorClientes = new GestorClientes();
        gestorVehiculos = new GestorVehiculos();
    }

    void opciones() {
        System.out.println("---Gestion de Alquiler---");
        System.out.println("1. Menu de Vehiculos");
        System.out.println("2. Menu de Clientes");
        System.out.println("3. Rentar un vehiculo");
        System.out.println("4. Listar vehiculos disponibles");
        System.out.println("5. Listar vehiculos rentados por cliente");

        System.out.println("0. Salir");
    }

    public void menu() {
        int op;
        do {
            opciones();
            System.out.println("Ingresar opción: ");
            op = GestorEntradaConsola.leerEntero();
            switch (op) {
                case 1:
                    gestorVehiculos.menu();
                    break;
                case 2:
                    gestorClientes.menu();
                    break;
                case 3:
                    rentarVehiculo();
                    break;
                case 4:
                    break;

            }
        } while (op != 0);
    }

    void rentarVehiculo() {

        Archivo<Vehiculo> archivoVehiculos = gestorVehiculos.getArchivo();
        Archivo<Cliente> archivoClientes = gestorClientes.getArchivo();
        Vehiculo vehiculoParaAlquilar;
        Cliente regCliente;

        if (archivosVacios(archivoVehiculos, archivoClientes)) {
            return;
        }

        vehiculoParaAlquilar = elegirVehiculo(archivoVehiculos);
        if (vehiculoParaAlquilar == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        regCliente = asignarVehiculoCliente(vehiculoParaAlquilar, archivoVehiculos, archivoClientes);
        if (regCliente == null) {
            System.out.println("Operacion cancelada.");
            return;
        }

        System.out.println("Vehiculo: ");
        vehiculoParaAlquilar.mostrarRegistro();
        System.out.println("Rentado a: ");
        regCliente.mostrarRegistro();
    }

    /**
     * @return {@code true} si el archivo de clientes o el archivo de vehiculos
     * esta vacio. {@code false} caso contrario
     */
    boolean archivosVacios(Archivo archivoVehiculos, Archivo archivoClientes) {

        boolean archivoVehiculosVacio = HerramientaArchivo.estaVacio(archivoVehiculos);
        boolean archivoClientesVacio = HerramientaArchivo.estaVacio(archivoClientes);

        if (archivoVehiculosVacio) {
            System.out.println("No hay vehiculos registrados");
            return true;
        }

        if (archivoClientesVacio) {
            System.out.println("No hay clientes registrados");
            return true;
        }

        return false;

    }

    /**
     * Elije un vehiculo del archivo de vehiculos para alquilar
     *
     * @param archivoVehiculo archivo de vehiculos.
     * @return el vehiculo para alquilar o {@code null} si se cancela la
     * operacion
     */
    Vehiculo elegirVehiculo(Archivo<Vehiculo> archivoVehiculo) {

        int codigoVehiculo;
        boolean operacionTerminada = false;
        Vehiculo reg = null;
        do {
            gestorVehiculos.listarDisponibles();
            System.out.print("Elija un vehiculo (Por codigo/0 para cancelar): ");
            codigoVehiculo = GestorEntradaConsola.leerEntero();
            reg = HerramientaArchivo.buscarRegistro(archivoVehiculo, codigoVehiculo);
            if (reg.getEstado()) {
                System.out.println("Se elegio: ");
                reg.mostrarRegistro();
                operacionTerminada = true;
            } else {
                reg = null;
                System.out.println("El vehiculo no esta en el sistema. Elija otro/0 para cancelar");
            }
        } while (codigoVehiculo != 0 && !operacionTerminada);

        return reg;

    }

    /**
     * Asigna el codigo de un cliente a un vehiculo del archivo de vehiculos
     *
     * @param vehiculoParaAlquilar el vehiculo a alquilar
     * @param archivoVehiculos el archivo de vehiculos
     * @param archivoClientes el archivo de clientes
     * @return el cliente al que se le alquilo o el vehiculo o {@code null} si
     * se cancelo la operacion
     */
    Cliente asignarVehiculoCliente(Vehiculo vehiculoParaAlquilar,
            Archivo<Vehiculo> archivoVehiculos,
            Archivo<Cliente> archivoClientes) {

        int dniCliente;
        Cliente regCliente = null;
        boolean operacionTerminada = false;
        do {
            gestorClientes.listado();
            System.out.print("Seleccione un cliente (Por codigo/0 para cancelar): ");
            dniCliente = GestorEntradaConsola.leerEntero();
            regCliente = HerramientaArchivo.buscarRegistro(archivoClientes, dniCliente);
            if (regCliente.getEstado()) {
                archivoVehiculos.abrirParaLeerEscribir();
                vehiculoParaAlquilar.setCodigoCliente(dniCliente);
                vehiculoParaAlquilar.setEstadoVehiculo('A');
                archivoVehiculos.grabarRegistro(vehiculoParaAlquilar);
                archivoVehiculos.cerrarArchivo();
                operacionTerminada = true;
            } else {
                regCliente = null;
                System.out.println("El cliente no esta en el sistema. Elija otro/0 para cancelar");
            }
        } while (dniCliente != 0 && !operacionTerminada);

        return regCliente;
    }
}
