package Persistencia;

import Logica.Entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import Logica.Entidades.Docente;
import java.util.List;

public class ManejadorUsuario {

    private final EntityManagerFactory emf;

    public ManejadorUsuario(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void addUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.persist(usuario);
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

    public boolean existeNickname(String nickname) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, nickname) != null;
        } finally {
            em.close();
        }
    }

    public boolean existeEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
    
    public List<Docente> listarDocentesPorInstituto(String nombreInstituto) {

    EntityManager em = emf.createEntityManager();

    try {
        return em.createQuery(
                "SELECT d FROM Docente d "
                + "WHERE d.instituto.nombre = :nombreInstituto",
                Docente.class)
                .setParameter("nombreInstituto", nombreInstituto)
                .getResultList();

    } finally {
        em.close();
    }
}
}
