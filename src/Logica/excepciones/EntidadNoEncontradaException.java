/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.excepciones;

public class EntidadNoEncontradaException extends PersistenciaException {
    public EntidadNoEncontradaException(String mensaje, Throwable motivo){
        super(mensaje,motivo);
    }
}
