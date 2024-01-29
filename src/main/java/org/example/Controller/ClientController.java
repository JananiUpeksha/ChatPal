package org.example.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Base64;

import javafx.application.Platform;

public class ClientController {
    public AnchorPane rootNode;
    public JFXTextArea txtArea1;
    public JFXTextArea txtArea2;
    public Label lblName;
    public Button btnSend;
    public ScrollPane sp;
    public VBox vbox;
    public JFXButton btnemoji;
    public JFXButton btnImage;
    private String clientPassword;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String clientName;
    private ServerController serverController;
    public void setServerController(ServerController serverController){
        this.serverController = serverController;

    }

    public void initialize(){
        lblName.setText(clientName);
        new Thread(() -> {
            try{
                socket = new Socket("localhost", 3000);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("Client connected");
                //serverController.receiveMessage(clientName+" joined.");
                if (serverController != null) {
                    Platform.runLater(() -> {
                        System.out.println("Sending message from client: " + clientName + " joined.");
                        serverController.receiveMessage(clientName + " joined.");
                    });
                }

                while (socket.isConnected()){
                    String receivingMsg = dataInputStream.readUTF();
                    //serverController.receiveMessage(receivingMsg);
                    /*Platform.runLater(() -> {
                        serverController.receiveMessage(receivingMsg);
                    });*/
                    receivingMessage(receivingMsg,ClientController.this.vbox);
                }
                /*while (!socket.isClosed() && socket.isConnected()) {
                    String receivingMsg = dataInputStream.readUTF();
                    Platform.runLater(() -> {
                        serverController.receiveMessage(receivingMsg);
                    });
                }*/
            }catch (IOException e){
                System.err.println("Error connecting to the server: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
        this.vbox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sp.setVvalue((Double) newValue);
            }
        });

        //emoji();
    }

    /*private void emoji() {
    }
*/
    public void btnSendOnAction(ActionEvent actionEvent) {
        String msgToSend = txtArea1.getText(); // Assuming the message is in txtArea1

        if (!msgToSend.isEmpty()) {
            // You may add additional checks or processing logic here if needed

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT); // Adjust alignment as needed
            hBox.setPadding(new Insets(5, 10, 5, 10));

            Text text = new Text(msgToSend);
            text.setStyle("-fx-font-size: 14");
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-color:  #ce93d8; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));

            hBox.getChildren().add(textFlow);

            try {
                dataOutputStream.writeUTF(clientName + "-" + msgToSend);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            vbox.getChildren().add(hBox);
            txtArea1.clear();
        }
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public void setClientPassword(String password) {
        this.clientPassword = password;
    }

    public void shutdown() {
       // serverController.receiveMessage(clientName+" left.");
        if (serverController != null) {
            serverController.receiveMessage(clientName + " left.");
        }
    }
    public void receivingMessage(String msg, VBox vbox){
       /* Platform.runLater(() -> {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);

            if (msg.startsWith("Image:")) {
                // Extract image data from the message
                String encodedImage = msg.substring("Image:".length());
                byte[] imageData = Base64.getDecoder().decode(encodedImage);
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageData)));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);

                HBox imageHbox = new HBox(imageView);
                imageHbox.setStyle("-fx-background-color: #ea80fc; -fx-background-radius: 15; -fx-padding: 20px 5px;");

                Text senderText = new Text(clientName + ": ");
                senderText.setStyle("-fx-font-size: 14");
                TextFlow senderTextFlow = new TextFlow(senderText);

                senderTextFlow.setStyle("-fx-background-color: #ea80fc; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
                senderTextFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().add(imageHbox);
            } else {
                // Handle regular text messages
                Text text = new Text(msg);
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: #ea80fc; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().add(textFlow);
            }

            vbox.getChildren().add(hBox);
        });*/
        Platform.runLater(() -> {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);

            if (msg.startsWith("Image:")) {
                // Extract image data from the message
                /*String encodedImage = msg.substring("Image:".length());
                byte[] imageData = Base64.getDecoder().decode(encodedImage);
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageData)));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);

                HBox imageHbox = new HBox(imageView);
                imageHbox.setStyle("-fx-background-color: #ea80fc; -fx-background-radius: 15; -fx-padding: 20px 5px;");

                Text senderText = new Text(clientName + ": ");
                senderText.setStyle("-fx-font-size: 14");
                TextFlow senderTextFlow = new TextFlow(senderText);

                senderTextFlow.setStyle("-fx-background-color: #ea80fc; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
                senderTextFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().addAll(senderTextFlow, imageHbox);*/
                String encodedImage = msg.substring("Image:".length());
                byte[] imageData = Base64.getDecoder().decode(encodedImage);
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageData)));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);

                HBox imageHbox = new HBox(imageView);
                imageHbox.setStyle("-fx-background-color: #ea80fc; -fx-background-radius: 15; -fx-padding: 20px 5px;");

                Text senderText = new Text(clientName + ": ");
                senderText.setStyle("-fx-font-size: 14");
                TextFlow senderTextFlow = new TextFlow(senderText);

                senderTextFlow.setStyle("-fx-background-color: #ea80fc; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
                senderTextFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().addAll(senderTextFlow, imageHbox);
            } else {
                // Handle regular text messages
                Text text = new Text(msg);
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: #ea80fc; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().add(textFlow);
            }

            vbox.getChildren().add(hBox);
        });
    }


    public void imageOnAction(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                byte[] imageData = Files.readAllBytes(file.toPath());
                String encodedImage = Base64.getEncoder().encodeToString(imageData);
                String message = "Image:" + encodedImage;

                // Pass the sender's name to the sendMessage method
                sendMessage(message, new VBox(createImageView(file)), clientName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private HBox createImageView(File file) {
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        HBox imageHbox = new HBox(imageView);
        imageHbox.setStyle("-fx-background-color: #ce93d8; -fx-background-radius:15; -fx-alignment: center; -fx-padding: 20px 5px;");

        return imageHbox;
    }

    public void sendMessage(String message, VBox vBox, String senderName) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Platform.runLater(() -> {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT); // Adjust alignment as needed
            hBox.setPadding(new Insets(5, 10, 5, 10));

            if (message.startsWith("Image:")) {
                // Extract image data from the message
                String encodedImage = message.substring("Image:".length());
                byte[] imageData = Base64.getDecoder().decode(encodedImage);
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageData)));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);

                HBox imageHbox = new HBox(imageView);
                imageHbox.setStyle("-fx-background-color: #ce93d8; -fx-background-radius:15; -fx-alignment: center; -fx-padding: 20px 5px;");

                hBox.getChildren().add(imageHbox);
            } else {
                // Handle regular text messages
                Text text = new Text(this.clientName + ": " + message);
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: #ce93d8; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().add(textFlow);
            }

            vbox.getChildren().add(hBox);
        });*/
        Platform.runLater(() -> {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT); // Adjust alignment as needed
            hBox.setPadding(new Insets(5, 10, 5, 10));

            if (message.startsWith("Image:")) {
                // Extract image data from the message
                String encodedImage = message.substring("Image:".length());
                byte[] imageData = Base64.getDecoder().decode(encodedImage);
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageData)));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);

                HBox imageHbox = new HBox(imageView);
                imageHbox.setStyle("-fx-background-color: #ce93d8; -fx-background-radius:15; -fx-alignment: center; -fx-padding: 20px 5px;");

                hBox.getChildren().add(imageHbox);
            } else {
                // Handle regular text messages
                Text text = new Text(senderName + ": " + message);
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: #ce93d8; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().add(textFlow);
            }

            vbox.getChildren().add(hBox);
        });
    }

    public void emojiOnAction(MouseEvent mouseEvent) {
    }
}
