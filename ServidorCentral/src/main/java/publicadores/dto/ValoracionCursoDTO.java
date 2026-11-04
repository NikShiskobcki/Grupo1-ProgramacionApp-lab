package publicadores.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Promedio del curso y puntaje del estudiante (los valores ausentes viajan como null). */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ValoracionCursoDTO",
    namespace = "http://ws.edext.utec.edu.uy/",
    propOrder = {"nombreCurso", "promedio", "cantidad", "puntajeEstudiante"}
)
public class ValoracionCursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreCurso;
    private Double promedio;
    private int cantidad;
    private Integer puntajeEstudiante;

    public ValoracionCursoDTO() {
    }

    public ValoracionCursoDTO(String nombreCurso, Double promedio, int cantidad,
            Integer puntajeEstudiante) {
        this.nombreCurso = nombreCurso;
        this.promedio = promedio;
        this.cantidad = cantidad;
        this.puntajeEstudiante = puntajeEstudiante;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPuntajeEstudiante() {
        return puntajeEstudiante;
    }

    public void setPuntajeEstudiante(Integer puntajeEstudiante) {
        this.puntajeEstudiante = puntajeEstudiante;
    }
}
