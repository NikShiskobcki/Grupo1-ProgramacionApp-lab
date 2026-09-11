package Persistencia;

import Logica.Entidades.InscripcionEdicion;
import Logica.excepciones.EntidadNoEncontradaException;
import Logica.excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class ManejadorInscripcionEdicion {

    private final EntityManagerFactory emf;

    public ManejadorInscripcionEdicion(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public InscripcionEdicion buscarInscripcion(String nicknameEstudiante,String nombreEdicion) {
        EntityManager em = emf.createEntityManager();
        try {
            List<InscripcionEdicion> inscripciones = em.createQuery(
                    "SELECT i FROM InscripcionEdicion i "
                    + "WHERE i.estudiante.nickname = :nickname "
                    + "AND i.edicion.nombre = :nombreEdicion",
                    InscripcionEdicion.class)
                    .setParameter("nickname", nicknameEstudiante)
                    .setParameter("nombreEdicion", nombreEdicion)
                    .getResultList();

            if (inscripciones.isEmpty()) {
                return null;
            }

            return inscripciones.get(0);

        } finally {
            em.close();
        }
    }
    
    public void addInscripcion(InscripcionEdicion inscripcion) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.persist(inscripcion);
            t.commit();

        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            throw new PersistenciaException("No se pudo guardar la inscripcion",e);

        } finally {
            em.close();
        }
    }
    
    public void modificarInscripcion(Long idInscripcion,LocalDate nuevaFecha) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            InscripcionEdicion inscripcion = em.find(InscripcionEdicion.class, idInscripcion);
            
            if (inscripcion == null){
                throw new EntidadNoEncontradaException("No existe una inscripcion con esa id",null);
            }
            inscripcion.setFechaInscripcion(nuevaFecha);

            t.commit();

        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            if (e instanceof PersistenciaException){
                throw (PersistenciaException) e;
            }
            throw new PersistenciaException("No se pudo modificar la inscripcion",e);

        } finally {
            em.close();
        }
    }
}