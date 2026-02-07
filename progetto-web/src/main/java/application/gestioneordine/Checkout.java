package application.gestioneordine;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import storage.FacadeDAO;
import storage.gestionecarrello.Carrello;
import storage.gestioneutente.Indirizzo;
import storage.gestioneutente.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Checkout", value = "/checkout")
public class Checkout extends HttpServlet {
    private FacadeDAO dao = new FacadeDAO();

    public void setFacadeDAO(FacadeDAO dao) {
        this.dao = dao;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente != null){ //check if user is present in the session (logged in)
            if(utente.getRuolo().equals("cliente")){//check if user is a Cliente
                Indirizzo indirizzo = utente.getIndirizzo();
                if(indirizzo != null){//check if indirizzo is present (indirizzo should always be present, if it happens, there is an error in the code)
                    Carrello carrello = (Carrello) session.getAttribute("carrelloList");
                    if(carrello != null && !carrello.isEmpty()){//check if carrello is present and is not empty
                        double total = 0;
                        total = carrello.getCartTotal();
                        if(utente.getSaldo() >= total){//check if cliente has enough funds to make the order
                            try{
                                List<String> checkoutResult = dao.checkout(utente, carrello);
                                session.removeAttribute("carrelloList");
                                request.setAttribute("checkoutResult", checkoutResult);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/checkoutResult.jsp");
                                dispatcher.forward(request, response);
                            }catch(RuntimeException re){
                                request.setAttribute("exception", re);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                                dispatcher.forward(request, response);
                            }
                        }else {//cliente does not have enough funds for the order, remove cart from session and show error page
                            session.removeAttribute("carrelloList");
                            request.setAttribute("errorMSG", "Saldo insufficiente! saldo: "+(Math.round(utente.getSaldo() * 100) / 100.0)+"€, ordine: "+total+"€");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                            dispatcher.forward(request, response);
                        }

                    }else {//carrello not present or it does not contain any items, show error page
                        request.setAttribute("errorMSG", "Tentato checkout con carrello vuoto!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                        dispatcher.forward(request, response);
                    }
                }else{//indirizzo is not present, show error page
                    request.setAttribute("errorMSG", "Errore! Operazione non completata a causa di mancanza dell'indirizzo.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(request, response);
                }
            }else{ //user is not a cliente, show error page
                request.setAttribute("errorMSG", "Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request, response);
            }
        }else{ //user is NOT present in the session (logged out)
            response.sendRedirect("login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
