package gestorHolding;

import java.io.Serializable;

public class Area implements Serializable, Mostrable {

    private String nombre;
    private String descripcion;

    public Area(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void mostrar() {
        EntradaSalida.mostrarString("");
        EntradaSalida.mostrarString("AREA");
        EntradaSalida.mostrarString("\tNombre: " + this.nombre);
        EntradaSalida.mostrarString("\tDescripcion: " + this.descripcion);
        EntradaSalida.mostrarString("");
    }

    
    /** 
     * @param nomArea
     * @return boolean
     */
    public boolean mismoNombre(String nomArea) {
        return nomArea.toLowerCase().equals(nombre.toLowerCase());
    }

    public String getNombre() {
        return this.nombre;
    }

}
