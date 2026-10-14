package servlets;

import util.Imagenes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/imagenes/*")
public class ImagenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        String ruta = request.getPathInfo();

        if (ruta == null || ruta.length() <= 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String nombre = ruta.substring(1);
        Path archivo;

        try {
            archivo = Imagenes.resolver(nombre);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!Files.isRegularFile(archivo) || !Files.isReadable(archivo)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String tipo = nombre.endsWith(".png")
                ? "image/png" : "image/jpeg";

        response.setContentType(tipo);
        response.setContentLengthLong(Files.size(archivo));

        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader(
                "Content-Disposition",
                "inline; filename=\"" + nombre + "\""
        );

        response.setHeader("Cache-Control", "private, max-age=3600");

        try (InputStream entrada = Files.newInputStream(archivo)) {
            entrada.transferTo(response.getOutputStream());
        }
    }
}