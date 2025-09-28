package gestores;

import datos.Cliente;
import persistencia.Archivo;
import utilidades.GestorEntradaConsola;

public class GestorClientes {

    Archivo<Cliente> archivo;

    public GestorClientes() {
        inicializarArchivo();
    }

    public void inicializarArchivo() {
        try {
            archivo = new Archivo("Clientes.dat", new Cliente());
            if (!archivo.getFd().exists()) {
                archivo.crearArchivoVacio(new Cliente());
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void listado() {
        Cliente regCliente;
        archivo.abrirParaLectura();
        while (!archivo.eof()) {
            regCliente = archivo.leerRegistro();
            if (regCliente.getEstado()) {
                regCliente.mostrarRegistro();
            }
        }
        archivo.cerrarArchivo();
    }

    void registrarNuevoCliente() {
        archivo.abrirParaLeerEscribir();
        do {
            Cliente nuevoRegCliente = new Cliente();
            nuevoRegCliente.cargarDatos();
            nuevoRegCliente.setNroOrden(nuevoRegCliente.getDni());
            nuevoRegCliente.setEstado(true);
            archivo.grabarRegistro(nuevoRegCliente);
        } while (GestorEntradaConsola.confirmar());
        archivo.cerrarArchivo();
    }

    void opciones() {
        System.out.println("---Menu Clientes---");
        System.out.println("1. Registrar nuevo cliente");
        System.out.println("2. Listar todos los clientes");
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
                    registrarNuevoCliente();
                    break;
                case 2:
                    listado();
                    break;
            }
        } while (op != 0);
    }


    public Archivo<Cliente> getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo<Cliente> archivo) {
        this.archivo = archivo;
    }

}
