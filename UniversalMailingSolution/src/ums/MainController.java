package ums;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button mainSubmit;

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    void validate(ActionEvent event) {
        try {
            String emString=emailInput.getText();
            String passString=passwordInput.getText();
            MailServicesImplementation implementation=new MailServicesImplementation();
            
            if(implementation.validateConnection(emString, passString)){
                // load the validate.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("validate.fxml"));
            Parent validateParent = (Parent)loader.load();
            Scene validateScene = new Scene(validateParent);

            // create a new stage for the next window
            Stage nextStage = new Stage();
            nextStage.setTitle("Validation");
            nextStage.setScene(validateScene);

            // hide the main window and show the next window
            Stage primaryStage = (Stage) mainSubmit.getScene().getWindow();
            primaryStage.hide();
            nextStage.show();
            }
            else{
                Main main=new Main();
                Stage primaryStage = (Stage) mainSubmit.getScene().getWindow();
                primaryStage.hide();
                main.start(new Stage());
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
