package Logica.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Puntaje (1 a 5) que un estudiante asigna a un curso.
 * La restricción única garantiza una sola valoración por estudiante y curso.
 */
@Entity
@Table(name = "VALORACIONCURSO",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_valoracion_estudiante_curso",
                columnNames = {"estudiante_nickname", "curso_nombre"}))
public class ValoracionCurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudiante_nickname", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_nombre", nullable = false)
    private Curso curso;

    @Column(nullable = false)
    private int puntaje;

    private LocalDate fecha;

    public ValoracionCurso() {
    }

    public ValoracionCurso(Estudiante estudiante, Curso curso, int puntaje, LocalDate fecha) {
        if (puntaje < 1 || puntaje > 5) {
            throw new IllegalArgumentException("El puntaje debe estar entre 1 y 5.");
        }
        this.estudiante = estudiante;
        this.curso = curso;
        this.puntaje = puntaje;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
