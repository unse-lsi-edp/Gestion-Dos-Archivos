package persistencia;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class Registro implements Grabable {

    protected int nroOrden;
    protected boolean estado;
    protected final int TAM_REG = 5;
    protected final int TAM_ARCH = 100;

    /**
     * Crea una instancia de registro sin datos, marcandolo como inactivo.
     */
    public Registro() {
        estado = false;
    }

    /**
     * Crea un Registro con un numero de orden, marcándolo como activo
     */
    public Registro(int orden) {
        nroOrden = orden;
        estado = true;
    }

    @Override
    public String toString() {
        return "Registro{" + "nroOrden=" + nroOrden + ", estado=" + estado + '}';
    }

    /**
     * Accede al valor del nroOrden
     *
     * @return el valor del atributo nroOrden
     */
    public int getNroOrden() {
        return nroOrden;
    }

    /**
     * Accede al valor del nroOrden
     *
     * @return el valor del atributo nroOrden
     */
    public void setNroOrden(int nroOrden) {
        this.nroOrden = nroOrden;
    }

    /**
     * Determina si el registro es activo o no
     *
     * @return true si es activo, false si no.
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * Cambia el estado del registro, en memoria
     *
     * @param x el nuevo estado
     */
    public void setEstado(boolean x) {
        estado = x;
    }

    /**
     * Calcula el tamaño en bytes del objeto, tal como será grabado en disco.
     * Pedido por Grabable.
     *
     * @return el tamaño en bytes del objeto como será grabado.
     */
    @Override
    public int tamRegistro() {
        return TAM_REG;
    }

    /**
     * Devuelve cantidad de registros. Pedido por Grabable.
     *
     * @return cantidad de registros.
     */
    @Override
    public int tamArchivo() {
        return TAM_ARCH;
    }

    /**
     * Especifica cómo se graba un Registro en disco. Pedido por Grabable.
     *
     * @param raf el manejador del archivo de disco donde se hará la grabación
     */
    @Override
    public void grabar(RandomAccessFile raf) {
        try {
            raf.writeInt(nroOrden);
            raf.writeBoolean(estado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Especifica cómo se lee un Registro desde disco. Pedido por Grabable.
     *
     * @param raf el manejador del archivo de disco desde donde se hará la lectura
     */
    @Override
    public void leer(RandomAccessFile raf) {
        try {
            nroOrden = raf.readInt();
            estado = raf.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee desde un archivo un String de "tam" caracteres. Se declara static
     * para que pueda ser usado en forma global por cualquier clase que requiera
     * leer una cadena de longitud fija desde un archivo.
     *
     * @param raf el manejador del archivo desde el cual se lee
     * @param tam la cantidad de caracteres a leer
     * @return el String leido
     */
    public static final String leerString(RandomAccessFile raf, int tam) {
        String cad = "";

        try {
            char vector[] = new char[tam];
            for (int i = 0; i < tam; i++) {
                vector[i] = raf.readChar();
            }
            cad = new String(vector, 0, tam);
        } catch (IOException e) {
            System.out.println("Error al leer una cadena: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return cad;
    }

    /**
     * Graba en un archivo un String de "tam" caracteres. Se declara static y
     * publico para que pueda ser usado en forma global por cualquier clase que
     * requiera grabar una cadena de longitud fija en un archivo.
     *
     * @param raf el manejador del archivo en el cual se graba
     * @param cad la cadena a a grabar
     * @param tam la cantidad de caracteres a grabar
     */
    public static void writeString(RandomAccessFile raf, String cad, int tam) {
        try {
            int i;
            char vector[] = new char[tam];
            for (i = 0; i < tam; i++) {
                vector[i] = ' ';
            }
            cad.getChars(0, cad.length(), vector, 0);
            for (i = 0; i < tam; i++) {
                raf.writeChar(vector[i]);
            }
        } catch (IOException e) {
            System.out.println("Error al grabar una cadena: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public abstract void mostrarRegistro();
    
    

}
