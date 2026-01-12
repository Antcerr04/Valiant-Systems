package application.gestioneutente;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import storage.gestioneutente.*;

import java.io.IOException;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recovery parameters
        String nome = request.getParameter("name");
        String cognome = request.getParameter("surname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String via = request.getParameter("via");
        String numeroCasaStr = request.getParameter("house-number");
        String capStr = request.getParameter("cap");
        String città = request.getParameter("city");
        String provincia = request.getParameter("provincia");
        String regione = request.getParameter("regione");

        // Controls
        if (nome == null || !nome.matches("^[A-Za-zà-ù]{2,20}$") ||
                cognome == null || !cognome.matches("^[A-Za-zà-ù]{2,20}$") ||
                email == null || email.length() <= 10 ||
                username == null || username.length() < 3 ||
                password == null || !validatePassword(password) ||
                via == null || !via.matches("^[A-Za-z \\-]+$") ||
                numeroCasaStr == null || !numeroCasaStr.matches("^\\d{1,3}$") ||
                capStr == null || !capStr.matches("^\\d{5}$") ||
                città == null || !città.matches("^[A-Za-z \\-]+$") ||
                provincia == null || provincia.isEmpty() ||
                regione == null || regione.isEmpty()) {

            request.setAttribute("error", "Dati non validi o mancanti.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        int numeroCasa = Integer.parseInt(numeroCasaStr);
        int cap = Integer.parseInt(capStr);

        // Verifica unicità email e username

        /**
         * if (service.emailExists(email)) {
         *             request.setAttribute("error", "Email già in uso.");
         *             request.getRequestDispatcher("register.jsp").forward(request, response);
         *             return;
         *         }
         *         if (service.usernameExists(username)) {
         *             request.setAttribute("error", "Username già in uso.");
         *             request.getRequestDispatcher("register.jsp").forward(request, response);
         *             return;
         *         }
         */
        UtenteDAO service = new UtenteDAO();


        // Create address
        Indirizzo indirizzo= new Indirizzo(via,numeroCasa,cap,regione,provincia,città);


        // Save client
        Cliente cliente = new Cliente(nome,cognome,username,email,password,indirizzo);
        cliente.setPasswordHash(password);

        service.doSave(cliente);

        // Success : go to login
        Cookie cookie = new Cookie("notification", "Registrazione-avvenuta-con-successo!");
        cookie.setMaxAge(1);
        cookie.setSecure(true);
        response.addCookie(cookie);
        response.sendRedirect("login.jsp");
    }

    //function to validate password
    private boolean validatePassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#\\$%\\^&\\*\\)\\(+=._-].*");
    }
}
