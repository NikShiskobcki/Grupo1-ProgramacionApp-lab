// Usuario.java
package Logica.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements Serializable {

    @Id
    private String nickname;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String contrasenia;

    private LocalDate fechaNacimiento;

    private String rutaImagen;
    
    @ManyToMany
    @JoinTable(
            name = "USUARIO_SEGUIDOS",
            joinColumns = @JoinColumn(name = "seguidor_nickname"),
            inverseJoinColumns = @JoinColumn(name = "seguido_nickname")
    )
    private List<Usuario> seguidos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nickname, String nombre, String apellido,
                   String email, String contrasenia, LocalDate fechaNacimiento) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
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

    public List<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(List<Usuario> seguidos) {
        this.seguidos = seguidos;
    }
    
    

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
