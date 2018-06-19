/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Querys;
import ControladorBasedeDatos.Controlador;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Bastian Vidal
 */
public class Querys {
    static Controlador con = new Controlador();//SE LLAMA A LA CLASE CONTROLADOR
    static Statement st = con.getStatement();//SE OBTIENE EL Statement DE LA CLASE CONTROLADOR
    static ResultSet rs; //SE INSTANCIA LA CLASE ResultSet PARA SU USO
    static String sql;//STRING PARA GUARDAR LAS CONSULTAS;

    
    public void CrearCategoria(String nombre_categoria){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from categorias where nombre='"+nombre_categoria+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            int error = Integer.parseInt("a");
         }
         
        st.execute("insert into categorias (nombre) values ('"+nombre_categoria+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre_categoria+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(NumberFormatException a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA EXISTE ","ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearAutor(String nombre_autor, String apellidop,String apellidom){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from autores where nombre='"+nombre_autor+"' AND apellido_paterno='"+apellidop+"' "
                 + "AND apellido_materno='"+apellidom+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
          
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            int error = Integer.parseInt("a");
         }
         
        st.execute("insert into autores (nombre,apellido_paterno,apellido_materno) values "
                + "('"+nombre_autor+"','"+apellidop+"','"+apellidom+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS
        
        JOptionPane.showMessageDialog(null, "'"+nombre_autor+" "+apellidop+" "+apellidom+"'  ha sido registrado correctamente",
                "REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(NumberFormatException a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA EXISTE ","ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearEditorial(String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from editoriales where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            int error = Integer.parseInt("a");
         }
         
        st.execute("insert into editoriales (nombre) values ('"+nombre+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(NumberFormatException a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA EXISTE ","ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearIdioma(String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from idiomas where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            int error = Integer.parseInt("a");
         }
         
        st.execute("insert into idiomas (nombre) values ('"+nombre+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(NumberFormatException a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA EXISTE ","ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearEstado(String descripcion){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select descripción from estados where descripción='"+descripcion+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            int error = Integer.parseInt("a");
         }
         
        st.execute("insert into estados (descripción) values ('"+descripcion+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+descripcion+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(NumberFormatException a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA EXISTE ","ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
    public void CrearMetododePago(String nombre,String descripcion){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select nombre from metodos_de_pago where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            int error = Integer.parseInt("a");
         }
         
        st.execute("insert into metodos_de_pago (nombre,descripcion) values ('"+nombre+"','"+descripcion+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(NumberFormatException a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA EXISTE ","ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
    
     public void CrearDistribuidor(String rut,String nombre,String pais,String ciudad,String comuna,String calle,String numeracion,
                                    long telefono, short año){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select rut from distribuidores where rut='"+rut+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            int error = Integer.parseInt("a");
         }
         // SE INGRESAN LOS DATOS POR SEPARADO A LAS TABLAS CORRESPONDIENTES
        st.execute("insert into distribuidores (rut,nombre_empresa,ano_inicio_ventas) values ('"+rut+"','"+nombre+"','"+año+"');");
        st.execute("insert into direcciones (pais,ciudad,comuna,calle,numeracion) values ('"+pais+"','"+ciudad+"','"+comuna+"','"+calle+"','"+numeracion+"');");
        st.execute("insert into telefonos (num_telefono) values ('"+telefono+"');");
        
        //SE BUSCAN LOS ULTIMOS REGISTROS INGRESADOS EN LAS TABLAS RELACIONADAS CON EL DISTRIBUIDOR Y A LAS QUE SE
        // LE INGRESARON DATOS
        rs = st.executeQuery("select max(cod) from direcciones");
        rs.next();
        String cod_d = rs.getString(1);
        
        rs = st.executeQuery("select max(cod) from telefonos");
        rs.next(); 
        String cod_t = rs.getString(1);
        
        rs = st.executeQuery("select max(cod) from distribuidores");
        rs.next(); 
        String cod_dis = rs.getString(1);
        
        //SE ACTUALIZA LAS CLAVES FORANEAS DE LA TABLA DISTRIBUIDORES CON LA DIRECCION Y TELEFONO ASOCIADO
        st.execute("update distribuidores set direccion_asoc ='"+cod_d+"', telefono_asoc ='"+cod_t+"' where cod='"+cod_dis+"'");
        
        JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(NumberFormatException a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA EXISTE ","ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
    }
}
