package application.gestioneordine;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import storage.FacadeDAO;
import storage.gestioneordine.Ordine;
import storage.gestioneutente.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageOrdersServlet",urlPatterns = {"/manage-orders"})
public class ManageOrdersServlet extends HttpServlet {
    private FacadeDAO dao = new FacadeDAO();

    public void setFacadeDAO(FacadeDAO dao) {
        this.dao = dao;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente != null){ //check if user is present in the session (logged in)
            if(utente.getRuolo().equals("manager")){//check if user is a Manager
                try{
                    List<Ordine> orderList = dao.getAllUnshippedOrders();//retrieve all unshipped orders
                    request.setAttribute("orderList", orderList);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/manageOrders.jsp");
                    dispatcher.forward(request, response);
                } catch (RuntimeException e) {
                    request.setAttribute("exception", e);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request, response);
                }
            }else{//user is not a Manager, show error page
                request.setAttribute("errorMSG", "Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request, response);
            }
        }else{ //user is NOT present in the session (logged out)
            response.sendRedirect("login.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
