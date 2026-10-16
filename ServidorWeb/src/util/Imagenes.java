package util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.Part;

public final class Imagenes {

    public static final long MAX_BYTES = 5L * 1024 * 1024;

    private static final int MAX_DIMENSION = 4096;
    private static final long MAX_PIXELES = 16_000_000L;

    private static final String PREFIJO = "/imagenes/";

    private static final Pattern NOMBRE_VALIDO = Pattern.compile(
            "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-"
            + "[0-9a-f]{4}-[0-9a-f]{12}\\.(jpg|png)"
    );

    private Imagenes() {
    }

    private static Path carpeta() {
        String configurada = System.getenv("IMAGENES_DIR");

        if (configurada == null || configurada.trim().isEmpty()) {
            configurada = "/var/lib/edext/imagenes";
        }

        return Paths.get(configurada).toAbsolutePath().normalize();
    }

    
     //Devuelve null si no se seleccionó un archivo
     //Si existe, valida y guarda la imagen
   
    public static String guardar(Part archivo) throws IOException {
        if (archivo == null) {
            return null;
        }

        String nombreOriginal = archivo.getSubmittedFileName();

        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return null;
        }

        if (archivo.getSize() == 0) {
            throw new IllegalArgumentException(
                    "El archivo de imagen está vacío.");
        }

        if (archivo.getSize() > MAX_BYTES) {
            throw new IllegalArgumentException(
                    "La imagen no puede superar los 5 MB.");
        }

        try (InputStream entrada = archivo.getInputStream()) {
            return guardar(entrada);
        }
    }

    /**
     * Valida y guarda una imagen leída desde cualquier InputStream
     * (por ejemplo, un recurso del classpath). Es la misma lógica que usa
     * guardar(Part), así los datos de prueba pasan por las mismas validaciones.
     */
    public static String guardar(InputStream entradaImagen) throws IOException {
        if (entradaImagen == null) {
            return null;
        }

        byte[] contenido = entradaImagen.readNBytes((int) MAX_BYTES + 1);

        if (contenido.length == 0) {
            throw new IllegalArgumentException(
                    "El archivo de imagen está vacío.");
        }

        if (contenido.length > MAX_BYTES) {
            throw new IllegalArgumentException(
                    "La imagen no puede superar los 5 MB.");
        }

        BufferedImage imagen;
        String formato;

        try (ImageInputStream entrada = ImageIO.createImageInputStream(
                new ByteArrayInputStream(contenido))) {

            if (entrada == null) {
                throw new IllegalArgumentException(
                        "No se pudo leer la imagen.");
            }

            Iterator<ImageReader> lectores =
                    ImageIO.getImageReaders(entrada);

            if (!lectores.hasNext()) {
                throw new IllegalArgumentException(
                        "El archivo no es una imagen JPG o PNG válida.");
            }

            ImageReader lector = lectores.next();

            try {
                lector.setInput(entrada, true, true);

                String tipo = lector.getFormatName();

                if ("JPEG".equalsIgnoreCase(tipo)
                        || "JPG".equalsIgnoreCase(tipo)) {
                    formato = "jpg";

                } else if ("PNG".equalsIgnoreCase(tipo)) {
                    formato = "png";

                } else {
                    throw new IllegalArgumentException(
                            "Solo se permiten imágenes JPG y PNG.");
                }

                int ancho = lector.getWidth(0);
                int alto = lector.getHeight(0);

                if (ancho <= 0 || alto <= 0
                        || ancho > MAX_DIMENSION
                        || alto > MAX_DIMENSION
                        || (long) ancho * alto > MAX_PIXELES) {

                    throw new IllegalArgumentException(
                            "La imagen no puede superar 4096 píxeles "
                            + "por lado ni 16 millones de píxeles.");
                }

                imagen = lector.read(0);

                if (imagen == null) {
                    throw new IllegalArgumentException(
                            "No se pudo interpretar la imagen.");
                }

            } finally {
                lector.dispose();
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "La imagen está dañada o no se puede leer.", e);
        }

        // Normalizamos JPG a RGB para asegurar su escritura.
        if ("jpg".equals(formato)) {
            BufferedImage rgb = new BufferedImage(
                    imagen.getWidth(),
                    imagen.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D dibujo = rgb.createGraphics();

            try {
                dibujo.drawImage(imagen, 0, 0, null);
            } finally {
                dibujo.dispose();
            }

            imagen = rgb;
        }

        Path directorio = carpeta();
        Files.createDirectories(directorio);

        String nombre = UUID.randomUUID().toString() + "." + formato;

        Path destino = directorio.resolve(nombre);
        Path temporal = Files.createTempFile(
                directorio, "imagen-", ".tmp");

        try {
            // Reescribimos la imagen: no copiamos el archivo original.
            if (!ImageIO.write(imagen, formato, temporal.toFile())) {
                throw new IOException(
                        "No se pudo guardar la imagen.");
            }

            Files.move(temporal, destino);

            return PREFIJO + nombre;

        } finally {
            Files.deleteIfExists(temporal);
        }
    }

    
    //Resuelve solamente nombres generados por esta utilidad.
    public static Path resolver(String nombre) {
        if (nombre == null || !NOMBRE_VALIDO.matcher(nombre).matches()) {
            throw new IllegalArgumentException(
                    "El nombre de la imagen no es válido.");
        }

        return carpeta().resolve(nombre);
    }

 
     //Elimina una imagen local por ej si falla el alta.
    public static void eliminar(String rutaImagen) throws IOException {
        if (rutaImagen == null || !rutaImagen.startsWith(PREFIJO)) {
            return;
        }

        String nombre = rutaImagen.substring(PREFIJO.length());
        Files.deleteIfExists(resolver(nombre));
    }
}
