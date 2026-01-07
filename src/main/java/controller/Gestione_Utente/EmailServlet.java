package controller.Gestione_Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Gestione_Utente.ApacheEmailAdapter;
import model.Gestione_Utente.EmailService;

import java.io.IOException;
import java.util.Random;

/**
 * This is a Servlet used to send email
 */
@WebServlet(name = "EmailServlet", value = "/EmailServlet")
public class EmailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailDestinatario = request.getParameter("email");
        String action = request.getParameter("action");

        if ("send".equals(action)) {
            // 1. Genera codice di 6 cifre
            String codice = String.format("%06d", new Random().nextInt(999999));

            //2. Salva in sessione per il controllo futuro
            HttpSession session = request.getSession();
            session.setAttribute("codiceVerifica", codice);
            session.setAttribute("emailReset", emailDestinatario);

            //3. Usa l'Adapter per inviare
            EmailService mailer = new ApacheEmailAdapter();
            mailer.sendEmail(emailDestinatario, "Codice di Verifica Valiant Systems",
                    "Il tuo codice di sicurezza è : " + codice);

            // 4. Risposa al JavaScript
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");
        }
    }
}
