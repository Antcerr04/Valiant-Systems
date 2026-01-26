package application.gestioneinventario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.FacadeDAO;
import storage.gestioneutente.Manager;
import storage.gestioneutente.Utente;

import java.io.IOException;

/**
 * Servlet to delete Product
 */
@WebServlet(name = "DeleteProduct", value = "/delete")
public class DeleteProduct extends HttpServlet {
    private FacadeDAO service = new FacadeDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Get utente in the session
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente instanceof Manager) {
            //If utente is Manager
            if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
                int id = Integer.parseInt(req.getParameter("id"));

                if (service.deleteProduct(id)) {
                    Cookie cookie = new Cookie("notification", "Prodotto-con-id-" + id + "-eliminato-con-successo!");
                    cookie.setMaxAge(1);
                    cookie.setSecure(true);
                    resp.addCookie(cookie);
                    resp.sendRedirect("manage");
                } else {
                    req.setAttribute("errorMSG", "Il campo prodotto id non può essere vuoto!");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(req, resp);
                }
            } else {
                req.setAttribute("errorMSG", "Accesso negato. La risorsa richiesta richiede privilegi di amministratore.");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
