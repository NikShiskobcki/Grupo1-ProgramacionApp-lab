package Entidades;

import Logica.controladores.Controlador;
import Logica.DTO.*;
import Logica.Entidades.*;
import Logica.excepciones.*;
import Persistencia.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ControladorCasosUsoTest {

    private Controlador controlador;

    @Before
    public void setUp() {
        controlador = Controlador.getInstance();
    }

    private String generarSufijo() {
        return "_" + System.nanoTime();
    }

    @Test
    public void testGestionCategorias() {
        String catNom = "Cat" + generarSufijo();
        assertFalse(controlador.existeCategoria(catNom));

        controlador.altaCategoria(catNom);
        assertTrue(controlador.existeCategoria(catNom));
        assertTrue(controlador.listarNombresCategorias().contains(catNom));

        // Intento de alta duplicada debe lanzar excepción
        try {
            controlador.altaCategoria(catNom);
            fail("Debía lanzar excepción por categoría duplicada");
        } catch (NombreDuplicadoException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testAltaCursoConCategoriasYPrevias() {
        String sufijo = generarSufijo();
        String instNom = "Inst" + sufijo;
        controlador.altaInstituto(instNom);
        Instituto inst = new Instituto(instNom);

        String cat1 = "CatA" + sufijo;
        String cat2 = "CatB" + sufijo;
        controlador.altaCategoria(cat1);
        controlador.altaCategoria(cat2);

        String cursoPrevia = "CursoPrevia" + sufijo;
        controlador.altaCurso(cursoPrevia, "Previa desc", 2, 20, 2, "http://previa.uy", LocalDate.now(), inst, Collections.emptyList(), null, null);

        Curso previaObj = new Curso(cursoPrevia, "Previa desc", 2, 20, 2, "http://previa.uy", LocalDate.now(), inst);

        String cursoPrincipal = "CursoPrinc" + sufijo;
        List<String> categorias = Arrays.asList(cat1, cat2, "CatInexistente" + sufijo);
        controlador.altaCurso(
                cursoPrincipal, "Princ desc", 4, 40, 5, "http://princ.uy",
                LocalDate.now(), inst, Collections.singletonList(previaObj),
                categorias, "img/princ.png"
        );

        assertTrue(controlador.existeCurso(cursoPrincipal));
        DetalleCurso detalle = controlador.consultarCurso(cursoPrincipal);
        assertNotNull(detalle);
        assertEquals("img/princ.png", detalle.getRutaImagen());
        assertTrue(detalle.getCategorias().contains(cat1));
        assertTrue(detalle.getCategorias().contains(cat2));
        assertFalse(detalle.getCategorias().contains("CatInexistente" + sufijo));
        assertEquals(1, detalle.getPrevias().size());
    }

    @Test
    public void testAutenticacionUsuarios() {
        String sufijo = generarSufijo();
        String nick = "usr_auth" + sufijo;
        String email = "auth" + sufijo + "@test.uy";
        String clave = "secreta123";

        controlador.altaUsuarioEstudiante(nick, "Nom", "Ape", email, clave, LocalDate.of(2001, 1, 1), "img/avatar.png");

        // Login con nickname exitoso
        UsuarioResumen resNick = controlador.iniciarSesion(nick, clave);
        assertNotNull(resNick);
        assertEquals(nick, resNick.getNickname());
        assertEquals("Estudiante", resNick.getTipo());

        // Login con email exitoso
        UsuarioResumen resEmail = controlador.iniciarSesion(email, clave);
        assertNotNull(resEmail);
        assertEquals(nick, resEmail.getNickname());

        // Login con contraseña incorrecta
        UsuarioResumen resClaveInvalida = controlador.iniciarSesion(nick, "clave_incorrecta");
        assertNull(resClaveInvalida);

        // Login con usuario inexistente
        UsuarioResumen resInexistente = controlador.iniciarSesion("inexistente" + sufijo, clave);
        assertNull(resInexistente);
    }

    @Test
    public void testInscripcionEdicionValidaciones() {
        String sufijo = generarSufijo();
        String instNom = "InstVal" + sufijo;
        controlador.altaInstituto(instNom);
        Instituto inst = new Instituto(instNom);

        String docNick = "docVal" + sufijo;
        controlador.altaUsuarioDocente(docNick, "Doc", "Val", docNick + "@test.uy", "clave", LocalDate.of(1985, 2, 2), instNom, null);
        List<Docente> docentes = controlador.listarDocentesPorInstituto(instNom);

        String cursoNom = "CursoVal" + sufijo;
        controlador.altaCurso(cursoNom, "Desc", 3, 30, 3, "http://val.uy", LocalDate.now(), inst, Collections.emptyList(), null, null);

        // Edición vigente (inicia ayer, termina en dos meses)
        LocalDate hoy = LocalDate.now(java.time.ZoneId.of("America/Montevideo"));
        String edVigente = "EdVigente" + sufijo;
        controlador.altaEdicionCurso(edVigente, hoy.minusDays(2), hoy.plusMonths(2), 10, cursoNom, docentes, "img/ed.png");

        // Edición vencida (terminó ayer)
        String edVencida = "EdVencida" + sufijo;
        controlador.altaEdicionCurso(edVencida, hoy.minusMonths(2), hoy.minusDays(1), 10, cursoNom, docentes, null);

        // Edición futura (inicia mañana)
        String edFutura = "EdFutura" + sufijo;
        controlador.altaEdicionCurso(edFutura, hoy.plusDays(1), hoy.plusMonths(2), 10, cursoNom, docentes, null);

        String estNick = "estVal" + sufijo;
        controlador.altaUsuarioEstudiante(estNick, "Est", "Val", estNick + "@test.uy", "clave", LocalDate.of(2003, 3, 3), null);

        // 1. Estudiante inexistente
        try {
            controlador.inscribirEstudianteEdicion("estFantasma" + sufijo, edVigente, hoy);
            fail("Debía fallar con estudiante inexistente");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("estudiante no existe"));
        }

        // 2. Edición inexistente
        try {
            controlador.inscribirEstudianteEdicion(estNick, "edFantasma" + sufijo, hoy);
            fail("Debía fallar con edición inexistente");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("edición no existe"));
        }

        // 3. Edición vencida
        try {
            controlador.inscribirEstudianteEdicion(estNick, edVencida, hoy);
            fail("Debía fallar con edición vencida");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("vigente"));
        }

        // 4. Edición futura
        try {
            controlador.inscribirEstudianteEdicion(estNick, edFutura, hoy);
            fail("Debía fallar con edición futura");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("vigente"));
        }

        // 5. Fecha de inscripción nula
        try {
            controlador.inscribirEstudianteEdicion(estNick, edVigente, null);
            fail("Debía fallar con fecha nula");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("fecha de inscripción"));
        }

        // 6. Inscripción exitosa
        controlador.inscribirEstudianteEdicion(estNick, edVigente, hoy);
        InscripcionEdicion insc = controlador.buscarInscripcionEdicion(estNick, edVigente);
        assertNotNull(insc);
        assertEquals(EstadoInscripcion.INSCRIPTO, insc.getEstado());
    }

    @Test
    public void testInscripcionProgramaYListados() {
        String sufijo = generarSufijo();
        String progNom = "Prog" + sufijo;
        String docNick = "docProg" + sufijo;

        String instNom = "InstProg" + sufijo;
        controlador.altaInstituto(instNom);
        controlador.altaUsuarioDocente(docNick, "Doc", "Prog", docNick + "@test.uy", "clave", LocalDate.of(1980, 1, 1), instNom, null);

        controlador.altaPrograma(
                progNom, "Desc prog", LocalDate.now(), LocalDate.now().plusMonths(3),
                LocalDate.now(), "img/prog.png", docNick
        );

        List<ElementoResumen> progCreados = controlador.listarProgramasCreados(docNick);
        assertFalse(progCreados.isEmpty());
        assertTrue(progCreados.stream().anyMatch(e -> e.getNombre().equals(progNom)));

        String estNick = "estProg" + sufijo;
        controlador.altaUsuarioEstudiante(estNick, "Est", "Prog", estNick + "@test.uy", "clave", LocalDate.of(2002, 5, 5), null);

        assertNull(controlador.buscarInscripcionPrograma(estNick, progNom));

        controlador.inscribirEstudiantePrograma(estNick, progNom, LocalDate.now());
        InscripcionPrograma inscProg = controlador.buscarInscripcionPrograma(estNick, progNom);
        assertNotNull(inscProg);
        assertEquals(estNick, inscProg.getEstudiante().getNickname());
        assertEquals(progNom, inscProg.getPrograma().getNombre());
    }

    @Test
    public void testSeleccionEstudiantesYRanking() {
        String sufijo = generarSufijo();
        String instNom = "InstSel" + sufijo;
        controlador.altaInstituto(instNom);
        Instituto inst = new Instituto(instNom);

        String docNick = "docSel" + sufijo;
        controlador.altaUsuarioDocente(docNick, "Doc", "Sel", docNick + "@test.uy", "clave", LocalDate.of(1975, 4, 4), instNom, null);
        List<Docente> docentes = controlador.listarDocentesPorInstituto(instNom);

        String cursoNom = "CursoSel" + sufijo;
        controlador.altaCurso(cursoNom, "Desc", 4, 40, 5, "http://sel.uy", LocalDate.now(), inst, Collections.emptyList(), null, null);

        LocalDate hoy = LocalDate.now(java.time.ZoneId.of("America/Montevideo"));
        String edNom = "EdSel" + sufijo;
        controlador.altaEdicionCurso(edNom, hoy.minusDays(5), hoy.plusMonths(1), 5, cursoNom, docentes, null);

        // Crear 3 estudiantes e inscribirlos
        String e1 = "estSel1" + sufijo;
        String e2 = "estSel2" + sufijo;
        String e3 = "estSel3" + sufijo;
        controlador.altaUsuarioEstudiante(e1, "E1", "Ape1", e1 + "@test.uy", "c", LocalDate.of(2001, 1, 1), null);
        controlador.altaUsuarioEstudiante(e2, "E2", "Ape2", e2 + "@test.uy", "c", LocalDate.of(2001, 1, 1), null);
        controlador.altaUsuarioEstudiante(e3, "E3", "Ape3", e3 + "@test.uy", "c", LocalDate.of(2001, 1, 1), null);

        controlador.inscribirEstudianteEdicion(e1, edNom, hoy.minusDays(2));
        controlador.inscribirEstudianteEdicion(e2, edNom, hoy.minusDays(1));
        controlador.inscribirEstudianteEdicion(e3, edNom, hoy);

        InscripcionEdicion ins1 = controlador.buscarInscripcionEdicion(e1, edNom);
        InscripcionEdicion ins2 = controlador.buscarInscripcionEdicion(e2, edNom);
        InscripcionEdicion ins3 = controlador.buscarInscripcionEdicion(e3, edNom);

        assertNotNull(ins1);
        assertNotNull(ins2);
        assertNotNull(ins3);

        // Probar seleccionarEstudiante individual
        controlador.seleccionarEstudiante(ins1.getId(), true);
        controlador.seleccionarEstudiante(ins2.getId(), false);

        List<InscripcionResumen> aceptados = controlador.listarAceptadosPorEdicion(edNom);
        assertEquals(1, aceptados.size());
        assertEquals(e1, aceptados.get(0).getNicknameEstudiante());

        List<ResultadoInscripcion> resE1 = controlador.listarResultadosPorEstudiante(e1);
        assertFalse(resE1.isEmpty());
        assertEquals("ACEPTADA", resE1.get(0).getEstado());

        List<ResultadoInscripcion> resE2 = controlador.listarResultadosPorEstudiante(e2);
        assertFalse(resE2.isEmpty());
        assertEquals("RECHAZADA", resE2.get(0).getEstado());

        // Probar listar con orden por prioridad y por fecha
        List<InscripcionResumen> porFecha = controlador.listarInscripcionesPorEdicion(edNom, false);
        assertEquals(3, porFecha.size());

        List<InscripcionResumen> porPrioridad = controlador.listarInscripcionesPorEdicion(edNom, true);
        assertEquals(3, porPrioridad.size());
    }

    @Test
    public void testSeleccionarEstudiantesEdicionValidaciones() {
        String sufijo = generarSufijo();
        String instNom = "InstValSel" + sufijo;
        controlador.altaInstituto(instNom);
        Instituto inst = new Instituto(instNom);

        String docNick = "docValSel" + sufijo;
        controlador.altaUsuarioDocente(docNick, "Doc", "ValSel", docNick + "@test.uy", "clave", LocalDate.of(1975, 4, 4), instNom, null);
        List<Docente> docentes = controlador.listarDocentesPorInstituto(instNom);

        String docAjeno = "docAjeno" + sufijo;
        controlador.altaUsuarioDocente(docAjeno, "Doc", "Ajeno", docAjeno + "@test.uy", "clave", LocalDate.of(1975, 4, 4), instNom, null);

        String cursoNom = "CursoValSel" + sufijo;
        controlador.altaCurso(cursoNom, "Desc", 4, 40, 5, "http://valsel.uy", LocalDate.now(), inst, Collections.emptyList(), null, null);

        LocalDate hoy = LocalDate.now(java.time.ZoneId.of("America/Montevideo"));
        String edNom = "EdValSel" + sufijo;
        // Cupo de 1
        controlador.altaEdicionCurso(edNom, hoy.minusDays(5), hoy.plusMonths(1), 1, cursoNom, docentes, null);

        String e1 = "estValSel1" + sufijo;
        String e2 = "estValSel2" + sufijo;
        controlador.altaUsuarioEstudiante(e1, "E1", "A1", e1 + "@test.uy", "c", LocalDate.of(2001, 1, 1), null);
        controlador.altaUsuarioEstudiante(e2, "E2", "A2", e2 + "@test.uy", "c", LocalDate.of(2001, 1, 1), null);

        controlador.inscribirEstudianteEdicion(e1, edNom, hoy.minusDays(1));
        controlador.inscribirEstudianteEdicion(e2, edNom, hoy);

        InscripcionEdicion ins1 = controlador.buscarInscripcionEdicion(e1, edNom);
        InscripcionEdicion ins2 = controlador.buscarInscripcionEdicion(e2, edNom);

        // 1. Docente nulo o vacío
        try {
            controlador.seleccionarEstudiantesEdicion(null, edNom, Collections.singletonList(ins1.getId()), Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("docente"));
        }

        // 2. Edición nula o vacía
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, "", Collections.singletonList(ins1.getId()), Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("edición"));
        }

        // 3. Listas nulas
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, edNom, null, Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("nulas"));
        }

        // 4. Listas vacías (ningún id)
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, edNom, Collections.emptyList(), Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("al menos una"));
        }

        // 5. ID inválido (negativo o nulo)
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, edNom, Collections.singletonList(-1L), Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("inválidas o repetidas"));
        }

        // 6. ID repetido en aceptadas
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, edNom, Arrays.asList(ins1.getId(), ins1.getId()), Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("inválidas o repetidas"));
        }

        // 7. Mismo ID en aceptadas y rechazadas
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, edNom, Collections.singletonList(ins1.getId()), Collections.singletonList(ins1.getId()));
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("aceptada y rechazada a la vez"));
        }

        // 8. Docente que no participa en la edición
        try {
            controlador.seleccionarEstudiantesEdicion(docAjeno, edNom, Collections.singletonList(ins1.getId()), Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("docente participante"));
        }

        // 9. Edición inexistente en DB
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, "EdFantasma" + sufijo, Collections.singletonList(ins1.getId()), Collections.emptyList());
            fail("Debía fallar");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("no existe"));
        }

        // 10. Superar el cupo (cupo es 1, intentamos aceptar 2)
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, edNom, Arrays.asList(ins1.getId(), ins2.getId()), Collections.emptyList());
            fail("Debía fallar por superar el cupo");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("supera el cupo"));
        }

        // 11. Selección válida (aceptar 1 y rechazar 1)
        controlador.seleccionarEstudiantesEdicion(docNick, edNom, Collections.singletonList(ins1.getId()), Collections.singletonList(ins2.getId()));

        InscripcionEdicion ins1Mod = controlador.buscarInscripcionEdicion(e1, edNom);
        assertNotNull(ins1Mod);
        assertEquals(EstadoInscripcion.ACEPTADA, ins1Mod.getEstado());

        // 12. Intentar seleccionar una inscripción que ya fue resuelta
        try {
            controlador.seleccionarEstudiantesEdicion(docNick, edNom, Collections.singletonList(ins1.getId()), Collections.emptyList());
            fail("Debía fallar porque ya está resuelta");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("ya fue resuelta"));
        }
    }

    @Test
    public void testConsultasUsuarioPorDistintosPerfiles() {
        String sufijo = generarSufijo();
        String instNom = "InstCons" + sufijo;
        controlador.altaInstituto(instNom);
        Instituto inst = new Instituto(instNom);

        String docNick = "docCons" + sufijo;
        controlador.altaUsuarioDocente(docNick, "Doc", "Cons", docNick + "@test.uy", "clave", LocalDate.of(1980, 1, 1), instNom, "img/doc.png");
        List<Docente> docentes = controlador.listarDocentesPorInstituto(instNom);

        String cursoNom = "CursoCons" + sufijo;
        controlador.altaCurso(cursoNom, "Desc", 4, 40, 5, "http://c.uy", LocalDate.now(), inst, Collections.emptyList(), null, null);

        LocalDate hoy = LocalDate.now(java.time.ZoneId.of("America/Montevideo"));
        String edNom = "EdCons" + sufijo;
        controlador.altaEdicionCurso(edNom, hoy.minusDays(5), hoy.plusMonths(1), 10, cursoNom, docentes, null);

        String estNick = "estCons" + sufijo;
        controlador.altaUsuarioEstudiante(estNick, "Est", "Cons", estNick + "@test.uy", "clave", LocalDate.of(2001, 1, 1), "img/est.png");

        controlador.inscribirEstudianteEdicion(estNick, edNom, hoy.minusDays(1));
        InscripcionEdicion insc = controlador.buscarInscripcionEdicion(estNick, edNom);
        controlador.seleccionarEstudiante(insc.getId(), false); // Se rechaza

        // 1. Visitante consulta estudiante
        DetalleUsuario detVis = controlador.consultarUsuario(estNick, null);
        assertNotNull(detVis);
        assertTrue(detVis.getInscripcionesRechazadas().isEmpty());

        // 2. Estudiante ajeno consulta estudiante
        DetalleUsuario detAjeno = controlador.consultarUsuario(estNick, "otro_est" + sufijo);
        assertNotNull(detAjeno);
        assertTrue(detAjeno.getInscripcionesRechazadas().isEmpty());

        // 3. Mismo estudiante consulta su propio perfil -> ve rechazadas
        DetalleUsuario detPropio = controlador.consultarUsuario(estNick, estNick);
        assertNotNull(detPropio);
        assertEquals(1, detPropio.getInscripcionesRechazadas().size());

        // 4. Consulta de Docente por visitante y por sí mismo
        DetalleUsuario detDocVis = controlador.consultarUsuario(docNick, null);
        assertNotNull(detDocVis);
        assertEquals(instNom, detDocVis.getInstituto());

        DetalleUsuario detDocPropio = controlador.consultarUsuario(docNick, docNick);
        assertNotNull(detDocPropio);
        assertFalse(detDocPropio.getEdiciones().isEmpty());
    }

    @Test
    public void testModificarUsuarioDocente() {
        String sufijo = generarSufijo();
        String instNom1 = "InstMod1" + sufijo;
        String instNom2 = "InstMod2" + sufijo;
        controlador.altaInstituto(instNom1);
        controlador.altaInstituto(instNom2);

        String docNick = "docMod" + sufijo;
        controlador.altaUsuarioDocente(docNick, "Doc", "Original", docNick + "@test.uy", "clave", LocalDate.of(1982, 2, 2), instNom1, null);

        UsuarioEdicion editAntes = controlador.buscarUsuarioParaEditar(docNick);
        assertEquals("Doc", editAntes.getNombre());
        assertEquals(instNom1, editAntes.getInstituto());

        controlador.modificarUsuario(docNick, "DocNuevo", "ApeNuevo", LocalDate.of(1983, 3, 3), instNom2, "img/nuevo_doc.png");

        UsuarioEdicion editDespues = controlador.buscarUsuarioParaEditar(docNick);
        assertEquals("DocNuevo", editDespues.getNombre());
        assertEquals("ApeNuevo", editDespues.getApellido());
        assertEquals(LocalDate.of(1983, 3, 3), editDespues.getFechaNacimiento());
        assertEquals(instNom2, editDespues.getInstituto());
        assertEquals("img/nuevo_doc.png", editDespues.getRutaImagen());
    }

    @Test
    public void testMetodosManejadoresAdicionales() {
        String sufijo = generarSufijo();
        String instNom = "InstExtra" + sufijo;
        controlador.altaInstituto(instNom);
        Instituto inst = new Instituto(instNom);

        String docNick = "docExtra" + sufijo;
        controlador.altaUsuarioDocente(docNick, "Doc", "Extra", docNick + "@test.uy", "clave", LocalDate.of(1980, 1, 1), instNom, null);

        String c1Nom = "CursoEx1" + sufijo;
        String c2Nom = "CursoEx2" + sufijo;
        controlador.altaCurso(c1Nom, "Desc1", 2, 20, 2, "http://1.uy", LocalDate.now(), inst, Collections.emptyList(), null, null);
        controlador.altaCurso(c2Nom, "Desc2", 2, 20, 2, "http://2.uy", LocalDate.now(), inst, Collections.emptyList(), null, null);

        // Test ManejadorCurso.agregarPrevia
        Persistencia.ManejadorCurso mCurso = new Persistencia.ManejadorCurso(javax.persistence.Persistence.createEntityManagerFactory("edextPU"));
        mCurso.agregarPrevia(c1Nom, c2Nom);
        DetalleCurso dc1 = controlador.consultarCurso(c1Nom);
        assertTrue(dc1.getPrevias().contains(c2Nom));

        // Test ManejadorEdicionCurso.agregarDocente
        LocalDate hoy = LocalDate.now(java.time.ZoneId.of("America/Montevideo"));
        String edNom = "EdExtra" + sufijo;
        controlador.altaEdicionCurso(edNom, hoy.minusDays(1), hoy.plusMonths(1), 10, c1Nom, Collections.emptyList(), null);

        Persistencia.ManejadorEdicionCurso mEdicion = new Persistencia.ManejadorEdicionCurso(javax.persistence.Persistence.createEntityManagerFactory("edextPU"));
        mEdicion.agregarDocente(edNom, docNick);
        DetalleEdicionCurso dec = controlador.consultarEdicion(edNom);
        assertTrue(dec.getDocentes().stream().anyMatch(d -> d.contains(docNick)));

        EdicionCurso ev = mEdicion.buscarEdicionVigentePorCurso(c1Nom);
        assertNotNull(ev);
        assertEquals(edNom, ev.getNombre());
    }
}
