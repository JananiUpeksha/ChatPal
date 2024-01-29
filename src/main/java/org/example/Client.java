package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Client {
    /*private final Socket socket;
    private final Server server;
    //private final String username;

    public Client(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        //this.username = username;
        server.addClient(this);
    }

    public void sendMessage(String message) {
        try {
            // Get the OutputStream from the Socket
            try (OutputStream outputStream = socket.getOutputStream()) {

                // Convert the message to bytes and send it
                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                outputStream.write(messageBytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            // Handle the exception (e.g., log it)
            e.printStackTrace();
        }
    }

   *//* public void joinServer() {
        // Some logic for handling the join process
        server.broadcastMessage(username + " joined the server!");

    }*//*
*/
    private  Socket socket;
    //private final Server server;
    //private final String username;
    private List<Client> clients;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String msg = "";

    public Client(Socket socket,List<Client> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (socket.isConnected()) {
                        msg = dataInputStream.readUTF();
                        for (Client clientHandler : clients) {
                            if (clientHandler.socket.getPort() != socket.getPort()) {
                                clientHandler.dataOutputStream.writeUTF(msg);
                                clientHandler.dataOutputStream.flush();
                            }
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*private void sendUsername(String username) {
        try {
            // Use DataOutputStream to send the username
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(username);
            dataOutputStream.flush();
        } catch (IOException e) {
            // Handle the exception (e.g., log it)
            e.printStackTrace();
        }
    }*/

    /*public void sendMessage(String message) {
        try {
            // Get the OutputStream from the Socket
            try (OutputStream outputStream = socket.getOutputStream()) {

                // Convert the message to bytes and send it
                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                outputStream.write(messageBytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            // Handle the exception (e.g., log it)
            e.printStackTrace();
        }
    }*/
}
