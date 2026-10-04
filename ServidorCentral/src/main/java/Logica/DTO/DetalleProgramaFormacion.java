package Logica.DTO;

import java.time.LocalDate;
import java.util.List;

public class DetalleProgramaFormacion {
    private final String nombre;
    private final String descripcion;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final LocalDate fechaAlta;
    private final List<CursoResumen> cursos;
    private final List<String> categorias;
    private final String rutaImagen;

    public DetalleProgramaFormacion(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, List<CursoResumen> cursos, List<String> categorias,String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.cursos = cursos;
        this.categorias = categorias;
        this.rutaImagen = rutaImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public List<CursoResumen> getCursos() {
        return cursos;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }
    
    

    
    
}
