package application.gestioneutente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.FacadeDAO;
import storage.gestioneutente.Cliente;


import java.io.IOException;

/**
 * Servlet to delete Client
 */
@WebServlet(name = "Delete", value = "/Delete")
public class Delete extends HttpServlet {
    private FacadeDAO dao = new FacadeDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get Session
        HttpSession session = req.getSession();

        //Get user
        Cliente cliente = (Cliente) session.getAttribute("utente");

        if (cliente != null) {
            boolean success = dao.deleteClient(cliente.getEmail());

            if (success) {
                //Disconnect Client
                session.removeAttribute("utente");
                session.invalidate();
                //Set cookie to show notification
                Cookie cookie = new Cookie("notification", "Eliminazione-avvenuta-con-successo");
                cookie.setMaxAge(1);
                resp.addCookie(cookie);
                resp.sendRedirect("index.jsp");
                return;
            }

        } else {
            req.setAttribute("errorMSG", "Errore durante l'eliminazione dell'account");
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            rd.forward(req, resp);
            return;
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
