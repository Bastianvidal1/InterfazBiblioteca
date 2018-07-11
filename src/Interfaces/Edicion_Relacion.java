/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import static Interfaces.Registrar_Libro.st;
import Querys.Querys;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Bastian Vidal
 */
public class Edicion_Relacion extends javax.swing.JFrame {
    Querys q = new Querys();
    String cod;
    DefaultComboBoxModel modelo_jcomborel = new DefaultComboBoxModel();
    String relacion;

    /**
     * Creates new form Modidicar_Relacion
     */
    
    /**
     * Constructor por defecto
     */
    public Edicion_Relacion(){
        initComponents();
    }
    
    /**
     * Constructor utilizado para agregar un registro a la relación
     * @param cod Código del registro de la tabla Libro o Compra al cual se le agrega un registro en la tabla relacional
     * @param rel Relación a la que se le agregan registros
     */
    public Edicion_Relacion(int cod,String rel) {
        initComponents();
        label_cod.setText("COD LIBRO/COMPRA ASOCIADA:"+cod);
        label_modificar.setText("Agregar:"+rel);
        this.relacion = rel;
        this.cod = ""+cod;
        CrearComboBox(relacion);
        btn_modificar.setVisible(false);
    }
    
    /**
     * Constructor utilizado para modificar un registro de la relación
     * @param cod Código del registro relacional el cual será modificado
     * @param relacion Relació a la que se le modifican registros
     */
    public Edicion_Relacion(String cod, String relacion){
        initComponents();
        this.cod = cod;
        this.relacion = relacion;
        label_cod.setText(label_cod.getText()+""+cod);
        label_modificar.setText(label_modificar.getText()+""+relacion);
        CrearComboBox(relacion);
        btn_agregar.setVisible(false);
    }

    /**
     * Método encargado de definir los JComboBox con los datos correspondientes a la relación solicitada
     * @param relacion Relación a la cual se le van a editar o agregar registros
     */
    private void CrearComboBox(String relacion){
        ResultSet rs;
        
        try{
        switch(relacion){
            case "LIBRO_AUTOR": q.ListarAutoresCB();
                                rs  =st.executeQuery(q.getSql());//SE EJECUTA UNA NUEVA CONSULTA
                                while(rs.next()){
                                    String cod =rs.getString("cod");// LOS REGISTROS OBTENIDOS SON INCLUIDOS EN EL MODELO DE JCOMBO
                                    modelo_jcomborel.addElement(cod);
                                }
                                jcombo_relacion.setModel(modelo_jcomborel);
                                label_relacion.setText(label_relacion.getText()+" AUTOR");
                                break;
                
            case "LIBRO_IDIOMAS":q.ListarIdiomaCB();
                                rs  =st.executeQuery(q.getSql());//SE EJECUTA UNA NUEVA CONSULTA
                                while(rs.next()){
                                    String cod =rs.getString("cod");// LOS REGISTROS OBTENIDOS SON INCLUIDOS EN EL MODELO DE JCOMBO
                                    modelo_jcomborel.addElement(cod);
                                }
                                jcombo_relacion.setModel(modelo_jcomborel);
                                label_relacion.setText(label_relacion.getText()+" IDIOMA");
                                break;
                
            case "LIBRO_CATEGORIAS":q.ListarCategoriasCB();
                                    rs  =st.executeQuery(q.getSql());//SE EJECUTA UNA NUEVA CONSULTA
                                    while(rs.next()){
                                        String cod =rs.getString("cod");// LOS REGISTROS OBTENIDOS SON INCLUIDOS EN EL MODELO DE JCOMBO
                                        modelo_jcomborel.addElement(cod);
                                    }
                                    jcombo_relacion.setModel(modelo_jcomborel);
                                    label_relacion.setText(label_relacion.getText()+" CATEGORIA");
                                    break;
                
            case "LIBRO_COMPRAS":   q.ListarLibrosCB();
                                    rs  =st.executeQuery(q.getSql());//SE EJECUTA UNA NUEVA CONSULTA
                                    while(rs.next()){
                                        String cod =rs.getString("cod");// LOS REGISTROS OBTENIDOS SON INCLUIDOS EN EL MODELO DE JCOMBO
                                        modelo_jcomborel.addElement(cod);
                                    }
                                    jcombo_relacion.setModel(modelo_jcomborel);
                                    label_relacion.setText(label_relacion.getText()+" LIBRO");
                                    break;
        }//Fin Switch
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR DE MySQL: "+ e.getMessage(),"ERROR DE CONEXIÓN", JOptionPane.ERROR_MESSAGE);
        }//Fin Try-Catch
    }//Fin métodod
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_modificar = new javax.swing.JLabel();
        label_cod = new javax.swing.JLabel();
        label_relacion = new javax.swing.JLabel();
        jcombo_relacion = new javax.swing.JComboBox();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        label_modificar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label_modificar.setText("Modificar: ");

        label_cod.setText("COD:");

        label_relacion.setText("CÓDIGO");

        jcombo_relacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_agregar.setText("AGREGAR");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        btn_modificar.setText("MODIFICAR");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(label_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(55, 55, 55)
                            .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(13, 13, 13)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_relacion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcombo_relacion, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(label_modificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_cod)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_relacion)
                    .addComponent(jcombo_relacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregar)
                    .addComponent(btn_modificar))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        // TODO add your handling code here:
        //Envía los datos necesarios para modificar registro de la relación 
        q.ModificarRelacion(cod, jcombo_relacion.getSelectedItem().toString(), relacion);
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        //Envía los datos necesario para agregar un registro a la relación
        q.AgregarRelacion(cod, jcombo_relacion.getSelectedItem().toString(), relacion);
    }//GEN-LAST:event_btn_agregarActionPerformed

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
            java.util.logging.Logger.getLogger(Edicion_Relacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Edicion_Relacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Edicion_Relacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Edicion_Relacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Edicion_Relacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox jcombo_relacion;
    private javax.swing.JLabel label_cod;
    private javax.swing.JLabel label_modificar;
    private javax.swing.JLabel label_relacion;
    // End of variables declaration//GEN-END:variables
}
