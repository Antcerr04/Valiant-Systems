package application.gestioneutente;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import storage.gestioneutente.*;

import java.io.IOException;

/**
 * This is a Servlet to do logins
 */
@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Validazione lato server
            if (username == null || username.trim().isEmpty() || username.length() > 75 ||
                    password == null || password.trim().isEmpty() || password.length() > 50) {
                request.setAttribute("errorMSG", "Username o password non validi.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Accesso ai dati
            UtenteDAO service = new UtenteDAO();
            Utente utente = service.doRetrieveByUsernamePassword(username, password);

            if (utente != null) {

                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 60); // 1 ora
                session.setAttribute("utente", utente);

                Cookie cookie = new Cookie("notification", "Benvenuto-" + utente.getUsername() + "!");
                cookie.setMaxAge(1);
                cookie.setSecure(true);
                response.addCookie(cookie);
                response.sendRedirect("index.jsp");
            } else {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
