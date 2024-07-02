package gestorHolding;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vendedor extends Usuario {

    private String codigo;
    private String nombre;
    private String direccion;
    private LocalDate fechaCaptacion;
    private ArrayList<Vendedor> vendedores;
    private Empresa empresa;
    private Vendedor vendedorSuperior;

    public Vendedor(String usuario, String password, String codigo, String nombre, String direccion, Empresa empresa,
            Vendedor vendedorSuperior, LocalDate fechaCaptacion) {
        setUsuario(usuario);
        setPassword(password);
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.empresa = empresa;
        this.vendedorSuperior = vendedorSuperior;
        this.fechaCaptacion = fechaCaptacion;
        this.vendedores = new ArrayList<Vendedor>();
    }

    @Override
    public void mostrar() {
        EntradaSalida.mostrarString("");
        EntradaSalida.mostrarString("VENDEDOR");
        EntradaSalida.mostrarString("\tCodigo: " + this.codigo);
        EntradaSalida.mostrarString("\tNombre: " + this.nombre);
        EntradaSalida.mostrarString("\tDireccion: " + this.direccion);
        if (this.vendedorSuperior != null) {
            EntradaSalida.mostrarString("\tVendedor Superior:");
            EntradaSalida.mostrarString("\t\tCodigo: " + this.vendedorSuperior.getCodigo());
            EntradaSalida.mostrarString("\t\tNombre: " + this.vendedorSuperior.getNombre());
            EntradaSalida.mostrarString("\tFecha de Captacion: " + this.fechaCaptacion);
        }
        EntradaSalida.mostrarString("\tEmpresa: " + this.empresa.getNombre());
        EntradaSalida.mostrarString("");
    }

    
    /** 
     * @return String
     */
    public String getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    @Override
    public boolean proceder(SistemaGestor sistemaGestor) {// sistema gestor no se usa (posible futuro uso??)
        int opcion;

        do {
            EntradaSalida.mostrarString("");
            EntradaSalida.mostrarString("Bienvenido/a al sistema, Sr(a) Vendedor(a)");
            EntradaSalida.mostrarString("1- Consultar Datos");
            EntradaSalida.mostrarString("2- Salir");
            opcion = EntradaSalida.leerInt("Ingrese la opcion deseada: ");
        } while (opcion < 1 || opcion > 2);

        if (opcion == 1) {
            this.mostrar();
            EntradaSalida.mostrarString("VENDEDORES A CARGO: ");
            for (int i = 0; i < this.vendedores.size(); i++) {
                this.vendedores.get(i).mostrar();
            }
        }
        return true;
    }

    public boolean mismoCodigo(String codVendedor) {
        return codVendedor.equals(codigo);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void eliminarVendedorInferior(Vendedor vendedorAEliminar) {
        vendedores.remove(vendedorAEliminar);
    }

    public void agregarVendedorACargo(Vendedor vendedor) {
        this.vendedores.add(vendedor);
    }

    public void agregarVendedorSuperior(Vendedor vendedor) {
        this.vendedorSuperior = vendedor;
    }

    @Override
    public Vendedor accept(Visitor v) {
        return v.visit(this);
    }

    public Vendedor devolvete() {
        return this;
    }

    public void eliminarVendedorSuperior(Vendedor vendedorAEliminar) {
        if (this.vendedorSuperior.equals(vendedorAEliminar))
            this.vendedorSuperior = null;
    }
}
