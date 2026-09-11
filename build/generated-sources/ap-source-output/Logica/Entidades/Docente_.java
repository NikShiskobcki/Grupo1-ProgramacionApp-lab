package Logica.Entidades;

import Logica.Entidades.EdicionCurso;
import Logica.Entidades.Instituto;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T15:17:20", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(Docente.class)
public class Docente_ extends Usuario_ {

    public static volatile SingularAttribute<Docente, Instituto> instituto;
    public static volatile ListAttribute<Docente, EdicionCurso> ediciones;

}