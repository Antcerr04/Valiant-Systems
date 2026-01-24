package gestioneutente;

import application.gestioneutente.Login;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.FacadeDAO;
import storage.gestioneutente.Cliente;
import storage.gestioneutente.Indirizzo;
import storage.gestioneutente.Manager;
import storage.gestioneutente.Utente;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Class to test Login
 */
public class testLogin {
    private Login servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    /**
     * Scenario with ALL correct data
     */
    @BeforeEach
    void setUp() {
        servlet = new Login();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFaceDAO(daoMock);

        //Prepare a scenario width ALL correct dates of default
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("username")).thenReturn("Mario2004");
        when(request.getParameter("password")).thenReturn("Mario2004@");
        //Mock of the dispatcher (used in error cases)
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    /**
     * Method to verify a failure
     *
     * @throws Exception
     */
    private void verifyFailure() throws Exception {
        //Verify the failure (back to register page)
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), anyString());
    }

    /**
     * Method to Test a failure when user isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_3_1_UsernameErrato() throws Exception {
        when(request.getParameter("username")).thenReturn("Ma");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method to test a failure if not exist a user with a specific username
     *
     * @throws Exception
     */
    @Test
    void TC_1_3_2_UsernameNonCorrispondente() throws Exception {
        String usernameInserito = "Mario200";
        String passwordInserita = "Mario2004@";

        when(request.getParameter("username")).thenReturn(usernameInserito);
        when(request.getParameter("password")).thenReturn(passwordInserita);

        when(daoMock.getUserByCredentials(usernameInserito, passwordInserita)).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errorMSG"), eq("Username o password incorretti."));

        verify(dispatcher).forward(request, response);

        verify(session, never()).setAttribute(eq("utente"), any());
    }

    /**
     * Method used to test a failure when a password isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_3_3_PasswordErrata() throws Exception {
        when(request.getParameter("password")).thenReturn("Mario2004");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when not exist a user with a specific password
     *
     * @throws Exception
     */
    @Test
    void TC_1_3_4_PasswordNonCorrispondente() throws Exception {
        String usernameInserito = "Mario2004";
        String passwordInserita = "Mario2004@@";

        when(request.getParameter("username")).thenReturn(usernameInserito);
        when(request.getParameter("password")).thenReturn(passwordInserita);

        when(daoMock.getUserByCredentials(usernameInserito, passwordInserita)).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errorMSG"), eq("Username o password incorretti."));

        verify(dispatcher).forward(request, response);

        verify(session, never()).setAttribute(eq("utente"), any());
    }

    /**
     * Method used to test an error in DB
     * @throws Exception
     */
    @Test
    void TC_1_3_5_LoginErroreImprevisto() throws Exception {
        //Force DAO to throw a generics exception
        when(daoMock.getUserByCredentials(anyString(), anyString()))
                .thenThrow(new RuntimeException("Errore DB"));

        servlet.doPost(request, response);

        //Verify that we are in the catch
        verify(request).setAttribute(eq("exception"), any(RuntimeException.class));
        verify(dispatcher).forward(request, response);
    }

    /**
     * Method used to test when a Manager do login after insert a product in the cart
     * @throws Exception
     */
    @Test
    void TC_1_3_6_LoginManagerConCarrello() throws Exception {
        String user = "Antcerr04";
        String pass = "Antonio2004@";
        Manager managerMock = mock(Manager.class);
        when(managerMock.getUsername()).thenReturn(user);


        when(request.getParameter("username")).thenReturn(user);
        when(request.getParameter("password")).thenReturn(pass);


        when(session.getAttribute("carrelloList")).thenReturn(new java.util.ArrayList<>());


        when(daoMock.getUserByCredentials(user, pass)).thenReturn(managerMock);


        servlet.doPost(request, response);


        verify(session).removeAttribute("carrelloList");
    }

    /**
     * Method used to test Manager Login without cart
     * @throws Exception
     */

    @Test
    void TC_1_3_7_LoginManagerSenzaCarrello() throws Exception {
        Manager managerMock = mock(Manager.class);
        when(managerMock.getUsername()).thenReturn("Antcerr04");

        // Simula la presenza di un carrello precedente
        when(session.getAttribute("carrelloList")).thenReturn(null);
        when(daoMock.getUserByCredentials("Antcerr04", "Antonio2004@")).thenReturn(managerMock);

        servlet.doPost(request, response);

        verify(session,never()).removeAttribute("carrelloList");
    }

    /**
     * Method to test the success of login
     *
     * @throws Exception
     */
    @Test
    void TC_1_3_8_LoginSuccessoCliente() throws Exception {
        String user = "Mario2004";
        String pass = "Mario2004@";
        Indirizzo indirizzo = new Indirizzo("via Rufigliano", 10, 84022, "Campania", "Salerno", "Salerno");


        Cliente utenteMock = mock(Cliente.class);


        when(utenteMock.getUsername()).thenReturn(user);
        when(utenteMock.getPassword()).thenReturn(pass);
        when(utenteMock.getIndirizzo()).thenReturn(indirizzo); // Serve per la riga dell'indirizzo

        when(request.getParameter("username")).thenReturn(user);
        when(request.getParameter("password")).thenReturn(pass);
        when(request.getSession()).thenReturn(session);


        when(daoMock.getUserByCredentials(user, pass)).thenReturn(utenteMock);

        servlet.doPost(request, response);


        verify(session).setAttribute(eq("utente"), eq(utenteMock));


        verify(session).setAttribute(eq("indirizzo"), eq(indirizzo));


        verify(response).addCookie(argThat(cookie ->
                cookie.getName().equals("notification") &&
                        cookie.getValue().contains("Benvenuto-" + user)));

        verify(response).sendRedirect(eq("index.jsp"));

        verify(dispatcher, never()).forward(request, response);
    }




}
