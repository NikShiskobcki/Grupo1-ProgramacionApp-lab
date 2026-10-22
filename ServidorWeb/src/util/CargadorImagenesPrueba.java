package util;

import Logica.DatosPrueba.ImagenesPrueba;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class CargadorImagenesPrueba {

    private CargadorImagenesPrueba() {
    }

    public static void cargar() throws IOException {
        for (Map.Entry<String, String> imagen
                : ImagenesPrueba.ARCHIVOS.entrySet()) {

            String ruta = ImagenesPrueba.ruta(imagen.getKey());
            String nombre = ruta.substring("/imagenes/".length());

            Path destino = Imagenes.resolver(nombre);

            // Si ya está en el volumen, no creamos otra copia.
            if (Files.isRegularFile(destino)
                    && Files.size(destino) > 0) {
                continue;
            }

            String recurso = "/datos-prueba/imagenes/"
                    + imagen.getValue();

            try (InputStream entrada =
                    CargadorImagenesPrueba.class
                            .getResourceAsStream(recurso)) {

                if (entrada == null) {
                    throw new IOException(
                            "No se encontró la imagen de prueba: "
                            + recurso);
                }

                String rutaTemporal = null;

                try {
                    // Reutiliza las validaciones y escritura de las altas.
                    rutaTemporal = Imagenes.guardar(entrada);

                    String nombreTemporal = rutaTemporal.substring(
                            "/imagenes/".length());

                    Path temporal = Imagenes.resolver(nombreTemporal);

                    String extensionEsperada =
                            nombre.substring(nombre.lastIndexOf('.'));

                    if (!nombreTemporal.endsWith(extensionEsperada)) {
                        throw new IOException(
                                "El formato real de "
                                + imagen.getValue()
                                + " no coincide con su extensión. "
                                + "Convertí el archivo a "
                                + extensionEsperada);
                    }

                    // Usa el nombre estable que Central guardará en la BD.
                    Files.move(temporal, destino);

                } catch (IllegalArgumentException e) {
                    throw new IOException(
                            "Imagen de prueba inválida: "
                            + imagen.getValue() + ". "
                            + e.getMessage(), e);

                } finally {
                    if (rutaTemporal != null) {
                        Imagenes.eliminar(rutaTemporal);
                    }
                }
            }
        }
    }
}
