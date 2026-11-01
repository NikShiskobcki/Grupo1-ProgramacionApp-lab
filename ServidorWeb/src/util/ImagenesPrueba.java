package util;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/** Manifiesto de recursos de imagen de Web; no depende de la lógica de Central. */
public final class ImagenesPrueba {
    private ImagenesPrueba() { }

    public static final Map<String, String> ARCHIVOS = Map.ofEntries(
            Map.entry("RO", "RO.jpg"),
            Map.entry("CH", "CH.jpg"),
            Map.entry("JW", "JW.jpg"),
            Map.entry("WW", "WW.jpg"),
            Map.entry("OK", "OK.jpg"),
            Map.entry("EW", "EW.jpg"),
            Map.entry("GH", "GH.jpg"),
            Map.entry("TC", "TC.jpg"),
            Map.entry("C3", "C3.jpg"),
            Map.entry("C5", "C5.jpg"),
            Map.entry("C6", "C6.jpg"),
            Map.entry("C7", "C7.jpg"),
            Map.entry("C8", "C8.jpg"),
            Map.entry("C10", "C10.jpg"),
            Map.entry("E1", "E1.jpg"),
            Map.entry("E2", "E2.jpg"),
            Map.entry("E3", "E3.jpg"),
            Map.entry("E4", "E4.jpg"),
            Map.entry("E7", "E7.jpg"),
            Map.entry("E8", "E8.jpg"),
            Map.entry("E9", "E9.jpg"),
            Map.entry("P1", "P1.jpg"),
            Map.entry("P2", "P2.jpg"),
            Map.entry("P3", "P3.jpg")
    );

    public static String ruta(String referencia) {
        String archivo = ARCHIVOS.get(referencia);

        if (archivo == null) {
            throw new IllegalArgumentException(
                    "Imagen de prueba desconocida: " + referencia);
        }

        String extension = archivo.endsWith(".png") ? ".png" : ".jpg";

        UUID identificador = UUID.nameUUIDFromBytes(
                ("edext-datos-prueba/" + referencia)
                        .getBytes(StandardCharsets.UTF_8));

        return "/imagenes/" + identificador + extension;
    }

}
