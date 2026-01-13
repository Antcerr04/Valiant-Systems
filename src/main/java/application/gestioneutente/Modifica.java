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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sourcce = req.getParameter("source");
        HttpSession session = req.getSession();

        if ("reset".equals(sourcce)) {
            //1. Recupero dati dal form

            String emailInserita = req.getParameter("email");
            String codiceInserito = req.getParameter("codice");
            String nuovaPassword = req.getParameter("newPassword");

            //2. Recupero dati salvati in sessione dalla EmailServlet

            String codiceCorretto = (String) session.getAttribute("codiceVerifica");

            //Controllo vaidità del codice
            if (codiceCorretto == null || !codiceCorretto.equals(codiceInserito)) {
                req.setAttribute("errorMSG", "Codide di verifica errato");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            //Controllo password
            if (!Utente.validatePassword(nuovaPassword)) {
                req.setAttribute("errorMSG", "Password non corretta");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            //Se è tutto ok aggiorno i dati
            boolean success = service.updateUserPassword(emailInserita,nuovaPassword);

            if (success) {
                session.removeAttribute("codiceVerifica");
                Cookie cookie = new Cookie("notification", "Riprstino-eseguito-con-successo.");
                cookie.setMaxAge(1);
                cookie.setSecure(true);
                resp.addCookie(cookie);
                resp.sendRedirect("index.jsp");

            } else {
                req.setAttribute("errorMSG", "Errore nel DB");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
            }
        } else if (sourcce.equals("update")) {
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

            //Validazione
            if(!Utente.validateNome(nome) || !Utente.validateCognome(cognome)){
                req.setAttribute("errorMSG", "Nome o cognome non corretti");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);

            }
            utenteAttuale.setNome(nome);
            utenteAttuale.setCognome(cognome);

            if(!Indirizzo.validateRegione(regione) ||
            !Indirizzo.validateProvincia(provincia) ||
            !Indirizzo.validateVia(via) ||
            !Indirizzo.validateNumCiv(Integer.parseInt(numCivico)) ||
            !Indirizzo.validateCap(Integer.parseInt(cap)) ||
            !Indirizzo.validateCittà(city)){
                req.setAttribute("errorMSG", "Dati indirizzo non validi");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
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
            }

        }
    }

}
