package application.gestioneinventario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import storage.FacadeDAO;
import storage.gestioneinventario.Prodotto;
import storage.gestioneinventario.ProdottoDAO;

import java.io.IOException;

@WebServlet(name="ShowProduct", value = "/ShowProduct")
public class ShowProduct extends HttpServlet {

    private FacadeDAO dao = new FacadeDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
            //get id of selected product
            int id = Integer.parseInt(req.getParameter("id"));
            //call the sql query to get the product's id

            Prodotto prodotto=dao.getProductById(id);

            if(prodotto!=null){
                req.setAttribute("prodotto", prodotto);
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/productPage.jsp");
                rd.forward(req, resp);

            }
            else {
                req.setAttribute("errorMSG", "Il prodotto con id "+id+" non è presente nel database!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);

            }


        }else {
            req.setAttribute("errorMSG","Il campo prodtto id non può essere vuoto!");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
