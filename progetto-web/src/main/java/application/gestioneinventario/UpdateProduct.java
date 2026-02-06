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
 * Servlet to update Product
 */
@WebServlet(name = "UpdateProduct", value = "/UpdateProduct")
@MultipartConfig
public class UpdateProduct extends HttpServlet {
    private FacadeDAO service = new FacadeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String source = request.getParameter("source");
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente.getRuolo()=="manager") {
            if (source != null) {
                if (source.equals("showAll")) {  //called from adminView to show form for update product
                    if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                        int id = Integer.parseInt(request.getParameter("id"));
                        Prodotto prodotto = service.getProductById(id);
                        if (prodotto != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("prodotto", prodotto);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/updateProduct.jsp");
                            dispatcher.forward(request, response);
                        } else {
                            request.setAttribute("errorMSG", "Il prodotto con id '" + id + "' non è presente nel database!");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                            dispatcher.forward(request, response);
                        }
                    } else {
                        request.setAttribute("errorMSG", "Il campo prodotto id non può essere vuoto!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                        dispatcher.forward(request, response);
                    }
                }

                if (source.equals("updateProduct")) { //called from update to modify product
                    try {
                        request.setCharacterEncoding("UTF-8");
                        int id = Integer.parseInt(request.getParameter("id"));

                        String nome = request.getParameter("nameProdotto").trim();
                        String prezzo = request.getParameter("prezzoProdotto");
                        Part immagine = request.getPart("immagineProdottto");
                        String saldoStr = request.getParameter("percentualeSaldo");
                        String quantitaStr = request.getParameter("quantità");
                        String cpu = request.getParameter("cpu").trim();
                        String gpu = request.getParameter("gpu").trim();
                        String ramSizeStr = request.getParameter("RAMsize");
                        String ramSpeedStr = request.getParameter("RAMspeed");
                        String ssdSizeStr = request.getParameter("SSDsize");

                        // Validation nome
                        if (!Prodotto.validateNome(nome)) {
                            errore(response, "Il nome del prodotto deve contenere da 7 a 50 caratteri.");
                            return;
                        }

                        // Validation price
                        double prezzoDouble;
                        try {
                            if (prezzo == null) throw new NumberFormatException();
                            prezzoDouble = Double.parseDouble(prezzo);
                            if (!Prodotto.validatePrezzo(prezzoDouble)) {
                                errore(response, "Il prezzo deve essere compreso tra 0,01 e 9999999,99.");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            errore(response, "Prezzo non valido. Inserisci un numero corretto con virgola (es: 999,99).");
                            return;
                        }

                        // Validation image
                        Prodotto currProd = service.getProductById(id);
                        String nomeFile;
                        if (immagine == null || immagine.getSize() == 0) {
                            nomeFile = currProd.getImmagine();
                        } else {
                            String uploadPath = request.getServletContext().getRealPath("") + "/images/PCimages/";
                            String tempFileName = immagine.getSubmittedFileName();
                            nomeFile = tempFileName;
                            File file = new File(uploadPath + tempFileName);
                            for (int i = 2; file.exists(); i++) {
                                nomeFile = i + "_" + tempFileName;
                                file = new File(uploadPath + nomeFile);
                            }
                            immagine.write(file.getAbsolutePath());
                        }

                        // Validation saldo
                        int saldo;
                        try {
                            saldo = Integer.parseInt(saldoStr);
                            if (!Prodotto.validatePercSaldo(saldo)) {
                                errore(response, "Il saldo deve essere tra 0 e 99.");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            errore(response, "Percentuale di saldo non valida.");
                            return;
                        }

                        // Validation quantity
                        int quantita;
                        try {
                            quantita = Integer.parseInt(quantitaStr);
                            if (!Prodotto.validateQuantita(quantita)) {
                                errore(response, "La quantità deve essere almeno 1.");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            errore(response, "Quantità non valida.");
                            return;
                        }

                        // Validation CPU
                        if (!Prodotto.validateCPU(cpu)) {
                            errore(response, "La CPU deve contenere da 3 a 60 caratteri.");
                            return;
                        }

                        // Validation GPU
                        if (!Prodotto.validateGPU(gpu)) {
                            errore(response, "La GPU deve contenere da 3 a 60 caratteri.");
                            return;
                        }

                        // Validation RAM size
                        int ramSize;
                        try {
                            ramSize = Integer.parseInt(ramSizeStr);
                            if (!Prodotto.validateRamSize(ramSize)) {
                                errore(response, "La RAM deve essere almeno 4 ed essere un multiplo di 2.");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            errore(response, "RAM size non valida.");
                            return;
                        }

                        // Validation RAM speed
                        int ramSpeed;
                        try {
                            ramSpeed = Integer.parseInt(ramSpeedStr);
                            if (!Prodotto.validateRamSpeed(ramSpeed)) {
                                errore(response, "La RAM speed deve essere tra 1000 e 10000 MHz.");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            errore(response, "RAM speed non valida.");
                            return;
                        }

                        // Validation SSD size
                        int ssdSize;
                        try {
                            ssdSize = Integer.parseInt(ssdSizeStr);
                            if (!Prodotto.validateSSDSize(ssdSize)) {
                                errore(response, "L'SSD deve essere almeno 256 ed essere un multiplo di 4.");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            errore(response, "SSD size non valida.");
                            return;
                        }

                        // If are all valid
                        Prodotto prodotto = new Prodotto();
                        prodotto.setId(id);
                        prodotto.setNome(nome);
                        prodotto.setPrezzo(prezzoDouble);
                        prodotto.setImmagine(nomeFile);
                        prodotto.setPercSaldo(saldo);
                        prodotto.setQuantita(quantita);
                        prodotto.setCPU(cpu);
                        prodotto.setGPU(gpu);
                        prodotto.setRAM_size(ramSize);
                        prodotto.setRAM_speed(ramSpeed);
                        prodotto.setSSD_size(ssdSize);

                        if (service.updateProduct(prodotto) == 1) {
                            Cookie cookie = new Cookie("notification", "Prodotto-aggiornato-con-successo!");
                            cookie.setMaxAge(1);
                            cookie.setSecure(true);
                            response.addCookie(cookie);
                            response.sendRedirect("manage");
                        } else {
                            errore(response, "Errore nel salvataggio del prodotto.");
                        }

                    } catch (Exception e) {
                        request.setAttribute("exception", e);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                        dispatcher.forward(request, response);
                    }
                }
            } else {
                request.setAttribute("errorMSG", "Errore! Operazione non valida.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("errorMSG", "Accesso negato. La risorsa richiesta richiede privilegi di amministratore.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void errore(HttpServletResponse response, String messaggio) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('" + messaggio + "'); history.back();</script>");
    }
}
