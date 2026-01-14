package gestioneutente;

import application.gestioneutente.Modifica;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.FacadeDAO;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class testRecuperoPassword {
    private Modifica servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    @BeforeEach
    public void setUp() {
        servlet = new Modifica();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFaceDAO(daoMock);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("source")).thenReturn("reset");
        when(request.getParameter("email")).thenReturn("Mariorossi@gmail.com");
        when(request.getParameter("codice")).thenReturn("190804");
        when(request.getParameter("newPassword")).thenReturn("Mariorossi2004@");
        when(session.getAttribute("codiceVerifica")).thenReturn("190804");


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
    void TC_1_5_2_EmailInserita() throws ServletException, IOException {

        String email = "Mariorossigmail@gmail.com";
        when(request.getParameter("email")).thenReturn(email);

        when(daoMock.isEmailPresent(email)).thenReturn(false);

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


}
