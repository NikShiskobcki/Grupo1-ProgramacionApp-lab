package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Docente implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String nickname;
    private String nombre;
    private String apellido;
    private String rutaImagen;

    public Docente() { }

    public Docente(String nickname, String nombre, String apellido, String rutaImagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rutaImagen = rutaImagen;
    }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}
