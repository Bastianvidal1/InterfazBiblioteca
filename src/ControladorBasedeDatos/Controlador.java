/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorBasedeDatos;

import java.sql.*;//LIBRERIAS DE SQL SON IMPORTADAS
import javax.swing.JOptionPane;

/**
 *
 * @author Bastian Vidal
 * 
 */
public class Controlador {
    private static Connection conn;//DECLARACION DE DATOS DE SQL PARA UTILIZAR EN LA CONEXIÓN
    private static Statement st;
    
    /**
     * Este método devuelve un objeto de la clase Statement para ser utilizado en las transacciónes de la clase Querys
     * @return 
     */
    public Statement getStatement(){//CREACIÓN DE UN MËTODO PARA CONEXTAR A LA BASE DE DATOS
        if(conn==null){   
            try{
                Class.forName("com.mysql.jdbc.Driver");//LLAMADO A LIBRERIA DEL CONTROLADOR 
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca","root","root");//SE ESTABLECE UNA CONEXIÖN CON LA BASE DE DATOS
                st= conn.createStatement(); //SE INSTANCIA UNA SENTENCIA CON LA CONEXIÓN ESTABLECIDA
            }catch(ClassNotFoundException c){//SE DESPLIEGA UN MENSAJE CON EL ERROR CORRESPONDIENTE
                JOptionPane.showMessageDialog(null, "ERROR AL ENCONTRAR EL CONTROLADOR DE BASE DE DATOS","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
            }//Fin Try-Catch
        }//Fin IF
            return st; //SE RETORNA UN VALOR DE Statement YA OBTENIDO
        }//Fin método
}//Fin class