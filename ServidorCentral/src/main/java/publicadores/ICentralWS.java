package publicadores;

import java.util.List;
import Logica.DTO.CursoResumen;
import Logica.DTO.UsuarioResumen;
import publicadores.dto.*;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;

@WebService(name = "ICentralWS", targetNamespace = "http://ws.edext.utec.edu.uy/")
public interface ICentralWS {

    @WebMethod
    void agregarCursoAPrograma(
            @WebParam(name = "nombrePrograma") String nombrePrograma,
            @WebParam(name = "nombreCurso") String nombreCurso) throws CentralException;

    @WebMethod
    void altaCurso(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "duracion") int duracion,
            @WebParam(name = "cantidadHoras") int cantidadHoras,
            @WebParam(name = "creditos") int creditos,
            @WebParam(name = "url") String url,
            @WebParam(name = "fechaAlta") String fechaAlta,
            @WebParam(name = "nombreInstituto") String nombreInstituto,
            @WebParam(name = "nombresPrevias") List<String> nombresPrevias,
            @WebParam(name = "nombresCategorias") List<String> nombresCategorias,
            @WebParam(name = "rutaImagen") String rutaImagen) throws CentralException;

    @WebMethod
    void altaEdicionCurso(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "fechaInicio") String fechaInicio,
            @WebParam(name = "fechaFin") String fechaFin,
            @WebParam(name = "cupo") Integer cupo,
            @WebParam(name = "nombreCurso") String nombreCurso,
            @WebParam(name = "nicknamesDocentes") List<String> nicknamesDocentes,
            @WebParam(name = "rutaImagen") String rutaImagen) throws CentralException;

    @WebMethod
    void altaPrograma(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "fechaInicio") String fechaInicio,
            @WebParam(name = "fechaFin") String fechaFin,
            @WebParam(name = "fechaAlta") String fechaAlta,
            @WebParam(name = "rutaImagen") String rutaImagen,
            @WebParam(name = "nicknameDocente") String nicknameDocente) throws CentralException;

    @WebMethod
    void altaUsuarioDocente(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apellido") String apellido,
            @WebParam(name = "email") String email,
            @WebParam(name = "contrasenia") String contrasenia,
            @WebParam(name = "fechaNacimiento") String fechaNacimiento,
            @WebParam(name = "nombreInstituto") String nombreInstituto,
            @WebParam(name = "rutaImagen") String rutaImagen) throws CentralException;

    @WebMethod
    void altaUsuarioEstudiante(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apellido") String apellido,
            @WebParam(name = "email") String email,
            @WebParam(name = "contrasenia") String contrasenia,
            @WebParam(name = "fechaNacimiento") String fechaNacimiento,
            @WebParam(name = "rutaImagen") String rutaImagen) throws CentralException;

    @WebMethod
    EdicionCursoDTO buscarEdicion(
            @WebParam(name = "nombre") String nombre);

    @WebMethod
    InscripcionEdicionDTO buscarInscripcionEdicion(
            @WebParam(name = "nicknameEstudiante") String nicknameEstudiante,
            @WebParam(name = "nombreEdicion") String nombreEdicion);

    @WebMethod
    InscripcionProgramaDTO buscarInscripcionPrograma(
            @WebParam(name = "nicknameEstudiante") String nicknameEstudiante,
            @WebParam(name = "nombrePrograma") String nombrePrograma);

    @WebMethod
    UsuarioEdicionDTO buscarUsuarioParaEditar(
            @WebParam(name = "nickname") String nickname);

    @WebMethod
    boolean cambiarContrasenia(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "contraseniaActual") String contraseniaActual,
            @WebParam(name = "contraseniaNueva") String contraseniaNueva) throws CentralException;

    @WebMethod
    DetalleCursoDTO consultarCurso(
            @WebParam(name = "nombreCurso") String nombreCurso);

    @WebMethod
    DetalleEdicionCursoDTO consultarEdicion(
            @WebParam(name = "nombreEdicion") String nombreEdicion);

    @WebMethod
    DetalleProgramaFormacionDTO consultarPrograma(
            @WebParam(name = "nombre") String nombre);

    @WebMethod
    DetalleUsuarioDTO consultarUsuario(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "nicknameConsulta") String nicknameConsulta);

    @WebMethod
    void dejarDeSeguirUsuario(
            @WebParam(name = "nicknameSeguidor") String nicknameSeguidor,
            @WebParam(name = "nicknameSeguido") String nicknameSeguido) throws CentralException;

    @WebMethod
    boolean existeCurso(
            @WebParam(name = "nombre") String nombre);

    @WebMethod
    @WebResult(name = "existe")
    boolean existeEdicion(
            @WebParam(name = "nombre") String nombre);

    @WebMethod
    boolean existeEmail(
            @WebParam(name = "email") String email);

    @WebMethod
    boolean existeInstituto(
            @WebParam(name = "nombre") String nombre);

    @WebMethod
    boolean existeNickname(
            @WebParam(name = "nickname") String nickname);

    @WebMethod
    boolean existePrograma(
            @WebParam(name = "nombre") String nombre);

    @WebMethod
    UsuarioResumen iniciarSesion(
            @WebParam(name = "nicknameOEmail") String nicknameOEmail,
            @WebParam(name = "contrasenia") String contrasenia);

    @WebMethod
    void inscribirEstudianteEdicion(
            @WebParam(name = "nicknameEstudiante") String nicknameEstudiante,
            @WebParam(name = "nombreEdicion") String nombreEdicion,
            @WebParam(name = "fechaInscripcion") String fechaInscripcion,
            @WebParam(name = "videoUrl") String videoUrl) throws CentralException;

    @WebMethod
    void inscribirEstudiantePrograma(
            @WebParam(name = "nicknameEstudiante") String nicknameEstudiante,
            @WebParam(name = "nombrePrograma") String nombrePrograma,
            @WebParam(name = "fechaInscripcion") String fechaInscripcion) throws CentralException;

    @WebMethod
    List<InscripcionResumenDTO> listarAceptadosPorEdicion(
            @WebParam(name = "nombreEdicion") String nombreEdicion);

    @WebMethod
    List<CursoDTO> listarCursos();

    @WebMethod
    @WebResult(name = "curso")
    List<CursoResumen> listarCursosPorInstituto(
            @WebParam(name = "instituto") String nombreInstituto);

    @WebMethod
    @WebResult(name = "docente")
    List<UsuarioResumen> listarDocentesPorInstituto(
            @WebParam(name = "instituto") String nombreInstituto);

    @WebMethod
    List<EdicionCursoDTO> listarEdicionesPorCurso(
            @WebParam(name = "nombreCurso") String nombreCurso);

    @WebMethod
    List<EdicionCursoDTO> listarEdicionesVigentesPorCurso(
            @WebParam(name = "nombreCurso") String nombreCurso);

    @WebMethod
    List<InscripcionResumenDTO> listarInscripcionesPorEdicion(
            @WebParam(name = "nombreEdicion") String nombreEdicion,
            @WebParam(name = "ordenarPorPrioridad") boolean ordenarPorPrioridad);

    @WebMethod
    List<InstitutoDTO> listarInstitutos();

    @WebMethod
    List<String> listarNombresCategorias();

    @WebMethod
    @WebResult(name = "instituto")
    List<String> listarNombresInstitutos();

    @WebMethod
    List<String> listarProgramas();

    @WebMethod
    List<ElementoResumenDTO> listarProgramasCreados(
            @WebParam(name = "nicknameDocente") String nicknameDocente);

    @WebMethod
    List<ResultadoInscripcionDTO> listarResultadosPorEstudiante(
            @WebParam(name = "nicknameEstudiante") String nicknameEstudiante);

    @WebMethod
    List<UsuarioResumen> listarSeguidores(
            @WebParam(name = "nickname") String nickname);

    @WebMethod
    List<UsuarioResumen> listarSeguidos(
            @WebParam(name = "nickname") String nickname);

    @WebMethod
    List<UsuarioResumen> listarUsuarios();

    @WebMethod
    void modificarInscripcionEdicion(
            @WebParam(name = "idInscripcion") Long idInscripcion,
            @WebParam(name = "nuevaFecha") String nuevaFecha,
            @WebParam(name = "videoUrl") String videoUrl) throws CentralException;

    @WebMethod
    void modificarUsuario(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apellido") String apellido,
            @WebParam(name = "fechaNacimiento") String fechaNacimiento,
            @WebParam(name = "nombreInstituto") String nombreInstituto,
            @WebParam(name = "rutaImagen") String rutaImagen) throws CentralException;

    @WebMethod
    void seguirUsuario(
            @WebParam(name = "nicknameSeguidor") String nicknameSeguidor,
            @WebParam(name = "nicknameSeguido") String nicknameSeguido) throws CentralException;

    @WebMethod
    void seleccionarEstudiantesEdicion(
            @WebParam(name = "nicknameDocente") String nicknameDocente,
            @WebParam(name = "nombreEdicion") String nombreEdicion,
            @WebParam(name = "aceptadas") List<Long> aceptadas,
            @WebParam(name = "rechazadas") List<Long> rechazadas) throws CentralException;

    @WebMethod
    boolean sigueAUsuario(
            @WebParam(name = "nicknameSeguidor") String nicknameSeguidor,
            @WebParam(name = "nicknameSeguido") String nicknameSeguido);

    @WebMethod
    void desistirInscripcionEdicion(
            @WebParam(name = "nicknameEstudiante") String nicknameEstudiante,
            @WebParam(name = "nombreEdicion") String nombreEdicion) throws CentralException;
    

    @WebMethod
    List<EvaluacionEstudianteDTO> listarEvaluacionesPorEdicion(
            @WebParam(name = "nombreEdicion") String nombreEdicion);

    @WebMethod
    void guardarNotas(
            @WebParam(name = "nicknameDocente") String nicknameDocente,
            @WebParam(name = "nombreEdicion") String nombreEdicion,
            @WebParam(name = "notas") List<EvaluacionEstudianteDTO> notas)
            throws CentralException;

    @WebMethod
    void cerrarEdicion(
            @WebParam(name = "nicknameDocente") String nicknameDocente,
            @WebParam(name = "nombreEdicion") String nombreEdicion)
            throws CentralException;

    @WebMethod
    boolean edicionCerrada(
            @WebParam(name = "nombreEdicion") String nombreEdicion)
            throws CentralException;
}
