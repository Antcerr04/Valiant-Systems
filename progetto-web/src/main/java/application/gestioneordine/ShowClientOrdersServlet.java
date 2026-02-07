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

@WebServlet(name = "ShowClientOrdersServlet", value = "/orders")
public class ShowClientOrdersServlet extends HttpServlet {
    private FacadeDAO dao = new FacadeDAO();

    public void setFacadeDAO(FacadeDAO dao) {
        this.dao = dao;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            Utente utente = (Utente) session.getAttribute("utente");
            if ( utente != null ) { //check if utente is present in the session
                if(utente.getRuolo().equals("cliente")){ //check if utente is an istance of Cliente
                    List<Ordine> orderList = dao.getClientOrders(utente);
                    request.setAttribute("orderList", orderList);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/orderView.jsp");
                    dispatcher.forward(request, response);
                }else{//utente is not a Cliente, therefore can't access this service
                    request.setAttribute("errorMSG", "Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request, response);
                }
            }else{//utente is not present in the session
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
