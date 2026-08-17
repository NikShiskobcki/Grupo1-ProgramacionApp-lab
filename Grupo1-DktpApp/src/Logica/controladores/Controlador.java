/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.controladores;


public class Controlador implements IControlador {

    private static Controlador instancia;
    //Aca iria la declaracion de los manejadores;

    // Constructor privado para aplicar Singleton
    private Controlador() {

         //Cuando creemos los manejadores los instanciamos aca 
        
    }

    
    public static Controlador getInstance() {

        if (instancia == null) {
            instancia = new Controlador();
        }

        return instancia;
    }


    //Aca implementamos los casos de uso 

}
