package application.gestionecarrello;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import storage.FacadeDAO;
import storage.gestionecarrello.Carrello;
import storage.gestioneinventario.Prodotto;

import java.io.IOException;

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
                        response.sendRedirect("cart.jsp");
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
                                response.sendRedirect("cart.jsp");
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
