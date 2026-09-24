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
public class InscripcionEdicion implements Serializable {

    // ATRIBUTOS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInscripcion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudiante_nickname", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "edicion_nombre", nullable = false)
    private EdicionCurso edicion;


    // CONSTRUCTORES

    public InscripcionEdicion() {
    }


    public InscripcionEdicion(LocalDate fechaInscripcion,
                              Estudiante estudiante,
                              EdicionCurso edicion) {

        this.fechaInscripcion = fechaInscripcion;
        this.estudiante = estudiante;
        this.edicion = edicion;
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

    public EdicionCurso getEdicion() {
        return edicion;
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

    public void setEdicion(EdicionCurso edicion) {
        this.edicion = edicion;
    }
}