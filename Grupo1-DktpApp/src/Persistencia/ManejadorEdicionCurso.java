package Persistencia;

import javax.persistence.EntityManagerFactory;

public class ManejadorEdicionCurso {

    private final EntityManagerFactory emf;

    public ManejadorEdicionCurso(EntityManagerFactory emf) {
        this.emf = emf;
    }
}