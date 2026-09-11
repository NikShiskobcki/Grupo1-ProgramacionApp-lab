package Logica.Entidades;

import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.InscripcionPrograma;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T15:17:20", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(Estudiante.class)
public class Estudiante_ extends Usuario_ {

    public static volatile ListAttribute<Estudiante, InscripcionEdicion> inscripcionesEdiciones;
    public static volatile ListAttribute<Estudiante, InscripcionPrograma> inscripcionesProgramas;

}