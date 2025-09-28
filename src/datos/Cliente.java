package datos;

import java.io.IOException;
import java.io.RandomAccessFile;
import persistencia.Registro;
import utilidades.GestorEntradaConsola;

public class Cliente extends Registro {

    private int dni; // 4 bytes
    private String nombreCompleto; // 80 bytes
    private String telefono; // 20 bytes

    private final int LEN_NOMBRE = 40;
    private final int LEN_TELEFONO = 20;

    private final int TAM_REG_CLIENTE = 4 + 40 * 2 + 20 * 2;
    private final int TAMARCH = 100;

    public Cliente() {
        super();
        this.dni = 0;
        this.nombreCompleto = " ";
        this.telefono = " ";
    }

    public void cargarDatos() {
        cargarDni();
        cargarNombreCompleto();
        cargarTelefono();
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
            esValido = !nuevoNombre.trim().isEmpty() && nuevoNombre.length() <= LEN_NOMBRE;
        }
        setNombreCompleto(nuevoNombre);
    }

    public void cargarTelefono() {
        String nuevoTelefono = " ";
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Telefono: ");
            nuevoTelefono = GestorEntradaConsola.leerString();
            esValido = !nuevoTelefono.trim().isEmpty() && nuevoTelefono.length() <= LEN_TELEFONO;
        }
        setTelefono(nuevoTelefono);
    }
    
    
    @Override
    public void grabar(RandomAccessFile raf) {
        try {
            super.grabar(raf);
            raf.writeInt(dni);
            Registro.writeString(raf, nombreCompleto, LEN_NOMBRE);
            Registro.writeString(raf, telefono, LEN_TELEFONO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void leer(RandomAccessFile raf) {
        try {
            super.leer(raf);
            dni = raf.readInt();
            nombreCompleto = Registro.leerString(raf, LEN_NOMBRE).trim();
            telefono = Registro.leerString(raf, LEN_TELEFONO).trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public int tamRegistro(){
        return super.tamRegistro() + TAM_REG_CLIENTE;
    }
    
    @Override
    public void mostrarRegistro() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Cliente{" + "dni=" + dni + ", nombreCompleto=" + nombreCompleto + ", telefono=" + telefono + '}';
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
