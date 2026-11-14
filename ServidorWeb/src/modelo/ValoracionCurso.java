package modelo;

/**
 * Promedio del curso, puntaje del estudiante (null si todavía no valoró) y
 * desglose de cuántos estudiantes asignaron 1, 2, 3, 4 y 5 estrellas.
 */
public class ValoracionCurso implements java.io.Serializable {

    private static final long serialVersionUID = 2L;
    private final String nombreCurso;
    private final Double promedio;
    private final int cantidad;
    private final Integer puntajeEstudiante;
    /** Posición 0 = cantidad de valoraciones de 1 estrella ... posición 4 = de 5 estrellas. */
    private final int[] cantidadPorPuntaje;

    public ValoracionCurso(String nombreCurso, Double promedio, int cantidad, Integer puntajeEstudiante) {
        this(nombreCurso, promedio, cantidad, puntajeEstudiante, null);
    }

    public ValoracionCurso(String nombreCurso, Double promedio, int cantidad,
            Integer puntajeEstudiante, int[] cantidadPorPuntaje) {
        this.nombreCurso = nombreCurso;
        this.promedio = promedio;
        this.cantidad = cantidad;
        this.puntajeEstudiante = puntajeEstudiante;
        this.cantidadPorPuntaje = cantidadPorPuntaje == null ? null : cantidadPorPuntaje.clone();
    }

    public String getNombreCurso() { return nombreCurso; }
    /** null si el curso todavía no tiene valoraciones. */
    public Double getPromedio() { return promedio; }
    public int getCantidad() { return cantidad; }
    public Integer getPuntajeEstudiante() { return puntajeEstudiante; }

    /** true si el Servidor Central informó el desglose por cantidad de estrellas. */
    public boolean tieneDesglose() { return cantidadPorPuntaje != null; }

    /** Cantidad de estudiantes que dieron exactamente {@code estrellas} (1 a 5). */
    public int getCantidadConPuntaje(int estrellas) {
        if (cantidadPorPuntaje == null || estrellas < 1 || estrellas > 5) return 0;
        return cantidadPorPuntaje[estrellas - 1];
    }
}
