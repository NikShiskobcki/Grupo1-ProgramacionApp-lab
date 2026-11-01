package modelo;

import java.time.LocalDate;
import java.util.List;

public class DetalleCurso implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private final String nombre;
    private final String descripcion;
    private final int duracion;
    private final int cantidadHoras;
    private final int creditos;
    private final String url;
    private final LocalDate fechaAlta;
    private final String instituto;
    private final List<String> previas;
    private final List<String> ediciones;
    private final List<String> programas;
    private final long cantidadInscriptos;
    private final List<String> categorias;
    private final String rutaImagen;

    public DetalleCurso(String nombre, String descripcion, int duracion,
                        int cantidadHoras, int creditos, String url,
                        LocalDate fechaAlta, String instituto,
                        List<String> previas, List<String> ediciones,
                        List<String> programas, long cantidadInscriptos, List<String> categorias,String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.cantidadHoras = cantidadHoras;
        this.creditos = creditos;
        this.url = url;
        this.fechaAlta = fechaAlta;
        this.instituto = instituto;
        this.previas = previas;
        this.ediciones = ediciones;
        this.programas = programas;
        this.cantidadInscriptos = cantidadInscriptos;
        this.categorias = categorias;
        this.rutaImagen = rutaImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public int getCreditos() {
        return creditos;
    }

    public String getUrl() {
        return url;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public String getInstituto() {
        return instituto;
    }

    public List<String> getPrevias() {
        return previas;
    }

    public List<String> getEdiciones() {
        return ediciones;
    }

    public List<String> getProgramas() {
        return programas;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }
    
    

    
    public long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public List<String> getCategorias() {
        return categorias;
    }
    
    
}
