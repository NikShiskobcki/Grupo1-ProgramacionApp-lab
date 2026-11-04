package util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import modelo.*;

/** Conversión de tipos SOAP a datos utilizados por la presentación Web. */
public final class MapeadorCliente {
    private MapeadorCliente() { }

    public static LocalDate fecha(String valor) {
        return valor == null ? null : LocalDate.parse(valor);
    }

    public static <A, B> List<B> lista(List<A> valores, Function<A, B> funcion) {
        List<B> resultado = new ArrayList<>();
        if (valores != null) {
            for (A valor : valores) resultado.add(funcion.apply(valor));
        }
        return resultado;
    }

    public static DetalleEdicionCurso convertir(cliente.central.DetalleEdicionCursoDTO origen) {
        if (origen == null) return null;
        return new DetalleEdicionCurso(
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

    public static DetalleCurso convertir(cliente.central.DetalleCursoDTO origen) {
        if (origen == null) return null;
        return new DetalleCurso(
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

    public static ElementoResumen convertir(cliente.central.ElementoResumenDTO origen) {
        if (origen == null) return null;
        return new ElementoResumen(
                origen.getNombre(),
                origen.getTextoMostrar());
    }

    public static DetalleProgramaFormacion convertir(cliente.central.DetalleProgramaFormacionDTO origen) {
        if (origen == null) return null;
        return new DetalleProgramaFormacion(
                origen.getNombre(),
                origen.getDescripcion(),
                fecha(origen.getFechaInicio()),
                fecha(origen.getFechaFin()),
                fecha(origen.getFechaAlta()),
                lista(origen.getCursos(), MapeadorCliente::convertir),
                origen.getCategorias(),
                origen.getRutaImagen());
    }

    public static DetalleUsuario convertir(cliente.central.DetalleUsuarioDTO origen) {
        if (origen == null) return null;
        return new DetalleUsuario(
                origen.getNickname(),
                origen.getNombre(),
                origen.getApellido(),
                origen.getEmail(),
                fecha(origen.getFechaNacimiento()),
                origen.getTipoUsuario(),
                origen.getInstituto(),
                lista(origen.getCursos(), MapeadorCliente::convertir),
                lista(origen.getEdiciones(), MapeadorCliente::convertir),
                lista(origen.getProgramas(), MapeadorCliente::convertir),
                origen.getRutaImagen(),
                lista(origen.getInscripcionesRechazadas(), MapeadorCliente::convertir));
    }

    public static InscripcionResumen convertir(cliente.central.InscripcionResumenDTO origen) {
        if (origen == null) return null;
        return new InscripcionResumen(
                origen.getId(),
                origen.getNicknameEstudiante(),
                origen.getNombreEstudiante(),
                fecha(origen.getFechaInscripcion()),
                origen.getEstado(),
                origen.getPrioridad());
    }

    public static EvaluacionEstudiante convertir(
            cliente.central.EvaluacionEstudianteDTO origen) {
        if (origen == null) {
            return null;
        }
        return new EvaluacionEstudiante(
                origen.getIdInscripcion(),
                origen.getNicknameEstudiante(),
                origen.getNombreEstudiante(),
                origen.getNota());
    }
    
    public static UsuarioResumen convertir(cliente.central.UsuarioResumen origen) {
        if (origen == null) return null;
        return new UsuarioResumen(
                origen.getNickname(),
                origen.getNombreCompleto(),
                origen.getTipo(),
                origen.getRutaImagen());
    }

    public static ResultadoEvaluacion convertir(cliente.central.ResultadoEvaluacionDTO origen) {
        if (origen == null) return null;
        return new ResultadoEvaluacion(
                origen.getNombreEdicion(),
                origen.getNombreCurso(),
                fecha(origen.getFechaInscripcion()),
                fecha(origen.getFechaFin()),
                origen.getEstado(),
                origen.getNota());
    }

    public static ValoracionCurso convertir(cliente.central.ValoracionCursoDTO origen) {
        if (origen == null) return null;
        return new ValoracionCurso(
                origen.getNombreCurso(),
                origen.getPromedio(),
                origen.getCantidad(),
                origen.getPuntajeEstudiante());
    }

    public static ResultadoInscripcion convertir(cliente.central.ResultadoInscripcionDTO origen) {
        if (origen == null) return null;
        return new ResultadoInscripcion(
                origen.getNombreEdicion(),
                origen.getNombreCurso(),
                fecha(origen.getFechaInscripcion()),
                origen.getEstado());
    }

    public static UsuarioEdicion convertir(cliente.central.UsuarioEdicionDTO origen) {
        if (origen == null) return null;
        return new UsuarioEdicion(
                origen.getNickname(),
                origen.getEmail(),
                origen.getTipoUsuario(),
                origen.getNombre(),
                origen.getApellido(),
                fecha(origen.getFechaNacimiento()),
                origen.getInstituto(),
                origen.getRutaImagen());
    }

    public static CursoResumen convertir(cliente.central.CursoResumen origen) {
        if (origen == null) return null;
        return new CursoResumen(
                origen.getNombre(),
                origen.getNombreInstituto());
    }

    public static Instituto convertir(cliente.central.InstitutoDTO origen) {
        return origen == null ? null : new Instituto(origen.getNombre());
    }

    public static Curso convertir(cliente.central.CursoDTO origen) {
        if (origen == null) return null;
        return new Curso(origen.getNombre(), origen.getDescripcion(),
                origen.getDuracion(), origen.getCantidadHoras(), origen.getCreditos(),
                origen.getUrl(), fecha(origen.getFechaAlta()), origen.getRutaImagen(),
                convertir(origen.getInstituto()));
    }

    public static Curso curso(cliente.central.CursoResumen origen) {
        if (origen == null) return null;
        Curso curso = new Curso();
        curso.setNombre(origen.getNombre());
        curso.setInstituto(new Instituto(origen.getNombreInstituto()));
        return curso;
    }

    public static Docente convertir(cliente.central.DocenteDTO origen) {
        return origen == null ? null : new Docente(origen.getNickname(),
                origen.getNombre(), origen.getApellido(), origen.getRutaImagen());
    }

    public static EdicionCurso convertir(cliente.central.EdicionCursoDTO origen) {
        if (origen == null) return null;
        return new EdicionCurso(origen.getNombre(), fecha(origen.getFechaInicio()),
                fecha(origen.getFechaFin()), origen.getCupo(),
                fecha(origen.getFechaPublicacion()), origen.getRutaImagen(),
                origen.getNombreCurso(), lista(origen.getDocentes(), MapeadorCliente::convertir));
    }

    public static InscripcionEdicion convertir(cliente.central.InscripcionEdicionDTO origen) {
        if (origen == null) return null;
        EstadoInscripcion estado = origen.getEstado() == null ? EstadoInscripcion.INSCRIPTO
                : EstadoInscripcion.valueOf(origen.getEstado());
        return new InscripcionEdicion(origen.getId(), fecha(origen.getFechaInscripcion()),
                estado, origen.getNicknameEstudiante(), origen.getNombreEdicion(),
                origen.getVideoUrl());
    }

    public static InscripcionPrograma convertir(cliente.central.InscripcionProgramaDTO origen) {
        return origen == null ? null : new InscripcionPrograma(origen.getId(),
                fecha(origen.getFechaInscripcion()), origen.getNicknameEstudiante(),
                origen.getNombrePrograma());
    }
}
