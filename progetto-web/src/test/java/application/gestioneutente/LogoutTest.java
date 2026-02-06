package application.gestioneutente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import storage.FacadeDAO;
import storage.gestioneutente.Utente;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class LogoutTest {

    private Logout servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    @BeforeEach
    public void setUp() {
        servlet = new Logout();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFacadeDAO(daoMock);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }


    /**
     * Method used to test a logout when a user isn't logged
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void TC_1_8_1testUtenteNULL() throws ServletException, IOException {
        when(session.getAttribute("utente")).thenReturn(null);

        servlet.doGet(request, response);
        verify(request).setAttribute(eq("errorMSG"),eq("Tentato logout senza aver prima effettuato l'accesso."));
        verify(dispatcher).forward(request,response);
    }

    /**
     * Method used to test logout
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void TC_1_8_2testLogout() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        servlet.doGet(request, response);

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());

        Cookie cookieInviato = cookieCaptor.getValue();


        assertEquals("notification", cookieInviato.getName(), "Il nome del cookie deve essere 'notification'");
        assertEquals("Logout-effettuato-con-successo.-A-presto!", cookieInviato.getValue(), "Il messaggio del cookie  dovrebbe corrispondere");
        verify(response).sendRedirect("index.jsp");

    }
}
