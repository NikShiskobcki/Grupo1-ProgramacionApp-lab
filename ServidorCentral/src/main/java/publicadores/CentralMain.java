package publicadores;

import Logica.controladores.Fabrica;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public final class CentralMain {
    private CentralMain() { }

    public static void main(String[] args) throws Exception {
        ArranqueCentral.iniciar();
        if (Arrays.asList(args).contains("--datos-prueba")) {
            Fabrica.getInstance().getIControlador().cargarDatosPrueba();
            System.out.println("Datos de prueba cargados en la base configurada de Central.");
        }
        System.out.println("Central está listo para recibir peticiones.");
        System.out.println("Para detenerlo, presioná Ctrl+C.");
        new CountDownLatch(1).await();
    }
}
