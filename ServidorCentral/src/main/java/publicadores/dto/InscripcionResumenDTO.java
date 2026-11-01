package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "InscripcionResumenDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"id", "nicknameEstudiante", "nombreEstudiante", "fechaInscripcion", "estado", "prioridad"}
)
public class InscripcionResumenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nicknameEstudiante;
    private String nombreEstudiante;
    private String fechaInscripcion;
    private String estado;
    private double prioridad;

    public InscripcionResumenDTO() {
    }

    public InscripcionResumenDTO(
            Long id,
            String nicknameEstudiante,
            String nombreEstudiante,
            String fechaInscripcion,
            String estado,
            double prioridad) {
        this.id = id;
        this.nicknameEstudiante = nicknameEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.prioridad = prioridad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(double prioridad) {
        this.prioridad = prioridad;
    }
}
