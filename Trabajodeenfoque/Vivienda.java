
package Trabajodeenfoque;

public class Vivienda {
    private int id;
    private String ubicacion;
    private int metros2;
    private int NumeroHabitaciones;
    private int NumeroBanos;
    private int Precio;
    
    public Vivienda (int id, String ubicacion, int metros2, int NumeroHabitaciones, int NumeroBanos, int Precio){        
        this.Precio = Precio;
        this.id = id;
        this.ubicacion = ubicacion;
        this.metros2 = metros2;
        this.NumeroHabitaciones = NumeroHabitaciones;
        this.NumeroBanos = NumeroBanos;   
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMetros2() {
        return metros2;
    }

    public void setMetros2(int metros2) {
        this.metros2 = metros2;
    }

    public int getNumeroHabitaciones() {
        return NumeroHabitaciones;
    }

    public void setNumeroHabitaciones(int NumeroHabitaciones) {
        this.NumeroHabitaciones = NumeroHabitaciones;
    }

    public int getNumeroBaños() {
        return NumeroBanos;
    }

    public void setNumeroBaños(int NumeroBaños) {
        this.NumeroBanos = NumeroBaños;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int Precio) {
        this.Precio = Precio;
    }


}
