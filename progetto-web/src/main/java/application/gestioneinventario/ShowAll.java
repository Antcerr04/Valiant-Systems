package application.gestioneinventario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import storage.gestioneutente.Manager;
import storage.gestioneutente.Utente;
import storage.gestioneinventario.Prodotto;
import storage.gestioneinventario.ProdottoDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet used to show all product or to show all product in sale or to show manager view
 */
@WebServlet(name = "ShowAllServlet", urlPatterns = {"/show", "/sale", "/manage"})
public class ShowAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //To show all products
            if (req.getServletPath().equals("/show")) {
                ProdottoDAO service = new ProdottoDAO();
                List<Prodotto> prodottoList;
                if (req.getParameter("cpu") == null && req.getParameter("gpu") == null) {
                    prodottoList = service.doRetrieveAll();
                } else {
                    if (!req.getParameter("cpu").isEmpty() && !req.getParameter("gpu").isEmpty()) {
                        //Search By filter
                        prodottoList = service.doFilter(req.getParameter("cpu"), req.getParameter("gpu"));
                        req.setAttribute("cpuValue", req.getParameter("cpu"));
                        req.setAttribute("gpuValue", req.getParameter("gpu"));

                    } else {
                        prodottoList = service.doRetrieveAll(); //fallback in case of removal of required fields through html inspections
                    }
                }
                req.setAttribute("prodottoList", prodottoList);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/showAll.jsp");
                dispatcher.forward(req, resp);
            }
            if (req.getServletPath().equals("/sale")) {
                //To show product in sale
                ProdottoDAO service = new ProdottoDAO();
                List<Prodotto> prodottoList = service.doRetrieveAllAvailableSales();
                req.setAttribute("prodottoList", prodottoList);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/showSale.jsp");
                dispatcher.forward(req, resp);
            }
            if (req.getServletPath().equals("/manage")) {
                //To show the manager view
                HttpSession session = req.getSession();
                Utente utente = (Utente) session.getAttribute("utente");
                if (utente == null) {
                    resp.sendRedirect("login.jsp");
                } else {
                    if(utente.getRuolo()=="manager"){
                        ProdottoDAO service = new ProdottoDAO();
                        List<Prodotto> prodottoList = service.doRetrieveAll();
                        req.setAttribute("prodottoList", prodottoList);
                        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/adminView.jsp");
                        dispatcher.forward(req, resp);
                    } else {
                        req.setAttribute("errorMSG", "Accesso negato. La risorsa richiesta richiede privilegi di amministratore");
                        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                        dispatcher.forward(req, resp);
                    }
                }
            }
        } catch (Exception e) {
            req.setAttribute("exception", e);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
