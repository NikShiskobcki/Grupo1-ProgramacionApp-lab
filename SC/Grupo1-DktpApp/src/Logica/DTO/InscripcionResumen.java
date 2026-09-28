/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.DTO;

import java.time.LocalDate;

public class InscripcionResumen {
    private final Long id;
    private final String nicknameEstudiante;
    private final String nombreEstudiante;
    private final LocalDate fechaInscripcion;
    private final String estado;
    private final double prioridad;

    public InscripcionResumen(Long id, String nicknameEstudiante, String nombreEstudiante, LocalDate fechaInscripcion, String estado, double prioridad) {
        this.id = id;
        this.nicknameEstudiante = nicknameEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.prioridad = prioridad;
    }

    public Long getId() {
        return id;
    }

    public String getNicknameEstudiante() {
        return nicknameEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public double getPrioridad() {
        return prioridad;
    }
    
    
}
