package controller.Gestione_Utente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

//This Servlet is used to exit from account
@WebServlet(name = "Logout", value = "/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            if (session.getAttribute("utente") != null) {
                session.invalidate();
                Cookie cookie = new Cookie("notification", "Logout-effettuato-con-successo.-A-presto!");
                cookie.setMaxAge(1);
                cookie.setSecure(true);
                resp.addCookie(cookie);
                resp.sendRedirect("index.jsp");
            } else {
                req.setAttribute("errorMSG", "Tentato logout senza aver prima effettuato l'accesso.");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/results/error.jsp");
                rd.forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("exception", e);
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/results/error.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
