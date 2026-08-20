package Logica.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class ProgramaFormacion implements Serializable {

    // ATRIBUTOS

    @Id
    private String nombre;

    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaAlta;

    @ManyToMany
    @JoinTable(
        name = "PROGRAMA_CURSO",
        joinColumns = @JoinColumn(name = "programa_nombre"),
        inverseJoinColumns = @JoinColumn(name = "curso_nombre")
    )
    private List<Curso> cursos = new ArrayList<>();
    
    @OneToMany(mappedBy = "programa")
private List<InscripcionPrograma> inscripciones = new ArrayList<>();

    public void setInscripciones(List<InscripcionPrograma> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public List<InscripcionPrograma> getInscripciones() {
        return inscripciones;
    }


    //CONSTRUCTORES

    public ProgramaFormacion() {
    }

    public ProgramaFormacion(String nombre, String descripcion,
                             LocalDate fechaInicio, LocalDate fechaFin,
                             LocalDate fechaAlta) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
    }


    // GETTERS

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

    public List<Curso> getCursos() {
        return cursos;
    }


    // SETTERS

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
