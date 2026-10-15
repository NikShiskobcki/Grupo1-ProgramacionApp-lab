package servlets;

import Logica.controladores.Fabrica;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CargaDatosListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Fabrica.getInstance().getIControlador().cargarDatosPrueba();
            sce.getServletContext().log("Datos de prueba cargados en la base del servidor web.");
        } catch (Exception e) {
            sce.getServletContext().log("No se pudieron cargar los datos de prueba", e);
        }
    }
}
