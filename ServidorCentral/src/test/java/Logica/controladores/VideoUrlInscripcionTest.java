package Logica.controladores;

import static org.junit.Assert.*;
import org.junit.Test;

/** Validación de la URL opcional del video de motivación (no requiere base de datos). */
public class VideoUrlInscripcionTest {

    @Test
    public void sinVideoDevuelveNull() {
        assertNull(Controlador.normalizarVideoUrl(null));
        assertNull(Controlador.normalizarVideoUrl(""));
        assertNull(Controlador.normalizarVideoUrl("   "));
    }

    @Test
    public void aceptaHttpYHttpsYRecorta() {
        assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                Controlador.normalizarVideoUrl("  https://www.youtube.com/watch?v=dQw4w9WgXcQ "));
        assertEquals("http://youtu.be/dQw4w9WgXcQ",
                Controlador.normalizarVideoUrl("http://youtu.be/dQw4w9WgXcQ"));
    }

    @Test
    public void rechazaUrlsInvalidas() {
        String[] invalidas = {"youtube.com/watch?v=1", "ftp://host/video", "javascript:alert(1)",
            "https://", "texto libre", "https://a b.com"};
        for (String url : invalidas) {
            try {
                Controlador.normalizarVideoUrl(url);
                fail("Debía rechazar: " + url);
            } catch (IllegalArgumentException e) {
                assertNotNull(e.getMessage());
            }
        }
    }

    @Test
    public void rechazaUrlDemasiadoLarga() {
        try {
            Controlador.normalizarVideoUrl("https://a.com/" + "x".repeat(500));
            fail("Debía rechazar URL de más de 500 caracteres");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("500"));
        }
    }
}
