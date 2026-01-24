package gestioneutente.testLogin;

import org.junit.jupiter.api.Test;
import storage.gestioneutente.Manager;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class TestLoginManager extends AbstractLoginTest {
    @Test
    void TC_1_3_7_LoginManagerConCarrello() throws Exception {
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
    void TC_1_3_8_LoginManagerSenzaCarrello() throws Exception {
        Manager managerMock = mock(Manager.class);
        when(managerMock.getUsername()).thenReturn("Antcerr04");

        // Simula la presenza di un carrello precedente
        when(session.getAttribute("carrelloList")).thenReturn(null);
        when(daoMock.getUserByCredentials("Antcerr04", "Antonio2004@")).thenReturn(managerMock);

        servlet.doPost(request, response);

        verify(session,never()).removeAttribute("carrelloList");
    }
}
