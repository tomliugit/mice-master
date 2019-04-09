package util.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author sunwell
 */
public class MailAuth extends Authenticator {
    private String userName = null;
    private String password = null;

    public MailAuth(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}   
