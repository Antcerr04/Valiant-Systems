package application.gestioneutente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.gestioneutente.UtenteDAO;

import java.io.IOException;

/**
 * This is a servlet to do updates
 */
@WebServlet(name = "Modifica", value = "/Modifica")
public class Modifica extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sourcce = req.getParameter("source");

        if ("reset".equals(sourcce)) {
            //1. Recupero dati dal form

            String emailInserita = req.getParameter("email");
            String codiceInserito = req.getParameter("codice");
            String nuovaPassword = req.getParameter("newPassword");

            //2. Recupero dati salvati in sessione dalla EmailServlet
            HttpSession session = req.getSession();
            String codiceCorretto = (String) session.getAttribute("codiceVerifica");

            //Controllo vaidità del codice
            if (codiceCorretto == null || !codiceCorretto.equals(codiceInserito)) {
                req.setAttribute("errorMSG","Codide di verifica errato");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            //Controllo password
            if (!isPasswordValid(nuovaPassword)) {
                req.setAttribute("errorMSG","Password non corretta");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
            }
            //Se è tutto ok aggiorno i dati
            UtenteDAO dao = new UtenteDAO();
            boolean success = dao.updatePassword(emailInserita, nuovaPassword);

            if (success) {
                session.removeAttribute("codiceVerifica");
                Cookie cookie = new Cookie("notification", "Riprstino-eseguito-con-successo.");
                cookie.setMaxAge(1);
                cookie.setSecure(true);
                resp.addCookie(cookie);
                resp.sendRedirect("index.jsp");

            } else {
                req.setAttribute("errorMSG","Errore nel DB");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }

    /**
     * Function to validate password
     * @param nuovaPassword The password to validate
     * @return true if password is valid, else return false
     */
    private boolean isPasswordValid(String nuovaPassword) {
        if (nuovaPassword == null || nuovaPassword.length() < 8) {
            return false;
        }

        boolean hashUpper = nuovaPassword.matches(".*[A-Z].*");

        boolean hasDigit = nuovaPassword.matches(".*[0-9].*");

        boolean hasSpecial = nuovaPassword.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        return hashUpper && hasDigit && hasSpecial;
    }
}
