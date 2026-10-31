package modelo;

import java.time.LocalDate;
import java.util.List;

public class DetalleEdicionCurso implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private final String nombre;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final Integer cupo;
    private final LocalDate fechaPublicacion;
    private final String curso;
    private final List<String> docentes;
    private final long cantidadInscriptos;
    private final String rutaImagen;
    private final List<String> categorias;

    public DetalleEdicionCurso(String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                               Integer cupo, LocalDate fechaPublicacion, String curso,
                               List<String> docentes, long cantidadInscriptos,String rutaImagen) {
        this(nombre, fechaInicio, fechaFin, cupo, fechaPublicacion, curso,
             docentes, cantidadInscriptos, rutaImagen, new java.util.ArrayList<>());
    }

    public DetalleEdicionCurso(String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                               Integer cupo, LocalDate fechaPublicacion, String curso,
                               List<String> docentes, long cantidadInscriptos,String rutaImagen,
                               List<String> categorias) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupo = cupo;
        this.fechaPublicacion = fechaPublicacion;
        this.curso = curso;
        this.docentes = docentes;
        this.cantidadInscriptos = cantidadInscriptos;
        this.rutaImagen = rutaImagen;
        this.categorias = categorias;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Integer getCupo() {
        return cupo;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getCurso() {
        return curso;
    }

    public List<String> getDocentes() {
        return docentes;
    }

    
    public long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    
    public List<String> getCategorias() {
        return categorias;
    }
    
    
}
