/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.DatosPrueba;

import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.Estudiante;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.Instituto;
import Logica.Entidades.ProgramaFormacion;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import Persistencia.ManejadorCurso;
import Persistencia.ManejadorEdicionCurso;
import Persistencia.ManejadorInscripcionEdicion;
import Persistencia.ManejadorUsuario;
import Persistencia.ManejadorInstituto;
import Persistencia.ManejadorProgramaFormacion;

/**
 *
 * @author xnahu
 */
public class CargadorDatosPrueba {

     private final ManejadorInstituto manejadorInstituto;
    private final ManejadorUsuario manejadorUsuario;
    private final ManejadorCurso manejadorCurso;
    private final ManejadorEdicionCurso manejadorEdicionCurso;
    private final ManejadorInscripcionEdicion manejadorInscripcionEdicion;
    private final ManejadorProgramaFormacion manejadorProgramaFormacion;

    public CargadorDatosPrueba(
            ManejadorInstituto manejadorInstituto,
            ManejadorUsuario manejadorUsuario,
            ManejadorCurso manejadorCurso,
            ManejadorEdicionCurso manejadorEdicionCurso, ManejadorInscripcionEdicion manejadorInscripcionEdicion,
            ManejadorProgramaFormacion manejadorProgramaFormacion) {

        this.manejadorInstituto = manejadorInstituto;
        this.manejadorUsuario = manejadorUsuario;
        this.manejadorCurso = manejadorCurso;
        this.manejadorEdicionCurso = manejadorEdicionCurso;
        this.manejadorInscripcionEdicion = manejadorInscripcionEdicion;
        this.manejadorProgramaFormacion = manejadorProgramaFormacion;
        
        
    }

    public void cargar() {
        cargarInstitutos();
        cargarUsuarios();
        cargarCursos();
        cargarPrevias();
        cargarEdiciones();
        cargarDocentesEdiciones();
        cargarInscripciones(); 
        cargarProgramas();

        
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

 private void cargarUsuarios() {

    // ESTUDIANTES

    if (!manejadorUsuario.existeNickname("eleven11")) {
        manejadorUsuario.addUsuario(
                new Estudiante(
                        "eleven11",
                        "Eleven",
                        "Twelve",
                        "eleven11@gmail.com",
                        LocalDate.of(1971, 12, 31)
                )
        );
    }

    if (!manejadorUsuario.existeNickname("costas")) {
        manejadorUsuario.addUsuario(
                new Estudiante(
                        "costas",
                        "Gerardo",
                        "Costas",
                        "gcostas@gmail.com",
                        LocalDate.of(1983, 11, 15)
                )
        );
    }

    if (!manejadorUsuario.existeNickname("roro")) {
        manejadorUsuario.addUsuario(
                new Estudiante(
                        "roro",
                        "Rodrigo",
                        "Cotelo",
                        "rcotelo@yahoo.com",
                        LocalDate.of(1975, 8, 2)
                )
        );
    }

    if (!manejadorUsuario.existeNickname("chechi")) {
        manejadorUsuario.addUsuario(
                new Estudiante(
                        "chechi",
                        "Cecilia",
                        "Garrido",
                        "cgarrido@hotmail.com",
                        LocalDate.of(1987, 9, 12)
                )
        );
    }

    if (!manejadorUsuario.existeNickname("jeffw")) {
        manejadorUsuario.addUsuario(
                new Estudiante(
                        "jeffw",
                        "Jeff",
                        "Williams",
                        "jwilliams@gmail.com",
                        LocalDate.of(1964, 11, 27)
                )
        );
    }

    if (!manejadorUsuario.existeNickname("weiss")) {
        manejadorUsuario.addUsuario(
                new Estudiante(
                        "weiss",
                        "Adrian",
                        "Weiss",
                        "aweiss@hotmail.com",
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
        manejadorUsuario.addUsuario(
                new Docente(
                        "heisenberg",
                        "Walter",
                        "White",
                        "heisenberg@gmail.com",
                        LocalDate.of(1956, 3, 7),
                        inco
                )
        );
    }

    if (!manejadorUsuario.existeNickname("benkenobi")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "benkenobi",
                        "Obi-Wan",
                        "Kenobi",
                        "benKenobi@gmail.com",
                        LocalDate.of(1914, 4, 2),
                        inco
                )
        );
    }

    if (!manejadorUsuario.existeNickname("waston")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "waston",
                        "Emma",
                        "Watson",
                        "e.watson@gmail.com",
                        LocalDate.of(1990, 4, 15),
                        inco
                )
        );
    }

