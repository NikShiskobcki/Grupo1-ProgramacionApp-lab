package Logica.DTO;

public class CursoResumen {
    
    private final String nombre;
    private final String nombreInstituto;

    public CursoResumen(String nombre, String nombreInstituto) {
        this.nombre = nombre;
        this.nombreInstituto = nombreInstituto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreInstituto() {
        return nombreInstituto;
    }

    @Override
    public String toString() {
        return (nombre + " ("+ nombreInstituto + ")");  
    }
 
}
