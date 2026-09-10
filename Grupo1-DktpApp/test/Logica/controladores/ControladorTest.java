/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.controladores;

import Logica.DTO.DetalleCurso;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.Instituto;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
   public void setUp(){
       controlador = Controlador.getInstance();
   }
   
   
    @Test
    public void testCargaDatosPruebaDoble() {
        controlador.cargarDatosPrueba();
        controlador.cargarDatosPrueba();
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
            java.util.Arrays.asList("Prog1")
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

    // 2. DetalleEdicionCurso
    Logica.DTO.DetalleEdicionCurso detEd = new Logica.DTO.DetalleEdicionCurso(
            "Edicion Test", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 6, 1),
            20, LocalDate.of(2026, 2, 1), "Curso Test",
            java.util.Arrays.asList("Docente1")
    );
    org.junit.Assert.assertEquals("Edicion Test", detEd.getNombre());
    org.junit.Assert.assertEquals(LocalDate.of(2026, 3, 1), detEd.getFechaInicio());
    org.junit.Assert.assertEquals(LocalDate.of(2026, 6, 1), detEd.getFechaFin());
    org.junit.Assert.assertEquals(Integer.valueOf(20), detEd.getCupo());
    org.junit.Assert.assertEquals(LocalDate.of(2026, 2, 1), detEd.getFechaPublicacion());
    org.junit.Assert.assertEquals("Curso Test", detEd.getCurso());
    org.junit.Assert.assertFalse(detEd.getDocentes().isEmpty());

    // 3. DetalleUsuario
    Logica.DTO.DetalleUsuario detUsr = new Logica.DTO.DetalleUsuario(
            "nick1", "Nom", "Ape", "mail@test.com", LocalDate.of(1990, 1, 1),
            "Docente", "INCO", java.util.Collections.emptyList(),
            java.util.Collections.emptyList(), java.util.Collections.emptyList()
    );
    org.junit.Assert.assertEquals("nick1", detUsr.getNickname());
    org.junit.Assert.assertEquals("Nom", detUsr.getNombre());
    org.junit.Assert.assertEquals("Ape", detUsr.getApellido());
    org.junit.Assert.assertEquals("mail@test.com", detUsr.getEmail());
    org.junit.Assert.assertEquals(LocalDate.of(1990, 1, 1), detUsr.getFechaNacimiento());
    org.junit.Assert.assertEquals("Docente", detUsr.getTipoUsuario());
    org.junit.Assert.assertEquals("INCO", detUsr.getInstituto());
    org.junit.Assert.assertNotNull(detUsr.getCursos());
    org.junit.Assert.assertNotNull(detUsr.getEdiciones());
    org.junit.Assert.assertNotNull(detUsr.getProgramas());

    // 4. UsuarioEdicion (usa getTipoUsuario)
    Logica.DTO.UsuarioEdicion usrEd = new Logica.DTO.UsuarioEdicion(
            "nick2", "mail2@test.com", "Estudiante", "Nom2", "Ape2",
            LocalDate.of(1995, 2, 2), null
    );
    org.junit.Assert.assertEquals("nick2", usrEd.getNickname());
    org.junit.Assert.assertEquals("mail2@test.com", usrEd.getEmail());
    org.junit.Assert.assertEquals("Estudiante", usrEd.getTipoUsuario());
    org.junit.Assert.assertEquals("Nom2", usrEd.getNombre());
    org.junit.Assert.assertEquals("Ape2", usrEd.getApellido());
    org.junit.Assert.assertEquals(LocalDate.of(1995, 2, 2), usrEd.getFechaNacimiento());
    org.junit.Assert.assertNull(usrEd.getInstituto());

    // 5. UsuarioResumen y CursoResumen
    Logica.DTO.UsuarioResumen usrRes = new Logica.DTO.UsuarioResumen("nick3", "Nom Ape", "Docente");
    org.junit.Assert.assertEquals("nick3", usrRes.getNickname());
    org.junit.Assert.assertEquals("Nom Ape", usrRes.getNombreCompleto());
    org.junit.Assert.assertEquals("Docente", usrRes.getTipo());

    Logica.DTO.CursoResumen curRes = new Logica.DTO.CursoResumen("Curso1", "INCO");
    org.junit.Assert.assertEquals("Curso1", curRes.getNombre());
    org.junit.Assert.assertEquals("INCO", curRes.getNombreInstituto());

    // 6. DetalleProgramaFormacion
    Logica.DTO.DetalleProgramaFormacion detProg = new Logica.DTO.DetalleProgramaFormacion(
            "Prog1", "Desc", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31),
            LocalDate.of(2025, 12, 1), java.util.Collections.emptyList()
    );
    org.junit.Assert.assertEquals("Prog1", detProg.getNombre());
    org.junit.Assert.assertEquals("Desc", detProg.getDescripcion());
    org.junit.Assert.assertEquals(LocalDate.of(2026, 1, 1), detProg.getFechaInicio());
    org.junit.Assert.assertEquals(LocalDate.of(2026, 12, 31), detProg.getFechaFin());
    org.junit.Assert.assertEquals(LocalDate.of(2025, 12, 1), detProg.getFechaAlta());
    org.junit.Assert.assertNotNull(detProg.getCursos());
}
    
    
   @Test
   public void testGestionInstituto(){
       String nombreInst="inst_test_"+ System.currentTimeMillis();
       controlador.altaInstituto(nombreInst);
       
       assertTrue(controlador.existeInstituto(nombreInst));
       assertFalse(controlador.listarInstitutos().isEmpty());
       assertTrue(controlador.listarNombresInstitutos().contains(nombreInst));
   }
   
   @Test
   public void testGestionUsuarios(){
       String instNom = "inst_test_"+System.currentTimeMillis();
       controlador.altaInstituto(instNom);
       assertTrue(controlador.existeInstituto(instNom));
       //estudiante
       String nickEst = "est_nick_test_"+ System.currentTimeMillis();
       String mailEst = "est_mail_test_"+ System.currentTimeMillis();
       controlador.altaUsuarioEstudiante(nickEst,"est_nom_test","est_ap_test",mailEst, LocalDate.of(2026,1,1));
       assertTrue(controlador.existeNickname(nickEst));
       assertTrue(controlador.existeEmail(mailEst));
       assertFalse(controlador.listarEstudiantes().isEmpty());
       
       //docente
       String nickDoc = "doc_nick_test_"+ System.currentTimeMillis();
       String mailDoc = "doc_mail_test_"+ System.currentTimeMillis();
       controlador.altaUsuarioDocente(nickDoc, "doc_nom_test", "doc_ap_test", mailDoc, LocalDate.of(2026,1,1), instNom);
       assertTrue(controlador.existeNickname(nickDoc));
       assertFalse(controlador.listarDocentesPorInstituto(instNom).isEmpty());
       
       //busqueda y modificacion
       assertFalse(controlador.listarUsuarios().isEmpty());
       assertNotNull(controlador.consultarUsuario(nickEst));
       assertNotNull(controlador.buscarUsuarioParaEditar(nickEst));
       controlador.modificarUsuario(nickEst, "nom_modificado", "doc_ap_test", LocalDate.of(2026,1,1), instNom);
       
       //usuario que no existe
       String nickInexistente = "inexistente_" + System.currentTimeMillis();

       try {
           controlador.modificarUsuario(
                   nickInexistente,
                   "NombreTest",
                   "ApellidoTest",
                   LocalDate.of(1995, 5, 15),
                   null
           );
       } catch (Logica.excepciones.EntidadNoEncontradaException e) {
           assertTrue(e.getMessage().contains(nickInexistente));
       }
   }
   
   
   @Test
   public void testGestionCurso(){
       String nombreInst = "inst_curso_test"+ System.currentTimeMillis();
       controlador.altaInstituto(nombreInst);
       List<Instituto> institutos = controlador.listarInstitutos();
       Instituto inst = null;
       for(Instituto i:institutos){
           if (i.getNombre().equals(nombreInst)){
               inst = i;
               break;
           }
       }
       String nombreCurso = "nom_curso_test"+ System.currentTimeMillis();
       controlador.altaCurso(nombreCurso,"desc",8,40,5,"url",LocalDate.now(),inst,Collections.<Logica.Entidades.Curso>emptyList());
       
       assertTrue(controlador.existeCurso(nombreCurso));
       assertFalse(controlador.listarCursos().isEmpty());
       assertFalse(controlador.listarCursosPorInstituto(nombreInst).isEmpty());
       DetalleCurso detalle = controlador.consultarCurso(nombreCurso);
       assertNotNull(detalle);
   }
   
   @Test
   public void testGestionProgramasFormacion(){
       //alta programa
       String nombreProg = "nom_prog_test"+System.currentTimeMillis();
       controlador.altaPrograma(
                    nombreProg, "desc prog", LocalDate.now(), LocalDate.now().plusMonths(2), LocalDate.now());
       assertTrue(controlador.existePrograma(nombreProg));
       assertTrue(controlador.listarProgramas().contains(nombreProg));
       
       //asignar curso
       String instNombre = "nom_inst_test_"+System.currentTimeMillis();
       controlador.altaInstituto(instNombre);
       assertTrue(controlador.existeInstituto(instNombre));
       List<Instituto> institutos = controlador.listarInstitutos();
       Instituto inst = null;
       for(Instituto i:institutos){
           if(i.getNombre().equals(instNombre)){
               inst=i;
               break;
           }
       }
       
       String cursoNombre = "nom_curso_test_"+System.currentTimeMillis();
       controlador.altaCurso(cursoNombre, "desc", 0, 0, 0, "url_test", LocalDate.now(), inst, Collections.<Curso>emptyList());
       
       controlador.agregarCursoAPrograma(nombreProg, cursoNombre);
       assertNotNull(controlador.consultarPrograma(nombreProg));
   }
   
  
   @Test
   public void testDetalleCursoInexistente(){
       String nombreInexistente = "CursoInexistente_" + System.currentTimeMillis();
       DetalleCurso detalleInex = controlador.consultarCurso(nombreInexistente);
       assertNull(detalleInex);
   }
   
