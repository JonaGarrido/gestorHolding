package gestorHolding;

import java.io.Serializable;
import java.time.LocalDate;

public class Asesoria implements Serializable, Mostrable {

    private Area area;
    private Empresa empresa;
    private LocalDate fechaInicio;

    public Asesoria(Empresa empresa, Area area, LocalDate fechaInicio) {
        this.empresa = empresa;
        this.area = area;
        this.fechaInicio = fechaInicio;
    }

    public void mostrar() {
        EntradaSalida.mostrarString("");
        EntradaSalida.mostrarString("ASESORIA");
        this.area.mostrar();
        this.empresa.mostrar();
        EntradaSalida.mostrarString("\tFecha de inicio: " + this.fechaInicio);
        EntradaSalida.mostrarString("");
    }

    
    /** 
     * @return Empresa
     */
    public Empresa getEmpresa() {
        return this.empresa;
    }

    public Area getArea() {
        return this.area;
    }

}
