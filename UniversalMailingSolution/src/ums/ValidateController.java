package ums;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ValidateController {
    @FXML
    private Button composeBtn;

    @FXML
    private Button recieveBtn;

    @FXML
    private Button sendBtn;

    @FXML
    void displayMessages(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("readMessages.fxml"));
            Parent messagesParent = (Parent)loader.load();
            Scene messagesScene = new Scene(messagesParent);

            // create a new stage for the next window
            Stage nextStage = new Stage();
            nextStage.setTitle("Validation");
            nextStage.setScene(messagesScene);

            // hide the main window and show the next window
            Stage primaryStage = (Stage) recieveBtn.getScene().getWindow();
            primaryStage.hide();
            nextStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        
    }
}
