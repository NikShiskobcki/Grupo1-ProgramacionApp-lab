package Persistencia;

import Logica.Entidades.EdicionCurso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class ManejadorEdicionCurso {

    private final EntityManagerFactory emf;

    public ManejadorEdicionCurso(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EdicionCurso buscarPorNombre(String nombre) {

    EntityManager em = emf.createEntityManager();

    try {
        return em.find(EdicionCurso.class, nombre);

    } finally {
        em.close();
    }
}
    
    public void addEdicion(EdicionCurso edicion) {

    EntityManager em = emf.createEntityManager();
    EntityTransaction t = em.getTransaction();

    try {
        t.begin();

        em.persist(edicion);

        t.commit();

    } catch (Exception e) {

        if (t.isActive()) {
            t.rollback();
        }

        throw e;

    } finally {
        em.close();
    }
}
    
    public List<EdicionCurso> listarEdicionesPorCurso(String nombreCurso) {

    EntityManager em = emf.createEntityManager();

    try {
        return em.createQuery(
                "SELECT e FROM EdicionCurso e "
                + "WHERE e.curso.nombre = :nombreCurso "
                + "ORDER BY e.nombre",
                EdicionCurso.class)
                .setParameter("nombreCurso", nombreCurso)
                .getResultList();

    } finally {
        em.close();
    }
}
    
    public EdicionCurso buscarEdicionCompleta(String nombre) {

    EntityManager em = emf.createEntityManager();

    try {
        return em.createQuery(
                "SELECT DISTINCT e FROM EdicionCurso e "
                + "LEFT JOIN FETCH e.docentes "
                + "WHERE e.nombre = :nombre",
                EdicionCurso.class)
                .setParameter("nombre", nombre)
                .getSingleResult();

    } finally {
        em.close();
    }
}
}