package application.gestioneinventario;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import storage.FacadeDAO;
import storage.gestioneinventario.Prodotto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet used to Search a product
 */
@WebServlet(name = "Search", value = "/search")
public class Search extends HttpServlet {
    private FacadeDAO service = new FacadeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("q") != null && !req.getParameter("q").isEmpty()) {
                String query = req.getParameter("q");
                List<Prodotto> prodottoList = service.searchProduct(query);
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
