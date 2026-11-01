package publicadores;

import Logica.controladores.Controlador;
import configuracion.ConfiguracionCentral;
import jakarta.xml.ws.Endpoint;

public final class ArranqueCentral {

    private static Endpoint servicio;

    private ArranqueCentral() {
    }

    public static synchronized void iniciar() throws Exception {
        if (servicio != null) {
            return;
        }

        // Lee la configuración del archivo externo.
        ConfiguracionCentral configuracion =
                ConfiguracionCentral.cargar();

        // Prepara el controlador y su persistencia.
        Controlador.iniciarCentral(
                configuracion.propiedadesPersistencia());

        // Publica las operaciones implementadas en CentralWS.
        servicio = Endpoint.publish(
                configuracion.urlPublicacion(),
                new CentralWS());

        // Detiene el servicio cuando termina el programa Java.
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> servicio.stop()));

        System.out.println(
                "Servicio SOAP publicado en: "
                + configuracion.urlPublicacion());
    }
}