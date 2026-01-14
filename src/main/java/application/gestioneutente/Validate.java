package application.gestioneutente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import storage.FacadeDAO;

import java.io.IOException;

/**
 * This Servlet is used to validate user's input
 */
@WebServlet(name = "Validate", value = "/Validate")
public class Validate extends HttpServlet {
    private FacadeDAO service = new FacadeDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equals("checkUsername")) { //chiamata per il check dell'userename
            boolean usernameExist = service.isUserPresent(req.getParameter("username"));
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            if (usernameExist) {
                resp.getWriter().write("0"); //Username already exists
            } else {
                resp.getWriter().write("1"); // Username not exists
            }

        } else if (action.equals("checkEmail")) {
            String email = req.getParameter("email");
            boolean emailExist = service.isEmailPresent(email);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            if (emailExist) {
                //Email already exists
                resp.getWriter().write("0");
                //Email not exists
            } else resp.getWriter().write("1");
        }


    }
}
