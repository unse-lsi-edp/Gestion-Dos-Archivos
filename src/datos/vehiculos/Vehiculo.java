package datos.vehiculos;

import datos.Fecha;
import java.io.IOException;
import java.io.RandomAccessFile;
import persistencia.Registro;
import utilidades.GestorEntradaConsola;

public class Vehiculo extends Registro {

    protected int codigo; //4 bytes
    protected int codigoCliente; //4 bytes
    protected char tipoVehiculo; // Valores: "A" auto, "M" moto, "C" camioneta. 2 bytes
    protected char estadoVehiculo; // Valores: "D" disponible, "R" rentado, "A" averiado. 2 bytes
    protected Fecha fechaAdquisicion; // 12 Bytes
    protected String marca;
    protected final int LEN_MARCA = 15;
    protected String modelo;
    protected final int LEN_MODELO = 30;

    protected final int TAM_ARCH = 100;

    /*
    Se ajusta el tamaño del bloque de registro sumando la diferencia en bytes entre
    el tamaño de la clase base (Vehiculo) y el de la subclase más grande.
    En este caso, Vehiculo ocupa 114 bytes, mientras que Camioneta (la subclase más grande)
    ocupa 122 bytes. Como 122 - 114 = 8, se agregan 8 bytes al registro de Vehiculo
    para asegurar que todos los registros tengan el mismo tamaño.
     */
    private final int TAM_REG_VEHICULO = 4 + 4 + 2 + 2 + 12 + LEN_MARCA * 2 + LEN_MODELO * 2;
    protected final int TAM_REG = TAM_REG_VEHICULO + 8;
    private final int BYTE_DIFF = TAM_REG - TAM_REG_VEHICULO;

    public Vehiculo() {
        super();
        this.codigo = 0;
        codigoCliente = 0;
        tipoVehiculo = 0;
        estadoVehiculo = '-';
        fechaAdquisicion = new Fecha();
        marca = "";
        modelo = "";
    }

    /**
     * Crea un Registro con datos, marcándolo como activo
     *
     * @param orden nro de orden del registro
     */
    public Vehiculo(int orden) {
        super();
        this.codigo = 0;
        codigoCliente = 0;
        tipoVehiculo = 0;
        estadoVehiculo = '-';
        fechaAdquisicion = new Fecha();
        marca = "";
        modelo = "";
    }

    /*
        IMPORTANTE: Se debe reescribir todos los métodos de la interfaz grabable, 
        pues de no ser asi, tomara los metodos de la clase abstracta Registro.
     */
    @Override
    public int tamRegistro() {
        return super.tamRegistro() + TAM_REG;
    }

    @Override
    public int tamArchivo() {
        return super.tamArchivo();
    }

