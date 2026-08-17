/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.controladores;

import Logica.Entidades.Instituto;
import Persistencia.ManejadorInstituto;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Controlador implements IControlador {

    private static Controlador instancia;
    
    //Fabrica de entityManager utilizada por todos los manejadores
    private final EntityManagerFactory emf;
   
    
    //Manejadores;
     private final ManejadorInstituto manejadorInstituto;

    // Constructor privado para aplicar Singleton
     
    private Controlador() {
        //Fabrica de entidades creada una sola vez para toda la app
        emf= Persistence.createEntityManagerFactory("edextPU"); 
        
         //Manejadores que comparten la misma EMF 
         manejadorInstituto= new ManejadorInstituto(emf); 
        
    }

    
    public static Controlador getInstance() {

        if (instancia == null) {
            instancia = new Controlador();
        }

        return instancia;
    }


    //Aca implementamos los casos de uso 
     
    @Override
    public void altaInstituto(String nombre) {

        Instituto instituto = new Instituto(nombre);

        manejadorInstituto.addInstituto(instituto);

    }
    
}
