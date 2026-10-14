package Entidades;

import Logica.controladores.Controlador;
import Logica.DTO.UsuarioResumen;
import Logica.excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.assertEquals;
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

    @Test
    public void testListarSeguidosYSeguidores() {
        String a = nuevoEstudiante("lst_a");
        String b = nuevoEstudiante("lst_b");
        String c = nuevoEstudiante("lst_c");

        assertTrue(controlador.listarSeguidos(a).isEmpty());
        assertTrue(controlador.listarSeguidores(a).isEmpty());

        controlador.seguirUsuario(a, b);
        controlador.seguirUsuario(a, c);
        controlador.seguirUsuario(b, a);

        List<UsuarioResumen> seguidosDeA = controlador.listarSeguidos(a);
        assertEquals(2, seguidosDeA.size());
        assertEquals(b, seguidosDeA.get(0).getNickname());
        assertEquals(c, seguidosDeA.get(1).getNickname());
        assertEquals("Estudiante", seguidosDeA.get(0).getTipo());

        List<UsuarioResumen> seguidoresDeA = controlador.listarSeguidores(a);
        assertEquals(1, seguidoresDeA.size());
        assertEquals(b, seguidoresDeA.get(0).getNickname());

        assertEquals(1, controlador.listarSeguidores(c).size());
        assertTrue(controlador.listarSeguidos(c).isEmpty());

        controlador.dejarDeSeguirUsuario(a, b);
        assertEquals(1, controlador.listarSeguidos(a).size());
        assertTrue(controlador.listarSeguidores(b).isEmpty());
    }

    @Test
    public void testCambiarContrasenia() {
        String a = nuevoEstudiante("pwd");

        // Contraseña actual incorrecta: no cambia
        assertFalse(controlador.cambiarContrasenia(a, "otra", "nueva"));
        assertTrue(controlador.iniciarSesion(a, "clave") != null);

        // Contraseña actual correcta: cambia
        assertTrue(controlador.cambiarContrasenia(a, "clave", "nueva"));
        assertTrue(controlador.iniciarSesion(a, "clave") == null);
        assertTrue(controlador.iniciarSesion(a, "nueva") != null);
    }

    @Test
    public void testCambiarContraseniaUsuarioInexistente() {
        try {
            controlador.cambiarContrasenia("fantasma_" + System.nanoTime(), "x", "y");
            fail("Debía lanzar excepción con un usuario inexistente");
        } catch (PersistenciaException e) {
            assertTrue(e.getMessage().contains("no existe"));
        }
    }
}
