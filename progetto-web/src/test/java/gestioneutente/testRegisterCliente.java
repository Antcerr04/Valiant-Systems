package gestioneutente;

import application.gestioneutente.Register;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//Import for JUNIT 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import storage.FacadeDAO;
import storage.gestioneutente.Cliente;

import static org.mockito.Mockito.*;

//This class is used to Tester Registrazione cliente
class testRegisterCliente {

    private Register servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    /**
     * A scenario with ALL correct data
     */
    @BeforeEach
    void setUp() {
        servlet = new Register();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFaceDAO(daoMock);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Mario");
        when(request.getParameter("surname")).thenReturn("Rossi");
        when(request.getParameter("email")).thenReturn("Mariorossi@gmail.com");
        when(request.getParameter("username")).thenReturn("Mar04");
        when(request.getParameter("password")).thenReturn("Mario2004@");
        when(request.getParameter("via")).thenReturn("Via Vittorio Emanuele");
        when(request.getParameter("house-number")).thenReturn("10");
        when(request.getParameter("cap")).thenReturn("84023");
        when(request.getParameter("city")).thenReturn("Salerno");
        when(request.getParameter("provincia")).thenReturn("Salerno");
        when(request.getParameter("regione")).thenReturn("Campania");

        //Mock of the dispatcher (used in the error cases)
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    /**
     * Method used to verify a failure
     * @throws Exception
     */
    private void verifyFailure() throws Exception {
        //Verify the failure (back to register page)
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("error"), anyString());
    }

    /**
     * Method used to test failure when name isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_1_NomeErrato() throws Exception {
        // Override only the parameter name
        when(request.getParameter("name")).thenReturn("Mario@");

        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when surname isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_2_CognomeErrato() throws Exception {
        //Override only the parameter surname
        when(request.getParameter("surname")).thenReturn("R0ssi");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when email isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_3_EmailErrato() throws Exception {
        //Override only the parameter surname
        when(request.getParameter("email")).thenReturn("Mariorossigmail,com");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when email already exist
     * @throws Exception
     */
    @Test
    void TC_1_1_4_EmailEsistente() throws Exception {
        //Simulate that email is present into DB
        String emailDuplicata = "Mariorossi@gmail.com";
        when(request.getParameter("email")).thenReturn(emailDuplicata);

        when(daoMock.isEmailPresent(emailDuplicata)).thenReturn(true);

        servlet.doPost(request, response);
        verifyFailure();
        verify(request).setAttribute(eq("error"), contains("Email già in uso."));
    }

    /**
     * Method to test a failure when username isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_5_UsernameErrato() throws Exception {
        when(request.getParameter("username")).thenReturn("Ma");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method to test a failure when username already exist
     * @throws Exception
     */
    @Test
    void TC_1_1_6_UsernameEsistente() throws Exception {
        String usernameDuplicata = "Mar04";
        when(request.getParameter("username")).thenReturn(usernameDuplicata);

        when(daoMock.isUserPresent(usernameDuplicata)).thenReturn(true);

        servlet.doPost(request, response);
        verifyFailure();
        verify(request).setAttribute(eq("error"), contains("Username già in uso."));
    }

    /**
     * Method used to test when a password isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_7_PasswordErrato() throws Exception {
        when(request.getParameter("password")).thenReturn("Mario2004");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when a regione isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_8_RegioneErrato() throws Exception {
        when(request.getParameter("regione")).thenReturn("");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when a province isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_9_ProvinciaErrato() throws Exception {
        when(request.getParameter("provincia")).thenReturn("");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when via isn't correct
     * @throws Exception
     */

    @Test
    void TC_1_1_10_ViaErrato() throws Exception {
        when(request.getParameter("via")).thenReturn("Piazza B*ivo");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when numCivico isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_11_NumCivicoErrato() throws Exception {
        when(request.getParameter("house-number")).thenReturn("1O");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when cap isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_12_CapErrato() throws Exception {
        when(request.getParameter("cap")).thenReturn("84O23");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a failure when city isn't correct
     * @throws Exception
     */
    @Test
    void TC_1_1_13_CittàErrato() throws Exception {
        when(request.getParameter("city")).thenReturn("Sale3rnO");
        servlet.doPost(request, response);
        verifyFailure();
    }

    /**
     * Method used to test a correct registration of the Clients
     * @throws Exception
     */
    @Test
    void TestRegistrazione() throws Exception {
        when(daoMock.isEmailPresent(anyString())).thenReturn(false);
        when(daoMock.isUserPresent(anyString())).thenReturn(false);

        servlet.doPost(request, response);

        verify(daoMock).saveClient(any(Cliente.class));

        verify(response).addCookie(argThat(cookie -> cookie.getName().equals("notification") &&
                cookie.getValue().equals("Registrazione-avvenuta-con-successo!")));

        verify(response).sendRedirect("login.jsp");

        verify(dispatcher, never()).forward(request, response);

    }

}
