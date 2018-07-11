/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 * La clase "ExcepcionPersonalizada" es creada con el fin de capturar excepciones 
 * provocadas en las validaciónes de datos y mostrar al usuario el error en el cual
 * se incurre
 * @author Bastian Vidal
 */


public class ExcepcionPersonalizada extends Exception {
    
    /**
     * Constructor por defecto
     */
    public ExcepcionPersonalizada() {
    }//Fin Constructor por defecto
    
    /**
     ** Este constructor declara como mensaje de excepcion en la clase Super el 
     * dato ingresado como parámetro
     * @param msg Mensaje a mostrar 
     */
    public ExcepcionPersonalizada(String msg){
        super(msg);
    }//Fin Constructor
}
