package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "DocenteDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nickname", "nombre", "apellido", "rutaImagen"}
)
public class DocenteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;
    private String nombre;
    private String apellido;
    private String rutaImagen;

    public DocenteDTO() {
    }

    public DocenteDTO(
            String nickname,
            String nombre,
            String apellido,
            String rutaImagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rutaImagen = rutaImagen;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
