package com.aps4sem.chatclient;

import com.aps4sem.chatlibrary.*;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home extends javax.swing.JFrame {
    
    private final Socket clientSocket;
    private final String user;
    
    public Home(Socket clientSocket, String user) {
        this.clientSocket = clientSocket;
        this.user = user;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listModel_contatos = new javax.swing.DefaultListModel();
        pn_principal = new javax.swing.JPanel();
        lb_titulo = new javax.swing.JLabel();
        bt_atualizar = new javax.swing.JButton();
        bt_conversar = new javax.swing.JButton();
        scroll_list_contatos = new javax.swing.JScrollPane();
        list_contatos = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Home");
        setMinimumSize(new java.awt.Dimension(600, 480));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pn_principal.setBackground(java.awt.Color.white);
        pn_principal.setLayout(null);

        lb_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_titulo.setText("< Usuário : " + user + " >");
        lb_titulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_principal.add(lb_titulo);
        lb_titulo.setBounds(10, 10, 380, 40);

        bt_atualizar.setText("Atualizar Contatos");
        pn_principal.add(bt_atualizar);
        bt_atualizar.setBounds(405, 10, 185, 40);

        bt_conversar.setText("Abrir Conversa");
        pn_principal.add(bt_conversar);
        bt_conversar.setBounds(10, 430, 580, 40);

        list_contatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuários Online"));
        list_contatos.setModel(listModel_contatos);
        list_contatos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scroll_list_contatos.setViewportView(list_contatos);

        pn_principal.add(scroll_list_contatos);
        scroll_list_contatos.setBounds(10, 60, 580, 360);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            SocketStream.send(clientSocket, ClientRequest.LOGOUT);
        }
        catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        new Thread(() -> {
            try {
                ServerMessage serverMessage;

                while (true)
                {
                    serverMessage = (ServerMessage) SocketStream.receive(clientSocket);
                    System.out.println("Mensagem recebida");

                    switch (serverMessage)
                    {
                        case USER_LOGON:
                            String userJoined = (String) SocketStream.receive(clientSocket);
                            listModel_contatos.addElement(userJoined);
                            break;
                        case USER_LOGOFF:
                            String userLeft = (String) SocketStream.receive(clientSocket);
                            listModel_contatos.removeElement(userLeft);
                            break;
                    }
                }
                
                //System.out.println("Conexão encerrada.");
            }
            catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (ClassNotFoundException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_atualizar;
    private javax.swing.JButton bt_conversar;
    private javax.swing.JLabel lb_titulo;
    private javax.swing.DefaultListModel listModel_contatos;
    private javax.swing.JList<String> list_contatos;
    private javax.swing.JPanel pn_principal;
    private javax.swing.JScrollPane scroll_list_contatos;
    // End of variables declaration//GEN-END:variables

}
