package Persistencia;

import javax.persistence.EntityManagerFactory;

public class ManejadorInscripcionPrograma {

    private final EntityManagerFactory emf;

    public ManejadorInscripcionPrograma(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