    @Override
    public void grabar(RandomAccessFile raf) {
        try {
            super.grabar(raf);
            raf.writeInt(codigo);
            raf.writeInt(codigoCliente);
            raf.writeChar(tipoVehiculo);
            raf.writeChar(estadoVehiculo);
            fechaAdquisicion.grabar(raf);
            Registro.writeString(raf, marca, LEN_MARCA);
            Registro.writeString(raf, modelo, LEN_MODELO);
            rellenar(raf, BYTE_DIFF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void leer(RandomAccessFile raf) {
        try {
            super.leer(raf);
            codigo = raf.readInt();
            codigoCliente = raf.readInt();
            tipoVehiculo = raf.readChar();
            estadoVehiculo = raf.readChar();
            fechaAdquisicion.leer(raf);
            marca = Registro.leerString(raf, LEN_MARCA).trim();
            modelo = Registro.leerString(raf, LEN_MODELO).trim();
            leerRelleno(raf, BYTE_DIFF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void rellenar(RandomAccessFile raf, int bytes) throws IOException {
        for (int i = 0; i < bytes; i++) {
            raf.writeByte(0);
        }
    }

    public void leerRelleno(RandomAccessFile raf, int bytes) throws IOException {
        for (int i = 0; i < bytes; i++) {
            raf.readByte();
        }

    }

    @Override
    public void mostrarRegistro() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "codigo=" + codigo + ", codigoCliente=" + codigoCliente + ", tipoVehiculo=" + tipoVehiculo + ", estadoVehiculo=" + estadoVehiculo + ", fechaAdquisicion=" + fechaAdquisicion + ", marca=" + marca + ", modelo=" + modelo + '}';
    }

    public void cargarDatos() {
        cargarCodigoVehiculo();
//        cargarTipoVehiculo();
        cargarEstadoVehiculo();
        cargarFechaAdquisicion();
        cargarMarca();
        cargarModelo();
    }

    public void cargarCodigoVehiculo() {
        int nuevoCodigo = 0;
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Codigo vehiculo: ");
            nuevoCodigo = GestorEntradaConsola.leerEntero();
            esValido = nuevoCodigo > 0 && nuevoCodigo <= TAM_ARCH;
        }
        setCodigo(nuevoCodigo);
    }

//    public int cargarTipoVehiculo() {
//
//        char tipo = '-';
//        boolean esValido = false;
//        int opcion = 0;
//        
//        System.out.println("Tipo de Vehiculo: ");
//        System.out.println("1. Auto");
//        System.out.println("2. Moto");
//        System.out.println("3. Camioneta");
//        System.out.print("Seleccione una opcion: ");
//
//        while (!esValido) {
//            opcion = GestorEntradaConsola.leerEntero();
//            switch (opcion) {
//                case 1:
//                    tipo = 'A';
//                    break;
//                case 2:
//                    tipo = 'M';
//                    break;
//                case 3:
//                    tipo = 'C';
//                    break;
//                default:
//                    System.out.println("Opcion invalida!");
//            }
//            esValido = opcion == 1 || opcion == 2 || opcion == 3;
//        }
//
//        setTipoVehiculo(tipo);
//        return opcion;
//    }
    public void cargarEstadoVehiculo() {

        char nuevoEstado = '-';
        boolean esValido = false;

        System.out.println("Estado del Vehiculo: ");
        System.out.println("1. Disponible");
        System.out.println("2. Rentado");
        System.out.println("3. Averiado");
        System.out.print("Seleccione una opcion: ");

        while (!esValido) {
            int opcion = GestorEntradaConsola.leerEntero();
            switch (opcion) {
                case 1:
                    nuevoEstado = 'D';
                    break;
                case 2:
                    nuevoEstado = 'R';
                    break;
                case 3:
                    nuevoEstado = 'A';
                    break;
                default:
                    System.out.println("Opcion invalida!");
            }
            esValido = opcion == 1 || opcion == 2 || opcion == 3;
        }

        setEstadoVehiculo(nuevoEstado);
    }

    public void cargarFechaAdquisicion() {
        System.out.println("Fecha de adquisicion: ");
        Fecha nuevaFechaAdquisicion = new Fecha();
        nuevaFechaAdquisicion.cargarFecha();
        setFechaAdquisicion(nuevaFechaAdquisicion);

    }

    public void cargarMarca() {
        String nuevaMarca = " ";
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Marca: ");
            nuevaMarca = GestorEntradaConsola.leerString();
            esValido = !nuevaMarca.trim().isEmpty() && nuevaMarca.length() <= LEN_MARCA;
        }
        setMarca(nuevaMarca);
    }

    public void cargarModelo() {
        String nuevoModelo = " ";
        boolean esValido = false;
        while (!esValido) {
            System.out.print("Modelo: ");
            nuevoModelo = GestorEntradaConsola.leerString();
            esValido = !nuevoModelo.trim().isEmpty() && nuevoModelo.length() <= LEN_MODELO;
        }
        setModelo(nuevoModelo);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public char getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(char estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public Fecha getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Fecha fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public char getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(char tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

}
