package application.gestioneutente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.FacadeDAO;
import storage.gestioneutente.Utente;

import java.io.IOException;

/**
 * Servlet used to Recovery Password
 */
@WebServlet(name="Recovery",value = "/Recovery")
public class RecoveryPassword  extends HttpServlet {
    private FacadeDAO service = new FacadeDAO();

    public void setFaceDAO(FacadeDAO service) {
        this.service = service;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String emailInserita = req.getParameter("email");
        String codiceInserito = req.getParameter("codice");
        String nuovaPassword = req.getParameter("newPassword");

        //Validation email
        if(!Utente.validateEmail(emailInserita)) {
            req.setAttribute("errorMSG", "Email invalida");
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            rd.forward(req, resp);

            return;
        }
        boolean existEmail=service.isEmailPresent(emailInserita);
        if(!existEmail) {
            req.setAttribute("errorMSG", "Email non associata a nessun account");
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            rd.forward(req, resp);
            return;
        }

        //2.Get data in the session

        String codiceCorretto = (String) session.getAttribute("codiceVerifica");

        //Validation code
        if (codiceCorretto == null || !codiceCorretto.equals(codiceInserito)) {
            req.setAttribute("errorMSG", "Codice di verifica errato");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        //Validation password
        if (!Utente.validatePassword(nuovaPassword)) {
            req.setAttribute("errorMSG", "Password non corretta");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        //If is all ok
        boolean success = service.updateUserPassword(emailInserita,nuovaPassword);

        if (success) {
            session.removeAttribute("codiceVerifica");
            Cookie cookie = new Cookie("notification", "Riprstino-eseguito-con-successo.");
            cookie.setMaxAge(1);
            cookie.setSecure(true);
            resp.addCookie(cookie);
            resp.sendRedirect("index.jsp");
            return;

        } else {
            req.setAttribute("errorMSG", "Errore nel DB");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
        }
    }


}
