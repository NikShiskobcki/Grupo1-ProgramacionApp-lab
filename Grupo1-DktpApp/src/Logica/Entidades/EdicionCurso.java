/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Entidades;

import java.time.LocalDate;

public class EdicionCurso {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cupo;
    private LocalDate fPublicacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public LocalDate getfPublicacion() {
        return fPublicacion;
    }

    public void setfPublicacion(LocalDate fPublicacion) {
        this.fPublicacion = fPublicacion;
    }
    
    
}
