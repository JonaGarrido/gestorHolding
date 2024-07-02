package gestorHolding;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Empresa implements Serializable, Mostrable {

    private String nombre;
    private LocalDate fechaIngreso;
    private double facturacionAnual;
    private int cantidadVendedores;
    private Pais sedePais;
    private String sedeCiudad;
    private ArrayList<Pais> paises;
    private ArrayList<Area> areas;

    public Empresa(String nombre, LocalDate fechaIngreso, double facturacionAnual, Pais sedePais, String sedeCiudad) {
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.facturacionAnual = facturacionAnual;
        this.cantidadVendedores = 0;
        this.sedePais = sedePais;
        this.sedeCiudad = sedeCiudad;
        this.paises = new ArrayList<Pais>();
        this.areas = new ArrayList<Area>();
    }

    public void mostrar() {
        EntradaSalida.mostrarString("");
        EntradaSalida.mostrarString("EMPRESA");
        EntradaSalida.mostrarString("\tNombre: " + this.nombre);
        EntradaSalida.mostrarString("\tFecha de Ingreso: " + this.fechaIngreso);
        EntradaSalida.mostrarString("\tFacturacion Anual: " + this.facturacionAnual);
        EntradaSalida.mostrarString("\tCantidad de Vendedores: " + this.cantidadVendedores);
        EntradaSalida.mostrarString("\tCiudad Sede: " + this.sedeCiudad);

        for (int i = 0; i < this.paises.size(); i++) {
            this.paises.get(i).mostrar();
        }
        for (int i = 0; i < this.areas.size(); i++) {
            this.areas.get(i).mostrar();
        }
        EntradaSalida.mostrarString("");
    }

    
    /** 
     * @param nomEmpresa
     * @return boolean
     */
    public boolean mismoNombre(String nomEmpresa) {
        return nomEmpresa.toLowerCase().equals(nombre.toLowerCase());
    }

    public void sumarVendedor() {
        cantidadVendedores++;
    }

    public void restarVendedor() {
        cantidadVendedores--;
    }

    public void agregarPais(Pais paisTrabajo) {
        paises.add(paisTrabajo);
    }

    public void agregarArea(Area areaTrabajo) {
        areas.add(areaTrabajo);
    }

    public Pais getPaisSede() {
        return sedePais;
    }

    public ArrayList<Area> getAreas() {
        return this.areas;
    }

    public ArrayList<Pais> getPaises() {
        return this.paises;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void eliminarArea(Area areaUtilizada) {
        this.areas.remove(areaUtilizada);
    }

    public void eliminarPais(Pais paisUtilizado) {
        this.paises.remove(paisUtilizado);
    }
}
