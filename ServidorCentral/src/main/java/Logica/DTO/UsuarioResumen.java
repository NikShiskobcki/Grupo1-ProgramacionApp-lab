package Logica.DTO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "UsuarioResumen",
        namespace = "http://ws.edext.utec.edu.uy/",
        propOrder = {
            "nickname",
            "nombreCompleto",
            "tipo",
            "rutaImagen"
        }
)
public class UsuarioResumen {

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