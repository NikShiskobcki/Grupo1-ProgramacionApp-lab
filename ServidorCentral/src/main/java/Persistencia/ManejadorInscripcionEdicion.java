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
                    + "AND i.edicion.nombre = :nombreEdicion "
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
    
    public void seleccionarEstudiantesEdicion(
        String nicknameDocente,
        String nombreEdicion,
        List<Long> aceptadas,
        List<Long> rechazadas) {

    if (nicknameDocente == null || nicknameDocente.trim().isEmpty()) {
        throw new IllegalArgumentException(
                "Debe indicar el docente que realiza la selección.");
    }

    if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
        throw new IllegalArgumentException(
                "Debe seleccionar una edición.");
    }

    if (aceptadas == null || rechazadas == null) {
        throw new IllegalArgumentException(
                "Las listas de selección no pueden ser nulas.");
    }

    // Copias propias: no modificamos las listas recibidas.
    List<Long> idsAceptadas = new ArrayList<>(aceptadas);
    List<Long> idsRechazadas = new ArrayList<>(rechazadas);

    java.util.Set<Long> ids = new java.util.HashSet<>();

    for (Long id : idsAceptadas) {
        if (id == null || id <= 0 || !ids.add(id)) {
            throw new IllegalArgumentException(
                    "La selección contiene inscripciones inválidas o repetidas.");
        }
    }

    for (Long id : idsRechazadas) {
        if (id == null || id <= 0 || !ids.add(id)) {
            throw new IllegalArgumentException(
                    "Una inscripción no puede aparecer repetida "
                    + "ni estar aceptada y rechazada a la vez.");
        }
    }

    if (ids.isEmpty()) {
        throw new IllegalArgumentException(
                "Seleccioná al menos una inscripción para aceptar o rechazar.");
    }

    EntityManager em = emf.createEntityManager();
    EntityTransaction transaccion = em.getTransaction();

    try {
        transaccion.begin();

        // Serializa las selecciones concurrentes sobre esta edición.
        Logica.Entidades.EdicionCurso edicion = em.find(
                Logica.Entidades.EdicionCurso.class,
                nombreEdicion,
                javax.persistence.LockModeType.PESSIMISTIC_WRITE
        );

        if (edicion == null) {
            throw new IllegalArgumentException(
                    "La edición seleccionada no existe.");
        }

        boolean participa = false;

        for (Logica.Entidades.Docente docente : edicion.getDocentes()) {
            if (docente.getNickname().equals(nicknameDocente)) {
                participa = true;
                break;
            }
        }

        if (!participa) {
            throw new IllegalArgumentException(
                    "Solo un docente participante puede seleccionar "
                    + "estudiantes para esta edición.");
        }

        LocalDate hoy = LocalDate.now(
                java.time.ZoneId.of("America/Montevideo"));

        if (edicion.getFechaInicio() == null
                || edicion.getFechaFin() == null
                || hoy.isBefore(edicion.getFechaInicio())
                || hoy.isAfter(edicion.getFechaFin())) {

            throw new IllegalArgumentException(
                    "La selección solo puede realizarse "
                    + "sobre una edición vigente.");
        }

        List<Long> idsOrdenados = new ArrayList<>(ids);
        java.util.Collections.sort(idsOrdenados);

        java.util.Map<Long, InscripcionEdicion> inscripciones =
                new java.util.HashMap<>();

        // Primero validamos y bloqueamos todos los registros seleccionados.
        for (Long id : idsOrdenados) {
            InscripcionEdicion inscripcion = em.find(
                    InscripcionEdicion.class,
                    id,
                    javax.persistence.LockModeType.PESSIMISTIC_WRITE
            );

            if (inscripcion == null) {
                throw new IllegalArgumentException(
                        "Una de las inscripciones ya no existe. "
                        + "Recargá la página.");
            }

            if (!nombreEdicion.equals(
                    inscripcion.getEdicion().getNombre())) {

                throw new IllegalArgumentException(
                        "Todas las inscripciones deben pertenecer "
                        + "a la edición seleccionada.");
            }

            if (inscripcion.getEstado() != EstadoInscripcion.INSCRIPTO) {
                throw new IllegalArgumentException(
                        "Una de las inscripciones ya fue resuelta. "
                        + "Recargá la página para ver su estado actual.");
            }

            inscripciones.put(id, inscripcion);
        }

        long aceptadosActuales = em.createQuery(
                "SELECT COUNT(i) FROM InscripcionEdicion i "
                + "WHERE i.edicion.nombre = :edicion "
                + "AND i.estado = :estado",
                Long.class)
                .setParameter("edicion", nombreEdicion)
                .setParameter("estado", EstadoInscripcion.ACEPTADA)
                .getSingleResult();

        Integer cupo = edicion.getCupo();

        if (cupo != null
                && aceptadosActuales + idsAceptadas.size() > cupo) {

            throw new IllegalArgumentException(
                    "La selección supera el cupo de la edición. "
                    + "Cupo: " + cupo
                    + ". Ya aceptados: " + aceptadosActuales
                    + ". Nuevas aceptaciones: " + idsAceptadas.size() + ".");
        }

        // Aplicamos los cambios recién después de validar todo.
        for (Long id : idsAceptadas) {
            inscripciones.get(id).setEstado(EstadoInscripcion.ACEPTADA);
        }

        for (Long id : idsRechazadas) {
            inscripciones.get(id).setEstado(EstadoInscripcion.RECHAZADA);
        }

        transaccion.commit();

    } catch (Exception e) {
        if (transaccion.isActive()) {
            transaccion.rollback();
        }

        if (e instanceof IllegalArgumentException) {
            throw (IllegalArgumentException) e;
        }

        throw new PersistenciaException(
                "No se pudo guardar la selección de estudiantes.", e);

    } finally {
        em.close();
    }
}
}