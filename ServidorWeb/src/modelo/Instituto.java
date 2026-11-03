package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Instituto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;

    public Instituto() { }

    public Instituto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
