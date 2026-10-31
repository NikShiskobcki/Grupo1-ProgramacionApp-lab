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
    name = "DetalleEdicionCursoDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre", "fechaInicio", "fechaFin", "cupo", "fechaPublicacion", "curso", "docentes", "cantidadInscriptos", "rutaImagen", "categorias"}
)
public class DetalleEdicionCursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private Integer cupo;
    private String fechaPublicacion;
    private String curso;
    private List<String> docentes = new ArrayList<>();
    private long cantidadInscriptos;
    private String rutaImagen;
    private List<String> categorias = new ArrayList<>();

    public DetalleEdicionCursoDTO() {
    }

    public DetalleEdicionCursoDTO(
            String nombre,
            String fechaInicio,
            String fechaFin,
            Integer cupo,
            String fechaPublicacion,
            String curso,
            List<String> docentes,
            long cantidadInscriptos,
            String rutaImagen,
            List<String> categorias) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupo = cupo;
        this.fechaPublicacion = fechaPublicacion;
        this.curso = curso;
        this.docentes = docentes == null ? new ArrayList<>() : new ArrayList<>(docentes);
        this.cantidadInscriptos = cantidadInscriptos;
        this.rutaImagen = rutaImagen;
        this.categorias = categorias == null ? new ArrayList<>() : new ArrayList<>(categorias);
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<String> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<String> docentes) {
        this.docentes = docentes == null ? new ArrayList<>() : new ArrayList<>(docentes);
    }

    public long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(long cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias == null ? new ArrayList<>() : new ArrayList<>(categorias);
    }
}
