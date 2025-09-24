package datos;

import persistencia.Registro;
import utilidades.GestorEntradaConsola;

public class Cliente extends Registro {

    private int dni; // 4 bytes
    private String nombreCompleto; // 80 bytes
    private String telefono; // 20 bytes

    private final int LONG_DESCRIPCION = 40;
    private final int LONG_TELEFONO = 20;

    private final int TAMREG = 4 + 40 * 2 + 20 * 2;
    private final int TAMARCH = 100;

    public Cliente() {
        this.dni = 0;
        this.nombreCompleto = " ";
    }

    public void cargarDatos() {
        cargarDni();
        cargarNombreCompleto();
    }

    public void cargarDni() {
        int nuevoDni = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("DNI: ");
            nuevoDni = GestorEntradaConsola.leerEntero();
            esValido = nuevoDni > 0 && nuevoDni <= TAMARCH;
        }
        setDni(nuevoDni);
    }

    public void cargarNombreCompleto() {
        String nuevoNombre = " ";
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Nombre completo: ");
            nuevoNombre = GestorEntradaConsola.leerString();
            esValido = !nuevoNombre.trim().isEmpty() && nuevoNombre.length() <= LONG_DESCRIPCION;
        }
        setNombreCompleto(nuevoNombre);
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Cliente{" + "dni=" + dni + ", nombreCompleto=" + nombreCompleto + '}';
    }

    public int getDni() {
        return dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    private void setDni(int codDes) {
        this.dni = codDes;
    }

    private void setNombreCompleto(String descripcion) {
        this.nombreCompleto = descripcion;
    }
}
