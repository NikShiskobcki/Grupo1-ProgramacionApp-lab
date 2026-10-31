package modelo;

public class CursoResumen implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String nombreInstituto;

    public CursoResumen() {
    }

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
        return nombre + " (" + nombreInstituto + ")";
    }
}
