package modelo;


public class ElementoResumen implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

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
