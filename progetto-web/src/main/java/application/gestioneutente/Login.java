package application.gestioneutente;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import storage.FacadeDAO;
import storage.gestioneutente.*;

import java.io.IOException;

/**
 * This is a Servlet to do logins
 */
@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    private FacadeDAO dao = new FacadeDAO();

    public void setFaceDAO(FacadeDAO dao) {
        this.dao = dao;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Take parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Sever-side validation
            if (!Utente.validateUsername(username) ||
                    !Utente.validatePassword(password)) {
                request.setAttribute("errorMSG", "Username o password non validi.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Access at the data

            Utente utente = dao.getUserByCredentials(username, password);
            //If user isn't null put it in the session
            if (utente != null) {

                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 60); // 1 hour
                session.setAttribute("utente", utente);
                if (utente instanceof Cliente c) { //if the user is a client, put the address in the session
                    session.setAttribute("indirizzo", c.getIndirizzo());
                }

                //Remove cart from session if a manager logs in with items in cart
                if ((utente.getRuolo()=="manager") && ((session.getAttribute("carrelloList") != null))) {
                    session.removeAttribute("carrelloList");
                }

                //Create cookie to show a message after login
                Cookie cookie = new Cookie("notification", "Benvenuto-" + utente.getUsername() + "!");
                cookie.setMaxAge(1);
                cookie.setSecure(true);
                response.addCookie(cookie);
                response.sendRedirect("index.jsp");
            } else { //  if user is null
                request.setAttribute("errorMSG", "Username o password incorretti.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}