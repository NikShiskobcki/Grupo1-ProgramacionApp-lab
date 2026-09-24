/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.excepciones;

public class RelacionInvalidaException extends PersistenciaException {
    public RelacionInvalidaException(String mensaje, Throwable motivo){
        super(mensaje,motivo);
    }
    
    public static boolean esRelacionInvalida(Throwable e){
        Throwable actual = e;
        while (actual != null){
            if (actual instanceof IllegalStateException){
                return true;
            }
            actual = actual.getCause();
        }
        return false;
    }
}
