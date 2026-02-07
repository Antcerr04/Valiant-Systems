package application.gestioneordine;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.FacadeDAO;
import storage.gestioneutente.Utente;

import java.io.IOException;

@WebServlet(name = "SendOrderServlet",urlPatterns = {"/manage-orders/send"})
public class SendOrderServlet extends HttpServlet {
    private FacadeDAO dao = new FacadeDAO();

    public void setFacadeDAO(FacadeDAO dao) {
        this.dao = dao;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente != null) { //check if user is present in the session (logged in)
            if(utente.getRuolo().equals("manager")){
                String id = request.getParameter("id");
                if(id != null) {
                    if(id.matches("^[1-9][0-9]*$")){
                        int orderID = Integer.parseInt(id);
                        try{
                            if(dao.sendOrder(orderID)){
                                Cookie cookie = new Cookie("notification", "Ordine-con-id-" + orderID + "-inviato-con-successo!");
                                cookie.setMaxAge(1);
                                cookie.setSecure(true);
                                response.addCookie(cookie);
                                response.sendRedirect(request.getContextPath()+"\\manage-orders");
                            }else{
                                request.setAttribute("errorMSG", "Ordine con id " + orderID + " non trovato o già inviato!");
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                                dispatcher.forward(request, response);
                            }
                        } catch (RuntimeException e) {
                            request.setAttribute("exception", e);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                            dispatcher.forward(request, response);
                        }

                    }else{
                        request.setAttribute("errorMSG", "Il campo id non rispetta il formato!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                        dispatcher.forward(request, response);
                    }
                }else{
                    request.setAttribute("errorMSG", "Il campo id deve essere specificato!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request, response);
                }
            }else{//user is not a Manager, show error page
                request.setAttribute("errorMSG", "Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request, response);
            }
        }else{ //user is NOT present in the session (logged out)
            response.sendRedirect(request.getContextPath()+"\\login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
