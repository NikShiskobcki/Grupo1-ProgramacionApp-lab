/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.DTO;

import java.time.LocalDate;
import java.util.List;

public class DetalleEdicionCurso {

    private final String nombre;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final Integer cupo;
    private final LocalDate fechaPublicacion;
    private final String curso;
    private final List<String> docentes;

    public DetalleEdicionCurso(String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                               Integer cupo, LocalDate fechaPublicacion, String curso,
                               List<String> docentes) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupo = cupo;
        this.fechaPublicacion = fechaPublicacion;
        this.curso = curso;
        this.docentes = docentes;
    }

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

    public String getCurso() {
        return curso;
    }

    public List<String> getDocentes() {
        return docentes;
    }
}