package Persistencia;

import javax.persistence.EntityManagerFactory;

public class ManejadorCurso {

    private final EntityManagerFactory emf;

    public ManejadorCurso(EntityManagerFactory emf) {
        this.emf = emf;
    }
}