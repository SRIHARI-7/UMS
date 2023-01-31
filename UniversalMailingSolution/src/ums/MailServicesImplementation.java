package ums;

import java.util.Properties;
import javax.mail.Transport;
import javax.mail.*;

public class MailServicesImplementation implements MailServices{
    

    @Override
    public boolean validateConnection(String emailString, String passString) {
        // Set email server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", MailConfig.SMPT_HOST_ADDRESS);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", MailConfig.SMPT_HOST_ADDRESS);
        

        // Create a new session
        Session session = Session.getInstance(props, null);
        MailConfig.setUSER_NAME_EMAIL(emailString);
        MailConfig.setUSER_PASSWORD(passString);
        try {
            // Create a new transport
            Transport transport = session.getTransport("smtp");

            // Connect to the email server
            transport.connect(emailString, passString);

            // Check if the email and password are valid
            if (transport.isConnected()) {
                return true;
            } 
            // Close the transport connection
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    

    @Override
    public void getMessages() {
        
        
    }
    
}
