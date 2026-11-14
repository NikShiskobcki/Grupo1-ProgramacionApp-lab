package Logica.DTO;

/** Promedio del curso y puntaje del estudiante consultante (null si todavía no valoró). */
public class DetalleValoracionCurso {

    private final String nombreCurso;
    private final Double promedio;
    private final int cantidad;
    private final Integer puntajeEstudiante;
    /** Posición 0 = cantidad de valoraciones de 1 estrella ... posición 4 = de 5 estrellas. */
    private final int[] cantidadPorPuntaje;

    public DetalleValoracionCurso(String nombreCurso, Double promedio,
            int cantidad, Integer puntajeEstudiante, int[] cantidadPorPuntaje) {
        this.nombreCurso = nombreCurso;
        this.promedio = promedio;
        this.cantidad = cantidad;
        this.puntajeEstudiante = puntajeEstudiante;
        this.cantidadPorPuntaje = cantidadPorPuntaje == null ? new int[5] : cantidadPorPuntaje.clone();
    }

    /** Cantidad de estudiantes que asignaron 1, 2, 3, 4 y 5 estrellas (siempre 5 valores). */
    public int[] getCantidadPorPuntaje() {
        return cantidadPorPuntaje.clone();
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
