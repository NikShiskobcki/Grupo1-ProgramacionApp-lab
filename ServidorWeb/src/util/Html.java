package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/** Utilidades para JSP: escape de HTML, codificación de URLs y resolución de imágenes. */
public final class Html {

    private Html() {}

    /** Escapa texto para insertarlo de forma segura en HTML (evita XSS). */
    public static String esc(Object o) {
        if (o == null) return "";
        String s = o.toString();
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&':  sb.append("&amp;");  break;
                case '<':  sb.append("&lt;");   break;
                case '>':  sb.append("&gt;");   break;
                case '"':  sb.append("&quot;"); break;
                case '\'': sb.append("&#39;");  break;
                default:   sb.append(c);
            }
        }
        return sb.toString();
    }

    /** Codifica un valor para usarlo como parámetro de URL. */
    public static String url(String s) {
        try {
            return URLEncoder.encode(s == null ? "" : s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Devuelve el src de una imagen lista para usar en HTML (ya escapada).
     * Acepta URLs absolutas (http/https) o rutas relativas a la aplicación.
     * Devuelve cadena vacía si no hay imagen.
     */
    public static String imagen(String ctx, String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) return "";
        String r = ruta.trim();
        String lower = r.toLowerCase();
        if (lower.startsWith("http://") || lower.startsWith("https://")) return esc(r);
        if (lower.startsWith("javascript:") || lower.startsWith("data:")) return "";
        if (r.startsWith("/")) return esc(ctx + r);
        return esc(ctx + "/" + r);
    }

    private static final java.util.regex.Pattern ID_YOUTUBE =
            java.util.regex.Pattern.compile("[A-Za-z0-9_-]{11}");

    /** true si el texto es una URL http/https con host (validación de formato, no de existencia). */
    public static boolean esUrlHttp(String texto) {
        if (texto == null) return false;
        try {
            java.net.URI u = new java.net.URI(texto.trim());
            String esq = u.getScheme();
            return esq != null
                    && (esq.equalsIgnoreCase("http") || esq.equalsIgnoreCase("https"))
                    && u.getHost() != null && !u.getHost().isEmpty();
        } catch (java.net.URISyntaxException e) {
            return false;
        }
    }

    /**
     * Convierte una URL de YouTube (watch?v=, youtu.be/, /embed/, /shorts/) en la URL
     * de embebido para un iframe. Devuelve cadena vacía si no es un video de YouTube
     * reconocible; en ese caso la página debe mostrar un enlace en lugar del reproductor.
     * El resultado ya está escapado para HTML.
     */
    public static String embedYoutube(String url) {
        if (!esUrlHttp(url)) return "";
        try {
            java.net.URI u = new java.net.URI(url.trim());
            String host = u.getHost().toLowerCase();
            if (host.startsWith("www.")) host = host.substring(4);
            if (host.startsWith("m.")) host = host.substring(2);
            String path = u.getPath() == null ? "" : u.getPath();
            String id = null;
            if (host.equals("youtu.be")) {
                id = path.startsWith("/") ? path.substring(1) : path;
            } else if (host.equals("youtube.com") || host.equals("youtube-nocookie.com")) {
                if (path.equals("/watch")) {
                    String q = u.getRawQuery();
                    if (q != null) {
                        for (String par : q.split("&")) {
                            if (par.startsWith("v=")) { id = par.substring(2); break; }
                        }
                    }
                } else if (path.startsWith("/embed/")) {
                    id = path.substring(7);
                } else if (path.startsWith("/shorts/")) {
                    id = path.substring(8);
                }
            }
            if (id != null) {
                int corte = id.indexOf('/');
                if (corte >= 0) id = id.substring(0, corte);
                if (ID_YOUTUBE.matcher(id).matches()) {
                    return esc("https://www.youtube.com/embed/" + id);
                }
            }
        } catch (java.net.URISyntaxException e) {
            // se trata como no reconocible
        }
        return "";
    }

    /**
     * Representa un puntaje de 1 a 5 como estrellas ("★★★☆☆"). Se redondea al entero
     * más cercano; null, o un valor fuera de rango, se acota al intervalo 0 a 5.
     */
    public static String estrellas(Double puntaje) {
        int llenas = puntaje == null ? 0 : (int) Math.round(Math.max(0, Math.min(5, puntaje)));
        StringBuilder sb = new StringBuilder(5);
        for (int i = 1; i <= 5; i++) sb.append(i <= llenas ? '\u2605' : '\u2606');
        return sb.toString();
    }

    /** Extrae el nickname de un texto con formato "Nombre Apellido (nickname)". */
    public static String nicknameDeDocente(String texto) {
        if (texto == null) return null;
        int a = texto.lastIndexOf('(');
        int b = texto.lastIndexOf(')');
        if (a >= 0 && b > a) return texto.substring(a + 1, b).trim();
        return null;
    }
}
