package application.gestioneordine;

import application.gestioneutente.Login;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import storage.Connector.ConPool;
import storage.FacadeDAO;
import storage.gestionecarrello.Carrello;
import storage.gestionecarrello.CarrelloItem;
import storage.gestioneinventario.Prodotto;
import storage.gestioneordine.OrdineDAO;
import storage.gestioneutente.Indirizzo;
import storage.gestioneutente.Utente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CheckoutTest {
    private CheckoutServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    @BeforeEach
    void setUp() {
        servlet = new CheckoutServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFacadeDAO(daoMock);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

    }

    @Test
    public void TC_2_1_1testUtenteNULL() throws ServletException, IOException {
        when(session.getAttribute("utente")).thenReturn(null);

        servlet.doGet(request, response);
        verify(response).sendRedirect(eq("login.jsp"));
        verify(dispatcher, never()).forward(request, response);
    }

    @Test
    public void TC_2_1_2testUtenteManager() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("manager");
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta."));

    }

    @Test
    public void TC_2_1_3testClienteNullCart() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        Indirizzo indirizzo = mock(Indirizzo.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("cliente");
        when(utente.getIndirizzo()).thenReturn(indirizzo);
        when(session.getAttribute("carrelloList")).thenReturn(null);
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Tentato checkout con carrello vuoto!"));

    }

    @Test
    public void TC_2_1_4testClienteEmptyCart() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        Indirizzo indirizzo = mock(Indirizzo.class);
        Carrello carrello = mock(Carrello.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getIndirizzo()).thenReturn(indirizzo);
        when(utente.getRuolo()).thenReturn("cliente");
        when(session.getAttribute("carrelloList")).thenReturn(carrello);
        when(carrello.isEmpty()).thenReturn(true);
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Tentato checkout con carrello vuoto!"));

    }

    @Test
    public void TC_2_1_5testClienteInsuffSaldo() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        Indirizzo indirizzo = mock(Indirizzo.class);
        Carrello carrello = mock(Carrello.class);
        List<CarrelloItem> carrelloItemList = mock(List.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getIndirizzo()).thenReturn(indirizzo);
        when(utente.getRuolo()).thenReturn("cliente");
        when(session.getAttribute("carrelloList")).thenReturn(carrello);
        when(carrello.getCarrelloItemList()).thenReturn(carrelloItemList);
        when(carrelloItemList.isEmpty()).thenReturn(false);
        when(carrello.getCartTotal()).thenReturn(1001.0);
        when(utente.getSaldo()).thenReturn(1000.0);
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Saldo insufficiente! saldo: 1000.0€, ordine: 1001.0€"));

    }

    @Test
    public void TC_2_1_6testInsuffProdQTY() throws ServletException, IOException, SQLException {

        //dipende dal db,controllare prima di eseguire
        Prodotto p = new Prodotto();
        p.setId(1);
        p.setNome("PC");
        p.setPrezzo(1000);
        p.setQuantita(1);
        p.setPercSaldo(0);
        p.setImmagine("DEFAULT.png");

        CarrelloItem item = mock(CarrelloItem.class);
        when(item.getProdotto()).thenReturn(p);
        when(item.getQuantita()).thenReturn(5);

        Carrello carrello = mock(Carrello.class);
        when(carrello.getCarrelloItemList()).thenReturn(List.of(item));
        carrello.addCarrelloItem(p);

        Utente cliente = mock(Utente.class);
        //Indirizzo indirizzo = new Indirizzo("via Nazionale",1,84025,"Campania","Salerno","Eboli");
        Indirizzo indirizzo = mock(Indirizzo.class);
        when(cliente.getRuolo()).thenReturn("cliente");
        when(cliente.getId()).thenReturn(1);
        when(cliente.getSaldo()).thenReturn(5000.0);
        when(cliente.getIndirizzo()).thenReturn(indirizzo);


        when(session.getAttribute("utente")).thenReturn(cliente);
        when(cliente.getIndirizzo()).thenReturn(indirizzo);
        when(cliente.getRuolo()).thenReturn("cliente");
        when(session.getAttribute("carrelloList")).thenReturn(carrello);
        when(carrello.isEmpty()).thenReturn(false);



        //List<String> errors =daoMock.checkout(cliente,carrello);

        FacadeDAO dao = new FacadeDAO();
        List<String> errors = dao.checkout(cliente, carrello);


        assertFalse(errors.isEmpty());
        assertTrue(errors.get(0).contains("La quantità richiesta di "));



    }

    @Test
    public void TC_2_1_7DBError() throws Exception {

        Prodotto p = new Prodotto();
        p.setId(1);
        p.setNome("PC");
        p.setPrezzo(1000);
        p.setQuantita(1);
        p.setPercSaldo(0);
        p.setImmagine("DEFAULT.png");

        CarrelloItem item = mock(CarrelloItem.class);
        when(item.getProdotto()).thenReturn(p);
        when(item.getQuantita()).thenReturn(1);

        Carrello carrello = mock(Carrello.class);
        when(carrello.getCarrelloItemList()).thenReturn(List.of(item));

        Utente cliente = mock(Utente.class);
        Indirizzo indirizzo = new Indirizzo(
                "via Nazionale", 1, 84025,
                "Campania", "Salerno", "Eboli"
        );

        when(cliente.getRuolo()).thenReturn("cliente");
        when(cliente.getId()).thenReturn(1);
        when(cliente.getSaldo()).thenReturn(5000.0);
        when(cliente.getIndirizzo()).thenReturn(indirizzo);

        when(session.getAttribute("utente")).thenReturn(cliente);
        when(session.getAttribute("carrelloList")).thenReturn(carrello);

        when(daoMock.checkout(cliente,carrello)).thenThrow(new RuntimeException("DB error"));

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("exception"), any(RuntimeException.class));
        verify(dispatcher).forward(request, response);

    }

    @Test
    public void TC_2_1_8CheckoutSuccess() throws Exception {

        Prodotto p = new Prodotto();
        p.setId(1);
        p.setNome("PC");
        p.setPrezzo(1000);
        p.setQuantita(1);
        p.setPercSaldo(0);
        p.setImmagine("DEFAULT.png");

        CarrelloItem item = mock(CarrelloItem.class);
        when(item.getProdotto()).thenReturn(p);
        when(item.getQuantita()).thenReturn(1);

        Carrello carrello = mock(Carrello.class);
        when(carrello.getCarrelloItemList()).thenReturn(List.of(item));

        Utente cliente = mock(Utente.class);
        Indirizzo indirizzo = new Indirizzo(
                "via Nazionale", 1, 84025,
                "Campania", "Salerno", "Eboli"
        );

        when(cliente.getRuolo()).thenReturn("cliente");
        when(cliente.getId()).thenReturn(1);
        when(cliente.getSaldo()).thenReturn(5000.0);
        when(cliente.getIndirizzo()).thenReturn(indirizzo);

        when(session.getAttribute("utente")).thenReturn(cliente);
        when(session.getAttribute("carrelloList")).thenReturn(carrello);

        List<String> errors =new ArrayList<>();
        when(daoMock.checkout(cliente,carrello)).thenReturn(errors);
        servlet.doPost(request, response);


        verify(daoMock).checkout(cliente,carrello);
        verify(dispatcher).forward(request, response);

    }






}
