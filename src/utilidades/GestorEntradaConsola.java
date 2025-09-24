package utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Carlos Álvarez
 */
public class GestorEntradaConsola {

    /**
     * Lee un string desde teclado. El string termina con un salto de linea
     *
     * @return el string leido (sin el salto de linea)
     */
    public static String leerString() {

        int ch;
        String r = "";
        boolean done = false;
        while (!done) {
            try {
                ch = System.in.read();
                if (ch < 0 || (char) ch == '\n') {
                    done = true;
                } else {
                    if ((char) ch != '\r') {
                        r = r + (char) ch;
                    }
                }
            } catch (java.io.IOException e) {
                done = true;
            }
        }
        return r;
    }

    /**
     * Lee un integer desde teclado. La entrada termina con un salto de linea
     *
     * @return el valor cargado, como un int
     */
    public static int leerEntero() {

        while (true) {
            try {
                return Integer.parseInt(leerString().trim());
            } catch (NumberFormatException e) {
                System.out.print("ERROR! Escriba nuevamente: ");
//                e.printStackTrace();
            }
        }
    }

    /**
     * Lee un Integer desde teclado
     *
     * @return el Integer leido
     */
    public static Integer leerInteger() {

        while (true) {
            try {
                return Integer.parseInt(leerDato());
            } catch (NumberFormatException e) {
                System.out.print("ERROR! Escriba nuevamente: ");
            }
        }
    }

    /**
     * Lee un flotante desde teclado
     *
     * @return el flotante leido
     */
    public static float leerFloat() {
        while (true) {
            try {
                return Float.parseFloat(leerString().trim());
            } catch (NumberFormatException e) {
                System.out.print("ERROR! Intente nuevamente: ");
            }
        }
    }

    /**
     * Lee un double desde teclado. La entrada termina con un salto de linea
     *
     * @return el valor cargado, como un double
     */
    public static double leerDouble() {

        while (true) {
            try {
                return Double.parseDouble(leerString().trim());
            } catch (NumberFormatException e) {
                System.out.print("ERROR! Intente nuevamente: ");
            }
        }
    }

    /**
     * Lee un long desde teclado. La entrada termina con un salto de linea
     *
     * @return el valor cargado, como un double
     */
    public static long leerLong() {

        while (true) {
            try {
                return Long.parseLong(leerString().trim());
            } catch (NumberFormatException e) {
                System.out.print("ERROR! Intente nuevamente: ");
            }
        }
    }

    /**
     * Lee un caracter desde teclado.
     *
     * @return el caracter leido (sin el salto de linea)
     */
    public static char leerChar() {
        while (true) {
            try {
                return (char) leerDato().charAt(0);
            } catch (NumberFormatException e) {
                System.out.print("ERROR! Intente nuevamente: ");
            }
        }
    }

    /**
     * Captura la entrada éstandar por consola utilizando las clases
     * InputStreamReader y BufferedReader
     *
     * @return Un {@link String} que contiene la línea ingresada por el usuario.
     * Si ocurre un error durante la lectura, devuelve una cadena vacía ("").
     */
    private static String leerDato() {
        String dato = "";
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            dato = br.readLine();
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }

        return dato;
    }

    /**
     * Metodo util para cuando se necesite confirmar una opcion con Si y No.
     *
     * @return un booleano verdadero si se ingreso
     */
    public static boolean confirmar() {
        char op = ' ';
        while (op != 'n' && op != 'N' && op != 's' && op != 'S') {
            System.out.print("continuar? s/n: ");
            op = leerChar();
        }
        return (op != 'n' && op != 'N');
    }

    /**
     * Muestra un mensaje por consola esperando ENTER
     */
    public static void pausar() {
        System.out.print("Pulse ENTER para continuar...");
        leerString();
    }
}
