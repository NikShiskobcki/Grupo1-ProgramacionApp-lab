/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class EdicionCurso implements Serializable {

    // ATRIBUTOS

    @Id
    private String nombre;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cupo;
    private LocalDate fechaPublicacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_nombre", nullable = false)
    private Curso curso;

    @ManyToMany
    @JoinTable(
        name = "DOCENTE_PARTICIPA_EDICION",
        joinColumns = @JoinColumn(name = "edicion_nombre"),
        inverseJoinColumns = @JoinColumn(name = "docente_nickname")
    )
    private List<Docente> docentes = new ArrayList<>();
    
    @OneToMany(mappedBy = "edicion")
    private List<InscripcionEdicion> inscripciones = new ArrayList<>();

    public void setInscripciones(List<InscripcionEdicion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public List<InscripcionEdicion> getInscripciones() {
        return inscripciones;
    }



    public EdicionCurso() {
    }


    public EdicionCurso(String nombre, LocalDate fechaInicio,
                        LocalDate fechaFin, Integer cupo,
                        LocalDate fechaPublicacion, Curso curso) {

        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupo = cupo;
        this.fechaPublicacion = fechaPublicacion;
        this.curso = curso;
    }


    // GETTERS

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

    public Curso getCurso() {
        return curso;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }


    // SETTERS

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }
}