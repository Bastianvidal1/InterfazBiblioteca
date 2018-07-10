/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Querys.Querys;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Bastian Vidal
 */
public class Listas extends javax.swing.JFrame {

    /**
     * Creates new form Listas
     */
    String tabla;
    String[] columnas;
    Querys q = new Querys();
    
    /**
     * Constructor por defecto
     */
    public Listas() {
        initComponents();
        
    }
    
    /**
     * Contructor con parametros
     * @param tabla Que define cuales van a ser los componentes y el contenido que tendra el JTable
     */
    public Listas(String tabla) {
        initComponents();
        this.tabla = tabla;
        setTitle(tabla);
        setModelo(tabla);
        setBotones(tabla);
    }  
        
        /**
         *METODO ENCARGADO DE DEFINIR EL MODELO DE LA TABLA PARA LA VISUALIZACIÓN
         * @param tabla 
         */
        public void setModelo(String tabla){
            
            switch(tabla){//SWITCH PARA LA DEFINICION DEL MODELO DE TABLA
                    case "Métodos de pago": lista.setModel(q.ListarMetodoPago());
                                            break;
                        
                    case "Compras": lista.setModel(q.ListarCompra());
                                    break;
                        
                    case "Facturas":lista.setModel(q.ListarFactura());
                                    break;
                        
                    case "Distribuidores": lista.setModel(q.ListarDistribuidores());
                                           break;
                        
                    case "Idiomas": lista.setModel(q.ListarIdiomas());
                                    break;
                        
                    case "Categorias":lista.setModel(q.ListarCategorias());
                                     break;
                        
                    case "Editorial":lista.setModel(q.ListarEditoriales());
                                     break;
                        
                    case "Autores":lista.setModel(q.ListarAutores());
                                   break;
                        
                    case "Libros":lista.setModel(q.ListarLibros());
                                   break;
                        
                    case "Estado": lista.setModel(q.ListarEstado());
                                   break;
                     
            }//Fin Switch
        }//Fin método
        
        
        /**
         *ESTE METODO SE ENCARGA DE HACER VISIBLES O NO LOS BOTONES PARA LISTAR REFERENCIAS
         *SEGÚN CORRESPONDA
         * @param tabla 
        **/
        public void setBotones(String tabla){
            
            switch(tabla){
                case "Libros":  btn_ver_libros_comprados.setVisible(false);
                                break;
                    
                case "Compras":  btn_ver_autores.setVisible(false);
                                 btn_ver_categorias.setVisible(false);
                                 btn_ver_idiomas.setVisible(false);
                                 break;
                    
                default:btn_ver_libros_comprados.setVisible(false);
                       btn_ver_autores.setVisible(false);
                         btn_ver_categorias.setVisible(false);
                         btn_ver_idiomas.setVisible(false);
            }//Fin Switch              
        }//Fin método
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JTable();
        txt_busqueda = new javax.swing.JTextField();
        texto_filtro = new javax.swing.JLabel();
        btn_filtrar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_ver_autores = new javax.swing.JButton();
        btn_ver_categorias = new javax.swing.JButton();
        btn_ver_idiomas = new javax.swing.JButton();
        btn_ver_libros_comprados = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        lista.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane1.setViewportView(lista);

