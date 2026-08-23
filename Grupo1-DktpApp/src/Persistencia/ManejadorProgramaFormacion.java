package Persistencia;

import javax.persistence.EntityManagerFactory;

public class ManejadorProgramaFormacion {

    private final EntityManagerFactory emf;

    public ManejadorProgramaFormacion(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
