import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ActualizarContrato {

    private static final String XSD =
            "http://www.w3.org/2001/XMLSchema";

    private static final String WSDL =
            "http://schemas.xmlsoap.org/wsdl/";

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println(
                    "Uso: java herramientas/ActualizarContrato.java "
                    + "<URL-WSDL> <carpeta-destino>");

            System.exit(1);
        }

        URI origen = URI.create(args[0]);
        validarDireccion(origen);

        Path destino = Path.of(args[1]).toAbsolutePath().normalize();
        Path temporal = Files.createTempDirectory("edext-contrato-");

        try {
            descargarContrato(origen, temporal);

            Files.createDirectories(destino);

            // Reemplazamos los archivos después de descargar todo.
            try (var archivos = Files.list(temporal)) {
                for (Path archivo : archivos.toList()) {
                    Files.move(
                            archivo,
                            destino.resolve(archivo.getFileName()),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            }

            System.out.println("Contrato actualizado en: " + destino);
            System.out.println(
                    "Ejecutá Maven para generar las clases cliente.");

        } finally {
            try (var archivos = Files.walk(temporal)) {
                for (Path archivo : archivos
                        .sorted(Comparator.reverseOrder())
                        .toList()) {

                    Files.deleteIfExists(archivo);
                }
            }
        }
    }

    private static void descargarContrato(
            URI origen,
            Path carpeta) throws Exception {

        HttpClient cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        Map<URI, String> nombres = new LinkedHashMap<>();
        Queue<URI> pendientes = new ArrayDeque<>();

        nombres.put(origen, "central.wsdl");
        pendientes.add(origen);

        while (!pendientes.isEmpty()) {
            URI direccion = pendientes.remove();
            validarDireccion(direccion);

            HttpRequest peticion = HttpRequest.newBuilder(direccion)
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            HttpResponse<byte[]> respuesta = cliente.send(
                    peticion,
                    HttpResponse.BodyHandlers.ofByteArray());

            if (respuesta.statusCode() != 200) {
                throw new IllegalStateException(
                        "No se pudo descargar " + direccion
                        + ". HTTP " + respuesta.statusCode());
            }

            Document documento = leerXml(respuesta.body());

            Element raiz = documento.getDocumentElement();

            if (direccion.equals(origen)
                    && !(WSDL.equals(raiz.getNamespaceURI())
                    && "definitions".equals(raiz.getLocalName()))) {

                throw new IllegalStateException(
                        "La dirección indicada no devolvió un WSDL válido.");
            }

            NodeList elementos = documento.getElementsByTagName("*");

            for (int i = 0; i < elementos.getLength(); i++) {
                Element elemento = (Element) elementos.item(i);

                String atributo = atributoReferencia(elemento);

                if (atributo == null) {
                    continue;
                }

                String referencia = elemento.getAttribute(atributo);

                if (referencia.isBlank()) {
                    continue;
                }

                URI dependencia = respuesta.uri().resolve(referencia);

                validarDireccion(dependencia);

                if (!nombres.containsKey(dependencia)) {
                    String extension =
                            WSDL.equals(elemento.getNamespaceURI())
                            ? ".wsdl"
                            : ".xsd";

                    nombres.put(
                            dependencia,
                            "dependencia-" + nombres.size() + extension);

                    pendientes.add(dependencia);
                }

                // El documento ahora apunta a una copia local.
                elemento.setAttribute(
                        atributo,
                        nombres.get(dependencia));
            }

            guardarXml(
                    documento,
                    carpeta.resolve(nombres.get(direccion)));

            System.out.println(
                    "Descargado: " + nombres.get(direccion));
        }
    }

    private static String atributoReferencia(Element elemento) {
        String espacio = elemento.getNamespaceURI();
        String nombre = elemento.getLocalName();

        if (XSD.equals(espacio)
                && ("import".equals(nombre)
                || "include".equals(nombre)
                || "redefine".equals(nombre))) {

            return "schemaLocation";
        }

        if (WSDL.equals(espacio) && "import".equals(nombre)) {
            return "location";
        }

        return null;
    }

    private static Document leerXml(byte[] contenido) throws Exception {
        DocumentBuilderFactory fabrica =
                DocumentBuilderFactory.newInstance();

        fabrica.setNamespaceAware(true);

        fabrica.setFeature(
                XMLConstants.FEATURE_SECURE_PROCESSING, true);

        fabrica.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true);

        fabrica.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        fabrica.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        try (var entrada = new ByteArrayInputStream(contenido)) {
            return fabrica.newDocumentBuilder().parse(entrada);
        }
    }

    private static void guardarXml(
            Document documento,
            Path archivo) throws Exception {

        TransformerFactory fabrica =
                TransformerFactory.newInstance();

        fabrica.setFeature(
                XMLConstants.FEATURE_SECURE_PROCESSING, true);

        fabrica.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        fabrica.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        var transformador = fabrica.newTransformer();

        transformador.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformador.setOutputProperty(OutputKeys.INDENT, "yes");

        transformador.transform(
                new DOMSource(documento),
                new StreamResult(archivo.toFile()));
    }

    private static void validarDireccion(URI direccion) {
        String protocolo = direccion.getScheme();

        if (direccion.getHost() == null
                || !("http".equalsIgnoreCase(protocolo)
                || "https".equalsIgnoreCase(protocolo))) {

            throw new IllegalArgumentException(
                    "Se esperaba una dirección HTTP o HTTPS: "
                    + direccion);
        }
    }
}