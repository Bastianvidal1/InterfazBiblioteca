/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Querys;
import Excepciones.ExcepcionPersonalizada;
import ControladorBasedeDatos.Controlador;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Bastian Vidal
 */
public class Querys {
    private static Controlador con = new Controlador();//SE LLAMA A LA CLASE CONTROLADOR
    private static Statement st = con.getStatement();//SE OBTIENE EL Statement DE LA CLASE CONTROLADOR
    private static ResultSet rs; //SE INSTANCIA LA CLASE ResultSet PARA SU USO
    private static String sql;//STRING PARA GUARDAR LAS CONSULTAS;

    public static Controlador getCon() {
        return con;
    }

    public static void setCon(Controlador aCon) {
        con = aCon;
    }

    public static Statement getSt() {
        return st;
    }

    public static void setSt(Statement aSt) {
        st = aSt;
    }

    public static ResultSet getRs() {
        return rs;
    }

    public static void setRs(ResultSet aRs) {
        rs = aRs;
    }

    public static String getSql() {
        return sql;
    }

    public static void setSql(String aSql) {
        sql = aSql;
    }

    
    public void CrearCategoria(String nombre_categoria){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from categorias where nombre='"+nombre_categoria+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into categorias (nombre) values ('"+nombre_categoria+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre_categoria+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearAutor(String nombre_autor, String apellidop,String apellidom){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from autores where nombre='"+nombre_autor+"' AND apellido_paterno='"+apellidop+"' "
                 + "AND apellido_materno='"+apellidom+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
          
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into autores (nombre,apellido_paterno,apellido_materno) values "
                + "('"+nombre_autor+"','"+apellidop+"','"+apellidom+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS
        
        JOptionPane.showMessageDialog(null, "'"+nombre_autor+" "+apellidop+" "+apellidom+"'  ha sido registrado correctamente",
                "REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearEditorial(String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from editoriales where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into editoriales (nombre) values ('"+nombre+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearIdioma(String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from idiomas where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into idiomas (nombre) values ('"+nombre+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearEstado(String descripcion){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select descripción from estados where descripción='"+descripcion+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into estados (descripción) values ('"+descripcion+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+descripcion+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearMetododePago(String nombre,String descripcion){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from metodos_de_pago where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into metodos_de_pago (nombre,descripcion) values ('"+nombre+"','"+descripcion+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearDistribuidor(String rut,String nombre,String pais,String ciudad,String comuna,String calle,String numeracion,
                                    long telefono, short año){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select rut from distribuidores where rut='"+rut+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         // SE INGRESAN LOS DATOS A LA TABLA DISTRIBUIDORES
        st.execute("insert into distribuidores (rut,nombre_empresa,calle,numeracion,comuna,pais,telefono,ano_inicio_ventas) "
                + "values ('"+rut+"','"+nombre+"','"+calle+"','"+numeracion+"','"+comuna+"','"+pais+"','"+telefono+"','"+año+"');");
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null,  a ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public Statement ListarDistribuidor(){
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            sql="select * from distribuidores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                
            }   
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }
        return st;
    }
    //METODO PARA LISTAR METODOS DE PAGO EN VENTANA 
    //SE DEVUELVE UN DefaultTableModel PARA SER APLICADO EN EL LISTADO INMEDIATAMENTE;
        public DefaultTableModel ListarMetodoPago(){ 
                                                     
         
            String[] Columnas = {"COD","NOMBRE","DESCRIPCIÓN"};
            DefaultTableModel modelo = new DefaultTableModel(Columnas,0);
                try{

               
                sql="select * from metodos_de_pago;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
                rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

                if(!(rs.next())){
                    throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");

                }
                   while(rs.next()){
                     String[] fila = new String[3];
                     fila[0] = rs.getString("cod");
                     fila[1] = rs.getString("nombre");
                     fila[2] = rs.getString("descripcion");
                     modelo.addRow(fila);  
                }
            }catch(SQLException e){
               JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
            }catch(ExcepcionPersonalizada a){
                JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
            }
        
        return modelo;
    }
    public Long ValidarLong(String dato, String casilla){ //MËTODO UTILIZADO PARA VALIDAR DATOS DEL TIPO LONG PARA NUMEROS TELEFÓNICOS
        Long val=null;
        try{
            val = Long.parseLong(dato);
        }catch(NumberFormatException e){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, "EL VALOR INGRESADO EN "+casilla+" NO CORRESPONDE A UN VALOR NUMERICO. INTENTE NUEVAMENTE");
        }
        return val;
    }
    
    public short ValidarAño(String dato, String casilla){//MÉTODO UTILIZADO PARA VALIDAR LOS DATOS DEL TIPO SHORT PARA AÑOS
        Calendar cal = Calendar.getInstance();
        short val = 0;
        try{
            val = Short.parseShort(dato);
            if((int)val>Calendar.YEAR){
                throw new ExcepcionPersonalizada("EL AÑO INGRESADO EN LA CASILLA "+casilla+"  NO CORRESPONDE A UN AÑO VÄLIDO. INTENTE NUEVAMENTE");
            }
                
        }catch(NumberFormatException e){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, "EL AÑO INGRESADO EN LA CASILLA "+casilla+"  NO CORRESPONDE A UN AÑO. INTENTE NUEVAMENTE");
        }catch(ExcepcionPersonalizada a){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, a);
        }
        return val;
    }
    
}
