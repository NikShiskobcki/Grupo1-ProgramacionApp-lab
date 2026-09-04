package Persistencia;

import Logica.Entidades.EdicionCurso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import Logica.DTO.DetalleEdicionCurso;
import Logica.Entidades.Docente;
import java.time.LocalDate;
import java.util.ArrayList;

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
    
   public List<EdicionCurso> listarEdicionesVigentesPorCurso(String nombreCurso) {

    EntityManager em = emf.createEntityManager();

    try {
        return em.createQuery(
                "SELECT e FROM EdicionCurso e "
                + "WHERE e.curso.nombre = :nombreCurso "
                + "AND :hoy BETWEEN e.fechaInicio AND e.fechaFin "
                + "ORDER BY e.nombre",
                EdicionCurso.class)
                .setParameter("nombreCurso", nombreCurso)
                .setParameter("hoy", LocalDate.now())
                .getResultList();

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
            docentes.add(
                d.getNombre() + " " +
                d.getApellido() + " (" +
                d.getNickname() + ")"
            );
        }

        return new DetalleEdicionCurso(
            edicion.getNombre(),
            edicion.getFechaInicio(),
            edicion.getFechaFin(),
            edicion.getCupo(),
            edicion.getFechaPublicacion(),
            edicion.getCurso() != null
                ? edicion.getCurso().getNombre()
                : "",
            docentes
        );

    } finally {
        em.close();
    }
}
    public EdicionCurso buscarEdicionVigentePorCurso(String nombreCurso) {

    EntityManager em = emf.createEntityManager();

    try {
        List<EdicionCurso> ediciones = em.createQuery(
                "SELECT e FROM EdicionCurso e "
                + "WHERE e.curso.nombre = :nombreCurso "
                + "AND :hoy BETWEEN e.fechaInicio AND e.fechaFin",
                EdicionCurso.class)
                .setParameter("nombreCurso", nombreCurso)
                .setParameter("hoy", LocalDate.now())
                .getResultList();

        if (ediciones.isEmpty()) {
            return null;
        }

        return ediciones.get(0);

    } finally {
        em.close();
    }
}
    public void agregarDocente(
        String nombreEdicion,
        String nicknameDocente) {

    EntityManager em = emf.createEntityManager();
    EntityTransaction t = em.getTransaction();

    try {
        t.begin();

        EdicionCurso edicion =
                em.find(EdicionCurso.class, nombreEdicion);

        Docente docente =
                em.find(Docente.class, nicknameDocente);

        if (edicion != null
                && docente != null
                && !edicion.getDocentes().contains(docente)) {

            edicion.getDocentes().add(docente);
        }

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
}