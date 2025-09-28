package gestores;

import persistencia.Archivo;
import persistencia.Registro;

public class HerramientaArchivo {

    /**
     * Determina si un archivo esta vacio
     *
     * @param archivo gestor de archivo con el que se trata el archivo
     * @return true si no hay registros activos en el archivo. False, caso
     * contrario
     */
    public static boolean estaVacio(Archivo<? extends Registro> archivo) {
        Registro reg;
        boolean estaVacio = true;
        archivo.abrirParaLectura();
        while (!archivo.eof() && estaVacio) {
            reg = archivo.leerRegistro();
            if (reg.getEstado()) {
                estaVacio = false;
            }
        }
        archivo.cerrarArchivo();
        return estaVacio;
    }

    /**
     * Devuelve un registro de un archivo dado un numero de orden
     *
     * @param <T> el tipo de Registro que se espera devolver, debe extender de
     * Registro.
     * @param archivo el archivo de donde se desea recuperar el registro
     * @param nroOrden del registro
     * @return el registro encontrado.
     */
    public static <T extends Registro> T buscarRegistro(Archivo<? extends Registro> archivo, int nroOrden) {

        T reg;
        archivo.abrirParaLectura();
        archivo.posicionarRAF(nroOrden);
        reg = (T) archivo.leerRegistro();
        return reg;

    }
}
