package modelo;

public class UsuarioResumen implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;
    private String nombreCompleto;
    private String tipo;
    private String rutaImagen;

    public UsuarioResumen() {
    }

    public UsuarioResumen(
            String nickname,
            String nombreCompleto,
            String tipo,
            String rutaImagen) {

        this.nickname = nickname;
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
        this.rutaImagen = rutaImagen;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    @Override
    public String toString() {
        return nickname + " - " + nombreCompleto
                + " (" + tipo + ")";
    }
}
