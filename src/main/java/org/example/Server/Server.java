package org.example.Server;

import org.example.Client.Client;
import org.example.Client.ClientObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server{
/*    private ServerSocket serverSocket;
    private Socket socket;
    private static Server server;
    private List<Client> clients = new ArrayList<>();

    private Server() throws IOException {
        serverSocket = new ServerSocket(3000);
    }
    public static Server getInstance() throws IOException {
        return server!= null? server:(server= new Server());
    }

    public void creatSocket(){
        while (!serverSocket.isClosed()){
            try {
                socket = serverSocket.accept();
                Client client = new Client(socket, this);
                clients.add(client);
                System.out.println("client accepted");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void addClient(Client client) {
        clients.add(client);
    }

    public void broadcastMessage(String message) {
        for (Client client : clients) {
            client.sendMessage(message);
        }
    }*/
private static Server instance;
    private final ServerSocket serverSocket;
    private  List<Client> clients;
    private final List<ClientObserver> observers;

    private Server() throws IOException {
        serverSocket = new ServerSocket(3000);
        clients = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static  Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    /*public void start() {
        new Thread(this::acceptClients).start();
    }

    private void acceptClients() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();

                // Assuming the client sends the username as the first message
                String username = readUsername(socket.getInputStream());

                Client client = new Client(socket,clients);
                clients.add(client);
                System.out.println("Client " + username + " accepted");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }*/
    /*private String readUsername(InputStream inputStream) throws IOException {

        DataInputStream dataInputStream = new DataInputStream(inputStream);

        // Assuming the username is sent as the first message
        String username = dataInputStream.readUTF();

        return username;
    }*/
    /*public void broadcastMessage(String message) {
        for (Client client : clients) {
            client.sendMessage(message);
        }
    }*/

    /*public void addClient(Client client) {
        clients.add(client);
    }

    public void addObserver(ClientObserver observer) {
        observers.add(observer);
    }*/

   /* public static void main(String[] args) throws IOException {
        *//*Server server = Server.getInstance();
        server.start();*//*
        Server server = Server.getInstance();
        ServerController serverController = new ServerController();
        //server.addObserver(serverController);
        //server.start();
    }*/

    public void creatSocket() {
        while (!serverSocket.isClosed()){
            try{
                Socket socket = serverSocket.accept();
                Client clientHandler = new Client(socket, clients);
                clients.add(clientHandler);
                System.out.println("client socket accepted "+socket.toString());
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
