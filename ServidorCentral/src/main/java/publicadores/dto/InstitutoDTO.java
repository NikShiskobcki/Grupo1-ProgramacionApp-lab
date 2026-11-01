package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "InstitutoDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre"}
)
public class InstitutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;

    public InstitutoDTO() {
    }

    public InstitutoDTO(
            String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
