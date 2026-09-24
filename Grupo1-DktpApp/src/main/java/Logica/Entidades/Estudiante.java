/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Estudiante.java
package Logica.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Estudiante extends Usuario {
    
    @OneToMany(mappedBy = "estudiante")
private List<InscripcionPrograma> inscripcionesProgramas = new ArrayList<>();
    
    @OneToMany(mappedBy = "estudiante")
private List<InscripcionEdicion> inscripcionesEdiciones = new ArrayList<>();

    public void setInscripcionesEdiciones(List<InscripcionEdicion> inscripcionesEdiciones) {
        this.inscripcionesEdiciones = inscripcionesEdiciones;
    }

    public List<InscripcionEdicion> getInscripcionesEdiciones() {
        return inscripcionesEdiciones;
    }

    public void setInscripcionesProgramas(List<InscripcionPrograma> inscripcionesProgramas) {
        this.inscripcionesProgramas = inscripcionesProgramas;
    }

    public List<InscripcionPrograma> getInscripcionesProgramas() {
        return inscripcionesProgramas;
    }

    public Estudiante() {
    }

    public Estudiante(String nickname, String nombre, String apellido,
                      String email, LocalDate fechaNacimiento) {
        super(nickname, nombre, apellido, email, fechaNacimiento);
    }
}
