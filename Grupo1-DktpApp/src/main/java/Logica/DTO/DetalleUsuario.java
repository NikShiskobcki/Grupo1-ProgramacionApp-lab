package Logica.DTO;

import java.time.LocalDate;
import java.util.List;

public class DetalleUsuario {

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
    
    

    /**
     * Cursos registrados por el usuario (solo aplica a Docente).
     * Cada elemento conserva el nombre real del curso (clave para volver a
     * consultarlo con IControlador#consultarCurso) y un texto descriptivo
     * para mostrar en la lista.
     */
    public List<ElementoResumen> getCursos() {
        return cursos;
    }

    /**
     * Ediciones de curso registradas (Docente) o en las que se inscribió
     * (Estudiante). Cada elemento conserva el nombre real de la edición
     * (clave para IControlador#consultarEdicion).
     */
    public List<ElementoResumen> getEdiciones() {
        return ediciones;
    }

    /**
     * Programas de formación registrados (Docente) o en los que se
     * inscribió (Estudiante). Cada elemento conserva el nombre real del
     * programa (clave para IControlador#consultarPrograma).
     */
    public List<ElementoResumen> getProgramas() {
        return programas;
    }
}
