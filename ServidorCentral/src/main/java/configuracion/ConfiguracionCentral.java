package configuracion;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfiguracionCentral {

    private final Properties propiedades;

    private ConfiguracionCentral(Properties propiedades) {
        this.propiedades = propiedades;
    }

    public static ConfiguracionCentral cargar() throws IOException {
        Path archivo = Path.of(
                System.getProperty("user.home"),
                ".edEXT",
                "central.properties"
        );

        Properties propiedades = new Properties();

        try (Reader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            propiedades.load(lector);

        } catch (IOException e) {
            throw new IOException(
                    "No se pudo leer la configuración: " + archivo, e);
        }

        return new ConfiguracionCentral(propiedades);
    }

    public String obtener(String clave) {
        String valor = propiedades.getProperty(clave);

        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                    "Falta configurar la propiedad: " + clave);
        }

        return valor.trim();
    }

    public String urlPublicacion() {
        return obtener("central.publicacion");
    }

    public Map<String, Object> propiedadesPersistencia() {
        Map<String, Object> resultado = new HashMap<>();

        String[] claves = {
            "javax.persistence.jdbc.url",
            "javax.persistence.jdbc.user",
            "javax.persistence.jdbc.password",
            "javax.persistence.jdbc.driver"
        };

        for (String clave : claves) {
            resultado.put(clave, obtener(clave));
        }

        return resultado;
    }
}
