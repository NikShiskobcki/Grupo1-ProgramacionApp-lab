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

    /** Extrae el nickname de un texto con formato "Nombre Apellido (nickname)". */
    public static String nicknameDeDocente(String texto) {
        if (texto == null) return null;
        int a = texto.lastIndexOf('(');
        int b = texto.lastIndexOf(')');
        if (a >= 0 && b > a) return texto.substring(a + 1, b).trim();
        return null;
    }
}
