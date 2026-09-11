package Logica.Entidades;

import Logica.Entidades.Estudiante;
import Logica.Entidades.ProgramaFormacion;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T15:17:20", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(InscripcionPrograma.class)
public class InscripcionPrograma_ { 

    public static volatile SingularAttribute<InscripcionPrograma, Estudiante> estudiante;
    public static volatile SingularAttribute<InscripcionPrograma, ProgramaFormacion> programa;
    public static volatile SingularAttribute<InscripcionPrograma, Long> id;
    public static volatile SingularAttribute<InscripcionPrograma, LocalDate> fechaInscripcion;

}