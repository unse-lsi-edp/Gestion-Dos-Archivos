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
        System.out.println("1. Alta");
        System.out.println("2. Listado");
        System.out.println("0. Salir");
    }

    void alta() {
        archivo.abrirParaLeerEscribir();
        do {
            Vehiculo nuevoRegVehiculo = crearRegVehiculo();
            nuevoRegVehiculo.setNroOrden(nuevoRegVehiculo.getCodigo());
            nuevoRegVehiculo.setEstado(true);
            archivo.grabarRegistro(nuevoRegVehiculo);
        } while (GestorEntradaConsola.confirmar());
        archivo.cerrarArchivo();
    }

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

        v.cargarDatos();
        return v;
    }

    public void listado() {
        Vehiculo regVehiculo;
        char tipoVehiculo;
        archivo.abrirParaLectura();
        while (!archivo.eof()) {
            regVehiculo = archivo.leerRegistro();
            if (regVehiculo.getEstado()) {
                tipoVehiculo = regVehiculo.getTipoVehiculo();
                mostrarVehiculoSegunTipo(tipoVehiculo, regVehiculo.getNroOrden());
            }
        }
        archivo.cerrarArchivo();
    }
    
    /**
     * Devuelve al puntero (RandomAcessFile) a su posicion inicial, y luego
     * en base al tipo de vehiculo previamente cargado, llama al metodo leer
     * correspondiente.
     * @param tipoVehiculo el caracter que representa al tipo
     * @param nroOrden del registro a mostrar
     */
    void mostrarVehiculoSegunTipo(char tipoVehiculo, int nroOrden) {

        Vehiculo temp;
        archivo.buscarRegistro(nroOrden);

        switch (tipoVehiculo) {
            case 'A':
                temp = archivo.leerRegistroHijo(Automotor.class);
                break;
            case 'M':
                temp = archivo.leerRegistroHijo(Motocicleta.class);
                break;
            case 'C':
                temp = archivo.leerRegistroHijo(Camioneta.class);
                break;
            default:
                temp = archivo.leerRegistroHijo(Vehiculo.class);
        }

        temp.mostrarRegistro();
    }

    public void menu() {
        int op;
        do {
            opciones();
            System.out.println("Ingresar opción: ");
            op = GestorEntradaConsola.leerEntero();
            switch (op) {
                case 1:
                    alta();
                    break;
                case 2:
                    listado();
                    break;
            }
        } while (op != 0);
    }

    public static void main(String[] args) {
        GestorVehiculos app = new GestorVehiculos();
        app.menu();
    }
}
