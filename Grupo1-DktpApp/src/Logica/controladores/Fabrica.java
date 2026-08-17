/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.controladores;

import Logica.controladores.IControlador;
import Logica.controladores.Controlador;


public class Fabrica {

    private static Fabrica instancia;

    private Fabrica() {
    }

    public static Fabrica getInstance() {

        if (instancia == null) {
            instancia = new Fabrica();
        }

        return instancia;
    }

    // La presentación recibe la interfaz
    public IControlador getIControlador() {

        return Controlador.getInstance();
    }
}
