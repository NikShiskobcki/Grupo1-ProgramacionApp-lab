
package Logica.controladores;

import Logica.DTO.DetalleCurso;
import Logica.DTO.DetalleEdicionCurso;

import Logica.DTO.DetalleProgramaFormacion;
import Logica.DTO.DetalleUsuario;
import Logica.DTO.InscripcionResumen;
import Logica.DTO.ResultadoInscripcion;
import Logica.DTO.UsuarioEdicion;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Instituto;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.Estudiante;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.InscripcionPrograma;

import java.time.LocalDate;
import java.util.List;

public interface IControlador {

    void cargarDatosPrueba();
    void altaInstituto(String nombre);


    // Alta de Curso
    List<Instituto> listarInstitutos();

    List<Curso> listarCursos();

    List<Curso> listarCursosPorInstituto(String nombreInstituto);

    boolean existeCurso(String nombre);
    

    void altaCurso(
            String nombre,
            String descripcion,
            int duracion,
            int cantidadHoras,
            int creditos,
            String url,
            LocalDate fechaAlta,
            Instituto instituto,
            List<Curso> previas,
            List<String> nombresCategorias,
            String rutaImagen
    );

    DetalleCurso consultarCurso(String nombreCurso);
    
    boolean existeInstituto(String nombre);


    // Alta de Usuario
    List<String> listarNombresInstitutos();

    boolean existeNickname(String nickname);

    boolean existeEmail(String email);

    void altaUsuarioEstudiante(
            String nickname,
            String nombre,
            String apellido,
            String email,
            String contrasenia,
            LocalDate fechaNacimiento,
            String rutaImagen
    );

    void altaUsuarioDocente(
            String nickname,
            String nombre,
            String apellido,
            String email,
            String contrasenia,
            LocalDate fechaNacimiento,
            String nombreInstituto,
            String rutaImagen
    );


    // Alta Programa Formacion
    boolean existePrograma(String nombre);

    void altaPrograma(
            String nombre,
            String descripcion,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            LocalDate fechaAlta,
            String rutaImagen
    );


    // Agregar curso a Programa Formacion
    List<String> listarProgramas();

    void agregarCursoAPrograma(
            String nombrePrograma,
            String nombreCurso
    );
    
    // Consulta Programa de Formacion
    DetalleProgramaFormacion consultarPrograma(String nombre);


    // Alta y Consulta de Edicion
    List<Docente> listarDocentesPorInstituto(String nombreInstituto);

    boolean existeEdicion(String nombre);

    void altaEdicionCurso(
            String nombre,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer cupo,
            String nombreCurso,
            List<Docente> docentes,
            String rutaImagen
    );

    List<EdicionCurso> listarEdicionesPorCurso(String nombreCurso);

    EdicionCurso buscarEdicion(String nombre);

    DetalleEdicionCurso consultarEdicion(String nombreEdicion);
    
    void inscribirEstudianteEdicion(String nicknameEstudiante,String nombreEdicion,LocalDate fechaInscripcion);
    
    void modificarInscripcionEdicion(Long idInscripcion, LocalDate nuevaFecha);
    
    //Inscripcion a edición 
    List<EdicionCurso> listarEdicionesVigentesPorCurso(String nombreCurso);
    
    List<Estudiante> listarEstudiantes();
    
    InscripcionEdicion buscarInscripcionEdicion(
        String nicknameEstudiante,
        String nombreEdicion);
    
    List<InscripcionResumen> listarInscripcionesPorEdicion(String nombreEdicion, boolean ordenarPorPrioridad);
    
    void seleccionarEstudiante(Long idInscripcion, boolean aceptado);
    
// Consulta de Usuario
    List<UsuarioResumen> listarUsuarios();

    DetalleUsuario consultarUsuario(String nickname, String nicknameConsulta);


    // Modificar Datos de Usuario
    UsuarioEdicion buscarUsuarioParaEditar(String nickname);

    void modificarUsuario(
            String nickname,
            String nombre,
            String apellido,
            LocalDate fechaNacimiento,
            String nombreInstituto,
            String rutaImagen
    );
    
    //categorias
    void altaCategoria(String nombre);
    boolean existeCategoria(String nombre);
    List<String> listarNombresCategorias();
    
    //iniciar sesion
    UsuarioResumen iniciarSesion(String nicknameOEmail, String contrasenia);
    
    //listar inscripciones por edicion
    List<InscripcionResumen> listarAceptadosPorEdicion(String nombreEdicion);
    
    //listar resultados inscripciones por estudiante
    List<ResultadoInscripcion> listarResultadosPorEstudiante(String nicknameEstudiante);
    
   //inscripcion programa formacion
    InscripcionPrograma buscarInscripcionPrograma(String nicknameEstudiante, String nombrePrograma);
    void inscribirEstudiantePrograma(String nicknameEstudiante, String nombrePrograma, LocalDate fechaInscripcion);
    
    //seguir y dejar de seguir usuarios
    void seguirUsuario(String nicknameSeguidor, String nicknameSeguido);
    void dejarDeSeguirUsuario(String nicknameSeguidor, String nicknameSeguido);
}