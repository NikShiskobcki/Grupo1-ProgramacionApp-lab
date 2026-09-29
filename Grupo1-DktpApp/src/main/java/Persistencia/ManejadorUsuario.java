package Persistencia;

import Logica.DTO.DetalleUsuario;
import Logica.DTO.ElementoResumen;
import Logica.DTO.UsuarioEdicion;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.EstadoInscripcion;
import Logica.Entidades.Estudiante;
import Logica.Entidades.Instituto;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.InscripcionPrograma;
import Logica.Entidades.ProgramaFormacion;
import Logica.Entidades.Usuario;
import Logica.excepciones.EntidadNoEncontradaException;
import Logica.excepciones.NombreDuplicadoException;
import Logica.excepciones.PersistenciaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

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
            if (NombreDuplicadoException.esNombreDuplicado(e)){
                throw new NombreDuplicadoException("Ya existe ese usuario",e);
            }else{
                throw new PersistenciaException("No se pudo guardar el usuario", e);
            }

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
                    "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email",
                    Long.class
            );

            query.setParameter("email", email);

            return query.getSingleResult() > 0;

        } finally {
            em.close();
        }
    }

    // Ediciones
    public List<Docente> listarDocentesPorInstituto(String nombreInstituto) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT d FROM Docente d "
                    + "WHERE d.instituto.nombre = :nombreInstituto",
                    Docente.class
            )
                    .setParameter("nombreInstituto", nombreInstituto)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // Consulta de Usuario
    public List<UsuarioResumen> listarUsuarios() {
        EntityManager em = emf.createEntityManager();

        try {
            List<Usuario> usuarios = em.createQuery(
                    "SELECT u FROM Usuario u ORDER BY u.nickname",
                    Usuario.class
            ).getResultList();

            List<UsuarioResumen> resumenes = new ArrayList<>();

            for (Usuario u : usuarios) {
                String tipo = (u instanceof Docente)
                        ? "Docente"
                        : "Estudiante";

                resumenes.add(
                        new UsuarioResumen(
                                u.getNickname(),
                                u.getNombre() + " " + u.getApellido(),
                                tipo,
                                u.getRutaImagen()
                        )
                );
            }

            return resumenes;

        } finally {
            em.close();
        }
    }

    public DetalleUsuario buscarDetalleUsuario(String nickname, String nicknameConsulta) {
        EntityManager em = emf.createEntityManager();

        try {
            Usuario usuario = em.find(Usuario.class, nickname);

            if (usuario == null) {
                return null;
            }
            boolean esPropioPerfil = nickname.equals(nicknameConsulta);

            String tipoUsuario;
            String instituto = null;

            List<ElementoResumen> cursos = new ArrayList<>();
            List<ElementoResumen> ediciones = new ArrayList<>();
            List<ElementoResumen> programas = new ArrayList<>();
            List<ElementoResumen> rechazadas = new ArrayList<>();

            if (usuario instanceof Docente) {

                Docente docente = (Docente) usuario;
                tipoUsuario = "Docente";
                instituto = docente.getInstituto().getNombre();

                Set<String> nombresCursos = new LinkedHashSet<>();

                for (EdicionCurso edicion : docente.getEdiciones()) {

                    ediciones.add(new ElementoResumen(
                            edicion.getNombre(),
                            edicion.getNombre()
                            + " (" + edicion.getFechaInicio()
                            + " a " + edicion.getFechaFin() + ")"
                    ));

                    Curso curso = edicion.getCurso();

                    if (nombresCursos.add(curso.getNombre())) {
                        cursos.add(new ElementoResumen(
                                curso.getNombre(),
                                curso.getNombre()
                                + " - "
                                + curso.getDescripcion()
                        ));
                    }
                }

                if (!nombresCursos.isEmpty()) {

                    List<ProgramaFormacion> programasEncontrados
                            = em.createQuery(
                                    "SELECT DISTINCT p "
                                    + "FROM ProgramaFormacion p "
                                    + "JOIN p.cursos c "
                                    + "WHERE c.nombre IN :nombres",
                                    ProgramaFormacion.class
                            )
                                    .setParameter("nombres", nombresCursos)
                                    .getResultList();

                    for (ProgramaFormacion programa : programasEncontrados) {

                        programas.add(new ElementoResumen(
                                programa.getNombre(),
                                programa.getNombre()
                                + " (" + programa.getFechaInicio()
                                + " a " + programa.getFechaFin() + ")"
                        ));
                    }
                }

            } else {

                Estudiante estudiante = (Estudiante) usuario;
                tipoUsuario = "Estudiante";

                for (InscripcionEdicion inscripcion
                        : estudiante.getInscripcionesEdiciones()) {

                    EdicionCurso edicion = inscripcion.getEdicion();
                    EstadoInscripcion estado = inscripcion.getEstado();

                    ElementoResumen resumen = new ElementoResumen(
                            edicion.getNombre(),
                            edicion.getNombre()
                            + " (inscripto el "
                            + inscripcion.getFechaInscripcion()
                            + ")"
                    );

                    if (estado == EstadoInscripcion.RECHAZADA) {
                        if (esPropioPerfil) {
                            rechazadas.add(resumen);
                        }
                    } else {
                        ediciones.add(resumen);
                    }
                }
            }

            return new DetalleUsuario(
                    usuario.getNickname(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getFechaNacimiento(),
                    tipoUsuario,
                    instituto,
                    cursos,
                    ediciones,
                    programas,
                    usuario.getRutaImagen(),
                    rechazadas
            );

        } finally {
            em.close();
        }
    }

    // Modificar Usuario
    public UsuarioEdicion buscarUsuarioParaEditar(String nickname) {
        EntityManager em = emf.createEntityManager();

        try {
            Usuario usuario = em.find(Usuario.class, nickname);

            if (usuario == null) {
                return null;
            }

            String tipoUsuario;
            String instituto = null;

            if (usuario instanceof Docente) {
                tipoUsuario = "Docente";
                instituto = ((Docente) usuario)
                        .getInstituto()
                        .getNombre();

            } else {
                tipoUsuario = "Estudiante";
            }

            return new UsuarioEdicion(
                    usuario.getNickname(),
                    usuario.getEmail(),
                    tipoUsuario,
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getFechaNacimiento(),
                    instituto,
                    usuario.getRutaImagen()
            );

        } finally {
            em.close();
        }
    }

    public void actualizarUsuario(
            String nickname,
            String nombre,
            String apellido,
            LocalDate fechaNacimiento,
            String nombreInstituto,
            String rutaImagen) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            Usuario usuario = em.find(Usuario.class, nickname);

            if (usuario == null) {
                throw new EntidadNoEncontradaException("El usuario '" + nickname + "' no existe.",null);
            }

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setFechaNacimiento(fechaNacimiento);
            usuario.setRutaImagen(rutaImagen);

            if (usuario instanceof Docente) {

                Instituto instituto = em.find(
                        Instituto.class,
                        nombreInstituto
                );

                ((Docente) usuario).setInstituto(instituto);
            }

            t.commit();

        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }

            if (e instanceof PersistenciaException){
               throw (PersistenciaException) e;
            }
            throw new PersistenciaException("No se pudo guardar el usuario",e);


        } finally {
            em.close();
        }
    }
    
    public List<Estudiante> listarEstudiantes() {

    EntityManager em = emf.createEntityManager();

    try {
        return em.createQuery(
                "SELECT e FROM Estudiante e "
                + "ORDER BY e.nickname",
                Estudiante.class)
                .getResultList();

    } finally {
        em.close();
    }
}
    
    public Estudiante buscarEstudiante(String nickname) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Estudiante.class, nickname);
        } finally {
            em.close();
        }
    }   
    
    public UsuarioResumen autenticar(String nicknameOMail, String contrasenia){
        EntityManager em = emf.createEntityManager();
        try{
            List<Usuario> resultado = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nickname = :dato OR u.email = :dato",
                    Usuario.class)
                    .setParameter("dato", nicknameOMail)
                    .getResultList();
            if (resultado.isEmpty()){
                return null;
            }
            Usuario u = resultado.get(0);
            if (!u.getContrasenia().equals(contrasenia)){
                return null;
            }
            String tipo = (u instanceof Docente) ? "Docente" : "Estudiante";
            return new UsuarioResumen(
                    u.getNickname(),
                    u.getNombre() + " " + u.getApellido(),
                    tipo,
                    u.getRutaImagen()
            );
        }finally{
            em.close();
        }
    }
    
    public void seguirUsuario(String nicknameSeguidor, String nicknameSeguido){
        EntityManager em=emf.createEntityManager();
        EntityTransaction t= em.getTransaction();
        try{
            t.begin();
            Usuario seguidor = em.find(Usuario.class, nicknameSeguidor);
            Usuario seguido = em.find(Usuario.class, nicknameSeguido);
            if (seguidor!=null && seguido!=null && !seguidor.getSeguidos().contains(seguido)){
                seguidor.getSeguidos().add(seguido);
            }
            t.commit();
        }catch(Exception e){
            if (t.isActive()){t.rollback();}
            throw new PersistenciaException("No se pudo seguir al usuario",e);
        }finally{
          em.close();  
        }
    }
    
    public void dejarDeSeguirUsuario(String nicknameSeguidor, String nicknameSeguido){
        EntityManager em=emf.createEntityManager();
        EntityTransaction t= em.getTransaction();
        try{
            t.begin();
            Usuario seguidor = em.find(Usuario.class,nicknameSeguidor);
            if (seguidor !=null){
                seguidor.getSeguidos().removeIf(u->u.getNickname().equals(nicknameSeguido));
            }
            t.commit();
        }catch(Exception e){
            if (t.isActive()){t.rollback();}
            throw new PersistenciaException("No se pudo dejar de seguir al usuario",e);
        }finally{
            em.close();
        }
    }
        
}