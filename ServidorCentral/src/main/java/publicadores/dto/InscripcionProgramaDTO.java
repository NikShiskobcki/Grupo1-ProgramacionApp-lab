package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "InscripcionProgramaDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"id", "fechaInscripcion", "nicknameEstudiante", "nombrePrograma"}
)
public class InscripcionProgramaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fechaInscripcion;
    private String nicknameEstudiante;
    private String nombrePrograma;

    public InscripcionProgramaDTO() {
    }

    public InscripcionProgramaDTO(
            Long id,
            String fechaInscripcion,
            String nicknameEstudiante,
            String nombrePrograma) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.nicknameEstudiante = nicknameEstudiante;
        this.nombrePrograma = nombrePrograma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getNicknameEstudiante() {
        return nicknameEstudiante;
    }

    public void setNicknameEstudiante(String nicknameEstudiante) {
        this.nicknameEstudiante = nicknameEstudiante;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }
}
