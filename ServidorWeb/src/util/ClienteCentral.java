package util;

import cliente.central.CentralException_Exception;
import cliente.central.CentralService;
import cliente.central.ICentralWS;
import jakarta.xml.ws.BindingProvider;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletContext;
import modelo.*;

//Adaptador remoto utilizado por los servlets. No depende de ServidorCentral.jar. */
public final class ClienteCentral {

    private final String direccion;
    private final CentralService servicio;

    public ClienteCentral(ServletContext contexto) {
        String configurada
                = ConfiguracionWeb.obtener("central.ws.url");
        if (configurada == null || configurada.isBlank()) {
            throw new IllegalStateException("Falta configurar central.ws.url en .edEXT/web.properties.");
        }
        direccion = configurada.trim();
        URI uri = URI.create(direccion);
        if (!uri.isAbsolute() || uri.getHost() == null
                || !("http".equalsIgnoreCase(uri.getScheme())
                || "https".equalsIgnoreCase(uri.getScheme()))) {
            throw new IllegalStateException("central.ws.url debe ser una dirección HTTP o HTTPS válida.");
        }
        URL contrato = ClienteCentral.class.getResource("/wsdl/central.wsdl");
        if (contrato == null) {
            throw new IllegalStateException("No se encontró /wsdl/central.wsdl en los recursos de Web.");
        }
        servicio = new CentralService(contrato);
    }

