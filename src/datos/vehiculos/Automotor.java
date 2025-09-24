package datos.vehiculos;

import java.io.IOException;
import java.io.RandomAccessFile;
import utilidades.GestorEntradaConsola;

public class Automotor extends Vehiculo {

    private int cantidadPuertas;
    private char tipoTransimision; // "A" Automatico, "M" Manual

    private final int TAM_REG_AUTOMOTOR = 4 + 2;
    private final int BYTE_DIFF = TAM_REG - TAM_REG_AUTOMOTOR;

    public Automotor() {
        super();
        cantidadPuertas = 0;
        tipoTransimision = 0;
    }

    public Automotor(int orden) {
        super(orden);
        cantidadPuertas = 0;
        tipoTransimision = 0;
    }

    @Override
    public void grabar(RandomAccessFile raf) {
        super.grabar(raf);
        try {
            raf.writeInt(cantidadPuertas);
            raf.writeChar(tipoTransimision);
            rellenar(raf, BYTE_DIFF);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void leer(RandomAccessFile raf) {
        super.leer(raf);
        try {
            cantidadPuertas = raf.readInt();
            tipoTransimision = raf.readChar();
            leerRelleno(raf, BYTE_DIFF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString()
                + "Automotor{" + "cantidadPuertas=" + cantidadPuertas + ", tipoTransimision=" + tipoTransimision + '}';
    }

    @Override
    public void cargarDatos() {
        super.cargarDatos();
        cargarCantidadPuertas();
        cargarTipoTransmision();
    }

    public void cargarCantidadPuertas() {
        int cantPuertas = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Cantidad de puertas: ");
            cantPuertas = GestorEntradaConsola.leerEntero();
            esValido = cantPuertas > 0;
        }
        setCantidadPuertas(cantPuertas);
    }

    public void cargarTipoTransmision() {
        char tipo = '-';
        boolean esValido = false;

        System.out.println("Tipo de Transimision: ");
        System.out.println("1. Automatica");
        System.out.println("2. Manual");
        System.out.print("Seleccione una opcion: ");

        while (!esValido) {
            int opcion = GestorEntradaConsola.leerEntero();
            switch (opcion) {
                case 1:
                    tipo = 'A';
                    break;
                case 2:
                    tipo = 'M';
                    break;
                default:
                    System.out.println("Opcion invalida!");
            }
            esValido = opcion == 1 || opcion == 2;
        }

        setTipoTransimision(tipo);
    }

    public int getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(int cantidadPuertas) {
        this.cantidadPuertas = cantidadPuertas;
    }

    public char getTipoTransimision() {
        return tipoTransimision;
    }

    public void setTipoTransimision(char tipoTransimision) {
        this.tipoTransimision = tipoTransimision;
    }

}
