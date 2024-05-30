package Main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.Charset;
import java.sql.DataTruncation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class Janela extends javax.swing.JFrame {
    
    private static MulticastSocket socket = null;
    private static InetAddress group = null;
    
    
    
    public Janela(MulticastSocket socket, InetAddress group) {
        initComponents();
        Janela.socket = socket;
        Janela.group = group;  
        lerMensagem();
        
        
        ajustes();
        
    }

    private void lerMensagem(){
        new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        byte[] rxData = new byte[65507];

                        /*   Esse limite é determinado pelo tamanho máximo permitido para um pacote UDP
                        (User Datagram Protocol) na camada de transporte. O tamanho máximo teórico de
                        um pacote UDP é de 65.535 bytes, mas, na prática,o limite é reduzido para
                        acomodar os cabeçalhos do protocolo.
                        No Java, o tamanho máximo recomendado é de 65507 bytes, pois esse valor é
                        adequado para acomodar os cabeçalhos UDP e IP padrão.*/
                        DatagramPacket rxPkt = new DatagramPacket(rxData, rxData.length);
                        socket.receive(rxPkt);
                        String payloadEmUTF8 = new String(rxData, "UTF-8");
                        //String payloadEmUTF8 = rxPkt.toString();//retorna endereco logico
                        System.out.println(rxData);
                        System.out.println(rxPkt);
                        System.out.println(rxPkt.toString());
                        System.out.println(payloadEmUTF8);
                        
                        JSONObject json = new JSONObject(payloadEmUTF8);
                        
                        ta_mensagens.append(json.getString("hora") + " \n" + json.getString("data") + "\n" +
                        json.getString("nome") + "\n" +json.getString("mensagem") + "\n\n");                
                       
                    }catch (IOException ex) {
                        Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
            }
        }.start();
    }
    
    private void enviarMensagem(){
        jLabel1.setVisible(false);
        new Thread(){
                @Override
                public void run(){
                    if(tf_nome.getText().isBlank()){
                        JOptionPane.showMessageDialog(null, "preencha o campo <nome>" );
                    }
                    else{
                        JSONObject json = new JSONObject();
                        String mensagem = tf_mensagem.getText();
                        LocalDateTime instanteDeEnvio = LocalDateTime.now();
                        String nome = tf_nome.getText();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String dataMensagem = dateFormatter.format(instanteDeEnvio);
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String horaMensagem = timeFormatter.format(instanteDeEnvio);
                        
                        json.put("nome", nome);
                        json.put("mensagem", mensagem);
                        json.put("data", dataMensagem);
                        json.put("hora", horaMensagem);
                        
                        tf_mensagem.setText("");
                        
                        try {
                            byte[] txData = json.toString().getBytes("UTF-8");
                            DatagramPacket txPkt = new DatagramPacket(txData, txData.length, group, socket.getLocalPort());
                            socket.send(txPkt);      
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
        }.start();
       
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_mensagens = new javax.swing.JTextArea();
        la_nome = new javax.swing.JLabel();
        tf_mensagem = new javax.swing.JTextField();
        la_mensagem = new javax.swing.JLabel();
        tf_nome = new javax.swing.JTextField();
        bt_sair = new javax.swing.JButton();
        la_ip = new javax.swing.JLabel();
        la_socket = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ta_mensagens.setEditable(false);
        ta_mensagens.setColumns(20);
        ta_mensagens.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ta_mensagens.setRows(5);
        jScrollPane1.setViewportView(ta_mensagens);

        la_nome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        la_nome.setText("Nome:");

        tf_mensagem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tf_mensagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_mensagemActionPerformed(evt);
            }
        });

        la_mensagem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        la_mensagem.setText("Mensagem:");

        tf_nome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        bt_sair.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bt_sair.setText("SAIR");
        bt_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_sairActionPerformed(evt);
            }
        });

        la_ip.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        la_ip.setText("IP: 224.0.0.1");

        la_socket.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        la_socket.setText("Socket: 8888");

        jLabel1.setText("*pressione enter para enviar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_sair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(la_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(la_mensagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_mensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(la_socket, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(la_ip, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(150, 150, 150)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(la_ip)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4)
                .addComponent(la_socket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(la_nome)
                    .addComponent(tf_mensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(la_mensagem)
                    .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_sair, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_sairActionPerformed
        try {
            socket.leaveGroup(group);
            socket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_sairActionPerformed

    private void tf_mensagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_mensagemActionPerformed
        enviarMensagem();
        
    }//GEN-LAST:event_tf_mensagemActionPerformed

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
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Janela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Janela(socket, group).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_sair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel la_ip;
    private javax.swing.JLabel la_mensagem;
    private javax.swing.JLabel la_nome;
    private javax.swing.JLabel la_socket;
    private javax.swing.JTextArea ta_mensagens;
    private javax.swing.JTextField tf_mensagem;
    private javax.swing.JTextField tf_nome;
    // End of variables declaration//GEN-END:variables

    private void ajustes() {
        String socketTXT = "soquete: " + String.valueOf(socket.getLocalPort());
        String IPTXT = "IP: "+group.toString().substring(1);//sem substring retorna /N.N.N.N
        la_ip.setText(IPTXT);
        System.out.println(socketTXT);
        la_socket.setText(socketTXT);
        
    }
}
