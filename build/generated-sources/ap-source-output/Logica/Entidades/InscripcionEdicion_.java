package Logica.Entidades;

import Logica.Entidades.EdicionCurso;
import Logica.Entidades.Estudiante;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T15:17:20", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(InscripcionEdicion.class)
public class InscripcionEdicion_ { 

    public static volatile SingularAttribute<InscripcionEdicion, Estudiante> estudiante;
    public static volatile SingularAttribute<InscripcionEdicion, Long> id;
    public static volatile SingularAttribute<InscripcionEdicion, LocalDate> fechaInscripcion;
    public static volatile SingularAttribute<InscripcionEdicion, EdicionCurso> edicion;

}