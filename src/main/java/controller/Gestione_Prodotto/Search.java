package controller.Gestione_Prodotto;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.gestioneinventario.Prodotto;
import model.gestioneinventario.ProdottoDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//This Servlet is used to search a product
@WebServlet(name = "Search", value = "/search")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("q") != null && !req.getParameter("q").isEmpty()) {
                String query = req.getParameter("q");
                ProdottoDAO service = new ProdottoDAO();
                List<Prodotto> prodottoList = service.doSearchByName(query);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                out.println(gson.toJson(prodottoList));
                out.flush();
            }
        } catch (Exception e) {
            req.setAttribute("exception", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/views/error.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
