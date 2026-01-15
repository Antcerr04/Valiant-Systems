package application.gestioneutente;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import storage.FacadeDAO;
import storage.gestioneutente.*;

import java.io.IOException;

@WebServlet(name = "Register", urlPatterns = {"/Register","/RegisterManager"})
public class Register extends HttpServlet {

    private FacadeDAO dao= new FacadeDAO();
    //This is used for test
    public void setFaceDAO(FacadeDAO dao) {
        this.dao = dao;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recovery parameters


            String nome = request.getParameter("name");
            String cognome = request.getParameter("surname");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String via = request.getParameter("via");
            String numeroCasaStr = request.getParameter("house-number");
            int numeroCivico = 0;
            int cap = 0;
            if (numeroCasaStr != null && !numeroCasaStr.isEmpty()) {
                try {
                    numeroCivico = Integer.parseInt(numeroCasaStr);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Dati non validi o mancanti.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
            }
            String capStr = request.getParameter("cap");
            if (capStr != null && !capStr.isEmpty()) {
                try {
                    cap = Integer.parseInt(capStr);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Dati non validi o mancanti.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
            }
            String città = request.getParameter("city");
            String provincia = request.getParameter("provincia");
            String regione = request.getParameter("regione");

            // Controls
            if (!Utente.validateNome(nome) ||
                    !Utente.validateCognome(cognome) ||
                    !Utente.validateEmail(email) ||
                    !Utente.validateUsername(username) ||
                    !Utente.validatePassword(password) ||
                    !Indirizzo.validateVia(via) ||
                    !Indirizzo.validateNumCiv(numeroCivico) ||
                    !Indirizzo.validateProvincia(provincia) ||
                    !Indirizzo.validateRegione(regione) ||
                    !Indirizzo.validateCittà(città) ||
                    !Indirizzo.validateCap(cap)) {

                request.setAttribute("error", "Dati non validi o mancanti.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }


            // Verifica unicità email e username


            if (dao.isEmailPresent(email)) {
                request.setAttribute("error", "Email già in uso.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            if (dao.isUserPresent(username)) {
                request.setAttribute("error", "Username già in uso.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }




            // Create address
            Indirizzo indirizzo = new Indirizzo(via, numeroCivico, cap, regione, provincia, città);


            // Save client
            Cliente cliente = new Cliente(nome, cognome, username, email, password, indirizzo);
            cliente.setPasswordHash(password);

            dao.saveClient(cliente);

            // Success : go to login
            Cookie cookie = new Cookie("notification", "Registrazione-avvenuta-con-successo!");
            cookie.setMaxAge(1);
            cookie.setSecure(true);
            response.addCookie(cookie);
            response.sendRedirect("login.jsp");
        }




}
