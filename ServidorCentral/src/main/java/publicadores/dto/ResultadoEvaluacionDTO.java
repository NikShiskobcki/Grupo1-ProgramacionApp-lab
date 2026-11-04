package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Datos de transporte SOAP. Las fechas se expresan como texto ISO AAAA-MM-DD. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ResultadoEvaluacionDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombreEdicion", "nombreCurso", "fechaInscripcion", "fechaFin", "estado", "nota"}
)
public class ResultadoEvaluacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreEdicion;
    private String nombreCurso;
    private String fechaInscripcion;
    private String fechaFin;
    private String estado;
    private Integer nota;

    public ResultadoEvaluacionDTO() {
    }

    public ResultadoEvaluacionDTO(String nombreEdicion, String nombreCurso,
            String fechaInscripcion, String fechaFin, String estado, Integer nota) {
        this.nombreEdicion = nombreEdicion;
        this.nombreCurso = nombreCurso;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.nota = nota;
    }

    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
