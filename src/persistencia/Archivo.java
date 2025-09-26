package persistencia;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase genérica que administra un archivo binario de registros fijos. Se
 * encarga de las operaciones de creación, apertura, escritura, así como del
 * posicionamiento dentro del archivo.
 *
 * @param <T> tipo de registro que implementa Registro y Grabable
 */
public class Archivo<T extends Registro & Grabable> {

    private File fd;                    // Descriptor del archivo para acceder a sus propiedades externas 
    private RandomAccessFile raf;       // Objeto para acceder al contenido del archivo 
    private T tipo;                     // Representa al tipo de objeto que se puede grabar en el archivo 

    /**
     * Crea un manejador para el Archivo, asociando al mismo un nombre a modo de
     * file descriptor, y un tipo de contenido al que quedará fijo. El segundo
     * parámetro se usa para fijar el tipo de registro que será aceptado para
     * grabar en el archivo. No se crea el archivo en disco, ni se abre. Sólo se
     * crea un descriptor general para él. La apertura y eventual creación, debe
     * hacerse con el método abrirParaLeerEscribir().
     *
     * @param nombre es el nombre físico del archivo a crear
     * @param r una instancia de la clase a la que pertenecen los objetos cuyos
     * datos serán grabados. La instancia referida por r NO será grabada en el
     * archivo
     * @throws ClassNotFoundException si no se informa correctamente el tipo de
     * registro a grabar
     */
    public Archivo(String nombre, T r) throws ClassNotFoundException {
        if (r == null) {
            throw new ClassNotFoundException("Clase incorrecta o inexistente para el tipo de registro");
        }
        tipo = r;
        fd = new File(nombre);
    }

    /**
     * Acceso al descriptor del archivo
     *
     * @return un objeto de tipo File con las propiedades de file system del
     * archivo
     */
    public File getFd() {
        return fd;
    }

    /**
     * Acceso al manejador del archivo binario
     *
     * @return un objeto de tipo RandomAccessFile que permite acceder al bloque
     * físico de datos en disco, en forma directa
     */
    public RandomAccessFile getRAF() {
        return raf;
    }

    /**
     * Acceso al descriptor de la clase del registro que se graba en el archivo
     *
     * @return una cadena con el nombre de la clase del registro usado en el
     * archivo
     */
    public String getTipoRegistro() {
        return tipo.getClass().getName();
    }

    /**
     * Crea un archivo vacío. Llena el archivo con registros en blanco.
     *
     * @param reg registro base usado para calcular el tamaño y generar las
     * posiciones iniciales
     */
    public void crearArchivoVacio(T reg) {
        abrirParaLeerEscribir();
        try {
            for (int i = 0; i < reg.tamArchivo(); i++) {
                reg.setNroOrden(i);
                reg.setEstado(false);
                buscarRegistro(i);
                grabarRegistro(reg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cerrarArchivo();
    }

    /**
     * Abre el archivo en modo de sólo lectura. El archivo en disco debe existir
     * previamente. Queda posicionado al principio del archivo.
     */
    public void abrirParaLectura() {
        try {
            raf = new RandomAccessFile(fd, "r");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre el archivo en modo de lectura y grabación. Si el archivo en disco no
     * existía, será creado. Si existía, será posicionado al principio del
     * archivo. Mueva el puntero de registro activo con el método seekRegistro()
     * o con buscarByte().
     */
    public void abrirParaLeerEscribir() {
        try {
            raf = new RandomAccessFile(fd, "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra el archivo
     */
    public void cerrarArchivo() {
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ubica el puntero de registro activo en la posición del registro número i.
     * Se supone que los registros grabados son del mismo tipo, y que la
     * longitud de los registros es uniforme.
     *
     * @param i número relativo del registro que se quiere acceder
     */
    public void buscarRegistro(long i) {
        try {
            raf.seek(i * tipo.tamRegistro());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve la cantidad de registros del archivo en este momento
     *
     * @return el número de registros del archivo
     */
    public long cantidadRegistros() {
        try {
            return raf.length() / tipo.tamRegistro();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Determina si se ha llegado al final del archivo o no
     *
     * @return true si se llegó al final - false en caso contrario
     */
    public boolean eof() {
        try {
            return raf.getFilePointer() >= raf.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Graba un registro en el archivo
     *
     * @param r el registro a grabar
     */
    public void grabarRegistro(T r) {

        if (r != null && tipo.getClass().isInstance(r)) {
            try {
                buscarRegistro(r.getNroOrden());
                r.grabar(raf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lee un registro del archivo
     *
     * @return el registro leído
     */
    public T leerRegistro() {
        try {
            tipo.leer(raf);
            return tipo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lee un registro que sea hijo del tipo definido en el archivo.
     * @param <U> debe extender de T
     * @param clazz el tipo de clase hija de T
     * @return una instancia hija de T, con sus datos cargados.
     */
    public <U extends T> U leerRegistroHijo(Class<U> clazz) {
        try {
            U obj = clazz.getDeclaredConstructor().newInstance(); // crea la instancia concreta
            obj.leer(raf); // carga sus datos desde el archivo
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
