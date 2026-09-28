package Logica.Entidades;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class CursoTest {

    @Test
    public void crearCursoConDatosValidos() {

        Instituto instituto = new Instituto("INCO");

        Curso curso = new Curso(
                "Programación",
                "Curso de programación",
                10,
                60,
                5,
                "https://ejemplo.com",
                LocalDate.of(2026, 9, 1),
                instituto
        );

        assertNotNull(curso);
        assertEquals("Programación", curso.getNombre());
        assertEquals(10, curso.getDuracion());
        assertEquals(60, curso.getCantidadHoras());
        assertEquals(5, curso.getCreditos());
        assertEquals(instituto, curso.getInstituto());
    }
}