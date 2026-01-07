package model.Gestione_Utente;

public interface EmailService {
    void sendEmail(String to,String subject, String message);
}
