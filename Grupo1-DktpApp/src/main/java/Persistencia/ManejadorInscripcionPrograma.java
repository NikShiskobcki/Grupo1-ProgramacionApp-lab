package Persistencia;

import Logica.Entidades.InscripcionPrograma;
import Logica.excepciones.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class ManejadorInscripcionPrograma {

    private final EntityManagerFactory emf;

    public ManejadorInscripcionPrograma(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public void addInscripcion(InscripcionPrograma inscripcion){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        
        try{
            t.begin();
            em.persist(inscripcion);
            t.commit();
        }catch(Exception e){
            if (t.isActive()){
                t.rollback();
            }
            throw new PersistenciaException("No se pudo guardar la inscripcion",e);
        }finally{
            em.close();
        }
    }
    
    public InscripcionPrograma buscarInscripcion(String nicknameEstudiante, String nombrePrograma) {
        EntityManager em = emf.createEntityManager();
        try {
            List<InscripcionPrograma> inscripciones = em.createQuery(
                    "SELECT i FROM InscripcionPrograma i "
                    + "WHERE i.estudiante.nickname = :nickname "
                    + "AND i.programa.nombre = :nombrePrograma",
                    InscripcionPrograma.class)
                    .setParameter("nickname", nicknameEstudiante)
                    .setParameter("nombrePrograma", nombrePrograma)
                    .getResultList();
            if (inscripciones.isEmpty()) {
                return null;
            }
            return inscripciones.get(0);
        } finally {
            em.close();
        }
    }
}
