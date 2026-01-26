package application.gestioneutente;

import application.gestioneutente.Modifica;
import application.gestioneutente.RecoveryPassword;
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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class testRecuperoPassword {
    private RecoveryPassword servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    private static final String EMAIL = "Mariorossi@gmail.com";
    private static final String CODICE = "190804";
    private static final String NEWPASSWORD = "Mario2004@";
    private static final String CODICEINSERITO = "190804";

    @BeforeEach
    public void setUp() {
        servlet = new RecoveryPassword();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFaceDAO(daoMock);

        when(request.getSession()).thenReturn(session);

        when(request.getParameter("email")).thenReturn(EMAIL);
        when(request.getParameter("codice")).thenReturn(CODICE);
        when(request.getParameter("newPassword")).thenReturn(NEWPASSWORD);
        when(session.getAttribute("codiceVerifica")).thenReturn(CODICEINSERITO);


        //Mock of the dispatcher (used in the error cases)
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    /**
     * Method used to test a failure when email isn't correct
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    void TC_1_5_1_EmailErrata() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("Mariorossigmail.com");
        servlet.doPost(request, response);


        verify(request).setAttribute(eq("errorMSG"), eq("Email invalida"));
        verify(dispatcher).forward(request, response);
    }

    /**
     * Method used to test a failure when email doesn't exist
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    void TC_1_5_2_EmailInesistente() throws ServletException, IOException {

        when(request.getParameter("email")).thenReturn(EMAIL);

        when(daoMock.isEmailPresent(EMAIL)).thenReturn(false);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errorMSG"), eq("Email non associata a nessun account"));
        verify(dispatcher).forward(request, response);
    }

    /**
     * Method used to test a failure when Code verificator isn't correct
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    void TC_1_5_3_CodiceNonCorrispondente() throws ServletException, IOException {

        when(request.getParameter("codice")).thenReturn("1908004");

        when(daoMock.isEmailPresent(anyString())).thenReturn(true);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Codice di verifica errato"));
        verify(dispatcher).forward(request, response);


    }

    @Test
    /**
     * Method used to test a failure when a password isn't correct
     */
    void TC_1_5_4_PasswordNonCoretta() throws ServletException, IOException {

        when(daoMock.isEmailPresent(anyString())).thenReturn(true);

        when(request.getParameter("newPassword")).thenReturn("Mariorossi@");
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("errorMSG"), eq("Password non corretta"));
        verify(dispatcher).forward(request, response);


    }

    /**
     * Method used to test an error in DB
     *
     * @throws ServletException
     * @throws IOException
     */

    @Test
    void TC_1_5_5_testErrore() throws ServletException, IOException {
        when(daoMock.isEmailPresent(EMAIL)).thenReturn(true);
        when(daoMock.updateUserPassword(EMAIL, NEWPASSWORD)).thenReturn(false);

        servlet.doPost(request, response);
    }

    /**
     * Method used to test a correct recovery of the password
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    void TC_1_5_6_testRecoveryPassword() throws ServletException, IOException {
        when(daoMock.isEmailPresent(EMAIL)).thenReturn(true);
        when(daoMock.updateUserPassword(EMAIL, NEWPASSWORD)).thenReturn(true);
        servlet.doPost(request, response);
        // 3. Verify Cookie
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());

        Cookie cookieInviato = cookieCaptor.getValue();


        assertEquals("notification", cookieInviato.getName(), "Il nome del cookie deve essere 'notification'");
        assertEquals("Riprstino-eseguito-con-successo.", cookieInviato.getValue(), "Il messaggio del cookie  corrisponde");

        verify(response).sendRedirect("index.jsp");
    }


}
