/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Querys.Querys;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Bastian Vidal
 * @version 1.0 29/06/2018
 */
public class Registrar_Factura extends javax.swing.JFrame {

    /**
     * Creates new form Registrar_Libro
     */
    static Querys q = new Querys();
    static Statement st = q.getSt();
    int i=0;
    String cod;
    
    DefaultComboBoxModel modelo_jcombod = new DefaultComboBoxModel();
    DefaultComboBoxModel modelo_jcombom = new DefaultComboBoxModel();
    
    /**
     * Constructor por defecto
     */
    public Registrar_Factura() {
        initComponents();
        setTitle("Registro: Factura" );
        CrearComboBox();  
        label_cod.setVisible(false);
        label_op.setText("Registro: Factura");
        btn_modificar.setVisible(false);
    }
    
    /**
     * Constructor utilizado para modificar registros
     * @param cod Código del registro a modificar
     * @param folio Dato actual
     * @param precio_neto Dato actual
     * @param precio_IVA Dato actual
     * @param costo_IVA Dato actual
     * @param fecha_compra Dato actual
     * @param hora_compra Dato actual
     * @param cod_dist Dato actual
     * @param cod_met Dato actual 
     */
     public Registrar_Factura(String cod,String folio,String precio_neto, String precio_IVA, String costo_IVA, String fecha_compra, String hora_compra, String cod_dist, String cod_met) {
        initComponents();
        setTitle("Modificar: Factura" );
        label_op.setText("Modificar: Factura");
        CrearComboBox();
        btn_registrar.setVisible(false);
        this.cod = cod;
        label_cod.setText(label_cod.getText()+""+cod);
        txt_folio.setText(folio);
        txt_precio_neto.setText(precio_neto);
        txt_precio_con_iva.setText(precio_IVA);
        txt_costo_iva.setText(costo_IVA);
        txt_ano_compra.setText(fecha_compra.charAt(0)+""+fecha_compra.charAt(1)+""+fecha_compra.charAt(2)+""+fecha_compra.charAt(3)+"");
        txt_mes_compra.setText(fecha_compra.charAt(5)+""+fecha_compra.charAt(6)+"");
        txt_dia_compra.setText(fecha_compra.charAt(8)+""+fecha_compra.charAt(9)+"");
        txt_hora_compra.setText(hora_compra.charAt(0)+""+hora_compra.charAt(1)+"");
        txt_minutos_compra.setText(hora_compra.charAt(3)+""+hora_compra.charAt(4)+"");
        jcombo_distribuidor.setSelectedItem(cod_dist);
        jcombo_metodo_pago.setSelectedItem(cod_met);
    }
    
