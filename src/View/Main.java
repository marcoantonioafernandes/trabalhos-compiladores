package View;

import Control.AnalisadorLexico;
import Control.AnalisadorSintatico;
import Model.Registro;
import Model.Token;
import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

public class Main extends javax.swing.JFrame {

    AnalisadorLexico al = new AnalisadorLexico();
    AnalisadorSintatico as;
    
    RSyntaxTextArea rsta = new RSyntaxTextArea(60, 60);

    public Main() {
        initComponents();
        
        this.panelFonte.setLayout(new BorderLayout());        
        rsta.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);  
        rsta.setTemplatesEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(rsta);
        this.panelFonte.add(sp);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelFonte = new javax.swing.JPanel();
        panelItens = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaItens = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaSimbolos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaMessagens = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        btnAvaliar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compiladores");

        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        panelFonte.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout panelFonteLayout = new javax.swing.GroupLayout(panelFonte);
        panelFonte.setLayout(panelFonteLayout);
        panelFonteLayout.setHorizontalGroup(
            panelFonteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1058, Short.MAX_VALUE)
        );
        panelFonteLayout.setVerticalGroup(
            panelFonteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Fonte", panelFonte);

        panelItens.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        areaItens.setEditable(false);
        areaItens.setColumns(20);
        areaItens.setRows(5);
        jScrollPane2.setViewportView(areaItens);

        javax.swing.GroupLayout panelItensLayout = new javax.swing.GroupLayout(panelItens);
        panelItens.setLayout(panelItensLayout);
        panelItensLayout.setHorizontalGroup(
            panelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
        );
        panelItensLayout.setVerticalGroup(
            panelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Itens Léxicos", panelItens);

        tabelaSimbolos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexema", "Categoria", "Tipo", "Endereço"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelaSimbolos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Tabela de Símbolos", jPanel1);

        jPanel4.add(jTabbedPane1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Mensagens"));

        areaMessagens.setColumns(20);
        areaMessagens.setRows(5);
        jScrollPane4.setViewportView(areaMessagens);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        btnAvaliar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icons/check.png"))); // NOI18N
        btnAvaliar.setText("Avaliar");
        btnAvaliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvaliarActionPerformed(evt);
            }
        });
        jPanel3.add(btnAvaliar);

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icons/close.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel3.add(btnSair);

        jMenu1.setText("Arquivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Executar");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Janela");
        jMenuBar1.add(jMenu4);

        jMenu5.setText("Ajuda");
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAvaliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarActionPerformed
        // TODO add your handling code here:
        if (!this.rsta.getText().isEmpty()) {
            String fonte[] = this.rsta.getText().split("\n");
            al.analisar(fonte);
            List<Token> tokens = al.getTokens();
            as = new AnalisadorSintatico(tokens);
            as.analisar();
            Iterator<Registro> it = as.getSimbolos().iterator();
            int i = 0;
            if(as.getMsgErro().equals("")){
                this.areaMessagens.setText("Analise sintática concluída");
            } else {
                this.areaMessagens.setText(as.getMsgErro());
            }
            
            DefaultTableModel tabSimbolos = (DefaultTableModel) this.tabelaSimbolos.getModel();
            tabSimbolos.setRowCount(0);
            
            while(it.hasNext()){                
                Registro simbolo = it.next();                                                 
                String[] row = {simbolo.getNome(), simbolo.getCategoria(), simbolo.getTipo(), simbolo.getEndereco()};         
                /* System.out.println(i++ + " - Lexema: " + simbolo.getLexema() + " Categoria: " + simbolo.getCategoria()
                + " Tipo: " + simbolo.getTipo() + " Endereço: " + simbolo.getEndereco()); */
                tabSimbolos.addRow(row);
            }
            this.areaItens.setText(al.tokensToString());            
        }                
    }//GEN-LAST:event_btnAvaliarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                JFrame main = new Main();
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaItens;
    private javax.swing.JTextArea areaMessagens;
    private javax.swing.JButton btnAvaliar;
    private javax.swing.JButton btnSair;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelFonte;
    private javax.swing.JPanel panelItens;
    private javax.swing.JTable tabelaSimbolos;
    // End of variables declaration//GEN-END:variables
}
