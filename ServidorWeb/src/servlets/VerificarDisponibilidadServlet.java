package servlets;

import util.ServletRemoto;
import util.ClienteCentral;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Requerimiento Especial 7.5 (Validación de Datos): verificación en segundo plano
 * (AJAX) de disponibilidad de nickname/email durante el Alta de Usuario, mientras
 * el usuario escribe en el formulario de registro.
 *
 * GET /verificar-disponibilidad?campo=nickname|email&valor=texto
 * Responde JSON: {"disponible":true|false}
 * Un valor vacío, o un campo desconocido, se considera "disponible" (no hay nada
 * que objetar todavía); el servlet de alta (AltaUsuarioServlet) sigue siendo quien
 * valida definitivamente al enviar el formulario.
 */
@WebServlet("/verificar-disponibilidad")
public class VerificarDisponibilidadServlet extends ServletRemoto {

    @Override
    public void init() throws ServletException {
        super.init();
        control = new ClienteCentral(getServletContext());
    }

    private ClienteCentral control;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");

        String campo = request.getParameter("campo");
        String valor = request.getParameter("valor");
        valor = (valor == null) ? "" : valor.trim();

        boolean disponible = true;
        try {
            if (!valor.isEmpty() && "nickname".equalsIgnoreCase(campo)) {
                disponible = !control.existeNickname(valor);
            } else if (!valor.isEmpty() && "email".equalsIgnoreCase(campo)) {
                disponible = !control.existeEmail(valor);
            }
        } catch (Exception e) {
            getServletContext().log("Error al verificar disponibilidad de " + campo, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\":true}");
            return;
        }

        PrintWriter out = response.getWriter();
        out.print("{\"disponible\":" + disponible + "}");
    }
}
