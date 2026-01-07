package model.Gestione_Utente;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ApacheEmailAdapter implements EmailService{
    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            Email email= new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);

            email.setAuthenticator(new DefaultAuthenticator("youremail@gmail.com","your passwordApp"));
            email.setSSLOnConnect(true);

            email.setFrom("youremail@gmail.com");
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);

            email.send();

        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
