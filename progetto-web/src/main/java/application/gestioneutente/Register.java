package application.gestioneutente;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import storage.FacadeDAO;
import storage.gestioneutente.*;

import java.io.IOException;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {

    private FacadeDAO dao = new FacadeDAO();

    // Setter per Dependency Injection nei test
    public void setFacadeDAO(FacadeDAO dao) {
        this.dao = dao;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Extraction and validation indirizzo
            Indirizzo indirizzo = extractIndirizzo(request);
            if (indirizzo == null) {
                forwardWithError(request, response, "Dati indirizzo non validi o mancanti.", "register.jsp");
                return;
            }

            // 2. Get user parameters
            String nome = request.getParameter("name");
            String cognome = request.getParameter("surname");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Validation
            if (!validateInput(nome, cognome, email, username, password, indirizzo)) {
                forwardWithError(request, response, "Dati non validi o mancanti.", "WEB-INF/results/error.jsp");
                return;
            }

            // Verify if email is already exists
            if (dao.isEmailPresent(email)) {
                forwardWithError(request, response, "Email già in uso.", "WEB-INF/results/error.jsp");
                return;
            }
            //Verify if user is already exists
            if (dao.isUserPresent(username)) {
                forwardWithError(request, response, "Username già in uso.", "WEB-INF/results/error.jsp");
                return;
            }

            // Saving client
            Cliente cliente = new Cliente(nome, cognome, username, email, password, indirizzo);
            cliente.setPasswordHash(password);

            dao.saveClient(cliente);

            // Success
            sendSuccessNotification(response);

        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/WEB-INF/results/error.jsp").forward(request, response);
        }
    }

    private Indirizzo extractIndirizzo(HttpServletRequest request) {
        try {
            String via = request.getParameter("via");
            String città = request.getParameter("city");
            String provincia = request.getParameter("provincia");
            String regione = request.getParameter("regione");

            String houseNumStr = request.getParameter("house-number");
            String capStr = request.getParameter("cap");

            if (houseNumStr == null || capStr == null) return null;

            int numeroCivico = Integer.parseInt(houseNumStr);
            int cap = Integer.parseInt(capStr);

            return new Indirizzo(via, numeroCivico, cap, regione, provincia, città);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean validateInput(String n, String c, String e, String u, String p, Indirizzo ind) {
        return Utente.validateNome(n) &&
                Utente.validateCognome(c) &&
                Utente.validateEmail(e) &&
                Utente.validateUsername(u) &&
                Utente.validatePassword(p) &&
                Indirizzo.validateVia(ind.getVia()) &&
                Indirizzo.validateNumCiv(ind.getNumCiv()) &&
                Indirizzo.validateRegione(ind.getRegione()) &&
                Indirizzo.validateProvincia(ind.getRegione(),ind.getProvincia()) &&
                Indirizzo.validateCittà(ind.getCittà()) &&
                Indirizzo.validateCap(ind.getCap());
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse res, String msg, String path)
            throws ServletException, IOException {
        req.setAttribute("errorMSG", msg);
        req.getRequestDispatcher(path).forward(req, res);
    }

    private void sendSuccessNotification(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("notification", "Registrazione-avvenuta-con-successo!");
        cookie.setMaxAge(1); // Aumentato a 5 secondi per sicurezza
        cookie.setSecure(true);
        response.addCookie(cookie);
        response.sendRedirect("login.jsp");
    }
}