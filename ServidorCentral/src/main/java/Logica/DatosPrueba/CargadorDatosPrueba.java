package Logica.DatosPrueba;

import Logica.Entidades.Categoria;
import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.EstadoInscripcion;
import Logica.Entidades.Estudiante;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.InscripcionPrograma;
import Logica.Entidades.Instituto;
import Logica.Entidades.ProgramaFormacion;
import Persistencia.ManejadorCategoria;
import java.time.LocalDate;

import Persistencia.ManejadorCurso;
import Persistencia.ManejadorEdicionCurso;
import Persistencia.ManejadorInscripcionEdicion;
import Persistencia.ManejadorInscripcionPrograma;
import Persistencia.ManejadorUsuario;
import Persistencia.ManejadorInstituto;
import Persistencia.ManejadorProgramaFormacion;

public class CargadorDatosPrueba {

    private final ManejadorInstituto manejadorInstituto;
    private final ManejadorUsuario manejadorUsuario;
    private final ManejadorCurso manejadorCurso;
    private final ManejadorEdicionCurso manejadorEdicionCurso;
    private final ManejadorInscripcionEdicion manejadorInscripcionEdicion;
    private final ManejadorProgramaFormacion manejadorProgramaFormacion;
    private final ManejadorCategoria manejadorCategoria;
    private final ManejadorInscripcionPrograma manejadorInscripcionPrograma;
    
    public CargadorDatosPrueba(
            ManejadorInstituto manejadorInstituto,
            ManejadorUsuario manejadorUsuario,
            ManejadorCurso manejadorCurso,
            ManejadorEdicionCurso manejadorEdicionCurso, ManejadorInscripcionEdicion manejadorInscripcionEdicion,
            ManejadorProgramaFormacion manejadorProgramaFormacion,
            ManejadorCategoria manejadorCategoria,
            ManejadorInscripcionPrograma manejadorInscripcionPrograma) {

        this.manejadorInstituto = manejadorInstituto;
        this.manejadorUsuario = manejadorUsuario;
        this.manejadorCurso = manejadorCurso;
        this.manejadorEdicionCurso = manejadorEdicionCurso;
        this.manejadorInscripcionEdicion = manejadorInscripcionEdicion;
        this.manejadorProgramaFormacion = manejadorProgramaFormacion;
        this.manejadorCategoria = manejadorCategoria;
        this.manejadorInscripcionPrograma = manejadorInscripcionPrograma;
    }

    public void cargar() {
        cargarInstitutos();
        cargarUsuarios();
        cargarSeguidores();
        cargarCursos();
        cargarCategorias();
        cargarCategoriasCursos();
        cargarPrevias();
        cargarEdiciones();
        cargarDocentesEdiciones();
        cargarInscripciones(); 
        cargarProgramas();
        cargarInscripcionesProgramas();
        
    }
    
    private void cargarInstitutos() {

        String[] nombres = {
            "INCO",
            "IMERL",
            "Física",
            "IMPII",
            "Eléctrica",
            "DISI"
        };

        for (String nombre : nombres) {
            if (manejadorInstituto.buscarPorNombre(nombre) == null) {
                manejadorInstituto.addInstituto(
                        new Instituto(nombre)
                );
            }
        }
    }
    
    private void cargarCategorias() {
        String[] nombres = {"Social", "Industrial", "Educativo", "Interdisciplinario"};
        for (String nombre : nombres) {
            if (manejadorCategoria.buscarPorNombre(nombre) == null) {
                manejadorCategoria.addCategoria(new Categoria(nombre));
            }
        }
    }

    private void cargarCategoriasCursos() {
        String c3 = "Dalavuelta";
        String c4 = "Extensionismo Industrial";
        String c5 = "Inclusión Energética";
        String c6 = "Flor del Ceibo";
        String c7 = "Taller de robótica educativa.";
        String c8 = "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela";
        String c9 = "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación";
        String c10 = "MicroBit";

        for (String c : new String[]{c3, c7, c9}) {
            manejadorCurso.agregarCategoria(c, "Social");
        }
        for (String c : new String[]{c4, c5, c10}) {
            manejadorCurso.agregarCategoria(c, "Industrial");
        }
        for (String c : new String[]{c6, c7, c8, c9, c10}) {
            manejadorCurso.agregarCategoria(c, "Educativo");
        }
        manejadorCurso.agregarCategoria(c6, "Interdisciplinario");
    }
    
