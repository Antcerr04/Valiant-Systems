package application.gestioneinventario;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import storage.FacadeDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//This Servlet is used to add cpu and gpu in select element within showAll.jsp
@WebServlet(name = "ShowAllFilter", urlPatterns = {"/getCPU", "/getGPU"})
public class ShowAllFilter extends HttpServlet {
    private FacadeDAO service = new FacadeDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getServletPath().equals("/getCPU")) {
                List<String> cpuList = service.getAllProductCPU();
                PrintWriter writer = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                writer.print(gson.toJson(cpuList));
                writer.flush();
            }
            if (req.getServletPath().equals("/getGPU")) {
                if (req.getParameter("cpu") != null && !req.getParameter("cpu").isEmpty()) {
                    List<String> gpuList = service.getAllProductGPU(req.getParameter("cpu"));
                    PrintWriter writer = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    Gson gson = new Gson();
                    writer.print(gson.toJson(gpuList));
                    writer.flush();
                }
            }
        } catch (Exception e) {
            req.setAttribute("exception", e);
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