    if (!manejadorUsuario.existeNickname("house")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "house",
                        "Gregory",
                        "House",
                        "greghouse@gmail.com",
                        LocalDate.of(1959, 5, 15),
                        electrica
                )
        );
    }

    if (!manejadorUsuario.existeNickname("timmy")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "timmy",
                        "Tim",
                        "Cook",
                        "tim.cook@apple.com",
                        LocalDate.of(1960, 11, 1),
                        imerl
                )
        );
    }

    if (!manejadorUsuario.existeNickname("danny")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "danny",
                        "Daniel",
                        "Riccio",
                        "dan.riccio@gmail.com",
                        LocalDate.of(1963, 7, 5),
                        imerl
                )
        );
    }

    if (!manejadorUsuario.existeNickname("phils")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "phils",
                        "Philip",
                        "Schiller",
                        "schiller@gmail.com",
                        LocalDate.of(1961, 10, 7),
                        impii
                )
        );
    }

    if (!manejadorUsuario.existeNickname("bruces")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "bruces",
                        "Bruce",
                        "Sewell",
                        "sewell@gmail.com",
                        LocalDate.of(1959, 12, 3),
                        disi
                )
        );
    }

    if (!manejadorUsuario.existeNickname("adri")) {
        manejadorUsuario.addUsuario(
                new Docente(
                        "adri",
                        "Adriana",
                        "García",
                        "agarcia@gmail.com",
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
        manejadorCurso.addCurso(curso);
    }
}
    
    private void agregarEdicionSiNoExiste(EdicionCurso edicion) {

    if (manejadorEdicionCurso.buscarPorNombre(edicion.getNombre()) == null) {
        manejadorEdicionCurso.addEdicion(edicion);
    }
}
    private void cargarInscripciones() {

    agregarInscripcionSiNoExiste(
            "eleven11",
            "Flor del Ceibo - 2010",
            LocalDate.of(2010, 2, 20)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Flor del Ceibo - 2010",
            LocalDate.of(2010, 2, 25)
    );

    agregarInscripcionSiNoExiste(
            "costas",
            "Flor del Ceibo - 2012",
            LocalDate.of(2012, 7, 12)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Flor del Ceibo - 2012",
            LocalDate.of(2012, 7, 15)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Flor del Ceibo - 2012",
            LocalDate.of(2012, 7, 30)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Flor del Ceibo - 2025",
            LocalDate.of(2025, 3, 10)
    );

    agregarInscripcionSiNoExiste(
            "jeffw",
            "Flor del Ceibo - 2025",
            LocalDate.of(2025, 3, 15)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Dalavuelta - 2025",
            LocalDate.of(2024, 7, 25)
    );

    agregarInscripcionSiNoExiste(
            "eleven11",
            "Dalavuelta - 2025",
            LocalDate.of(2024, 7, 28)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Dalavuelta - 2025",
            LocalDate.of(2024, 8, 2)
    );

    agregarInscripcionSiNoExiste(
            "costas",
            "Dalavuelta - 2025",
            LocalDate.of(2024, 8, 10)
    );

    agregarInscripcionSiNoExiste(
            "jeffw",
            "Dalavuelta - 2025",
            LocalDate.of(2024, 8, 15)
    );

    agregarInscripcionSiNoExiste(
            "costas",
            "Extensionismo Industrial - 2025",
            LocalDate.of(2025, 7, 18)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Extensionismo Industrial - 2025",
            LocalDate.of(2025, 7, 20)
    );

    agregarInscripcionSiNoExiste(
            "eleven11",
            "Extensionismo Industrial - 2025",
            LocalDate.of(2025, 7, 29)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Extensionismo Industrial - 2025",
            LocalDate.of(2025, 8, 5)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Inclusión Energética - 2026",
            LocalDate.of(2026, 2, 23)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Inclusión Energética - 2026",
            LocalDate.of(2026, 2, 25)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Inclusión Energética - 2026",
            LocalDate.of(2026, 2, 28)
    );

    agregarInscripcionSiNoExiste(
            "eleven11",
            "Inclusión Energética - 2026",
            LocalDate.of(2026, 3, 3)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Taller de robótica educativa - 2024",
            LocalDate.of(2017, 2, 18)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Taller de robótica educativa - 2024",
            LocalDate.of(2024, 2, 20)
    );

    agregarInscripcionSiNoExiste(
            "eleven11",
            "Taller de robótica educativa - 2024",
            LocalDate.of(2024, 3, 3)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Taller de robótica educativa - 2024",
            LocalDate.of(2024, 3, 5)
    );

    agregarInscripcionSiNoExiste(
            "jeffw",
            "Taller de robótica educativa - 2026",
            LocalDate.of(2026, 2, 18)
    );

    agregarInscripcionSiNoExiste(
            "costas",
            "Taller de robótica educativa - 2026",
            LocalDate.of(2026, 2, 22)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Taller de robótica educativa - 2026-2",
            LocalDate.of(2026, 8, 18)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Taller de robótica educativa - 2026-2",
            LocalDate.of(2026, 8, 22)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Taller de robótica educativa - 2026-2",
            LocalDate.of(2026, 9, 3)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2026",
            LocalDate.of(2026, 7, 13)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2026",
            LocalDate.of(2026, 7, 20)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2026",
            LocalDate.of(2026, 7, 22)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación - 26",
            LocalDate.of(2026, 6, 4)
    );

    agregarInscripcionSiNoExiste(
            "eleven11",
            "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación - 26",
            LocalDate.of(2026, 7, 18)
    );

    agregarInscripcionSiNoExiste(
            "jeffw",
            "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación - 26",
            LocalDate.of(2026, 8, 20)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "MicroBit - 2026",
            LocalDate.of(2026, 7, 12)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "MicroBit - 2026",
            LocalDate.of(2026, 7, 14)
    );

    agregarInscripcionSiNoExiste(
            "eleven11",
            "MicroBit - 2026",
            LocalDate.of(2026, 7, 25)
    );

    agregarInscripcionSiNoExiste(
            "jeffw",
            "MicroBit - 2026",
            LocalDate.of(2026, 8, 5)
    );

    agregarInscripcionSiNoExiste(
            "costas",
            "Talleres plenarios - 2026",
            LocalDate.of(2026, 3, 5)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Talleres plenarios - 2026",
            LocalDate.of(2026, 3, 4)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Talleres plenarios - 2026",
            LocalDate.of(2026, 3, 7)
    );

    agregarInscripcionSiNoExiste(
            "weiss",
            "Seminarios de Resolución de Problemas - 2026",
            LocalDate.of(2026, 7, 15)
    );

    agregarInscripcionSiNoExiste(
            "costas",
            "Seminarios de Resolución de Problemas - 2026",
            LocalDate.of(2026, 7, 20)
    );

    agregarInscripcionSiNoExiste(
            "roro",
            "Seminarios de Resolución de Problemas - 2026",
            LocalDate.of(2026, 8, 6)
    );

    agregarInscripcionSiNoExiste(
            "chechi",
            "Seminarios de Resolución de Problemas - 2026",
            LocalDate.of(2026, 8, 30)
    );
}
    private void cargarProgramas() {

    if (!manejadorProgramaFormacion.existePrograma(
            "EFI Ingeniería Mecánica")) {

        manejadorProgramaFormacion.addPrograma(
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

        manejadorProgramaFormacion.addPrograma(
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

        manejadorProgramaFormacion.addPrograma(
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
}