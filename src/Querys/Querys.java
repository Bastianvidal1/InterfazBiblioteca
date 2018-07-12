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
 * La clase Query contiene los métodos encargados de realizar las transacciones con la base de datos 
 * según se requiera
 * @author Bastian Vidal
 * @version 1.0 02/07/2018
 */
public class Querys {
    private static Controlador con = new Controlador();//SE LLAMA A LA CLASE CONTROLADOR
    private static Statement st = con.getStatement();//SE OBTIENE EL Statement DE LA CLASE CONTROLADOR
    private static ResultSet rs; //SE INSTANCIA LA CLASE ResultSet PARA SU USO
    private static ResultSet rscod; //SE INSTANCIA LA CLASE ResultSet PARA USARLA EN LAS RELACIONES
    private static String sql;//STRING PARA GUARDAR LAS CONSULTAS;

    /**
     * Constructor por defecto
     */
    public Querys() {
    }

    
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
    
    /**
     * Recibe los datos del formulario Registro_Compra y los ingresa en la base de datos. 
     * @param libros DefaultListModel con los codigos correspondientes
     * @param distribuidor Dato a ingresar
     * @param factura Dato a ingresar
     */
    public void CrearCompra(DefaultListModel libros,int distribuidor,int factura){//MÉTODO INCLUTE CONSULTA PARA INSERTAR REGISTROS EN LA TABLA COMPRA Y SUS RELACONES
        try{
            sql= "select * from compra where factura='"+factura+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

                if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                   throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
                }//Fin IF
            st.execute("insert into compra (dist_involucrado,factura) " // SE INGRESA EL REGISTRO A LA BASE DE DATOS
                   + "values ('"+distribuidor+"','"+factura+"');");

            rscod = st.executeQuery("select max(cod) from compra");//SE EJECUTA UNA CONSULTA BUSCANDO LA ULTIMA COMPRA INGRESADA
            rscod.next();
            String codcompra = rscod.getString(1);//EL CODIGO DE LA ÚLTIMA COMPRA ES INGRESADO EN UNA VARIABLE
            
                for (int i = 0; i < libros.getSize(); i++) {//SE INGRESAN LOS LIBROS SELECCIONADOS EN LA TABLA RELACIONAL COMPRA_LIBRO
                    st.execute("insert into compra_libro (compra_asoc,libro_asoc) values ('"+codcompra+"','"+libros.getElementAt(i)+"');");
                }//Fin FOR

            JOptionPane.showMessageDialog(null, "La compra  ha sido registrada correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(Exception b){// CAPTURA DE CUALQUIER EXCEPCIÓN GENERADA 
            JOptionPane.showMessageDialog(null, b.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch 
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_Libro y los ingresa en la base de datos.
     * @param nserie Dato correspondiente
     * @param isbn Dato correspondiente
     * @param titulo Dato correspondiente
     * @param npaginas Dato correspondiente
     * @param precioref Dato correspondiente
     * @param idiomas DefaultListModel con los codigos correspondientes
     * @param ano_publicacion Dato correspondiente
     * @param autores DefaultListModel con los codigos correspondientes
     * @param editorial Dato correspondiente
     * @param categorias DefaultListModel con los codigos correspondientes
     * @param estado Código correspondiente
     */
    public void CrearLibro(String nserie, String isbn, String titulo, int npaginas, int precioref, DefaultListModel idiomas
                            ,short ano_publicacion,DefaultListModel autores, int editorial, DefaultListModel categorias,int estado){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE LIBRO
        try{
            sql= "select * from libros where num_serie='"+nserie+"' OR isbn ='"+isbn+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

                if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                    throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
                }//Fin IF
                
                //SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR El ERROR
                if(nserie.trim().isEmpty() || isbn.trim().isEmpty() || titulo.trim().isEmpty() || npaginas==0 || precioref==0 || ano_publicacion ==0 || editorial==0 || estado==0){
                    throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
                }//Fin IF
            st.execute("insert into libros (num_serie,isbn,titulo,npaginas,precio_ref,ano_publicacion,editorial,estado) " // SE INGRESA EL REGISTRO A LA BASE DE DATOS
                    + "values ('"+nserie+"','"+isbn+"','"+titulo+"','"+npaginas+"','"+precioref+"','"+ano_publicacion+"','"+editorial+"','"+estado+"');");

            rscod = st.executeQuery("select max(cod) from libros"); //SE EJECUTA UNA CONSULTA BUSCANDO EL ULTIMO LIBRO INGRESADO
            rscod.next();
            String codlibro = rscod.getString(1);//EL CODIGO DEL ULTIMO LIBRO ES GUARDADO EN UNA VARIABLE

                for (int i = 0; i < idiomas.getSize(); i++) {//SE INGRESAN LOS IDIOMAS ASOCIADOS CON EL LIBRO INGRESADO EN LA TABLA RELACIONAL LIBRO_IDIOMAS
                    st.execute("insert into libro_idiomas (cod_libro,cod_idioma) values ('"+codlibro+"','"+idiomas.getElementAt(i)+"');");
                }//Fin FOR

                for (int i = 0; i < categorias.getSize(); i++) {//SE INGRESAN LOS IDIOMAS ASOCIADOS CON EL LIBRO INGRESADO EN LA TABLA RELACIONAL LIBRO_CATEGORIA
                    st.execute("insert into libro_categoria(cod_libro,cod_categoria) values ('"+codlibro+"','"+categorias.getElementAt(i)+"');");
                }//Fin FOR

                for (int i = 0; i < autores.getSize(); i++) {//SE INGRESAN LOS IDIOMAS ASOCIADOS CON EL LIBRO INGRESADO EN LA TABLA RELACIONAL LIBRO_AUTORES
                    st.execute("insert into libro_autores (cod_libro,cod_autor) values ('"+codlibro+"','"+autores.getElementAt(i)+"');");
                }//Fin FOR

            JOptionPane.showMessageDialog(null, "'"+titulo+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(Exception b){// CAPTURA DE CUALQUIER EXCEPCIÓN GENERADA 
            JOptionPane.showMessageDialog(null, b.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin método
     
    /**
     * Recibe los datos del formulario Registro_Categoria y los ingresa en la base de datos. 
     * @param nombre_categoria Dato correspondiente  
     */
    public void CrearCategoria(String nombre_categoria){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE CATEGORIA
        try{
            sql= "select nombre from categorias where nombre='"+nombre_categoria+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }//Fin IF
            
            if (nombre_categoria.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF

            st.execute("insert into categorias (nombre) values ('"+nombre_categoria+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
            JOptionPane.showMessageDialog(null, "'"+nombre_categoria+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
      }//Fin Try-Catch
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_Autor y los ingresa en la base de datos.
     * @param nombre_autor Dato correspondiente
     * @param apellidop Dato correspondiente
     * @param apellidom Dato correspondiente   
     */
    public void CrearAutor(String nombre_autor, String apellidop,String apellidom){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE AUTOR
        try{
            sql= "select nombre from autores where nombre='"+nombre_autor+"' AND apellido_paterno='"+apellidop+"' "
                   + "AND apellido_materno='"+apellidom+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO

            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }
            
            if(nombre_autor.trim().isEmpty() || apellidop.trim().isEmpty() || apellidom.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF

            st.execute("insert into autores (nombre,apellido_paterno,apellido_materno) values "
                  + "('"+nombre_autor+"','"+apellidop+"','"+apellidom+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS

            JOptionPane.showMessageDialog(null, "'"+nombre_autor+" "+apellidop+" "+apellidom+"'  ha sido registrado correctamente",
                  "REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_Editorial y los ingresa en la base de datos. 
     * @param nombre Dato correspondiente 
     */
    public void CrearEditorial(String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE EDITORIAL
        try{
            sql= "select nombre from editoriales where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }//Fin IF
            
            if(nombre.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF

            st.execute("insert into editoriales (nombre) values ('"+nombre+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_Idioma y los ingresa en la base de datos. 
     * @param nombre Dato correspondiente     
     */
    public void CrearIdioma(String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE IDIOMA
        try{
            sql= "select nombre from idiomas where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }//Fin IF
            
             if(nombre.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            st.execute("insert into idiomas (nombre) values ('"+nombre+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTIRA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_Estado y los ingresa en la base de datos.
     * @param descripcion Dato correspondiente  
      */
    public void CrearEstado(String descripcion){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE ESTADO
        try{
            sql= "select descripcion from estados where descripcion='"+descripcion+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }//Fin IF
            
             if(descripcion.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF

            st.execute("insert into estados (descripcion) values ('"+descripcion+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
            JOptionPane.showMessageDialog(null, "'"+descripcion+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_MetododePago y los ingresa en la base de datos. 
     * @param nombre Dato correspondiente 
     * @param descripcion Dato correspondiente     
     */
    public void CrearMetododePago(String nombre,String descripcion){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE METODO DE PAGO
        try{
            sql= "select nombre from metodos_de_pago where nombre='"+nombre+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }//Fin IF
            
             if(nombre.trim().isEmpty() || descripcion.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF

            st.execute("insert into metodos_de_pago (nombre,descripcion) values ('"+nombre+"','"+descripcion+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_Factura y los ingresa en la base de datos. 
     * @param folio Dato correspondiente 
     * @param precio_neto Dato correspondiente 
     * @param precio_iva Dato correspondiente 
     * @param costo_iva Dato correspondiente 
     * @param fecha_compra Dato correspondiente 
     * @param hora_compra Dato correspondiente 
     * @param dist_involucrado Código correspondiente 
     * @param metodo_pago Código correspondiente     
     */
    public void CrearFactura(String folio,int precio_neto,int precio_iva,int costo_iva,String fecha_compra,String hora_compra,int dist_involucrado,int metodo_pago){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE FACTURA
        try{
            sql= "select * from facturas where folio='"+folio+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÖN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }//Fin IF

             if(folio.trim().isEmpty() || fecha_compra.trim().isEmpty() || hora_compra.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            st.execute("insert into facturas (folio,precio_neto,precio_iva,costo_iva,fecha_compra,hora_compra,dist_involucrado,metodo_pago) "
                  + "values ('"+folio+"','"+precio_neto+"','"+precio_iva+"','"+costo_iva+"','"+fecha_compra+"','"
                  +hora_compra+"','"+dist_involucrado+"','"+metodo_pago+"');");// SE INGRESA EL REGISTRO A LA BASE DE DATOS 
            JOptionPane.showMessageDialog(null, "'"+folio+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch 
    }//Fin método
    
    /**
     * Recibe los datos del formulario Registro_Distribuidor y los ingresa en la base de datos. 
     * @param rut Dato correspondiente 
     * @param nombre Dato correspondiente 
     * @param pais Dato correspondiente 
     * @param ciudad Dato correspondiente 
     * @param comuna Dato correspondiente 
     * @param calle Dato correspondiente 
     * @param numeracion Dato correspondiente 
     * @param telefono Dato correspondiente 
     * @param año Dato correspondiente  
     */
    public void CrearDistribuidor(String rut,String nombre,String pais,String ciudad,String comuna,String calle,String numeracion,
                                    long telefono, short año){//MÉTODO QUE INCLUYE CONSULTA PARA INSERTAR RESGITRO DE DISTRIBUIDOR
        try{
            sql= "select rut from distribuidores where rut='"+rut+"';";//SE CONFIRMA QUE EL VALOR INGRESADO NO SE ENCUENTRE REGISTRADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if (rs.next()){  //SI SE ENCUENTRA REGISTRADO EL VALOR SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                throw new ExcepcionPersonalizada("ESTE REGISTRO YA EXISTE");
            }//Fin IF
            
             if(rut.trim().isEmpty() || nombre.trim().isEmpty() || pais.trim().isEmpty() || ciudad.trim().isEmpty()
                     || comuna.trim().isEmpty() || calle.trim().isEmpty() || numeracion.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            // SE INGRESAN LOS DATOS A LA TABLA DISTRIBUIDORES
            st.execute("insert into distribuidores (rut,nombre_empresa,calle,numeracion,comuna,pais,telefono,ano_inicio_ventas,ciudad) "
                  + "values ('"+rut+"','"+nombre+"','"+calle+"','"+numeracion+"','"+comuna+"','"+pais+"','"+telefono+"','"+año+"','"+ciudad+"');");
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  ha sido registrado correctamente","REGISTRO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(ExcepcionPersonalizada a){// CAPTURA DE EXCEPCIÖN EN CASO QUE EL EL VALOR INGRESADO YA SE ENCUENTRA REGISTRADO
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement      
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox 
     */
    public Statement ListarDistribuidorCB(){
        try{
            sql="select * from distribuidores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");     
            }//Fin IF
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
    
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement     
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox
     */
    public Statement ListarFacturaCB(){
        try{
            sql="select * from facturas;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");    
            }//Fin IF   
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
    
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement    
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox
     */
    public Statement ListarMetodoPagoCB(){
        try{
            sql="select * from metodos_de_pago;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");      
            }   
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
    
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement   
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox
     */
    public Statement ListarLibrosCB(){
        try{
            sql="select * from libros;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");     
            }   
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
    
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement  
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox
     */
    public Statement ListarIdiomaCB(){
        try{
            sql="select * from idiomas;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");      
            }//Fin IF
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
     
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement 
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox
     */
    public Statement ListarEditorialCB(){
        try{
            sql="select * from editoriales;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");     
            }//Fin IF   
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
     
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox
     */
    public Statement ListarAutoresCB(){
        try{
            sql="select * from autores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");   
            }//Fin IF
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
     
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox 
     */
    public Statement ListarCategoriasCB(){
        try{
            sql="select * from categorias;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");  
            }//Fin IF
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
    
    /**
     * Método utilizado para definir modelos de ComboBox en las ventanas mediante Statement
     * @return Un Statement para ingresar los datos obtenidos de la consulta dento de un ComboBox 
     */
    public Statement ListarEstadoCB(){
        try{
            sql="select * from estados;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE ESTADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//SI NO HAY REGISTROS SE PROVOCA UNA EXCEPCIÓN
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");  
            }//Fin IF   
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return st;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Metodos de pago
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas
     */
    public DefaultTableModel ListarMetodoPago(){
        String[] Columnas = {"COD","NOMBRE","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[3];
        try{                
            sql="select * from metodos_de_pago;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE METODO DE PAGO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
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
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;//SE RETORNA EL MODELO DE LA TABLA 
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Libros
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarLibros(){
        String[] Columnas = {"COD","N° SERIE","ISBN","TITULO","N° PAGINAS","PRECIO REF","AÑO PUBLICACIÓN",
            "COD. EDITORIAL","COD. ESTADO"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[9];
        try{                
            sql="select * from libros;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE LIBROS
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("num_serie");
            fila[2] = rs.getString("isbn");
            fila[3] = rs.getString("titulo");
            fila[4] = rs.getString("npaginas");
            fila[5] = rs.getString("precio_ref");
            fila[6] = rs.getString("ano_publicacion");
            fila[7] = rs.getString("editorial");
            fila[8] = rs.getString("estado");
            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("num_serie");
                fila[2] = rs.getString("isbn");
                fila[3] = rs.getString("titulo");
                fila[4] = rs.getString("npaginas");
                fila[5] = rs.getString("precio_ref");
                fila[6] = rs.getString("ano_publicacion");
                fila[7] = rs.getString("editorial");
                fila[8] = rs.getString("estado");
                modelo.addRow(fila);
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;//SE RETORNA EL MODELO DE LA TABLA 
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Estado
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas
     */
    public DefaultTableModel ListarEstado(){
        String[] Columnas = {"COD","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{                
            sql="select * from estados;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE ESTADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("descripcion");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("descripcion");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Facturas
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarFactura(){
        String[] Columnas = {"COD","FOLIO","PRECIO NETO","PRECIO IVA","COSTO IVA",
                            "FECHA COMPRA","HORA COMPRA","COD DISTRIBUIDOR INV","COD METODO DE PAGO"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[9];
        try{                
            sql="select * from facturas;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE FACTURAS
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("folio");
            fila[2] = rs.getString("precio_neto");
            fila[3] = rs.getString("precio_iva");
            fila[4] = rs.getString("costo_iva");
            fila[5] = rs.getString("fecha_compra");
            fila[6] = rs.getString("hora_compra");
            fila[7] = rs.getString("dist_involucrado");
            fila[8] = rs.getString("metodo_pago");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("folio");
                fila[2] = rs.getString("precio_neto");
                fila[3] = rs.getString("precio_iva");
                fila[4] = rs.getString("costo_iva");
                fila[5] = rs.getString("fecha_compra");
                fila[6] = rs.getString("hora_compra");
                fila[7] = rs.getString("dist_involucrado");
                fila[8] = rs.getString("metodo_pago");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Compras
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarCompra(){
        String[] Columnas = {"COD","DISTRIBUIDOR INVOLUCRADO","FACTURA"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[3];
        try{                
            sql="select * from compra;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE COMPRA
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("dist_involucrado");
            fila[2] = rs.getString("factura");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                 fila[0] = rs.getString("cod");
                fila[1] = rs.getString("dist_involucrado");
                fila[2] = rs.getString("factura");
                modelo.addRow(fila);  
            }//Fin While
            
        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Idiomas
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarIdiomas(){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{                
            sql="select * from idiomas;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE IDIOMAS
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
            }//Fin While
            
        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Categorias
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarCategorias(){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{                
            sql="select * from categorias;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE CATEGORIAS
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Editoriales
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarEditoriales(){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{                
            sql="select * from editoriales;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE EDITORIALES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
             }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Distribuidores
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarDistribuidores(){
        String[] Columnas = {"COD","RUT","NOMBRE EMPRESA","CALLE","NUMERACION","COMUNA","CIUDAD","PAIS","TELÉFONO","AÑO INICIO VENTAS"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[10];
        try{                
            sql="select * from distribuidores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("rut");
            fila[2] = rs.getString("nombre_empresa");
            fila[3] = rs.getString("calle");
            fila[4] = rs.getString("numeracion");
            fila[5] = rs.getString("comuna");
            fila[6] = rs.getString("ciudad");
            fila[7] = rs.getString("pais");
            fila[8] = rs.getString("telefono");
            fila[9] = ""+rs.getInt("ano_inicio_ventas");
            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("rut");
                fila[2] = rs.getString("nombre_empresa");
                fila[3] = rs.getString("calle");
                fila[4] = rs.getString("numeracion");
                fila[5] = rs.getString("comuna");
                fila[6] = rs.getString("ciudad");
                fila[7] = rs.getString("pais");
                fila[8] = rs.getString("telefono");
                fila[9] = ""+rs.getInt("ano_inicio_ventas");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Recibe los parametros necesarios para buscar las los registros en las Tablas relacionales
     * según la relación que se ingrese como parámetros. A su vez evalúa cual es la relación ingresada
     * y realiza la búsqueda 
     * @param cod Código del registro de la tbla principal
     * @param rel Nombre de la relación a listar
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas_Relacionales 
     */
    public DefaultTableModel ListarRelaciones(String cod,String rel){

        DefaultTableModel modelo = new DefaultTableModel(0,0);//DECLARACION DEL MODELO
        
        switch(rel){//SWITCH UTILIZADO PARA LISTAR REGISTROS DE LAS RELACIONES SEGÚN CORRESPONDA
            case "LIBRO_AUTOR": String[] Columnas = {"COD","COD. LIBRO","COD. AUTOR","NOMBRE","APELLIDO PATERNO","APELLIDO MATERNO"};//VECTOR CORRESPONDIENTE A LAS COLUMNAS
                                modelo.setColumnIdentifiers(Columnas);
                                try{
                                    sql=("select * from libro_autores,autores where libro_autores.Cod_Autor = autores.Cod and cod_libro='"+cod+"';");
                                    rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
                                    
                                    if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                                        throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                                    }//Fin IF
                                    String[] fila = new String[6];

                                    //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
                                    //AL MODELO
                                    fila[0] = rs.getString(1);
                                    fila[1] = rs.getString(2);
                                    fila[2] = rs.getString(3);
                                    fila[3] = rs.getString(5);
                                    fila[4] = rs.getString(6);
                                    fila[5] = rs.getString(7);

                                    modelo.addRow(fila);

                                    while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                                        fila[0] = rs.getString(1);
                                        fila[1] = rs.getString(2);
                                        fila[2] = rs.getString(3);
                                        fila[3] = rs.getString(5);
                                        fila[4] = rs.getString(6);
                                        fila[5] = rs.getString(7);
                                        modelo.addRow(fila);
                                     }//Fin While
                                }catch(SQLException e){
                                    JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
                                }catch(ExcepcionPersonalizada a){
                                    JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);  
                                }//Fin Try-Catch
                                break;
                                
            case "LIBRO_IDIOMAS": String[] Columnasi = {"COD","COD. LIBRO","COD. IDIOMA","NOMBRE"};//VECTOR CORRESPONDIENTE A LAS COLUMNAS
                                modelo.setColumnIdentifiers(Columnasi);
                                try{
                                    sql=("select * from libro_idiomas,idiomas where libro_idiomas.Cod_idioma = idiomas.Cod and cod_libro="+cod+";");
                                    rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
                                    
                                    if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                                        throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                                    }//Fin IF
                                    String[] fila = new String[4];
                                    
                                    //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
                                    //AL MODELO
                                    fila[0] = rs.getString(1);
                                    fila[1] = rs.getString(2);
                                    fila[2] = rs.getString(3);
                                    fila[3] = rs.getString(5);

                                    modelo.addRow(fila);

                                    while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                                        fila[0] = rs.getString(1);
                                        fila[1] = rs.getString(2);
                                        fila[2] = rs.getString(3);
                                        fila[3] = rs.getString(5);
                                        modelo.addRow(fila);
                                    }//Fin While
                                }catch(SQLException e){
                                    JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
                                }catch(ExcepcionPersonalizada a){
                                    JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);  
                                }//Fin Try-Catch
                                break;
                                
            case "LIBRO_CATEGORIAS": String[] Columnasc = {"COD","COD. LIBRO","COD. CATEGORIA","NOMBRE"};//VECTOR CORRESPONDIENTE A LAS COLUMNAS
                                modelo.setColumnIdentifiers(Columnasc);
                                try{
                                    sql=("select * from libro_categoria,categorias where libro_categoria.Cod_categoria = categorias.Cod and cod_libro="+cod+";");
                                    rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
                                    
                                    if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                                        throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                                    }//Fin IF
                                    String[] fila = new String[4];
                                    
                                      //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
                                        //AL MODELO
                                        fila[0] = rs.getString(1);
                                        fila[1] = rs.getString(2);
                                        fila[2] = rs.getString(3);
                                        fila[3] = rs.getString(5);
                                        
                                        modelo.addRow(fila);
                                        
                                        while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                                            fila[0] = rs.getString(1);
                                            fila[1] = rs.getString(2);
                                            fila[2] = rs.getString(3);
                                            fila[3] = rs.getString(5);
                                            modelo.addRow(fila);
                                        }//Fin While
                                }catch(SQLException e){
                                    JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
                                }catch(ExcepcionPersonalizada a){
                                    JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);  
                                }//Fin Try-Catch
                                break;
                                
                case "LIBRO_COMPRAS": String[] Columnasl = {"COD","COD. COMPRA","COD. LIBRO","N°SERIE","ISBN","TITULO","N°PAGINAS","PRECIO REF","AÑO PUBLICACION","COD. EDITORIAL","COD ESTADO"};//VECTOR CORRESPONDIENTE A LAS COLUMNAS
                                    modelo.setColumnIdentifiers(Columnasl);
                                    try{
                                        sql=("select * from compra_libro,libros where compra_libro.Libro_asoc = libros.Cod and compra_Asoc="+cod+";");
                                        rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

                                        if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                                           throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                                        }//Fin IF
                                        String[] fila = new String[11];

                                          //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
                                            //AL MODELO
                                            fila[0] = rs.getString(1);
                                            fila[1] = rs.getString(2);
                                            fila[2] = rs.getString(3);
                                            fila[3] = rs.getString(5);
                                            fila[4] = rs.getString(6);
                                            fila[5] = rs.getString(7);
                                            fila[6] = rs.getString(8);
                                            fila[7] = rs.getString(9);
                                            fila[8] = rs.getString(10);
                                            fila[9] = rs.getString(11);
                                            fila[10] = rs.getString(12);
                                            modelo.addRow(fila);

                                            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                                                fila[0] = rs.getString(1);
                                                fila[1] = rs.getString(2);
                                                fila[2] = rs.getString(3);
                                                fila[3] = rs.getString(5);
                                                fila[4] = rs.getString(6);
                                                fila[5] = rs.getString(7);
                                                fila[6] = rs.getString(8);
                                                fila[7] = rs.getString(9);
                                                fila[8] = rs.getString(10);
                                                fila[9] = rs.getString(11);
                                                fila[10] = rs.getString(12);
                                                modelo.addRow(fila);
                                             }//Fin While

                                    }catch(SQLException e){
                                        JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
                                    }catch(ExcepcionPersonalizada a){
                                        JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);  
                                    }//Fin Try-Catch
                                    break;
        }//Fin Switch        
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de listar los registros de Autores
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas 
     */
    public DefaultTableModel ListarAutores(){
        String[] Columnas = {"COD","NOMBRE","APELLIDO PATERNO","APELLIDO MATERNO"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[4];
        try{                
            sql="select * from autores;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE AUTORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
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
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde
     * @param texto Texto a filtar en la lista 
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas   
     */
    public DefaultTableModel FiltrarLibros(String texto){
        String[] Columnas = {"COD","N° SERIE","ISBN","TITULO","N° PAGINAS","PRECIO REF","AÑO PUBLICACIÓN",
            "COD. EDITORIAL","COD. ESTADO"};//VECTOR CORRESPONDIENTE A LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[9];

        try{
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
                return ListarLibros();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from libros where cod='"+texto+"' OR num_serie='"+texto+"' OR isbn='"+texto+"' OR titulo='"+texto+"' OR npaginas='"+texto+"' OR "
                    + "precio_ref='"+texto+"' OR ano_publicacion='"+texto+"' OR editorial='"+texto+"' OR estado='"+texto+"' ;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE LIBROS
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
           
            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("num_serie");
            fila[2] = rs.getString("isbn");
            fila[3] = rs.getString("titulo");
            fila[4] = rs.getString("npaginas");
            fila[5] = rs.getString("precio_ref");
            fila[6] = rs.getString("ano_publicacion");
            fila[7] = rs.getString("editorial");
            fila[8] = rs.getString("estado");
            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("num_serie");
                fila[2] = rs.getString("isbn");
                fila[3] = rs.getString("titulo");
                fila[4] = rs.getString("npaginas");
                fila[5] = rs.getString("precio_ref");
                fila[6] = rs.getString("ano_publicacion");
                fila[7] = rs.getString("editorial");
                fila[8] = rs.getString("estado");
                modelo.addRow(fila);
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;//SE RETORNA EL MODELO DE LA TABLA 
    }//Fin método
    
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde 
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas
     */
    public DefaultTableModel FiltrarMetodoPago(String texto){
        String[] Columnas = {"COD","NOMBRE","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[3];

        try{
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
                return ListarMetodoPago();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from metodos_de_pago where cod='"+texto+"' OR nombre='"+texto+"' OR descripcion='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE METODO PAGO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA
           
            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
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
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;//SE RETORNA EL MODELO DE LA TABLA 
    }//Fin método
     
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde 
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas  
     */
    public DefaultTableModel FiltrarEstado(String texto){
        String[] Columnas = {"COD","DESCRIPCIÓN"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{   
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
                return ListarEstado();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from estados where cod ='"+texto+"' OR descripcion='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE ESTADO
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("descripcion");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("descripcion");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
     
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde 
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas
     */
    public DefaultTableModel FiltrarIdiomas(String texto){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{   
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
                return ListarIdiomas();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from idiomas where cod='"+texto+"' OR nombre='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE IDIOMAS
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas   
     */
    public DefaultTableModel FiltrarCategorias(String texto){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{    
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
                return ListarCategorias();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from categorias where cod='"+texto+"' OR nombre='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE CATEGORIAS
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);

        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde* 
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas
     */
    public DefaultTableModel FiltrarCompras(String texto){
        String[] Columnas = {"COD","DISTRIBUIDOR INVOLUCRADO","FACTURA"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[3];
        try{    
             if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarCompra();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from compra where cod='"+texto+"' OR dist_involucrado='"+texto+"' OR factura='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE COMPRA
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("dist_involucrado");
            fila[2] = rs.getString("factura");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                 fila[0] = rs.getString("cod");
                fila[1] = rs.getString("dist_involucrado");
                fila[2] = rs.getString("factura");
                modelo.addRow(fila);  
             }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas    
     */
    public DefaultTableModel FiltrarEditoriales(String texto){
        String[] Columnas = {"COD","NOMBRE"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[2];
        try{ 
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarEditoriales();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from editoriales where cod='"+texto+"' OR nombre='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE EDITORIALES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("nombre");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);  
             }//Fin While
            
        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
     
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas
     */
    public DefaultTableModel FiltrarDistribuidores(String texto){
        String[] Columnas = {"COD","RUT","NOMBRE EMPRESA","CALLE","NUMERACION","COMUNA","PAIS","TELÉFONO","AÑO INICIO VENTAS"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[9];
        try{    
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarDistribuidores();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from distribuidores where cod='"+texto+"' OR rut='"+texto+"' OR nombre_empresa='"+texto+"' OR calle='"+texto+"' OR numeracion='"+texto+"' OR comuna='"+texto+"' "
                    + "OR pais='"+texto+"' OR telefono='"+texto+"' OR ano_inicio_ventas='"+texto+"' ;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE DISTRIBUIDORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
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
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde 
     * @param texto Texto a filtar en la lista
     * @return Un DefaultTableModel para ser aplicado al JList del JFrame Listas  
     */
    public DefaultTableModel FiltrarAutores(String texto){
        String[] Columnas = {"COD","NOMBRE","APELLIDO PATERNO","APELLIDO MATERNO"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[4];
        try{ 
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
               return ListarAutores();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from autores where cod='"+texto+"' OR nombre='"+texto+"' OR apellido_paterno='"+texto+"' OR apellido_materno='"+texto+"';";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE AUTORES
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

            if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
            }//Fin IF
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
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Realiza la búsquedad de coincidencias en la tabla que corresponde
     * @param texto Texto a filtar en la lista
     * @return  Un DefaultTableModel para ser aplicado al JList del JFrame Listas  
     */
    public DefaultTableModel FiltrarFactura(String texto){
        String[] Columnas = {"COD","FOLIO","PRECIO NETO","PRECIO IVA","COSTO IVA",
                            "FECHA COMPRA","HORA COMPRA","COD DISTRIBUIDOR INV","COD METODO DE PAGO"};//VECTOR CORRESPONDIENTE AL LAS COLUMNAS
        DefaultTableModel modelo = new DefaultTableModel(Columnas,0);//DECLARACION DEL MODELO
        String[] fila = new String[9];
        try{    
            if(texto.equals(" ") || texto.isEmpty()){
                JOptionPane.showMessageDialog(null, "EL CAMPO DE BUSQUEDA ESTA VACÍO... RECUPERANDO REGISTROS","RECUPERACIÓN", JOptionPane.INFORMATION_MESSAGE);
                return ListarFactura();// SI EL CAMPO DE BUSQUEDA SE ENCUENTRA VACIÓ SE RECUPERAN LOS REGISTROS
            }//Fin IF
            sql="select * from facturas where cod='"+texto+"' OR folio='"+texto+"' OR precio_neto='"+texto+"' OR precio_iva='"+texto+"'"
                    + "OR costo_iva='"+texto+"' OR fecha_compra='"+texto+"' OR hora_compra='"+texto+"' OR dist_involucrado='"+texto+"' "
                    + "OR metodo_pago='"+texto+"' ;";//SE REALIZA BUSQUEDA DE LOS REGISTROS DE FACTURA
            rs = st.executeQuery(sql);//SE EJECUTA LA CONSULTA

                if(!(rs.next())){//COMPROBACIÖN DE LOS DATOS 
                    throw new ExcepcionPersonalizada("NO HAY REGISTROS PARA LISTAR");
                }//Fin IF
            //EN PRIMERA INSTANCIA, SI NO SE PRODUCE UNA EXCEPCIÓN SE AÑADE UNA FILA
            //AL MODELO
            fila[0] = rs.getString("cod");
            fila[1] = rs.getString("folio");
            fila[2] = rs.getString("precio_neto");
            fila[3] = rs.getString("precio_iva");
            fila[4] = rs.getString("costo_iva");
            fila[5] = rs.getString("fecha_compra");
            fila[6] = rs.getString("hora_compra");
            fila[7] = rs.getString("dist_involucrado");
            fila[8] = rs.getString("metodo_pago");

            modelo.addRow(fila);

            while(rs.next()){//SE INGRESAN LAS FILAS AL MODELO MEDIANTE UN CICLO 
                fila[0] = rs.getString("cod");
                fila[1] = rs.getString("folio");
                fila[2] = rs.getString("precio_neto");
                fila[3] = rs.getString("precio_iva");
                fila[4] = rs.getString("costo_iva");
                fila[5] = rs.getString("fecha_compra");
                fila[6] = rs.getString("hora_compra");
                fila[7] = rs.getString("dist_involucrado");
                fila[8] = rs.getString("metodo_pago");
                modelo.addRow(fila);  
            }//Fin While

        }catch(SQLException e){//CAPTURA DE EXCEPCION DE MySQL
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(ExcepcionPersonalizada a){//CAPTURA PRODUCIDA SI NO EXISTEN DATOS EN LA TABLA
            JOptionPane.showMessageDialog(null, a.getMessage(),"ERROR AL LISTAR REGISTROS", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
        return modelo;
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda
     * @param cod El código del registro a eliminar 
     */
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
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
           JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda 
     * @param cod El código del registro a eliminar   
     */
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
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda 
     * @param cod El código del registro a eliminar    
     */
    public void EliminarLibros(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "update libros set estado = 2 where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA

            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda  
     * @param cod El código del registro a eliminar    
     */
    public void EliminarCompras(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            
            sql= "delete from compra_libro where compra_asoc='"+cod+"';";
            st.execute(sql);
            
            sql= "delete from compra where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA DEL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA
            
            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda 
     * @param cod El código del registro a eliminar    
     */
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
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda 
     * @param cod El código del registro a eliminar
     */
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
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda  
     * @param cod El código del registro a eliminar
     */
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
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        } catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda 
     * @param cod El código del registro a eliminar 
     */
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
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda 
     * @param cod El código del registro a eliminar
     */
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
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar el registro que contiene la tabla que corresponda* 
     * @param cod El código del registro a eliminar  
     */
    public void EliminarFacturas(String cod ){
        try{
            if(cod.equals(-1)){
                throw new ExcepcionPersonalizada("NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE");
            }
            sql= "delete from facturas where cod='"+cod+"';";//SE CREA LA SENTENCIA DONDE SE ELIMINA EL REGISTRO INDICADO
            st.execute(sql);//SE EJECUTA LA CONSULTA

            JOptionPane.showMessageDialog(null,"EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO

        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,"NO SE HA SELECCIONADO NINGÚN REGISTRO PARA ELIMINAR, INTENTE NUEVAMENTE","ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);  
        }catch(SQLIntegrityConstraintViolationException a){
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO NO SE PUEDE ELIMINAR YA QUE ESTA RELACIONADA CON ALGÚN OTRO REGISTRO","ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde
     * @param cod Código del registro a modificar
     * @param nombre_autor Dato nuevo
     * @param apellidop Dato nuevo
     * @param apellidom Dato nuevo     
     */
    public void ModificarAutor(String cod,String nombre_autor, String apellidop,String apellidom){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE AUTOR
        try{
            
             if(cod.trim().isEmpty() || nombre_autor.trim().isEmpty() || apellidop.trim().isEmpty() || apellidom.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            st.execute("update autores set nombre='"+nombre_autor+"',apellido_paterno='"+apellidop+"',apellido_materno='"+apellidom+"' "
                        + "WHERE cod="+cod+";");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+nombre_autor+" "+apellidop+" "+apellidom+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACION EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde 
     * @param cod Código del registro a modificar
     * @param nombre Dato nuevo
     */
    public void ModificarCategoría(String cod,String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE CATEGORIA
        try{
            
            if(cod.trim().isEmpty() || nombre.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
            st.execute("update categorias set nombre='"+nombre+"' WHERE cod="+cod+";");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde 
     * @param cod Código del registro a modificar
     * @param nombre Dato nuevo
     */
    public void ModificarIdioma(String cod,String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE IDIOMA
        try{
            
            if(cod.trim().isEmpty() || nombre.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
            st.execute("update idiomas set nombre='"+nombre+"' WHERE cod="+cod+";");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde 
     * @param cod Código del registro a modificar
     * @param nombre Dato nuevo
     */
    public void ModificarEditorial(String cod,String nombre){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE EDITORIAL
        try{
            
            if(cod.trim().isEmpty() || nombre.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            st.execute("update editoriales set nombre='"+nombre+"' WHERE cod="+cod+";");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde
     * @param cod Código del registro a modificar
     * @param rut Dato nuevo
     * @param nombre Dato nuevo
     * @param pais Dato nuevo
     * @param ciudad Dato nuevo
     * @param comuna Dato nuevo
     * @param calle Dato nuevo
     * @param numeracion Dato nuevo
     * @param telefono Dato nuevo
     * @param año Dato nuevo  
     */
    public void ModificarDistribuidor(String cod,String rut,String nombre,String pais,String ciudad,String comuna,String calle,String numeracion,
                                    long telefono, short año){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE DISTRIBUIDOR
        try{
            
            if(cod.trim().isEmpty() || rut.trim().isEmpty() || nombre.trim().isEmpty() || pais.trim().isEmpty() || ciudad.trim().isEmpty() || comuna.trim().isEmpty() 
                    || calle.trim().isEmpty() || numeracion.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
             st.execute("update distribuidores set rut='"+rut+"', nombre_empresa='"+nombre+"', calle='"+calle+"', numeracion='"+numeracion+"', comuna='"+comuna+"'"
                  + ",ciudad='"+ciudad+"', pais='"+pais+"', telefono='"+telefono+"', ano_inicio_ventas='"+año+"' WHERE cod="+cod+";");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
             JOptionPane.showMessageDialog(null, "'"+nombre+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde
     * @param cod Código del registro a modificar
     * @param desc Dato nuevo
     */
    public void ModificarEstado(String cod,String desc){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE ESTADO
        try{
            
             if(cod.trim().isEmpty() || desc.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            st.execute("update estados set descripcion='"+desc+"' WHERE cod="+cod+";");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+desc+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde
     * @param cod Código del registro a modificar
     * @param nombre Dato nuevo
     * @param desc Dato nuevo 
     */
    public void ModificarMetododePago(String cod,String nombre, String desc){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE METODOS DE PAGO
        try{
            
            if(cod.trim().isEmpty() || nombre.trim().isEmpty() || desc.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
            st.execute("update metodos_de_pago set nombre='"+nombre+"' ,descripcion='"+desc+"' WHERE cod="+cod+";");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+nombre+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro que corresponde al código ingresado según la tabla que corresponde 
     * @param cod Código del registro a modificar
     * @param folio Dato nuevo
     * @param precio_neto Dato nuevo
     * @param precio_IVA Dato nuevo
     * @param costo_IVA Dato nuevo
     * @param fecha_compra Dato nuevo
     * @param hora_compra Dato nuevo
     * @param cod_dist Dato nuevo
     * @param cod_met Dato nuevo  
     */
    public void ModificarFactura(String cod,String folio,int precio_neto, int precio_IVA, int costo_IVA, String fecha_compra, String hora_compra, int cod_dist, int cod_met){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE FACTURA
        try{
            
            if(cod.trim().isEmpty() || folio.trim().isEmpty() || fecha_compra.trim().isEmpty() || hora_compra.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
            st.execute("update facturas set folio='"+folio+"', precio_neto='"+precio_neto+"', precio_iva='"+precio_IVA+"', costo_iva='"+costo_IVA+"', fecha_compra='"+fecha_compra+"'"
                        + ",hora_compra='"+hora_compra+"', dist_involucrado='"+cod_dist+"', metodo_pago='"+cod_met+"' WHERE cod='"+cod+"';");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+folio+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro de compra
     * @param cod Código del registro a modificar
     * @param cod_dist Dato nuevo
     * @param cod_factura Dato nuevo 
     */
    public void ModificarCompra(String cod,String cod_dist,String cod_factura){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE COMPRA
         try{
             
             if(cod.trim().isEmpty() || cod_dist.trim().isEmpty() || cod_factura.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            st.execute("update compra set dist_involucrado='"+cod_dist+"', factura='"+cod_factura+"' WHERE cod='"+cod+"';");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+cod+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
         }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
         }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro libro
     * @param cod Código del registro a modificar
     * @param nserie Dato nuevo
     * @param isbn Dato nuevo
     * @param titulo Dato nuevo
     * @param npaginas Dato nuevo
     * @param precioref Dato nuevo
     * @param ano_publicacion Dato nuevo
     * @param editorial Dato nuevo
     * @param estado Dato nuevo 
     */
    public void ModificarLibro(String cod,String nserie, String isbn, String titulo, int npaginas, int precioref
                            ,short ano_publicacion, int editorial,int estado) {//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTRO DE LIBRO
        try{
            
            if(cod.trim().isEmpty() || nserie.trim().isEmpty() || isbn.trim().isEmpty() || titulo.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
            st.execute("update libros set num_serie='"+nserie+"', isbn='"+isbn+"', titulo='"+titulo+"', npaginas='"+npaginas+"', precio_ref='"+precioref+"',"
                     + "ano_publicacion='"+ano_publicacion+"', editorial='"+editorial+"', estado='"+estado+"' WHERE cod='"+cod+"';");// SE MODIFICA EL CAMPO EN BASE DE DATOS
        
            JOptionPane.showMessageDialog(null, "'"+titulo+"'  HA SIDO MODIFICADO CORRECTAMENTE",
                "MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
        
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin Try-Catch
    }//Fin método
    
    /**
     * Método encargado de modificar el registro de relaciones según corresponda
     * @param cod Código del registro a modificar
     * @param nuevo_cod_rel Código del registro de la nueva asociación
     * @param relacion Relación a modificar
     */
    public void ModificarRelacion(String cod,String nuevo_cod_rel,String relacion){//MÉTODO QUE INCLUYE CONSULTA PARA MODIFICAR REGISTROS RELACIONALES
        try{
            
            if(cod.trim().isEmpty() || nuevo_cod_rel.trim().isEmpty() || relacion.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
            switch(relacion){//Dependiendo de la relación se realizan las consultas que corresponden para modificar registros
                case  "LIBRO_AUTOR": sql =("update libro_autores set cod_autor='"+nuevo_cod_rel+"' WHERE cod ='"+cod+"';");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO MODIFICADO CORRECTAMENTE","MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;
                    
                case "LIBRO_IDIOMAS":sql =("update libro_idiomas set cod_idioma='"+nuevo_cod_rel+"' WHERE cod ='"+cod+"';");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO MODIFICADO CORRECTAMENTE","MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;

                case "LIBRO_CATEGORIAS":sql =("update libro_categoria set cod_categoria='"+nuevo_cod_rel+"' WHERE cod ='"+cod+"';");
                                        st.execute(sql);
                                        JOptionPane.showMessageDialog(null,"HA SIDO MODIFICADO CORRECTAMENTE","MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                        break;

                case "LIBRO_COMPRAS":sql =("update compra_libro set libro_asoc='"+nuevo_cod_rel+"' WHERE cod ='"+cod+"';");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO MODIFICADO CORRECTAMENTE","MODIFICACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;
            }//Fin Switch
         }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin  Try-Catch
    }//Fin método
    
    /**
     * Método encargado de agregar registros a tablas relacionales según corresponda
     * @param cod Código del registro de la tabla principal al cual se asocia la relación
     * @param cod_rel Código del registro de la relación a agregar
     * @param relacion Relación al cual se agregan registros
     */
    public void AgregarRelacion(String cod,String cod_rel,String relacion){//MÉTODO QUE INCLUYE CONSULTA PARA AGREGAR REGISTROS RELACIONALES
        try{
             if(cod.trim().isEmpty() || cod_rel.trim().isEmpty() || relacion.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
             
            switch(relacion){//Dependiendo de la relación se realizan las consultas que corresponden para Agregar registros
                case  "LIBRO_AUTOR": sql =("insert into libro_autores (cod_libro,cod_autor) values ("+cod+","+cod_rel+");");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO INGRESADO CORRECTAMENTE","INGRESO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;
                    
                case "LIBRO_IDIOMAS":sql =("insert into libro_idiomas (cod_libro,cod_idioma) values ("+cod+","+cod_rel+");");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO INGRESADO CORRECTAMENTE","INGRESO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;

                case "LIBRO_CATEGORIAS":sql =("insert into libro_categoria (cod_libro,cod_categoria) values ("+cod+","+cod_rel+");");
                                        st.execute(sql);
                                        JOptionPane.showMessageDialog(null,"HA SIDO INGRESADO CORRECTAMENTE","INGRESO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                        break;

                case "LIBRO_COMPRAS":sql =("insert into compra_libro (compra_asoc,libro_asoc) values ("+cod+","+cod_rel+");");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO INGRESADO CORRECTAMENTE","INGRESO EXITOSO", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;
            }//Fin Switch
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin  Try-Catch
    }//Fin método
    
    /**
     * Método encargado de eliminar registros de tablas relacionales según corresponda
     * @param cod Código del registro de la relación a eliminar
     * @param relacion Relación al cual se le eliminan registros
     */
    public void EliminarRelacion(String cod,String relacion){//MÉTODO QUE INCLUYE CONSULTA PARA ELIMINAR REGISTROS RELACIONALES
        try{
            
            if(cod.trim().isEmpty() || relacion.trim().isEmpty()){//SI HAY CAMPOS VACIOS SE FUERZA UNA EXCEPCIÓN PARA CONTROLAR EL ERROR
                 throw new ExcepcionPersonalizada("HAY CAMPOS VACIOS");
            }//Fin IF
            
            switch(relacion){//Dependiendo de la relación se realizan las consultas que corresponden para eliminar registros
                case  "LIBRO_AUTOR": sql =("delete from libro_autores WHERE cod='"+cod+"';");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;

                case "LIBRO_IDIOMAS":sql =("delete from libro_idiomas WHERE cod='"+cod+"';");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;

                case "LIBRO_CATEGORIAS":sql =("delete from libro_categoria WHERE cod='"+cod+"';");
                                        st.execute(sql);
                                        JOptionPane.showMessageDialog(null,"HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                        break;

                case "LIBRO_COMPRAS":sql =("delete from compra_libro WHERE cod='"+cod+"';");
                                     st.execute(sql);
                                     JOptionPane.showMessageDialog(null,"HA SIDO ELIMINADO CORRECTAMENTE","ELIMINACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE); //SE INFORMA AL USUARIO
                                     break;
            }//Fin Switch
        }catch(ExcepcionPersonalizada a){
            JOptionPane.showMessageDialog(null,  a.getMessage() ,"ERROR DE REGISTRO", JOptionPane.ERROR_MESSAGE);
         }catch(SQLException e){// CAPTURA DE EXCEPCION DE CONEXIÓN A LA BASE DE DATOS
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE); 
        }//Fin  Try-Catch
    }//Fin método
    
    /**
     * Método encargado de validar que el dato ingresado sea un válido como tipo Long Ej:Numero Telefónico
     * @param dato Dato a validar
     * @param casilla nombre del JLabel asociado JTextField el cual contiene el dato
     * @return El mísmo dato ingresado como Long, siempre y cuando sea válido 
     */
    public Long ValidarLong(String dato, String casilla){ //MËTODO UTILIZADO PARA VALIDAR DATOS DEL TIPO LONG PARA NUMEROS TELEFÓNICOS
        Long val=null;
        try{
            val = Long.parseLong(dato);
        }catch(NumberFormatException e){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, "EL VALOR INGRESADO EN "+casilla+" NO CORRESPONDE A UN VALOR NUMERICO. INTENTE NUEVAMENTE");
        }//Fin Try-Catch
        return val;
    }//Fin método
    
    /**
     * Método que valida datos numéricos
     * @param dato Dato a validar
     * @return El mismo dato ingresado como tipo de dato Integer, siempre y cuando sea válido como Integer 
     */
    public int ValidarInteger(String dato){ //MËTODO UTILIZADO PARA VALIDAR DATOS DEL TIPO LONG PARA NUMEROS TELEFÓNICOS
        int val = 0;
        try{
            val = Integer.parseInt(dato);
        }catch(NumberFormatException e){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, "UN VALOR INGRESADO NO CORRESPONDE A UN VALOR NUMERICO. INTENTE NUEVAMENTE");
        }//Fin Try-Catch
        return val;
    }//Fin método
    
    /**
     * Método encargado de validar que el dato ingresado sea válido como un Año comprobando la fecha actual del sistema
     * con el dato ingresado 
     * @param dato Dato a validar
     * @return El mismo dato ingresado como Integer, siempre y cuando sea válido como Integer 
     */
    public short ValidarAño(String dato){//MÉTODO UTILIZADO PARA VALIDAR LOS DATOS DEL TIPO SHORT PARA AÑOS
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        short val = 0;
        try{
            val = Short.parseShort(dato);
            if((int)val>year){
                throw new ExcepcionPersonalizada("EL AÑO INGRESADO NO CORRESPONDE A UN AÑO VÄLIDO. INTENTE NUEVAMENTE");
            }//Fin IF       
        }catch(NumberFormatException e){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, "EL AÑO INGRESADO NO CORRESPONDE A UN AÑO VÁLIDO. INTENTE NUEVAMENTE");
        }catch(ExcepcionPersonalizada a){//EN LA CAPTURA DE LA EXCEPCION DE INGRESO DE DATO SE ESPECIFICA El ERROR
            JOptionPane.showMessageDialog(null, a.getMessage());
        }//Fin Try-Catch
        return val;
    }//Fin método
    
    /**
     * Método encargado de validar que los datos ingresados como fecha sean válidos, comparando 
     * mes, y dia por separado del año 
     * @param dia Día ingresado en JTextField
     * @param mes Mes ingresado en JTextField
     * @return Un valor String que contiene el formato de Fecha para ser ingresado correctamente 
     */
    public String ValidarFecha(String dia,String mes){
        String fecha=null;
        try{
            if(Integer.parseInt(dia)>31){
                throw new ExcepcionPersonalizada("DIA INGRESADO NO ES VÁLIDO");
            }//Fin IF
            if(Integer.parseInt(mes)>12){
                throw new ExcepcionPersonalizada("MES INGRESADO NO ES VÁLIDO");
            }//Fin IF
        fecha = (mes+"-"+dia); //Post validacion son declarados en una variable String
        }catch(ExcepcionPersonalizada e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch(Exception a){
            JOptionPane.showMessageDialog(null, a.getMessage());
        }//Fin Try-Catch
        return fecha;
    }//Fin método
    
    /**
     * Método encargado de validar que los datos ingresados sean correctos. Validando 
     * Hora y minutos por separados
     * @param hora Hora ingresada en JTextField
     * @param minutos Minutos ingresados en JTextField
     * @return Un valor String que contiene un formato de Hora válido para ser ingresado
     */
    public String ValidarHora(String hora,String minutos){
        String time = null;
        try{
            if(Integer.parseInt(hora)>23){//Validar con el valor máximo posible para hora
                throw new ExcepcionPersonalizada("LA HORA INGRESADA NO ES VÁLIDA");
            }//Fin IF
            if(Integer.parseInt(minutos)>59){//Validar con el valor máximo posible para minutos
                throw new ExcepcionPersonalizada("LOS MINUTOS INGRESADOS NO SON VÁLIDOS");
            }//Fin IF
        time=(hora+":"+minutos);  //Post validacion se declaran dentro de una variabel String
        }catch(ExcepcionPersonalizada e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch(Exception a){
            JOptionPane.showMessageDialog(null, a.getMessage());
        }//Fin Try-Catch
        return time;
    }//Fin método
}//Fin class
