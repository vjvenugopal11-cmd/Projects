package chatapp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Me extends Frame implements Runnable, ActionListener {

    TextField textField;
    TextArea textArea;
    Button send;

    ServerSocket serverSocket;
    Socket socket;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Thread chat;

    Me() {

        textField = new TextField();
        textArea = new TextArea();
        send = new Button("Send");

        send.addActionListener(this);

        try {
        	
            serverSocket = new ServerSocket(12000);
            socket = serverSocket.accept();

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        
        setLayout(new BorderLayout());

       
        add(textArea, BorderLayout.CENTER);

        
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new BorderLayout());

        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(send, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setSize(500, 500);
        setTitle("Me");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String msg = textField.getText();

        textArea.append("Me : " + msg + "\n");

        textField.setText("");

        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (Exception e1) {

        }
    }

    public static void main(String[] args) {
        new Me();
    }

    @Override
    public void run() {

        while (true) {

            try {

                String msg = dataInputStream.readUTF();

                textArea.append("He/She : " + msg + "\n");

            } catch (Exception e) {

            }

        }
    }
}
