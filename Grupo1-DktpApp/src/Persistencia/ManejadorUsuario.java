package Persistencia;

import javax.persistence.EntityManagerFactory;

public class ManejadorUsuario {

    private final EntityManagerFactory emf;

    public ManejadorUsuario(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
