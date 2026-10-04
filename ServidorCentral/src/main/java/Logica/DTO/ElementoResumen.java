package Logica.DTO;

/**
 * DTO genérico para representar un ítem "navegable" dentro del detalle de
 * un usuario: guarda el nombre real de la entidad (curso, edición de curso
 * o programa de formación), que se usa como clave para volver a consultarla,
 * y un texto descriptivo para mostrar en las listas de la interfaz.
 */
public class ElementoResumen {

    private final String nombre;
    private final String textoMostrar;

    public ElementoResumen(String nombre, String textoMostrar) {
        this.nombre = nombre;
        this.textoMostrar = textoMostrar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTextoMostrar() {
        return textoMostrar;
    }

    @Override
    public String toString() {
        return textoMostrar;
    }
}
