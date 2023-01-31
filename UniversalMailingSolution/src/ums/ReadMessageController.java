package ums;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;

public class ReadMessageController {
@FXML
private ListView<String> messageList;

@FXML
    private Button downloadButton;

@FXML
private TextArea messageArea;

@FXML
private Button forwardButton;

@FXML
private TextField forwardMailInput;

private Message[] messages;

private static int displayMessagesBtnClickCount=0;
private ObservableList<String> subjects=null;

@FXML
void displayRecentMessages(ActionEvent event) {
    
    displayMessagesBtnClickCount++;
    System.out.println(displayMessagesBtnClickCount);
    if(displayMessagesBtnClickCount%2==0){
        messageList.getItems().clear();
        
    }
    
        Properties props = new Properties();
    props.put("mail.store.protocol", "imaps");

    Session session = Session.getInstance(props, null);
    Store store;
    try {
        store = session.getStore();
        store.connect(MailConfig.SMPT_HOST_ADDRESS, MailConfig.getUSER_NAME_EMAIL(), MailConfig.getUSER_PASSWORD());

        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);

        int messageCount = inbox.getMessageCount();
            int recentMessageCount = 5*displayMessagesBtnClickCount; // Number of recent messages to retrieve
            int startIndex = messageCount - recentMessageCount;
            messages = inbox.getMessages(startIndex,messageCount);
            
        
        subjects = messageList.getItems();
        
        for (Message message : messages) {
            System.out.println(message.getSubject()+" ");
            subjects.add( message.getSubject());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    
}

@FXML
void displayMailContent(MouseEvent event) {
    try {
        messageArea.clear();
        int selectedIndex = messageList.getSelectionModel().getSelectedIndex();
        Message selectedMessage = messages[selectedIndex];

        Object content = selectedMessage.getContent();
        if (content instanceof Multipart) {
            Multipart mp = (Multipart) content;
            for (int i = 0; i <mp.getCount() ; i++) {
                BodyPart bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    messageArea.setText((String) bp.getContent());
                    
                }
            }
        }
        //messageArea.setText
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@FXML
private void handleDownloadButtonClick(ActionEvent event) {
    try {
        int selectedIndex = messageList.getSelectionModel().getSelectedIndex();
        Message selectedMessage = messages[selectedIndex];

        Object content = selectedMessage.getContent();
        if (content instanceof Multipart) {
            Multipart mp = (Multipart) content;
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bp.getDisposition())) {
                    // Use JavaFX's FileChooser to prompt the user to select a directory to save the attachments
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File selectedDirectory = directoryChooser.showDialog(downloadButton.getScene().getWindow());

                    File savedFile = new File(selectedDirectory, bp.getFileName());
                    try (FileOutputStream fos = new FileOutputStream(savedFile)) {
                        bp.getDataHandler().writeTo(fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Show a message dialog box to inform the user that the attachments have been downloaded successfully
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attachments Downloaded");
        alert.setHeaderText(null);
        alert.setContentText("The attachments have been downloaded successfully.");
        alert.showAndWait();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void sendEmail(String recipient, String subject, String text) throws Exception {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", MailConfig.SMPT_HOST_ADDRESS);
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(MailConfig.getUSER_NAME_EMAIL(), MailConfig.getUSER_PASSWORD());
        }
      });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(MailConfig.getUSER_NAME_EMAIL()));
    message.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse(recipient));
    message.setSubject(subject);
    message.setText(text);

    Transport.send(message);
}


@FXML
private void handleForwardButtonClick(ActionEvent event) {
    String recipient = forwardMailInput.getText();
    String subject = "Forwarded Message: " + messageList.getSelectionModel().getSelectedItem();
    String text = messageArea.getText();
    System.out.println("Subject: "+subject+" Text: " +text);
    try {
        sendEmail(recipient, subject, text);
        forwardMailInput.clear();
        forwardButton.setDisable(true);
    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error sending email");
        alert.show();
    }
    
}



}
