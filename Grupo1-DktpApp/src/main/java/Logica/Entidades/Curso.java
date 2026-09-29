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
public class Curso implements Serializable {

    // ATRIBUTOS

    @Id
    private String nombre;

    private String descripcion;
    private int duracion;
    private int cantidadHoras;
    private int creditos;
    private String url;
    private LocalDate fechaAlta;
     

    @ManyToOne(optional = false)
    @JoinColumn(name = "instituto_nombre", nullable = false)
    private Instituto instituto;

    @ManyToMany
    @JoinTable(
        name = "CURSO_PREVIA",
        joinColumns = @JoinColumn(name = "curso_nombre"),
        inverseJoinColumns = @JoinColumn(name = "previa_nombre")
    )
    private List<Curso> previas = new ArrayList<>();

    @OneToMany(mappedBy = "curso")
    private List<EdicionCurso> ediciones = new ArrayList<>();


    public Curso() {
    }


    public Curso(String nombre, String descripcion, int duracion,
                 int cantidadHoras, int creditos, String url,
                 LocalDate fechaAlta, Instituto instituto) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.cantidadHoras = cantidadHoras;
        this.creditos = creditos;
        this.url = url;
        this.fechaAlta = fechaAlta;
        this.instituto = instituto;
    }


    // GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public int getCreditos() {
        return creditos;
    }

    public String getUrl() {
        return url;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Instituto getInstituto() {
        return instituto;
    }

    public List<Curso> getPrevias() {
        return previas;
    }

    public List<EdicionCurso> getEdiciones() {
        return ediciones;
    }


    // SETTERS

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setInstituto(Instituto instituto) {
        this.instituto = instituto;
    }

    public void setPrevias(List<Curso> previas) {
        this.previas = previas;
    }

    public void setEdiciones(List<EdicionCurso> ediciones) {
        this.ediciones = ediciones;
    }
}