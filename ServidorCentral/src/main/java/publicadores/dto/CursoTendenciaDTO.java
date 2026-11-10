package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP para el listado de tendencias. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "CursoTendenciaDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre", "nombreInstituto", "rutaImagen", "visitas"}
)
public class CursoTendenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String nombreInstituto;
    private String rutaImagen;
    private long visitas;

    public CursoTendenciaDTO() {
    }

    public CursoTendenciaDTO(String nombre, String nombreInstituto,
                             String rutaImagen, long visitas) {
        this.nombre = nombre;
        this.nombreInstituto = nombreInstituto;
        this.rutaImagen = rutaImagen;
        this.visitas = visitas;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombreInstituto() { return nombreInstituto; }
    public void setNombreInstituto(String nombreInstituto) { this.nombreInstituto = nombreInstituto; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public long getVisitas() { return visitas; }
    public void setVisitas(long visitas) { this.visitas = visitas; }
}