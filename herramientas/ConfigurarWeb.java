import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class ConfigurarWeb {

    public static void main(String[] args) throws Exception {
        Path carpeta = Path.of(
                System.getProperty("user.home"),
                ".edEXT");

        Path archivo = carpeta.resolve("web.properties");
        Properties propiedades = new Properties();

        if (Files.exists(archivo)) {
            try (var lector = Files.newBufferedReader(
                    archivo, StandardCharsets.UTF_8)) {

                propiedades.load(lector);
            }
        }

        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(
                        System.in, StandardCharsets.UTF_8));

        boolean cambio = false;

        if (falta(propiedades, "central.ws.url")) {
            String valor = preguntar(
                    entrada,
                    "URL de Central accesible desde Tomcat",
                    "http://host.docker.internal:9000/edext/central");

            validarUrl(valor, false);
            propiedades.setProperty("central.ws.url", valor);
            cambio = true;
        }

        if (falta(propiedades, "central.wsdl.url")) {
            String valor = preguntar(
                    entrada,
                    "URL del WSDL accesible desde Maven",
                    "http://localhost:9000/edext/central?wsdl");

            validarUrl(valor, true);
            propiedades.setProperty("central.wsdl.url", valor);
            cambio = true;
        }

        if (falta(propiedades, "imagenes.dir")) {
            String valor = preguntar(
                    entrada,
                    "Carpeta de imágenes en el entorno de Tomcat",
                    "/var/lib/edext/imagenes");

            propiedades.setProperty("imagenes.dir", valor);
            cambio = true;
        }

        if (!cambio) {
            System.out.println(
                    "La configuración está completa y se conserva:");
            System.out.println(archivo);
            return;
        }

        Files.createDirectories(carpeta);

        try (var escritor = Files.newBufferedWriter(
                archivo,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            propiedades.store(
                    escritor,
                    "Configuración de Servidor Web");
        }

        System.out.println("Configuración guardada en:");
        System.out.println(archivo);
    }

    private static boolean falta(
            Properties propiedades,
            String clave) {

        String valor = propiedades.getProperty(clave);

        return valor == null || valor.isBlank();
    }

    private static String preguntar(
            BufferedReader entrada,
            String mensaje,
            String valorPredeterminado) throws Exception {

        System.out.print(
                mensaje + " [" + valorPredeterminado + "]: ");

        String respuesta = entrada.readLine();

        if (respuesta == null) {
            throw new IllegalStateException(
                    "Se interrumpió la entrada de datos.");
        }

        return respuesta.isBlank()
                ? valorPredeterminado
                : respuesta.trim();
    }

    private static void validarUrl(
            String valor,
            boolean contrato) {

        URI uri = URI.create(valor);

        if (uri.getHost() == null
                || !("http".equalsIgnoreCase(uri.getScheme())
                || "https".equalsIgnoreCase(uri.getScheme()))
                || "0.0.0.0".equals(uri.getHost())
                || uri.getRawUserInfo() != null
                || uri.getRawFragment() != null) {

            throw new IllegalArgumentException(
                    "La dirección HTTP o HTTPS no es válida.");
        }

        if (contrato) {
            if (!"wsdl".equalsIgnoreCase(uri.getRawQuery())) {
                throw new IllegalArgumentException(
                        "La dirección del contrato debe terminar en ?wsdl.");
            }

        } else if (uri.getRawQuery() != null) {
            throw new IllegalArgumentException(
                    "La dirección del servicio no debe incluir ?wsdl.");
        }
    }
}