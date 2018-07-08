/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import static Interfaces.Registrar_Factura.q;
import Querys.Querys;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Bastian Vidal
 */
public class Registrar_Libro extends javax.swing.JFrame {

    /**
     * Creates new form Registrar_Libro
     */
    String [] botones = {"Añadir Autor","Añadir Idioma","Añadir Categoría","Cancelar"};
    static Querys q = new Querys();
    static Statement st = q.getSt();
    int i=0;
    DefaultComboBoxModel modelo_jcomboidiomas = new DefaultComboBoxModel();
    DefaultComboBoxModel modelo_jcomboestado = new DefaultComboBoxModel();
    DefaultComboBoxModel modelo_jcomboautores = new DefaultComboBoxModel();
    DefaultComboBoxModel modelo_jcombocategorias = new DefaultComboBoxModel();
    DefaultComboBoxModel modelo_jcomboeditoriales = new DefaultComboBoxModel();
    DefaultListModel modelo_listacategorias = new DefaultListModel();
    DefaultListModel modelo_listaidiomas = new DefaultListModel();
    DefaultListModel modelo_listaautores = new DefaultListModel();
    String cod;
    

    /**
     * Constructor por defecto
     */
    public Registrar_Libro() {
        initComponents();
        setTitle("Registro: Libro" );
        label_op.setText("Registro: Libro");
        label_cod.setVisible(false);
        btn_modificar.setVisible(false);
        CrearComboBox();
    }
    
    /**
     * Constructor utilizado para la modificacion de registros
     * @param cod
     * @param nserie
     * @param isbn
     * @param titulo
     * @param npaginas
     * @param precioref
     * @param apublicacion
     * @param cod_editorial
     * @param cod_estado 
     */
    public Registrar_Libro(String cod,String nserie,String isbn,String titulo,String npaginas, String precioref, String apublicacion,
        String cod_editorial, String cod_estado) {
        initComponents();
        setTitle("Modificar: Libro" );
        label_op.setText("Modificar: Libro");
        CrearComboBox();
        this.cod = cod;
        label_cod.setText(label_cod.getText()+""+cod);
        txt_nserie.setText(nserie);
        txt_isbn.setText(isbn);
        txt_titulo.setText(titulo);
        txt_npaginas.setText(npaginas);
        txt_precioref.setText(precioref);
        txt_apublicacion.setText(apublicacion.charAt(0)+""+apublicacion.charAt(1)+""+apublicacion.charAt(2)+""+apublicacion.charAt(3));
        jcombo_editorial.setSelectedItem(cod_editorial);
        jcombo_estado.setSelectedItem(cod_estado);
        jcombo_idiomas.setEnabled(false);
        jcombo_autores.setEnabled(false);
        jcombo_categorias.setEnabled(false);
        btn_agregarautor.setEnabled(false);
        btn_agregarcategoria.setEnabled(false);
        btn_agregaridioma.setEnabled(false);
        btn_registrar.setVisible(false);
        jlist_autores.setEnabled(false);
        jlist_categorias.setEnabled(false);
        jlist_idiomas.setEnabled(false);
    }
    