        txt_busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_busquedaActionPerformed(evt);
            }
        });

        texto_filtro.setText("Ingresar [texto] a flitrar:");

        btn_filtrar.setText("Filtrar");
        btn_filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filtrarActionPerformed(evt);
            }
        });

        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_modificar.setText("Modificar");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        btn_ver_autores.setText("Autores");
        btn_ver_autores.setMaximumSize(new java.awt.Dimension(80, 30));
        btn_ver_autores.setPreferredSize(new java.awt.Dimension(80, 30));
        btn_ver_autores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_autoresActionPerformed(evt);
            }
        });

        btn_ver_categorias.setText("Categorias");
        btn_ver_categorias.setMaximumSize(new java.awt.Dimension(80, 30));
        btn_ver_categorias.setPreferredSize(new java.awt.Dimension(90, 30));
        btn_ver_categorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_categoriasActionPerformed(evt);
            }
        });

        btn_ver_idiomas.setText("Idiomas");
        btn_ver_idiomas.setMaximumSize(new java.awt.Dimension(80, 30));
        btn_ver_idiomas.setPreferredSize(new java.awt.Dimension(80, 30));
        btn_ver_idiomas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_idiomasActionPerformed(evt);
            }
        });

        btn_ver_libros_comprados.setText("Libros comprados");
        btn_ver_libros_comprados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_libros_compradosActionPerformed(evt);
            }
        });

        btn_actualizar.setText("Actualizar / Recuperar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(texto_filtro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_filtrar)
                        .addGap(29, 29, 29)
                        .addComponent(btn_actualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                        .addComponent(btn_modificar)
                        .addGap(28, 28, 28)
                        .addComponent(btn_eliminar)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btn_ver_autores, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_ver_categorias, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_ver_idiomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_ver_libros_comprados, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ver_autores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ver_categorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ver_idiomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ver_libros_comprados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(texto_filtro)
                    .addComponent(btn_filtrar)
                    .addComponent(btn_eliminar)
                    .addComponent(btn_modificar)
                    .addComponent(btn_actualizar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_busquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_busquedaActionPerformed

    private void btn_filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filtrarActionPerformed
        // BOTON ENCARGADO DE LISTAR SOLO LOS REGISTROS QUE COINCIDEN CON EL FILTRO
        switch(tabla){//SWITCH PARA LA DEFINICION DEL MODELO DE TABLA
                    case "Métodos de pago": lista.setModel(q.FiltrarMetodoPago(txt_busqueda.getText()));
                                            break;
                        
                    case "Compras": lista.setModel(q.FiltrarCompras(txt_busqueda.getText()));
                                    break;
                        
                    case "Facturas":lista.setModel(q.FiltrarFactura(txt_busqueda.getText()));
                                    break;
                        
                    case "Distribuidores":lista.setModel(q.FiltrarDistribuidores(txt_busqueda.getText()));
                                            break;
                        
                    case "Idiomas": lista.setModel(q.FiltrarIdiomas(txt_busqueda.getText()));
                                            break;
                        
                    case "Categorias":lista.setModel(q.FiltrarCategorias(txt_busqueda.getText()));
                                            break;
                        
                    case "Editorial":lista.setModel(q.FiltrarEditoriales(txt_busqueda.getText()));
                                            break;
                        
                    case "Autores":lista.setModel(q.FiltrarAutores(txt_busqueda.getText()));
                                            break;
                        
                    case "Libros":lista.setModel(q.FiltrarLibros(txt_busqueda.getText()));
                                  break;
                        
                    case "Estado": lista.setModel(q.FiltrarEstado(txt_busqueda.getText()));
                                            break;
                     
            }//Fin Switch
    }//GEN-LAST:event_btn_filtrarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        //BOTON ENCARGADO DE ELIMINAR EL REGISTRO DE SELECCIONADO EN LA TABLA SEGÚN CORRESPONDA
        try{
            switch(tabla){// SWITCH PARA LA ELIMINACION DE DATOS 
                    case "Métodos de pago": q.EliminarMetodoPago(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                            lista.setModel(q.ListarMetodoPago());
                                            break;
                        
                    case "Compras":q.EliminarCompras(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                   lista.setModel(q.ListarCompra());
                                   break;   
                        
                    case "Facturas":q.EliminarFacturas(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                    lista.setModel(q.ListarFactura());
                                    break; 
                        
                    case "Distribuidores":q.EliminarDistribuidores(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                          lista.setModel(q.ListarDistribuidores());
                                          break;
                        
                    case "Idiomas":q.EliminarIdiomas(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                   lista.setModel(q.ListarIdiomas());
                                   break;
                        
                    case "Categorias":q.EliminarCategorias(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                      lista.setModel(q.ListarCategorias());
                                      break;
                        
                    case "Editorial":q.EliminarEditoriales(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                     lista.setModel(q.ListarEditoriales());
                                     break;
                        
                    case "Autores":q.EliminarAutores(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                   lista.setModel(q.ListarAutores());
                                   break;
                        
                    case "Libros":q.EliminarLibros(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                  lista.setModel(q.ListarLibros());
                                  break;
                        
                    case "Estado":q.EliminarEstado(lista.getValueAt(lista.getSelectedRow(), 0).toString());
                                  lista.setModel(q.ListarEstado());
                                  break;
            }//Fin Switch
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane,"NO SE HA SELECCIONADO UNA FILA INTENTE NUEVAMENTE");
        }//Fin Try-Catch
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_ver_autoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_autoresActionPerformed
        // BOTON QUE MUESTRA UNA TABLA CON LAS RELACIONES ASOCIADAS AL REGISTRO SELECCIONADO
        try{
            Listas_Relacionales lista_relacional = new Listas_Relacionales(lista.getValueAt(lista.getSelectedRow(), 0).toString(),"LIBRO_AUTOR");
            lista_relacional.setVisible(true);
        }catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(rootPane,"NO SE HA SELECCIONADO UNA FILA INTENTE NUEVAMENTE");
        }//Fin Try-Catch
    }//GEN-LAST:event_btn_ver_autoresActionPerformed

    private void btn_ver_idiomasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_idiomasActionPerformed
        //  BOTON QUE MUESTRA UNA TABLA CON LAS RELACIONES ASOCIADAS AL REGISTRO SELECCIONADO
        try{
            Listas_Relacionales lista_relacional = new Listas_Relacionales(lista.getValueAt(lista.getSelectedRow(), 0).toString(),"LIBRO_IDIOMAS");
            lista_relacional.setVisible(true);
        }catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(rootPane,"NO SE HA SELECCIONADO UNA FILA INTENTE NUEVAMENTE");
        }//Fin Try-Catch
    }//GEN-LAST:event_btn_ver_idiomasActionPerformed

    private void btn_ver_categoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_categoriasActionPerformed
        // BOTON QUE MUESTRA UNA TABLA CON LAS RELACIONES ASOCIADAS AL REGISTRO SELECCIONADO
        try{
            Listas_Relacionales lista_relacional = new Listas_Relacionales(lista.getValueAt(lista.getSelectedRow(), 0).toString(),"LIBRO_CATEGORIAS");
            lista_relacional.setVisible(true);
        }catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(rootPane,"NO SE HA SELECCIONADO UNA FILA INTENTE NUEVAMENTE");
        }//Fin Try-Catch
    }//GEN-LAST:event_btn_ver_categoriasActionPerformed

    private void btn_ver_libros_compradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_libros_compradosActionPerformed
        // BOTON QUE MUESTRA UNA TABLA CON LAS RELACIONES ASOCIADAS AL REGISTRO SELECCIONADO
         try{
            Listas_Relacionales lista_relacional = new Listas_Relacionales(lista.getValueAt(lista.getSelectedRow(), 0).toString(),"LIBRO_COMPRAS");
            lista_relacional.setVisible(true);
        }catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(rootPane,"NO SE HA SELECCIONADO UNA FILA INTENTE NUEVAMENTE");
        }//Fin Try-Catch
    }//GEN-LAST:event_btn_ver_libros_compradosActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        /* ABRE UNA VENTANA CON LOS DATOS DE LA FILA SELECCIONADA PRECARGADOS PARA MODIFICARLOS E INGRESAR
        +  SON DECLARADOS TODAS LAS VARIABLES QUE SE RECIBEN DESDE LAS DISTINTAS TABLAS PARA TENER ORDEN AL LLAMAR LOS JFrames
        */
        String cod,nombre,rut,calle,numeracion,comuna,ciudad,pais,ntelefono,ainicio_ventas,desc,folio,precio_neto,precio_IVA,costo_IVA
        ,fecha_compra,hora_compra,cod_dist,cod_met,nserie,isbn,titulo,npaginas,precioref,apublicacion,cod_editorial,cod_estado,cod_factura;
        
        try{
        switch(tabla){// SWITCH PARA LA MODIFICACION DE DATOS 
                    case "Métodos de pago":cod = lista.getValueAt(lista.getSelectedRow(), 0).toString();
                                           nombre = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                           desc = lista.getValueAt(lista.getSelectedRow(), 2).toString();
                                           Registrar_MetododePago reg_metodopago = new Registrar_MetododePago(cod, nombre, desc);
                                           reg_metodopago.setVisible(true);
                                           break;
                    case "Compras": cod = lista.getValueAt(lista.getSelectedRow(), 0).toString();
                                    cod_dist = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                    cod_factura = lista.getValueAt(lista.getSelectedRow(), 2).toString();
                                    Registrar_Compra reg_compra = new Registrar_Compra(cod, cod_dist, cod_factura);
                                    reg_compra.setVisible(true);
                                    break;
                        
                    case "Facturas":cod = lista.getValueAt(lista.getSelectedRow(), 0).toString();
                                   folio = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                   precio_neto = lista.getValueAt(lista.getSelectedRow(), 2).toString();
                                   precio_IVA = lista.getValueAt(lista.getSelectedRow(), 3).toString();
                                   costo_IVA = lista.getValueAt(lista.getSelectedRow(), 4).toString();
                                   fecha_compra = lista.getValueAt(lista.getSelectedRow(), 5).toString();
                                   hora_compra = lista.getValueAt(lista.getSelectedRow(), 6).toString();
                                   cod_dist = lista.getValueAt(lista.getSelectedRow(), 7).toString();
                                   cod_met = lista.getValueAt(lista.getSelectedRow(), 8).toString();
                                   Registrar_Factura reg_factura = new Registrar_Factura(cod, folio, precio_neto, precio_IVA, costo_IVA, fecha_compra, hora_compra, cod_dist, cod_met);
                                   reg_factura.setVisible(true);
                                   break;   
                        
                    case "Distribuidores": cod = lista.getValueAt(lista.getSelectedRow(), 0).toString();
                                           rut = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                           nombre = lista.getValueAt(lista.getSelectedRow(), 2).toString();
                                           calle = lista.getValueAt(lista.getSelectedRow(), 3).toString();
                                           numeracion = lista.getValueAt(lista.getSelectedRow(), 4).toString();
                                           comuna = lista.getValueAt(lista.getSelectedRow(), 5).toString();
                                           ciudad = lista.getValueAt(lista.getSelectedRow(), 6).toString();
                                           pais = lista.getValueAt(lista.getSelectedRow(), 7).toString();
                                           ntelefono = lista.getValueAt(lista.getSelectedRow(), 8).toString();
                                           ainicio_ventas = lista.getValueAt(lista.getSelectedRow(), 9).toString();
                                           Registrar_Distribuidor reg_dist = new Registrar_Distribuidor(cod, rut, nombre, pais, ciudad, comuna, calle, numeracion, ntelefono, ainicio_ventas);
                                           reg_dist.setVisible(true);
                                           break;
                                           
                    case "Idiomas": cod = lista.getValueAt(lista.getSelectedRow(),0).toString();
                                    nombre = lista.getValueAt(lista.getSelectedRow(),1).toString();
                                    Registrar_Idioma reg_idiomas = new Registrar_Idioma(cod, nombre);
                                    reg_idiomas.setVisible(true);
                                    break;
                                    
                                    
                    case "Categorias": cod = lista.getValueAt(lista.getSelectedRow(),0).toString();
                                       nombre = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                       Registrar_Categoria reg_categoria = new Registrar_Categoria(cod, nombre);
                                       reg_categoria.setVisible(true);
                                       break;
                                               
                    case "Editorial": cod = lista.getValueAt(lista.getSelectedRow(),0).toString();
                                      nombre = lista.getValueAt(lista.getSelectedRow(),1).toString();
                                      Registrar_Editorial reg_editorial= new Registrar_Editorial(cod, nombre);
                                      reg_editorial.setVisible(true);
                                      break;
                                        
                    case "Autores":cod = lista.getValueAt(lista.getSelectedRow(), 0).toString();
                                   nombre = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                   String apellidop = lista.getValueAt(lista.getSelectedRow(), 2).toString();
                                   String apellidom = lista.getValueAt(lista.getSelectedRow(), 3).toString();
                                   Registrar_Autor reg_autor = new Registrar_Autor(cod, nombre,apellidop,apellidom);
                                   reg_autor.setVisible(true);
                                   break;
                        
                    case "Libros":cod = lista.getValueAt(lista.getSelectedRow(), 0).toString();
                                  nserie = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                  isbn = lista.getValueAt(lista.getSelectedRow(), 2).toString();
                                  titulo = lista.getValueAt(lista.getSelectedRow(), 3).toString();
                                  npaginas = lista.getValueAt(lista.getSelectedRow(), 4).toString();
                                  precioref = lista.getValueAt(lista.getSelectedRow(), 5).toString();
                                  apublicacion = lista.getValueAt(lista.getSelectedRow(), 6).toString();
                                  cod_editorial = lista.getValueAt(lista.getSelectedRow(), 7).toString();
                                  cod_estado = lista.getValueAt(lista.getSelectedRow(), 8).toString();        
                                  Registrar_Libro reg_libro = new Registrar_Libro(cod, nserie, isbn, titulo, npaginas, precioref, apublicacion, cod_editorial, cod_estado);
                                  reg_libro.setVisible(true);
                                  break;
                        
                    case "Estado":cod = lista.getValueAt(lista.getSelectedRow(), 0).toString();
                                  desc = lista.getValueAt(lista.getSelectedRow(), 1).toString();
                                  Registrar_Estado reg_estado = new Registrar_Estado(cod, desc);
                                  reg_estado.setVisible(true);
                                  break;            
            }//Fin Switch
        }
        catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(rootPane,"NO SE HA SELECCIONADO UNA FILA INTENTE NUEVAMENTE");
        }//Fin Try-Catch
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
         setModelo(tabla);
    }//GEN-LAST:event_btn_actualizarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Listas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_filtrar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_ver_autores;
    private javax.swing.JButton btn_ver_categorias;
    private javax.swing.JButton btn_ver_idiomas;
    private javax.swing.JButton btn_ver_libros_comprados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable lista;
    private javax.swing.JLabel texto_filtro;
    private javax.swing.JTextField txt_busqueda;
    // End of variables declaration//GEN-END:variables
}
