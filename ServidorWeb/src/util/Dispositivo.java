package util;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * Distingue si el usuario navega desde un celular o desde una computadora.
 *
 * Se usa el header Sec-CH-UA-Mobile cuando el navegador lo envía (Chrome y Edge, sobre
 * HTTPS o localhost) y, si no está, el User-Agent. Las tablets se consideran computadora:
 * lo que cambia el diseño es el ancho de pantalla, y de eso se encarga Bootstrap.
 */
public final class Dispositivo {

    private Dispositivo() {}

    /** Atributo de request que deja cargado DispositivoFilter (Boolean). */
    public static final String ATRIBUTO = "esMovil";

    private static final Pattern MOVIL = Pattern.compile(
            "mobi|iphone|ipod|windows phone|blackberry|opera mini",
            Pattern.CASE_INSENSITIVE);

    /** Versión pura (sin request) para poder probarla fácilmente. */
    public static boolean esMovil(String userAgent, String chMobile) {
        if (chMobile != null) {
            String h = chMobile.trim();
            if (h.equals("?1")) return true;
            if (h.equals("?0")) return false;
        }
        if (userAgent == null) return false;
        // Safari de iPad trae "Mobile/..." en el User-Agent, pero es una tablet
        if (userAgent.toLowerCase().contains("ipad")) return false;
        return MOVIL.matcher(userAgent).find();
    }

    public static boolean esMovil(HttpServletRequest request) {
        Object cargado = request.getAttribute(ATRIBUTO);
        if (cargado instanceof Boolean) return (Boolean) cargado;
        return esMovil(request.getHeader("User-Agent"), request.getHeader("Sec-CH-UA-Mobile"));
    }
}
