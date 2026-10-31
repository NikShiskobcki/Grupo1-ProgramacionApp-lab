package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UsuarioEdicionDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nickname", "email", "tipoUsuario", "nombre", "apellido", "fechaNacimiento", "instituto", "rutaImagen"}
)
public class UsuarioEdicionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;
    private String email;
    private String tipoUsuario;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String instituto;
    private String rutaImagen;

    public UsuarioEdicionDTO() {
    }

    public UsuarioEdicionDTO(
            String nickname,
            String email,
            String tipoUsuario,
            String nombre,
            String apellido,
            String fechaNacimiento,
            String instituto,
            String rutaImagen) {
        this.nickname = nickname;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.instituto = instituto;
        this.rutaImagen = rutaImagen;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getInstituto() {
        return instituto;
    }

    public void setInstituto(String instituto) {
        this.instituto = instituto;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
