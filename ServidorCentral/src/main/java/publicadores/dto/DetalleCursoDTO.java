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
    name = "DetalleCursoDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre", "descripcion", "duracion", "cantidadHoras", "creditos", "url", "fechaAlta", "instituto", "previas", "ediciones", "programas", "cantidadInscriptos", "categorias", "rutaImagen"}
)
public class DetalleCursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;
    private int duracion;
    private int cantidadHoras;
    private int creditos;
    private String url;
    private String fechaAlta;
    private String instituto;
    private List<String> previas = new ArrayList<>();
    private List<String> ediciones = new ArrayList<>();
    private List<String> programas = new ArrayList<>();
    private long cantidadInscriptos;
    private List<String> categorias = new ArrayList<>();
    private String rutaImagen;

    public DetalleCursoDTO() {
    }

    public DetalleCursoDTO(
            String nombre,
            String descripcion,
            int duracion,
            int cantidadHoras,
            int creditos,
            String url,
            String fechaAlta,
            String instituto,
            List<String> previas,
            List<String> ediciones,
            List<String> programas,
            long cantidadInscriptos,
            List<String> categorias,
            String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.cantidadHoras = cantidadHoras;
        this.creditos = creditos;
        this.url = url;
        this.fechaAlta = fechaAlta;
        this.instituto = instituto;
        this.previas = previas == null ? new ArrayList<>() : new ArrayList<>(previas);
        this.ediciones = ediciones == null ? new ArrayList<>() : new ArrayList<>(ediciones);
        this.programas = programas == null ? new ArrayList<>() : new ArrayList<>(programas);
        this.cantidadInscriptos = cantidadInscriptos;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getInstituto() {
        return instituto;
    }

    public void setInstituto(String instituto) {
        this.instituto = instituto;
    }

    public List<String> getPrevias() {
        return previas;
    }

    public void setPrevias(List<String> previas) {
        this.previas = previas == null ? new ArrayList<>() : new ArrayList<>(previas);
    }

    public List<String> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<String> ediciones) {
        this.ediciones = ediciones == null ? new ArrayList<>() : new ArrayList<>(ediciones);
    }

    public List<String> getProgramas() {
        return programas;
    }

    public void setProgramas(List<String> programas) {
        this.programas = programas == null ? new ArrayList<>() : new ArrayList<>(programas);
    }

    public long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(long cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
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
