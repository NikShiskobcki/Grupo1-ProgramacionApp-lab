package modelo;

public class EvaluacionEstudiante implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private final Long idInscripcion;
    private final String nicknameEstudiante;
    private final String nombreEstudiante;
    private final Integer nota;

    public EvaluacionEstudiante(Long idInscripcion,
            String nicknameEstudiante,
            String nombreEstudiante,
            Integer nota) {
        this.idInscripcion = idInscripcion;
        this.nicknameEstudiante = nicknameEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.nota = nota;
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public String getNicknameEstudiante() {
        return nicknameEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public Integer getNota() {
        return nota;
    }
}
