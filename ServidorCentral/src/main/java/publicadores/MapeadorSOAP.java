package publicadores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import publicadores.dto.*;

/** Conversiones de datos. No aplica reglas de negocio. */
public final class MapeadorSOAP {
    private MapeadorSOAP() { }

    public static String fecha(LocalDate fecha) {
        return fecha == null ? null : fecha.toString();
    }

    public static <A, B> List<B> lista(List<A> valores, Function<A, B> convertir) {
        List<B> resultado = new ArrayList<>();
        if (valores != null) {
            for (A valor : valores) resultado.add(convertir.apply(valor));
        }
        return resultado;
    }

    public static DetalleCursoDTO convertir(Logica.DTO.DetalleCurso origen) {
        if (origen == null) return null;
        return new DetalleCursoDTO(
                origen.getNombre(),
                origen.getDescripcion(),
                origen.getDuracion(),
                origen.getCantidadHoras(),
                origen.getCreditos(),
                origen.getUrl(),
                fecha(origen.getFechaAlta()),
                origen.getInstituto(),
                origen.getPrevias(),
                origen.getEdiciones(),
                origen.getProgramas(),
                origen.getCantidadInscriptos(),
                origen.getCategorias(),
                origen.getRutaImagen());
    }

    public static DetalleEdicionCursoDTO convertir(Logica.DTO.DetalleEdicionCurso origen) {
        if (origen == null) return null;
        return new DetalleEdicionCursoDTO(
                origen.getNombre(),
                fecha(origen.getFechaInicio()),
                fecha(origen.getFechaFin()),
                origen.getCupo(),
                fecha(origen.getFechaPublicacion()),
                origen.getCurso(),
                origen.getDocentes(),
                origen.getCantidadInscriptos(),
                origen.getRutaImagen(),
                origen.getCategorias());
    }

    public static DetalleProgramaFormacionDTO convertir(Logica.DTO.DetalleProgramaFormacion origen) {
        if (origen == null) return null;
        return new DetalleProgramaFormacionDTO(
                origen.getNombre(),
                origen.getDescripcion(),
                fecha(origen.getFechaInicio()),
                fecha(origen.getFechaFin()),
                fecha(origen.getFechaAlta()),
                origen.getCursos(),
                origen.getCategorias(),
                origen.getRutaImagen());
    }

    public static DetalleUsuarioDTO convertir(Logica.DTO.DetalleUsuario origen) {
        if (origen == null) return null;
        return new DetalleUsuarioDTO(
                origen.getNickname(),
                origen.getNombre(),
                origen.getApellido(),
                origen.getEmail(),
                fecha(origen.getFechaNacimiento()),
                origen.getTipoUsuario(),
                origen.getInstituto(),
                origen.getRutaImagen(),
                lista(origen.getCursos(), MapeadorSOAP::convertir),
                lista(origen.getEdiciones(), MapeadorSOAP::convertir),
                lista(origen.getProgramas(), MapeadorSOAP::convertir),
                lista(origen.getInscripcionesRechazadas(), MapeadorSOAP::convertir));
    }

    public static ElementoResumenDTO convertir(Logica.DTO.ElementoResumen origen) {
        if (origen == null) return null;
        return new ElementoResumenDTO(
                origen.getNombre(),
                origen.getTextoMostrar());
    }

    public static InscripcionResumenDTO convertir(Logica.DTO.InscripcionResumen origen) {
        if (origen == null) return null;
        return new InscripcionResumenDTO(
                origen.getId(),
                origen.getNicknameEstudiante(),
                origen.getNombreEstudiante(),
                fecha(origen.getFechaInscripcion()),
                origen.getEstado(),
                origen.getPrioridad());
    }

    public static ResultadoInscripcionDTO convertir(Logica.DTO.ResultadoInscripcion origen) {
        if (origen == null) return null;
        return new ResultadoInscripcionDTO(
                origen.getNombreEdicion(),
                origen.getNombreCurso(),
                fecha(origen.getFechaInscripcion()),
                origen.getEstado());
    }

    public static UsuarioEdicionDTO convertir(Logica.DTO.UsuarioEdicion origen) {
        if (origen == null) return null;
        return new UsuarioEdicionDTO(
                origen.getNickname(),
                origen.getEmail(),
                origen.getTipoUsuario(),
                origen.getNombre(),
                origen.getApellido(),
                fecha(origen.getFechaNacimiento()),
                origen.getInstituto(),
                origen.getRutaImagen());
    }

    public static InstitutoDTO convertir(Logica.Entidades.Instituto origen) {
        return origen == null ? null : new InstitutoDTO(origen.getNombre());
    }

    public static CursoDTO convertir(Logica.Entidades.Curso origen) {
        if (origen == null) return null;
        return new CursoDTO(origen.getNombre(), origen.getDescripcion(),
                origen.getDuracion(), origen.getCantidadHoras(), origen.getCreditos(),
                origen.getUrl(), fecha(origen.getFechaAlta()), origen.getRutaImagen(),
                convertir(origen.getInstituto()));
    }

    public static DocenteDTO convertir(Logica.Entidades.Docente origen) {
        if (origen == null) return null;
        return new DocenteDTO(origen.getNickname(), origen.getNombre(),
                origen.getApellido(), origen.getRutaImagen());
    }

    public static EdicionCursoDTO edicion(
            Logica.Entidades.EdicionCurso origen, boolean incluirDocentes) {
        if (origen == null) return null;
        // Las listas no necesitan recorrer relaciones de carga diferida.
        // buscarEdicion ya obtiene explícitamente los docentes en el manejador.
        return new EdicionCursoDTO(origen.getNombre(), fecha(origen.getFechaInicio()),
                fecha(origen.getFechaFin()), origen.getCupo(),
                fecha(origen.getFechaPublicacion()), origen.getRutaImagen(),
                null, incluirDocentes
                    ? lista(origen.getDocentes(), MapeadorSOAP::convertir)
                    : new ArrayList<>());
    }

    public static InscripcionEdicionDTO inscripcionEdicion(
            Logica.Entidades.InscripcionEdicion origen,
            String nicknameEstudiante, String nombreEdicion) {
        if (origen == null) return null;
        return new InscripcionEdicionDTO(origen.getId(),
                fecha(origen.getFechaInscripcion()),
                origen.getEstado() == null ? null : origen.getEstado().name(),
                nicknameEstudiante, nombreEdicion);
    }

    public static InscripcionProgramaDTO inscripcionPrograma(
            Logica.Entidades.InscripcionPrograma origen,
            String nicknameEstudiante, String nombrePrograma) {
        if (origen == null) return null;
        return new InscripcionProgramaDTO(origen.getId(),
                fecha(origen.getFechaInscripcion()),
                nicknameEstudiante, nombrePrograma);
    }
}
