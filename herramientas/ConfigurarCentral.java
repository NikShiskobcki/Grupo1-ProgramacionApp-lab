import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.net.URI;
import java.util.Arrays;
import java.util.Properties;

public class ConfigurarCentral {

    private static final Console CONSOLA = System.console();

    private static final BufferedReader ENTRADA =
            new BufferedReader(
                    new InputStreamReader(
                            System.in,
                            StandardCharsets.UTF_8));

    public static void main(String[] args) throws Exception {
        Path carpeta = Path.of(
                System.getProperty("user.home"),
                ".edEXT");

        Path archivo = carpeta.resolve("central.properties");

        if (Files.exists(archivo)) {
            System.out.println(
                    "La configuración ya existe y se conserva:");
            System.out.println(archivo);
            return;
        }

        System.out.println("Configuración inicial de Servidor Central.");
        System.out.println(
                "Presioná Enter para aceptar un valor entre corchetes.");

        String publicacion = preguntar(
                "URL de publicación SOAP",
                "http://0.0.0.0:9000/edext/central");

        URI direccion = URI.create(publicacion);

        if (direccion.getHost() == null
                || !("http".equalsIgnoreCase(direccion.getScheme())
                || "https".equalsIgnoreCase(direccion.getScheme()))) {

            throw new IllegalArgumentException(
                    "La URL de publicación debe ser HTTP o HTTPS.");
        }

        String baseDatos = preguntar(
                "URL JDBC de MariaDB",
                "jdbc:mariadb://localhost:3306/edext_web");

        String usuario = preguntar(
                "Usuario de MariaDB",
                "admin");

        String contrasenia = pedirContrasenia();

        if (contrasenia.isEmpty()) {
            throw new IllegalArgumentException(
                    "La contraseña no puede quedar vacía.");
        }

        Properties propiedades = new Properties();

        propiedades.setProperty(
                "central.publicacion",
                publicacion);

        propiedades.setProperty(
                "javax.persistence.jdbc.url",
                baseDatos);

        propiedades.setProperty(
                "javax.persistence.jdbc.user",
                usuario);

        propiedades.setProperty(
                "javax.persistence.jdbc.password",
                contrasenia);

        propiedades.setProperty(
                "javax.persistence.jdbc.driver",
                "org.mariadb.jdbc.Driver");

        Files.createDirectories(carpeta);

        try (var escritor = Files.newBufferedWriter(
                archivo,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE)) {

            propiedades.store(
                    escritor,
                    "Configuración de Servidor Central");
        }

        System.out.println("Configuración creada en:");
        System.out.println(archivo);
        System.out.println(
                "Central leerá este archivo en su próximo arranque.");
    }

    private static String preguntar(
            String mensaje,
            String valorPredeterminado) throws Exception {

        String texto = mensaje + " [" + valorPredeterminado + "]: ";

        String respuesta;

        if (CONSOLA != null) {
            respuesta = CONSOLA.readLine("%s", texto);
        } else {
            System.out.print(texto);
            respuesta = ENTRADA.readLine();
        }

        if (respuesta == null) {
            throw new IllegalStateException(
                    "Se interrumpió la entrada de datos.");
        }

        return respuesta.isBlank()
                ? valorPredeterminado
                : respuesta.trim();
    }

    private static String pedirContrasenia() throws Exception {
        if (CONSOLA != null) {
            char[] caracteres =
                    CONSOLA.readPassword("Contraseña de MariaDB: ");

            if (caracteres == null) {
                throw new IllegalStateException(
                        "Se interrumpió la entrada de datos.");
            }

            try {
                return new String(caracteres);
            } finally {
                Arrays.fill(caracteres, '\0');
            }
        }

        System.out.print("Contraseña de MariaDB: ");
        String respuesta = ENTRADA.readLine();

        if (respuesta == null) {
            throw new IllegalStateException(
                    "Se interrumpió la entrada de datos.");
        }

        return respuesta;
    }
}