package datos.vehiculos;

import java.io.IOException;
import java.io.RandomAccessFile;
import utilidades.GestorEntradaConsola;

/**
 *
 * @author Carlos Álvarez
 */
public class Motocicleta extends Vehiculo {

    private int cilindrada;

    private final int TAM_REG_MOTO = 4;
    private final int BYTE_DIFF = TAM_REG - TAM_REG_MOTO;
    
    public Motocicleta() {
        super();
        cilindrada = 0;
    }

    public Motocicleta(int orden) {
        super(orden);
        cilindrada = 0;
    }

    @Override
    public String toString() {
        return super.toString() + "Motocicleta{" + "cilindrada=" + cilindrada + '}';
    }

    @Override
    public void grabar(RandomAccessFile raf) {
        super.grabar(raf);
        try {
            raf.writeInt(cilindrada);
            rellenar(raf, BYTE_DIFF);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void leer(RandomAccessFile raf) {
        super.leer(raf);
        try {
            cilindrada = raf.readInt();
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
    public void cargarDatos() {
        super.cargarDatos();
        cargarCilindrada();
    }

    public void cargarCilindrada() {
        int nuevaCilindrada = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Cilindrada: ");
            nuevaCilindrada = GestorEntradaConsola.leerEntero();
            esValido = nuevaCilindrada > 0;
        }
        setCilindrada(nuevaCilindrada);
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrara) {
        this.cilindrada = cilindrara;
    }

}
