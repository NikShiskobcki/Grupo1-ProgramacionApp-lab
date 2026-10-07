package Entidades;

import Logica.controladores.Controlador;
import Logica.excepciones.PersistenciaException;
import java.time.LocalDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class SeguirUsuarioTest {

    private Controlador controlador;

    @Before
    public void setUp() {
        controlador = Controlador.getInstance();
    }

    private String nuevoEstudiante(String prefijo) {
        String nick = prefijo + "_" + System.nanoTime();
        controlador.altaUsuarioEstudiante(nick, "Nombre", "Apellido",
                nick + "@test.uy", "clave", LocalDate.of(2000, 1, 1), null);
        return nick;
    }

    @Test
    public void testSeguirYDejarDeSeguir() {
        String a = nuevoEstudiante("seg_a");
        String b = nuevoEstudiante("seg_b");

        assertFalse(controlador.sigueAUsuario(a, b));

        controlador.seguirUsuario(a, b);
        assertTrue(controlador.sigueAUsuario(a, b));
        // La relación es unidireccional
        assertFalse(controlador.sigueAUsuario(b, a));

        // Seguir dos veces no duplica ni falla
        controlador.seguirUsuario(a, b);
        assertTrue(controlador.sigueAUsuario(a, b));

        controlador.dejarDeSeguirUsuario(a, b);
        assertFalse(controlador.sigueAUsuario(a, b));

        // Dejar de seguir a quien no se sigue no falla
        controlador.dejarDeSeguirUsuario(a, b);
        assertFalse(controlador.sigueAUsuario(a, b));
    }

    @Test
    public void testNoSePuedeSeguirASiMismo() {
        String a = nuevoEstudiante("seg_self");
        try {
            controlador.seguirUsuario(a, a);
            fail("Debía lanzar excepción al seguirse a sí mismo");
        } catch (PersistenciaException e) {
            assertTrue(e.getMessage().contains("sí mismo"));
        }
        assertFalse(controlador.sigueAUsuario(a, a));
    }

    @Test
    public void testSeguirUsuarioInexistente() {
        String a = nuevoEstudiante("seg_inex");
        String fantasma = "fantasma_" + System.nanoTime();
        controlador.seguirUsuario(a, fantasma);
        assertFalse(controlador.sigueAUsuario(a, fantasma));
        controlador.dejarDeSeguirUsuario(fantasma, a);
        assertFalse(controlador.sigueAUsuario(fantasma, a));
    }
}