//   @Test
//   public void testDetalleCursoSinInstituto(){
//       String nombreCursoSinInst = "CursoSinInst_" + System.currentTimeMillis();
//
//       try {
//           controlador.altaCurso(
//                   nombreCursoSinInst,
//                   "Descripción sin inst",
//                   5, 30, 4, "b",
//                   LocalDate.now(),
//                   null,
//                   Collections.emptyList()
//           );
//       } catch (Logica.excepciones.RelacionInvalidaException e) {
//           assertNotNull(e.getMessage());
//       }
//   }
//   
   @Test
   public void testEdicionesInscripciones(){
       //instituto
       String instNombre = "nom_inst_test_"+System.currentTimeMillis();
       controlador.altaInstituto(instNombre);
       List<Instituto> institutos = controlador.listarInstitutos();
       Instituto inst = null;
       for(Instituto i:institutos){
           if (i.getNombre().equals(instNombre)){
               inst = i;
               break;
           }
       }
       //docente
       String nickDoc = "doc_nick_test_"+ System.currentTimeMillis();
       String mailDoc = "doc_mail_test_"+ System.currentTimeMillis();
       controlador.altaUsuarioDocente(nickDoc, "doc_nom_test", "doc_ap_test", mailDoc, LocalDate.of(2026,1,1), instNombre);
       List<Docente> docentes = controlador.listarDocentesPorInstituto(instNombre);
       
       String cursoNombre = "nom_curso_test_"+System.currentTimeMillis();
       controlador.altaCurso(cursoNombre, "desc", 0, 0, 0, "url_test", LocalDate.now(), inst, Collections.<Curso>emptyList());
       
       //alta edicion curso
       String edicionNombre = "ed_curso_test_"+System.currentTimeMillis();
       controlador.altaEdicionCurso(
           edicionNombre, LocalDate.now(), LocalDate.now().plusMonths(2), 
           20, cursoNombre, docentes
       );
       
       assertTrue(controlador.existeEdicion(edicionNombre));
       assertFalse(controlador.listarEdicionesPorCurso(cursoNombre).isEmpty());
       assertFalse(controlador.listarEdicionesVigentesPorCurso(cursoNombre).isEmpty());
       assertNotNull(controlador.buscarEdicion(edicionNombre));
       assertNotNull(controlador.consultarEdicion(edicionNombre));
       
       //inscripcion de estudiante a edicion
       String nickEst = "est_nick_test_"+System.currentTimeMillis();
       String mailEst = "est_mail_test_"+System.currentTimeMillis();
       controlador.altaUsuarioEstudiante(nickEst, "nom_est", "ap_est", mailEst + "mail_test", LocalDate.of(2001, 2, 2));
       controlador.inscribirEstudianteEdicion(nickEst, edicionNombre, LocalDate.now());
       InscripcionEdicion inscripcion = controlador.buscarInscripcionEdicion(nickEst, edicionNombre);
        if (inscripcion != null && inscripcion.getId() != null) {
            controlador.modificarInscripcionEdicion(inscripcion.getId(), LocalDate.now().plusDays(1));
        }   

   }
   

   @Test
   public void testBusquedasInexistentes(){
       String sufijo = "_" + System.currentTimeMillis();
    
       assertFalse(controlador.existeInstituto("INST_NO_EXISTE" + sufijo));
       assertFalse(controlador.existeNickname("NICK_NO_EXISTE" + sufijo));
       assertFalse(controlador.existeEmail("EMAIL_NO_EXISTE" + sufijo + "@test.com"));
       assertFalse(controlador.existeCurso("CURSO_NO_EXISTE" + sufijo));
       assertFalse(controlador.existePrograma("PROG_NO_EXISTE" + sufijo));
       assertFalse(controlador.existeEdicion("ED_NO_EXISTE" + sufijo));
    
       // tienen que retornar null porque no existen
       assertNull(controlador.consultarUsuario("NICK_FANTASMA" + sufijo));
       assertNull(controlador.consultarCurso("CURSO_FANTASMA" + sufijo));
       assertNull(controlador.consultarPrograma("PROG_FANTASMA" + sufijo));
       assertNull(controlador.consultarEdicion("ED_FANTASMA" + sufijo));
       assertNull(controlador.buscarUsuarioParaEditar("NICK_FANTASMA" + sufijo));
    }

    @Test
    public void testDuplicados(){
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
       controlador.altaUsuarioEstudiante(nick, "Nombre", "Apellido", mail, LocalDate.of(2000, 1, 1));

       // nick y mail duplicado
       try {
           controlador.altaUsuarioEstudiante(nick, "Otro", "Otro", "nuevo@mail.com", LocalDate.of(2000, 1, 1));
       } catch (Exception e) {
           assertNotNull(e);
       }

       try {
           controlador.altaUsuarioEstudiante("otro_nick" + sufijo, "Otro", "Otro", mail, LocalDate.of(2000, 1, 1));
       } catch (Exception e) {
           assertNotNull(e);
       }
   }
   
   @Test
    public void testListasVacias(){
       String sufijo = "_" + System.currentTimeMillis(); 
       assertTrue(controlador.listarDocentesPorInstituto("INST_VACIO" + sufijo).isEmpty());
       assertTrue(controlador.listarEdicionesPorCurso("CURSO_VACIO" + sufijo).isEmpty());
       assertTrue(controlador.listarEdicionesVigentesPorCurso("CURSO_VACIO" + sufijo).isEmpty());
       assertTrue(controlador.listarCursosPorInstituto("INST_VACIO" + sufijo).isEmpty());
    }
   
   
   @Test
   public void testCargaDatos(){
       controlador.cargarDatosPrueba();
   }
   
   
}
