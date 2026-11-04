package Logica.DTO;

/** Promedio del curso y puntaje del estudiante consultante (null si todavía no valoró). */
public class DetalleValoracionCurso {

    private final String nombreCurso;
    private final Double promedio;
    private final int cantidad;
    private final Integer puntajeEstudiante;

    public DetalleValoracionCurso(String nombreCurso, Double promedio,
            int cantidad, Integer puntajeEstudiante) {
        this.nombreCurso = nombreCurso;
        this.promedio = promedio;
        this.cantidad = cantidad;
        this.puntajeEstudiante = puntajeEstudiante;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    /** null si el curso todavía no tiene valoraciones. */
    public Double getPromedio() {
        return promedio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Integer getPuntajeEstudiante() {
        return puntajeEstudiante;
    }
}
