package Logica.Entidades;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-09-11T18:16:55", comments="EclipseLink-2.7.16.v20250613-rNA")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ { 

    public static volatile SingularAttribute<Usuario, LocalDate> fechaNacimiento;
    public static volatile SingularAttribute<Usuario, String> apellido;
    public static volatile SingularAttribute<Usuario, String> nickname;
    public static volatile SingularAttribute<Usuario, String> rutaImagen;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> email;

}