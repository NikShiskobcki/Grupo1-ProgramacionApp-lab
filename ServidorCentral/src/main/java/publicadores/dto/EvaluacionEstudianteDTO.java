package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "EvaluacionEstudianteDTO",
        namespace = "http://ws.edext.utec.edu.uy/",
        propOrder = {"idInscripcion", "nicknameEstudiante", "nombreEstudiante", "nota"}
)
public class EvaluacionEstudianteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idInscripcion;
    private String nicknameEstudiante;
    private String nombreEstudiante;
    private Integer nota;

    public EvaluacionEstudianteDTO() {
    }

    public EvaluacionEstudianteDTO(
            Long idInscripcion,
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

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public String getNicknameEstudiante() {
        return nicknameEstudiante;
    }

    public void setNicknameEstudiante(String nicknameEstudiante) {
        this.nicknameEstudiante = nicknameEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
