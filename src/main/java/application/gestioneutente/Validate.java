package application.gestioneutente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import storage.gestioneutente.UtenteDAO;

import java.io.IOException;

/**
 * This Servlet is used to validate user's input
 */
@WebServlet(name = "Validate", value = "/Validate")
public class Validate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UtenteDAO service = new UtenteDAO();
        if (action.equals("checkUsername")) { //chiamata per il check dell'userename
            boolean usernameExist = service.existUsername(req.getParameter("username"));
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            if (usernameExist) {
                resp.getWriter().write("0"); //Username giò esistente
            } else {
                resp.getWriter().write("1"); // Username disponibile
            }

        } else if (action.equals("checkEmail")) {
            String email = req.getParameter("email");
            boolean emailExist = service.existEmail(email);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            if (emailExist) {
                //Email esistente
                resp.getWriter().write("0");
                //Email libera
            } else resp.getWriter().write("1");
        }


    }
}
