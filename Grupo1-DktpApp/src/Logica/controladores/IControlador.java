package Logica.controladores;

import Logica.Entidades.Instituto;
import Logica.Entidades.Curso;
import java.time.LocalDate;
import java.util.List;

public interface IControlador {

    void altaInstituto(String nombre);

    // Alta de Curso 
    List<Instituto> listarInstitutos();
    List<Curso> listarCursos();
    boolean existeCurso(String nombre);
    void altaCurso(String nombre, String descripcion, int duracion,
                   int cantidadHoras, int creditos, String url,
                   LocalDate fechaAlta, Instituto instituto,
                   List<Curso> previas);
    
    
     // Alta de Usuario
    List<String> listarNombresInstitutos();
    boolean existeNickname(String nickname);
    boolean existeEmail(String email);
    void altaUsuarioEstudiante(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento);
    void altaUsuarioDocente(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento, String nombreInstituto);
    
    
    // Alta ProgramaFormacion
    boolean existePrograma(String nombre);
    void altaPrograma(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta);
}

