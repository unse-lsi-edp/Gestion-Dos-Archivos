package persistencia;

import java.io.RandomAccessFile;

public interface Grabable {

    /**
     * Calcula el tamaño en bytes del objeto, tal como será grabado
     *
     * @return el tamaño en bytes del objeto
     */
    int tamRegistro();

    /**
     * Obtiene la cantidad de registros que se desea en el archivo
     *
     * @return la cantidad de registros
     */
    int tamArchivo();

    /**
     * Indica cómo grabar un objeto
     *
     */
    void grabar(RandomAccessFile raf);

    /**
     * Indica cómo leer un objeto
     *
     */
    void leer(RandomAccessFile raf);

}
