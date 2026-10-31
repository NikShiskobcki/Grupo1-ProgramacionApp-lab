package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class EdicionCurso implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cupo;
    private LocalDate fechaPublicacion;
    private String rutaImagen;
    private String nombreCurso;
    private List<Docente> docentes = new ArrayList<>();

    public EdicionCurso() { }

    public EdicionCurso(String nombre, LocalDate fechaInicio, LocalDate fechaFin, Integer cupo, LocalDate fechaPublicacion, String rutaImagen, String nombreCurso, List<Docente> docentes) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupo = cupo;
        this.fechaPublicacion = fechaPublicacion;
        this.rutaImagen = rutaImagen;
        this.nombreCurso = nombreCurso;
        this.docentes = docentes;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Integer getCupo() { return cupo; }
    public void setCupo(Integer cupo) { this.cupo = cupo; }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public List<Docente> getDocentes() { return docentes; }
    public void setDocentes(List<Docente> docentes) { this.docentes = docentes; }
}
