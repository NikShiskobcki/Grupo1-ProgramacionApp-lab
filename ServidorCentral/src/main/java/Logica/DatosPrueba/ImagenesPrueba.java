package Logica.DatosPrueba;

import Logica.Entidades.Curso;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.ProgramaFormacion;
import Logica.Entidades.Usuario;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public final class ImagenesPrueba {

    private ImagenesPrueba() {
    }

    private static final Map<String, String> USUARIOS = Map.of(
            "roro", "RO",
            "chechi", "CH",
            "jeffw", "JW",
            "heisenberg", "WW",
            "benkenobi", "OK",
            "waston", "EW",
            "house", "GH",
            "timmy", "TC"
    );

    private static final Map<String, String> CURSOS = Map.of(
            "Dalavuelta", "C3",
            "Inclusión Energética", "C5",
            "Flor del Ceibo", "C6",
            "Taller de robótica educativa.", "C7",
            "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", "C8",
            "MicroBit", "C10"
    );

    private static final Map<String, String> EDICIONES = Map.of(
            "Flor del Ceibo - 2010", "E1",
            "Flor del Ceibo - 2012", "E2",
            "Flor del Ceibo - 2025", "E3",
            "Dalavuelta - 2025", "E4",
            "Taller de robótica educativa - 2024", "E7",
            "Taller de robótica educativa - 2026", "E8",
            "Taller de robótica educativa - 2026-2", "E9"
    );

    private static final Map<String, String> PROGRAMAS = Map.of(
            "EFI Ingeniería Mecánica", "P1",
            "Formación integral", "P2",
            "EFI Robótica", "P3"
    );

    public static final Map<String, String> ARCHIVOS = Map.ofEntries(
            Map.entry("RO", "RO.jpg"),
            Map.entry("CH", "CH.jpg"),
            Map.entry("JW", "JW.jpg"),
            Map.entry("WW", "WW.jpg"),
            Map.entry("OK", "OK.jpg"),
            Map.entry("EW", "EW.jpg"),
            Map.entry("GH", "GH.jpg"),
            Map.entry("TC", "TC.jpg"),
            Map.entry("C3", "C3.jpg"),
            Map.entry("C5", "C5.jpg"),
            Map.entry("C6", "C6.jpg"),
            Map.entry("C7", "C7.jpg"),
            Map.entry("C8", "C8.jpg"),
            Map.entry("C10", "C10.jpg"),
            Map.entry("E1", "E1.jpg"),
            Map.entry("E2", "E2.jpg"),
            Map.entry("E3", "E3.jpg"),
            Map.entry("E4", "E4.jpg"),
            Map.entry("E7", "E7.jpg"),
            Map.entry("E8", "E8.jpg"),
            Map.entry("E9", "E9.jpg"),
            Map.entry("P1", "P1.jpg"),
            Map.entry("P2", "P2.jpg"),
            Map.entry("P3", "P3.jpg")
    );

    public static String ruta(String referencia) {
        String archivo = ARCHIVOS.get(referencia);

        if (archivo == null) {
            throw new IllegalArgumentException(
                    "Imagen de prueba desconocida: " + referencia);
        }

        String extension = archivo.endsWith(".png") ? ".png" : ".jpg";

        UUID identificador = UUID.nameUUIDFromBytes(
                ("edext-datos-prueba/" + referencia)
                        .getBytes(StandardCharsets.UTF_8));

        return "/imagenes/" + identificador + extension;
    }

    public static <T> T preparar(T entidad) {
        if (entidad instanceof Usuario usuario) {
            String referencia = USUARIOS.get(usuario.getNickname());
            if (referencia != null) {
                usuario.setRutaImagen(ruta(referencia));
            }

        } else if (entidad instanceof Curso curso) {
            String referencia = CURSOS.get(curso.getNombre());
            if (referencia != null) {
                curso.setRutaImagen(ruta(referencia));
            }

        } else if (entidad instanceof EdicionCurso edicion) {
            String referencia = EDICIONES.get(edicion.getNombre());
            if (referencia != null) {
                edicion.setRutaImagen(ruta(referencia));
            }

        } else if (entidad instanceof ProgramaFormacion programa) {
            String referencia = PROGRAMAS.get(programa.getNombre());
            if (referencia != null) {
                programa.setRutaImagen(ruta(referencia));
            }
        }

        return entidad;
    }
}