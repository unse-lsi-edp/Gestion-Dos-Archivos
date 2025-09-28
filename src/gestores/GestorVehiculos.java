package gestores;

import datos.vehiculos.Automotor;
import datos.vehiculos.Camioneta;
import datos.vehiculos.Motocicleta;
import datos.vehiculos.Vehiculo;
import java.io.IOException;
import persistencia.Archivo;
import persistencia.Registro;
import utilidades.GestorEntradaConsola;

public class GestorVehiculos {

    public Archivo<Vehiculo> archivo;

    public GestorVehiculos() {
        inicializarArchivo();
    }

    public void inicializarArchivo() {
        try {
            archivo = new Archivo("Vehiculos.dat", new Vehiculo());
            if (!archivo.getFd().exists()) {
                archivo.crearArchivoVacio(new Vehiculo());
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void opciones() {
        System.out.println("---Menu Vehiculos---");
        System.out.println("1. Registrar un nuevo vehiculo");
        System.out.println("2. Listadar todos los vehiculos");
        System.out.println("0. Salir");
    }

    void registrarNuevoVehiculo() {
        archivo.abrirParaLeerEscribir();
        do {
            Vehiculo nuevoRegVehiculo = crearRegVehiculo();
            nuevoRegVehiculo.setNroOrden(nuevoRegVehiculo.getCodigo());
            nuevoRegVehiculo.setEstado(true);
            archivo.grabarRegistro(nuevoRegVehiculo);
        } while (GestorEntradaConsola.confirmar());
        archivo.cerrarArchivo();
    }

    /**
     * Carga de datos de manera polimorfica.
     *
     * @return Una instancia de Vehiculo segun su subtipo.
     */
    Vehiculo crearRegVehiculo() {

        boolean esValido = false;
        int opcion = 0;
        Vehiculo v = new Vehiculo();
        System.out.println("Tipo de Vehiculo: ");
        System.out.println("1. Auto");
        System.out.println("2. Moto");
        System.out.println("3. Camioneta");
        System.out.print("Seleccione una opcion: ");

        while (!esValido) {
            opcion = GestorEntradaConsola.leerEntero();
            switch (opcion) {
                case 1:
                    v = new Automotor();
                    v.setTipoVehiculo('A');
                    break;
                case 2:
                    v = new Motocicleta();
                    v.setTipoVehiculo('M');
                    break;
                case 3:
                    v = new Camioneta();
                    v.setTipoVehiculo('C');
                    break;
                default:
                    System.out.println("Opcion invalida!");
            }
            esValido = opcion == 1 || opcion == 2 || opcion == 3;
        }

        v.cargarNuevoVehiculo();
        return v;
    }

    public void listado() {
        Vehiculo regVehiculo;
        char tipoVehiculo;
        int nroOrden;
        archivo.abrirParaLectura();
        while (!archivo.eof()) {
            regVehiculo = archivo.leerRegistro();
            if (regVehiculo.getEstado()) {
                tipoVehiculo = regVehiculo.getTipoVehiculo();
                nroOrden = regVehiculo.getNroOrden();
                regVehiculo = determinarTipo(tipoVehiculo, nroOrden);
                regVehiculo.mostrarRegistro();
            }
        }
        archivo.cerrarArchivo();
    }

    /**
     * Lista todos los vehiculos cuyo estado sea 'D'
     */
    public void listarDisponibles() {
        Vehiculo regVehiculo;
        char tipoVehiculo;
        int nroOrden;
        char estadoVehiculo;
        archivo.abrirParaLectura();
        while (!archivo.eof()) {
            regVehiculo = archivo.leerRegistro();
            if (regVehiculo.getEstado()) {
                estadoVehiculo = regVehiculo.getEstadoVehiculo();
                if (estadoVehiculo == 'D') {
                    tipoVehiculo = regVehiculo.getTipoVehiculo();
                    nroOrden = regVehiculo.getNroOrden();
                    regVehiculo = determinarTipo(tipoVehiculo, nroOrden);
                    regVehiculo.mostrarRegistro();
                }
            }
        }
        archivo.cerrarArchivo();
    }

    /**
     * Determina de que tipo es un registro del archivo
     *
     * @param tipoVehiculo caracter que representa el tipo de vehiculo.
     * @param nroOrden nro de orden para revobinar el archivo.
     * @return
     */
    Vehiculo determinarTipo(char tipoVehiculo, int nroOrden) {
        Vehiculo temp;
        archivo.posicionarRAF(nroOrden);

        switch (tipoVehiculo) {
            case 'A':
                temp = (Automotor) archivo.leerRegistroHijo(Automotor.class);
                break;
            case 'M':
                temp = (Motocicleta) archivo.leerRegistroHijo(Motocicleta.class);
                break;
            case 'C':
                temp = (Camioneta) archivo.leerRegistroHijo(Camioneta.class);
                break;
            default:
                temp = archivo.leerRegistroHijo(Vehiculo.class);
        }
        return temp;
    }

    public void menu() {
        int op;
        do {
            opciones();
            System.out.println("Ingresar opción: ");
            op = GestorEntradaConsola.leerEntero();
            switch (op) {
                case 1:
                    registrarNuevoVehiculo();
                    break;
                case 2:
                    listado();
                    break;
            }
        } while (op != 0);
    }

    public Archivo<Vehiculo> getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo<Vehiculo> archivo) {
        this.archivo = archivo;
    }

}
