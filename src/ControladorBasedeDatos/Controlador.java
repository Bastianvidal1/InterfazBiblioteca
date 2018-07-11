/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorBasedeDatos;

import java.sql.*;//LIBRERIAS DE SQL SON IMPORTADAS
import javax.swing.JOptionPane;

/**
 * Clase controladora de la base de datos
 * @author Bastian Vidal
 * 
 */
public class Controlador {
    private static Connection conn;//DECLARACION DE DATOS DE SQL PARA UTILIZAR EN LA CONEXIÓN
    private static Statement st;
    public  static String user;
    public  static String password;

 
    
    /**
     * Este método devuelve un objeto de la clase Statement para ser utilizado en las transacciónes de la clase Querys
     * @return Un Statement utilizado para las transacciones con la base de datos
     */
    public Statement getStatement(){//CREACIÓN DE UN MËTODO PARA CONEXTAR A LA BASE DE DATOS
        if(conn==null){   
            try{
                Class.forName("com.mysql.jdbc.Driver");//LLAMADO A LIBRERIA DEL CONTROLADOR
                user = JOptionPane.showInputDialog("INGRESE NOMBRE USUARIO"); //SE SOLICITA LOS DATOS DE USUARIO DE LA BASE DE DATOS
                password = JOptionPane.showInputDialog("INGRESE CONTRASEÑA"); // SE SOLICITA LOS DATOS DE CONTRASEÑA DE LA BASE DE DATOS
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca",user,password);//SE ESTABLECE UNA CONEXIÖN CON LA BASE DE DATOS
                st= conn.createStatement(); //SE INSTANCIA UNA SENTENCIA CON LA CONEXIÓN ESTABLECIDA
            }catch(ClassNotFoundException c){//SE DESPLIEGA UN MENSAJE CON EL ERROR CORRESPONDIENTE
                JOptionPane.showMessageDialog(null, "ERROR AL ENCONTRAR EL CONTROLADOR DE BASE DE DATOS","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
            }catch(SQLException e){//EN CASO DE FALLAR LA CONEXIÓN SE SOLICITA SE PROCEDE A REINTENTAR O NO LA CONEXIÓN
                JOptionPane.showMessageDialog(null,"USUARIO O CONTRASEÑA INCORRECTOS","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);//SE INFORMA AL USUARIO
                int op = JOptionPane.showOptionDialog( null,"SELECCIONE OPCIÓN A SEGUIR","ERROR DE INGRESO",JOptionPane.YES_NO_CANCEL_OPTION,//MUESTRA UN MENSAJE DE CONFIRMACIÓN
                JOptionPane.INFORMATION_MESSAGE,null,new String[] { "Volver a intentar", "Salir"},"Volver a intentar");
                
                if(op==0){//SI LA RESPUESTA ES "Volver a intentar" SE EXECUTA DENUEVO EL MÉTODO
                    st= getStatement();  
                }else{// SI LA RESPUESTA ES OTRA SE INTERRUMPE EL SISTEMA
                  System.exit(0);
                }//Fin IF
   
            }//Fin Try-Catch
        }//Fin IF
            return st; //SE RETORNA UN VALOR DE Statement YA OBTENIDO
        }//Fin método
}//Fin class