package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "InscripcionEdicionDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"id", "fechaInscripcion", "estado", "nicknameEstudiante", "nombreEdicion"}
)
public class InscripcionEdicionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fechaInscripcion;
    private String estado;
    private String nicknameEstudiante;
    private String nombreEdicion;

    public InscripcionEdicionDTO() {
    }

    public InscripcionEdicionDTO(
            Long id,
            String fechaInscripcion,
            String estado,
            String nicknameEstudiante,
            String nombreEdicion) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.nicknameEstudiante = nicknameEstudiante;
        this.nombreEdicion = nombreEdicion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNicknameEstudiante() {
        return nicknameEstudiante;
    }

    public void setNicknameEstudiante(String nicknameEstudiante) {
        this.nicknameEstudiante = nicknameEstudiante;
    }

    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }
}
