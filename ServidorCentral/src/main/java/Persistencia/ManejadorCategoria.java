/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Logica.Entidades.Categoria;
import Logica.excepciones.NombreDuplicadoException;
import Logica.excepciones.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class ManejadorCategoria {
    private final EntityManagerFactory emf;

    public ManejadorCategoria(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public boolean existeCategoria(String nombre){
        EntityManager em=emf.createEntityManager();
        try{
            return em.find(Categoria.class,nombre)!=null;
        }finally{
            em.close();
        }
    }
    
    public void addCategoria(Categoria categoria){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(categoria);
            t.commit();
        }catch(Exception e){
            if (t.isActive()){
                t.rollback();
            }
            if (NombreDuplicadoException.esNombreDuplicado(e)){
                throw new NombreDuplicadoException("Ya existe una categoria con ese nombre",e);
            }else{
                throw new PersistenciaException("No se pudo guardar la categoria",e);
            }
        }finally{
            em.close();
        }
    }
    
    public List<Categoria> listarCategorias(){
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre", Categoria.class).getResultList();
        }finally{
            em.close();
        }
    }
    
    public Categoria buscarPorNombre(String nombre){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Categoria.class,nombre);
        }finally{
            em.close();
        }
    }
  
}