    private void cargarSeguidores() {
        seguir("eleven11", "roro", "heisenberg", "waston");
        seguir("costas", "chechi", "jeffw", "benkenobi", "danny", "phils");
        seguir("roro", "costas", "weiss");
        seguir("chechi", "eleven11", "jeffw", "house");
        seguir("jeffw", "eleven11");
        seguir("weiss", "costas", "heisenberg", "timmy");
        seguir("heisenberg", "roro");
        seguir("benkenobi", "danny");
        seguir("waston", "roro", "weiss", "heisenberg");
        seguir("house", "jeffw", "weiss", "benkenobi");
        seguir("timmy", "eleven11", "chechi", "jeffw");
        seguir("danny", "costas", "weiss", "heisenberg");
        seguir("phils", "bruces");
        seguir("bruces", "phils");
        seguir("adri", "heisenberg");
    }

    private void seguir(String seguidor, String... seguidos) {
        for (String seguido : seguidos) {
            manejadorUsuario.seguirUsuario(seguidor, seguido);
        }
    }

    private void cargarUsuarios() {
       // ESTUDIANTES

       if (!manejadorUsuario.existeNickname("eleven11")) {
           agregarUsuarioConImagen(
                   new Estudiante(
                           "eleven11",
                           "Eleven",
                           "Twelve",
                           "eleven11@gmail.com",
                           "1234",
                           LocalDate.of(1971, 12, 31)
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("costas")) {
           agregarUsuarioConImagen(
                   new Estudiante(
                           "costas",
                           "Gerardo",
                           "Costas",
                           "gcostas@gmail.com",
                           "1234",
                           LocalDate.of(1983, 11, 15)
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("roro")) {
           agregarUsuarioConImagen(
                   new Estudiante(
                           "roro",
                           "Rodrigo",
                           "Cotelo",
                           "rcotelo@yahoo.com",
                           "1234",
                           LocalDate.of(1975, 8, 2)
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("chechi")) {
           agregarUsuarioConImagen(
                   new Estudiante(
                           "chechi",
                           "Cecilia",
                           "Garrido",
                           "cgarrido@hotmail.com",
                           "1234",
                           LocalDate.of(1987, 9, 12)
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("jeffw")) {
           agregarUsuarioConImagen(
                   new Estudiante(
                           "jeffw",
                           "Jeff",
                           "Williams",
                           "jwilliams@gmail.com",
                           "1234",
                           LocalDate.of(1964, 11, 27)
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("weiss")) {
           agregarUsuarioConImagen(
                   new Estudiante(
                           "weiss",
                           "Adrian",
                           "Weiss",
                           "aweiss@hotmail.com",
                           "1234",
                           LocalDate.of(1978, 12, 23)
                   )
           );
       }

       // INSTITUTOS PARA LOS DOCENTES

       Instituto inco =
               manejadorInstituto.buscarPorNombre("INCO");

       Instituto imerl =
               manejadorInstituto.buscarPorNombre("IMERL");

       Instituto impii =
               manejadorInstituto.buscarPorNombre("IMPII");

       Instituto electrica =
               manejadorInstituto.buscarPorNombre("Eléctrica");

       Instituto disi =
               manejadorInstituto.buscarPorNombre("DISI");

       // DOCENTES

       if (!manejadorUsuario.existeNickname("heisenberg")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "heisenberg",
                           "Walter",
                           "White",
                           "heisenberg@gmail.com",
                           "1234",
                           LocalDate.of(1956, 3, 7),
                           inco
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("benkenobi")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "benkenobi",
                           "Obi-Wan",
                           "Kenobi",
                           "benKenobi@gmail.com",
                           "1234",
                           LocalDate.of(1914, 4, 2),
                           inco
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("waston")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "waston",
                           "Emma",
                           "Watson",
                           "e.watson@gmail.com",
                           "1234",
                           LocalDate.of(1990, 4, 15),
                           inco
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("house")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "house",
                           "Gregory",
                           "House",
                           "greghouse@gmail.com",
                           "1234",
                           LocalDate.of(1959, 5, 15),
                           electrica
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("timmy")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "timmy",
                           "Tim",
                           "Cook",
                           "tim.cook@apple.com",
                           "1234",
                           LocalDate.of(1960, 11, 1),
                           imerl
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("danny")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "danny",
                           "Daniel",
                           "Riccio",
                           "dan.riccio@gmail.com",
                           "1234",
                           LocalDate.of(1963, 7, 5),
                           imerl
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("phils")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "phils",
                           "Philip",
                           "Schiller",
                           "schiller@gmail.com",
                           "1234",
                           LocalDate.of(1961, 10, 7),
                           impii
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("bruces")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "bruces",
                           "Bruce",
                           "Sewell",
                           "sewell@gmail.com",
                           "1234",
                           LocalDate.of(1959, 12, 3),
                           disi
                   )
           );
       }

       if (!manejadorUsuario.existeNickname("adri")) {
           agregarUsuarioConImagen(
                   new Docente(
                           "adri",
                           "Adriana",
                           "García",
                           "agarcia@gmail.com",
                           "1234",
                           LocalDate.of(1978, 7, 28),
                           disi
                   )
           );
       }
   }   
    private void cargarCursos() {

    Instituto imerl =
            manejadorInstituto.buscarPorNombre("IMERL");

    Instituto impii =
            manejadorInstituto.buscarPorNombre("IMPII");

    Instituto disi =
            manejadorInstituto.buscarPorNombre("DISI");

    Instituto inco =
            manejadorInstituto.buscarPorNombre("INCO");

    Instituto electrica =
            manejadorInstituto.buscarPorNombre("Eléctrica");


    agregarCursoSiNoExiste(
            new Curso(
                    "Talleres plenarios",
                    "Talleres plenarios: presentados por cuatro reconocidos matemáticos uruguayos, "
                    + "plantearán diversos tópicos de matemática en el marco de los cuales se realizarán "
                    + "actividades fomentando la integración entre estudiantes, docentes e investigadores.",
                    3,
                    15,
                    1,
                    "www.tmu.edu.uy",
                    LocalDate.of(2026, 2, 1),
                    imerl
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Seminarios de Resolución de Problemas",
                    "Seminario, todos los jueves en Facultad de Ingeniería a partir del jueves 25 de Julio, "
                    + "en las áreas en que se desarrollan los problemas de las Olimpíadas de Matemática.",
                    5,
                    30,
                    2,
                    "www.tmu.edu.uy",
                    LocalDate.of(2026, 7, 12),
                    imerl
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Dalavuelta",
                    "Dalavuelta es un proyecto de extensión que nace en el Instituto de Ingeniería Mecánica "
                    + "y Producción Industrial (IIMPI) de Fing, que inicia su trabajo en el desarrollo de "
                    + "bicicletas accesibles para personas en situación de discapacidad motriz.",
                    10,
                    60,
                    4,
                    "https://eva.fing.edu.uy/course/view.php?id=783#section-2",
                    LocalDate.of(2024, 6, 25),
                    impii
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Extensionismo Industrial",
                    "El proyecto tiene como objetivo desarrollar intervenciones curriculares en pequeños "
                    + "emprendimientos productivos de diferentes sectores de la industria nacional.",
                    12,
                    75,
                    5,
                    "https://eva.fing.edu.uy/course/view.php?id=783#section-2",
                    LocalDate.of(2025, 6, 16),
                    impii
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Inclusión Energética",
                    "En el proyecto se conjuga el trabajo de docentes y estudiantes de la carrera Ingeniería "
                    + "Industrial Mecánica a través del Módulo de Extensión.",
                    6,
                    45,
                    3,
                    "https://eva.fing.edu.uy/course/view.php?id=783#section-2",
                    LocalDate.of(2026, 2, 1),
                    impii
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Flor del Ceibo",
                    "Flor de Ceibo es un proyecto central de la Universidad de la República que tiene misión "
                    + "por movilizar la participación de estudiantes universitarios en tareas vinculadas "
                    + "con la puesta en funcionamiento del Plan Ceibal.",
                    15,
                    150,
                    10,
                    "http://www.flordeceibo.edu.uy/",
                    LocalDate.of(2008, 7, 27),
                    disi
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Taller de robótica educativa.",
                    "La asignatura se organiza en dos etapas y trabaja sobre el diseño e implementación "
                    + "de una experiencia didáctica de inclusión del robot Butiá en el aula.",
                    8,
                    90,
                    6,
                    "https://eva.fing.edu.uy/course/view.php?id=1187",
                    LocalDate.of(2024, 2, 2),
                    inco
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela",
                    "Se propone desarrollar una aplicación interactiva para tablet Android basada en el juego "
                    + "de tablero Komikan.",
                    9,
                    45,
                    3,
                    "https://eva.fing.edu.uy/mod/folder/view.php?id=89398",
                    LocalDate.of(2026, 6, 15),
                    inco
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación",
                    "Se realizarán visitas a escuelas rurales participantes en un proyecto conjunto del grupo "
                    + "PLN y el Programa de Políticas Lingüísticas de ANEP.",
                    12,
                    60,
                    4,
                    "https://eva.fing.edu.uy/mod/folder/view.php?id=89398",
                    LocalDate.of(2026, 5, 24),
                    inco
            )
    );

    agregarCursoSiNoExiste(
            new Curso(
                    "MicroBit",
                    "El Centro Ceibal se encuentra distribuyendo placas micro:bit para que estudiantes "
                    + "aprendan nociones básicas de robótica, electrónica y programación.",
                    15,
                    105,
                    7,
                    "https://www.fing.edu.uy/",
                    LocalDate.of(2026, 3, 13),
                    electrica
            )
    );
}   
    
    private void cargarPrevias() {

        manejadorCurso.agregarPrevia(
                "Seminarios de Resolución de Problemas",
                "Talleres plenarios"
        );
    }

    private void cargarEdiciones() {

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Flor del Ceibo - 2010",
                    LocalDate.of(2010, 3, 15),
                    LocalDate.of(2010, 7, 7),
                    null,
                    LocalDate.of(2010, 2, 16),
                    manejadorCurso.buscarPorNombre("Flor del Ceibo")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Flor del Ceibo - 2012",
                    LocalDate.of(2012, 8, 1),
                    LocalDate.of(2012, 11, 20),
                    null,
                    LocalDate.of(2012, 7, 10),
                    manejadorCurso.buscarPorNombre("Flor del Ceibo")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Flor del Ceibo - 2025",
                    LocalDate.of(2025, 4, 10),
                    LocalDate.of(2025, 8, 7),
                    null,
                    LocalDate.of(2025, 3, 6),
                    manejadorCurso.buscarPorNombre("Flor del Ceibo")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Dalavuelta - 2025",
                    LocalDate.of(2024, 8, 20),
                    LocalDate.of(2024, 11, 10),
                    15,
                    LocalDate.of(2024, 7, 20),
                    manejadorCurso.buscarPorNombre("Dalavuelta")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Extensionismo Industrial - 2025",
                    LocalDate.of(2025, 8, 10),
                    LocalDate.of(2025, 11, 10),
                    15,
                    LocalDate.of(2025, 7, 8),
                    manejadorCurso.buscarPorNombre("Extensionismo Industrial")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Inclusión Energética - 2026",
                    LocalDate.of(2026, 3, 15),
                    LocalDate.of(2026, 4, 30),
                    30,
                    LocalDate.of(2026, 2, 20),
                    manejadorCurso.buscarPorNombre("Inclusión Energética")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Taller de robótica educativa - 2024",
                    LocalDate.of(2024, 3, 10),
                    LocalDate.of(2024, 5, 10),
                    10,
                    LocalDate.of(2024, 2, 15),
                    manejadorCurso.buscarPorNombre("Taller de robótica educativa.")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Taller de robótica educativa - 2026",
                    LocalDate.of(2026, 3, 10),
                    LocalDate.of(2026, 5, 10),
                    10,
                    LocalDate.of(2026, 2, 15),
                    manejadorCurso.buscarPorNombre("Taller de robótica educativa.")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Taller de robótica educativa - 2026-2",
                    LocalDate.of(2026, 9, 10),
                    LocalDate.of(2026, 11, 8),
                    20,
                    LocalDate.of(2026, 8, 15),
                    manejadorCurso.buscarPorNombre("Taller de robótica educativa.")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2026",
                    LocalDate.of(2026, 7, 29),
                    LocalDate.of(2026, 10, 7),
                    5,
                    LocalDate.of(2026, 7, 10),
                    manejadorCurso.buscarPorNombre(
                            "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela"
                    )
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación - 26",
                    LocalDate.of(2026, 9, 15),
                    LocalDate.of(2026, 12, 15),
                    5,
                    LocalDate.of(2026, 6, 2),
                    manejadorCurso.buscarPorNombre(
                            "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación"
                    )
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "MicroBit - 2026",
                    LocalDate.of(2026, 8, 12),
                    LocalDate.of(2026, 12, 5),
                    30,
                    LocalDate.of(2026, 7, 2),
                    manejadorCurso.buscarPorNombre("MicroBit")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Talleres plenarios - 2026",
                    LocalDate.of(2026, 3, 10),
                    LocalDate.of(2026, 3, 30),
                    null,
                    LocalDate.of(2026, 3, 2),
                    manejadorCurso.buscarPorNombre("Talleres plenarios")
            )
    );

    agregarEdicionSiNoExiste(
            new EdicionCurso(
                    "Seminarios de Resolución de Problemas - 2026",
                    LocalDate.of(2026, 9, 10),
                    LocalDate.of(2026, 10, 20),
                    null,
                    LocalDate.of(2026, 7, 12),
                    manejadorCurso.buscarPorNombre(
                            "Seminarios de Resolución de Problemas"
                    )
            )
    );
}
    private void cargarDocentesEdiciones() {

    manejadorEdicionCurso.agregarDocente(
            "Flor del Ceibo - 2010",
            "bruces"
    );

    manejadorEdicionCurso.agregarDocente(
            "Flor del Ceibo - 2012",
            "bruces"
    );

    manejadorEdicionCurso.agregarDocente(
            "Flor del Ceibo - 2012",
            "adri"
    );

    manejadorEdicionCurso.agregarDocente(
            "Flor del Ceibo - 2025",
            "bruces"
    );

    manejadorEdicionCurso.agregarDocente(
            "Flor del Ceibo - 2025",
            "adri"
    );

    manejadorEdicionCurso.agregarDocente(
            "Dalavuelta - 2025",
            "phils"
    );

    manejadorEdicionCurso.agregarDocente(
            "Extensionismo Industrial - 2025",
            "phils"
    );

    manejadorEdicionCurso.agregarDocente(
            "Inclusión Energética - 2026",
            "phils"
    );

    manejadorEdicionCurso.agregarDocente(
            "Taller de robótica educativa - 2024",
            "heisenberg"
    );

    manejadorEdicionCurso.agregarDocente(
            "Taller de robótica educativa - 2026",
            "heisenberg"
    );

    manejadorEdicionCurso.agregarDocente(
            "Taller de robótica educativa - 2026",
            "benkenobi"
    );

    manejadorEdicionCurso.agregarDocente(
            "Taller de robótica educativa - 2026-2",
            "benkenobi"
    );

    manejadorEdicionCurso.agregarDocente(
            "Taller de robótica educativa - 2026-2",
            "waston"
    );

    manejadorEdicionCurso.agregarDocente(
            "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2026",
            "waston"
    );

    manejadorEdicionCurso.agregarDocente(
            "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación - 26",
            "heisenberg"
    );

    manejadorEdicionCurso.agregarDocente(
            "MicroBit - 2026",
            "house"
    );

    manejadorEdicionCurso.agregarDocente(
            "Talleres plenarios - 2026",
            "timmy"
    );

    manejadorEdicionCurso.agregarDocente(
            "Talleres plenarios - 2026",
            "danny"
    );

    manejadorEdicionCurso.agregarDocente(
            "Seminarios de Resolución de Problemas - 2026",
            "timmy"
    );
}
    
    private void agregarCursoSiNoExiste(Curso curso) {

    if (manejadorCurso.buscarPorNombre(curso.getNombre()) == null) {
        agregarCursoConImagen(curso);
    }
}
    
    private void agregarEdicionSiNoExiste(EdicionCurso edicion) {

    if (manejadorEdicionCurso.buscarPorNombre(edicion.getNombre()) == null) {
        agregarEdicionConImagen(edicion);
    }
}
    private void cargarInscripciones() {
        final EstadoInscripcion A = EstadoInscripcion.ACEPTADA;
        final EstadoInscripcion R = EstadoInscripcion.RECHAZADA;
        final EstadoInscripcion I = EstadoInscripcion.INSCRIPTO;

        String e1 = "Flor del Ceibo - 2010";
        String e2 = "Flor del Ceibo - 2012";
        String e3 = "Flor del Ceibo - 2025";
        String e4 = "Dalavuelta - 2025";
        String e5 = "Extensionismo Industrial - 2025";
        String e6 = "Inclusión Energética - 2026";
        String e7 = "Taller de robótica educativa - 2024";
        String e8 = "Taller de robótica educativa - 2026";
        String e9 = "Taller de robótica educativa - 2026-2";
        String e10 = "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2026";
        String e11 = "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación - 26";
        String e12 = "MicroBit - 2026";
        String e13 = "Talleres plenarios - 2026";
        String e14 = "Seminarios de Resolución de Problemas - 2026";

        // E1
        inscribir("eleven11", e1, 2010, 2, 20, A);
        inscribir("chechi", e1, 2010, 2, 25, A);
        // E2
        inscribir("costas", e2, 2012, 7, 12, A);
        inscribir("roro", e2, 2012, 7, 15, R);
        inscribir("weiss", e2, 2012, 7, 30, A);
        // E3
        inscribir("roro", e3, 2025, 3, 10, A);
        inscribir("jeffw", e3, 2025, 3, 15, A);
        // E4
        inscribir("chechi", e4, 2024, 7, 25, A);
        inscribir("eleven11", e4, 2024, 7, 28, R);
        inscribir("roro", e4, 2024, 8, 2, A);
        inscribir("costas", e4, 2024, 8, 10, A);
        inscribir("jeffw", e4, 2024, 8, 15, R);
        // E5
        inscribir("costas", e5, 2025, 7, 18, A);
        inscribir("chechi", e5, 2025, 7, 20, A);
        inscribir("eleven11", e5, 2025, 7, 29, A);
        inscribir("weiss", e5, 2025, 8, 5, A);
        // E6
        inscribir("roro", e6, 2026, 2, 23, A);
        inscribir("weiss", e6, 2026, 2, 25, R);
        inscribir("chechi", e6, 2026, 2, 28, A);
        inscribir("eleven11", e6, 2026, 3, 3, R);
        // E7
        inscribir("weiss", e7, 2024, 2, 18, R);
        inscribir("roro", e7, 2024, 2, 20, R);
        inscribir("eleven11", e7, 2024, 3, 3, A);
        // E8
        inscribir("jeffw", e8, 2026, 2, 18, A);
        inscribir("costas", e8, 2026, 2, 22, A);
        inscribir("roro", e8, 2026, 2, 23, R);
        // E9
        inscribir("weiss", e9, 2026, 8, 18, I);
        inscribir("chechi", e9, 2026, 8, 22, I);
        inscribir("roro", e9, 2026, 9, 3, I);
        // E10
        inscribir("chechi", e10, 2026, 7, 13, A);
        inscribir("weiss", e10, 2026, 7, 20, A);
        inscribir("roro", e10, 2026, 7, 22, A);
        // E11
        inscribir("weiss", e11, 2026, 6, 4, I);
        inscribir("eleven11", e11, 2026, 7, 18, I);
        inscribir("jeffw", e11, 2026, 8, 20, I);
        // E12
        inscribir("chechi", e12, 2026, 7, 12, I);
        inscribir("roro", e12, 2026, 7, 14, I);
        inscribir("eleven11", e12, 2026, 7, 25, I);
        inscribir("jeffw", e12, 2026, 8, 5, I);
        // E13
        inscribir("costas", e13, 2026, 3, 5, R);
        inscribir("weiss", e13, 2026, 3, 4, A);
        inscribir("roro", e13, 2026, 3, 7, A);
        // E14
        inscribir("weiss", e14, 2026, 7, 15, A);
        inscribir("costas", e14, 2026, 7, 20, R);
        inscribir("roro", e14, 2026, 8, 6, A);
        inscribir("chechi", e14, 2026, 8, 30, R);
    }

    private void inscribir(String nickname, String edicion,
            int anio, int mes, int dia, EstadoInscripcion estado) {
        agregarInscripcionSiNoExiste(nickname, edicion, LocalDate.of(anio, mes, dia), estado);
    }
    
    private void agregarInscripcionSiNoExiste(
            String nicknameEstudiante,
            String nombreEdicion,
            LocalDate fechaInscripcion,
            EstadoInscripcion estado) {

        if (manejadorInscripcionEdicion.existeInscripcionCualquierEstado(nicknameEstudiante, nombreEdicion)) {
            return;
        }

        Estudiante estudiante = manejadorUsuario.buscarEstudiante(nicknameEstudiante);
        EdicionCurso edicion = manejadorEdicionCurso.buscarPorNombre(nombreEdicion);

        InscripcionEdicion inscripcion = new InscripcionEdicion(fechaInscripcion, estudiante, edicion);
        inscripcion.setEstado(estado);

        manejadorInscripcionEdicion.addInscripcion(inscripcion);
    }
    private void cargarProgramas() {

    if (!manejadorProgramaFormacion.existePrograma(
            "EFI Ingeniería Mecánica")) {

        agregarProgramaConImagen(
                new ProgramaFormacion(
                        "EFI Ingeniería Mecánica",
                        "Programa mecánica",
                        LocalDate.of(2026, 5, 1),
                        LocalDate.of(2026, 10, 31),
                        LocalDate.of(2026, 5, 1)
                )
        );
    }

    if (!manejadorProgramaFormacion.existePrograma(
            "Formación integral")) {

        agregarProgramaConImagen(
                new ProgramaFormacion(
                        "Formación integral",
                        "Programa varios institutos",
                        LocalDate.of(2026, 7, 15),
                        LocalDate.of(2027, 1, 1),
                        LocalDate.of(2026, 7, 15)
                )
        );
    }

    if (!manejadorProgramaFormacion.existePrograma(
            "EFI Robótica")) {

        agregarProgramaConImagen(
                new ProgramaFormacion(
                        "EFI Robótica",
                        "Programa robótica",
                        LocalDate.of(2026, 9, 3),
                        LocalDate.of(2026, 11, 18),
                        LocalDate.of(2026, 9, 3)
                )
        );
    }


    // CURSOS DE P1
    manejadorProgramaFormacion.agregarCursoAPrograma(
            "EFI Ingeniería Mecánica",
            "Dalavuelta"
    );

    manejadorProgramaFormacion.agregarCursoAPrograma(
            "EFI Ingeniería Mecánica",
            "Extensionismo Industrial"
    );

    manejadorProgramaFormacion.agregarCursoAPrograma(
            "EFI Ingeniería Mecánica",
            "Inclusión Energética"
    );


    // CURSOS DE P2
    manejadorProgramaFormacion.agregarCursoAPrograma(
            "Formación integral",
            "Seminarios de Resolución de Problemas"
    );

    manejadorProgramaFormacion.agregarCursoAPrograma(
            "Formación integral",
            "Extensionismo Industrial"
    );

    manejadorProgramaFormacion.agregarCursoAPrograma(
            "Formación integral",
            "Flor del Ceibo"
    );

    manejadorProgramaFormacion.agregarCursoAPrograma(
            "Formación integral",
            "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela"
    );


    // CURSOS DE P3
    manejadorProgramaFormacion.agregarCursoAPrograma(
            "EFI Robótica",
            "Taller de robótica educativa."
    );

    manejadorProgramaFormacion.agregarCursoAPrograma(
            "EFI Robótica",
            "MicroBit"
    );
}
    
    private void cargarInscripcionesProgramas() {
        String p1 = "EFI Ingeniería Mecánica";
        String p2 = "Formación integral";
        String p3 = "EFI Robótica";

        agregarInscripcionProgramaSiNoExiste("eleven11", p1, LocalDate.of(2026, 4, 15));
        agregarInscripcionProgramaSiNoExiste("chechi", p1, LocalDate.of(2026, 4, 20));

        agregarInscripcionProgramaSiNoExiste("costas", p2, LocalDate.of(2026, 7, 10));
        agregarInscripcionProgramaSiNoExiste("chechi", p2, LocalDate.of(2026, 7, 10));
        agregarInscripcionProgramaSiNoExiste("eleven11", p2, LocalDate.of(2026, 7, 12));
        agregarInscripcionProgramaSiNoExiste("weiss", p2, LocalDate.of(2026, 7, 15));
        agregarInscripcionProgramaSiNoExiste("roro", p2, LocalDate.of(2026, 7, 18));

        agregarInscripcionProgramaSiNoExiste("costas", p3, LocalDate.of(2026, 8, 11));
        agregarInscripcionProgramaSiNoExiste("chechi", p3, LocalDate.of(2026, 8, 15));
        agregarInscripcionProgramaSiNoExiste("roro", p3, LocalDate.of(2026, 8, 22));
        agregarInscripcionProgramaSiNoExiste("jeffw", p3, LocalDate.of(2026, 8, 30));
    }

    private void agregarInscripcionProgramaSiNoExiste(String nicknameEstudiante,
            String nombrePrograma,
            LocalDate fecha) {
        if (manejadorInscripcionPrograma.buscarInscripcion(nicknameEstudiante, nombrePrograma) != null) {
            return;
        }
        Estudiante estudiante = manejadorUsuario.buscarEstudiante(nicknameEstudiante);
        ProgramaFormacion programa = manejadorProgramaFormacion.buscarPrograma(nombrePrograma);
        manejadorInscripcionPrograma.addInscripcion(
                new InscripcionPrograma(fecha, estudiante, programa));
    }
    
    private void agregarInscripcionSiNoExiste(
        String nicknameEstudiante,
        String nombreEdicion,
        LocalDate fechaInscripcion) {

    if (manejadorInscripcionEdicion.buscarInscripcion(
            nicknameEstudiante,
            nombreEdicion) != null) {
        return;
    }

    Estudiante estudiante =
            manejadorUsuario.buscarEstudiante(nicknameEstudiante);

    EdicionCurso edicion =
            manejadorEdicionCurso.buscarPorNombre(nombreEdicion);

    InscripcionEdicion inscripcion =
            new InscripcionEdicion(
                    fechaInscripcion,
                    estudiante,
                    edicion
            );

    manejadorInscripcionEdicion.addInscripcion(inscripcion);
}

    private void agregarUsuarioConImagen(Logica.Entidades.Usuario usuario) {
        manejadorUsuario.addUsuario(ImagenesPrueba.preparar(usuario));
    }

    private void agregarCursoConImagen(Curso curso) {
        manejadorCurso.addCurso(ImagenesPrueba.preparar(curso));
    }

    private void agregarEdicionConImagen(EdicionCurso edicion) {
        manejadorEdicionCurso.addEdicion(ImagenesPrueba.preparar(edicion));
    }

    private void agregarProgramaConImagen(ProgramaFormacion programa) {
        manejadorProgramaFormacion.addPrograma(
                ImagenesPrueba.preparar(programa));
    }
}