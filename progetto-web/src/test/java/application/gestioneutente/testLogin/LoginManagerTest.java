package application.gestioneutente.testLogin;

import org.junit.jupiter.api.Test;
import storage.gestioneutente.Utente;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class LoginManagerTest extends AbstractLoginTest {
    private static final String username = "Antcerr04";
    private static final String password = "Antonio2004@";

    /**
     * Method used to test a Login of the Manager after inserted product in the cart
     *
     * @throws Exception
     */
    @Test
    void TC_1_3_7_LoginManagerConCarrello() throws Exception {

        Utente utenteMock = mock(Utente.class);
       when(utenteMock.getRuolo()).thenReturn("manager");
        when(utenteMock.getUsername()).thenReturn(username);


        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);


        when(session.getAttribute("carrelloList")).thenReturn(new java.util.ArrayList<>());


        when(daoMock.getUserByCredentials(username, password)).thenReturn(utenteMock);


        servlet.doPost(request, response);


        verify(session).removeAttribute("carrelloList");
    }

    /**
     * Method used to test Manager Login without cart
     *
     * @throws Exception
     */

    @Test
    void TC_1_3_8_LoginManagerSenzaCarrello() throws Exception {
        Utente utenteMock = mock(Utente.class);
        utenteMock.setRuolo("manager");
        when(utenteMock.getUsername()).thenReturn(username);

        // The cart is null
        when(session.getAttribute("carrelloList")).thenReturn(null);
        when(daoMock.getUserByCredentials(username, password)).thenReturn(utenteMock);

        servlet.doPost(request, response);

        verify(session, never()).removeAttribute("carrelloList");
    }
}
