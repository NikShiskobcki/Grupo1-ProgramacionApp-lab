package Persistencia;

import Logica.DTO.DetalleUsuario;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.Estudiante;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.InscripcionPrograma;
import Logica.Entidades.ProgramaFormacion;
import Logica.Entidades.Usuario;
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

    /**
     * Lista todos los usuarios (Estudiantes y Docentes) para poblar la
     * pantalla de selección del caso de uso "Consulta de Usuario".
     */
    public List<UsuarioResumen> listarUsuarios() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Usuario> usuarios = em.createQuery(
                    "SELECT u FROM Usuario u ORDER BY u.nickname", Usuario.class).getResultList();

            List<UsuarioResumen> resumenes = new ArrayList<>();
            for (Usuario u : usuarios) {
                String tipo = (u instanceof Docente) ? "Docente" : "Estudiante";
                resumenes.add(new UsuarioResumen(u.getNickname(), u.getNombre() + " " + u.getApellido(), tipo));
            }
            return resumenes;
        } finally {
            em.close();
        }
    }

    /**
     * Arma el detalle completo de un usuario para el caso de uso
     * "Consulta de Usuario". Toda la información se extrae mientras el
     * EntityManager sigue abierto para evitar problemas de lazy loading.
     */
    public DetalleUsuario buscarDetalleUsuario(String nickname) {
        EntityManager em = emf.createEntityManager();
        try {
            Usuario usuario = em.find(Usuario.class, nickname);
            if (usuario == null) {
                return null;
            }

            String tipoUsuario;
            String instituto = null;
            List<String> cursos = new ArrayList<>();
            List<String> ediciones = new ArrayList<>();
            List<String> programas = new ArrayList<>();

            if (usuario instanceof Docente) {
                Docente docente = (Docente) usuario;
                tipoUsuario = "Docente";
                instituto = docente.getInstituto().getNombre();

                Set<String> nombresCursos = new LinkedHashSet<>();
                for (EdicionCurso edicion : docente.getEdiciones()) {
                    ediciones.add(edicion.getNombre() + " (" + edicion.getFechaInicio()
                            + " a " + edicion.getFechaFin() + ")");
                    Curso curso = edicion.getCurso();
                    if (nombresCursos.add(curso.getNombre())) {
                        cursos.add(curso.getNombre() + " - " + curso.getDescripcion());
                    }
                }

                if (!nombresCursos.isEmpty()) {
                    List<ProgramaFormacion> programasEncontrados = em.createQuery(
                            "SELECT DISTINCT p FROM ProgramaFormacion p JOIN p.cursos c "
                            + "WHERE c.nombre IN :nombres", ProgramaFormacion.class)
                            .setParameter("nombres", nombresCursos)
                            .getResultList();
                    for (ProgramaFormacion programa : programasEncontrados) {
                        programas.add(programa.getNombre() + " (" + programa.getFechaInicio()
                                + " a " + programa.getFechaFin() + ")");
                    }
                }

            } else {
                Estudiante estudiante = (Estudiante) usuario;
                tipoUsuario = "Estudiante";

                for (InscripcionEdicion inscripcion : estudiante.getInscripcionesEdiciones()) {
                    EdicionCurso edicion = inscripcion.getEdicion();
                    ediciones.add(edicion.getNombre() + " (inscripto el "
                            + inscripcion.getFechaInscripcion() + ")");
                }
                for (InscripcionPrograma inscripcion : estudiante.getInscripcionesProgramas()) {
                    ProgramaFormacion programa = inscripcion.getPrograma();
                    programas.add(programa.getNombre() + " (inscripto el "
                            + inscripcion.getFechaInscripcion() + ")");
                }
            }

            return new DetalleUsuario(usuario.getNickname(), usuario.getNombre(), usuario.getApellido(),
                    usuario.getEmail(), usuario.getFechaNacimiento(), tipoUsuario, instituto,
                    cursos, ediciones, programas);
        } finally {
            em.close();
        }
    }
}
