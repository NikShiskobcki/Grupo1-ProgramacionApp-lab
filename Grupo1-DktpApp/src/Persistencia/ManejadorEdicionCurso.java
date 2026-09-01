package Persistencia;

import Logica.DTO.DetalleEdicionCurso;
import Logica.Entidades.Docente;
import java.util.ArrayList;

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
    public DetalleEdicionCurso buscarDetalleEdicion(String nombre) {
        EntityManager em = emf.createEntityManager();
        try {
            EdicionCurso edicion = em.find(EdicionCurso.class, nombre);
            if (edicion == null) {
                return null;
            }

            List<String> docentes = new ArrayList<>();
            for (Docente d : edicion.getDocentes()) {
                docentes.add(d.getNombre() + " " + d.getApellido() + " (" + d.getNickname() + ")");
            }

            return new DetalleEdicionCurso(
                    edicion.getNombre(),
                    edicion.getFechaInicio(),
                    edicion.getFechaFin(),
                    edicion.getCupo(),
                    edicion.getFechaPublicacion(),
                    edicion.getCurso() != null ? edicion.getCurso().getNombre() : "",
                    docentes
            );
        } finally {
            em.close();
        }
    }
}