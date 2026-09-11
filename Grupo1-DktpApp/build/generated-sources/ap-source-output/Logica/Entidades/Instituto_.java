package Logica.Entidades;

import Logica.Entidades.Curso;
import Logica.Entidades.Docente;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T18:16:55", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(Instituto.class)
public class Instituto_ { 

    public static volatile ListAttribute<Instituto, Curso> cursos;
    public static volatile SingularAttribute<Instituto, String> nombre;
    public static volatile ListAttribute<Instituto, Docente> docentes;

}