package gestorHolding;

import java.io.Serializable;

public class Pais implements Serializable, Mostrable {

    private String nombre;
    private double PBI;
    private double numeroHabitantes;
    private String capital;

    public Pais(String nombre, double PBI, double numeroHabitantes, String capital) {
        this.nombre = nombre;
        this.PBI = PBI;
        this.numeroHabitantes = numeroHabitantes;
        this.capital = capital;
    }

    public void mostrar() {
        EntradaSalida.mostrarString("");
        EntradaSalida.mostrarString("PAIS");
        EntradaSalida.mostrarString("\tNombre: " + this.nombre);
        EntradaSalida.mostrarString("\tProducto Bruto Interno: " + this.PBI);
        EntradaSalida.mostrarString("\tCantidad de Habitantes: " + this.numeroHabitantes);
        EntradaSalida.mostrarString("\tCapital: " + this.capital);
        EntradaSalida.mostrarString("");
    }

    
    /** 
     * @param nomPais
     * @return boolean
     */
    public boolean mismoNombre(String nomPais) {
        return nomPais.toLowerCase().equals(nombre.toLowerCase());
    }

    public String getNombre() {
        return nombre;
    }

}