     /**
      * Método utilizado para definir los modelos de los JComboBox
      */
    private void CrearComboBox(){
         try{
            q.ListarDistribuidorCB();
            ResultSet rs  =st.executeQuery(q.getSql());
            while(rs.next()){
                String cod =rs.getString("cod");
                modelo_jcombod.addElement(cod);
           }
            q.ListarMetodoPagoCB();
            rs  =st.executeQuery(q.getSql());
            while(rs.next()){
                String cod =rs.getString("cod");
                modelo_jcombom.addElement(cod);
           }
           
         jcombo_distribuidor.setModel(modelo_jcombod);
         jcombo_metodo_pago.setModel(modelo_jcombom);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_precio_neto = new javax.swing.JTextField();
        txt_folio = new javax.swing.JTextField();
        txt_precio_con_iva = new javax.swing.JTextField();
        txt_costo_iva = new javax.swing.JTextField();
        txt_dia_compra = new javax.swing.JTextField();
        txt_minutos_compra = new javax.swing.JTextField();
        btn_registrar = new javax.swing.JButton();
        label_op = new javax.swing.JLabel();
        jcombo_metodo_pago = new javax.swing.JComboBox();
        jcombo_distribuidor = new javax.swing.JComboBox();
        txt_mes_compra = new javax.swing.JTextField();
        txt_ano_compra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_hora_compra = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        label_cod = new javax.swing.JLabel();
        btn_modificar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Folio:");

        jLabel2.setText("Precio neto:");

        jLabel3.setText("Precio con IVA:");

        jLabel4.setText("Costo IVA:");

        jLabel5.setText("Fecha de compra:");

        jLabel6.setText("Hora de compra:");

        jLabel7.setText("Codigo Distribuidor:");

        jLabel8.setText("Codigo Método de pago:");

        txt_precio_neto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_precio_netoActionPerformed(evt);
            }
        });

        txt_folio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_folioActionPerformed(evt);
            }
        });

        txt_precio_con_iva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_precio_con_ivaActionPerformed(evt);
            }
        });

        txt_costo_iva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_costo_ivaActionPerformed(evt);
            }
        });

        txt_dia_compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dia_compraActionPerformed(evt);
            }
        });

        txt_minutos_compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_minutos_compraActionPerformed(evt);
            }
        });

        btn_registrar.setText("REGISTRAR");
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });

        label_op.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        label_op.setText("Registrar Factura");

        jcombo_metodo_pago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jcombo_distribuidor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("/");

        jLabel10.setText("/");

        jLabel12.setText("DD/MM/AAAA");

        jLabel13.setText(":");

        jLabel14.setText("HH:MM");

        label_cod.setText("COD:");

        btn_modificar.setText("MODIFICAR");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        jLabel11.setText("[ 00000 ] (Sin puntos)");

        jLabel15.setText("[ 00000 ] (Sin puntos)");

        jLabel16.setText("[ 00000 ] (Sin puntos)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(label_op)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_registrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_precio_neto, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_folio, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_precio_con_iva, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txt_costo_iva, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(jcombo_metodo_pago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcombo_distribuidor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txt_dia_compra)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_mes_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_ano_compra))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_hora_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_minutos_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel14)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel11)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addContainerGap())))
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
                    .addComponent(txt_folio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_precio_neto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_precio_con_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_costo_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_dia_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_mes_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ano_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_minutos_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_hora_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jcombo_distribuidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jcombo_metodo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_registrar)
                    .addComponent(btn_modificar))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_precio_netoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_precio_netoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_precio_netoActionPerformed

    private void txt_folioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_folioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_folioActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void txt_precio_con_ivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_precio_con_ivaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_precio_con_ivaActionPerformed

    private void txt_costo_ivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_costo_ivaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_costo_ivaActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        // TODO add your handling code here:
     try{ 
        String fecha = q.ValidarAño(txt_ano_compra.getText())+"-"+q.ValidarFecha(txt_dia_compra.getText(), txt_mes_compra.getText());
        q.CrearFactura(txt_folio.getText(), Integer.parseInt(txt_precio_neto.getText()),Integer.parseInt(txt_precio_con_iva.getText()), 
                Integer.parseInt(txt_costo_iva.getText()), fecha , q.ValidarHora(txt_hora_compra.getText(), txt_minutos_compra.getText()), 
                Integer.parseInt(jcombo_distribuidor.getSelectedItem().toString()), Integer.parseInt(jcombo_metodo_pago.getSelectedItem().toString()));
     }catch(Exception e){
         JOptionPane.showMessageDialog(null, e.getMessage());
     }
    }//GEN-LAST:event_btn_registrarActionPerformed

    private void txt_minutos_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_minutos_compraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_minutos_compraActionPerformed

    private void txt_dia_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dia_compraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dia_compraActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        // TODO add your handling code here:
             try{ 
        String fecha = q.ValidarAño(txt_ano_compra.getText())+"-"+q.ValidarFecha(txt_dia_compra.getText(), txt_mes_compra.getText());
        q.ModificarFactura(cod,txt_folio.getText(), Integer.parseInt(txt_precio_neto.getText()),Integer.parseInt(txt_precio_con_iva.getText()), 
                Integer.parseInt(txt_costo_iva.getText()), fecha , q.ValidarHora(txt_hora_compra.getText(), txt_minutos_compra.getText()), 
                Integer.parseInt(jcombo_distribuidor.getSelectedItem().toString()), Integer.parseInt(jcombo_metodo_pago.getSelectedItem().toString()));
     }catch(Exception e){
         JOptionPane.showMessageDialog(null, e.getMessage());
     }
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
            java.util.logging.Logger.getLogger(Registrar_Factura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_Factura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_Factura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_Factura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_Factura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_registrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox jcombo_distribuidor;
    private javax.swing.JComboBox jcombo_metodo_pago;
    private javax.swing.JLabel label_cod;
    private javax.swing.JLabel label_op;
    private javax.swing.JTextField txt_ano_compra;
    private javax.swing.JTextField txt_costo_iva;
    private javax.swing.JTextField txt_dia_compra;
    private javax.swing.JTextField txt_folio;
    private javax.swing.JTextField txt_hora_compra;
    private javax.swing.JTextField txt_mes_compra;
    private javax.swing.JTextField txt_minutos_compra;
    private javax.swing.JTextField txt_precio_con_iva;
    private javax.swing.JTextField txt_precio_neto;
    // End of variables declaration//GEN-END:variables
}
