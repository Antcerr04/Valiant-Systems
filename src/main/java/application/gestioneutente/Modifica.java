package application.gestioneutente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.FacadeDAO;
import storage.gestioneutente.Indirizzo;
import storage.gestioneutente.Utente;

import java.io.IOException;

/**
 * This is a servlet to do updates
 */
@WebServlet(name = "Modifica", value = "/Modifica")
public class Modifica extends HttpServlet {

    private FacadeDAO service=new FacadeDAO();

    public void setFaceDAO(FacadeDAO service) {
        this.service=service;
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sourcce = req.getParameter("source");
        HttpSession session = req.getSession();

        if ("reset".equals(sourcce)) {
            //1. Get data from form

            String emailInserita = req.getParameter("email");
            String codiceInserito = req.getParameter("codice");
            String nuovaPassword = req.getParameter("newPassword");

            //Validation email
            if(!Utente.validateEmail(emailInserita)) {
                req.setAttribute("errorMSG", "Email invalida");
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                rd.forward(req, resp);

                return;
            }
            boolean existEmail=service.isEmailPresent(emailInserita);
            if(!existEmail) {
                req.setAttribute("errorMSG", "Email non associata a nessun account");
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                rd.forward(req, resp);
                return;
            }

            //2.Get data in the session

            String codiceCorretto = (String) session.getAttribute("codiceVerifica");

            //Validation code
            if (codiceCorretto == null || !codiceCorretto.equals(codiceInserito)) {
                req.setAttribute("errorMSG", "Codice di verifica errato");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            //Validation password
            if (!Utente.validatePassword(nuovaPassword)) {
                req.setAttribute("errorMSG", "Password non corretta");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            //If is all ok
            boolean success = service.updateUserPassword(emailInserita,nuovaPassword);

            if (success) {
                session.removeAttribute("codiceVerifica");
                Cookie cookie = new Cookie("notification", "Riprstino-eseguito-con-successo.");
                cookie.setMaxAge(1);
                cookie.setSecure(true);
                resp.addCookie(cookie);
                resp.sendRedirect("index.jsp");
                return;

            } else {
                req.setAttribute("errorMSG", "Errore nel DB");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
            }
        } else if (sourcce.equals("update")) {
            try {
                Utente utenteAttuale= (Utente) session.getAttribute("utente");
                Indirizzo indirizzoAttuale= (Indirizzo) session.getAttribute("indirizzo");
                String nome=req.getParameter("name");
                String cognome=req.getParameter("surname");
                String regione=req.getParameter("regione");
                String provincia=req.getParameter("provincia");
                String via=req.getParameter("via");
                String numCivico=req.getParameter("house-number");
                String cap=req.getParameter("cap");
                String city=req.getParameter("city");

                //Validation name and suername
                if(!Utente.validateNome(nome) || !Utente.validateCognome(cognome)){
                    req.setAttribute("errorMSG", "Nome o cognome non corretti");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(req, resp);
                    return;

                }
                utenteAttuale.setNome(nome);
                utenteAttuale.setCognome(cognome);

                //Validation address
                if(!Indirizzo.validateRegione(regione) ||
                        !Indirizzo.validateProvincia(provincia) ||
                        !Indirizzo.validateVia(via) ||
                        !Indirizzo.validateNumCiv(Integer.parseInt(numCivico)) ||
                        !Indirizzo.validateCap(Integer.parseInt(cap)) ||
                        !Indirizzo.validateCittà(city)){
                    req.setAttribute("errorMSG", "Dati indirizzo non validi");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(req, resp);
                    return;
                }

                indirizzoAttuale.setRegione(regione);
                indirizzoAttuale.setProvincia(provincia);
                indirizzoAttuale.setVia(via);
                indirizzoAttuale.setNumCiv(Integer.parseInt(numCivico));
                indirizzoAttuale.setCap(Integer.parseInt(cap));
                indirizzoAttuale.setCittà(city);


                boolean ok=service.updateAccount(utenteAttuale,indirizzoAttuale);

                if (ok) {
                    Cookie cookie = new Cookie("notification", "Riprstino-eseguito-successo.");
                    cookie.setMaxAge(1);
                    cookie.setSecure(true);
                    resp.addCookie(cookie);
                    resp.sendRedirect("index.jsp");
                }
                else {
                    req.setAttribute("errorMSG", "Operazione non riuscita");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                    dispatcher.forward(req, resp);
                    return;
                }
            }catch (NumberFormatException Exception ) {
                req.setAttribute("errorMSG", "Errore non puoi inserire una lettera nel numero civico");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
                return;
            }

        }
    }

}
