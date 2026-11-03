package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "EdicionCursoDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre", "fechaInicio", "fechaFin", "cupo", "fechaPublicacion", "rutaImagen", "nombreCurso", "docentes"}
)
public class EdicionCursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private Integer cupo;
    private String fechaPublicacion;
    private String rutaImagen;
    private String nombreCurso;
    private List<DocenteDTO> docentes = new ArrayList<>();

    public EdicionCursoDTO() {
    }

    public EdicionCursoDTO(
            String nombre,
            String fechaInicio,
            String fechaFin,
            Integer cupo,
            String fechaPublicacion,
            String rutaImagen,
            String nombreCurso,
            List<DocenteDTO> docentes) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupo = cupo;
        this.fechaPublicacion = fechaPublicacion;
        this.rutaImagen = rutaImagen;
        this.nombreCurso = nombreCurso;
        this.docentes = docentes == null ? new ArrayList<>() : new ArrayList<>(docentes);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public List<DocenteDTO> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<DocenteDTO> docentes) {
        this.docentes = docentes == null ? new ArrayList<>() : new ArrayList<>(docentes);
    }
}