    private synchronized ICentralWS crearPuerto() {
        ICentralWS puerto = servicio.getCentralPort();
        ((BindingProvider) puerto).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, direccion);
        return puerto;
    }

    private static String fecha(LocalDate valor) {
        return valor == null ? null : valor.toString();
    }

    @FunctionalInterface
    private interface Escritura<T> {

        T ejecutar(ICentralWS puerto) throws CentralException_Exception;
    }

    private <T> T escribir(Escritura<T> operacion) {
        try {
            return operacion.ejecutar(crearPuerto());
        } catch (CentralException_Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public void seleccionarEstudiantesEdicion(
            String nicknameDocente,
            String nombreEdicion,
            List<Long> aceptadas,
            List<Long> rechazadas) {
        escribir(puerto -> {
            puerto.seleccionarEstudiantesEdicion(nicknameDocente, nombreEdicion, aceptadas, rechazadas);
            return null;
        });
    }

    public List<ResultadoInscripcion> listarResultadosPorEstudiante(
            String nicknameEstudiante) {
        return MapeadorCliente.lista(crearPuerto().listarResultadosPorEstudiante(nicknameEstudiante), MapeadorCliente::convertir);
    }

    public List<EdicionCurso> listarEdicionesVigentesPorCurso(
            String nombreCurso) {
        List<EdicionCurso> resultado = MapeadorCliente.lista(crearPuerto().listarEdicionesVigentesPorCurso(nombreCurso), MapeadorCliente::convertir);
        for (EdicionCurso edicion : resultado) {
            edicion.setNombreCurso(nombreCurso);
        }
        return resultado;
    }

    public List<InscripcionResumen> listarInscripcionesPorEdicion(
            String nombreEdicion,
            boolean ordenarPorPrioridad) {
        return MapeadorCliente.lista(crearPuerto().listarInscripcionesPorEdicion(nombreEdicion, ordenarPorPrioridad), MapeadorCliente::convertir);
    }

    public void altaCurso(
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
            String rutaImagen) {
        escribir(puerto -> {
            puerto.altaCurso(nombre, descripcion, duracion, cantidadHoras, creditos, url, fecha(fechaAlta), instituto == null ? null : instituto.getNombre(), previas == null ? null : previas.stream().map(Curso::getNombre).toList(), nombresCategorias, rutaImagen);
            return null;
        });
    }

    public List<ElementoResumen> listarProgramasCreados(
            String nicknameDocente) {
        return MapeadorCliente.lista(crearPuerto().listarProgramasCreados(nicknameDocente), MapeadorCliente::convertir);
    }

    public List<Instituto> listarInstitutos() {
        return MapeadorCliente.lista(crearPuerto().listarInstitutos(), MapeadorCliente::convertir);
    }

    public List<Curso> listarCursos() {
        return MapeadorCliente.lista(crearPuerto().listarCursos(), MapeadorCliente::convertir);
    }

    public boolean existeNickname(
            String nickname) {
        return crearPuerto().existeNickname(nickname);
    }

    public boolean existeEmail(
            String email) {
        return crearPuerto().existeEmail(email);
    }

    public boolean existePrograma(
            String nombre) {
        return crearPuerto().existePrograma(nombre);
    }

    public List<String> listarProgramas() {
        return crearPuerto().listarProgramas();
    }

    public EdicionCurso buscarEdicion(
            String nombre) {
        return MapeadorCliente.convertir(crearPuerto().buscarEdicion(nombre));
    }

    public List<EdicionCurso> listarEdicionesPorCurso(
            String nombreCurso) {
        List<EdicionCurso> resultado = MapeadorCliente.lista(crearPuerto().listarEdicionesPorCurso(nombreCurso), MapeadorCliente::convertir);
        for (EdicionCurso edicion : resultado) {
            edicion.setNombreCurso(nombreCurso);
        }
        return resultado;
    }

    public void seguirUsuario(
            String nicknameSeguidor,
            String nicknameSeguido) {
        escribir(puerto -> {
            puerto.seguirUsuario(nicknameSeguidor, nicknameSeguido);
            return null;
        });
    }

    public DetalleUsuario consultarUsuario(
            String nickname,
            String nicknameConsulta) {
        return MapeadorCliente.convertir(crearPuerto().consultarUsuario(nickname, nicknameConsulta));
    }

    public DetalleEdicionCurso consultarEdicion(
            String nombreEdicion) {
        return MapeadorCliente.convertir(crearPuerto().consultarEdicion(nombreEdicion));
    }

    public List<UsuarioResumen> listarSeguidos(
            String nickname) {
        return MapeadorCliente.lista(crearPuerto().listarSeguidos(nickname), MapeadorCliente::convertir);
    }

    public UsuarioEdicion buscarUsuarioParaEditar(
            String nickname) {
        return MapeadorCliente.convertir(crearPuerto().buscarUsuarioParaEditar(nickname));
    }

    public List<UsuarioResumen> listarUsuarios() {
        return MapeadorCliente.lista(crearPuerto().listarUsuarios(), MapeadorCliente::convertir);
    }

    public boolean existeInstituto(
            String nombre) {
        return crearPuerto().existeInstituto(nombre);
    }

    public List<String> listarNombresInstitutos() {
        return crearPuerto().listarNombresInstitutos();
    }

    public void altaUsuarioDocente(
            String nickname,
            String nombre,
            String apellido,
            String email,
            String contrasenia,
            LocalDate fechaNacimiento,
            String nombreInstituto,
            String rutaImagen) {
        escribir(puerto -> {
            puerto.altaUsuarioDocente(nickname, nombre, apellido, email, contrasenia, fecha(fechaNacimiento), nombreInstituto, rutaImagen);
            return null;
        });
    }

    public void altaUsuarioEstudiante(
            String nickname,
            String nombre,
            String apellido,
            String email,
            String contrasenia,
            LocalDate fechaNacimiento,
            String rutaImagen) {
        escribir(puerto -> {
            puerto.altaUsuarioEstudiante(nickname, nombre, apellido, email, contrasenia, fecha(fechaNacimiento), rutaImagen);
            return null;
        });
    }

    public DetalleProgramaFormacion consultarPrograma(
            String nombre) {
        return MapeadorCliente.convertir(crearPuerto().consultarPrograma(nombre));
    }

    public void agregarCursoAPrograma(
            String nombrePrograma,
            String nombreCurso) {
        escribir(puerto -> {
            puerto.agregarCursoAPrograma(nombrePrograma, nombreCurso);
            return null;
        });
    }

    public void dejarDeSeguirUsuario(
            String nicknameSeguidor,
            String nicknameSeguido) {
        escribir(puerto -> {
            puerto.dejarDeSeguirUsuario(nicknameSeguidor, nicknameSeguido);
            return null;
        });
    }

    public boolean existeCurso(
            String nombre) {
        return crearPuerto().existeCurso(nombre);
    }

    public List<UsuarioResumen> listarSeguidores(
            String nickname) {
        return MapeadorCliente.lista(crearPuerto().listarSeguidores(nickname), MapeadorCliente::convertir);
    }

    public boolean sigueAUsuario(
            String nicknameSeguidor,
            String nicknameSeguido) {
        return crearPuerto().sigueAUsuario(nicknameSeguidor, nicknameSeguido);
    }

    public void altaPrograma(
            String nombre,
            String descripcion,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            LocalDate fechaAlta,
            String rutaImagen,
            String nicknameDocente) {
        escribir(puerto -> {
            puerto.altaPrograma(nombre, descripcion, fecha(fechaInicio), fecha(fechaFin), fecha(fechaAlta), rutaImagen, nicknameDocente);
            return null;
        });
    }

    public List<Curso> listarCursosPorInstituto(
            String instituto) {
        // El contrato por instituto devuelve resúmenes. Conservamos los campos
        // completos que utilizan las listas de cursos, sin cambiar el WSDL.
        List<cliente.central.CursoResumen> resumenes
                = crearPuerto().listarCursosPorInstituto(instituto);
        java.util.Map<String, Curso> completos = new java.util.HashMap<>();
        if (resumenes != null && !resumenes.isEmpty()) {
            for (Curso curso : listarCursos()) {
                completos.put(curso.getNombre(), curso);
            }
        }
        List<Curso> resultado = new java.util.ArrayList<>();
        if (resumenes != null) {
            for (cliente.central.CursoResumen resumen : resumenes) {
                Curso curso = completos.get(resumen.getNombre());
                if (curso == null) {
                    throw new IllegalStateException(
                            "El curso cambió durante la consulta. Actualizá la página.");
                }
                resultado.add(curso);
            }
        }
        return resultado;
    }

    public List<UsuarioResumen> listarDocentesPorInstituto(
            String instituto) {
        return MapeadorCliente.lista(crearPuerto().listarDocentesPorInstituto(instituto), MapeadorCliente::convertir);
    }

    public DetalleCurso consultarCurso(
            String nombreCurso) {
        return MapeadorCliente.convertir(crearPuerto().consultarCurso(nombreCurso));
    }

    public boolean existeEdicion(
            String nombre) {
        return crearPuerto().existeEdicion(nombre);
    }

    public boolean cambiarContrasenia(
            String nickname,
            String contraseniaActual,
            String contraseniaNueva) {
        return escribir(puerto -> puerto.cambiarContrasenia(nickname, contraseniaActual, contraseniaNueva));
    }

    public void altaEdicionCurso(
            String nombre,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer cupo,
            String nombreCurso,
            List<String> nicknamesDocentes,
            String rutaImagen) {
        escribir(puerto -> {
            puerto.altaEdicionCurso(nombre, fecha(fechaInicio), fecha(fechaFin), cupo, nombreCurso, nicknamesDocentes, rutaImagen);
            return null;
        });
    }

    public InscripcionPrograma buscarInscripcionPrograma(
            String nicknameEstudiante,
            String nombrePrograma) {
        return MapeadorCliente.convertir(crearPuerto().buscarInscripcionPrograma(nicknameEstudiante, nombrePrograma));
    }

    public UsuarioResumen iniciarSesion(
            String nicknameOEmail,
            String contrasenia) {
        return MapeadorCliente.convertir(crearPuerto().iniciarSesion(nicknameOEmail, contrasenia));
    }

    public List<InscripcionResumen> listarAceptadosPorEdicion(
            String nombreEdicion) {
        return MapeadorCliente.lista(crearPuerto().listarAceptadosPorEdicion(nombreEdicion), MapeadorCliente::convertir);
    }

    public void inscribirEstudianteEdicion(
            String nicknameEstudiante,
            String nombreEdicion,
            LocalDate fechaInscripcion,
            String videoUrl) {
        escribir(puerto -> {
            puerto.inscribirEstudianteEdicion(nicknameEstudiante, nombreEdicion, fecha(fechaInscripcion), videoUrl);
            return null;
        });
    }

    public void modificarUsuario(
            String nickname,
            String nombre,
            String apellido,
            LocalDate fechaNacimiento,
            String nombreInstituto,
            String rutaImagen) {
        escribir(puerto -> {
            puerto.modificarUsuario(nickname, nombre, apellido, fecha(fechaNacimiento), nombreInstituto, rutaImagen);
            return null;
        });
    }

    public InscripcionEdicion buscarInscripcionEdicion(
            String nicknameEstudiante,
            String nombreEdicion) {
        return MapeadorCliente.convertir(crearPuerto().buscarInscripcionEdicion(nicknameEstudiante, nombreEdicion));
    }

    public List<String> listarNombresCategorias() {
        return crearPuerto().listarNombresCategorias();
    }

    public void modificarInscripcionEdicion(
            Long idInscripcion,
            LocalDate nuevaFecha,
            String videoUrl) {
        escribir(puerto -> {
            puerto.modificarInscripcionEdicion(idInscripcion, fecha(nuevaFecha), videoUrl);
            return null;
        });
    }

    public void inscribirEstudiantePrograma(
            String nicknameEstudiante,
            String nombrePrograma,
            LocalDate fechaInscripcion) {
        escribir(puerto -> {
            puerto.inscribirEstudiantePrograma(nicknameEstudiante, nombrePrograma, fecha(fechaInscripcion));
            return null;
        });
    }

    public void desistirInscripcionEdicion(
            String nicknameEstudiante,
            String nombreEdicion) {

        escribir(puerto -> {
            puerto.desistirInscripcionEdicion(
                    nicknameEstudiante,
                    nombreEdicion);

            return null;
        });
    }
    
    public List<EvaluacionEstudiante> listarEvaluacionesPorEdicion(
            String nombreEdicion) {
        return MapeadorCliente.lista(
                crearPuerto().listarEvaluacionesPorEdicion(nombreEdicion),
                MapeadorCliente::convertir);
    }

    public void guardarNotas(
            String nicknameDocente,
            String nombreEdicion,
            Map<Long, Integer> notas) {
        List<cliente.central.EvaluacionEstudianteDTO> lista = new ArrayList<>();
        for (Map.Entry<Long, Integer> e : notas.entrySet()) {
            cliente.central.EvaluacionEstudianteDTO dto
                    = new cliente.central.EvaluacionEstudianteDTO();
            dto.setIdInscripcion(e.getKey());
            dto.setNota(e.getValue());
            lista.add(dto);
        }
        escribir(puerto -> {
            puerto.guardarNotas(nicknameDocente, nombreEdicion, lista);
            return null;
        });
    }

    public void cerrarEdicion(String nicknameDocente, String nombreEdicion) {
        escribir(puerto -> {
            puerto.cerrarEdicion(nicknameDocente, nombreEdicion);
            return null;
        });
    }

    public boolean edicionCerrada(String nombreEdicion) {
        return escribir(puerto -> puerto.edicionCerrada(nombreEdicion));
    }
}
