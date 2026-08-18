/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Docente extends Usuario {

    @ManyToOne(optional = false)
    @JoinColumn(name = "instituto_nombre", nullable = false)
    private Instituto instituto;
    
    @ManyToMany(mappedBy = "docentes")
    private List<EdicionCurso> ediciones = new ArrayList<>();

    public void setEdiciones(List<EdicionCurso> ediciones) {
        this.ediciones = ediciones;
    }

    public List<EdicionCurso> getEdiciones() {
        return ediciones;
    }

    public Docente() {
    }

    public Docente(String nickname, String nombre, String apellido,
                   String email, LocalDate fechaNacimiento,
                   Instituto instituto) {
        super(nickname, nombre, apellido, email, fechaNacimiento);
        this.instituto = instituto;
    }

    public Instituto getInstituto() {
        return instituto;
    }

    public void setInstituto(Instituto instituto) {
        this.instituto = instituto;
    }
}