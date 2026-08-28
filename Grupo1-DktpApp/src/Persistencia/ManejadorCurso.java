package Persistencia;

import Logica.Entidades.Curso;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ManejadorCurso {

    private final EntityManagerFactory emf;

    public ManejadorCurso(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void addCurso(Curso curso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.persist(curso);
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

    public Curso buscarPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Curso.class, nombre);
        } finally {
            em.close();
        }
    }

    public List<Curso> listarCursos() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Curso c",
                    Curso.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public List<Curso> listarCursosPorInstituto(String nombreInstituto) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Curso c "
                    + "WHERE c.instituto.nombre = :nombreInstituto",
                    Curso.class)
                    .setParameter("nombreInstituto", nombreInstituto)
                    .getResultList();

        } finally {
            em.close();
        }
    }
}