package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "CursoDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre", "descripcion", "duracion", "cantidadHoras", "creditos", "url", "fechaAlta", "rutaImagen", "instituto"}
)
public class CursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;
    private int duracion;
    private int cantidadHoras;
    private int creditos;
    private String url;
    private String fechaAlta;
    private String rutaImagen;
    private InstitutoDTO instituto;

    public CursoDTO() {
    }

    public CursoDTO(
            String nombre,
            String descripcion,
            int duracion,
            int cantidadHoras,
            int creditos,
            String url,
            String fechaAlta,
            String rutaImagen,
            InstitutoDTO instituto) {
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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public InstitutoDTO getInstituto() {
        return instituto;
    }

    public void setInstituto(InstitutoDTO instituto) {
        this.instituto = instituto;
    }
}
