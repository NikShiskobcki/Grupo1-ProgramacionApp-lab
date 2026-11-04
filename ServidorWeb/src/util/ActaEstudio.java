package util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.CursoResumen;
import modelo.DetalleProgramaFormacion;
import modelo.EdicionCurso;
import modelo.ElementoResumen;
import modelo.EvaluacionEstudiante;
import modelo.ResultadoInscripcion;

/**
 * Lógica del caso de uso "Tramitar Acta de Estudio".
 *
 * Arma el estado de un estudiante en un programa de formación usando solo
 * operaciones que ya expone Central por SOAP. No guarda nada: cada vez que
 * se pide el acta se vuelve a calcular con los datos actuales.
 */
public final class ActaEstudio {

    /**
     * Nota mínima para considerar aprobado un curso (notas de 1 a 12).
     * La letra no define el valor; confirmarlo con el docente y, si cambia,
     * modificarlo solo acá.
     */
    public static final int NOTA_APROBACION = 6;

    public enum Estado { APROBADO, REPROBADO, PENDIENTE }

    /** Una fila del acta: el mejor resultado del estudiante en un curso. */
    public static final class Fila {
        private final String nombreCurso;
        private final String nombreEdicion;
        private final Integer nota;
        private final LocalDate fecha;
        private final Estado estado;

        Fila(String nombreCurso, String nombreEdicion, Integer nota,
                LocalDate fecha, Estado estado) {
            this.nombreCurso = nombreCurso;
            this.nombreEdicion = nombreEdicion;
            this.nota = nota;
            this.fecha = fecha;
            this.estado = estado;
        }

        public String getNombreCurso() { return nombreCurso; }
        public String getNombreEdicion() { return nombreEdicion; }
        public Integer getNota() { return nota; }
        public LocalDate getFecha() { return fecha; }
        public Estado getEstado() { return estado; }
    }

    /** Resultado completo para un programa de formación. */
    public static final class Acta {
        private final DetalleProgramaFormacion programa;
        private final List<Fila> filas;

        Acta(DetalleProgramaFormacion programa, List<Fila> filas) {
            this.programa = programa;
            this.filas = filas;
        }

        public DetalleProgramaFormacion getPrograma() { return programa; }
        public List<Fila> getFilas() { return filas; }

        public int getTotalCursos() { return filas.size(); }

        public int getCursosAprobados() {
            int n = 0;
            for (Fila f : filas) {
                if (f.getEstado() == Estado.APROBADO) n++;
            }
            return n;
        }

        /** El acta solo se puede tramitar si aprobó todos los cursos del programa. */
        public boolean isTramitable() {
            return !filas.isEmpty() && getCursosAprobados() == filas.size();
        }
    }

    private ActaEstudio() {
    }

    /** Nombres de los programas a los que está inscripto el estudiante. */
    public static List<String> programasDelEstudiante(
            ClienteCentral central, String nickname) {
        List<String> nombres = new ArrayList<>();
        modelo.DetalleUsuario detalle = central.consultarUsuario(nickname, nickname);
        if (detalle != null && detalle.getProgramas() != null) {
            for (ElementoResumen p : detalle.getProgramas()) {
                nombres.add(p.getNombre());
            }
        }
        return nombres;
    }

    /**
     * Calcula el estado del estudiante en cada curso del programa.
     * Por cada curso toma las ediciones cerradas en las que fue aceptado y
     * conserva la mejor nota. Si no hay ninguna, el curso queda pendiente.
     */
    public static Acta construir(ClienteCentral central, String nickname,
            String nombrePrograma) {
        DetalleProgramaFormacion programa = central.consultarPrograma(nombrePrograma);
        if (programa == null) {
            throw new IllegalArgumentException("El programa de formación no existe.");
        }

        List<ResultadoInscripcion> inscripciones =
                central.listarResultadosPorEstudiante(nickname);

        List<Fila> filas = new ArrayList<>();
        if (programa.getCursos() != null) {
            for (CursoResumen curso : programa.getCursos()) {
                filas.add(mejorResultado(central, nickname, curso.getNombre(), inscripciones));
            }
        }
        return new Acta(programa, filas);
    }

    private static Fila mejorResultado(ClienteCentral central, String nickname,
            String nombreCurso, List<ResultadoInscripcion> inscripciones) {

        Fila mejor = null;
        if (inscripciones != null) {
            for (ResultadoInscripcion ri : inscripciones) {
                if (!"ACEPTADA".equalsIgnoreCase(ri.getEstado())
                        || !nombreCurso.equals(ri.getNombreCurso())) {
                    continue;
                }
                String edicion = ri.getNombreEdicion();
                // Las notas solo son definitivas cuando el docente cerró la edición.
                if (!central.edicionCerrada(edicion)) {
                    continue;
                }
                Integer nota = notaDe(central, edicion, nickname);
                if (nota == null) {
                    continue;
                }
                EdicionCurso datosEdicion = central.buscarEdicion(edicion);
                LocalDate fecha = datosEdicion == null ? null : datosEdicion.getFechaFin();
                Estado estado = nota >= NOTA_APROBACION ? Estado.APROBADO : Estado.REPROBADO;
                Fila candidata = new Fila(nombreCurso, edicion, nota, fecha, estado);
                if (mejor == null || candidata.getNota() > mejor.getNota()) {
                    mejor = candidata;
                }
            }
        }
        return mejor != null
                ? mejor
                : new Fila(nombreCurso, null, null, null, Estado.PENDIENTE);
    }

    private static Integer notaDe(ClienteCentral central, String edicion, String nickname) {
        List<EvaluacionEstudiante> evaluaciones =
                central.listarEvaluacionesPorEdicion(edicion);
        if (evaluaciones != null) {
            for (EvaluacionEstudiante ev : evaluaciones) {
                if (nickname.equals(ev.getNicknameEstudiante())) {
                    return ev.getNota();
                }
            }
        }
        return null;
    }
}
