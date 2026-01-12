package storage.gestioneutente;

public interface EmailService {
    void sendEmail(String to,String subject, String message);
}
