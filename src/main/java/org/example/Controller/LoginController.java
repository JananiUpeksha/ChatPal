package org.example.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField txtUserName;
    public AnchorPane rootNode;
    public TextField txtPassword;
    public JFXButton btnLogin;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        /*if (!txtUserName.getText().isEmpty() && txtUserName.getText().matches("[A-Za-z0-9]+") && !txtPassword.getText().isEmpty()) {
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Client1.fxml"));

            // You don't need to set the controller here, as it's already specified in the FXML file.
            AnchorPane rootnode = fxmlLoader.load();
            ClientController controller = fxmlLoader.getController();
            controller.setClientName(txtUserName.getText());
            controller.setClientPassword(txtPassword.getText());

            primaryStage.setScene(new Scene(rootnode));
            primaryStage.setTitle(txtUserName.getText());
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.setOnCloseRequest(windowEvent -> {
                // Assuming you have a shutdown method in Client1Controller
                //((ClientController) fxmlLoader.getController()).shutdown();
                ((ClientController) fxmlLoader.getController()).shutdown();

            });
            primaryStage.show();

            txtUserName.clear();
            txtPassword.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter your name and password").show();
        }*/
        if (!txtUserName.getText().isEmpty() && txtUserName.getText().matches("[A-Za-z0-9]+") && !txtPassword.getText().isEmpty()) {
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Client1.fxml"));

            // Load the FXML file and get the controller instance
            AnchorPane rootnode = fxmlLoader.load();
            ClientController controller = fxmlLoader.getController();

            // Set the client name and password
            controller.setClientName(txtUserName.getText());
            controller.setClientPassword(txtPassword.getText());

            primaryStage.setScene(new Scene(rootnode));
            primaryStage.setTitle(txtUserName.getText());
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.setOnCloseRequest(windowEvent -> {
                // Assuming you have a shutdown method in ClientController
                controller.shutdown();
            });
            primaryStage.show();

            txtUserName.clear();
            txtPassword.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter your name and password").show();
        }

    }

    public void btnsigninOnAction(ActionEvent actionEvent) {
    }
}
