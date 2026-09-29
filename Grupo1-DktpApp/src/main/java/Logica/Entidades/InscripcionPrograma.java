/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InscripcionPrograma implements Serializable {

    // ATRIBUTOS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInscripcion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudiante_nickname", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "programa_nombre", nullable = false)
    private ProgramaFormacion programa;


    //CONSTRUCTORES

    public InscripcionPrograma() {
    }

    public InscripcionPrograma(LocalDate fechaInscripcion,
                               Estudiante estudiante,
                               ProgramaFormacion programa) {

        this.fechaInscripcion = fechaInscripcion;
        this.estudiante = estudiante;
        this.programa = programa;
    }


    // GETTERS

    public Long getId() {
        return id;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public ProgramaFormacion getPrograma() {
        return programa;
    }


    // SETTERS

    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setPrograma(ProgramaFormacion programa) {
        this.programa = programa;
    }
}