package ums;

public final class MailConfig {
    
    // Recipient's email ID needs to be mentioned.
    private static String TO_EMAIL_ADDRESS;

    // Sender's email ID needs to be mentioned
    private static String FROM_EMAIL_ADDRESS;

    // Email address for Login
    private static String USER_NAME_EMAIL;

    public static String getTO_EMAIL_ADDRESS() {
        return TO_EMAIL_ADDRESS;
    }

    public static void setTO_EMAIL_ADDRESS(String tO_EMAIL_ADDRESS) {
        TO_EMAIL_ADDRESS = tO_EMAIL_ADDRESS;
    }

    public static String getFROM_EMAIL_ADDRESS() {
        return FROM_EMAIL_ADDRESS;
    }

    public static void setFROM_EMAIL_ADDRESS(String fROM_EMAIL_ADDRESS) {
        FROM_EMAIL_ADDRESS = fROM_EMAIL_ADDRESS;
    }

    public static String getUSER_NAME_EMAIL() {
        return USER_NAME_EMAIL;
    }

    public static void setUSER_NAME_EMAIL(String uSER_NAME_EMAIL) {
        USER_NAME_EMAIL = uSER_NAME_EMAIL;
    }

    public static String getUSER_PASSWORD() {
        return USER_PASSWORD;
    }

    public static void setUSER_PASSWORD(String uSER_PASSWORD) {
        USER_PASSWORD = uSER_PASSWORD;
    }

    // Email password for Login
    private static String USER_PASSWORD;

   

    // Set SMTP Server Address - GMAIL, OUTLOOK, YAHOO or ZOHO
    public static String SMPT_HOST_ADDRESS = MailServers.GMAIL.getAddress();

    // Server name - gets reflected in email message Subject and body
    public static String SMPT_HOST_NAME = MailServers.GMAIL.getName();
}
