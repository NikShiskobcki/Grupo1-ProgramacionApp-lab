package modelo;

import java.time.LocalDate;

/** Inscripción a una edición cerrada con su resultado. La nota es null si no tiene. */
public class ResultadoEvaluacion implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private final String nombreEdicion;
    private final String nombreCurso;
    private final LocalDate fechaInscripcion;
    private final LocalDate fechaFin;
    private final String estado;
    private final Integer nota;

    public ResultadoEvaluacion(String nombreEdicion, String nombreCurso,
            LocalDate fechaInscripcion, LocalDate fechaFin, String estado, Integer nota) {
        this.nombreEdicion = nombreEdicion;
        this.nombreCurso = nombreCurso;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.nota = nota;
    }

    public String getNombreEdicion() { return nombreEdicion; }
    public String getNombreCurso() { return nombreCurso; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public LocalDate getFechaFin() { return fechaFin; }
    public String getEstado() { return estado; }
    public Integer getNota() { return nota; }
}
