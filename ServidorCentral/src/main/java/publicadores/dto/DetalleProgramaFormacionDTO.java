package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import Logica.DTO.CursoResumen;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "DetalleProgramaFormacionDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre", "descripcion", "fechaInicio", "fechaFin", "fechaAlta", "cursos", "categorias", "rutaImagen"}
)
public class DetalleProgramaFormacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private String fechaAlta;
    private List<CursoResumen> cursos = new ArrayList<>();
    private List<String> categorias = new ArrayList<>();
    private String rutaImagen;

    public DetalleProgramaFormacionDTO() {
    }

    public DetalleProgramaFormacionDTO(
            String nombre,
            String descripcion,
            String fechaInicio,
            String fechaFin,
            String fechaAlta,
            List<CursoResumen> cursos,
            List<String> categorias,
            String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.cursos = cursos == null ? new ArrayList<>() : new ArrayList<>(cursos);
        this.categorias = categorias == null ? new ArrayList<>() : new ArrayList<>(categorias);
        this.rutaImagen = rutaImagen;
    }

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

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<CursoResumen> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoResumen> cursos) {
        this.cursos = cursos == null ? new ArrayList<>() : new ArrayList<>(cursos);
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias == null ? new ArrayList<>() : new ArrayList<>(categorias);
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
