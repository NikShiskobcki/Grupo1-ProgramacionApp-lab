package publicadores;

import jakarta.xml.ws.WebFault;

@WebFault(
        name = "CentralFault",
        targetNamespace = "http://ws.edext.utec.edu.uy/"
)
public class CentralException extends Exception {

    public CentralException(String mensaje) {
        super(mensaje);
    }
}