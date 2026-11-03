package servlets;

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
            evento.getServletContext().log("Imágenes de prueba disponibles en el volumen.");
        } catch (Exception e) {
            evento.getServletContext().log("No se pudieron instalar las imágenes de prueba.", e);
        }
    }
}
