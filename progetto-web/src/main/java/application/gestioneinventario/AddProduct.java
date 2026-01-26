package application.gestioneinventario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import storage.FacadeDAO;
import storage.gestioneinventario.Prodotto;
import storage.gestioneutente.Manager;
import storage.gestioneutente.Utente;

import java.io.File;
import java.io.IOException;

/**
 * Servlet to add Product
 */
@MultipartConfig
@WebServlet(name="addProduct", value = "/addProduct")
public class AddProduct extends HttpServlet {

    private FacadeDAO service = new FacadeDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if(utente instanceof Manager) {
            try{
                req.setCharacterEncoding("UTF-8");

                //Get Parameter
                String nome=req.getParameter("nameProdotto").trim();
                String prezzo=req.getParameter("prezzoProdotto");
                Part image = req.getPart("immagineProdotto");
                String saldoStr = req.getParameter("percentualeSaldo");
                String quantita = req.getParameter("quantità");
                String cpu= req.getParameter("cpu").trim();
                String gpu= req.getParameter("gpu").trim();
                String ramSizeStr = req.getParameter("RAMsize");
                String ramSpeedStr = req.getParameter("RAMspeed");
                String ssdSizeStr = req.getParameter("SSDsize");

                //Validation name of the product
                if(!Prodotto.validateNome(nome)) {
                    errore(resp,"Il nome del prodotto deve contenere da 7 a 50 caratteri");
                    return;
                }

                //Validation price of the product
                double prezzoDouble;
                try {
                    if(prezzo==null) throw new NumberFormatException();

                    prezzoDouble = Double.parseDouble(prezzo);
                    if(!Prodotto.validatePrezzo(prezzoDouble)) {
                        errore(resp,"Il prezzo deve essere compreso tra 0,01 e 9999999,99.");
                        return;
                    }
                }catch (NumberFormatException e) {
                    errore(resp,"Prezzo non valido. Inserisci un numero corretto con virgola (es: 999,99");
                    return;
                }

                //Validation immage
                String nomeFile;
                if(image == null || image.getSize()==0) {
                    nomeFile="DEFAULT.png";
                }else{
                    String uploadPath = req.getServletContext().getRealPath("") + "/images/PCimages/";
                    String tempFileName = image.getSubmittedFileName();
                    nomeFile = image.getSubmittedFileName();
                    File file = new File(uploadPath + tempFileName);
                    for (int i = 2; file.exists(); i++) {
                        nomeFile = i + "_" + tempFileName;
                        file = new File(uploadPath + nomeFile);
                    }
                    image.write(file.getAbsolutePath());
                }

                //Validation saldo
                int saldo;
                try {
                    saldo = Integer.parseInt(saldoStr);
                    if(!Prodotto.validatePercSaldo(saldo)) {
                        errore(resp,"Il saldo deve essere tra 0 e 99");
                        return;
                    }
                }catch (NumberFormatException e) {
                    errore(resp,"Percentuale di saldo non valida");
                    return;
                }

                //Validation quantity
                int quantità;
                try {
                    quantità = Integer.parseInt(quantita);
                    if (!Prodotto.validateQuantita(quantità)){
                        errore(resp,"La quantità deve essere almeno 1");
                        return;
                    }
                }catch (NumberFormatException e) {
                    errore(resp,"Quantità non valida");
                    return;
                }

                //Validation cpu
                if(!Prodotto.validateCPU(cpu)) {
                    errore(resp,"La CPU deve essere lunga da 3 a 60 caratteri.");
                    return;
                }

                //Validation GPU
                if(!Prodotto.validateGPU(gpu)) {
                    errore(resp,"La GPU deve contenere da 3 aa 60 caratteri");
                    return;
                }

                //Validation RAM size
                int ramSize = 0;
                try {
                    ramSize = Integer.parseInt(ramSizeStr);
                    if (!Prodotto.validateRamSize(ramSize)) {
                        errore(resp,"La RAM deve essere almeno 4 ed essere un multiplo di 2");
                        return;
                    }
                }catch (NumberFormatException e) {
                    errore(resp,"RAM size non valida");
                    return;
                }

                //Validation RAM speed
                int ramSpeed;
                try {
                    ramSpeed = Integer.parseInt(ramSpeedStr);
                    if (!Prodotto.validateRamSpeed(ramSpeed)) {
                        errore(resp,"La RAM speed deve essere tra 1000 e 10000 Mhz");
                        return;
                    }
                }catch (NumberFormatException e) {
                    errore(resp,"RAM speed non valida");
                    return;
                }
                int ssdSize;
                try {
                    ssdSize = Integer.parseInt(ssdSizeStr);
                    if (!Prodotto.validateSSDSize(ssdSize)) {
                        errore(resp,"L'SSD deve essere almeno 256 ed essere un multiplo di 4");
                        return;
                    }
                }catch (NumberFormatException e){
                    errore(resp,"SSD size non valida.");
                    return;
                }

                //If is all ok
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(nome);
                prodotto.setPrezzo(prezzoDouble);
                prodotto.setImmagine(nomeFile);
                prodotto.setPercSaldo(saldo);
                prodotto.setQuantita(quantità);
                prodotto.setCPU(cpu);
                prodotto.setGPU(gpu);
                prodotto.setRAM_size(ramSize);
                prodotto.setRAM_speed(ramSpeed);
                prodotto.setSSD_size(ssdSize);

                //Save into database
                if(service.saveProduct(prodotto)==1) {
                    //Successful
                    Cookie cookie = new Cookie("notification", "Prodotto-inserito-con-successo!");
                    cookie.setMaxAge(1);
                    cookie.setSecure(true);
                    resp.addCookie(cookie);
                    resp.sendRedirect("manage");
                }
                else {
                    errore(resp, "Errore nel salvataggio dell Prodotto");
                }
            }catch (Exception e) {
                req.setAttribute("exception", e);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(req, resp);
            }
        }
        else {
            req.setAttribute("errorMSG", "Accesso negato. La risorsa richiesta richiede privilegi di amministratore.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void errore(HttpServletResponse response, String messaggio) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('" + messaggio + "'); history.back();</script>");
    }
}
