package Logica.Entidades;

import Logica.Entidades.Curso;
import Logica.Entidades.InscripcionPrograma;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T18:16:55", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(ProgramaFormacion.class)
public class ProgramaFormacion_ { 

    public static volatile SingularAttribute<ProgramaFormacion, String> descripcion;
    public static volatile ListAttribute<ProgramaFormacion, Curso> cursos;
    public static volatile SingularAttribute<ProgramaFormacion, LocalDate> fechaAlta;
    public static volatile SingularAttribute<ProgramaFormacion, LocalDate> fechaInicio;
    public static volatile ListAttribute<ProgramaFormacion, InscripcionPrograma> inscripciones;
    public static volatile SingularAttribute<ProgramaFormacion, String> nombre;
    public static volatile SingularAttribute<ProgramaFormacion, LocalDate> fechaFin;

}