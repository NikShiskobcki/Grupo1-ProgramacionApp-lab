package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ElementoResumenDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombre", "textoMostrar"}
)
public class ElementoResumenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String textoMostrar;

    public ElementoResumenDTO() {
    }

    public ElementoResumenDTO(
            String nombre,
            String textoMostrar) {
        this.nombre = nombre;
        this.textoMostrar = textoMostrar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTextoMostrar() {
        return textoMostrar;
    }

    public void setTextoMostrar(String textoMostrar) {
        this.textoMostrar = textoMostrar;
    }
}
