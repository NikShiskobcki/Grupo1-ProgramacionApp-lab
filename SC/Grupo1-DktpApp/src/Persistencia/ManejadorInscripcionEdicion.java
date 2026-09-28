package Persistencia;

import Logica.DTO.InscripcionResumen;
import Logica.DTO.ResultadoInscripcion;
import Logica.Entidades.EstadoInscripcion;
import Logica.Entidades.Estudiante;
import Logica.Entidades.InscripcionEdicion;
import Logica.excepciones.EntidadNoEncontradaException;
import Logica.excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.ArrayList;
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
                    + "AND i.edicion.nombre = :nombreEdicion"
                    + "AND (i.estado IS NULL OR i.estado <> Logica.Entidades.EstadoInscripcion.RECHAZADA)",
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
    
    //ranking de rechazos
    public long contarRechazosPrevios(String nicknameEstudiante, String nombreCurso){
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery(
                "SELECT COUNT(i) FROM InscripcionEdicion i "
                + "WHERE i.estudiante.nickname = :nickname "
                + "AND i.edicion.curso.nombre = :nombreCurso "
                + "AND i.estado = Logica.Entidades.EstadoInscripcion.RECHAZADA "
                + "AND i.edicion.fechaFin < :hoy",
                Long.class)
                .setParameter("nickname", nicknameEstudiante)
                .setParameter("nombreCurso", nombreCurso)
                .setParameter("hoy", LocalDate.now())
                .getSingleResult();
        }finally{
            em.close();
        }
    }
    
    public List<InscripcionResumen> listarInscripcionesPorEdicion(String nombreEdicion, boolean ordenarPorPrioridad){
        EntityManager em = emf.createEntityManager();
        try{
            List<InscripcionEdicion> inscripciones = em.createQuery(
                "SELECT i FROM InscripcionEdicion i "
                + "WHERE i.edicion.nombre = :nombreEdicion",
                InscripcionEdicion.class)
                .setParameter("nombreEdicion", nombreEdicion)
                .getResultList();
            
            String nombreCurso = null;
            if (!inscripciones.isEmpty()){
                nombreCurso = inscripciones.get(0).getEdicion().getCurso().getNombre();
            }
            
            List<InscripcionResumen> resumenes = new ArrayList<>();
            for (InscripcionEdicion i:inscripciones){
                Estudiante est = i.getEstudiante();
                long ipr = contarRechazosPrevios(est.getNickname(), nombreCurso);
                resumenes.add(new InscripcionResumen(
                            i.getId(),
                            est.getNickname(),
                            est.getNombre() + " "+ est.getApellido(),
                            i.getFechaInscripcion(),
                            i.getEstado().name(),
                            ipr*0.5
                ));
            }
            if (ordenarPorPrioridad){
                resumenes.sort((a,b)-> Double.compare(b.getPrioridad(), a.getPrioridad()));
            }else{
                resumenes.sort((a,b)-> a.getFechaInscripcion().compareTo(b.getFechaInscripcion()));
            }
                
            return resumenes;
        }finally{
            em.close();
        }
    }
    
    public void actualizarEstado(Long idInscripcion, EstadoInscripcion nuevoEstado){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            InscripcionEdicion inscripcion = em.find(InscripcionEdicion.class, idInscripcion);
            if (inscripcion == null){
                throw new EntidadNoEncontradaException("No existe una inscripcion con ese id",null);
            }
            inscripcion.setEstado(nuevoEstado);
            t.commit();
        }catch(Exception e){
            if (t.isActive()){
                t.rollback();
            }
            if(e instanceof EntidadNoEncontradaException){
                throw (EntidadNoEncontradaException) e;
            }
            throw new PersistenciaException("No se pudo actualizar la inscripcion", e);
        }finally{
            em.close();
        }
    }
    
    public List<ResultadoInscripcion> listarResultadosPorEstudiante(String nicknameEstudiante){
        EntityManager em = emf.createEntityManager();
        try{
            List<InscripcionEdicion> inscripciones=em.createQuery(
                "SELECT i FROM InscripcionEdicion i "
                + "WHERE i.estudiante.nickname = :nickname "
                + "ORDER BY i.fechaInscripcion DESC",
                InscripcionEdicion.class)
                .setParameter("nickname",nicknameEstudiante)
                .getResultList();
            
            List<ResultadoInscripcion> resultados = new ArrayList<>();
            for (InscripcionEdicion i : inscripciones){
                resultados.add(new ResultadoInscripcion(
                        i.getEdicion().getNombre(),
                        i.getEdicion().getCurso().getNombre(),
                        i.getFechaInscripcion(),
                        i.getEstado().name()
                ));  
            }
            return resultados;
        }finally{
            em.close();
        }
    }
}