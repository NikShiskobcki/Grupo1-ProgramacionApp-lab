
package Logica.controladores;
import Logica.Entidades.Instituto;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;


import java.time.LocalDate;
import java.util.List;

public interface IControlador {

    void altaInstituto(String nombre);


    // Alta de Curso 
    List<Instituto> listarInstitutos();
    List<Curso> listarCursos();
    List<Curso> listarCursosPorInstituto(String nombreInstituto);
    boolean existeCurso(String nombre);
    void altaCurso(String nombre, String descripcion, int duracion,
                   int cantidadHoras, int creditos, String url,
                   LocalDate fechaAlta, Instituto instituto,
                   List<Curso> previas);
    
    
    boolean existeInstituto(String nombre);
    
    

    // Alta de Usuario
    List<String> listarNombresInstitutos();

    boolean existeNickname(String nickname);

    boolean existeEmail(String email);

    void altaUsuarioEstudiante(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento);

    void altaUsuarioDocente(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento, String nombreInstituto);
    
    // Alta Programa Formacion
    boolean existePrograma(String nombre);
    void altaPrograma(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta);
    
    //Agregar curso a Programa Formacion
    List<String> listarProgramas();
    void agregarCursoAPrograma(String nombrePrograma, String nombreCurso);
    
    //Alta Edicion 
    List<Docente> listarDocentesPorInstituto(String nombreInstituto);
    boolean existeEdicion(String nombre);
    void altaEdicionCurso(
        String nombre,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Integer cupo,
        String nombreCurso,
        List<Docente> docentes);
    
    List<EdicionCurso> listarEdicionesPorCurso(String nombreCurso);

    EdicionCurso buscarEdicion(String nombre);

}

   