    /**
     * Método encargado de definir los modelos de los JComboBox
     */
     private void CrearComboBox(){
         try{
            q.ListarIdiomaCB();//SE EJECUTA UN METODO DE QUERYS PARA COMPROBAR LOS REGISTROS DE LA TABLA 
            ResultSet rs  =st.executeQuery(q.getSql());//SE EJECUTA UNA NUEVA CONSULTA
            while(rs.next()){
                String cod =rs.getString("cod");// LOS REGISTROS OBTENIDOS SON INCLUIDOS EN EL MODELO DE JCOMBO
                modelo_jcomboidiomas.addElement(cod);
           }
            q.ListarAutoresCB();
            rs  =st.executeQuery(q.getSql());
            while(rs.next()){
                String cod =rs.getString("cod");
                modelo_jcomboautores.addElement(cod);
           }
            q.ListarCategoriasCB();
            rs  =st.executeQuery(q.getSql());
            while(rs.next()){
                String cod =rs.getString("cod");
                modelo_jcombocategorias.addElement(cod);
           }
            
            q.ListarEditorialCB();
            rs  =st.executeQuery(q.getSql());
            while(rs.next()){
                String cod =rs.getString("cod");
                modelo_jcomboeditoriales.addElement(cod);
           }
            
             q.ListarEstadoCB();
            rs  =st.executeQuery(q.getSql());
            while(rs.next()){
                String cod =rs.getString("cod");
                modelo_jcomboestado.addElement(cod);
           }
           
         jcombo_idiomas.setModel(modelo_jcomboidiomas);// SE DEFINEN LOS JCOMBO CON LOS MODELOS EDITADOS
         jcombo_autores.setModel(modelo_jcomboautores);
         jcombo_categorias.setModel(modelo_jcombocategorias);
         jcombo_editorial.setModel(modelo_jcomboeditoriales);
         jcombo_estado.setModel(modelo_jcomboestado);
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<String>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<String>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_isbn = new javax.swing.JTextField();
        txt_nserie = new javax.swing.JTextField();
        txt_titulo = new javax.swing.JTextField();
        txt_npaginas = new javax.swing.JTextField();
        txt_precioref = new javax.swing.JTextField();
        txt_apublicacion = new javax.swing.JTextField();
        btn_registrar = new javax.swing.JButton();
        label_op = new javax.swing.JLabel();
        jcombo_idiomas = new javax.swing.JComboBox<String>();
        btn_agregaridioma = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlist_idiomas = new javax.swing.JList<String>();
        jcombo_autores = new javax.swing.JComboBox<String>();
        btn_agregarautor = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlist_autores = new javax.swing.JList<String>();
        jcombo_categorias = new javax.swing.JComboBox<String>();
        btn_agregarcategoria = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jlist_categorias = new javax.swing.JList<String>();
        jcombo_editorial = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jcombo_estado = new javax.swing.JComboBox();
        btn_modificar = new javax.swing.JButton();
        label_cod = new javax.swing.JLabel();

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Numero de Serie:");

        jLabel2.setText("ISBN:");

        jLabel3.setText("Título:");

        jLabel4.setText("N° páginas:");

        jLabel5.setText("Precio referencia:");

        jLabel6.setText("Código Idioma:");

        jLabel7.setText("Año de publicación:");

        jLabel8.setText("Código Autor:");

        jLabel9.setText("Código Categoría:");

        jLabel10.setText("Código Editorial:");

        txt_isbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_isbnActionPerformed(evt);
            }
        });

        txt_nserie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nserieActionPerformed(evt);
            }
        });

        txt_titulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tituloActionPerformed(evt);
            }
        });

        txt_npaginas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_npaginasActionPerformed(evt);
            }
        });

        txt_precioref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_preciorefActionPerformed(evt);
            }
        });

        txt_apublicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_apublicacionActionPerformed(evt);
            }
        });

        btn_registrar.setText("REGISTRAR");
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });

        label_op.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        label_op.setText("Registrar Libro");

        jcombo_idiomas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_agregaridioma.setText(">>");
        btn_agregaridioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregaridiomaActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jlist_idiomas);

        jcombo_autores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_agregarautor.setText(">>");
        btn_agregarautor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarautorActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(jlist_autores);

        jcombo_categorias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_agregarcategoria.setText(">>");
        btn_agregarcategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarcategoriaActionPerformed(evt);
            }
        });

        jScrollPane5.setViewportView(jlist_categorias);

        jcombo_editorial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Código Estado:");

        jcombo_estado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_modificar.setText("MODIFICAR");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        label_cod.setText("COD:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_isbn, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_nserie, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_npaginas, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_precioref, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_apublicacion, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(jcombo_idiomas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcombo_autores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcombo_categorias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcombo_editorial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcombo_estado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(label_op, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btn_agregarcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btn_agregaridioma, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btn_agregarautor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_op, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_cod))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_nserie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_isbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_npaginas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_precioref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jcombo_idiomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_agregaridioma))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_apublicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jcombo_autores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_agregarautor))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jcombo_editorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jcombo_categorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_agregarcategoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jcombo_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_registrar, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btn_modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_isbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_isbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_isbnActionPerformed

    private void txt_nserieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nserieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nserieActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void txt_tituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tituloActionPerformed

    private void txt_npaginasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_npaginasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_npaginasActionPerformed

    private void txt_preciorefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_preciorefActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_preciorefActionPerformed

    private void txt_apublicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_apublicacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_apublicacionActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        // TODO add your handling code here:
        q.CrearLibro(txt_nserie.getText(), txt_isbn.getText(),txt_titulo.getText().toUpperCase(), q.ValidarInteger(txt_npaginas.getText()),
                q.ValidarInteger(txt_precioref.getText()), modelo_listaidiomas, q.ValidarAño(txt_apublicacion.getText()),
                modelo_listaautores, q.ValidarInteger(jcombo_editorial.getSelectedItem().toString()), modelo_listacategorias,
                q.ValidarInteger(jcombo_estado.getSelectedItem().toString()));
    }//GEN-LAST:event_btn_registrarActionPerformed

    private void btn_agregaridiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregaridiomaActionPerformed
       
        modelo_listaidiomas.addElement(jcombo_idiomas.getSelectedItem());
        jlist_idiomas.setModel(modelo_listaidiomas);
    }//GEN-LAST:event_btn_agregaridiomaActionPerformed

    private void btn_agregarautorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarautorActionPerformed
        // TODO add your handling code here:
        modelo_listaautores.addElement(jcombo_autores.getSelectedItem());
        jlist_autores.setModel(modelo_listaautores);
    }//GEN-LAST:event_btn_agregarautorActionPerformed

    private void btn_agregarcategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarcategoriaActionPerformed
        // TODO add your handling code here:
        modelo_listacategorias.addElement(jcombo_categorias.getSelectedItem());
        jlist_categorias.setModel(modelo_listacategorias);
    }//GEN-LAST:event_btn_agregarcategoriaActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        // TODO add your handling code here:
        q.ModificarLibro(cod,txt_nserie.getText(), txt_isbn.getText(),txt_titulo.getText().toUpperCase(), q.ValidarInteger(txt_npaginas.getText()),
                q.ValidarInteger(txt_precioref.getText()), q.ValidarAño(txt_apublicacion.getText()),
                q.ValidarInteger(jcombo_editorial.getSelectedItem().toString()),q.ValidarInteger(jcombo_estado.getSelectedItem().toString()));
    }//GEN-LAST:event_btn_modificarActionPerformed

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
            java.util.logging.Logger.getLogger(Registrar_Libro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_Libro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_Libro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_Libro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_Libro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregarautor;
    private javax.swing.JButton btn_agregarcategoria;
    private javax.swing.JButton btn_agregaridioma;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_registrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox<String> jcombo_autores;
    private javax.swing.JComboBox<String> jcombo_categorias;
    private javax.swing.JComboBox jcombo_editorial;
    private javax.swing.JComboBox jcombo_estado;
    private javax.swing.JComboBox<String> jcombo_idiomas;
    private javax.swing.JList<String> jlist_autores;
    private javax.swing.JList<String> jlist_categorias;
    private javax.swing.JList<String> jlist_idiomas;
    private javax.swing.JLabel label_cod;
    private javax.swing.JLabel label_op;
    private javax.swing.JTextField txt_apublicacion;
    private javax.swing.JTextField txt_isbn;
    private javax.swing.JTextField txt_npaginas;
    private javax.swing.JTextField txt_nserie;
    private javax.swing.JTextField txt_precioref;
    private javax.swing.JTextField txt_titulo;
    // End of variables declaration//GEN-END:variables
}
