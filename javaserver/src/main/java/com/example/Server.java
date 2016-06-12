package com.example;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;

    private JTextArea textArea = new JTextArea();

    public Server(){

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setVisible(true);
        this.setResizable(false);
        this.textArea.setEditable(false);
        this.add(textArea);

        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waitting to connect......");

            textArea.append("IP of my system is := " + IP.getHostAddress() + "\n");
            textArea.append("Waitting to connect......\n");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();

                System.out.println("Connected!!");

                textArea.append("Connected!!\n");

                // Transfer data
                byte[] b = new byte[1024];
                int length;

                length = in.read(b);
                String s = new String(b);
                System.out.println("[Server Said]" + s);

                textArea.append("[Server Said]" + s + "\n");

            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
