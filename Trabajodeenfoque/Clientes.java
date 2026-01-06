
package Trabajodeenfoque;

public class Clientes {
    
    private int id;
    private String nombre;
    private String dni;
    private String Email;
    
    
    public Clientes (int id, String nombre, String dni, String Email){
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.Email = Email;            
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}
