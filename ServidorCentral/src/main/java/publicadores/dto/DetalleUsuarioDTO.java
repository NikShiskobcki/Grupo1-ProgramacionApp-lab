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
    name = "DetalleUsuarioDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nickname", "nombre", "apellido", "email", "fechaNacimiento", "tipoUsuario", "instituto", "rutaImagen", "cursos", "ediciones", "programas", "inscripcionesRechazadas"}
)
public class DetalleUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private String fechaNacimiento;
    private String tipoUsuario;
    private String instituto;
    private String rutaImagen;
    private List<ElementoResumenDTO> cursos = new ArrayList<>();
    private List<ElementoResumenDTO> ediciones = new ArrayList<>();
    private List<ElementoResumenDTO> programas = new ArrayList<>();
    private List<ElementoResumenDTO> inscripcionesRechazadas = new ArrayList<>();

    public DetalleUsuarioDTO() {
    }

    public DetalleUsuarioDTO(
            String nickname,
            String nombre,
            String apellido,
            String email,
            String fechaNacimiento,
            String tipoUsuario,
            String instituto,
            String rutaImagen,
            List<ElementoResumenDTO> cursos,
            List<ElementoResumenDTO> ediciones,
            List<ElementoResumenDTO> programas,
            List<ElementoResumenDTO> inscripcionesRechazadas) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
        this.instituto = instituto;
        this.rutaImagen = rutaImagen;
        this.cursos = cursos == null ? new ArrayList<>() : new ArrayList<>(cursos);
        this.ediciones = ediciones == null ? new ArrayList<>() : new ArrayList<>(ediciones);
        this.programas = programas == null ? new ArrayList<>() : new ArrayList<>(programas);
        this.inscripcionesRechazadas = inscripcionesRechazadas == null ? new ArrayList<>() : new ArrayList<>(inscripcionesRechazadas);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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

    public List<ElementoResumenDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<ElementoResumenDTO> cursos) {
        this.cursos = cursos == null ? new ArrayList<>() : new ArrayList<>(cursos);
    }

    public List<ElementoResumenDTO> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<ElementoResumenDTO> ediciones) {
        this.ediciones = ediciones == null ? new ArrayList<>() : new ArrayList<>(ediciones);
    }

    public List<ElementoResumenDTO> getProgramas() {
        return programas;
    }

    public void setProgramas(List<ElementoResumenDTO> programas) {
        this.programas = programas == null ? new ArrayList<>() : new ArrayList<>(programas);
    }

    public List<ElementoResumenDTO> getInscripcionesRechazadas() {
        return inscripcionesRechazadas;
    }

    public void setInscripcionesRechazadas(List<ElementoResumenDTO> inscripcionesRechazadas) {
        this.inscripcionesRechazadas = inscripcionesRechazadas == null ? new ArrayList<>() : new ArrayList<>(inscripcionesRechazadas);
    }
}
