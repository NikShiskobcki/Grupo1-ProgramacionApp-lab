/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.controladores;

import Logica.Entidades.Instituto;
import Logica.Entidades.Curso;
import java.time.LocalDate;
import java.util.List;

public interface IControlador {

    void altaInstituto(String nombre);

    // Alta de Curso 
    List<Instituto> listarInstitutos();
    List<Curso> listarCursos();
    boolean existeCurso(String nombre);
    void altaCurso(String nombre, String descripcion, int duracion,
                   int cantidadHoras, int creditos, String url,
                   LocalDate fechaAlta, Instituto instituto,
                   List<Curso> previas);
    
    
    boolean existeInstituto(String nombre);
    
    
}
