package Persistencia;

import Logica.Entidades.ValoracionCurso;
import Logica.excepciones.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class ManejadorValoracionCurso {

    private final EntityManagerFactory emf;

    public ManejadorValoracionCurso(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /** Puntaje que el estudiante asignó al curso, o null si no lo valoró. */
    public Integer buscarPuntaje(String nicknameEstudiante, String nombreCurso) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Integer> r = em.createQuery(
                    "SELECT v.puntaje FROM ValoracionCurso v "
                    + "WHERE v.estudiante.nickname = :nickname "
                    + "AND v.curso.nombre = :curso", Integer.class)
                    .setParameter("nickname", nicknameEstudiante)
                    .setParameter("curso", nombreCurso)
                    .getResultList();
            return r.isEmpty() ? null : r.get(0);
        } finally {
            em.close();
        }
    }

    /** Promedio de todas las valoraciones del curso, o null si no tiene. */
    public Double promedio(String nombreCurso) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT AVG(v.puntaje) FROM ValoracionCurso v "
                    + "WHERE v.curso.nombre = :curso", Double.class)
                    .setParameter("curso", nombreCurso)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    /** Cantidad de valoraciones del curso por puntaje: posición 0 = 1 estrella ... 4 = 5 estrellas. */
    public int[] contarPorPuntaje(String nombreCurso) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Object[]> filas = em.createQuery(
                    "SELECT v.puntaje, COUNT(v) FROM ValoracionCurso v "
                    + "WHERE v.curso.nombre = :curso GROUP BY v.puntaje", Object[].class)
                    .setParameter("curso", nombreCurso)
                    .getResultList();
            int[] resultado = new int[5];
            for (Object[] fila : filas) {
                int puntaje = ((Number) fila[0]).intValue();
                if (puntaje >= 1 && puntaje <= 5) {
                    resultado[puntaje - 1] = ((Number) fila[1]).intValue();
                }
            }
            return resultado;
        } finally {
            em.close();
        }
    }

    public long contar(String nombreCurso) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(v) FROM ValoracionCurso v "
                    + "WHERE v.curso.nombre = :curso", Long.class)
                    .setParameter("curso", nombreCurso)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Guarda la valoración. Si dos pedidos simultáneos intentan valorar el mismo curso,
     * la restricción única de la base rechaza el segundo y se informa como duplicado.
     */
    public void agregar(ValoracionCurso valoracion) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(valoracion);
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            if (buscarPuntaje(valoracion.getEstudiante().getNickname(),
                    valoracion.getCurso().getNombre()) != null) {
                throw new IllegalArgumentException("Ya valoraste este curso.");
            }
            throw new PersistenciaException("No se pudo guardar la valoración", e);
        } finally {
            em.close();
        }
    }
}
