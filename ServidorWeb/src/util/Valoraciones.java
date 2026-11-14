    package util;

import modelo.Curso;
import modelo.ValoracionCurso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Utilidades para mostrar el puntaje promedio de varios cursos (listados y búsqueda). */
public final class Valoraciones {

    private Valoraciones() {
    }

    /**
     * Promedio de cada curso indexado por nombre. Si no se puede consultar la valoración de
     * un curso, simplemente no aparece en el mapa: el listado se muestra igual.
     */
    public static Map<String, ValoracionCurso> deCursos(ClienteCentral control, List<Curso> cursos,
            javax.servlet.ServletContext ctx) {
        Map<String, ValoracionCurso> mapa = new HashMap<>();
        if (cursos == null) return mapa;
        for (Curso c : cursos) {
            try {
                ValoracionCurso v = control.consultarValoracionCurso(null, c.getNombre());
                if (v != null) mapa.put(c.getNombre(), v);
            } catch (RuntimeException e) {
                ctx.log("No se pudo obtener la valoración de " + c.getNombre(), e);
            }
        }
        return mapa;
    }
}
