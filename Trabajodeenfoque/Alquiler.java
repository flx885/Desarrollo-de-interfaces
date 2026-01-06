
package Trabajodeenfoque;

import java.util.Date;


public class Alquiler {
    private int idalquiler;
    private Date FechaInicio;
    private int duracion;
    private Clientes clientes;    
    private Vivienda Vivienda;
    
    public Alquiler ( int idalquiler, Date FechaInicio, int duracion, Vivienda Vivienda, Clientes clientes){
        this.idalquiler = idalquiler;
        this.FechaInicio = FechaInicio;
        this.duracion = duracion;
        this.Vivienda = Vivienda;
        this.clientes = clientes;               
    }
    public int getIdalquiler() {
        return idalquiler;
    }

    public void setIdalquiler(int idalquiler) {
        this.idalquiler = idalquiler;
    }

    public Vivienda getVivienda() {
        return Vivienda;
    }

    public void setVivienda(Vivienda Vivienda) {
        this.Vivienda = Vivienda;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }
    

}
