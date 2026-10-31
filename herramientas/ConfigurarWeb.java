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
                ".edEXT"
        );

        Path archivo = carpeta.resolve("web.properties");

        if (Files.exists(archivo)) {
            System.out.println(
                    "La configuración ya existe y se conserva:");
            System.out.println(archivo);
            return;
        }

        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(
                        System.in,
                        StandardCharsets.UTF_8)
        );

        System.out.println("Configuración inicial de Servidor Web.");
        System.out.println(
                "Ingresá la dirección de Central accesible desde Tomcat.");

        String direccion = preguntar(
                entrada,
                "URL SOAP de Central, sin ?wsdl");

        URI uri = URI.create(direccion);

        if (uri.getHost() == null
                || !("http".equalsIgnoreCase(uri.getScheme())
                || "https".equalsIgnoreCase(uri.getScheme()))
                || "0.0.0.0".equals(uri.getHost())
                || uri.getRawQuery() != null
                || uri.getRawFragment() != null
                || uri.getRawUserInfo() != null) {

            throw new IllegalArgumentException(
                    "Ingresá una URL HTTP o HTTPS válida del servicio, "
                    + "sin ?wsdl ni credenciales. No uses 0.0.0.0.");
        }

        String imagenes = preguntar(
                entrada,
                "Carpeta absoluta de imágenes en el entorno de Tomcat");

        Properties propiedades = new Properties();

        propiedades.setProperty("central.ws.url", direccion);
        propiedades.setProperty("imagenes.dir", imagenes);

        Files.createDirectories(carpeta);

        try (var escritor = Files.newBufferedWriter(
                archivo,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE)) {

            propiedades.store(
                    escritor,
                    "Configuración de Servidor Web");
        }

        System.out.println("Configuración creada en:");
        System.out.println(archivo);
        System.out.println(
                "Para cambiarla, editá este archivo y reiniciá Web.");
    }

    private static String preguntar(
            BufferedReader entrada,
            String mensaje) throws Exception {

        System.out.print(mensaje + ": ");

        String respuesta = entrada.readLine();

        if (respuesta == null || respuesta.isBlank()) {
            throw new IllegalArgumentException(
                    "El valor no puede quedar vacío.");
        }

        return respuesta.trim();
    }
}