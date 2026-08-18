/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.controladores;

import Logica.Entidades.Instituto;
import Persistencia.ManejadorCurso;
import Persistencia.ManejadorEdicionCurso;
import Persistencia.ManejadorInscripcionEdicion;
import Persistencia.ManejadorInscripcionPrograma;
import Persistencia.ManejadorInstituto;
import Persistencia.ManejadorProgramaFormacion;
import Persistencia.ManejadorUsuario;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import Logica.Entidades.Curso;
import java.util.List;
import java.time.LocalDate;


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
public List<Instituto> listarInstitutos() {
    return manejadorInstituto.listarInstitutos();
}

@Override
public List<Curso> listarCursos() {
    return manejadorCurso.listarCursos();
}

@Override
public boolean existeCurso(String nombre) {
    return manejadorCurso.buscarPorNombre(nombre) != null;
}

@Override
public void altaCurso(String nombre, String descripcion, int duracion,
                       int cantidadHoras, int creditos, String url,
                       LocalDate fechaAlta, Instituto instituto,
                       List<Curso> previas) {

    Curso curso = new Curso(nombre, descripcion, duracion, cantidadHoras,
                             creditos, url, fechaAlta, instituto);
    curso.setPrevias(previas);
    manejadorCurso.addCurso(curso);
}
    
}
