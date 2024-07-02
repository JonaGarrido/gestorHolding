package gestorHolding;

import java.util.ArrayList;

public class Asesor extends Usuario {

    private String codigo;
    private String nombre;
    private String direccion;
    private String titulacion;
    private ArrayList<Asesoria> asesorias;

    public Asesor(String usuario, String password, String codigo, String nombre, String direccion, String titulacion) {
        setUsuario(usuario);
        setPassword(password);
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.titulacion = titulacion;
        this.asesorias = new ArrayList<Asesoria>();
    }

    @Override
    public void mostrar() {
        EntradaSalida.mostrarString("");
        EntradaSalida.mostrarString("ASESOR");
        EntradaSalida.mostrarString("\tCodigo: " + this.codigo);
        EntradaSalida.mostrarString("\tNombre: " + this.nombre);
        EntradaSalida.mostrarString("\tDireccion: " + this.direccion);
        EntradaSalida.mostrarString("\tTitulacion: " + this.titulacion);
        for (Asesoria asesoria : asesorias) {
            asesoria.mostrar();
        }
        EntradaSalida.mostrarString("");
    }

    
    /** 
     * @param sistemaGestor
     * @return boolean
     */
    @Override
    public boolean proceder(SistemaGestor sistemaGestor) {// sistema gestor no se usa (posible futuro uso??)
        int opcion;

        do {
            EntradaSalida.mostrarString("");
            EntradaSalida.mostrarString("Bienvenido/a al sistema, Sr(a) Asesor(a)");
            EntradaSalida.mostrarString("1- Consultar Datos");
            EntradaSalida.mostrarString("2- Salir");
            opcion = EntradaSalida.leerInt("Ingrese la opcion deseada: ");
        } while (opcion < 1 || opcion > 2);

        if (opcion == 1)
            this.mostrar();
        return true;
    }

    public boolean mismoCodigo(String codAsesor) {
        return codAsesor == codigo;
    }

    public void agregarAsesoria(Asesoria asesoria) {
        asesorias.add(asesoria);
    }

    public Asesor accept(Visitor v) {
        return v.visit(this);
    }

    public Asesor devolvete() {
        return this;
    }

    public ArrayList<Asesoria> getAsesorias() {
        return this.asesorias;
    }

    public void eliminarAsesoria(int opcion) {
        this.asesorias.remove(opcion);
    }
}
