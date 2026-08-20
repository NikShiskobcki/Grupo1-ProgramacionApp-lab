package Logica.DTO;


public class UsuarioResumen {

    private final String nickname;
    private final String nombreCompleto;
    private final String tipo; // "Estudiante" o "Docente"

    public UsuarioResumen(String nickname, String nombreCompleto, String tipo) {
        this.nickname = nickname;
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
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

    @Override
    public String toString() {
        return nickname + " - " + nombreCompleto + " (" + tipo + ")";
    }
}
