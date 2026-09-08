package Persistencia;

import Logica.DTO.DetalleCurso;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.ProgramaFormacion;
import java.util.ArrayList;

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
    
     public DetalleCurso buscarDetalleCurso(String nombre) {
        EntityManager em = emf.createEntityManager();
        try {
            Curso curso = em.find(Curso.class, nombre);
            if (curso == null) {
                return null;
            }

            List<String> previas = new ArrayList<>();
            for (Curso previa : curso.getPrevias()) {
                previas.add(previa.getNombre());
            }

            List<String> ediciones = new ArrayList<>();
            for (EdicionCurso edicion : curso.getEdiciones()) {
                ediciones.add(edicion.getNombre());
            }

            List<ProgramaFormacion> programas = em.createQuery(
                    "SELECT DISTINCT p FROM ProgramaFormacion p JOIN p.cursos c WHERE c.nombre = :nombreCurso",
                    ProgramaFormacion.class)
                    .setParameter("nombreCurso", nombre)
                    .getResultList();

            List<String> nombresProgramas = new ArrayList<>();
            for (ProgramaFormacion pf : programas) {
                nombresProgramas.add(pf.getNombre());
            }

            return new DetalleCurso(
                    curso.getNombre(),
                    curso.getDescripcion(),
                    curso.getDuracion(),
                    curso.getCantidadHoras(),
                    curso.getCreditos(),
                    curso.getUrl(),
                    curso.getFechaAlta(),
                    curso.getInstituto() != null ? curso.getInstituto().getNombre() : "",
                    previas,
                    ediciones,
                    nombresProgramas
            );
        } finally {
            em.close();
        }
    }
     
     public void agregarPrevia(
        String nombreCurso,
        String nombrePrevia) {

    EntityManager em = emf.createEntityManager();
    EntityTransaction t = em.getTransaction();

    try {
        t.begin();

        Curso curso =
                em.find(Curso.class, nombreCurso);

        Curso previa =
                em.find(Curso.class, nombrePrevia);

        if (curso != null
                && previa != null
                && !curso.getPrevias().contains(previa)) {

            curso.getPrevias().add(previa);
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