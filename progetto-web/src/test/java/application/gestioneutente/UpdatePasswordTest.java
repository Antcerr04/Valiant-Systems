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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UpdatePasswordTest {

    private final String PASSWORD_ATTUALE="Antonio2004@";
    private final String NEW_PASSWORD="Antonio2005@";

    private UpdatePassword servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    @BeforeEach
    public void setUp() {
        servlet = new UpdatePassword();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFaceDAO(daoMock);

        when(request.getParameter("actualPassword")).thenReturn(PASSWORD_ATTUALE);
        when(request.getParameter("registerPassword")).thenReturn(NEW_PASSWORD);
        when(request.getParameter("repeatPassword")).thenReturn(NEW_PASSWORD);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    void TC_1_7_1_testPasswordAttualeErrata() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(request.getParameter("actualPassword")).thenReturn("Antonio2004");
        servlet.doPost(request,response);
        verify(request).setAttribute(eq("errorMSG"),eq("Formato della password attuale non valida"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void TC_1_7_2_testPasswordNonCorrispondente() throws ServletException, IOException {
        Utente utente = mock(Utente.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn("Antcerr04");
        when(request.getParameter("actualPassword")).thenReturn("Antonio2004@");
        when(daoMock.getUserByCredentials(utente.getUsername(), PASSWORD_ATTUALE)).thenReturn(null);

        servlet.doPost(request,response);
        verify(request).setAttribute(eq("errorMSG"),eq("La password attuale non corrisponde"));
        verify(dispatcher).forward(request, response);
    }



    @Test
    void TC_1_7_3_testNewPasswordErrata() throws ServletException, IOException {
        Utente utente = mock(Utente.class);

        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn("Antcerr04");
        when(daoMock.getUserByCredentials("Antcerr04",PASSWORD_ATTUALE)).thenReturn(utente);
        when(request.getParameter("registerPassword")).thenReturn("Antonio2005");
        servlet.doPost(request,response);
        verify(request).setAttribute(eq("errorMSG"),eq("Formato della nuova password non corretta"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void TC_1_7_4_testPasswordRipetutaErrata() throws ServletException, IOException {
        Utente utente = mock(Utente.class);

        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn("Antcerr04");
        when(daoMock.getUserByCredentials("Antcerr04",PASSWORD_ATTUALE)).thenReturn(utente);
        when(request.getParameter("repeatPassword")).thenReturn("Antonio2005");
        servlet.doPost(request,response);
        verify(request).setAttribute(eq("errorMSG"),eq("Formato della password ripetuta non corretta"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void TC_1_7_5_testPasswordNuoveNonCorrispondenti() throws ServletException, IOException {
        Utente utente = mock(Utente.class);

        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn("Antcerr04");
        when(daoMock.getUserByCredentials("Antcerr04",PASSWORD_ATTUALE)).thenReturn(utente);
        when(request.getParameter("repeatPassword")).thenReturn("Antonio200@");
        servlet.doPost(request,response);
        verify(request).setAttribute(eq("errorMSG"),eq("Le password non corrispondono"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void TC_1_7_6_testErrore() throws ServletException, IOException {
        Utente utente = mock(Utente.class);

        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn("Antcerr04");
        when(daoMock.getUserByCredentials("Antcerr04",PASSWORD_ATTUALE)).thenReturn(utente);
      when(daoMock.updateUserPassword(anyString(),eq(NEW_PASSWORD))).thenReturn(false);
        servlet.doPost(request,response);
        verify(request).setAttribute(eq("errorMSG"),eq("Errore nel Database"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void TC_1_7_7_testUpdatePassword() throws ServletException, IOException {
        Utente utente = mock(Utente.class);

        when(session.getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn("Antcerr04");
        when(daoMock.getUserByCredentials("Antcerr04",PASSWORD_ATTUALE)).thenReturn(utente);
        when(utente.getEmail()).thenReturn("antoniocerr@gmail.com");
        when(daoMock.updateUserPassword(anyString(),eq(NEW_PASSWORD))).thenReturn(true);
        servlet.doPost(request,response);
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());

        Cookie cookieInviato = cookieCaptor.getValue();


        assertEquals("notification", cookieInviato.getName(), "Il nome del cookie deve essere 'notification'");
        assertEquals("Password-aggiornata-con-successo", cookieInviato.getValue(), "Il messaggio del cookie  dovrebbe corrispondere");
        verify(response).sendRedirect("index.jsp");
    }



}
