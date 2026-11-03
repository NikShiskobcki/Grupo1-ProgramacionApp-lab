package modelo;

import java.time.LocalDate;
import java.util.List;

public class DetalleUsuario implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private final String nickname;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final LocalDate fechaNacimiento;
    private final String tipoUsuario; 
    private final String instituto;   // solo aplica a Docente, null si no corresponde
    private final String rutaImagen;

    private final List<ElementoResumen> cursos;
    private final List<ElementoResumen> ediciones;
    private final List<ElementoResumen> programas;
    private final List<ElementoResumen> inscripcionesRechazadas;

    public DetalleUsuario(String nickname, String nombre, String apellido, String email,
            LocalDate fechaNacimiento, String tipoUsuario, String instituto,
            List<ElementoResumen> cursos, List<ElementoResumen> ediciones, List<ElementoResumen> programas,
            String rutaImagen,List<ElementoResumen> inscripcionesRechazadas) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
        this.instituto = instituto;
        this.cursos = cursos;
        this.ediciones = ediciones;
        this.programas = programas;
        this.rutaImagen = rutaImagen;
        this.inscripcionesRechazadas = inscripcionesRechazadas;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getInstituto() {
        return instituto;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public List<ElementoResumen> getInscripcionesRechazadas() {
        return inscripcionesRechazadas;
    }
    
    

    
    public List<ElementoResumen> getCursos() {
        return cursos;
    }

    
    public List<ElementoResumen> getEdiciones() {
        return ediciones;
    }

    
    public List<ElementoResumen> getProgramas() {
        return programas;
    }
}
