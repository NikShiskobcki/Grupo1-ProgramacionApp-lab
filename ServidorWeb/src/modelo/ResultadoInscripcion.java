package modelo;

import java.time.LocalDate;

public class ResultadoInscripcion implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private final String nombreEdicion;
    private final String nombreCurso;
    private final LocalDate fechaInscripcion;
    private final String estado;

    public ResultadoInscripcion(String nombreEdicion, String nombreCurso, LocalDate fechaInscripcion, String estado) {
        this.nombreEdicion = nombreEdicion;
        this.nombreCurso = nombreCurso;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }
}
