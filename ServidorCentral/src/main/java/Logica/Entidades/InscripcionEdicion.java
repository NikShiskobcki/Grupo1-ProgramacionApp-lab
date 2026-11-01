/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    
    @Enumerated(EnumType.STRING)
    private EstadoInscripcion estado = EstadoInscripcion.INSCRIPTO;

    private Integer nota;

    /** URL opcional de un video de motivación (http/https). */
    @Column(length = 500)
    private String videoUrl;

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

    public EstadoInscripcion getEstado() {
        return estado == null ? EstadoInscripcion.INSCRIPTO : estado;
    }

    public Integer getNota() {
        return nota;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
    
    
    


    // SETTERS

//    public void setId(Long id) {
//        this.id = id;
//    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

//    public void setEstudiante(Estudiante estudiante) {
//        this.estudiante = estudiante;
//    }

//    public void setEdicion(EdicionCurso edicion) {
//        this.edicion = edicion;
//    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    public void setNota(Integer nota) {
        if (nota != null && (nota < 1 || nota > 12)) {
            throw new IllegalArgumentException(
                    "La nota debe estar entre 1 y 12");
        }
        this.nota = nota;
    }
    
    
}