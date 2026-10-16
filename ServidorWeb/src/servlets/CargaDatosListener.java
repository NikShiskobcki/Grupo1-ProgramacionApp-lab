package servlets;

import Logica.controladores.Fabrica;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import util.CargadorImagenesPrueba;

@WebListener
public class CargaDatosListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        try {
            CargadorImagenesPrueba.cargar();

            evento.getServletContext().log(
                    "Imágenes de prueba disponibles en el volumen.");

            Fabrica.getInstance()
                    .getIControlador()
                    .cargarDatosPrueba();

            evento.getServletContext().log(
                    "Datos de prueba cargados en la base del servidor web.");

        } catch (Exception e) {
            evento.getServletContext().log(
                    "No se pudieron cargar los datos o imágenes de prueba",
                    e);
        }
    }
}
