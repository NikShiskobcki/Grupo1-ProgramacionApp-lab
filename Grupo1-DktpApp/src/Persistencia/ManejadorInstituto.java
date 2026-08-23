
package Persistencia;

import Logica.Entidades.Instituto;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ManejadorInstituto {
    private final EntityManagerFactory emf; 

    public ManejadorInstituto(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
 public void addInstituto(Instituto instituto){
  EntityManager em =emf.createEntityManager(); 
  EntityTransaction t= em.getTransaction(); 
  
  try{
      t.begin(); 
      em.persist(instituto);
      t.commit();
      }catch(Exception e){
          if(t.isActive()){
          t.rollback();}
          throw e; 
 }finally{
      em.close();
  }
    
}

 /*
 public List<Instituto> listarInstitutos() {
    EntityManager em = emf.createEntityManager();
    try {
        return em.createQuery("SELECT i FROM Instituto i", Instituto.class).getResultList();
    } finally {
        em.close();
    }
}*/
 
 public Instituto buscarPorNombre(String nombre){
     EntityManager em = emf.createEntityManager();
     try{
         return em.find(Instituto.class, nombre); //nombre es @id
     }finally{
         em.close();
     }
 }
 

    public List<Instituto> listarInstitutos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Instituto i ORDER BY i.nombre", Instituto.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Instituto buscarInstituto(String nombre) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Instituto.class, nombre);
        } finally {
            em.close();
        }
    }
}
