/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.controladores;

import Logica.Entidades.Docente;
import Logica.Entidades.Estudiante;
import Logica.Entidades.Instituto;
import Persistencia.ManejadorCurso;
import Persistencia.ManejadorEdicionCurso;
import Persistencia.ManejadorInscripcionEdicion;
import Persistencia.ManejadorInscripcionPrograma;
import Persistencia.ManejadorInstituto;
import Persistencia.ManejadorProgramaFormacion;
import Persistencia.ManejadorUsuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Controlador implements IControlador {

    private static Controlador instancia;
    
    //Fabrica de entityManager utilizada por todos los manejadores
    private final EntityManagerFactory emf;
   
    
    //Manejadores;
    private final ManejadorUsuario manejadorUsuario;
    private final ManejadorInstituto manejadorInstituto;
    private final ManejadorCurso manejadorCurso;
    private final ManejadorEdicionCurso manejadorEdicionCurso;
    private final ManejadorProgramaFormacion manejadorProgramaFormacion;
    private final ManejadorInscripcionEdicion manejadorInscripcionEdicion;
    private final ManejadorInscripcionPrograma manejadorInscripcionPrograma;

    // Constructor privado para aplicar Singleton
     
    private Controlador() {
        //Fabrica de entidades creada una sola vez para toda la app
        emf= Persistence.createEntityManagerFactory("edextPU"); 
        
         //Manejadores que comparten la misma EMF 
                 manejadorUsuario = new ManejadorUsuario(emf);
        manejadorInstituto = new ManejadorInstituto(emf);
        manejadorCurso = new ManejadorCurso(emf);
        manejadorEdicionCurso = new ManejadorEdicionCurso(emf);
        manejadorProgramaFormacion = new ManejadorProgramaFormacion(emf);
        manejadorInscripcionEdicion = new ManejadorInscripcionEdicion(emf);
        manejadorInscripcionPrograma = new ManejadorInscripcionPrograma(emf);
        
    }

    
    public static Controlador getInstance() {

        if (instancia == null) {
            instancia = new Controlador();
        }

        return instancia;
    }


    //Aca implementamos los casos de uso 
     
    @Override
    public void altaInstituto(String nombre) {

        Instituto instituto = new Instituto(nombre);

        manejadorInstituto.addInstituto(instituto);

    }

    @Override
    public List<String> listarNombresInstitutos() {
        List<Instituto> institutos = manejadorInstituto.listarInstitutos();
        List<String> nombres = new ArrayList<>();
        for (Instituto instituto : institutos) {
            nombres.add(instituto.getNombre());
        }
        return nombres;
    }

    @Override
    public boolean existeNickname(String nickname) {
        return manejadorUsuario.existeNickname(nickname);
    }

    @Override
    public boolean existeEmail(String email) {
        return manejadorUsuario.existeEmail(email);
    }

    @Override
    public void altaUsuarioEstudiante(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento) {

        Estudiante estudiante = new Estudiante(nickname, nombre, apellido, email, fechaNacimiento);
        manejadorUsuario.addUsuario(estudiante);
    }

    @Override
    public void altaUsuarioDocente(String nickname, String nombre, String apellido,
            String email, LocalDate fechaNacimiento, String nombreInstituto) {

        Instituto instituto = manejadorInstituto.buscarInstituto(nombreInstituto);
        Docente docente = new Docente(nickname, nombre, apellido, email, fechaNacimiento, instituto);
        manejadorUsuario.addUsuario(docente);
    }

}
