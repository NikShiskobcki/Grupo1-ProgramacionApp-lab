package Logica.DTO;

public class CursoTendencia {

    private final String nombre;
    private final String nombreInstituto;
    private final String rutaImagen;
    private final long visitas;

    public CursoTendencia(String nombre, String nombreInstituto,
                          String rutaImagen, long visitas) {
        this.nombre = nombre;
        this.nombreInstituto = nombreInstituto;
        this.rutaImagen = rutaImagen;
        this.visitas = visitas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreInstituto() {
        return nombreInstituto;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public long getVisitas() {
        return visitas;
    }
}