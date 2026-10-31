package Logica.DTO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CursoResumen",
        namespace = "http://ws.edext.utec.edu.uy/",
        propOrder = {"nombre", "nombreInstituto"}
)
public class CursoResumen {

    private String nombre;
    private String nombreInstituto;

    public CursoResumen() {
    }

    public CursoResumen(String nombre, String nombreInstituto) {
        this.nombre = nombre;
        this.nombreInstituto = nombreInstituto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreInstituto() {
        return nombreInstituto;
    }

    @Override
    public String toString() {
        return nombre + " (" + nombreInstituto + ")";
    }
}
