package servlets;

import Logica.DTO.UsuarioEdicion;
import Logica.DTO.UsuarioResumen;
import Logica.Entidades.Curso;
import Logica.Entidades.Instituto;
import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Caso de uso: Alta de Curso (actor: Docente). */
@WebServlet("/alta-curso")
public class AltaCursoServlet extends HttpServlet {

    private final IControlador control = Fabrica.getInstance().getIControlador();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioResumen docente = docenteLogueado(request);
        if (docente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        cargarFormulario(request, docente);
        request.getRequestDispatcher("/altaCurso.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        UsuarioResumen docente = docenteLogueado(request);
        if (docente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String nombre = trim(request.getParameter("nombre"));
        String descripcion = trim(request.getParameter("descripcion"));
        String duracionStr = trim(request.getParameter("duracion"));
        String horasStr = trim(request.getParameter("cantidadHoras"));
        String creditosStr = trim(request.getParameter("creditos"));
        String url = trim(request.getParameter("url"));
        String nombreInstituto = trim(request.getParameter("instituto"));
        String[] previasSel = request.getParameterValues("previas");
        String[] categoriasSel = request.getParameterValues("categorias");

        // Se conservan los datos ingresados para que el docente pueda corregirlos
        request.setAttribute("v_nombre", nombre);
        request.setAttribute("v_descripcion", descripcion);
        request.setAttribute("v_duracion", duracionStr);
        request.setAttribute("v_horas", horasStr);
        request.setAttribute("v_creditos", creditosStr);
        request.setAttribute("v_url", url);
        request.setAttribute("v_instituto", nombreInstituto);
        request.setAttribute("v_previas", previasSel == null ? new ArrayList<String>() : Arrays.asList(previasSel));
        request.setAttribute("v_categorias", categoriasSel == null ? new ArrayList<String>() : Arrays.asList(categoriasSel));

        // ---- Validaciones ----
        if (nombre.isEmpty() || descripcion.isEmpty() || duracionStr.isEmpty() || horasStr.isEmpty()
                || creditosStr.isEmpty() || url.isEmpty() || nombreInstituto.isEmpty()) {
            error(request, response, docente, "Debe completar todos los campos obligatorios.");
            return;
        }

        int duracion, horas, creditos;
        try {
            duracion = Integer.parseInt(duracionStr);
            horas = Integer.parseInt(horasStr);
            creditos = Integer.parseInt(creditosStr);
        } catch (NumberFormatException e) {
            error(request, response, docente, "Duración, cantidad de horas y créditos deben ser números enteros.");
            return;
        }
        if (duracion <= 0 || horas <= 0 || creditos <= 0) {
            error(request, response, docente, "Duración, cantidad de horas y créditos deben ser mayores que cero.");
            return;
        }

        String urlLower = url.toLowerCase();
        if (!urlLower.startsWith("http://") && !urlLower.startsWith("https://")) {
            error(request, response, docente, "La URL debe comenzar con http:// o https://");
            return;
        }

        // Nombre único: el docente puede corregir los datos o cancelar
        if (control.existeCurso(nombre)) {
            error(request, response, docente, "Ya existe un curso con el nombre '" + nombre
                    + "'. Modifique los datos o cancele el alta.");
            return;
        }

        Instituto instituto = null;
        for (Instituto i : control.listarInstitutos()) {
            if (i.getNombre().equals(nombreInstituto)) {
                instituto = i;
                break;
            }
        }
        if (instituto == null) {
            error(request, response, docente, "El instituto seleccionado no existe.");
            return;
        }

        List<Curso> previas = new ArrayList<>();
        if (previasSel != null) {
            List<String> nombresPrevias = Arrays.asList(previasSel);
            for (Curso c : control.listarCursos()) {
                if (nombresPrevias.contains(c.getNombre())) {
                    previas.add(c);
                }
            }
        }

        List<String> categorias = categoriasSel == null
                ? new ArrayList<String>() : new ArrayList<>(Arrays.asList(categoriasSel));

        try {
            control.altaCurso(nombre, descripcion, duracion, horas, creditos, url,
                    LocalDate.now(), instituto, previas, categorias, null);
        } catch (Exception e) {
            error(request, response, docente, "No se pudo dar de alta el curso: " + e.getMessage());
            return;
        }

        response.sendRedirect(request.getContextPath() + "/curso-detalle?msg=creado&nombre="
                + URLEncoder.encode(nombre, "UTF-8"));
    }

    // ------------------------------------------------------------------

    private void error(HttpServletRequest request, HttpServletResponse response,
            UsuarioResumen docente, String mensaje) throws ServletException, IOException {
        request.setAttribute("error", mensaje);
        cargarFormulario(request, docente);
        request.getRequestDispatcher("/altaCurso.jsp").forward(request, response);
    }

    private void cargarFormulario(HttpServletRequest request, UsuarioResumen docente) {
        request.setAttribute("institutos", control.listarNombresInstitutos());
        request.setAttribute("categorias", control.listarNombresCategorias());

        List<String> cursosExistentes = new ArrayList<>();
        for (Curso c : control.listarCursos()) {
            cursosExistentes.add(c.getNombre());
        }
        request.setAttribute("cursosExistentes", cursosExistentes);

        // Instituto del docente, preseleccionado en el combo
        if (request.getAttribute("v_instituto") == null) {
            UsuarioEdicion ue = control.buscarUsuarioParaEditar(docente.getNickname());
            if (ue != null && ue.getInstituto() != null) {
                request.setAttribute("v_instituto", ue.getInstituto());
            }
        }
    }

    private static UsuarioResumen docenteLogueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        UsuarioResumen u = (UsuarioResumen) session.getAttribute("usuarioLogueado");
        return (u != null && "Docente".equalsIgnoreCase(u.getTipo())) ? u : null;
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
