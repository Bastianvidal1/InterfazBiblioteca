/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author Bastian Vidal
 */
/*LA CLASE EXCEPCIONPERSONALIZADA FUE CREADA CON EL FIN DE CAPTURAR EXCEPCIONES 
DE LAS VALIDACIONES DE DATOS DE MANERA PERSONALIZADA*/
public class ExcepcionPersonalizada extends Exception {

    public ExcepcionPersonalizada() {
    }
   
    public ExcepcionPersonalizada(String msg){
        super(msg);
}
}
