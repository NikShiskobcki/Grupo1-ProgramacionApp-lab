package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Curso implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String descripcion;
    private int duracion;
    private int cantidadHoras;
    private int creditos;
    private String url;
    private LocalDate fechaAlta;
    private String rutaImagen;
    private Instituto instituto;

    public Curso() { }

    public Curso(String nombre, String descripcion, int duracion, int cantidadHoras, int creditos, String url, LocalDate fechaAlta, String rutaImagen, Instituto instituto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.cantidadHoras = cantidadHoras;
        this.creditos = creditos;
        this.url = url;
        this.fechaAlta = fechaAlta;
        this.rutaImagen = rutaImagen;
        this.instituto = instituto;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public int getCantidadHoras() { return cantidadHoras; }
    public void setCantidadHoras(int cantidadHoras) { this.cantidadHoras = cantidadHoras; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public Instituto getInstituto() { return instituto; }
    public void setInstituto(Instituto instituto) { this.instituto = instituto; }
}
