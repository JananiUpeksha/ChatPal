package org.example.Controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.ClientObserver;
import org.example.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ServerController implements Initializable,ClientObserver {
    public AnchorPane rootNode;
    //public  JFXTextArea txtArea1;

    public Label lblName;
    public ScrollPane sp;
    private Server server;
    private StringProperty textAreaContent = new SimpleStringProperty();

    public void initialize(URL url, ResourceBundle resourceBundle) {
       // System.out.println("ServerController initialize called");
        System.out.println("ServerController initialize called");

        // Check if txtArea1 is properly initialized and references the correct control
        System.out.println("reference: " + sp);
        sp.setStyle("-fx-background-color: #f4e1c1;");
        lblName.setText("Server");
        lblName.setStyle("-fx-font-family: 'DejaVuMathTeXGyre-Regular'; -fx-font-size: 22.0;");

        receiveMessage("Sever Starting..");
        new Thread(() -> {
            try {
                server = Server.getInstance();
                server.creatSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        receiveMessage("Sever Running..");
        receiveMessage("Waiting for User..");
        // Example: Simulate updates in the background
        simulateBackgroundUpdates();
    }

    // Custom method to simulate background updates
    private void simulateBackgroundUpdates() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update text area content
                textAreaContent.set(textAreaContent.get() + "Iteration " + i + "\n");
            }
        }).start();
    }
    public  void receiveMessage(String msgFromClient) {
        System.out.println("Received message: " + msgFromClient);
        Text text = new Text(msgFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color:#ce93d8; -fx-font-weight: bold; -fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0, 0, 0));
        Region separator = new Region();
        separator.setMinHeight(6);

        Platform.runLater(() -> {
            VBox existingContent = (VBox) sp.getContent();

            // If it's the first message, create a new VBox
            if (existingContent == null) {
                System.out.println("Creating a new VBox");
                existingContent = new VBox();
                sp.setContent(existingContent);
            }

           /* // Add the new message to the VBox
            existingContent.getChildren().add(textFlow);*/
            // Add the new message and an empty line to the VBox
            existingContent.getChildren().addAll(textFlow, separator);
            System.out.println("Added message to VBox");

            // Scroll to the bottom to show the latest message
            sp.setVvalue(1.0);
            System.out.println("Scrolled to the bottom");
        });
    }

    @Override
    public void onClientJoin(String username) {
        Platform.runLater(() -> {
            sp.snappedLeftInset();
        });
    }

    /*private void acceptClients() {
        ServerSocket serverSocket;
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                String username = readUsername(socket.getInputStream());

                Client client = new Client(socket, this);
                clients.add(client);

                // Notify observers (ServerController) that a new client joined
                observers.forEach(observer -> observer.onClientJoin(username));

                System.out.println("Client " + username + " accepted");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/

}
