package Logica.controladores;

import Logica.DTO.DetalleCurso;
import Logica.DTO.DetalleEdicionCurso;

import Logica.DTO.DetalleProgramaFormacion;
import Logica.DTO.DetalleUsuario;
import Logica.DTO.UsuarioEdicion;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Docente;
import Logica.Entidades.Estudiante;
import Logica.Entidades.Instituto;
import Logica.Entidades.Curso;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.ProgramaFormacion;

import Persistencia.ManejadorCurso;
import Persistencia.ManejadorEdicionCurso;
import Persistencia.ManejadorInscripcionEdicion;
import Persistencia.ManejadorInscripcionPrograma;
import Persistencia.ManejadorInstituto;
import Persistencia.ManejadorProgramaFormacion;
import Persistencia.ManejadorUsuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Controlador implements IControlador {

    private static Controlador instancia;

    //Fabrica de entityManager utilizada por todos los manejadores
    private final EntityManagerFactory emf;

    //Manejadores;
    private final ManejadorUsuario manejadorUsuario;
    private final ManejadorInstituto manejadorInstituto;
    private final ManejadorCurso manejadorCurso;
    private final ManejadorEdicionCurso manejadorEdicionCurso;
    private final ManejadorProgramaFormacion manejadorProgramaFormacion;
    private final ManejadorInscripcionEdicion manejadorInscripcionEdicion;
    private final ManejadorInscripcionPrograma manejadorInscripcionPrograma;

    // Constructor privado para aplicar Singleton
    private Controlador() {
        //Fabrica de entidades creada una sola vez para toda la app
        emf = Persistence.createEntityManagerFactory("edextPU");

        //Manejadores que comparten la misma EMF
        manejadorUsuario = new ManejadorUsuario(emf);
        manejadorInstituto = new ManejadorInstituto(emf);
        manejadorCurso = new ManejadorCurso(emf);
        manejadorEdicionCurso = new ManejadorEdicionCurso(emf);
        manejadorProgramaFormacion = new ManejadorProgramaFormacion(emf);
        manejadorInscripcionEdicion = new ManejadorInscripcionEdicion(emf);
        manejadorInscripcionPrograma = new ManejadorInscripcionPrograma(emf);

    }

    public static Controlador getInstance() {

        if (instancia == null) {
            instancia = new Controlador();
        }

        return instancia;
    }

    //Aca implementamos los casos de uso
    @Override
    public void altaInstituto(String nombre) {

        Instituto instituto = new Instituto(nombre);

        manejadorInstituto.addInstituto(instituto);

    }

    @Override
    public List<Instituto> listarInstitutos() {
        return manejadorInstituto.listarInstitutos();
    }

    @Override
    public List<Curso> listarCursos() {
        return manejadorCurso.listarCursos();
    }
    
       @Override
    public DetalleCurso consultarCurso(String nombreCurso) {
        return manejadorCurso.buscarDetalleCurso(nombreCurso);
    }

    @Override
    public boolean existeCurso(String nombre) {
        return manejadorCurso.buscarPorNombre(nombre) != null;
    }

    @Override
    public void altaCurso(String nombre, String descripcion, int duracion,
            int cantidadHoras, int creditos, String url,
            LocalDate fechaAlta, Instituto instituto,
            List<Curso> previas) {

        Curso curso = new Curso(nombre, descripcion, duracion, cantidadHoras,
                creditos, url, fechaAlta, instituto);
        curso.setPrevias(previas);
        manejadorCurso.addCurso(curso);
    }

    @Override
    public boolean existeInstituto(String nombre) {
        return manejadorInstituto.buscarPorNombre(nombre) != null;
    }

    @Override
    public List<String> listarNombresInstitutos() {
        List<Instituto> institutos = manejadorInstituto.listarInstitutos();
        List<String> nombres = new ArrayList<>();
        for (Instituto instituto : institutos) {
            nombres.add(instituto.getNombre());
        }
        return nombres;
    }

    @Override
    public boolean existeNickname(String nickname) {
        return manejadorUsuario.existeNickname(nickname);
    }

    @Override
    public boolean existeEmail(String email) {
        return manejadorUsuario.existeEmail(email);
    }

    @Override
    public void altaUsuarioEstudiante(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento) {

        Estudiante estudiante = new Estudiante(nickname, nombre, apellido, email, fechaNacimiento);
        manejadorUsuario.addUsuario(estudiante);
    }

    @Override
    public void altaUsuarioDocente(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento, String nombreInstituto) {

        Instituto instituto = manejadorInstituto.buscarInstituto(nombreInstituto);
        Docente docente = new Docente(nickname, nombre, apellido, email, fechaNacimiento, instituto);
        manejadorUsuario.addUsuario(docente);
    }

    @Override
    public boolean existePrograma(String nombre){
        return manejadorProgramaFormacion.existePrograma(nombre);
    }

    @Override
    public void altaPrograma(String nombre, String descripcion,
            LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta){

        ProgramaFormacion programa = new ProgramaFormacion(
                nombre,
                descripcion,
                fechaInicio,
                fechaFin,
                fechaAlta
        );

        manejadorProgramaFormacion.addPrograma(programa);
    }

    @Override
    public List<String> listarProgramas(){
        List<ProgramaFormacion> programas = manejadorProgramaFormacion.listarProgramas();
        List<String> nombres = new ArrayList<>();

        for (ProgramaFormacion programa : programas) {
            nombres.add(programa.getNombre());
        }

        return nombres;
    }

    @Override
    public void agregarCursoAPrograma(String nombrePrograma, String nombreCurso){
        manejadorProgramaFormacion.agregarCursoAPrograma(nombrePrograma, nombreCurso);
    }
    
    @Override
    public DetalleProgramaFormacion consultarPrograma(String nombre){
        return manejadorProgramaFormacion.buscarDetallePrograma(nombre);
    }



    // =========================
    // EDICIONES
    // =========================

    @Override
    public List<Curso> listarCursosPorInstituto(String nombreInstituto) {
        return manejadorCurso.listarCursosPorInstituto(nombreInstituto);
    }

    @Override
    public List<Docente> listarDocentesPorInstituto(String nombreInstituto) {
        return manejadorUsuario.listarDocentesPorInstituto(nombreInstituto);
    }

    @Override
    public boolean existeEdicion(String nombre) {
        return manejadorEdicionCurso.buscarPorNombre(nombre) != null;
    }

    @Override
    public void altaEdicionCurso(
            String nombre,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer cupo,
            String nombreCurso,
            List<Docente> docentes) {

        Curso curso = manejadorCurso.buscarPorNombre(nombreCurso);

        LocalDate fechaPublicacion = LocalDate.now();

        EdicionCurso edicion = new EdicionCurso(
                nombre,
                fechaInicio,
                fechaFin,
                cupo,
                fechaPublicacion,
                curso
        );

        edicion.setDocentes(docentes);

        manejadorEdicionCurso.addEdicion(edicion);
    }

    @Override
    public List<EdicionCurso> listarEdicionesPorCurso(String nombreCurso) {
        return manejadorEdicionCurso.listarEdicionesPorCurso(nombreCurso);
    }

    @Override
    public EdicionCurso buscarEdicion(String nombre) {
        return manejadorEdicionCurso.buscarEdicionCompleta(nombre);
    }

  @Override
    public DetalleEdicionCurso consultarEdicion(String nombreEdicion) {
        return manejadorEdicionCurso.buscarDetalleEdicion(nombreEdicion);
    }
    
    @Override
    public EdicionCurso buscarEdicionVigentePorCurso(String nombreCurso) {
    return manejadorEdicionCurso.buscarEdicionVigentePorCurso(nombreCurso);
    }
    
    @Override
    public List<Estudiante> listarEstudiantes() {
    return manejadorUsuario.listarEstudiantes();
    }

    // =========================
    // CONSULTA DE USUARIO
    // =========================

    @Override
    public List<UsuarioResumen> listarUsuarios() {
        return manejadorUsuario.listarUsuarios();
    }

    @Override
    public DetalleUsuario consultarUsuario(String nickname) {
        return manejadorUsuario.buscarDetalleUsuario(nickname);
    }
    
    @Override
    public InscripcionEdicion buscarInscripcionEdicion (String nicknameEstudiante,
        String nombreEdicion) {
        
        return manejadorInscripcionEdicion.buscarInscripcion(nicknameEstudiante,nombreEdicion);
}
    @Override
public void inscribirEstudianteEdicion(
        String nicknameEstudiante,
        String nombreEdicion,
        LocalDate fechaInscripcion) {

    Estudiante estudiante =
            manejadorUsuario.buscarEstudiante(nicknameEstudiante);

    EdicionCurso edicion =
            manejadorEdicionCurso.buscarPorNombre(nombreEdicion);

    InscripcionEdicion inscripcion =
            new InscripcionEdicion(
                    fechaInscripcion,
                    estudiante,
                    edicion
            );

    manejadorInscripcionEdicion.addInscripcion(inscripcion);
}
    @Override
public void modificarInscripcionEdicion(Long idInscripcion, LocalDate nuevaFecha) {

    manejadorInscripcionEdicion.modificarInscripcion(idInscripcion,nuevaFecha);
}

    // =========================
    // MODIFICAR USUARIO
    // =========================

    @Override
    public UsuarioEdicion buscarUsuarioParaEditar(String nickname) {
        return manejadorUsuario.buscarUsuarioParaEditar(nickname);
    }

    @Override
    public void modificarUsuario(String nickname, String nombre, String apellido,
            LocalDate fechaNacimiento, String nombreInstituto) {

        manejadorUsuario.actualizarUsuario(
                nickname,
                nombre,
                apellido,
                fechaNacimiento,
                nombreInstituto
        );
    }
}