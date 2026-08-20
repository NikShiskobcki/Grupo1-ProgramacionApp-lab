
package Logica.controladores;

import Logica.DTO.DetalleUsuario;
import Logica.DTO.UsuarioResumen;
import java.time.LocalDate;
import java.util.List;

public interface IControlador {

    //Metodos casos de uso 
    void altaInstituto(String nombre);

    // Alta de Usuario
    List<String> listarNombresInstitutos();

    boolean existeNickname(String nickname);

    boolean existeEmail(String email);

    void altaUsuarioEstudiante(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento);

    void altaUsuarioDocente(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento, String nombreInstituto);

    // Consulta de Usuario
    List<UsuarioResumen> listarUsuarios();

    DetalleUsuario consultarUsuario(String nickname);

}
