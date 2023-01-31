package ums;

public interface MailServices {
    public boolean validateConnection(String emailString, String passString);
    public void getMessages();
}
