package Entidades;

import Logica.DTO.*;
import Logica.Entidades.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class EntidadesTest {

    @Test
    public void testCategoria() {
        Categoria c1 = new Categoria();
        assertNull(c1.getNombre());

        Categoria c2 = new Categoria("Programación");
        assertEquals("Programación", c2.getNombre());
    }

    @Test
    public void testEstadoInscripcion() {
        assertEquals(3, EstadoInscripcion.values().length);
        assertEquals(EstadoInscripcion.INSCRIPTO, EstadoInscripcion.valueOf("INSCRIPTO"));
        assertEquals(EstadoInscripcion.ACEPTADA, EstadoInscripcion.valueOf("ACEPTADA"));
        assertEquals(EstadoInscripcion.RECHAZADA, EstadoInscripcion.valueOf("RECHAZADA"));
    }

    @Test
    public void testInstituto() {
        Instituto i1 = new Instituto();
        assertNull(i1.getNombre());

        Instituto i2 = new Instituto("INCO");
        assertEquals("INCO", i2.getNombre());

        List<Curso> cursos = new ArrayList<>();
        i2.setCursos(cursos);
        // Instituto no tiene getter de cursos
    }

    @Test
    public void testCurso() {
        Instituto inst = new Instituto("INCO");
        Curso c1 = new Curso();
        assertNull(c1.getNombre());

        Curso c2 = new Curso("Java", "Desc", 4, 40, 5, "http://java.uy", LocalDate.of(2026, 1, 1), inst);
        assertEquals("Java", c2.getNombre());
        assertEquals("Desc", c2.getDescripcion());
        assertEquals(4, c2.getDuracion());
        assertEquals(40, c2.getCantidadHoras());
        assertEquals(5, c2.getCreditos());
        assertEquals("http://java.uy", c2.getUrl());
        assertEquals(LocalDate.of(2026, 1, 1), c2.getFechaAlta());
        assertEquals(inst, c2.getInstituto());
        assertNull(c2.getRutaImagen());
        assertTrue(c2.getPrevias().isEmpty());
        assertTrue(c2.getEdiciones().isEmpty());
        assertTrue(c2.getCategorias().isEmpty());

        Curso c3 = new Curso("Python", "Desc2", 2, 20, 3, "http://py.uy", LocalDate.of(2026, 2, 1), inst, "img/py.png");
        assertEquals("img/py.png", c3.getRutaImagen());

        List<Curso> previas = new ArrayList<>();
        previas.add(c2);
        c3.setPrevias(previas);
        assertEquals(1, c3.getPrevias().size());

        List<Categoria> cats = new ArrayList<>();
        cats.add(new Categoria("Backend"));
        c3.setCategorias(cats);
        assertEquals(1, c3.getCategorias().size());

        c3.setRutaImagen("img/new.png");
        assertEquals("img/new.png", c3.getRutaImagen());
    }

    @Test
    public void testDocente() {
        Instituto inst = new Instituto("INCO");
        Docente d1 = new Docente();
        assertNull(d1.getNickname());

        Docente d2 = new Docente("doc1", "Nombre", "Apellido", "doc@test.uy", "pass", LocalDate.of(1980, 5, 10), inst);
        assertEquals("doc1", d2.getNickname());
        assertEquals("Nombre", d2.getNombre());
        assertEquals("Apellido", d2.getApellido());
        assertEquals("doc@test.uy", d2.getEmail());
        assertEquals("pass", d2.getContrasenia());
        assertEquals(LocalDate.of(1980, 5, 10), d2.getFechaNacimiento());
        assertEquals(inst, d2.getInstituto());
        assertTrue(d2.getEdiciones().isEmpty());

        Instituto inst2 = new Instituto("FING");
        d2.setInstituto(inst2);
        assertEquals(inst2, d2.getInstituto());
    }

    @Test
    public void testEstudiante() {
        Estudiante e1 = new Estudiante();
        assertNull(e1.getNickname());

        Estudiante e2 = new Estudiante("est1", "NombreE", "ApellidoE", "est@test.uy", "pass1", LocalDate.of(2002, 3, 15));
        assertEquals("est1", e2.getNickname());
        assertEquals("NombreE", e2.getNombre());
        assertEquals("ApellidoE", e2.getApellido());
        assertEquals("est@test.uy", e2.getEmail());
        assertEquals("pass1", e2.getContrasenia());
        assertEquals(LocalDate.of(2002, 3, 15), e2.getFechaNacimiento());
        assertTrue(e2.getInscripcionesEdiciones().isEmpty());
        assertTrue(e2.getInscripcionesProgramas().isEmpty());

        List<InscripcionEdicion> ieList = new ArrayList<>();
        e2.setInscripcionesEdiciones(ieList);
        assertEquals(ieList, e2.getInscripcionesEdiciones());

        List<InscripcionPrograma> ipList = new ArrayList<>();
        e2.setInscripcionesProgramas(ipList);
        assertEquals(ipList, e2.getInscripcionesProgramas());
    }

    @Test
    public void testEdicionCurso() {
        Instituto inst = new Instituto("INCO");
        Curso curso = new Curso("Java", "Desc", 4, 40, 5, "http://java.uy", LocalDate.of(2026, 1, 1), inst);
        EdicionCurso ed1 = new EdicionCurso();
        assertNull(ed1.getNombre());

        EdicionCurso ed2 = new EdicionCurso("Ed1", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 1), 30, LocalDate.of(2026, 2, 1), curso);
        assertEquals("Ed1", ed2.getNombre());
        assertEquals(LocalDate.of(2026, 3, 1), ed2.getFechaInicio());
        assertEquals(LocalDate.of(2026, 7, 1), ed2.getFechaFin());
        assertEquals(Integer.valueOf(30), ed2.getCupo());
        assertEquals(LocalDate.of(2026, 2, 1), ed2.getFechaPublicacion());
        assertEquals(curso, ed2.getCurso());
        assertNull(ed2.getRutaImagen());
        assertTrue(ed2.getDocentes().isEmpty());
        assertTrue(ed2.getInscripciones().isEmpty());

        EdicionCurso ed3 = new EdicionCurso("Ed2", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 1), 25, LocalDate.of(2026, 2, 1), curso, "img/ed.png");
        assertEquals("img/ed.png", ed3.getRutaImagen());

        ed3.setNombre("Ed2_mod");
        assertEquals("Ed2_mod", ed3.getNombre());
        ed3.setFechaInicio(LocalDate.of(2026, 4, 1));
        assertEquals(LocalDate.of(2026, 4, 1), ed3.getFechaInicio());
        ed3.setFechaFin(LocalDate.of(2026, 8, 1));
        assertEquals(LocalDate.of(2026, 8, 1), ed3.getFechaFin());
        ed3.setCupo(40);
        assertEquals(Integer.valueOf(40), ed3.getCupo());
        ed3.setFechaPublicacion(LocalDate.of(2026, 3, 1));
        assertEquals(LocalDate.of(2026, 3, 1), ed3.getFechaPublicacion());
        ed3.setCurso(curso);
        assertEquals(curso, ed3.getCurso());
        ed3.setRutaImagen("img/new_ed.png");
        assertEquals("img/new_ed.png", ed3.getRutaImagen());

        List<Docente> docentes = new ArrayList<>();
        ed3.setDocentes(docentes);
        assertEquals(docentes, ed3.getDocentes());

        List<InscripcionEdicion> inscripciones = new ArrayList<>();
        ed3.setInscripciones(inscripciones);
        assertEquals(inscripciones, ed3.getInscripciones());
    }

    @Test
    public void testInscripcionEdicion() {
        InscripcionEdicion ie1 = new InscripcionEdicion();
        assertNull(ie1.getId());
        assertEquals(EstadoInscripcion.INSCRIPTO, ie1.getEstado());

        Estudiante est = new Estudiante("est_x", "Nom", "Ape", "e@x.uy", "p", LocalDate.of(2000, 1, 1));
        EdicionCurso ed = new EdicionCurso();
        InscripcionEdicion ie2 = new InscripcionEdicion(LocalDate.of(2026, 5, 1), est, ed);
        assertEquals(LocalDate.of(2026, 5, 1), ie2.getFechaInscripcion());
        assertEquals(est, ie2.getEstudiante());
        assertEquals(ed, ie2.getEdicion());
        assertEquals(EstadoInscripcion.INSCRIPTO, ie2.getEstado());

        ie2.setFechaInscripcion(LocalDate.of(2026, 6, 1));
        assertEquals(LocalDate.of(2026, 6, 1), ie2.getFechaInscripcion());

        ie2.setEstado(EstadoInscripcion.ACEPTADA);
        assertEquals(EstadoInscripcion.ACEPTADA, ie2.getEstado());
        ie2.setEstado(EstadoInscripcion.RECHAZADA);
        assertEquals(EstadoInscripcion.RECHAZADA, ie2.getEstado());
        ie2.setEstado(null);
        assertEquals(EstadoInscripcion.INSCRIPTO, ie2.getEstado());
    }

    @Test
    public void testInscripcionPrograma() {
        InscripcionPrograma ip1 = new InscripcionPrograma();
        assertNull(ip1.getId());

        Estudiante est = new Estudiante("est_y", "Nom", "Ape", "e@y.uy", "p", LocalDate.of(2000, 1, 1));
        ProgramaFormacion prog = new ProgramaFormacion();
        InscripcionPrograma ip2 = new InscripcionPrograma(LocalDate.of(2026, 4, 1), est, prog);
        assertEquals(LocalDate.of(2026, 4, 1), ip2.getFechaInscripcion());
        assertEquals(est, ip2.getEstudiante());
        assertEquals(prog, ip2.getPrograma());

        ip2.setFechaInscripcion(LocalDate.of(2026, 4, 15));
        assertEquals(LocalDate.of(2026, 4, 15), ip2.getFechaInscripcion());
    }

    @Test
    public void testProgramaFormacion() {
        ProgramaFormacion pf1 = new ProgramaFormacion();
        assertNull(pf1.getNombre());

        ProgramaFormacion pf2 = new ProgramaFormacion("Prog1", "Desc", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2025, 12, 1));
        assertEquals("Prog1", pf2.getNombre());
        assertEquals("Desc", pf2.getDescripcion());
        assertEquals(LocalDate.of(2026, 1, 1), pf2.getFechaInicio());
        assertEquals(LocalDate.of(2026, 12, 31), pf2.getFechaFin());
        assertEquals(LocalDate.of(2025, 12, 1), pf2.getFechaAlta());
        assertNull(pf2.getRutaImagen());
        assertNull(pf2.getNicknameDocente());
        assertTrue(pf2.getCursos().isEmpty());
        assertTrue(pf2.getInscripciones().isEmpty());

        ProgramaFormacion pf3 = new ProgramaFormacion("Prog2", "Desc2", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31), LocalDate.of(2025, 12, 1), "img/prog.png");
        assertEquals("img/prog.png", pf3.getRutaImagen());

        pf3.setNombre("Prog2_mod");
        assertEquals("Prog2_mod", pf3.getNombre());
        pf3.setDescripcion("Desc_mod");
        assertEquals("Desc_mod", pf3.getDescripcion());
        pf3.setFechaInicio(LocalDate.of(2026, 2, 1));
        assertEquals(LocalDate.of(2026, 2, 1), pf3.getFechaInicio());
        pf3.setFechaFin(LocalDate.of(2026, 11, 30));
        assertEquals(LocalDate.of(2026, 11, 30), pf3.getFechaFin());
        pf3.setFechaAlta(LocalDate.of(2025, 11, 1));
        assertEquals(LocalDate.of(2025, 11, 1), pf3.getFechaAlta());
        pf3.setRutaImagen("img/new_prog.png");
        assertEquals("img/new_prog.png", pf3.getRutaImagen());
        pf3.setNicknameDocente("docente1");
        assertEquals("docente1", pf3.getNicknameDocente());

        List<Curso> cursos = new ArrayList<>();
        pf3.setCursos(cursos);
        assertEquals(cursos, pf3.getCursos());

        List<InscripcionPrograma> inas = new ArrayList<>();
        pf3.setInscripciones(inas);
        assertEquals(inas, pf3.getInscripciones());
    }

    @Test
    public void testUsuario() {
        Usuario u = new Estudiante();
        u.setNickname("u1");
        assertEquals("u1", u.getNickname());
        u.setNombre("Nom");
        assertEquals("Nom", u.getNombre());
        u.setApellido("Ape");
        assertEquals("Ape", u.getApellido());
        u.setContrasenia("pass");
        assertEquals("pass", u.getContrasenia());
        u.setFechaNacimiento(LocalDate.of(1999, 9, 9));
        assertEquals(LocalDate.of(1999, 9, 9), u.getFechaNacimiento());
        u.setRutaImagen("img/u.png");
        assertEquals("img/u.png", u.getRutaImagen());

        List<Usuario> seguidos = new ArrayList<>();
        u.setSeguidos(seguidos);
        assertEquals(seguidos, u.getSeguidos());
    }

    @Test
    public void testDTOExtra() {
        UsuarioResumen ur = new UsuarioResumen("nickX", "Nom Ape", "Estudiante", null);
        assertEquals("nickX - Nom Ape (Estudiante)", ur.toString());

        CursoResumen cr = new CursoResumen("CursoX", "INCO");
        assertEquals("CursoX (INCO)", cr.toString());

        DetalleEdicionCurso dec = new DetalleEdicionCurso(
                "EdX", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 1),
                15, LocalDate.of(2025, 12, 1), "CursoX",
                Collections.emptyList(), 0L, null
        );
        assertEquals("EdX", dec.getNombre());
        assertTrue(dec.getCategorias().isEmpty());
    }
}
