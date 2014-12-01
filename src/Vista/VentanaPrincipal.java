/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.*;
import Modelo.AdminUsuarios;
import Modelo.Candidato;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author carla
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    ControladorVotos controladorVotos;
    ControladorCandidatos controladorCandidatos;
    ControladorSesion controladorSesion;
    GestorCandidatos gestor;
    private final AdminUsuarios adminUsuarios;
    private static VentanaPrincipal instancia;

    public VentanaPrincipal(ControladorVotos controladorDeVotos, ControladorSesion controladorSesion) {
        this.controladorVotos = controladorDeVotos;
        this.controladorSesion = controladorSesion;
        this.controladorCandidatos = new ControladorCandidatos(controladorVotos.getModelo(), 0);
        this.gestor = GestorCandidatos.getInstance(controladorVotos, controladorSesion);
        this.adminUsuarios = AdminUsuarios.getInstance();
        initComponents();
    }

    public static VentanaPrincipal getInstance(ControladorVotos controladorDeVotos, ControladorSesion controladorSesion) {
        if (instancia == null) {
            instancia = new VentanaPrincipal(controladorDeVotos, controladorSesion);
        }
        return instancia;
    }

    public void inicializaVentana(ArrayList<Candidato> candidatos) {
        DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
        for (Candidato candidato : candidatos) {
            modeloCombo.addElement(candidato.getId() + "  " + candidato.getNombre());
        }
        cboxCandidatos1.setModel(modeloCombo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbTexto1 = new javax.swing.JLabel();
        cboxCandidatos1 = new javax.swing.JComboBox();
        btnVotar = new javax.swing.JButton();
        lbVotar = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu = new javax.swing.JMenu();
        menuGestionar = new javax.swing.JMenuItem();
        CerrarSesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

        lbTexto1.setText("Seleccione el candidato por el cual desea votar:");

        cboxCandidatos1.setBackground(new java.awt.Color(254, 254, 254));
        cboxCandidatos1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnVotar.setBackground(new java.awt.Color(0, 144, 106));
        btnVotar.setForeground(new java.awt.Color(254, 254, 254));
        btnVotar.setText("Votar");
        btnVotar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVotarActionPerformed(evt);
            }
        });

        lbVotar.setFont(new java.awt.Font("Cantarell", 1, 16)); // NOI18N
        lbVotar.setForeground(new java.awt.Color(0, 144, 106));
        lbVotar.setText("Votar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTexto1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cboxCandidatos1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addComponent(btnVotar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbVotar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbVotar)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTexto1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxCandidatos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVotar))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Menu.setText("Sesión");

        menuGestionar.setText("Gestionar Candidato");
        menuGestionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGestionarActionPerformed(evt);
            }
        });
        Menu.add(menuGestionar);

        CerrarSesion.setText("Cerrar Sesión");
        CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarSesionActionPerformed(evt);
            }
        });
        Menu.add(CerrarSesion);

        jMenuBar1.add(Menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVotarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVotarActionPerformed
        String candidato = cboxCandidatos1.getSelectedItem().toString();
        String[] candidatoID = candidato.split("  ");
        int id;
        id = Integer.parseInt(candidatoID[0]);

        if (adminUsuarios.getPermiso("Votar")) {
            controladorVotos.realizarVotacion(id);
        } else {
            JOptionPane.showMessageDialog(null, "No tienes permiso para votar");
        }
    }//GEN-LAST:event_btnVotarActionPerformed

    private void CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarSesionActionPerformed
        controladorSesion.logOut();

//        this.dispose();
        new VentanaLogIn().setVisible(true);
    }//GEN-LAST:event_CerrarSesionActionPerformed

    private void menuGestionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGestionarActionPerformed
        if (adminUsuarios.getPermiso("Gestionar candidatos")) {
            gestor.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No tienes permiso para gestionar los candidatos");
        }
    }//GEN-LAST:event_menuGestionarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CerrarSesion;
    private javax.swing.JMenu Menu;
    private javax.swing.JButton btnVotar;
    private javax.swing.JComboBox cboxCandidatos1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbTexto1;
    private javax.swing.JLabel lbVotar;
    public javax.swing.JMenuItem menuGestionar;
    // End of variables declaration//GEN-END:variables
}
