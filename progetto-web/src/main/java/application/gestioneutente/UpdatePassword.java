package application.gestioneutente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.FacadeDAO;
import storage.gestioneutente.Utente;

import java.io.IOException;

/**
 * Servlet to Update Password
 */
@WebServlet(name = "UpdatePassword", value = "/UpdatePassword")
public class UpdatePassword extends HttpServlet {
    FacadeDAO dao = new FacadeDAO();

    public void setFaceDAO(FacadeDAO dao) {
        this.dao = dao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String passwordAttuale = req.getParameter("actualPassword");
        String newPassword = req.getParameter("registerPassword");
        String repeatNewPassword = req.getParameter("repeatPassword");

        HttpSession session = req.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        String username = utente.getUsername();

        if (!Utente.validatePassword(passwordAttuale)) {
            req.setAttribute("errorMSG", "Formato della password attuale non valida");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        Utente temp = dao.getUserByCredentials(username, passwordAttuale);
        if (temp == null) {
            req.setAttribute("errorMSG", "La password attuale non corrisponde");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        if (!Utente.validatePassword(newPassword)) {
            req.setAttribute("errorMSG", "Formato della nuova password non corretta");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        if (!Utente.validatePassword(repeatNewPassword)) {
            req.setAttribute("errorMSG", "Formato della password ripetuta non corretta");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        if (!newPassword.equals(repeatNewPassword)) {
            req.setAttribute("errorMSG", "Le password non corrispondono");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        boolean success = dao.updateUserPassword(utente.getEmail(), newPassword);
        if (success) {
            Cookie cookie = new Cookie("notification", "Password-aggiornata-con-successo");
            cookie.setMaxAge(1);
            resp.addCookie(cookie);
            resp.sendRedirect("index.jsp");
        }
        else {
            req.setAttribute("errorMSG", "Errore nel Database");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
            return;
        }


    }
}
