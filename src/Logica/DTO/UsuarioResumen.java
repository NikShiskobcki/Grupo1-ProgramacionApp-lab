package Logica.DTO;


public class UsuarioResumen {

    private final String nickname;
    private final String nombreCompleto;
    private final String tipo; // "Estudiante" o "Docente"
    private final String rutaImagen;

    public UsuarioResumen(String nickname, String nombreCompleto, String tipo, String rutaImagen) {
        this.nickname = nickname;
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
        this.rutaImagen = rutaImagen;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nickname + " - " + nombreCompleto + " (" + tipo + ")";
    }
}
