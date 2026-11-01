package util;

import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPFaultException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Manejo común de fallos SOAP para los servlets de Web. */
public abstract class ServletRemoto extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            super.service(request, response);
        } catch (SOAPFaultException e) {
            log("Central devolvió un error SOAP.", e);
            responder(request, response, 500,
                    "No se pudo completar la operación en Central.", e);
        } catch (WebServiceException e) {
            log("No fue posible comunicarse con Central.", e);
            responder(request, response, 503,
                    "No fue posible comunicarse con Central. Si estabas guardando datos, verificá el resultado antes de volver a enviarlos.", e);
        }
    }

    private void responder(HttpServletRequest request, HttpServletResponse response,
            int estado, String mensaje, Exception causa) throws ServletException, IOException {
        if (response.isCommitted()) throw new ServletException(mensaje, causa);
        response.resetBuffer();
        response.setHeader("Cache-Control", "no-store");
        String acepta = request.getHeader("Accept");
        boolean json = acepta != null && acepta.contains("application/json");
        if (json) {
            response.setStatus(estado);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\":\"" + mensaje + "\"}");
        } else {
            response.sendError(estado, mensaje);
        }
    }
}
