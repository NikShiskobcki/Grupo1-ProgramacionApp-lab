/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.excepciones;
import java.sql.SQLIntegrityConstraintViolationException;

public class NombreDuplicadoException extends PersistenciaException{
    public NombreDuplicadoException(String mensaje, Throwable motivo){
        super(mensaje,motivo);
    }
    
    public static boolean esNombreDuplicado(Throwable e){
        Throwable actual = e;
        while (actual != null){
            if (actual instanceof SQLIntegrityConstraintViolationException){
                return true;
            }
            actual = actual.getCause();
        }
        return false;
    }
}
