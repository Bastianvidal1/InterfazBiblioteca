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
import javax.swing.DefaultListModel;
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
    private static ResultSet rscod; //SE INSTANCIA LA CLASE ResultSet PARA USARLA EN LAS RELACIONES
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
    
    public void CrearCompra(DefaultListModel libros,int distribuidor,int factura){
        try{
         sql= "select * from compra where factura='"+factura+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
        st.execute("insert into compra (dist_involucrado,factura) " // SE INGRESA EL REGISTRO A LA BASE DE DATOS
                + "values ('"+distribuidor+"','"+factura+"');");
        
        rscod = st.executeQuery("select max(cod) from compra");
        rscod.next();
        String codcompra = rscod.getString(1);
        
          for (int i = 0; i < libros.getSize(); i++) {
              st.execute("insert into compra_libro (compra_asoc,libro_asoc) values ('"+codcompra+"','"+libros.getElementAt(i)+"');");
          }
                    
        JOptionPane.showMessageDialog(null, "L compra  ha sido registrada correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }catch(Exception b){// CAPTURA DE CUALQUIER EXCEPCIÓN GENERADA 
        JOptionPane.showMessageDialog(null, b.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      } 
    }
    
     public void CrearLibro(String nserie, String isbn, String titulo, int npaginas, int precioref, DefaultListModel idiomas
                            ,short ano_publicacion,DefaultListModel autores, int editorial, DefaultListModel categorias,int estado){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select * from libros where num_serie='"+nserie+"' OR isbn ='"+isbn+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
        st.execute("insert into libros (num_serie,isbn,titulo,npaginas,precio_ref,ano_publicacion,editorial,estado) " // SE INGRESA EL REGISTRO A LA BASE DE DATOS
                + "values ('"+nserie+"','"+isbn+"','"+titulo+"','"+npaginas+"','"+precioref+"','"+ano_publicacion+"','"+editorial+"','"+estado+"');");
        
        rscod = st.executeQuery("select max(cod) from libros");
        rscod.next();
        String codlibro = rscod.getString(1);
        
          for (int i = 0; i < idiomas.getSize(); i++) {
              st.execute("insert into libro_idiomas (cod_libro,cod_idioma) values ('"+codlibro+"','"+idiomas.getElementAt(i)+"');");
          }
          
          for (int i = 0; i < categorias.getSize(); i++) {
              st.execute("insert into libro_categoria(cod_libro,cod_categoria) values ('"+codlibro+"','"+categorias.getElementAt(i)+"');");
          }
                    
          for (int i = 0; i < autores.getSize(); i++) {
              st.execute("insert into libro_autores (cod_libro,cod_autor) values ('"+codlibro+"','"+autores.getElementAt(i)+"');");
          }
          
        JOptionPane.showMessageDialog(null, "'"+titulo+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
      }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
      }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
        JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }catch(Exception b){// CAPTURA DE CUALQUIER EXCEPCIÓN GENERADA 
        JOptionPane.showMessageDialog(null, b.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }  
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
         sql= "select descripcion from estados where descripcion='"+descripcion+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into estados (descripcion) values ('"+descripcion+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
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
    
    public void CrearFactura(String folio,int precio_neto,int precio_iva,int costo_iva,String fecha_compra,String hora_compra,int dist_involucrado,int metodo_pago){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
      try{
         sql= "select * from facturas where folio='"+folio+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
         rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
         
         if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
            throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
         }
         
        st.execute("insert into facturas (folio,precio_neto,precio_iva,costo_iva,fecha_compra,hora_compra,dist_involucrado,metodo_pago) "
                + "values ('"+folio+"','"+precio_neto+"','"+precio_iva+"','"+costo_iva+"','"+fecha_compra+"','"
                +hora_compra+"','"+dist_involucrado+"','"+metodo_pago+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
        JOptionPane.showMessageDialog(null, "'"+folio+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
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
    
    public Statement ListarDistribuidorCB(){
        try{
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
    
    public Statement ListarFacturaCB(){
        try{
            sql="select * from facturas;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
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
    
    public Statement ListarMetodoPagoCB(){
        try{
            sql="select * from metodos_de_pago;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
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
    
    public Statement ListarLibrosCB(){
        try{
            sql="select * from libros;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
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
    
    public Statement ListarIdiomaCB(){
        try{
            sql="select * from idiomas;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                
            }   
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }
        return st;
    }
     
    public Statement ListarEditorialCB(){
        try{
            sql="select * from editoriales;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                
            }   
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }
        return st;
    }
     
    public Statement ListarAutoresCB(){
        try{
            sql="select * from autores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                
            }   
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }
        return st;
    }
     
    public Statement ListarCategoriasCB(){
        try{
            sql="select * from categorias;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");  
            }   
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }
        return st;
    }
    
    public Statement ListarEstadoCB(){
        try{
            sql="select * from estados;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");  
            }   
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }
        return st;
    }
    //SE DEVUELVE UN DefaultTableModel PARA SER APLICADO EN EL LISTADO INMEDIATAMENTE;
    public DefaultTableModel ListarMetodoPago(){
        String[] Columnas = {"COD","NOMBRE","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[3];
        try{                
            sql="select * from metodos_de_pago;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");
            fila[2] = rs.getString("descripcion");
            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                modelo.addRow(fila);  
            }

    }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
       JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
    }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
        JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
    }

return modelo;//SE RETORNA EL MODELO DE LA TABLA 
    }
    
    public DefaultTableModel ListarEstado(){
    String[] Columnas = {"COD","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
    DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
    String[] fila = new String[2];
    try{                
        sql="select * from estados;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
        rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

        if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
            throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
        }
        //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
        //AL MODELO
        fila[0] = rs.getString("cod");
        fila[1] = rs.getString("descripción");

        modelo.addRow(fila);

        while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("descripción");
            modelo.addRow(fila);  
        }

}catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
   JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
}catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
    JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

}
    return modelo;
}
    
    public DefaultTableModel ListarIdiomas(){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{                
            sql="select * from idiomas;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
            }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;

    }  

    public DefaultTableModel ListarCategorias(){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{                
            sql="select * from categorias;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
    
    public DefaultTableModel ListarEditoriales(){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{                
            sql="select * from editoriales;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
    
    public DefaultTableModel ListarDistribuidores(){
       String[] Columnas = {"COD","RUT","NOMBRE EMPRESA","CALLE","NUMERACION","COMUNA","PAIS","TELÉFONO","AÑO INICIO VENTAS"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[9];
        try{                
            sql="select * from distribuidores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("rut");
            fila[2] = rs.getString("nombre_empresa");
            fila[3] = rs.getString("calle");
            fila[4] = rs.getString("numeracion");
            fila[5] = rs.getString("comuna");
            fila[6] = rs.getString("pais");
            fila[7] = rs.getString("telefono");
            fila[8] = ""+rs.getInt("ano_inicio_ventas");
            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("rut");
                fila[2] = rs.getString("nombre_empresa");
                fila[3] = rs.getString("calle");
                fila[4] = rs.getString("numeracion");
                fila[5] = rs.getString("comuna");
                fila[6] = rs.getString("pais");
                fila[7] = rs.getString("telefono");
                fila[8] = ""+rs.getInt("ano_inicio_ventas");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
    
    public DefaultTableModel ListarAutores(){
        String[] Columnas = {"COD","NOMBRE","APELLIDO PATERNO","APELLIDO MATERNO"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[4];
        try{                
            sql="select * from autores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");
            fila[2] = rs.getString("apellido_paterno");
            fila[3] = rs.getString("apellido_materno");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido_paterno");
                fila[3] = rs.getString("apellido_materno");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a,"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
    
    public DefaultTableModel FiltrarMetodoPago(String texto){
        String[] Columnas = {"COD","NOMBRE","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[3];

        try{
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarMetodoPago();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }
            sql="select * from metodos_de_pago where cod='"+texto+"' OR nombre='"+texto+"' OR descripcion='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
           
            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");
            fila[2] = rs.getString("descripcion");
            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                modelo.addRow(fila);  
            }

    }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
       JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
    }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
        JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
    }

    return modelo;//SE RETORNA EL MODELO DE LA TABLA 
    }
     
    public DefaultTableModel FiltrarEstado(String texto){
        String[] Columnas = {"COD","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{   
            if(texto.equals(" ") || texto.isEmpty()){
                    JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
                   return ListarEstado();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
                }
            sql="select * from estados where cod ='"+texto+"' OR descripcion='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("descripción");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("descripción");
                modelo.addRow(fila);  
            }

    }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
       JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
    }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
        JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

    }
        return modelo;
    }
     
    public DefaultTableModel FiltrarIdiomas(String texto){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{   
             if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarIdiomas();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }
            sql="select * from idiomas where cod='"+texto+"' OR nombre='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
            }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;

    }  
    
    public DefaultTableModel FiltrarCategorias(String texto){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{    
             if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarCategorias();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }
            sql="select * from categorias where cod='"+texto+"' OR nombre='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
    
    public DefaultTableModel FiltrarEditoriales(String texto){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{ 
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarEditoriales();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }
            sql="select * from editoriales where cod='"+texto+"' OR nombre='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
     
    public DefaultTableModel FiltrarDistribuidores(String texto){
       String[] Columnas = {"COD","RUT","NOMBRE EMPRESA","CALLE","NUMERACION","COMUNA","PAIS","TELÉFONO","AÑO INICIO VENTAS"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[9];
        try{    
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarDistribuidores();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }
            sql="select * from distribuidores where cod='"+texto+"' OR rut='"+texto+"' OR nombre_empresa='"+texto+"' OR calle='"+texto+"' OR numeracion='"+texto+"' OR comuna='"+texto+"' "
                    + "OR pais='"+texto+"' OR telefono='"+texto+"' OR ano_inicio_ventas='"+texto+"' ;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("rut");
            fila[2] = rs.getString("nombre_empresa");
            fila[3] = rs.getString("calle");
            fila[4] = rs.getString("numeracion");
            fila[5] = rs.getString("comuna");
            fila[6] = rs.getString("pais");
            fila[7] = rs.getString("telefono");
            fila[8] = ""+rs.getInt("ano_inicio_ventas");
            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("rut");
                fila[2] = rs.getString("nombre_empresa");
                fila[3] = rs.getString("calle");
                fila[4] = rs.getString("numeracion");
                fila[5] = rs.getString("comuna");
                fila[6] = rs.getString("pais");
                fila[7] = rs.getString("telefono");
                fila[8] = ""+rs.getInt("ano_inicio_ventas");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
    
    public DefaultTableModel FiltrarAutores(String texto){
        String[] Columnas = {"COD","NOMBRE","APELLIDO PATERNO","APELLIDO MATERNO"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[4];
        try{ 
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarAutores();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }
            sql="select * from autores where cod='"+texto+"' OR nombre='"+texto+"' OR apellido_paterno='"+texto+" OR apellido_materno='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");
            fila[2] = rs.getString("apellido_paterno");
            fila[3] = rs.getString("apellido_materno");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido_paterno");
                fila[3] = rs.getString("apellido_materno");
                modelo.addRow(fila);  
             }

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }
            return modelo;
    }
     
    public void EliminarMetodoPago(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from metodos_de_pago where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA


            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

         }catch(ExcepcionPersonalizada a){
           JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public void EliminarDistribuidores(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from distribuidores where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA


            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

         }catch(ExcepcionPersonalizada a){
           JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public void EliminarIdiomas(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from idiomas where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA


            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

         }catch(ExcepcionPersonalizada a){
           JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public void EliminarCategorias(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from categorias where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA


            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

         }catch(ExcepcionPersonalizada a){
           JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public void EliminarEditoriales(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from editoriales where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA


            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

         }catch(ExcepcionPersonalizada a){
           JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }
        }

    public void EliminarAutores(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from autores where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA


            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

         }catch(ExcepcionPersonalizada a){
           JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }
        }

    public void EliminarEstado(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from estados where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA


            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

         }catch(ExcepcionPersonalizada a){
           JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e,"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }
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
    
     public int ValidarInteger(String dato){ //MËTODO UTILIZADO PARA VALIDAR DATOS DEL TIPO LONG PARA NUMEROS TELEFÓNICOS
        int val = 0;
        try{
            val = Integer.parseInt(dato);
        }catch(NumberFormatException e){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, "UN VALOR INGRESADO NO CORRESPONDE A UN VALOR NUMERICO. INTENTE NUEVAMENTE");
        }
        return val;
    }
    
    public short ValidarAño(String dato){//MÉTODO UTILIZADO PARA VALIDAR LOS DATOS DEL TIPO SHORT PARA AÑOS
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        short val = 0;
        try{
            val = Short.parseShort(dato);
            if((int)val>year){
                throw new ExcepcionPersonalizada("EL AÑO INGRESADO NO CORRESPONDE A UN AÑO VÄLIDO. INTENTE NUEVAMENTE");
            }
                
        }catch(NumberFormatException e){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, "EL AÑO INGRESADO NO CORRESPONDE A UN AÑO VÁLIDO. INTENTE NUEVAMENTE");
        }catch(ExcepcionPersonalizada a){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, a);
        }
        return val;
    }
    
    public String ValidarFecha(String dia,String mes){
        String fecha=null;
        try{
            if(Integer.parseInt(dia)>31){
                throw new ExcepcionPersonalizada("DIA INGRESADO NO ES VÁLIDO");
            }
            if(Integer.parseInt(mes)>12){
                throw new ExcepcionPersonalizada("MES INGRESADO NO ES VÁLIDO");
            }
           fecha = (mes+"-"+dia);
        }catch(ExcepcionPersonalizada e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch(Exception a){
            JOptionPane.showMessageDialog(null, a.getMessage());
        }
        
        return fecha;
    }
    
    public String ValidarHora(String hora,String minutos){
        String time = null;
        try{
            if(Integer.parseInt(hora)>23){
                throw new ExcepcionPersonalizada("LA HORA INGRESADA NO ES VÁLIDA");
            }
            if(Integer.parseInt(minutos)>59){
                throw new ExcepcionPersonalizada("LOS MINUTOS INGRESADOS NO SON VÁLIDOS");
            }
          time=(hora+":"+minutos);  
        }catch(ExcepcionPersonalizada e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch(Exception a){
            JOptionPane.showMessageDialog(null, a.getMessage());
        }
        return time;
    }
    
}
