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

    public DetalleProgramaFormacion(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, List<CursoResumen> cursos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.cursos = cursos;
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

    
    
}
