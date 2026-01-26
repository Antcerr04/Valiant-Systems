package application.gestioneutente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import storage.gestioneutente.ApacheEmailAdapter;
import storage.gestioneutente.EmailService;

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
            //Generate code of 6 numbers
            String codice = String.format("%06d", new Random().nextInt(999999));

            // Save in the session for future control
            HttpSession session = request.getSession();
            session.setAttribute("codiceVerifica", codice);
            session.setAttribute("emailReset", emailDestinatario);

            //Use Adapter to Send Email
            EmailService mailer = new ApacheEmailAdapter();
            mailer.sendEmail(emailDestinatario, "Codice di Verifica Valiant Systems",
                    "Il tuo codice di sicurezza è : " + codice);

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");
        }
    }
}
