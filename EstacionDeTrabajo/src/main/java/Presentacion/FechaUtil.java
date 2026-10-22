package Presentacion;

import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 * Utilidades para trabajar con los selectores de fecha (JCalendar / JDateChooser)
 * de los formularios de la Estación de Trabajo.
 */
public final class FechaUtil {

    public static final String FORMATO = "dd/MM/yyyy";

    private FechaUtil() {
    }

    /** Deja el selector en español (Uruguay) y con formato dd/MM/yyyy. */
    public static void configurar(JDateChooser dc) {
        dc.setLocale(Locale.of("es", "UY"));
        dc.setDateFormatString(FORMATO);
    }

    /** Convierte la fecha del JDateChooser a LocalDate (null si no hay fecha). */
    public static LocalDate toLocalDate(Date fecha) {
        return fecha == null ? null : fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /** Convierte un LocalDate a Date para cargarlo en un JDateChooser (null si es null). */
    public static Date toDate(LocalDate fecha) {
        return fecha == null ? null : Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
