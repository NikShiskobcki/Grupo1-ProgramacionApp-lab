package Logica.seguridad;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Hash de contraseñas con PBKDF2-HMAC-SHA256 (solo JDK, sin dependencias).
 * Formato almacenado: pbkdf2$iteraciones$salt(base64)$hash(base64).
 * Los valores que no tienen ese formato se consideran contraseñas heredadas
 * en texto plano (ver {@link #esHash(String)}).
 */
public final class Contrasenias {

    private static final String PREFIJO = "pbkdf2$";
    private static final int ITERACIONES = 65536;
    private static final int BYTES_SALT = 16;
    private static final int BITS_HASH = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private Contrasenias() {
    }

    public static boolean esHash(String almacenada) {
        return almacenada != null && almacenada.startsWith(PREFIJO);
    }

    public static String hashear(String plana) {
        byte[] salt = new byte[BYTES_SALT];
        RANDOM.nextBytes(salt);
        byte[] hash = derivar(plana, salt, ITERACIONES);
        return PREFIJO + ITERACIONES + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    /** Compara una contraseña ingresada con la almacenada (hash o texto plano heredado). */
    public static boolean verificar(String plana, String almacenada) {
        if (plana == null || almacenada == null) {
            return false;
        }
        if (!esHash(almacenada)) {
            return MessageDigest.isEqual(
                    plana.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                    almacenada.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
        String[] partes = almacenada.split("\\$");
        if (partes.length != 4) {
            return false;
        }
        try {
            int iteraciones = Integer.parseInt(partes[1]);
            byte[] salt = Base64.getDecoder().decode(partes[2]);
            byte[] esperado = Base64.getDecoder().decode(partes[3]);
            return MessageDigest.isEqual(derivar(plana, salt, iteraciones), esperado);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] derivar(String plana, byte[] salt, int iteraciones) {
        try {
            PBEKeySpec spec = new PBEKeySpec(plana.toCharArray(), salt, iteraciones, BITS_HASH);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("No se pudo calcular el hash de la contraseña", e);
        }
    }
}
