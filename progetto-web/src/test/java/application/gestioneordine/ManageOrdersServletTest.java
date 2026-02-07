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
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ManageOrdersServletTest {
    private ManageOrders servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    @BeforeEach
    void setUp() {
        servlet = new ManageOrders();
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
    public void TC_2_3_1testUtenteNULL() throws ServletException, IOException {
        when(session.getAttribute("utente")).thenReturn(null);

        servlet.doGet(request, response);
        verify(response).sendRedirect(eq("login.jsp"));
        verify(dispatcher, never()).forward(request, response);
    }
    @Test
    public void TC_2_3_2testUtenteClient() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("cliente");
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Operazione negata! Non si hanno i permessi corretti per la risorsa richiesta."));

    }
    @Test
    public void TC_2_3_3ErroreDB() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("manager");
        when(daoMock.getAllUnshippedOrders()).thenThrow(new RuntimeException("Errore DB"));
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("exception"), any(RuntimeException.class));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void TC_2_3_4Success() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getRuolo()).thenReturn("manager");
        when(request.getRequestDispatcher("/WEB-INF/results/manageOrders.jsp"))
                .thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("orderList"), any(List.class));
        verify(dispatcher).forward(request, response);
    }
}
