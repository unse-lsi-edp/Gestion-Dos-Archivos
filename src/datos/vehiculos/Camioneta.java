package datos.vehiculos;

import java.io.IOException;
import java.io.RandomAccessFile;
import utilidades.GestorEntradaConsola;

public class Camioneta extends Vehiculo {

    private int cargaMaxima; // Capacidad maxima de carga, en toneladas
    private float volumenDeCarga; // Volumen maximo que se puede cargar

    private final int TAM_REG_CAMIONETA = 4 + 4;
    private final int BYTE_DIFF = TAM_REG_BASE - TAM_REG_CAMIONETA;

    public Camioneta() {
        super();
        cargaMaxima = 0;
        volumenDeCarga = 0;
    }

    public Camioneta(int orden) {
        super(orden);
        cargaMaxima = 0;
        volumenDeCarga = 0;
    }

    @Override
    public void grabar(RandomAccessFile raf) {
        super.grabar(raf);
        try {
            raf.writeInt(cargaMaxima);
            raf.writeFloat(volumenDeCarga);
            rellenar(raf, BYTE_DIFF);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void leer(RandomAccessFile raf) {
        super.leer(raf);
        try {
            cargaMaxima = raf.readInt();
            volumenDeCarga = raf.readFloat();
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
        return super.toString() + "Camioneta{" + "cargaMaxima=" + cargaMaxima + ", volumenDeCarga=" + volumenDeCarga + '}';
    }

    @Override
    public void cargarDatos() {
        super.cargarDatos();
        cargarCapacidadMaxima();
        cargarVolumenDeCarga();
    }

    @Override
    public void cargarNuevoVehiculo() {
        super.cargarNuevoVehiculo();
        cargarCapacidadMaxima();
        cargarVolumenDeCarga();
    }

    public void cargarCapacidadMaxima() {
        int nuevaCargaMax = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Capacidad maxima: ");
            nuevaCargaMax = GestorEntradaConsola.leerEntero();
            esValido = nuevaCargaMax > 0;
        }
        setCargaMaxima(nuevaCargaMax);
    }

    public void cargarVolumenDeCarga() {
        float nuevoVol = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Volumen de carga: ");
            nuevoVol = GestorEntradaConsola.leerFloat();
            esValido = nuevoVol > 0;
        }
        setVolumenDeCarga(nuevoVol);
    }

    public int getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(int cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    public float getVolumenDeCarga() {
        return volumenDeCarga;
    }

    public void setVolumenDeCarga(float volumenDeCarga) {
        this.volumenDeCarga = volumenDeCarga;
    }

}
