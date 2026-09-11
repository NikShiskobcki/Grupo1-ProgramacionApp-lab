package Logica.Entidades;

import Logica.Entidades.Curso;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.Instituto;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T15:17:20", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(Curso.class)
public class Curso_ { 

    public static volatile SingularAttribute<Curso, String> descripcion;
    public static volatile SingularAttribute<Curso, Instituto> instituto;
    public static volatile ListAttribute<Curso, Curso> previas;
    public static volatile SingularAttribute<Curso, Integer> cantidadHoras;
    public static volatile ListAttribute<Curso, EdicionCurso> ediciones;
    public static volatile SingularAttribute<Curso, LocalDate> fechaAlta;
    public static volatile SingularAttribute<Curso, Integer> duracion;
    public static volatile SingularAttribute<Curso, Integer> creditos;
    public static volatile SingularAttribute<Curso, String> nombre;
    public static volatile SingularAttribute<Curso, String> url;

}