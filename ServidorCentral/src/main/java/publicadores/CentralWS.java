package publicadores;

import java.util.List;
import Logica.DTO.CursoResumen;
import Logica.DTO.UsuarioResumen;
import publicadores.dto.*;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.Instituto;
import jakarta.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.LinkedHashMap;
import java.util.Map;

@WebService(endpointInterface = "publicadores.ICentralWS",
        serviceName = "CentralService", portName = "CentralPort",
        targetNamespace = "http://ws.edext.utec.edu.uy/")
public class CentralWS implements ICentralWS {

    private final IControlador control = Fabrica.getInstance().getIControlador();
    private static final Logger LOGGER = Logger.getLogger(CentralWS.class.getName());

    @Override
    public void agregarCursoAPrograma(
            String nombrePrograma,
            String nombreCurso) throws CentralException {
        try {
            control.agregarCursoAPrograma(nombrePrograma, nombreCurso);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló agregarCursoAPrograma", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void altaCurso(
            String nombre,
            String descripcion,
            int duracion,
            int cantidadHoras,
            int creditos,
            String url,
            String fechaAlta,
            String nombreInstituto,
            List<String> nombresPrevias,
            List<String> nombresCategorias,
            String rutaImagen) throws CentralException {
        try {
            control.altaCurso(nombre, descripcion, duracion, cantidadHoras, creditos, url, fecha(fechaAlta), instituto(nombreInstituto), cursos(nombresPrevias), nombresCategorias, rutaImagen);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló altaCurso", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void altaEdicionCurso(
            String nombre,
            String fechaInicio,
            String fechaFin,
            Integer cupo,
            String nombreCurso,
            List<String> nicknamesDocentes,
            String rutaImagen) throws CentralException {
        try {
            control.altaEdicionCurso(nombre, fecha(fechaInicio), fecha(fechaFin), cupo, nombreCurso, docentes(nombreCurso, nicknamesDocentes), rutaImagen);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló altaEdicionCurso", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void altaPrograma(
            String nombre,
            String descripcion,
            String fechaInicio,
            String fechaFin,
            String fechaAlta,
            String rutaImagen,
            String nicknameDocente) throws CentralException {
        try {
            control.altaPrograma(nombre, descripcion, fecha(fechaInicio), fecha(fechaFin), fecha(fechaAlta), rutaImagen, nicknameDocente);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló altaPrograma", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void altaUsuarioDocente(
            String nickname,
            String nombre,
            String apellido,
            String email,
            String contrasenia,
            String fechaNacimiento,
            String nombreInstituto,
            String rutaImagen) throws CentralException {
        try {
            control.altaUsuarioDocente(nickname, nombre, apellido, email, contrasenia, fecha(fechaNacimiento), nombreInstituto, rutaImagen);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló altaUsuarioDocente", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void altaUsuarioEstudiante(
            String nickname,
            String nombre,
            String apellido,
            String email,
            String contrasenia,
            String fechaNacimiento,
            String rutaImagen) throws CentralException {
        try {
            control.altaUsuarioEstudiante(nickname, nombre, apellido, email, contrasenia, fecha(fechaNacimiento), rutaImagen);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló altaUsuarioEstudiante", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public EdicionCursoDTO buscarEdicion(
            String nombre) {
        return MapeadorSOAP.edicion(control.buscarEdicion(nombre), true);
    }

    @Override
    public InscripcionEdicionDTO buscarInscripcionEdicion(
            String nicknameEstudiante,
            String nombreEdicion) {
        return MapeadorSOAP.inscripcionEdicion(control.buscarInscripcionEdicion(nicknameEstudiante, nombreEdicion), nicknameEstudiante, nombreEdicion);
    }

    @Override
    public InscripcionProgramaDTO buscarInscripcionPrograma(
            String nicknameEstudiante,
            String nombrePrograma) {
        return MapeadorSOAP.inscripcionPrograma(control.buscarInscripcionPrograma(nicknameEstudiante, nombrePrograma), nicknameEstudiante, nombrePrograma);
    }

    @Override
    public UsuarioEdicionDTO buscarUsuarioParaEditar(
            String nickname) {
        return MapeadorSOAP.convertir(control.buscarUsuarioParaEditar(nickname));
    }

    @Override
    public boolean cambiarContrasenia(
            String nickname,
            String contraseniaActual,
            String contraseniaNueva) throws CentralException {
        try {
            return control.cambiarContrasenia(nickname, contraseniaActual, contraseniaNueva);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló cambiarContrasenia", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public DetalleCursoDTO consultarCurso(
            String nombreCurso) {
        return MapeadorSOAP.convertir(control.consultarCurso(nombreCurso));
    }

    @Override
    public DetalleEdicionCursoDTO consultarEdicion(
            String nombreEdicion) {
        return MapeadorSOAP.convertir(control.consultarEdicion(nombreEdicion));
    }

    @Override
    public DetalleProgramaFormacionDTO consultarPrograma(
            String nombre) {
        return MapeadorSOAP.convertir(control.consultarPrograma(nombre));
    }

    @Override
    public DetalleUsuarioDTO consultarUsuario(
            String nickname,
            String nicknameConsulta) {
        return MapeadorSOAP.convertir(control.consultarUsuario(nickname, nicknameConsulta));
    }

    @Override
    public void dejarDeSeguirUsuario(
            String nicknameSeguidor,
            String nicknameSeguido) throws CentralException {
        try {
            control.dejarDeSeguirUsuario(nicknameSeguidor, nicknameSeguido);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló dejarDeSeguirUsuario", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public boolean existeCurso(
            String nombre) {
        return control.existeCurso(nombre);
    }

    @Override
    public boolean existeEdicion(
            String nombre) {
        return control.existeEdicion(nombre);
    }

    @Override
    public boolean existeEmail(
            String email) {
        return control.existeEmail(email);
    }

    @Override
    public boolean existeInstituto(
            String nombre) {
        return control.existeInstituto(nombre);
    }

    @Override
    public boolean existeNickname(
            String nickname) {
        return control.existeNickname(nickname);
    }

    @Override
    public boolean existePrograma(
            String nombre) {
        return control.existePrograma(nombre);
    }

    @Override
    public UsuarioResumen iniciarSesion(
            String nicknameOEmail,
            String contrasenia) {
        return control.iniciarSesion(nicknameOEmail, contrasenia);
    }

    @Override
    public void inscribirEstudianteEdicion(
            String nicknameEstudiante,
            String nombreEdicion,
            String fechaInscripcion,
            String videoUrl) throws CentralException {
        try {
            control.inscribirEstudianteEdicion(nicknameEstudiante, nombreEdicion, fecha(fechaInscripcion), videoUrl);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló inscribirEstudianteEdicion", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void inscribirEstudiantePrograma(
            String nicknameEstudiante,
            String nombrePrograma,
            String fechaInscripcion) throws CentralException {
        try {
            control.inscribirEstudiantePrograma(nicknameEstudiante, nombrePrograma, fecha(fechaInscripcion));
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló inscribirEstudiantePrograma", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public List<InscripcionResumenDTO> listarAceptadosPorEdicion(
            String nombreEdicion) {
        return MapeadorSOAP.lista(control.listarAceptadosPorEdicion(nombreEdicion), MapeadorSOAP::convertir);
    }

    @Override
    public List<CursoDTO> listarCursos() {
        return MapeadorSOAP.lista(control.listarCursos(), MapeadorSOAP::convertir);
    }

    @Override
    public List<CursoResumen> listarCursosPorInstituto(
            String nombreInstituto) {
        return MapeadorSOAP.lista(control.listarCursosPorInstituto(nombreInstituto), c -> new CursoResumen(c.getNombre(), nombreInstituto));
    }

    @Override
    public List<UsuarioResumen> listarDocentesPorInstituto(
            String nombreInstituto) {
        return MapeadorSOAP.lista(control.listarDocentesPorInstituto(nombreInstituto), d -> new UsuarioResumen(d.getNickname(), d.getNombre() + " " + d.getApellido(), "Docente", d.getRutaImagen()));
    }

    @Override
    public List<EdicionCursoDTO> listarEdicionesPorCurso(
            String nombreCurso) {
        return MapeadorSOAP.lista(control.listarEdicionesPorCurso(nombreCurso), e -> MapeadorSOAP.edicion(e, false));
    }

    @Override
    public List<EdicionCursoDTO> listarEdicionesVigentesPorCurso(
            String nombreCurso) {
        return MapeadorSOAP.lista(control.listarEdicionesVigentesPorCurso(nombreCurso), e -> MapeadorSOAP.edicion(e, false));
    }

    @Override
    public List<InscripcionResumenDTO> listarInscripcionesPorEdicion(
            String nombreEdicion,
            boolean ordenarPorPrioridad) {
        return MapeadorSOAP.lista(control.listarInscripcionesPorEdicion(nombreEdicion, ordenarPorPrioridad), MapeadorSOAP::convertir);
    }

    @Override
    public List<InstitutoDTO> listarInstitutos() {
        return MapeadorSOAP.lista(control.listarInstitutos(), MapeadorSOAP::convertir);
    }

    @Override
    public List<String> listarNombresCategorias() {
        return control.listarNombresCategorias();
    }

    @Override
    public List<String> listarNombresInstitutos() {
        return control.listarNombresInstitutos();
    }

    @Override
    public List<String> listarProgramas() {
        return control.listarProgramas();
    }

    @Override
    public List<ElementoResumenDTO> listarProgramasCreados(
            String nicknameDocente) {
        return MapeadorSOAP.lista(control.listarProgramasCreados(nicknameDocente), MapeadorSOAP::convertir);
    }

    @Override
    public List<ResultadoInscripcionDTO> listarResultadosPorEstudiante(
            String nicknameEstudiante) {
        return MapeadorSOAP.lista(control.listarResultadosPorEstudiante(nicknameEstudiante), MapeadorSOAP::convertir);
    }

    @Override
    public List<UsuarioResumen> listarSeguidores(
            String nickname) {
        return control.listarSeguidores(nickname);
    }

    @Override
    public List<UsuarioResumen> listarSeguidos(
            String nickname) {
        return control.listarSeguidos(nickname);
    }

    @Override
    public List<UsuarioResumen> listarUsuarios() {
        return control.listarUsuarios();
    }

    @Override
    public void modificarInscripcionEdicion(
            Long idInscripcion,
            String nuevaFecha,
            String videoUrl) throws CentralException {
        try {
            control.modificarInscripcionEdicion(idInscripcion, fecha(nuevaFecha), videoUrl);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló modificarInscripcionEdicion", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void modificarUsuario(
            String nickname,
            String nombre,
            String apellido,
            String fechaNacimiento,
            String nombreInstituto,
            String rutaImagen) throws CentralException {
        try {
            control.modificarUsuario(nickname, nombre, apellido, fecha(fechaNacimiento), nombreInstituto, rutaImagen);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló modificarUsuario", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void seguirUsuario(
            String nicknameSeguidor,
            String nicknameSeguido) throws CentralException {
        try {
            control.seguirUsuario(nicknameSeguidor, nicknameSeguido);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló seguirUsuario", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void seleccionarEstudiantesEdicion(
            String nicknameDocente,
            String nombreEdicion,
            List<Long> aceptadas,
            List<Long> rechazadas) throws CentralException {
        try {
            control.seleccionarEstudiantesEdicion(nicknameDocente, nombreEdicion, aceptadas, rechazadas);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló seleccionarEstudiantesEdicion", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public boolean sigueAUsuario(
            String nicknameSeguidor,
            String nicknameSeguido) {
        return control.sigueAUsuario(nicknameSeguidor, nicknameSeguido);
    }

    private static LocalDate fecha(String valor) {
        return valor == null ? null : LocalDate.parse(valor);
    }

    private Instituto instituto(String nombre) {
        for (Instituto instituto : control.listarInstitutos()) {
            if (instituto.getNombre().equals(nombre)) {
                return instituto;
            }
        }
        throw new IllegalArgumentException("No se encontró el instituto: " + nombre);
    }

    private Curso curso(String nombre) {
        for (Curso curso : control.listarCursos()) {
            if (curso.getNombre().equals(nombre)) {
                return curso;
            }
        }
        throw new IllegalArgumentException("No se encontró el curso: " + nombre);
    }

    private List<Curso> cursos(List<String> nombres) {
        List<Curso> resultado = new ArrayList<>();
        if (nombres != null) {
            for (String nombre : nombres) {
                resultado.add(curso(nombre));
            }
        }
        return resultado;
    }

    private List<Docente> docentes(String nombreCurso, List<String> nicknames) {
        String instituto = curso(nombreCurso).getInstituto().getNombre();
        List<Docente> disponibles = control.listarDocentesPorInstituto(instituto);
        List<Docente> resultado = new ArrayList<>();
        if (nicknames != null) {
            for (String nickname : nicknames) {
                Docente encontrado = disponibles.stream()
                        .filter(d -> d.getNickname().equals(nickname))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException(
                                "No se encontró el docente: " + nickname));
                resultado.add(encontrado);
            }
        }
        return resultado;
    }

    @Override
    public void desistirInscripcionEdicion(
            String nicknameEstudiante,
            String nombreEdicion) throws CentralException {

        try {
            control.desistirInscripcionEdicion(
                    nicknameEstudiante,
                    nombreEdicion);

        } catch (RuntimeException e) {
            LOGGER.log(
                    Level.WARNING,
                    "Falló desistirInscripcionEdicion",
                    e);

            throw new CentralException(
                    e.getMessage() == null
                    ? "No se pudo completar el desistimiento."
                    : e.getMessage());
        }
    }
    
    @Override
    public List<EvaluacionEstudianteDTO> listarEvaluacionesPorEdicion(
            String nombreEdicion) {
        return MapeadorSOAP.lista(
                control.listarEvaluacionesPorEdicion(nombreEdicion),
                MapeadorSOAP::convertir);
    }

    @Override
    public void guardarNotas(
            String nicknameDocente,
            String nombreEdicion,
            List<EvaluacionEstudianteDTO> notas) throws CentralException {
        try {
            Map<Long, Integer> mapa = new LinkedHashMap<>();
            if (notas != null) {
                for (EvaluacionEstudianteDTO dto : notas) {
                    if (dto == null || dto.getIdInscripcion() == null) {
                        throw new IllegalArgumentException(
                                "Los datos de las notas son inválidos.");
                    }
                    mapa.put(dto.getIdInscripcion(), dto.getNota());
                }
            }
            control.guardarNotas(nicknameDocente, nombreEdicion, mapa);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló guardarNotas", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void cerrarEdicion(
            String nicknameDocente,
            String nombreEdicion) throws CentralException {
        try {
            control.cerrarEdicion(nicknameDocente, nombreEdicion);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló cerrarEdicion", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public boolean edicionCerrada(String nombreEdicion)
            throws CentralException {
        try {
            return control.edicionCerrada(nombreEdicion);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló edicionCerrada", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public List<ResultadoEvaluacionDTO> listarResultadosEvaluacionesPorEstudiante(
            String nicknameEstudiante) throws CentralException {
        try {
            return MapeadorSOAP.lista(
                    control.listarResultadosEvaluacionesPorEstudiante(nicknameEstudiante),
                    MapeadorSOAP::convertir);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló listarResultadosEvaluacionesPorEstudiante", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public List<String> listarCursosConParticipacion(String nicknameEstudiante)
            throws CentralException {
        try {
            return control.listarCursosConParticipacion(nicknameEstudiante);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló listarCursosConParticipacion", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public ValoracionCursoDTO consultarValoracionCurso(
            String nicknameEstudiante, String nombreCurso) throws CentralException {
        try {
            return MapeadorSOAP.convertir(
                    control.consultarValoracionCurso(nicknameEstudiante, nombreCurso));
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló consultarValoracionCurso", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }

    @Override
    public void valorarCurso(String nicknameEstudiante, String nombreCurso, int puntaje)
            throws CentralException {
        try {
            control.valorarCurso(nicknameEstudiante, nombreCurso, puntaje);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Falló valorarCurso", e);
            throw new CentralException(e.getMessage() == null
                    ? "No se pudo completar la operación." : e.getMessage());
        }
    }
}
