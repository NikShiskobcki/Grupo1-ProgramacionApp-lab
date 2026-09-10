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
