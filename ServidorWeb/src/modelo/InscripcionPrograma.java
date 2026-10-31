package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class InscripcionPrograma implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private LocalDate fechaInscripcion;
    private String nicknameEstudiante;
    private String nombrePrograma;

    public InscripcionPrograma() { }

    public InscripcionPrograma(Long id, LocalDate fechaInscripcion, String nicknameEstudiante, String nombrePrograma) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.nicknameEstudiante = nicknameEstudiante;
        this.nombrePrograma = nombrePrograma;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public String getNicknameEstudiante() { return nicknameEstudiante; }
    public void setNicknameEstudiante(String nicknameEstudiante) { this.nicknameEstudiante = nicknameEstudiante; }

    public String getNombrePrograma() { return nombrePrograma; }
    public void setNombrePrograma(String nombrePrograma) { this.nombrePrograma = nombrePrograma; }
}
