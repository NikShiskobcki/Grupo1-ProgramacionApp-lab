package Logica.DTO;

import java.time.LocalDate;


public class UsuarioEdicion {

    private final String nickname;
    private final String email;
    private final String tipoUsuario; // "Estudiante" o "Docente"

    private final String nombre;
    private final String apellido;
    private final LocalDate fechaNacimiento;
    private final String instituto; // solo Docente, null si no aplica

    public UsuarioEdicion(String nickname, String email, String tipoUsuario,
            String nombre, String apellido, LocalDate fechaNacimiento, String instituto) {
        this.nickname = nickname;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.instituto = instituto;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getInstituto() {
        return instituto;
    }
}
