package application.gestioneordine;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.FacadeDAO;
import storage.gestioneutente.Utente;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SendOrderServletTest {
    private SendOrder servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    @BeforeEach
    void setUp() {
        servlet = new SendOrder();
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
    public void TC_2_4_1testUtenteNULL() throws ServletException, IOException {
        when(session.getAttribute("utente")).thenReturn(null);

        servlet.doGet(request, response);
        verify(response).sendRedirect(eq("null\\login.jsp"));
        verify(dispatcher, never()).forward(request, response);
    }
    @Test
    public void TC_2_4_2testUtenteClient() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("cliente");
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta."));
    }

    @Test
    public void TC_2_4_3NoID() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("manager");
        when(request.getParameter("id")).thenReturn(null);

        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Il campo id deve essere specificato!"));

    }

    @Test
    public void TC_2_4_4IDFormatError() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("manager");
        when(request.getParameter("id")).thenReturn("01");

        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Il campo id non rispetta il formato!"));

    }



    @Test
    public void TC_2_4_5ErroreDB() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("manager");
        when(request.getParameter("id")).thenReturn("1");
        when(daoMock.sendOrder(any(Integer.class))).thenThrow(new RuntimeException("Errore DB"));
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("exception"), any(RuntimeException.class));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void TC_2_4_6Success() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("manager");
        when(request.getParameter("id")).thenReturn("1");
        when(daoMock.sendOrder(any(Integer.class))).thenReturn(true);
        servlet.doGet(request, response);
        verify(response).addCookie(argThat(cookie -> cookie.getName().equals("notification")));
        verify(response).sendRedirect(eq("null\\manage-orders"));
    }

}
