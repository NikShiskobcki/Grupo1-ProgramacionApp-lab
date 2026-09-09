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

    private final List<String> cursos;
    private final List<String> ediciones;
    private final List<String> programas;

    public DetalleUsuario(String nickname, String nombre, String apellido, String email,
            LocalDate fechaNacimiento, String tipoUsuario, String instituto,
            List<String> cursos, List<String> ediciones, List<String> programas) {
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

    public List<String> getCursos() {
        return cursos;
    }

    public List<String> getEdiciones() {
        return ediciones;
    }

    public List<String> getProgramas() {
        return programas;
    }
}
