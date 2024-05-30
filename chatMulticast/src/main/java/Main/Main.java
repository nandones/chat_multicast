package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        
        try {
            
            InetAddress group = InetAddress.getByName("224.0.0.1");//IP multicast
            //Inet adress eh o tipo de variavel p/ IP
            MulticastSocket socket = new MulticastSocket(8888);//socket usada para o multicast
            socket.joinGroup(group);//socket responde ao grupo multicast desse IP

            
            Janela j = new Janela(socket, group);
            j.setVisible(true);
            j.setLocationRelativeTo(null);
            
            //ReceiverThread receiverThread = new ReceiverThread(group, socket);
            //receiverThread.start();

            //SenderThread senderThread = new SenderThread(group, socket);
            //senderThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}