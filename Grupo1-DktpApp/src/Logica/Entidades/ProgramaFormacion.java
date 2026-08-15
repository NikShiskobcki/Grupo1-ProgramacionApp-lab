/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Entidades;
import java.time.LocalDate;

//@Entity
public class ProgramaFormacion {
    private String nombre;
    private String descripcion;
    private LocalDate fInicio;
    private LocalDate fFin;
    private LocalDate fAlta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getfInicio() {
        return fInicio;
    }

    public void setfInicio(LocalDate fInicio) {
        this.fInicio = fInicio;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public LocalDate getfAlta() {
        return fAlta;
    }

    public void setfAlta(LocalDate fAlta) {
        this.fAlta = fAlta;
    }
    
    
}
