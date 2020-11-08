/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author helde
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        //DESABILITA O BOTÃO DE MAXIMIZAR
        setResizable(false);

        addWindowListener(new WindowAdapter() { // para confirna se deseja ralmente sair do sistema
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Sim", "Não"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                        "Deseja sair do sistema?", "ATENÇÃO",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        ObjButtons, ObjButtons[1]);
                if (PromptResult == 0) {
                    System.exit(0);
                }
            }
        });
    }

    public void habilitarForm() {
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Cad_Pais_ = new javax.swing.JMenuItem();
        cad_Estado_ = new javax.swing.JMenuItem();
        cad_Cidade_ = new javax.swing.JMenuItem();
        cad_Vizinhanca_ = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        menu_pesquisar_Alterar = new javax.swing.JMenu();
        alterar_Pais_ = new javax.swing.JMenuItem();
        alterar_Estado_ = new javax.swing.JMenuItem();
        alterar_Cidade_ = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CADASTRO EMPRESARIAL");
        setState(14);
        setType(java.awt.Window.Type.UTILITY);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logo.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/SALVAR 2.png"))); // NOI18N

        Cad_Pais_.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        Cad_Pais_.setText("País");
        Cad_Pais_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cad_Pais_ActionPerformed(evt);
            }
        });
        jMenu1.add(Cad_Pais_);

        cad_Estado_.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        cad_Estado_.setText("Estado");
        cad_Estado_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_Estado_ActionPerformed(evt);
            }
        });
        jMenu1.add(cad_Estado_);

        cad_Cidade_.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        cad_Cidade_.setText("Cidade");
        cad_Cidade_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_Cidade_ActionPerformed(evt);
            }
        });
        jMenu1.add(cad_Cidade_);

        cad_Vizinhanca_.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        cad_Vizinhanca_.setText("Vizinhança");
        cad_Vizinhanca_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_Vizinhanca_ActionPerformed(evt);
            }
        });
        jMenu1.add(cad_Vizinhanca_);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Companhia");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        menu_pesquisar_Alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/editar.png"))); // NOI18N

        alterar_Pais_.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        alterar_Pais_.setText("País");
        alterar_Pais_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterar_Pais_ActionPerformed(evt);
            }
        });
        menu_pesquisar_Alterar.add(alterar_Pais_);

        alterar_Estado_.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        alterar_Estado_.setText("Estado");
        alterar_Estado_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterar_Estado_ActionPerformed(evt);
            }
        });
        menu_pesquisar_Alterar.add(alterar_Estado_);

        alterar_Cidade_.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        alterar_Cidade_.setText("Cidade");
        alterar_Cidade_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterar_Cidade_ActionPerformed(evt);
            }
        });
        menu_pesquisar_Alterar.add(alterar_Cidade_);

        jMenuBar1.add(menu_pesquisar_Alterar);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menuPesquisar.png"))); // NOI18N
        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ESCLUIR 2.png"))); // NOI18N
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cad_Estado_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_Estado_ActionPerformed
        Localizacao l = new Localizacao();
        l.statusForm("cadastrar", "estado");
        l.selecionar_guia(1);
        l.setTitle("Cadastrar Estado");
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_cad_Estado_ActionPerformed

    private void Cad_Pais_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cad_Pais_ActionPerformed
        Localizacao l = new Localizacao();
        l.statusForm("cadastrar", "pais");
        l.selecionar_guia(0);
        l.setTitle("Cadastrar País");
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Cad_Pais_ActionPerformed

    private void cad_Cidade_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_Cidade_ActionPerformed
        Localizacao l = new Localizacao();
        l.statusForm("cadastrar", "cidade");
        l.selecionar_guia(2);
        l.setTitle("Cadastrar Cidade");
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_cad_Cidade_ActionPerformed

    private void cad_Vizinhanca_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_Vizinhanca_ActionPerformed
        Localizacao l = new Localizacao();
        l.statusForm("cadastrar", "vizinhanca");
        l.selecionar_guia(3);
        l.setTitle("Cadastrar Vizinhança");
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_cad_Vizinhanca_ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Companhia c = new Companhia();
        c.statusForm("cadastrar");
        c.setTitle("Cadastrar Companhia");
        c.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void alterar_Pais_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterar_Pais_ActionPerformed
        Localizacao l = new Localizacao();
        l.statusForm("alterar", "pais");
        l.selecionar_guia(0);
        l.setTitle("Alterar País");
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_alterar_Pais_ActionPerformed

    private void alterar_Estado_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterar_Estado_ActionPerformed
        Localizacao l = new Localizacao();
        l.statusForm("alterar", "estado");
        l.selecionar_guia(1);
        l.setTitle("Alterar Estado");
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_alterar_Estado_ActionPerformed

    private void alterar_Cidade_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterar_Cidade_ActionPerformed
        Localizacao l = new Localizacao();
        l.statusForm("alterar", "cidade");
        l.selecionar_guia(2);
        l.setTitle("Alterar Cidade");
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_alterar_Cidade_ActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Cad_Pais_;
    private javax.swing.JMenuItem alterar_Cidade_;
    private javax.swing.JMenuItem alterar_Estado_;
    private javax.swing.JMenuItem alterar_Pais_;
    private javax.swing.JMenuItem cad_Cidade_;
    private javax.swing.JMenuItem cad_Estado_;
    private javax.swing.JMenuItem cad_Vizinhanca_;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenu menu_pesquisar_Alterar;
    // End of variables declaration//GEN-END:variables
}
