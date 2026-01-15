package application.gestionecarrello;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import storage.FacadeDAO;
import storage.gestionecarrello.Carrello;
import storage.gestioneinventario.Prodotto;
import storage.gestioneordine.Ordine;
import storage.gestioneutente.Cliente;
import storage.gestioneutente.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShoppingCartServlet", urlPatterns = {"/cart","/addToCart", "/removeFromCart"})
public class ShoppingCartServlet extends HttpServlet {
    private FacadeDAO dao = new FacadeDAO();

    public void setFaceDAO(FacadeDAO dao) {
        this.dao = dao;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            if (request.getServletPath().equals("/addToCart")) {
                if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Prodotto prodotto = dao.getProductById(id);
                    if(prodotto != null) {
                        HttpSession session = request.getSession();
                        if(session.getAttribute("carrelloList") != null) {
                            Carrello carrello = (Carrello) session.getAttribute("carrelloList");
                            carrello.addCarrelloItem(prodotto);
                        }else{
                            Carrello carrello = new Carrello();
                            carrello.addCarrelloItem(prodotto);
                            session.setAttribute("carrelloList", carrello);
                        }
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cart.jsp");
                        dispatcher.forward(request, response);
                    }else{
                        request.setAttribute("errorMSG", "Il prodotto con id '"+id+"' non è presente nel database!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                        dispatcher.forward(request, response);
                    }
                }else {
                    request.setAttribute("errorMSG", "Il campo prodotto id non può essere vuoto!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request, response);
                }
            }
            if (request.getServletPath().equals("/removeFromCart")) {
                if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Prodotto prodotto = dao.getProductById(id);
                    if(prodotto != null) {
                        HttpSession session = request.getSession();
                        Carrello carrello = (Carrello) session.getAttribute("carrelloList");
                        if (carrello != null && !carrello.getCarrelloItemList().isEmpty()) {
                            if (carrello.removeCarrelloItem(prodotto)) {
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cart.jsp");
                                dispatcher.forward(request, response);
                            } else {
                                request.setAttribute("errorMSG", "Il prodotto con id '" + id + "' non è presente nel carrello!");
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                                dispatcher.forward(request, response);
                            }
                        } else {
                            request.setAttribute("errorMSG", "Tentata rimozione con carrello vuoto!");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                            dispatcher.forward(request, response);
                        }
                    }else{
                        request.setAttribute("errorMSG", "Il prodotto con id '" + id + "' non è presente nel database!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                        dispatcher.forward(request, response);
                    }
                }else {
                    request.setAttribute("errorMSG", "Il campo prodotto id non può essere vuoto!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request, response);
                }
            }else if (request.getServletPath().equals("/cart")) {
                HttpSession session = request.getSession();
                Utente utente = (Utente) session.getAttribute("utente");
                if ( utente == null || utente instanceof Cliente ) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cart.jsp");
                        dispatcher.forward(request, response);
                }else{
                    request.setAttribute("errorMSG", "Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request, response);
                }
            }

        }catch (Exception e) {
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
