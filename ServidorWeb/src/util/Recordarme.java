package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Opción «Recordarme» del login.
 *
 * Nunca se guarda la contraseña en el navegador. Si el usuario marca la casilla:
 *  - la sesión dura DIAS días (en vez de los 30 minutos de web.xml) y su cookie de
 *    sesión pasa a ser persistente, así que sigue logueado al volver a abrir el navegador;
 *  - se guarda su nickname en una cookie para rellenar el campo la próxima vez.
 */
public final class Recordarme {

    private Recordarme() {}

    public static final String COOKIE_NICK = "edext_nick";
    public static final int DIAS = 30;
    private static final int SEGUNDOS = DIAS * 24 * 60 * 60;

    /** Nickname guardado por una sesión anterior, o null si no hay. */
    public static String nickRecordado(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (COOKIE_NICK.equals(c.getName()) && c.getValue() != null && !c.getValue().isEmpty()) {
                try {
                    return URLDecoder.decode(c.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException | IllegalArgumentException e) {
                    return null;
                }
            }
        }
        return null;
    }

    /** Activa «Recordarme» para la sesión recién creada. */
    public static void activar(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, String nickname) {
        session.setMaxInactiveInterval(SEGUNDOS);

        // El contenedor ya envió la cookie de sesión (sin vencimiento); se reenvía con vencimiento.
        String nombreSesion = request.getServletContext().getSessionCookieConfig().getName();
        if (nombreSesion == null || nombreSesion.isEmpty()) nombreSesion = "JSESSIONID";
        response.addCookie(cookie(request, nombreSesion, session.getId(), SEGUNDOS));

        try {
            response.addCookie(cookie(request, COOKIE_NICK, URLEncoder.encode(nickname, "UTF-8"), SEGUNDOS));
        } catch (UnsupportedEncodingException e) {
            // UTF-8 siempre existe; si faltara, solo no se recuerda el nickname
        }
    }

    /** Desactiva «Recordarme»: borra el nickname guardado. */
    public static void desactivar(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(cookie(request, COOKIE_NICK, "", 0));
    }

    /** Borra del navegador la cookie de sesión (se usa después de invalidar la sesión en el servidor). */
    public static void borrarCookieSesion(HttpServletRequest request, HttpServletResponse response) {
        String nombreSesion = request.getServletContext().getSessionCookieConfig().getName();
        if (nombreSesion == null || nombreSesion.isEmpty()) nombreSesion = "JSESSIONID";
        response.addCookie(cookie(request, nombreSesion, "", 0));
    }

    private static Cookie cookie(HttpServletRequest request, String nombre, String valor, int segundos) {
        Cookie c = new Cookie(nombre, valor);
        String ctx = request.getContextPath();
        c.setPath(ctx == null || ctx.isEmpty() ? "/" : ctx);
        c.setMaxAge(segundos);
        c.setHttpOnly(true);
        c.setSecure(request.isSecure());
        return c;
    }
}
