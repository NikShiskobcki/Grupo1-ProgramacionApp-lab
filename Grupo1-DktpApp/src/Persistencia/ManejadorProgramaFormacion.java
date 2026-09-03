package Persistencia;

import Logica.DTO.CursoResumen;
import Logica.DTO.DetalleProgramaFormacion;
import Logica.Entidades.Curso;
import Logica.Entidades.ProgramaFormacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class ManejadorProgramaFormacion {

    private final EntityManagerFactory emf;

    public ManejadorProgramaFormacion(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public boolean existePrograma(String nombre){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(ProgramaFormacion.class, nombre)!=null;
        }finally{
            em.close();
        }
    }
    
    public void addPrograma(ProgramaFormacion programa){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(programa);
            t.commit();
        }catch (Exception e){
            if (t.isActive()){
                t.rollback();
            }
            throw e;
        }finally{
            em.close();
        }
    }
    
    public void agregarCursoAPrograma(String nombrePrograma, String nombreCurso){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            ProgramaFormacion programa = em.find(ProgramaFormacion.class,nombrePrograma);
            Curso curso = em.find(Curso.class, nombreCurso);
            if (programa != null
                && curso != null
                && !programa.getCursos().contains(curso)){
            programa.getCursos().add(curso);}
            t.commit();
        }catch (Exception e){
            if (t.isActive()){
                t.rollback();
            }
            throw e;
        }finally{
            em.close();
        }
    }
    
    public List<ProgramaFormacion> listarProgramas(){
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT p FROM ProgramaFormacion p ORDER BY p.nombre", ProgramaFormacion.class).getResultList();
        }finally {
            em.close();
        }
    }
    
    public DetalleProgramaFormacion buscarDetallePrograma(String nombre){
        EntityManager em = emf.createEntityManager();
        try{
            ProgramaFormacion programa = em.find(ProgramaFormacion.class, nombre);
            if (programa == null) return null;
            List<CursoResumen> cursos = new ArrayList<>();
            for (Curso curso : programa.getCursos()){
                cursos.add(new CursoResumen(curso.getNombre(), curso.getInstituto().getNombre()));
            }
            return new DetalleProgramaFormacion(
                programa.getNombre(),
                programa.getDescripcion(),
                programa.getFechaInicio(),
                programa.getFechaFin(),
                programa.getFechaAlta(),
                cursos
            );
        }finally{
            em.close();
        }
    }
}
