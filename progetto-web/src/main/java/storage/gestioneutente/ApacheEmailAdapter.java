package storage.gestioneutente;


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

            email.setAuthenticator(new DefaultAuthenticator("antoniocerrone132@gmail.com","bzfw xiuj yckl szkz"));
            email.setSSLOnConnect(true);

            email.setFrom("antoniocerrone132@gmail.com");
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);

            email.send();

        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
