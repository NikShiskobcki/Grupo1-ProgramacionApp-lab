/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Logica.Entidades.Instituto;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 *
 * @author xnahu
 */
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
 
 public List<Instituto> listarInstitutos() {
    EntityManager em = emf.createEntityManager();
    try {
        return em.createQuery("SELECT i FROM Instituto i", Instituto.class).getResultList();
    } finally {
        em.close();
    }
}
 
 public Instituto buscarPorNombre(String nombre){
     EntityManager em = emf.createEntityManager();
     try{
         return em.find(Instituto.class, nombre); //nombre es @id
     }finally{
         em.close();
     }
 }
 
}
