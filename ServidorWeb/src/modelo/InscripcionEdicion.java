package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class InscripcionEdicion implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private LocalDate fechaInscripcion;
    private EstadoInscripcion estado;
    private String nicknameEstudiante;
    private String nombreEdicion;

    public InscripcionEdicion() { }

    public InscripcionEdicion(Long id, LocalDate fechaInscripcion, EstadoInscripcion estado, String nicknameEstudiante, String nombreEdicion) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.nicknameEstudiante = nicknameEstudiante;
        this.nombreEdicion = nombreEdicion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public EstadoInscripcion getEstado() { return estado; }
    public void setEstado(EstadoInscripcion estado) { this.estado = estado; }

    public String getNicknameEstudiante() { return nicknameEstudiante; }
    public void setNicknameEstudiante(String nicknameEstudiante) { this.nicknameEstudiante = nicknameEstudiante; }

    public String getNombreEdicion() { return nombreEdicion; }
    public void setNombreEdicion(String nombreEdicion) { this.nombreEdicion = nombreEdicion; }
}
