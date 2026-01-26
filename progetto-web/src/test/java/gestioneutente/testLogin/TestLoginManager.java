package gestioneutente.testLogin;

import org.junit.jupiter.api.Test;
import storage.gestioneutente.Manager;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class TestLoginManager extends AbstractLoginTest {
    private static final String username = "Antcerr04";
    private static final String password = "Antonio2004@";

    /**
     * Method used to test a Login of the Manager after inserted product in the cart
     *
     * @throws Exception
     */
    @Test
    void TC_1_3_7_LoginManagerConCarrello() throws Exception {

        Manager managerMock = mock(Manager.class);
        when(managerMock.getUsername()).thenReturn(username);


        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);


        when(session.getAttribute("carrelloList")).thenReturn(new java.util.ArrayList<>());


        when(daoMock.getUserByCredentials(username, password)).thenReturn(managerMock);


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
        Manager managerMock = mock(Manager.class);
        when(managerMock.getUsername()).thenReturn(username);

        // The cart is null
        when(session.getAttribute("carrelloList")).thenReturn(null);
        when(daoMock.getUserByCredentials(username, password)).thenReturn(managerMock);

        servlet.doPost(request, response);

        verify(session, never()).removeAttribute("carrelloList");
    }
}
