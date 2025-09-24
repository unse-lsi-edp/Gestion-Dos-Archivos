package datos;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.Grabable;
import persistencia.Registro;
import utilidades.GestorEntradaConsola;

/**
 * Clase que representa una fecha con año, mes y día. Proporciona métodos para
 * cargar la fecha desde consola y validarla asegurando que corresponde a un día
 * real del calendario.
 *
 * @author Carlos Álvarez
 */
public class Fecha implements Grabable {

    private int año;
    private int mes;
    private int dia;
    private final int TAM_REGISTRO = 12;
    private final int TAM_ARCHIVO = 0;

    public Fecha() {
        año = 0;
        mes = 0;
        dia = 0;
    }

    public Fecha(int año, int mes, int dia) {
        if (!validarFecha(año, mes, dia)) {
            throw new IllegalArgumentException("Fecha inválida: " + año + "-" + mes + "-" + dia);
        }
        this.año = año;
        this.mes = mes;
        this.dia = dia;
    }

    public Fecha(Fecha fecha) {
        año = fecha.año;
        mes = fecha.mes;
        dia = fecha.dia;

    }

    /**
     * Solicita al usuario que ingrese un año, mes y día válidos, y los asigna a
     * esta instancia de {@code Fecha}.
     *
     * @return la misma instancia de Fecha con sus atributos cargados
     */
    public Fecha cargarFecha() {

        System.out.println("Fecha: ");
        int nuevoAño = cargarAño();
        int nuevoMes = cargarMes();
        int nuevoDia = cargarDia(nuevoAño, nuevoMes);

        año = nuevoAño;
        mes = nuevoMes;
        dia = nuevoDia;

        return this;
    }

    private int cargarAño() {
        int nuevoAño = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Año: ");
            nuevoAño = GestorEntradaConsola.leerEntero();
            esValido = nuevoAño > 1999;
        }
        return nuevoAño;
    }

    private int cargarMes() {
        int nuevoMes = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Mes: ");
            nuevoMes = GestorEntradaConsola.leerEntero();
            esValido = nuevoMes >= 1 && nuevoMes <= 12;
        }
        return nuevoMes;
    }

    private int cargarDia(int nuevoAño, int nuevoMes) {
        int nuevoDia = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Dia: ");
            nuevoDia = GestorEntradaConsola.leerEntero();
            esValido = validarFecha(nuevoAño, nuevoMes, nuevoDia);
        }
        return nuevoDia;
    }

    /**
     * Valida que la fecha proporcionada (año, mes y día) corresponda a una
     * fecha real del calendario.
     *
     * @param año el año a validar
     * @param mes el mes a validar (1-12)
     * @param dia el día a validar (1-31 según el mes)
     * @return {@code true} si la fecha es válida, {@code false} en caso
     * contrario
     */
    private boolean validarFecha(int año, int mes, int dia) {
        if (mes < 1 || mes > 12) {
            return false;
        }
        if (dia < 1) {
            return false;
        }

        int diasEnMes;
        switch (mes) {
            case 4:
            case 6:
            case 9:
            case 11: // Abril, junio, septiembre, noviembre
                diasEnMes = 30;
                break;
            case 2: // Febrero
                if (esBisiesto(año)) {
                    diasEnMes = 29;
                } else {
                    diasEnMes = 28;
                }
                break;
            default: // Meses con 31 días
                diasEnMes = 31;
        }

        return dia <= diasEnMes;
    }

    /**
     * Determina si un año es bisiesto.
     *
     * @param año el año a evaluar
     * @return {@code true} si el año es bisiesto, {@code false} en caso
     * contrario
     */
    private boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    /**
     * Obtiene el año de la fecha.
     *
     * @return el año (siempre válido, mayor a 1999)
     */
    public int getAño() {
        return año;
    }

    /**
     * Establece el año de la fecha. Valida que la combinación con mes y día
     * actuales sea válida.
     *
     * @param año nuevo año (mayor a 1999)
     * @throws IllegalArgumentException si la fecha resultante es inválida
     */
    public void setAño(int año) {
        if (!validarFecha(año, this.mes, this.dia)) {
            throw new IllegalArgumentException("Fecha inválida al cambiar el año: " + año + "-" + mes + "-" + dia);
        }
        this.año = año;
    }

    /**
     * Obtiene el mes de la fecha.
     *
     * @return el mes (1-12)
     */
    public int getMes() {
        return mes;
    }

    /**
     * Establece el mes de la fecha. Valida que la combinación con año y día
     * actuales sea válida.
     *
     * @param mes nuevo mes (1-12)
     * @throws IllegalArgumentException si la fecha resultante es inválida
     */
    public void setMes(int mes) {
        if (!validarFecha(this.año, mes, this.dia)) {
            throw new IllegalArgumentException("Fecha inválida al cambiar el mes: " + año + "-" + mes + "-" + dia);
        }
        this.mes = mes;
    }

    /**
     * Obtiene el día de la fecha.
     *
     * @return el día (siempre válido según mes y año)
     */
    public int getDia() {
        return dia;
    }

    /**
     * Establece el día de la fecha. Valida que la combinación con año y mes
     * actuales sea válida.
     *
     * @param dia nuevo día
     * @throws IllegalArgumentException si la fecha resultante es inválida
     */
    public void setDia(int dia) {
        if (!validarFecha(this.año, this.mes, dia)) {
            throw new IllegalArgumentException("Fecha inválida al cambiar el día: " + año + "-" + mes + "-" + dia);
        }
        this.dia = dia;
    }

    @Override
    public String toString() {
        return año + "-" + mes + "-" + dia;
    }

    @Override
    public int tamRegistro() {
        return TAM_REGISTRO;
    }

    @Override
    public int tamArchivo() {
        return TAM_ARCHIVO;
    }

    @Override
    public void grabar(RandomAccessFile a) {
        try {
            a.writeInt(año);
            a.writeInt(mes);
            a.writeInt(dia);
        } catch (IOException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            año = a.readInt();
            mes = a.readInt();
            dia = a.readInt();
        } catch (IOException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
