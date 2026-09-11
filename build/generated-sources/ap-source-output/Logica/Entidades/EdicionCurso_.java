package Logica.Entidades;

import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import Logica.Entidades.InscripcionEdicion;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T15:17:20", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(EdicionCurso.class)
public class EdicionCurso_ { 

    public static volatile SingularAttribute<EdicionCurso, LocalDate> fechaInicio;
    public static volatile SingularAttribute<EdicionCurso, Curso> curso;
    public static volatile ListAttribute<EdicionCurso, InscripcionEdicion> inscripciones;
    public static volatile SingularAttribute<EdicionCurso, LocalDate> fechaPublicacion;
    public static volatile SingularAttribute<EdicionCurso, String> nombre;
    public static volatile SingularAttribute<EdicionCurso, LocalDate> fechaFin;
    public static volatile ListAttribute<EdicionCurso, Docente> docentes;
    public static volatile SingularAttribute<EdicionCurso, Integer> cupo;

}