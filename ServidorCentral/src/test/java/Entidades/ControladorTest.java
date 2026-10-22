package Entidades;

import Logica.controladores.Controlador;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import Logica.DTO.DetalleCurso;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.Instituto;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class ControladorTest {

    private Controlador controlador;

    @Before
    public void setUp() {
        controlador = Controlador.getInstance();
    }

    @Test
    public void testCargaDatosPruebaDoble() {
        controlador.cargarDatosPrueba();
        controlador.cargarDatosPrueba();
    }

    @Test
    public void testFabrica() {
        Fabrica fabrica = Fabrica.getInstance();
        assertNotNull(fabrica);
        IControlador ic = fabrica.getIControlador();
        assertNotNull(ic);
    }

    @Test
    public void testExcepciones() {
        Exception causa = new RuntimeException("Causa de prueba");

        // 1. PersistenciaException
        Logica.excepciones.PersistenciaException pe = new Logica.excepciones.PersistenciaException("Error DB", causa);
        org.junit.Assert.assertEquals("Error DB", pe.getMessage());
        org.junit.Assert.assertEquals(causa, pe.getCause());

        // 2. EntidadNoEncontradaException
        Logica.excepciones.EntidadNoEncontradaException ene = new Logica.excepciones.EntidadNoEncontradaException("No existe", causa);
        org.junit.Assert.assertEquals("No existe", ene.getMessage());
        org.junit.Assert.assertEquals(causa, ene.getCause());

        // 3. RelacionInvalidaException y su método estático esRelacionInvalida
        Logica.excepciones.RelacionInvalidaException ri = new Logica.excepciones.RelacionInvalidaException("Relación inválida", causa);
        org.junit.Assert.assertEquals("Relación inválida", ri.getMessage());
        org.junit.Assert.assertEquals(causa, ri.getCause());

        IllegalStateException illegalEx = new IllegalStateException("Estado inválido");
        Logica.excepciones.RelacionInvalidaException riConIllegal = new Logica.excepciones.RelacionInvalidaException("Inválido", illegalEx);
        org.junit.Assert.assertTrue(Logica.excepciones.RelacionInvalidaException.esRelacionInvalida(riConIllegal));
        org.junit.Assert.assertFalse(Logica.excepciones.RelacionInvalidaException.esRelacionInvalida(new RuntimeException("Otro")));
        org.junit.Assert.assertFalse(Logica.excepciones.RelacionInvalidaException.esRelacionInvalida(null));

        // 4. NombreDuplicadoException y su método estático esNombreDuplicado
        java.sql.SQLIntegrityConstraintViolationException sqlEx = new java.sql.SQLIntegrityConstraintViolationException("Duplicado");
        Logica.excepciones.NombreDuplicadoException nde = new Logica.excepciones.NombreDuplicadoException("Duplicado", sqlEx);
        org.junit.Assert.assertEquals("Duplicado", nde.getMessage());
        org.junit.Assert.assertEquals(sqlEx, nde.getCause());
        org.junit.Assert.assertTrue(Logica.excepciones.NombreDuplicadoException.esNombreDuplicado(nde));
        org.junit.Assert.assertFalse(Logica.excepciones.NombreDuplicadoException.esNombreDuplicado(new RuntimeException("Otro")));
        org.junit.Assert.assertFalse(Logica.excepciones.NombreDuplicadoException.esNombreDuplicado(null));
    }

    @Test
    public void testDTOsGettersYToString() {
        // 1. DetalleCurso
        Logica.DTO.DetalleCurso detCurso = new Logica.DTO.DetalleCurso(
                "Curso Test", "Desc Test", 3, 30, 4, "http://test.uy",
                LocalDate.of(2026, 1, 1), "INCO",
                java.util.Arrays.asList("Previa1"),
                java.util.Arrays.asList("Edicion1"),
                java.util.Arrays.asList("Prog1"),
                5L,
                java.util.Arrays.asList("Cat1"),
                "img/curso.png"
        );
        org.junit.Assert.assertEquals("Curso Test", detCurso.getNombre());
        org.junit.Assert.assertEquals("Desc Test", detCurso.getDescripcion());
        org.junit.Assert.assertEquals(3, detCurso.getDuracion());
        org.junit.Assert.assertEquals(30, detCurso.getCantidadHoras());
        org.junit.Assert.assertEquals(4, detCurso.getCreditos());
        org.junit.Assert.assertEquals("http://test.uy", detCurso.getUrl());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 1, 1), detCurso.getFechaAlta());
        org.junit.Assert.assertEquals("INCO", detCurso.getInstituto());
        org.junit.Assert.assertFalse(detCurso.getPrevias().isEmpty());
        org.junit.Assert.assertFalse(detCurso.getEdiciones().isEmpty());
        org.junit.Assert.assertFalse(detCurso.getProgramas().isEmpty());
        org.junit.Assert.assertEquals(5L, detCurso.getCantidadInscriptos());
        org.junit.Assert.assertEquals(1, detCurso.getCategorias().size());
        org.junit.Assert.assertEquals("img/curso.png", detCurso.getRutaImagen());

        // 2. DetalleEdicionCurso
        Logica.DTO.DetalleEdicionCurso detEd = new Logica.DTO.DetalleEdicionCurso(
                "Edicion Test", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 6, 1),
                20, LocalDate.of(2026, 2, 1), "Curso Test",
                java.util.Arrays.asList("Docente1"),
                10L,
                "img/edicion.png",
                java.util.Arrays.asList("Cat1")
        );
        org.junit.Assert.assertEquals("Edicion Test", detEd.getNombre());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 3, 1), detEd.getFechaInicio());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 6, 1), detEd.getFechaFin());
        org.junit.Assert.assertEquals(Integer.valueOf(20), detEd.getCupo());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 2, 1), detEd.getFechaPublicacion());
        org.junit.Assert.assertEquals("Curso Test", detEd.getCurso());
        org.junit.Assert.assertFalse(detEd.getDocentes().isEmpty());
        org.junit.Assert.assertEquals(10L, detEd.getCantidadInscriptos());
        org.junit.Assert.assertEquals("img/edicion.png", detEd.getRutaImagen());
        org.junit.Assert.assertEquals(1, detEd.getCategorias().size());

        // 3. DetalleUsuario
        Logica.DTO.DetalleUsuario detUsr = new Logica.DTO.DetalleUsuario(
                "nick1", "Nom", "Ape", "mail@test.com", LocalDate.of(1990, 1, 1),
                "Docente", "INCO", java.util.Collections.emptyList(),
                java.util.Collections.emptyList(), java.util.Collections.emptyList(),
                "img/usr.png", java.util.Collections.emptyList()
        );
        org.junit.Assert.assertEquals("nick1", detUsr.getNickname());
        org.junit.Assert.assertEquals("Nom", detUsr.getNombre());
        org.junit.Assert.assertEquals("Ape", detUsr.getApellido());
        org.junit.Assert.assertEquals("mail@test.com", detUsr.getEmail());
        org.junit.Assert.assertEquals(LocalDate.of(1990, 1, 1), detUsr.getFechaNacimiento());
        org.junit.Assert.assertEquals("Docente", detUsr.getTipoUsuario());
        org.junit.Assert.assertEquals("INCO", detUsr.getInstituto());
        org.junit.Assert.assertEquals("img/usr.png", detUsr.getRutaImagen());
        org.junit.Assert.assertNotNull(detUsr.getCursos());
        org.junit.Assert.assertNotNull(detUsr.getEdiciones());
        org.junit.Assert.assertNotNull(detUsr.getProgramas());
        org.junit.Assert.assertNotNull(detUsr.getInscripcionesRechazadas());

        // 4. UsuarioEdicion
        Logica.DTO.UsuarioEdicion usrEd = new Logica.DTO.UsuarioEdicion(
                "nick2", "mail2@test.com", "Estudiante", "Nom2", "Ape2",
                LocalDate.of(1995, 2, 2), null, "img/edit.png"
        );
        org.junit.Assert.assertEquals("nick2", usrEd.getNickname());
        org.junit.Assert.assertEquals("mail2@test.com", usrEd.getEmail());
        org.junit.Assert.assertEquals("Estudiante", usrEd.getTipoUsuario());
        org.junit.Assert.assertEquals("Nom2", usrEd.getNombre());
        org.junit.Assert.assertEquals("Ape2", usrEd.getApellido());
        org.junit.Assert.assertEquals(LocalDate.of(1995, 2, 2), usrEd.getFechaNacimiento());
        org.junit.Assert.assertNull(usrEd.getInstituto());
        org.junit.Assert.assertEquals("img/edit.png", usrEd.getRutaImagen());

        // 5. UsuarioResumen y CursoResumen
        Logica.DTO.UsuarioResumen usrRes = new Logica.DTO.UsuarioResumen("nick3", "Nom Ape", "Docente", "img/res.png");
        org.junit.Assert.assertEquals("nick3", usrRes.getNickname());
        org.junit.Assert.assertEquals("Nom Ape", usrRes.getNombreCompleto());
        org.junit.Assert.assertEquals("Docente", usrRes.getTipo());
        org.junit.Assert.assertEquals("img/res.png", usrRes.getRutaImagen());

        Logica.DTO.CursoResumen curRes = new Logica.DTO.CursoResumen("Curso1", "INCO");
        org.junit.Assert.assertEquals("Curso1", curRes.getNombre());
        org.junit.Assert.assertEquals("INCO", curRes.getNombreInstituto());

        // 6. DetalleProgramaFormacion
        Logica.DTO.DetalleProgramaFormacion detProg = new Logica.DTO.DetalleProgramaFormacion(
                "Prog1", "Desc", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31),
                LocalDate.of(2025, 12, 1), java.util.Collections.emptyList(),
                java.util.Arrays.asList("CatProg"), "img/prog.png"
        );
        org.junit.Assert.assertEquals("Prog1", detProg.getNombre());
        org.junit.Assert.assertEquals("Desc", detProg.getDescripcion());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 1, 1), detProg.getFechaInicio());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 12, 31), detProg.getFechaFin());
        org.junit.Assert.assertEquals(LocalDate.of(2025, 12, 1), detProg.getFechaAlta());
        org.junit.Assert.assertNotNull(detProg.getCursos());
        org.junit.Assert.assertEquals("img/prog.png", detProg.getRutaImagen());
        org.junit.Assert.assertEquals(1, detProg.getCategorias().size());

        // 7. ElementoResumen, InscripcionResumen, ResultadoInscripcion
        Logica.DTO.ElementoResumen elRes = new Logica.DTO.ElementoResumen("nomEl", "textoEl");
        org.junit.Assert.assertEquals("nomEl", elRes.getNombre());
        org.junit.Assert.assertEquals("textoEl", elRes.getTextoMostrar());
        org.junit.Assert.assertEquals("textoEl", elRes.toString());

        Logica.DTO.InscripcionResumen insRes = new Logica.DTO.InscripcionResumen(1L, "estNick", "Nom Est", LocalDate.of(2026, 5, 5), "ACEPTADA", 2.5);
        org.junit.Assert.assertEquals(Long.valueOf(1L), insRes.getId());
        org.junit.Assert.assertEquals("estNick", insRes.getNicknameEstudiante());
        org.junit.Assert.assertEquals("Nom Est", insRes.getNombreEstudiante());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 5, 5), insRes.getFechaInscripcion());
        org.junit.Assert.assertEquals("ACEPTADA", insRes.getEstado());
        org.junit.Assert.assertEquals(2.5, insRes.getPrioridad(), 0.001);

        Logica.DTO.ResultadoInscripcion resIns = new Logica.DTO.ResultadoInscripcion("Ed1", "Cur1", LocalDate.of(2026, 4, 4), "RECHAZADA");
        org.junit.Assert.assertEquals("Ed1", resIns.getNombreEdicion());
        org.junit.Assert.assertEquals("Cur1", resIns.getNombreCurso());
        org.junit.Assert.assertEquals(LocalDate.of(2026, 4, 4), resIns.getFechaInscripcion());
        org.junit.Assert.assertEquals("RECHAZADA", resIns.getEstado());
    }

    @Test
    public void testGestionInstituto() {
        String nombreInst = "inst_test_" + System.currentTimeMillis();
        controlador.altaInstituto(nombreInst);

        assertTrue(controlador.existeInstituto(nombreInst));
        assertFalse(controlador.listarInstitutos().isEmpty());
        assertTrue(controlador.listarNombresInstitutos().contains(nombreInst));
    }

    @Test
    public void testGestionUsuarios() {
        String instNom = "inst_test_" + System.currentTimeMillis();
        controlador.altaInstituto(instNom);
        assertTrue(controlador.existeInstituto(instNom));
        //estudiante
        String nickEst = "est_nick_test_" + System.currentTimeMillis();
        String mailEst = "est_mail_test_" + System.currentTimeMillis();
        controlador.altaUsuarioEstudiante(nickEst, "est_nom_test", "est_ap_test", mailEst, "pass123", LocalDate.of(2026, 1, 1), null);
        assertTrue(controlador.existeNickname(nickEst));
        assertTrue(controlador.existeEmail(mailEst));
        assertFalse(controlador.listarEstudiantes().isEmpty());

        //docente
        String nickDoc = "doc_nick_test_" + System.currentTimeMillis();
        String mailDoc = "doc_mail_test_" + System.currentTimeMillis();
        controlador.altaUsuarioDocente(nickDoc, "doc_nom_test", "doc_ap_test", mailDoc, "pass123", LocalDate.of(2026, 1, 1), instNom, null);
        assertTrue(controlador.existeNickname(nickDoc));
        assertFalse(controlador.listarDocentesPorInstituto(instNom).isEmpty());

        //busqueda y modificacion
        assertFalse(controlador.listarUsuarios().isEmpty());
        assertNotNull(controlador.consultarUsuario(nickEst, null));
        assertNotNull(controlador.buscarUsuarioParaEditar(nickEst));
        controlador.modificarUsuario(nickEst, "nom_modificado", "doc_ap_test", LocalDate.of(2026, 1, 1), instNom, null);

        //usuario que no existe
        String nickInexistente = "inexistente_" + System.currentTimeMillis();

        try {
            controlador.modificarUsuario(
                    nickInexistente,
                    "NombreTest",
                    "ApellidoTest",
                    LocalDate.of(1995, 5, 15),
                    null, null
            );
        } catch (Logica.excepciones.EntidadNoEncontradaException e) {
            assertTrue(e.getMessage().contains(nickInexistente));
        }
    }

    @Test
    public void testGestionCurso() {
        String nombreInst = "inst_curso_test" + System.currentTimeMillis();
        controlador.altaInstituto(nombreInst);
        List<Instituto> institutos = controlador.listarInstitutos();
        Instituto inst = null;
        for (Instituto i : institutos) {
            if (i.getNombre().equals(nombreInst)) {
                inst = i;
                break;
            }
        }
        String nombreCurso = "nom_curso_test" + System.currentTimeMillis();
        controlador.altaCurso(nombreCurso, "desc", 8, 40, 5, "url", LocalDate.now(), inst, Collections.<Logica.Entidades.Curso>emptyList(), Collections.emptyList(), null);

        assertTrue(controlador.existeCurso(nombreCurso));
        assertFalse(controlador.listarCursos().isEmpty());
        assertFalse(controlador.listarCursosPorInstituto(nombreInst).isEmpty());
        DetalleCurso detalle = controlador.consultarCurso(nombreCurso);
        assertNotNull(detalle);
    }

    @Test
    public void testGestionProgramasFormacion() {
        //alta programa
        String nombreProg = "nom_prog_test" + System.currentTimeMillis();
        controlador.altaPrograma(
                nombreProg, "desc prog", LocalDate.now(), LocalDate.now().plusMonths(2), LocalDate.now(), null, null);
        assertTrue(controlador.existePrograma(nombreProg));
        assertTrue(controlador.listarProgramas().contains(nombreProg));

        //asignar curso
        String instNombre = "nom_inst_test_" + System.currentTimeMillis();
        controlador.altaInstituto(instNombre);
        assertTrue(controlador.existeInstituto(instNombre));
        List<Instituto> institutos = controlador.listarInstitutos();
        Instituto inst = null;
        for (Instituto i : institutos) {
            if (i.getNombre().equals(instNombre)) {
                inst = i;
                break;
            }
        }

        String cursoNombre = "nom_curso_test_" + System.currentTimeMillis();
        controlador.altaCurso(cursoNombre, "desc", 0, 0, 0, "url_test", LocalDate.now(), inst, Collections.<Curso>emptyList(), Collections.emptyList(), null);

        controlador.agregarCursoAPrograma(nombreProg, cursoNombre);
        assertNotNull(controlador.consultarPrograma(nombreProg));
    }

    @Test
    public void testDetalleCursoInexistente() {
        String nombreInexistente = "CursoInexistente_" + System.currentTimeMillis();
        DetalleCurso detalleInex = controlador.consultarCurso(nombreInexistente);
        assertNull(detalleInex);
    }

    @Test
    public void testEdicionesInscripciones() {
        //instituto
        String instNombre = "nom_inst_test_" + System.currentTimeMillis();
        controlador.altaInstituto(instNombre);
        List<Instituto> institutos = controlador.listarInstitutos();
        Instituto inst = null;
        for (Instituto i : institutos) {
            if (i.getNombre().equals(instNombre)) {
                inst = i;
                break;
            }
        }
        //docente
        String nickDoc = "doc_nick_test_" + System.currentTimeMillis();
        String mailDoc = "doc_mail_test_" + System.currentTimeMillis();
        controlador.altaUsuarioDocente(nickDoc, "doc_nom_test", "doc_ap_test", mailDoc, "pass123", LocalDate.of(2026, 1, 1), instNombre, null);
        List<Docente> docentes = controlador.listarDocentesPorInstituto(instNombre);

        String cursoNombre = "nom_curso_test_" + System.currentTimeMillis();
        controlador.altaCurso(cursoNombre, "desc", 0, 0, 0, "url_test", LocalDate.now(), inst, Collections.<Curso>emptyList(), Collections.emptyList(), null);

        //alta edicion curso
        String edicionNombre = "ed_curso_test_" + System.currentTimeMillis();
        controlador.altaEdicionCurso(
                edicionNombre, LocalDate.now().minusDays(5), LocalDate.now().plusMonths(2),
                20, cursoNombre, docentes, null
        );

        assertTrue(controlador.existeEdicion(edicionNombre));
        assertFalse(controlador.listarEdicionesPorCurso(cursoNombre).isEmpty());
        assertFalse(controlador.listarEdicionesVigentesPorCurso(cursoNombre).isEmpty());
        assertNotNull(controlador.buscarEdicion(edicionNombre));
        assertNotNull(controlador.consultarEdicion(edicionNombre));

        //inscripcion de estudiante a edicion
        String nickEst = "est_nick_test_" + System.currentTimeMillis();
        String mailEst = "est_mail_test_" + System.currentTimeMillis();
        controlador.altaUsuarioEstudiante(nickEst, "nom_est", "ap_est", mailEst + "mail_test", "pass123", LocalDate.of(2001, 2, 2), null);
        controlador.inscribirEstudianteEdicion(nickEst, edicionNombre, LocalDate.now());
        InscripcionEdicion inscripcion = controlador.buscarInscripcionEdicion(nickEst, edicionNombre);
        if (inscripcion != null && inscripcion.getId() != null) {
            controlador.modificarInscripcionEdicion(inscripcion.getId(), LocalDate.now().plusDays(1));
        }

    }

    @Test
    public void testBusquedasInexistentes() {
        String sufijo = "_" + System.currentTimeMillis();

        assertFalse(controlador.existeInstituto("INST_NO_EXISTE" + sufijo));
        assertFalse(controlador.existeNickname("NICK_NO_EXISTE" + sufijo));
        assertFalse(controlador.existeEmail("EMAIL_NO_EXISTE" + sufijo + "@test.com"));
        assertFalse(controlador.existeCurso("CURSO_NO_EXISTE" + sufijo));
        assertFalse(controlador.existePrograma("PROG_NO_EXISTE" + sufijo));
        assertFalse(controlador.existeEdicion("ED_NO_EXISTE" + sufijo));

        // tienen que retornar null porque no existen
        assertNull(controlador.consultarUsuario("NICK_FANTASMA" + sufijo, null));
        assertNull(controlador.consultarCurso("CURSO_FANTASMA" + sufijo));
        assertNull(controlador.consultarPrograma("PROG_FANTASMA" + sufijo));
        assertNull(controlador.consultarEdicion("ED_FANTASMA" + sufijo));
        assertNull(controlador.buscarUsuarioParaEditar("NICK_FANTASMA" + sufijo));
    }

    @Test
    public void testDuplicados() {
        String sufijo = "_" + System.currentTimeMillis();
        String instNom = "INST_DUP" + sufijo;
        controlador.altaInstituto(instNom);

        try {
            controlador.altaInstituto(instNom);
        } catch (Exception e) {
            assertNotNull(e);
        }

        String nick = "user_dup" + sufijo;
        String mail = "mail_dup" + sufijo;
        controlador.altaUsuarioEstudiante(nick, "Nombre", "Apellido", mail, "pass123", LocalDate.of(2000, 1, 1), null);

        // nick y mail duplicado
        try {
            controlador.altaUsuarioEstudiante(nick, "Otro", "Otro", "nuevo@mail.com", "pass123", LocalDate.of(2000, 1, 1), null);
        } catch (Exception e) {
            assertNotNull(e);
        }

        try {
            controlador.altaUsuarioEstudiante("otro_nick" + sufijo, "Otro", "Otro", mail, "pass123", LocalDate.of(2000, 1, 1), null);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testListasVacias() {
        String sufijo = "_" + System.currentTimeMillis();
        assertTrue(controlador.listarDocentesPorInstituto("INST_VACIO" + sufijo).isEmpty());
        assertTrue(controlador.listarEdicionesPorCurso("CURSO_VACIO" + sufijo).isEmpty());
        assertTrue(controlador.listarEdicionesVigentesPorCurso("CURSO_VACIO" + sufijo).isEmpty());
        assertTrue(controlador.listarCursosPorInstituto("INST_VACIO" + sufijo).isEmpty());
    }
}
