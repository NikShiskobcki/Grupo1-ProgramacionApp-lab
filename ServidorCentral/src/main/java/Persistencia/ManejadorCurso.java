package Persistencia;

import Logica.DTO.DetalleCurso;
import Logica.DTO.CursoTendencia;
import Logica.Entidades.Categoria;
import Logica.Entidades.ProgramaFormacion;
import java.util.ArrayList;

import Logica.Entidades.Curso;
import Logica.excepciones.NombreDuplicadoException;
import Logica.excepciones.PersistenciaException;
import Logica.excepciones.RelacionInvalidaException;
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
            if (NombreDuplicadoException.esNombreDuplicado(e)){
                throw new NombreDuplicadoException("Ya existe un curso con ese nombre",e);
  
            }else if (RelacionInvalidaException.esRelacionInvalida(e)){
                throw new RelacionInvalidaException("El instituto no existe", e);
            }else{
                throw new PersistenciaException("No se pudo guardar el curso", e);
            }
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
            
            List<String> categorias = new ArrayList<>();
            for (Categoria cat:curso.getCategorias()){
                categorias.add(cat.getNombre());
            }

            
            List<String> ediciones = em.createQuery(
                    "SELECT e.nombre FROM EdicionCurso e WHERE e.curso.nombre = :nombreCurso ORDER BY e.nombre",
                    String.class)
                    .setParameter("nombreCurso", nombre)
                    .getResultList();

            
            List<ProgramaFormacion> programas = em.createQuery(
                    "SELECT DISTINCT p FROM ProgramaFormacion p JOIN p.cursos c WHERE c.nombre = :nombreCurso",
                    ProgramaFormacion.class)
                    .setParameter("nombreCurso", nombre)
                    .getResultList();

            List<String> nombresProgramas = new ArrayList<>();
            for (ProgramaFormacion pf : programas) {
                nombresProgramas.add(pf.getNombre());
            }

            Long cantidadInscriptos = em.createQuery(
                    "SELECT COUNT(i) FROM InscripcionEdicion i "
                    + "WHERE i.edicion.curso.nombre = :nombreCurso",
                    Long.class)
                    .setParameter("nombreCurso", nombre)
                    .getSingleResult();

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
                    nombresProgramas,
                    cantidadInscriptos != null ? cantidadInscriptos : 0L,
                    categorias, curso.getRutaImagen()
            );
        } finally {
            em.close();
        }
    }

     
    public void agregarPrevia(String nombreCurso,String nombrePrevia) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            Curso curso = em.find(Curso.class, nombreCurso);
            Curso previa = em.find(Curso.class, nombrePrevia);

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
            throw new PersistenciaException("No se pudo guardar la previa",e);
        } finally {
            em.close();
        }
    }
    
    public void agregarCategoria(String nombreCurso, String nombreCategoria) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Curso curso = em.find(Curso.class, nombreCurso);
            Categoria categoria = em.find(Categoria.class, nombreCategoria);
            if (curso != null && categoria != null
                    && !curso.getCategorias().contains(categoria)) {
                curso.getCategorias().add(categoria);
            }
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            throw new PersistenciaException("Error al agregar la categoria al curso", e);
        } finally {
            em.close();
        }
    }
    
    /** Fija la cantidad de visitas del curso (la usa la carga de datos de prueba). */
    public void establecerVisitas(String nombreCurso, long visitas) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.createQuery(
                    "UPDATE Curso c SET c.visitas = :visitas WHERE c.nombre = :nombre")
                    .setParameter("visitas", visitas)
                    .setParameter("nombre", nombreCurso)
                    .executeUpdate();
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            throw new PersistenciaException(
                    "No se pudieron establecer las visitas del curso", e);
        } finally {
            em.close();
        }
    }

    public void registrarVisita(String nombreCurso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.createQuery(
                    "UPDATE Curso c "
                    + "SET c.visitas = COALESCE(c.visitas, 0) + 1 "
                    + "WHERE c.nombre = :nombre")
                    .setParameter("nombre", nombreCurso)
                    .executeUpdate();
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            throw new PersistenciaException(
                    "No se pudo registrar la visita al curso", e);
        } finally {
            em.close();
        }
    }

    public List<CursoTendencia> listarTendencias(int cantidad) {
        EntityManager em = emf.createEntityManager();

        try {
            List<Curso> cursos = em.createQuery(
                    "SELECT c FROM Curso c "
                    + "WHERE c.visitas > 0 "
                    + "ORDER BY c.visitas DESC, c.nombre",
                    Curso.class)
                    .setMaxResults(cantidad)
                    .getResultList();

            List<CursoTendencia> resultado = new ArrayList<>();
            for (Curso c : cursos) {
                resultado.add(new CursoTendencia(
                        c.getNombre(),
                        c.getInstituto() == null
                        ? null : c.getInstituto().getNombre(),
                        c.getRutaImagen(),
                        c.getVisitas()));
            }
            return resultado;

        } finally {
            em.close();
        }
    }
}