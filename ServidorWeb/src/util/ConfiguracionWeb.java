package util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ConfiguracionWeb {

    private static final Properties PROPIEDADES = cargar();

    private ConfiguracionWeb() {
    }

    private static Properties cargar() {
        Path archivo = Path.of(
                System.getProperty("user.home"),
                ".edEXT",
                "web.properties"
        );

        Properties propiedades = new Properties();

        try (Reader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            propiedades.load(lector);
            return propiedades;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo leer la configuración de Web: "
                    + archivo,
                    e);
        }
    }

    public static String obtener(String clave) {
        String valor = PROPIEDADES.getProperty(clave);

        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                    "Falta configurar " + clave
                    + " en .edEXT/web.properties.");
        }

        return valor.trim();
    }
}
